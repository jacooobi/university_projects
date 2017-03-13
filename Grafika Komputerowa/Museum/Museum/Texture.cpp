//
//  Texture.cpp
//  Museum
//
//  Created by Tomasz Kasperek on 30.08.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#include "Texture.hpp"
#include <iostream>

Texture::Texture(std::string filename, glm::vec3 scale) : scale(scale) {
    std::cout << "Loading " << filename << " file..." << std::endl;
    unsigned int error = lodepng::decode(image, width, height, filename);
    
    if (error != 0) {
        std::cerr << "Error loading " << filename << " texture file" << std::endl;
        exit(1);
    }
    
    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (unsigned char*)image.data());
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glBindTexture(GL_TEXTURE_2D, 0);
}

Texture::Texture(std::string filename) : Texture(filename, glm::vec3(1)) {}

Texture::~Texture() {
    glDeleteTextures(1, &texture);
}

void Texture::setUniform(ShaderProgram *sp) {
    sp->setUniform((char*)"material.texScale", 1, scale);
    sp->setUniformInt((char*)"material.tex", 0);
    
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, texture);
}
