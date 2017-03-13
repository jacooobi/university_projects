# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20170213210652) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "bands", force: :cascade do |t|
    t.string   "name",       null: false
    t.string   "genre",      null: false
    t.integer  "formed_on",  null: false
    t.string   "country",    null: false
    t.string   "about"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "concerts", force: :cascade do |t|
    t.string   "name",        null: false
    t.date     "date",        null: false
    t.datetime "created_at",  null: false
    t.datetime "updated_at",  null: false
    t.integer  "festival_id"
    t.integer  "band_id"
    t.integer  "location_id"
  end

  add_index "concerts", ["band_id"], name: "index_concerts_on_band_id", using: :btree
  add_index "concerts", ["festival_id"], name: "index_concerts_on_festival_id", using: :btree
  add_index "concerts", ["location_id"], name: "index_concerts_on_location_id", using: :btree

  create_table "festivals", force: :cascade do |t|
    t.string   "name",       null: false
    t.string   "edition",    null: false
    t.date     "start_date", null: false
    t.date     "end_date",   null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "locations", force: :cascade do |t|
    t.string   "name",         null: false
    t.string   "address",      null: false
    t.string   "opened_at",    null: false
    t.string   "closed_at",    null: false
    t.integer  "capacity",     null: false
    t.string   "phone_number", null: false
    t.string   "website"
    t.datetime "created_at",   null: false
    t.datetime "updated_at",   null: false
  end

  create_table "people", force: :cascade do |t|
    t.string   "first_name", null: false
    t.string   "last_name",  null: false
    t.integer  "age",        null: false
    t.string   "gender",     null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "tickets", force: :cascade do |t|
    t.integer  "price",       null: false
    t.string   "category",    null: false
    t.date     "valid_from",  null: false
    t.date     "valid_to",    null: false
    t.datetime "created_at",  null: false
    t.datetime "updated_at",  null: false
    t.integer  "person_id"
    t.integer  "festival_id"
  end

  add_index "tickets", ["festival_id"], name: "index_tickets_on_festival_id", using: :btree
  add_index "tickets", ["person_id"], name: "index_tickets_on_person_id", using: :btree

end
