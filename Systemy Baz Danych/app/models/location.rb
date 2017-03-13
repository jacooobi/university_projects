class Location < ActiveRecord::Base
  include PgSearch

  pg_search_scope :search, against: [:name, :address, :opened_at, :closed_at, :capacity, :phone_number, :website]

  validates_presence_of :name, :address, :opened_at, :closed_at, :capacity, :phone_number
  validates :capacity, numericality: { greater_than_or_equal_to: 0 }

  validate :check_opened_at
  validate :check_closed_at

  def check_opened_at
    errors.add(:opened_at, "- invalid format. Valid format example: 18:00") unless Tod::TimeOfDay.parsable?(opened_at)
  end

  def check_closed_at
    errors.add(:closed_at, "- invalid format. Valid format example: 18:00") unless Tod::TimeOfDay.parsable?(closed_at)
  end

  before_save :parse_opened_at, :parse_closed_at

  has_many :concerts

  protected

  def parse_opened_at
    Tod::TimeOfDay.parse(opened_at)
  end

  def parse_closed_at
    Tod::TimeOfDay.parse(closed_at)
  end
end
