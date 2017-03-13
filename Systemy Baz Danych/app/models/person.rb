class Person < ActiveRecord::Base
  include PgSearch

  pg_search_scope :search, against: [:first_name, :last_name, :age, :gender]

  validates_presence_of :first_name, :last_name, :age, :gender

  # validates :first_name, format: { with: /\A[a-zA-Z]+\z/,
  #   message: "only allows letters" }

  # validates :last_name, format: { with: /\A[a-zA-Z]+\z/,
  #   message: "only allows letters" }

  validates :age, numericality: { greater_than_or_equal_to: 0 }
  validates :age, numericality: { less_than_or_equal_to: 120 }

  has_many :tickets

  def full_name
    first_name + " " + last_name
  end
end
