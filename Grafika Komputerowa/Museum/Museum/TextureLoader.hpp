//
//  TextureLoader.hpp
//  Museum
//
//  Created by Tomasz Kasperek on 04.09.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef TextureLoader_hpp
#define TextureLoader_hpp

#include <iostream>
#include <vector>
#include <string>
#include "Texture.hpp"
#include "tinyobjloader.hpp"
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "utils.hpp"

class TextureLoader {
public:
    TextureLoader(const std::vector<tinyobj::material_t> &materials);
    virtual ~TextureLoader();
    Texture* get(int index);
private:
    std::vector<Texture *> textures;
    glm::vec3 textureScale(const tinyobj::material_t &material);
    std::string texturePath(const tinyobj::material_t &material);
};

#endif /* TextureLoader_hpp */
