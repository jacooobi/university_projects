#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <cstdlib>
#include <iostream>
#include <map>
#include <vector>
#include <sstream>
#include <fstream>
#include <string>
#include <algorithm>

bool compare_fun(std::pair<int, std::string> a , std::pair<int, std::string> b) {
    return a.first > b.first;
}

int main(int argc, char* argv[]) {
    int counter = 1;
    int resultCounter = 1;
    int sock;
    char client_number;
    struct sockaddr_in server;
    char message[1000], serv_message[7500];
    std::string safe_word = "inf116307";
    std::string finalString = "";
    std::vector<std::string> top_ten_words;

    if (argc < 2) {
        printf("Podaj identyfikator klienta!\n");
        return 1;
    }

    // stworz socket
    sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock == -1) {
        printf("Could not create socket");
        return 1;
    }
    puts("Socket created");

    server.sin_addr.s_addr = inet_addr("127.0.0.1");
    server.sin_family = AF_INET;
    server.sin_port = htons(8889);

    // podlacz do zewnetrznego serwera
    if (connect(sock, (struct sockaddr *)&server, sizeof(server)) < 0) {
        perror("Connect failed. Error");
        return 1;
    }

    if (send(sock, argv[1], strlen(argv[1]), 0) < 0) {
        perror("Przeslanie identyfikatora zakonczone niepowodzeniem!\n");
        return 1;
    }

    puts("Connected to server");

    // petla odczytujaca paczki z danymi do analizy
    while(1) {
        int data;

        if ((data = recv(sock, serv_message, 7500, 0)) < 0) {
            puts("Recv failed");
            break;
        } else if (data > 0) {
            printf("Paczka numer %d\n", counter);
            std::string text = serv_message;
            counter++;

            if (text.compare(0, safe_word.length(), safe_word) != 0) {
                finalString += text;
            } else {
                printf("Received the final batch, going to analyze it now!\n");
                break;
            }
        }

        memset(&serv_message[0], 0, sizeof(serv_message));
    }
    printf("Odebrano %d paczek\n", counter);

    std::ofstream my_output_file;
    std::ostringstream my_filename;
    my_filename << "text_received_part_" << argv[1];

    my_output_file.open(my_filename.str().c_str());

    std::map<std::string, int> values;

    std::istringstream streamString(finalString);

    while (!streamString.eof()) {
        //dla kazdego slowa sprawdzamy czy juz jest w mapie i aktualizujemy jego licznik
        std::string word;
        getline(streamString, word);
        std::cout << word << std::endl;
        my_output_file << word << std::endl;

        word.erase(word.find_last_not_of(" \n\r\t")+1);
        if (word.find_first_not_of("\t\n ") == std::string::npos) continue;
        (values.count(word) > 0) ? values[word] += 1 : values[word] = 1;

    }

    std::string resultString = "";

    for (std::map<std::string, int>::iterator i = values.begin(); i != values.end(); i++) {

        // z mapy budujemy stringa (slowo liczba)
        // co tysiac wysylamy taki pakiet
        std::stringstream ss;
        ss << i->second;
        std::string str = ss.str();
        resultString.append(i->first + " " + str + "\n");
        if (( std::distance(values.begin(), i) % 1000) == 0) {
            send(sock, resultString.c_str(), resultString.size(), 0);
            resultString = "";
        }
    }


    // na koniec safe word i mozemy juz zasypiac
    if (send(sock, "inf116307", strlen("inf116307"), 0) < 0) {
        perror("Przeslanie kodu koncowego zakonczone niepowodzeniem\n");
        return 1;
    }

    std::vector<std::pair<int, std::string> > sorted_words;

    for (std::map<std::string, int>::iterator i = values.begin(); i != values.end(); i++) {
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

    for (std::map<std::string, int>::iterator i = values.begin(); i != values.end(); i++) {
        total_length += i->first.length();
    }

    std::cout << "Przeanalizowano " << values.size() << " unikalnych słów o średniej długości " << (float)(total_length/values.size()) << std::endl;

    std::cout << "10 najpopularniejszych słow to (powyżej 3 liter) :\n";
    for (std::vector<std::string>::iterator i = top_ten_words.begin(); i != top_ten_words.end(); i++) {

        std::cout << *i << " " << values.find(*i)->second << " wystapien" << std::endl;
    }


    for (std::map<std::string, int>::iterator i = values.begin(); i != values.end(); i++) {
        // z mapy budujemy stringa (slowo liczba)
        // co tysiac wysylamy taki pakiet
        std::stringstream ss;
        ss << i->second;
        std::string str = ss.str();
        resultString.append(i->first + " " + str + "\n");
        if (( std::distance(values.begin(), i) % 1000) == 0) {
            send(sock, resultString.c_str(), resultString.size(), 0);
            resultString = "";
        }
    }


    sleep(30);
    close(sock);
    return 0;
}
