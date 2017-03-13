#include "combat.h"
#include "math.h"

const float LIGHT_ATTACK = 1.0;
const float HEAVY_ATTACK = 1.5;
const float CAVALRY_ATTACK = 3.5;
// const float WORKERS_ATTACK = 0;

const float LIGHT_DEFENSE = 1.2;
const float HEAVY_DEFENSE = 3.0;
const float CAVALRY_DEFENSE = 1.2;
// const float WORKERS_DEFENSE = 0;

float combat_vigor(int client_id) {
	int vigor;
	army_t army;
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();
	army = statuses[client_id].army;

	vigor = army.light * LIGHT_ATTACK + army.heavy * HEAVY_ATTACK + army.cavalry * CAVALRY_ATTACK;

	unlock_semaphore();

	detach_memory_data(statuses);
	return vigor;
}

float combat_shield(int client_id) {
	int defense;
	army_t army;

	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();
	army = statuses[client_id].army;

	defense = army.light * LIGHT_DEFENSE + army.heavy * HEAVY_DEFENSE + army.cavalry * CAVALRY_DEFENSE;

	unlock_semaphore();

	detach_memory_data(statuses);
	return defense;
}

int validate_attack_request(int attacker_id, army_t delegated_army) {
	int result = 0;
	army_t available_army;
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();
	available_army = statuses[attacker_id].army;

	if(available_army.light < delegated_army.light ||
	   available_army.heavy < delegated_army.heavy ||
	   available_army.cavalry < delegated_army.cavalry) {
		printf("Wybrano wiekszą liczbę jednostek do ataku niż posiadana!\n");
		result = -1;
	}

	unlock_semaphore();
	detach_memory_data(statuses);

	return result;
}

void perform_attack(int attacker_id, army_t delegated_army) {
	int defender_id = get_defender_id(attacker_id);

	float attacker_vigor_points = combat_vigor(attacker_id);
	float attacker_shield_points = combat_shield(attacker_id);
	float defender_vigor_points = combat_vigor(defender_id);
	float defender_shield_points = combat_shield(defender_id);

	update_attacker_units(attacker_id, delegated_army);

	sleep(5);

	if (attacker_vigor_points > defender_shield_points) {
		reset_defender_units(defender_id);
		return_attacker_units(attacker_id, delegated_army);
		printf("Siły gracza %d zniszczyly całkowicie armie gracza %d \n", attacker_id+1, defender_id+1);
		update_game_points(attacker_id);
		send_attack_result_msg(attacker_id, attacker_id);
	} else {
		printf("Siły gracza %d obroniły się przed atakiem gracza %d \n", defender_id+1, attacker_id+1);
		printf("%f <--> %f\n", attacker_vigor_points, defender_shield_points);
		recalculate_attacker_units(attacker_id, attacker_vigor_points/defender_shield_points, delegated_army);
		if (attacker_shield_points > defender_vigor_points) recalculate_defender_units(defender_id, defender_vigor_points/attacker_shield_points);
		send_attack_result_msg(attacker_id, defender_id);
	}
}

void update_attacker_units(int attacker_id, army_t delegated_army) {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	statuses[attacker_id].army.light -= delegated_army.light;
	statuses[attacker_id].army.heavy -= delegated_army.heavy;
	statuses[attacker_id].army.cavalry -= delegated_army.cavalry;

	unlock_semaphore();
	detach_memory_data(statuses);
}

void reset_defender_units(int defender_id) {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	statuses[defender_id].army.light = 0;
	statuses[defender_id].army.heavy = 0;
	statuses[defender_id].army.cavalry = 0;

	unlock_semaphore();
	detach_memory_data(statuses);
}

void return_attacker_units(int defender_id, army_t army) {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	statuses[defender_id].army.light += army.light;
	statuses[defender_id].army.heavy += army.heavy;
	statuses[defender_id].army.cavalry += army.cavalry;

	unlock_semaphore();
	detach_memory_data(statuses);
}


void recalculate_defender_units(int client_id, float ratio) {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	printf("Obrona, było %d lekkich %d ciężkich i %d koni\n",
	statuses[client_id].army.light,
	statuses[client_id].army.heavy,
	statuses[client_id].army.cavalry);

	printf("Obrona, stracono %d lekkich %d ciężkich i %d koni\n",
	(int)floor((double)(statuses[client_id].army.light * ratio)),
	(int)floor((double)(statuses[client_id].army.heavy * ratio)),
	(int)floor((double)(statuses[client_id].army.cavalry * ratio)));

	statuses[client_id].army.light -= (int)floor((double)(statuses[client_id].army.light * ratio));
	statuses[client_id].army.heavy -= (int)floor((double)(statuses[client_id].army.heavy * ratio));
	statuses[client_id].army.cavalry -= (int)floor((double)(statuses[client_id].army.cavalry * ratio));

	unlock_semaphore();
	detach_memory_data(statuses);
}

void recalculate_attacker_units(int client_id, float ratio, army_t army) {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	printf("Atak, było %d lekkich %d ciężkich i %d koni\n",
	army.light,
	army.heavy,
	army.cavalry);

	printf("Atak, stracono %d lekkich %d ciężkich i %d koni\n",
	(int)floor((double)(army.light * ratio)),
	(int)floor((double)(army.heavy * ratio)),
	(int)floor((double)(army.cavalry * ratio)));


	statuses[client_id].army.light += (army.light - (int)floor((double)(army.light * ratio)));
	statuses[client_id].army.heavy += (army.heavy - (int)floor((double)(army.heavy * ratio)));
	statuses[client_id].army.cavalry += (army.cavalry - (int)floor((double)(army.cavalry * ratio)));

	unlock_semaphore();
	detach_memory_data(statuses);
}


void update_game_points(int client_id) {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();
	statuses[client_id].wins += 1;

	if(statuses[client_id].wins == 5) {
		for(int i=0;i<2;i++) {
			server_message_t msg = { i+2, {GAME_RESULT, { .game_result = { client_id+1 }}}};
			if (msgsnd(get_queue(), &msg, sizeof(game_message_t), 0) == -1) perror("Error");	
		}
		
		printf("Gra została zakończona...\n");
		exit(0);
	}

	unlock_semaphore();
	detach_memory_data(statuses);
}


void send_attack_result_msg(int attacker_id, int winner_id) {
	for(int i=0;i<2;i++) {
		server_message_t msg = { i+2, {ATTACK_RESULT, { .attack_result = { winner_id + 1}}}};

		if (msgsnd(get_queue(), &msg, sizeof(game_message_t), 0) == -1) perror("Error");
	}	
}

int get_defender_id(int attacker_id) {
	return (attacker_id + 1) % 2;
}
