# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

Person.create(first_name: 'Paulina',last_name: 'Sobczak', age: 19, gender: 'kobieta')
Person.create(first_name: 'Henryk',last_name: 'Bąk' , age: 20,gender: 'mężczyzna')
Person.create(first_name: 'Kazimierz',last_name: 'Górski', age: 22,gender: 'mężczyzna')
Person.create(first_name: 'Irena',last_name: 'Wójcik',age: 30, gender: 'kobieta')
Person.create(first_name: 'Marian',last_name: 'Chmielewski',age: 25,gender: 'mężczyzna')
Person.create(first_name: 'Małgorzata',last_name: 'Jaworska',age: 27, gender: 'kobieta')
Person.create(first_name: 'Ewa',last_name: 'Duda',age: 18, gender: 'kobieta')
Person.create(first_name: 'Jakub',last_name: 'Malinowski',age: 19,gender: 'mężczyzna')
Person.create(first_name: 'Jadwiga',last_name: 'Brzezińska',age: 20, gender: 'kobieta')
Person.create(first_name: 'Roman',last_name: 'Sawicki',age: 25,gender: 'mężczyzna')
Person.create(first_name: 'Marcin',last_name: 'Szymczak',age: 26,gender: 'mężczyzna')
Person.create(first_name: 'Joanna',last_name: 'Baranowska',age: 25, gender: 'kobieta')
Person.create(first_name: 'Maciej',last_name: 'Szczepański',age: 27,gender: 'mężczyzna')
Person.create(first_name: 'Czesław',last_name: 'Wróbel',age: 20,gender: 'mężczyzna')
Person.create(first_name: 'Grażyna',last_name: 'Górska',age: 20, gender: 'kobieta')
Person.create(first_name: 'Wanda',last_name: 'Krawczyk',age: 24, gender: 'kobieta')
Person.create(first_name: 'Renata',last_name: 'Urbańska',age: 19, gender: 'kobieta')
Person.create(first_name: 'Wiesława',last_name: 'Tomaszewska',age: 22, gender: 'kobieta')
Person.create(first_name: 'Bożena',last_name: 'Baranowska',age: 22, gender: 'kobieta')
Person.create(first_name: 'Ewelina',last_name: 'Malinowska',age: 23, gender: 'kobieta')
Person.create(first_name: 'Anna',last_name: 'Krajewska',age: 40, gender: 'kobieta')
Person.create(first_name: 'Mieczysław',last_name: 'Zając',age: 20,gender: 'mężczyzna')
Person.create(first_name: 'Wiesław',last_name: 'Przybylski',age: 25,gender: 'mężczyzna')
Person.create(first_name: 'Dorota',last_name: 'Tomaszewska',age: 28, gender: 'kobieta')
Person.create(first_name: 'Jerzy',last_name: 'Wróblewski',age: 28,gender: 'mężczyzna')

Concert.create(name: 'Anita Lipnicka',date: '20.04.2017')
Concert.create(name: 'Deer Daniel',date: '20.04.2017')
Concert.create(name: 'Edyta Gorecka',date: '21.04.2017')
Concert.create(name: 'Lucy Rose',date: '22.04.2017')
Concert.create(name: 'Snowman',date: '22.04.2017')
Concert.create(name: 'Rysy',date: '28.07.2017')
Concert.create(name: 'Crystal Castles',date: '30.07.2017')
Concert.create(name: 'GusGus',date: '29.07.2017')
Concert.create(name: 'Leftfield',date: '29.07.2017')
Concert.create(name: 'Dj koze',date: '30.07.2017')
Concert.create(name: 'Brodka',date: '4.08.2017')
Concert.create(name: 'Jenny Hval',date: '4.08.2017')
Concert.create(name: 'The Kills',date: '5.08.2017')
Concert.create(name: 'Sotei',date: '5.08.2017')
Concert.create(name: 'Jaaa!',date: '6.08.2017')
Concert.create(name: 'Kiasmos',date: '6.08.2017')

