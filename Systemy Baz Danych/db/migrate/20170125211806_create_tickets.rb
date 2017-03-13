class CreateTickets < ActiveRecord::Migration
  def change
    create_table :tickets do |t|
      t.integer :price
      t.string :category
      t.date :valid_from
      t.date :valid_to

      t.timestamps null: false
    end
  end
end
