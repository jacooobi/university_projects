class Ticket < ActiveRecord::Base
  include PgSearch

  pg_search_scope :search, against: [:price, :category, :valid_from, :valid_to]

  validates_presence_of :price, :category, :valid_from, :valid_to, :festival_id

  validates :price, numericality: { greater_than_or_equal_to: 0 }

  validate :validate_valid_from_before_valid_to

  belongs_to :festival
  belongs_to :person

  def validate_valid_from_before_valid_to
    if valid_to && valid_from
      errors.add(:valid_to, "must occur after valid from") if valid_to < valid_from
    end
  end
end
