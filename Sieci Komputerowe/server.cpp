#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <pthread.h>
#include <sstream>
#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <map>
#include <ctype.h>
#include <string>
#include <algorithm>

// cztery stany dla kazdego z klientow
typedef enum { Waiting, Connected, AllBatchesSent, Completed } connectionFlag;
pthread_mutex_t connectionStatusesMutex;
std::vector<connectionFlag> connectionStatuses;
std::string base_filename;

void *connection_handler(void *);
void *my_pooling_handler(void *);
void update_connection_statuses(int id, connectionFlag flag);
void log_status(int client_number);
std::vector<connectionFlag> get_connection_statuses();
std::string convert_from_int_to_string(int value);
int convert_from_string_to_int(std::string value);
std::string clear_input_file(std::string original_file);
void divide_into_multiple_files(int count, std::string original_file);
bool compare_fun(std::pair<int, std::string> a , std::pair<int, std::string> b);

// std::string construct_filename(std::string base_name, int client_number);

void file_output();
void file_input();


int main(int argc, char* argv[]) {

    if (argc < 3) {
        printf("Podaj nazwe pliku tekstowego oraz liczbe instancji!\n");
        return -1;
    }

    int socket_desc, client_sock, c, *new_sock, clients_count;
    struct sockaddr_in server, client;

    // stworz socket
    socket_desc = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_desc == -1) {
        printf("Could not create socket");
        return -1;
    }
    puts("Socket created");

    // ustaw opcje w strukturze
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(8889);

    // bindujemy socket
    if (bind(socket_desc, (struct sockaddr *)&server, sizeof(server)) < 0) {
        perror("Bind failed. Error");
        return -1;
    }
    puts("Bind done");

    // nasluchujemy
    listen(socket_desc, 5);

    puts("Waiting for incoming connections...");
    c = sizeof(struct sockaddr_in);

    // zapisujemy sobie liczbe klientow
    clients_count = strtol(argv[2], NULL, 10);

    std::cout << "Awaiting " << clients_count << " client connections" << std::endl;

    for(int i=0; i<clients_count; i++) {
        connectionFlag flag = Waiting;
        connectionStatuses.push_back(flag);
    }

    // oczyszczamy plik bazowy i dzielimy go na n rownych czesci
    base_filename = std::string(argv[1]);
    base_filename = clear_input_file(base_filename);
    divide_into_multiple_files(clients_count, base_filename);

    // tworzymy dodatkowy watek ktory bedzie monitorowal stan przesylania wszystkich danych
    pthread_t pooling_thread;
    pthread_create(&pooling_thread, NULL, my_pooling_handler, (void *) &clients_count);

    // odbieraj dane
    while (1) {
        if ((client_sock = accept(socket_desc, (struct sockaddr *)&client, (socklen_t*)&c) )) {
            pthread_t sniffer_thread;
            new_sock = (int *)malloc(1);
            *new_sock = client_sock;

            // tworzymy watek na obsluge pojedynczego klienta
            if (pthread_create(&sniffer_thread, NULL, connection_handler, (void *) new_sock) < 0) {
                perror("Could not create thread");
                return -1;
            }

            puts("Connection with client established");
        }
    }

}

void *connection_handler(void *socket_desc) {
    int read_size, client_number;
    int counter = 0;
    int sock = *(int*) socket_desc;
    char * message, client_message[7500];
    std::string word, client_response, tempStr = "";

    // pierwsze odczytanie danych -> klient przesyla swoj identyfikator
    while ((read_size = recv(sock, client_message, 2000, 0)) > 0) {
        client_response = client_message;

        client_number = convert_from_string_to_int(client_response);

        std::cout << "Klient numer " << client_number << " - podłączony\n";

        update_connection_statuses(client_number, Connected);
        log_status(client_number);

        break;
    }

    // mamy juz przygotowana wersje pliku, wybieramy go dla tego konkretnego klienta
    std::ifstream input_file;
    std::ostringstream filename;
    filename << base_filename << "_part_" << client_response;
    input_file.open(filename.str().c_str());


    while (std::getline(input_file, word)) {
        tempStr += (word + "\n");

        // odczytujemy kolejne linijki, ale wysylamy dopiero jak uzbieramy tysiac
        if (counter % 1000 == 0) {
            send(sock, tempStr.c_str(), tempStr.size(), 0);
            // printf("%d\n", tempStr.size());
            tempStr = "";
        }

        ++counter;
    }

    // przeslanie ostatniej paczki (< 1000)
    send(sock, tempStr.c_str(), tempStr.size(), 0);
    sleep(5);
    // safe word - is a sign to client that all the batches were transmitted
    send(sock, "inf116307", strlen("inf116307"), 0);

    // zmieniamy stan na batches sent -> teraz klient musi przeanalizowac swoja paczke
    update_connection_statuses(client_number, AllBatchesSent);
    log_status(client_number);

    // przygotowujemy sie do odczytu danych (przeanalizowanych)
    std::string resultText;
    std::ofstream output_result_file;
    std::ostringstream result_filename;
    result_filename << base_filename << "_results_" << client_response;
    output_result_file.open(result_filename.str().c_str());

    while( (read_size = recv(sock, client_message, 7500, 0)) > 0) {
        std::string text = client_message;

        if (text.compare(0, strlen("inf116307"), "inf116307") == 0) {
            printf("Received the final batch, going to analyze it now!\n");
            // zmieniamy stan na completed
            update_connection_statuses(client_number, Completed);
            log_status(client_number);

            break;
        }

        // zapis do pliku rezultatu?
        std::istringstream streamString(text);
        while (!streamString.eof()) {
            std::string word;
            getline(streamString, word);
            output_result_file << word << std::endl;
        }
    }


    sleep(10);

    free(socket_desc);

    return 0;
}

