class CreateBands < ActiveRecord::Migration
  def change
    create_table :bands do |t|
      t.string :name
      t.string :genre
      t.integer :formed_on
      t.string :country
      t.string :about

      t.timestamps null: false
    end
  end
end