Location.create(name: 'LMain Stage', address: 'plaża nad Wisłą; Płock',opened_at: 18.00,closed_at: 3.00,capacity: 10000,phone_number: '48752888999',website: 'www.audioriver.pl')
Location.create(name: 'Circus Tent', address: 'plaża nad Wisłą; Płock',opened_at: 20.00,closed_at: 6.00,capacity:5000,phone_number: '48752888999',website: 'www.audioriver.pl')
Location.create(name: 'Electronic Beats Stage', address: 'plaża nad Wisłą; Płock',opened_at: 21.30,closed_at: 5.00,capacity:5000,phone_number: '48752888999',website: 'www.audioriver.pl')
Location.create(name: 'Scena miasta muzyki', address: 'Dolina Trzech Stawów; Katowice',opened_at: 16.00,closed_at:1.00,capacity:15000,phone_number: '48752234999',website: 'www.off-festival.pl')
Location.create(name: 'Scena trójki', address: 'Dolina Trzech Stawów; Katowice',opened_at: 15.00,closed_at: 1.00,capacity:5000,phone_number: '48752234999',website: 'www.off-festival.pl')
Location.create(name: 'Scena eksperymentalna', address: 'Dolina Trzech Stawów; Katowice',opened_at: 15.30,closed_at: 3.00,capacity:7000,phone_number: '48752234999',website: 'www.off-festival.pl')
Location.create(name: 'Meskalina', address: 'Stary Rynek 6; Poznań',opened_at: 12.00,closed_at: 24.00,capacity:200,phone_number: '48508171749',website: 'www.meskalina.com')
Location.create(name: 'Centrum Kultury Zamek', address: 'ulica św. Marcin 80/82; Poznań',opened_at:16.00,closed_at:23.00,capacity:500,phone_number: '616465272', website: 'www.ckzamek.pl')
Location.create(name: 'Plac Wolnosci', address: 'plac Wolnosci; Poznań',opened_at: 1.00,closed_at: 24.00,capacity:5000,phone_number: '616465332')
Location.create(name: 'Projekt Lab', address: 'ulica Grochowe Łąki 5; Poznań',opened_at: 20.00,closed_at: 5.00,capacity:500,phone_number: '48508141579')

Band.create(name: 'Anita Lipnicka',genre: 'pop',formed_on: 2002,country: 'Polska')
Band.create(name: 'Deer Daniel',genre: 'indie',formed_on: 2016,country: 'Polska')
Band.create(name: 'Edyta Gorecka',genre: 'pop',formed_on: 2014,country: 'Polska')
Band.create(name: 'Lucy Rose',genre: 'pop',formed_on: 2012,country: 'Wielka Brytania')
Band.create(name: 'Snowman',genre: 'rock', formed_on: 2008,country: 'Polska')
Band.create(name: 'Rysy',genre: 'electronic', formed_on: 2015,country: 'Polska')
Band.create(name: 'Crystal Castles',genre: 'electronic',formed_on: 2003,country: 'Kanada')
Band.create(name: 'GusGus',genre: 'electronic',formed_on: 1995,country: 'Islandia')
Band.create(name: 'Leftfield',genre: 'techno', formed_on: 1989,country: 'Wielka Brytania')
Band.create(name: 'Dj koze',genre: 'techno',formed_on:2013,country: 'Niemcy')
Band.create(name: 'Brodka',genre: 'pop', formed_on:2004,country: 'Polska')
Band.create(name: 'Jenny Hval',genre: 'art pop', formed_on: 2013,country: 'Norwegia')
Band.create(name: 'The Kills',genre: 'indie rock', formed_on: 2000,country: 'USA')
Band.create(name: 'Sotei',genre: 'electronic', formed_on: 2014,country: 'Polska')
Band.create(name: 'Jaaa!',genre: 'freak elektronic', formed_on: 2015,country: 'Polska')
Band.create(name: 'Kiasmos',genre: 'electronic', formed_on: 2009,country: 'Islandia')

