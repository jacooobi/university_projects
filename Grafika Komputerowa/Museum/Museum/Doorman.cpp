//
//  Doorman.cpp
//  Museum
//
//  Created by Tomasz Kasperek on 29.09.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#include "Doorman.hpp"

Doorman::Doorman(const Doorman& copy) : Model(copy.shaderProgram) {
    Model::meshes = copy.meshes;
    Model::lights = copy.lights;
    
    timer = copy.timer;
    directionModifier = copy.directionModifier;
    animationProgress = copy.animationProgress;
    animationSpeed = copy.animationSpeed;
    rotation = copy.rotation;
}

Doorman::Doorman(ShaderProgram *sp) : Model(sp) {
    timer = 0;
    directionModifier = 1.0f;
    animationProgress = 0.0f;
    animationSpeed = .15f;
    rotation = 0.0f;
}

Doorman::Doorman(ShaderProgram *sp, LightsCollection &lights) : Model(sp, lights) {
    timer = 0;
    directionModifier = 1.0f;
    animationProgress = 0.0f;
    animationSpeed = .15f;
    rotation = 0.0f;
}

Doorman::~Doorman() {}

void Doorman::setPositions(glm::vec3 begin, glm::vec3 end) {
    this->begin = begin;
    this->end = end;
}

void Doorman::setRotation(float angle) {
    rotation = angle;
}

glm::vec3 Doorman::interpolate() {
    return begin + ((end - begin) * animationProgress);
}

void Doorman::draw(Camera &camera, float delta) {
    if (animationProgress > 1) {
        animationProgress = 1.0f;
        directionModifier = -1.0f;
    }
    
    if (animationProgress < 0) {
        animationProgress = 0.0f;
        directionModifier = 1.0f;
    }
    
    animationProgress += delta * animationSpeed * directionModifier;
    Model::draw(camera, rotation, interpolate());
}
