class CreateLocations < ActiveRecord::Migration
  def change
    create_table :locations do |t|
      t.string :name
      t.string :address
      t.integer :opened_at
      t.integer :closed_at
      t.integer :capacity
      t.integer :phone_number
      t.string :website

      t.timestamps null: false
    end
  end
end
