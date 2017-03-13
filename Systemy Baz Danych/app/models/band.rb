class Band < ActiveRecord::Base
  include PgSearch

  pg_search_scope :search, against: [:name, :genre, :formed_on, :country, :about]

  validates_presence_of :name, :genre, :formed_on, :country

  validates :formed_on, numericality: { greater_than_or_equal_to: 1900, less_than_or_equal_to: 2017 }

  has_many :concerts
end
