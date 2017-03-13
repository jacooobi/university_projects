//
//  Texture.hpp
//  Museum
//
//  Created by Tomasz Kasperek on 30.08.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef Texture_hpp
#define Texture_hpp

#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <stdio.h>
#include <iostream>
#include <string>
#include <vector>
#include "ShaderProgram.hpp"
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "lodepng.h"
#include "tinyobjloader.hpp"

class Texture {
public:
    Texture(std::string filename);
    Texture(std::string filename, glm::vec3 scale);
    virtual ~Texture();
    void setUniform(ShaderProgram *sp);
private:
    std::vector<unsigned char> image;
    glm::vec3 scale;
    unsigned int width, height;
    GLuint texture;
};

#endif /* Texture_hpp */
