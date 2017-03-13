#ifndef DISPATCHER
#define DISPATCHER

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include "../shared_source/game_protocol.h"
#include "client_utilities.h"

char* new_command();
server_message_t* construct_message(char* command);
int* split_values();
void display_prompt();

#endif