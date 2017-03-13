//
//  Model.cpp
//  Museum
//
//  Created by Tomasz Kasperek on 26.08.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#include "Model.hpp"
#include "World.hpp"

Model::Model(ShaderProgram *sp, LightsCollection &lights) :
shaderProgram(sp),
lights(lights) {}

Model::Model(ShaderProgram *sp) : shaderProgram(sp) {}

Model::~Model() {
    if (shaderProgram != nullptr) {
        delete shaderProgram;
    }
    
    for (auto it = meshes.begin(); it != meshes.end(); ++it) {
        delete (*it);
    }
}

void Model::addMesh(Mesh *mesh) {
    if (mesh != nullptr) {
        meshes.push_back(mesh);
    }
}

void Model::setLights(LightsCollection &lights) {
    this->lights = lights;
}

void Model::setup() {
    for (auto it = meshes.begin(); it != meshes.end(); ++it) {
        (*it)->setup(shaderProgram);
    }
}

void Model::draw(Camera &camera, float rotation, glm::vec3 pos) {
    glm::mat4 modelMatrix = glm::translate(glm::mat4(1.0f), pos);
    modelMatrix = glm::rotate(modelMatrix, glm::radians(gDegreesRotated), glm::vec3(0, 1, 0));
    modelMatrix = glm::rotate(modelMatrix, glm::radians(rotation), glm::vec3(0, 1, 0));
    
    glm::mat4 cameraMatrix = camera.matrix();
    glm::vec3 cameraPosition = camera.position();
    
    shaderProgram->use();
    shaderProgram->setUniform((char *)"camera", 1, cameraMatrix);
    shaderProgram->setUniform((char *)"model", 1, modelMatrix);
    shaderProgram->setUniform((char *)"viewPos", 1, cameraPosition);
    
    lights.setAllUniforms(shaderProgram);
    
    for (auto it = meshes.begin(); it != meshes.end(); ++it) {
        (*it)->draw(shaderProgram);
    }
    
    glBindTexture(GL_TEXTURE_2D, 0);
}

void Model::draw(Camera &camera) {
    glm::mat4 modelMatrix = glm::rotate(glm::mat4(), glm::radians(gDegreesRotated), glm::vec3(0,1,0));
    glm::mat4 cameraMatrix = camera.matrix();
    glm::vec3 cameraPosition = camera.position();
    
    shaderProgram->use();
    shaderProgram->setUniform((char *)"camera", 1, cameraMatrix);
    shaderProgram->setUniform((char *)"model", 1, modelMatrix);
    shaderProgram->setUniform((char *)"viewPos", 1, cameraPosition);
    
    lights.setAllUniforms(shaderProgram);
    
    for (auto it = meshes.begin(); it != meshes.end(); ++it) {
        (*it)->draw(shaderProgram);
    }
    
    glBindTexture(GL_TEXTURE_2D, 0);
}
