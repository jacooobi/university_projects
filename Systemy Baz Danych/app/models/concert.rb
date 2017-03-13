class Concert < ActiveRecord::Base
  include PgSearch

  pg_search_scope :search, against: [:name, :date]

  validates_presence_of :name, :date, :location_id, :festival_id, :band_id

  # validates :name, format: { with: /\A[a-zA-Z]+\z/,
  #   message: "only allows letters" }

  # validates_format_of :date, :with => /\d{4}-\d{2}-\d{2}/, message: "^Date must be in the following format: yyyy-mm-dd"

  belongs_to :band
  belongs_to :location
  belongs_to :festival
end
