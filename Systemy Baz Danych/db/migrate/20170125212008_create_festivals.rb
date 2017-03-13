class CreateFestivals < ActiveRecord::Migration
  def change
    create_table :festivals do |t|
      t.string :name
      t.string :edition
      t.date :start_date
      t.date :end_date

      t.timestamps null: false
    end
  end
end
