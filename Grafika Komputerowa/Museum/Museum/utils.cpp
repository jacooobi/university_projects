//
//  utils.c
//  Museum
//
//  Created by Tomasz Kasperek on 18.07.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#include "utils.hpp"
namespace utils {
    void errorCallback(int error, const char* description) {
        fputs(description, stderr);
    }
    
    void split(const std::string &s, char delim, std::vector<std::string> &elems) {
        std::stringstream ss(s);
        std::string item;
        while (getline(ss, item, delim)) {
            elems.push_back(item);
        }
    }
    
    
    std::vector<std::string> split(const std::string &s, char delim) {
        std::vector<std::string> elems;
        split(s, delim, elems);
        return elems;
    }
}