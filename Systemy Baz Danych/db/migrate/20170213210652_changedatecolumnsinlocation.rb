class Changedatecolumnsinlocation < ActiveRecord::Migration
  def change
    change_column :locations, :opened_at, :string
    change_column :locations, :closed_at, :string
  end
end
