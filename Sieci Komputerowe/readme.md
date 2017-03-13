# Sieci komputerowe (Computer Networks)

This project is a basic implementation of the MapReduce algorithm using C socket library.
Take any book from the gutenberg project site. This program will count all the words used, average word length and other stats. Different client nodes are emulated with threads. Written in C++.


server file compilation:

`gcc first_server.cpp -lstdc++ -lpthread -o server`

client file compilation:

`gcc first_client.cpp -lstdc++ -o client`

main file compilation:

`gcc main.cpp -lstdc++ -o main`
