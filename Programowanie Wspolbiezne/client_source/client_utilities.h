#ifndef CLIENT_UTILITIES
#define CLIENT_UTILITIES

#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <errno.h>
#include <unistd.h>

#define KEY_PATH "/tmp"
#define SERVER_MSGQ_ID 19

extern int memory_id;
extern int client_id;

void connect_to_server();
void set_client_id(int id);
void validate_presence_of_id(int argc);
int get_queue();
int get_client_id();

#endif