#include "receiver.h"

void display_game_status(server_message_t msg) {
	clear_screen();
	printf("+---------------------+------+\n");
	printf("| (1) lekka piechota  |   %d  |\n", msg.mdata.data.status.army.light);
	printf("| (2) cieżka piechota |   %d  |\n", msg.mdata.data.status.army.heavy);
	printf("| (3) jazda           |   %d  |\n", msg.mdata.data.status.army.cavalry);
	printf("| (4) robotnicy       |   %d  |\n", msg.mdata.data.status.army.workers);
	printf("+---------------------+------+\n");
	printf("| surowce             | %d |\n",    msg.mdata.data.status.resources);
	printf("+---------------------+------+\n");
	printf("| ZWYCIĘSTWA          |   %d  |\n", msg.mdata.data.status.wins);
	printf("+---------------------+------+\n");
}

void display_attack_result(server_message_t msg) {
	int winner_id = msg.mdata.data.attack_result.winner_id;

	clear_screen();
	if (winner_id == get_client_id()) {
		printf("+----------------+\n");
		printf("| Wygrałeś bitwę |\n");
		printf("+----------------+\n");
	} else {
		printf("+------------------+\n");
		printf("| Przegrałeś bitwę |\n");
		printf("+------------------+\n");
	}

	sleep(2);
}

void display_end_game_result(server_message_t msg) {
	int winner_id = msg.mdata.data.attack_result.winner_id;

	clear_screen();
	if (winner_id == get_client_id()) {
		printf("+----------------------------------------+\n");
		printf("|\tKONIEC GRY : WYGRAŁEŚ!!! 	 |\n");
		printf("+----------------------------------------+\n");
	} else {
		printf("+---------------------------------------+\n");
		printf("|\tKONIEC GRY : PRZEGRAŁEŚ!!!	|\n");
		printf("+---------------------------------------+\n");
	}

	sleep(2);

	exit(0);
}

void clear_screen() {
	printf("\e[1;1H\e[2J");
	rewind(stdout);   
}