class ChangePhoneNumberColumnType < ActiveRecord::Migration
  def change
    change_column :locations, :phone_number, :string
  end
end
