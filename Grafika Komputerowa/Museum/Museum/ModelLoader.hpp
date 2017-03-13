//
//  ModelLoader.hpp
//  Museum
//
//  Created by Tomasz Kasperek on 16.07.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef ModelLoader_hpp
#define ModelLoader_hpp

#include <string>
#include <iostream>
#include "TextureLoader.hpp"
#include "tinyobjloader.hpp"
#include "Mesh.hpp"
#include "Model.hpp"
#include "Doorman.hpp"
#include "utils.hpp"
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"

class ModelLoader {
private:
    TextureLoader *texLoader;
    Mesh* createMesh(tinyobj::mesh_t mesh, tinyobj::material_t material, Texture* tex);
    void debugPrint(std::vector<tinyobj::shape_t> shapes, std::vector<tinyobj::material_t> materials);
public:
    ModelLoader();
    virtual ~ModelLoader();
    
    Model* load(std::string filename,
                std::string vertexShaderPath,
                std::string fragmentShaderPath);
    
    Doorman* loadDoorman(std::string filename,
                         std::string vertexShaderPath,
                         std::string fragmentShaderPath);
};

#endif /* ModelLoader_hpp */
