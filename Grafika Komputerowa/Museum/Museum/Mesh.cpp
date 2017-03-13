//
//  Mesh.cpp
//  Museum
//
//  Created by Tomasz Kasperek on 17.07.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#include "Mesh.hpp"

static GLuint vao;

Mesh::Mesh(std::vector<float> vertices,
           std::vector<float> normals,
           std::vector<float> texcoords,
           std::vector<unsigned int> indices,
           Material *mat) : vertices(vertices), normals(normals), texcoords(texcoords), indices(indices), material(mat) {
    this->vertexCount = (unsigned int)vertices.size();
    this->indicesCount = (unsigned int)indices.size();
    createBuffers();
}

Mesh::Mesh(std::vector<float> vertices,
           std::vector<float> normals,
           std::vector<float> texcoords,
           std::vector<unsigned int> indices) : Mesh(vertices, normals, texcoords, indices, nullptr) {}


Mesh::~Mesh() {
    glDeleteBuffers(1, &vertexBuffer);
    glDeleteBuffers(1, &elementBuffer);
    glDeleteBuffers(1, &normalBuffer);
    
    if (material != nullptr) {
        delete material;
    }
}

void Mesh::setup(ShaderProgram *shaderProgram) {
    glBindVertexArray(vao);
    
    GLuint vertexLocation = shaderProgram->getAttribLocation((char *)"vertex");
    glEnableVertexAttribArray(vertexLocation);
    glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
    glVertexAttribPointer(vertexLocation, 3, GL_FLOAT, GL_FALSE, 0, NULL);
    
    GLuint normalLocation = shaderProgram->getAttribLocation((char *)"normal");
    glEnableVertexAttribArray(normalLocation);
    glBindBuffer(GL_ARRAY_BUFFER, normalBuffer);
    glVertexAttribPointer(normalLocation, 3, GL_FLOAT, GL_FALSE, 0, NULL);
    
    GLuint texcoordLocation = shaderProgram->getAttribLocation((char *)"texCoord");
    glEnableVertexAttribArray(texcoordLocation);
    glBindBuffer(GL_ARRAY_BUFFER, texcoordsBuffer);
    glVertexAttribPointer(texcoordLocation, 2, GL_FLOAT, GL_FALSE, 0, NULL);
    
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBuffer);
    glBindVertexArray(0);
}

void Mesh::draw(ShaderProgram *shaderProgram) {
    this->setup(shaderProgram);
    
    if (material) {
        material->setUniform(shaderProgram);
    }
    
    glBindVertexArray(vao);
    glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, NULL);
    glBindVertexArray(0);
}

template<typename T> GLuint Mesh::createBuffer(int type, std::vector<T> array, unsigned long size) {
    GLuint handle;
    glGenBuffers(1, &handle);
    glBindBuffer(type, handle);
    glBufferData(type, size * sizeof(T), &array[0], GL_STATIC_DRAW);
    return handle;
}

void Mesh::createBuffers() {
    glGenVertexArrays(1, &vao);
    vertexBuffer = createBuffer<float>(GL_ARRAY_BUFFER, vertices, vertices.size());
    normalBuffer = createBuffer<float>(GL_ARRAY_BUFFER, normals, normals.size());
    texcoordsBuffer = createBuffer<float>(GL_ARRAY_BUFFER, texcoords, texcoords.size());
    elementBuffer = createBuffer<unsigned int>(GL_ELEMENT_ARRAY_BUFFER, indices, indices.size());
}

