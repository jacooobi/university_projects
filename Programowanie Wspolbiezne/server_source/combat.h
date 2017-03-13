#ifndef COMBAT
#define COMBAT

#include <stdlib.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <errno.h>
#include <unistd.h>
#include "../shared_source/game_protocol.h"
#include "server_utilities.h"
#include "training.h"

extern const float LIGHT_ATTACK;
extern const float HEAVY_ATTACK;
extern const float CAVALRY_ATTACK;
extern const float WORKERS_ATTACK;

extern const float LIGHT_DEFENSE;
extern const float HEAVY_DEFENSE;
extern const float CAVALRY_DEFENSE;
extern const float WORKERS_DEFENSE;

float combat_vigor(int client_id);
float combat_shiled(int client_id);
int validate_attack_request(int attacker_id, army_t delegated_army);
int get_defender_id(int attacker_id);
void perform_attack(int attacker_id, army_t delegated_army);
void update_attacker_units(int attacker_id, army_t delegated_army);
void reset_defender_units(int defender_id);
void return_attacker_units(int attacker_id, army_t delegated_army);
void recalculate_defender_units(int client_id, float ratio);
void recalculate_attacker_units(int client_id, float ratio, army_t army);
void update_game_points(int client_id);
void send_attack_result_msg(int attacker_id, int winner_id);

#endif