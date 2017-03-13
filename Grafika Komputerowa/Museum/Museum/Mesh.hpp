//
//  Mesh.hpp
//  Museum
//
//  Created by Tomasz Kasperek on 17.07.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef Mesh_hpp
#define Mesh_hpp

#include <stdio.h>
#include <vector>
#include <iostream>
#include <assert.h>
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "glm/gtc/matrix_transform.hpp"
#include "ShaderProgram.hpp"
#include "Material.hpp"

class Mesh {
public:
    Mesh(std::vector<float> vertices,
         std::vector<float> normals,
         std::vector<float> texcoords,
         std::vector<unsigned int> indicies);
    Mesh(std::vector<float> vertices,
         std::vector<float> normals,
         std::vector<float> texcoords,
         std::vector<unsigned int> indicies,
         Material *mat);
    virtual ~Mesh();
    void draw(ShaderProgram *shaderProgram);
    void setMaterial(Material &material);
    void setup(ShaderProgram *shaderProgram);
private:
    Material *material;
    unsigned int vertexCount;
    unsigned int indicesCount;
    std::vector<float> vertices;
    std::vector<float> normals;
    std::vector<float> texcoords;
    std::vector<unsigned int> indices;
    GLuint vertexBuffer, normalBuffer, elementBuffer, texcoordsBuffer;
    
    template<typename T> GLuint createBuffer(int type, std::vector<T> array, unsigned long size);
    void createBuffers();
};

#endif /* Mesh_hpp */
