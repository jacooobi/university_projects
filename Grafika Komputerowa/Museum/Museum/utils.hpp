//
//  utils.h
//  Museum
//
//  Created by Tomasz Kasperek on 18.07.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef utils_h
#define utils_h

#include <string>
#include <sstream>
#include <vector>
#include <stdio.h>
#include <GLFW/glfw3.h>
namespace utils {
    void errorCallback(int error, const char* description);
    std::vector<std::string> split(const std::string &s, char delim);
    void split(const std::string &s, char delim, std::vector<std::string> &elems);
}
#endif /* utils_h */
