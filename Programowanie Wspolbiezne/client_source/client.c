#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/stat.h>
#include <stdlib.h>
#include "../shared_source/game_protocol.h"
#include <signal.h>
#include <string.h>
#include "dispatcher.h"
#include "receiver.h"
#include "client_utilities.h"


int main(int argc, char* argv[]) {
	
	validate_presence_of_id(argc);
	set_client_id(*argv[1] - 48);
	connect_to_server();

    int pid = fork();
	if (pid == 0) {
		while(1) {
			server_message_t msg;
	        int i = msgrcv(get_queue(), &msg, sizeof(game_message_t), get_client_id()+1, 0);
	        if (i>0) {
		        switch(msg.mdata.action_type) {
		        	case GAME_STATUS: {
				        display_game_status(msg);
		     			break;
		        	}
		        	case ATTACK_RESULT: {
		        		display_attack_result(msg);
		        		break;
		        	}
		        	case GAME_RESULT: {
		        		display_end_game_result(msg);
		        		break;
		        	}
		        	default: break;
		        }
	        }
	    }	    
	} else {
		char c;
  		while ((c = getchar())) {
		    if (c == '\n') {
				kill(pid, SIGSTOP);

				display_prompt();
				server_message_t *msg = construct_message(new_command());

				if (msgsnd(get_queue(), msg, sizeof(game_message_t), 0) == -1)
					perror("Błąd przy wysyłaniu polecenia");

				sleep(2);
				kill(pid, SIGCONT);
			}
	 	}
	}

    return 0;
}