#include "players_connection.h"

int connected_players[2];

int awaiting_players() {
	if (connected_players[0] == 1 && connected_players[1] == 1) { return 0; }
	else { return 1; }
}

void add_player(int id) {
	if (id == 1 && connected_players[0] != 1) {
		connected_players[0] = 1;
		printf("Gracz 1 dołączył do gry!\n");
	}
	if (id == 2 && connected_players[1] != 1) {
		connected_players[1] = 1;
		printf("Gracz 2 dołączył do gry!\n");
	}
}

void initialize_players() {
	connected_players[0] = connected_players[1] = 0;
}

void broadcast_statuses() {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	for(int i=0; i<2; i++) {
		server_message_t msg = {i+2, {GAME_STATUS,
		 {.status = { statuses[i].resources, statuses[i].wins, statuses[i].army}}}};
		
		if (msgsnd(get_queue(), &msg, sizeof(game_message_t), 0) == -1) perror("Error");
	}

	unlock_semaphore();
	detach_memory_data(statuses);
}

