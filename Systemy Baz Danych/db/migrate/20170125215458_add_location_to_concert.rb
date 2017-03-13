class AddLocationToConcert < ActiveRecord::Migration
  def change
    add_reference :concerts, :location, index: true, foreign_key: true
  end
end
