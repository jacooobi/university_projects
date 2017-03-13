#ifndef TRAINING
#define TRAINING

#include <stdlib.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <errno.h>
#include <unistd.h>
#include "../shared_source/game_protocol.h"
#include "server_utilities.h"

extern const int LIGHT_VALUE;
extern const int HEAVY_VALUE;
extern const int CAVALRY_VALUE;
extern const int WORKERS_VALUE;

extern const int LIGHT_TIME;
extern const int HEAVY_TIME;
extern const int CAVALRY_TIME;
extern const int WORKERS_TIME;

void update_resources();
void train_units();
void produce_unit(int type, int client_id);
void produce_units(int type, int count, int client_id);
void subtract_resources(int resources_count, int client_id);
int validate_training_request(int type, int count, int client_id);
int production_time(int type);
int production_cost(int type);
int required_resources_amount(int type, int count);

#endif