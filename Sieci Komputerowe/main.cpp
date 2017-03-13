#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <string>
#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <string>
#include <algorithm>
#include <sstream>


// TODO :
// usun przeklenstwa
// usun duplikacje
// przerzuc wszystkie wspolne funkcje do jednego modulu
// wiecej logow
// porzadek z bibliotekami (includami)
// jakis plik z funkcjami logujacymi
// jakis plik z funkcjami formatujacymi
// plik z funkcjami do hmmm moze tworzenie tych plikow i ogolnie operacje I/O
// fajna prezentacji wynikow

void clear_input_file() {

    std::ifstream input_file("ulysses_full.txt");
    std::ofstream output_file("ulysses_full_filtered.txt");
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
}

void divide_into_multiple_files(int count) {

    std::ifstream source_file("ulysses_full_filtered.txt");
    std::vector<std::ofstream> files;
    std::string base_name = "ulysses_full_filtered_part_";
    std::string word;

    std::ofstream myFiles[count];
    for(int i = 0; i < count; i++){
        std::ostringstream filename;
        filename << "ulysses_full_filtered_part_" << i;
        myFiles[i].open(filename.str().c_str());
    }

    int counter = 0;
    while (std::getline(source_file, word)) {
        myFiles[(counter % count)] << word << "\n";
        ++counter;
    }
}


int main(int argc, char *argv[]) {
    int nodes_count = 5;
    clear_input_file();
    divide_into_multiple_files(nodes_count);
    std::ofstream output_file("ulysses_summary.txt");


    std::map<std::string, int> values;
    std::string word;

    std::ifstream input_file("ulysses_full_filtered.txt");

    if (!input_file) {
        printf("Error!\n");
        exit(-1);
    }

    while (std::getline(input_file, word)) {
        word.erase(word.find_last_not_of(" \n\r\t")+1);

        if (word.find_first_not_of("\t\n ") == std::string::npos) continue;

        (values.count(word) > 0) ? values[word] += 1 : values[word] = 1;
    }

    for (std::map<std::string, int>::iterator i = values.begin(); i != values.end(); i++) {
        output_file << i->first << " " << i->second << "\n";
    }

    return 0;
}
