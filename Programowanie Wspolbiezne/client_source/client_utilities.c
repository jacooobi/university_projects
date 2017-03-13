#include "client_utilities.h"
#include "../shared_source/game_protocol.h"

int queue_id;
int client_id;

void set_client_id(int id) {
	client_id = id;
}

void connect_to_server() {
	printf("Łączę z serwerem...\n");
	
	key_t queue_key = ftok(KEY_PATH, SERVER_MSGQ_ID);
	queue_id = msgget(queue_key, 0644);

	if (queue_id == -1) perror("Serwer może nie być uruchomiony!");
  
    server_message_t msg = { 1, { CONNECT, { .client_id = get_client_id() }}};

    if (msgsnd(get_queue(), &msg, sizeof(game_message_t), 0) == -1 ) perror("Błąd przy połączeniu z serwerem");
    
    printf("Pomyślnie połączono z serwerem!\n");
}

void validate_presence_of_id(int argc){
	if (argc < 2) {
		printf("Nie podano id gracza!\n");
		exit(0);
	}
}

int get_queue() {
	return queue_id;
}

int get_client_id() {
	return client_id;
}