Ticket.create(price: 89,category: 'przedsprzedaz',valid_from: '20.04.2017',valid_to: '22.04.2017')
Ticket.create(price: 89,category: 'przedsprzedaz',valid_from: '20.04.2017',valid_to: '22.04.2017')
Ticket.create(price: 100,category: 'normalny',valid_from: '20.04.2017', valid_to: '22.04.2017')
Ticket.create(price: 89,category: 'przedsprzedaz',valid_from: '20.04.2017',valid_to: '22.04.2017')
Ticket.create(price: 100,category: 'normalny',valid_from: '20.04.2017', valid_to: '22.04.2017')
Ticket.create(price: 89,category: 'przedsprzedaz',valid_from: '20.04.2017',valid_to: '22.04.2017')
Ticket.create(price: 100,category: 'normalny',valid_from: '20.04.2017', valid_to: '22.04.2017')
Ticket.create(price: 89,category: 'przedsprzedaz',valid_from: '20.04.2017',valid_to: '22.04.2017')
Ticket.create(price: 250,category: 'przedsprzedaz',valid_from: '28.07.2017',valid_to: '30.07.2017')
Ticket.create(price: 320,category: 'normalny',valid_from: '28.07.2017', valid_to: '30.07.2017')
Ticket.create(price: 250,category: 'przedsprzedaz',valid_from: '28.07.2017',valid_to: '30.07.2017')
Ticket.create(price: 320,category: 'normalny',valid_from: '28.07.2017', valid_to: '30.07.2017')
Ticket.create(price: 250,category: 'przedsprzedaz',valid_from: '28.07.2017',valid_to: '30.07.2017')
Ticket.create(price: 320,category: 'normalny',valid_from: '28.07.2017', valid_to: '30.07.2017')
Ticket.create(price: 320,category: 'normalny',valid_from: '28.07.2017', valid_to: '30.07.2017')
Ticket.create(price: 250,category: 'przedsprzedaz',valid_from: '28.07.2017',valid_to: '30.07.2017')
Ticket.create(price: 250,category: 'przedsprzedaz',valid_from: '28.07.2017',valid_to: '30.07.2017')
Ticket.create(price: 320,category: 'normalny',valid_from: '28.07.2017', valid_to: '30.07.2017')
Ticket.create(price: 320,category: 'normalny',valid_from: '28.07.2017', valid_to: '30.07.2017')
Ticket.create(price: 280,category: 'przedsprzedaz',valid_from: '4.08.2017',valid_to: '6.08.2017')
Ticket.create(price: 350,category: 'normalny',valid_from: '4.08.2017', valid_to: '6.08.2017')
Ticket.create(price: 280,category: 'przedsprzedaz',valid_from: '4.08.2017',valid_to: '6.08.2017')
Ticket.create(price: 350,category: 'normalny',valid_from: '4.08.2017', valid_to: '6.08.2017')
Ticket.create(price: 280,category: 'przedsprzedaz',valid_from: '4.08.2017',valid_to: '6.08.2017')
Ticket.create(price: 350,category: 'normalny',valid_from: '4.08.2017', valid_to: '6.08.2017')
Ticket.create(price: 350,category: 'normalny',valid_from: '4.08.2017', valid_to: '6.08.2017')
Ticket.create(price: 280,category: 'przedsprzedaz',valid_from: '4.08.2017',valid_to: '6.08.2017')
Ticket.create(price: 350,category: 'normalny',valid_from: '4.08.2017', valid_to: '6.08.2017')
Ticket.create(price: 350,category: 'normalny',valid_from: '4.08.2017', valid_to: '6.08.2017')
Ticket.create(price: 280,category: 'przedsprzedaz',valid_from: '4.08.2017',valid_to: '6.08.2017')
Ticket.create(price: 350,category: 'normalny',valid_from: '4.08.2017', valid_to: '6.08.2017')
Ticket.create(price: 350,category: 'normalny',valid_from: '4.08.2017', valid_to: '6.08.2017')
Ticket.create(price: 280,category: 'przedsprzedaz',valid_from: '4.08.2017',valid_to: '6.08.2017')

Festival.create(name: 'Enea Spring Break', edition: '4', start_date: '20.04.2017', end_date: '22.04.2017')
Festival.create(name: 'Audioriver Festival', edition: '11', start_date: '28.07.2017', end_date: '30.07.2017')
Festival.create(name: 'OFF Festival', edition: '11', start_date: '4.08.2017', end_date: '6.08.2017')
