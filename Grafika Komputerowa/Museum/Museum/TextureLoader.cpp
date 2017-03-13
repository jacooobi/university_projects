//
//  TextureLoader.cpp
//  Museum
//
//  Created by Tomasz Kasperek on 04.09.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#include "TextureLoader.hpp"

TextureLoader::TextureLoader(const std::vector<tinyobj::material_t> &materials) {
    for (auto it = materials.begin(); it != materials.end(); ++it) {
        std::string texPath = texturePath(*it);
        glm::vec3 texScale = textureScale(*it);
        Texture *tex = new Texture(texPath, texScale);
        textures.push_back(tex);
    }
}

TextureLoader::~TextureLoader() {
    for (auto it = textures.begin(); it != textures.end(); ++it) {
        delete (*it);
    }
}

std::string TextureLoader::texturePath(const tinyobj::material_t &material) {
    std::string materialKd = material.diffuse_texname;
    std::vector<std::string> tokens = utils::split(materialKd, ' ');
    
    for (auto it = tokens.begin(); it != tokens.end(); ++it) {
        std::string token = (*it);
        
        if (token.find(".png") != std::string::npos) {
            return token;
        }
    }
    
    return materialKd;
}

glm::vec3 TextureLoader::textureScale(const tinyobj::material_t &material) {
    std::string materialKd = material.diffuse_texname;
    auto tokens = utils::split(materialKd, ' ');
    
    if (tokens[0] == "-s") {
        float x = ::atof(tokens[1].c_str());
        float y = ::atof(tokens[2].c_str());
        float z = ::atof(tokens[3].c_str());
        return glm::vec3(x, y, z);
    }
    
    return glm::vec3(1);
}

Texture* TextureLoader::get(int index) {
    if (textures.size() <= index) {
        return nullptr;
    }
    
    return textures[index];
}
