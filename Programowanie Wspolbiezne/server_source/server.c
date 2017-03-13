#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <errno.h>
#include <stdio.h>
#include <string.h>
#include "../shared_source/game_protocol.h"
#include "server_utilities.h"
#include "players_connection.h"
#include "training.h"
#include "combat.h"


int main(int argc, char* argv[]) {
    initialize_queue();
    initialize_players();

    while(awaiting_players()) {
        server_message_t msg;
        int i = msgrcv(get_queue(), &msg, sizeof(game_message_t), 0, 0);
        if (i>0) add_player(msg.mdata.data.client_id);
    }

    initialize_semaphore();
    initialize_memory();
    initialize_game_data();

    if (fork() == 0) {
        while(1) {
            broadcast_statuses();
            sleep(1);
            update_resources();
        }        
    } else {
        while(1){
            server_message_t msg;
            if (msgrcv(get_queue(), &msg, sizeof(game_message_t), 1, 0) > 0) {
                switch(msg.mdata.action_type) {
                    case 5: {
                        int attacker_id = msg.mdata.data.attack.attacker_id;
                        army_t army = msg.mdata.data.attack.army;

                        printf("Otrzymano zlecenie ataku przez gracza %d \n", attacker_id);
                        if (validate_attack_request(attacker_id-1, army) == -1) break;

                        perform_attack(attacker_id-1, army);
                        break;
                    }
                    case 4: {
                        int client_id = msg.mdata.data.training.player_id;
                        int unit_type = msg.mdata.data.training.type;
                        int unit_count = msg.mdata.data.training.count;

                        printf("Otrzymano zlecenie treningu przez gracza %d rodzaj: %d liczba: %d \n", client_id, unit_type, unit_count);
                        if (validate_training_request(unit_type, unit_count, client_id-1) == -1) break;
                        
                        produce_units(unit_type, unit_count, client_id-1); 
                        break; 
                    }
                    default: break;
                }
            }
        }
    }

    return 0;   
}