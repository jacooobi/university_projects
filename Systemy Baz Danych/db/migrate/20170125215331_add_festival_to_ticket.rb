class AddFestivalToTicket < ActiveRecord::Migration
  def change
    add_reference :tickets, :festival, index: true, foreign_key: true
  end
end
