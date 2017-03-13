#ifndef RECEIVER
#define RECEIVER

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include "../shared_source/game_protocol.h"
#include "client_utilities.h"

int message_intercepted(server_message_t *msg);
void display_game_status(server_message_t msg);
void display_attack_result(server_message_t msg);
void display_end_game_result(server_message_t msg);
void clear_screen();

#endif