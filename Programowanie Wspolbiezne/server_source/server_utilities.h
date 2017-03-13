#ifndef SERVER_UTILITIES
#define SERVER_UTILITIES

#include <stdlib.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <errno.h>
#include <unistd.h>
#include "../shared_source/game_protocol.h"

#define KEY_PATH "/tmp"
#define SERVER_MSGQ_ID 19
#define MEMORY_KEY_PATH "/tmp"
#define MEMORY_KEY_ID 20
#define SEM_KEY_PATH "/tmp"
#define SEM_KEY_ID 55

extern int queue_id;
extern int memory_id;
extern int semaphore_id;

void initialize_memory();
void initialize_queue();
void initialize_game_data();
void initialize_semaphore();
void detach_memory_data(game_status_t *data);
game_status_t *get_attached_memory();
int get_queue();
int get_semaphore();
int lock_semaphore();
int unlock_semaphore();

#endif