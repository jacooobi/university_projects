#include "server_utilities.h"

int queue_id;
int memory_id;
int semaphore_id;

void initialize_memory() {
	key_t key = ftok(MEMORY_KEY_PATH, MEMORY_KEY_ID);
	memory_id = shmget(key, sizeof(game_status_t) * 2, IPC_CREAT | 0644);
	
	if (memory_id == -1) {
		perror("Utworzenie segmentu pamieci wspoldzielonej");
		exit(1);
	}
}

void initialize_queue() {
	key_t queue_key = ftok(KEY_PATH, SERVER_MSGQ_ID);
    queue_id = msgget(queue_key, IPC_CREAT | 0644);

    if (queue_id == -1) {
    	perror("Błąd przy tworzeniu kolejki komunikatów");
		exit(1);
    }

    printf("Pomyślnie uruchomiono serwer\n");
}

int get_queue() {
	return queue_id;
}

int get_semaphore() {
	return semaphore_id;
}

game_status_t *get_attached_memory() {
	return (game_status_t *) shmat(memory_id, 0, 0);
}

void detach_memory_data(game_status_t *data) {
  if (shmdt(data) == -1) {
    perror("Błąd przy odłączaniu pamięci");
    exit(1);
  };
}

void initialize_game_data() {
	game_status_t *statuses;
	statuses = get_attached_memory();

	if ((void *) -1 == (void *)statuses){
	    perror("Błąd przy dołączaniu pamięci");
	    exit(1);
	}

	for(int i=0; i<2; i++) {
		statuses[i] = (game_status_t){300,0,{0,0,0,0}};
	}

	detach_memory_data(statuses);
}

void initialize_semaphore() {
	key_t key = ftok(SEM_KEY_PATH, SEM_KEY_ID);
	semaphore_id = semget(key, 1, IPC_CREAT | 0644);
	
	if (semaphore_id == -1) {
		perror("Utworzenie semafora");
		exit(1);
	}

	if (semctl(semaphore_id, 0, SETVAL, (int)1) == -1) {
		perror("Nadanie wartosci semaforowi");
		exit(1);
	}
}


int unlock_semaphore() {
  struct sembuf sem_struct = {0, 1, SEM_UNDO};
  return semop(get_semaphore(), &sem_struct, 1);
}

int lock_semaphore() {
  struct sembuf sem_struct = {0, -1, SEM_UNDO};
  return semop(get_semaphore(), &sem_struct, 1);
}
