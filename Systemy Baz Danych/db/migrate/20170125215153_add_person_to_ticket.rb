class AddPersonToTicket < ActiveRecord::Migration
  def change
    add_reference :tickets, :person, index: true, foreign_key: true
  end
end
