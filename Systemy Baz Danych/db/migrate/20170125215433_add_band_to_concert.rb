class AddBandToConcert < ActiveRecord::Migration
  def change
    add_reference :concerts, :band, index: true, foreign_key: true
  end
end