void *my_pooling_handler(void* count) {

    // ciagle odczytywanie co sekunde stanu klientow, jezeli wszystkie completed to mozemy juz brac sie za ostateczna analize;
    int clients = *(int*) count;
    std::vector<std::string> top_ten_words;
    while(1) {
        sleep(1);
        std::vector<connectionFlag> current_statuses = get_connection_statuses();
        bool finished = true;
        for(int i=0; i<clients; i++) {
            if (current_statuses.at(i) != Completed) {
                finished = false;
            }
        }

        if (finished) {
            std::cout << "File analysis has been completed\n";
            break;
        }

    }

    std::map<std::string, int> finalValues;
    std::string line;

    // odczytujemy dane po kolei z kazdego z klientow i laczymy w jedna calosc
    for (int i=0;i<clients;i++) {
        std::string resultText;

        std::ifstream input_result_file;
        std::ostringstream result_filename;

        result_filename << base_filename << "_results_" << i;
        input_result_file.open(result_filename.str().c_str());

        while (std::getline(input_result_file, line)) {

            std::string word, word_count;

            std::istringstream iss(line, std::istringstream::in);

            iss >> word;
            iss >> word_count;

            int i_word_count;

            std::istringstream ss(word_count);
            ss >> i_word_count;

            (finalValues.count(word) > 0) ? finalValues[word] += i_word_count : finalValues[word] = i_word_count;
        }

    }

    std::vector<std::pair<int, std::string> > sorted_words;

    for (std::map<std::string, int>::iterator i = finalValues.begin(); i != finalValues.end(); i++) {
        std::pair <int, std::string> temp_pair;
        temp_pair = std::make_pair(i->second, i->first);
        sorted_words.push_back(temp_pair);
    }

    std::sort(sorted_words.begin(), sorted_words.end(), compare_fun);

    for (std::vector<std::pair<int, std::string> >::iterator i = sorted_words.begin(); i != sorted_words.end(); i++) {
        if (i->second.length() > 3) {
            top_ten_words.push_back(i->second);
        }

        if (top_ten_words.size() > 10) break;
    }

    float total_length = 0.0;

    for (std::map<std::string, int>::iterator i = finalValues.begin(); i != finalValues.end(); i++) {
        total_length += i->first.length();
    }

    std::cout << "Przeanalizowano " << finalValues.size() << " unikalnych słów o średniej długości " << (float)(total_length/finalValues.size()) << std::endl;

    std::cout << "10 najpopularniejszych słow to (powyżej 3 liter) :\n";
    for (std::vector<std::string>::iterator i = top_ten_words.begin(); i != top_ten_words.end(); i++) {

        std::cout << *i << " " << finalValues.find(*i)->second << " wystapien" << std::endl;
    }

    std::ofstream output_file_final("FINAL.txt");

    // std::cout << finalValues.size() << std::endl;

    // zapis laczny do pliku.
    for (std::map<std::string, int>::iterator i = finalValues.begin(); i != finalValues.end(); i++) {
        output_file_final << i->first << " " << i->second << "\n";
    }

    exit(0);
}

void update_connection_statuses(int id, connectionFlag flag) {
    // update naszego vectora stanow z uzyciem binarnego semafora

    pthread_mutex_lock(&connectionStatusesMutex);

    connectionStatuses.at(id) = flag;

    pthread_mutex_unlock(&connectionStatusesMutex);
}

std::vector<connectionFlag> get_connection_statuses() {
    // zwraca aktualny vector stanow przy poprzednim zablokowaniu operacji na nim

    std::vector<connectionFlag> cStatuses;

    pthread_mutex_lock(&connectionStatusesMutex);
    cStatuses = connectionStatuses;
    pthread_mutex_unlock(&connectionStatusesMutex);

    return cStatuses;
}

void log_status(int client_number) {
    std::vector<connectionFlag> statuses = get_connection_statuses();
    std::cout << "Klient " << client_number << " aktualny status: " << statuses.at(client_number) << std::endl;
}

// std::string convert_from_int_to_string(int value) {

// }
int convert_from_string_to_int(std::string value) {
    int result;
    std::stringstream str(value);
    str >> result;
    return result;
}

std::string clear_input_file(std::string original_file) {
    std::ifstream input_file(original_file.c_str());
    std::string output_filename = original_file + "_filtered";

    std::ofstream output_file(output_filename.c_str());
    std::string line;

    while (std::getline(input_file, line)) {
        for (int i = 0, len = line.size(); i < len; i++) {
            if (!(iscntrl(line[i]) || isalpha(line[i]) || isspace(line[i]))) {
                line.erase(i--, 1);
                len = line.size();
            }
        }

        std::transform(line.begin(), line.end(), line.begin(), ::tolower);

        std::stringstream string_line(line);
        std::string segment;

        while(std::getline(string_line, segment, ' ')) {
            if (!(segment.find_first_not_of("\t ") == std::string::npos))
                output_file << segment << "\n";
        }
    }

    input_file.close();
    output_file.close();

    return output_filename;
}

void divide_into_multiple_files(int count, std::string original_file) {

    std::ifstream source_file(original_file.c_str());
    std::vector<std::ofstream> files;
    std::string base_name = original_file + "_part_";
    std::string word;

    std::ofstream myFiles[count];
    for(int i = 0; i < count; i++){
        std::ostringstream filename;
        filename << base_name << i;
        myFiles[i].open(filename.str().c_str());
    }

    int counter = 0;
    while (std::getline(source_file, word)) {
        myFiles[(counter % count)] << word << "\n";
        ++counter;
    }
}

bool compare_fun(std::pair<int, std::string> a , std::pair<int, std::string> b) {
    return a.first > b.first;
}
