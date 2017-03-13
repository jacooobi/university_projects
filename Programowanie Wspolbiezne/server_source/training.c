#include "training.h"

const int LIGHT_VALUE = 100;
const int HEAVY_VALUE = 250;
const int CAVALRY_VALUE = 550;
const int WORKERS_VALUE = 150;

const int LIGHT_TIME = 2;
const int HEAVY_TIME = 3;
const int CAVALRY_TIME = 5;
const int WORKERS_TIME = 2;

void update_resources() {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	for(int i=0; i<2; i++) { statuses[i].resources += 50 + 5*statuses[i].army.workers; }
	unlock_semaphore();

	detach_memory_data(statuses);
}

void subtract_resources(int resources_count, int client_id) {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	statuses[client_id].resources -= resources_count;
	printf("Liczba surowców gracza %d została pomniejszona o %d\n", client_id+1, resources_count);
	unlock_semaphore();

	detach_memory_data(statuses);
}

void produce_unit(int type, int client_id) {
	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	switch (type) {
	    case 1: {
	      statuses[client_id].army.light += 1;
	      printf("Wytworzono lekką piechote dla gracza nr %d\n", client_id+1);
	      break;
	    }
	    case 2: {
	      statuses[client_id].army.heavy += 1;
	      printf("Wytworzono ciężką piechotę dla gracza nr %d\n", client_id+1);
	      break;
	    }
	    case 3: {
	      statuses[client_id].army.cavalry += 1;
	      printf("Wytworzono jazdę dla gracza nr %d\n", client_id+1);
	      break;
	    }
	    case 4: {
	      statuses[client_id].army.workers += 1;
	      printf("Wytworzono robotnika dla gracza nr %d\n", client_id+1);
	      break;
	    }
    	default: break;
  	}
  	unlock_semaphore();
  	detach_memory_data(statuses);
}

int validate_training_request(int type, int count, int client_id) {
	if(type < 1 || type > 4) {
		printf("Niedopuszczalny typ jednostki! Wybierz 1 - 4\n");
		return -1;
	}

	lock_semaphore();

	game_status_t *statuses;
	statuses = get_attached_memory();

	int accumulated_resources = statuses[client_id].resources;
	unlock_semaphore();
	detach_memory_data(statuses);

	int total_required_resources = required_resources_amount(type, count);

	if(accumulated_resources < total_required_resources) {
		printf("Niewystarczajaca liczba surowców! Posiadane: %d Wymagane: %d\n", accumulated_resources, total_required_resources);
		return -1;
	}

	return 0;
}


int production_time(int type) {
	switch(type) {
		case 1:
			return LIGHT_TIME;
		case 2:
			return HEAVY_TIME;
		case 3:
			return CAVALRY_TIME;
		case 4:
			return WORKERS_TIME;
		default:
			return 0;
	}
}

int production_cost(int type) {
	switch(type) {
		case 1:
			return LIGHT_VALUE;
		case 2:
			return HEAVY_VALUE;
		case 3:
			return CAVALRY_VALUE;
		case 4:
			return WORKERS_VALUE;
		default:
			return 0;
	}
}

int required_resources_amount(int type, int count) {
	return production_cost(type)*count;
}

void produce_units(int type, int count, int client_id) {
	if(fork() == 0) {
		subtract_resources((production_cost(type) * count), client_id);

		for(int i=0; i<count; i++) {
			sleep(production_time(type));
			produce_unit(type, client_id);
		}

		exit(0);
	}
}