#include "dispatcher.h"
#include "../shared_source/game_protocol.h"

char* new_command() {
    size_t nbytes = 20;
    char *my_string;

    my_string = (char *)malloc(nbytes+1);

    getline(&my_string, &nbytes, stdin);
    my_string[strlen(my_string)-1] = 0;
    return my_string;
}

server_message_t * construct_message(char* command) {
	char * pch;
	pch = strtok(command, " ");
	server_message_t *msg = (server_message_t*)malloc(sizeof(server_message_t));


	if (pch == NULL) return NULL;

	if (strncmp(pch, "atak", 4) == 0) {
		int *values = malloc (sizeof (int) * 4);
		values = split_values(pch, 3);


		*msg = (server_message_t){1, {ATTACK, { .attack = { get_client_id(), .army = {values[0], values[1], values[2], 0}}}}};
	} else if(strncmp(pch, "trening", 7) == 0) {
		int *values = malloc (sizeof (int) * 2);
		values = split_values(pch, 2);

		*msg = (server_message_t){1, {UNIT_TRAINING, { .training = { get_client_id(), values[0], values[1] }}}};

	} else {
		printf("Podana komenda nie została rozpoznana!\n");
		return NULL;
	}

	return msg;
}

int* split_values(char* values, int count) {
	int i = 0;
	int *result = malloc (sizeof (int) * count);

	while(values != NULL) {
		if (i > count-1) return result;
		values = strtok(NULL, " ");
		result[i] = atoi(values);
		i++;
	}

	return result;
}

void display_prompt() {
	printf("Przykład: \"trening (rodzaj_jednostek) (liczba_jednostek)\" -> trening 1 5\n");
	printf("Przykład: \"atak (liczba_lekkich) (liczba_ciezkich) (liczba_konnych) \" -> atak 2 2 0\n");

	printf(">>> ");
}