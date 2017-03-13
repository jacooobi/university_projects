SERVER_SRC=$(wildcard ./server_source/*.c) $(wildcard ./shared_source/*.c)
CLIENT_SRC=$(wildcard ./client_source/*.c) $(wildcard ./shared_source/*.c)

server: $(SERVER_SRC)
	gcc -o $@ $^ -Wall

client: $(CLIENT_SRC)
	gcc -o $@ $^ -Wall