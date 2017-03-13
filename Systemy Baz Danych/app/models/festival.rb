class Festival < ActiveRecord::Base
  include PgSearch

  pg_search_scope :search, against: [:name, :edition, :start_date, :end_date]

  validates_presence_of :name, :edition, :start_date, :end_date

  validates :edition, numericality: { greater_than_or_equal_to: 0 }

  validate :validate_end_date_before_start_date

  has_many :concerts, dependent: :destroy
  has_many :tickets, dependent: :destroy


  def validate_end_date_before_start_date
    if end_date && start_date
      errors.add(:end_date, "must occur after start date") if end_date < start_date
    end
  end
end
