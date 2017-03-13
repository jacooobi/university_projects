#ifndef PLAYERS_CONNECTION
#define PLAYERS_CONNECTION

#include <stdlib.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <errno.h>
#include <unistd.h>
#include "../shared_source/game_protocol.h"
#include "server_utilities.h"

extern int connected_players[2];

int awaiting_players();
void add_player(int id);
void initialize_players();
void broadcast_statuses();

#endif