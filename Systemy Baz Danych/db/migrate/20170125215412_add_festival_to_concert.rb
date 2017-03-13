class AddFestivalToConcert < ActiveRecord::Migration
  def change
    add_reference :concerts, :festival, index: true, foreign_key: true
  end
end
