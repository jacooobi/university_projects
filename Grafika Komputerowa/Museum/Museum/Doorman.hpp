//
//  Doorman.hpp
//  Museum
//
//  Created by Tomasz Kasperek on 29.09.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef Doorman_hpp
#define Doorman_hpp

#include <stdio.h>
#include "Model.hpp"
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"

class Doorman : public Model {
public:
    Doorman(const Doorman& copy);
    Doorman(Model* model);
    Doorman(ShaderProgram *sp, LightsCollection &lights);
    Doorman(ShaderProgram *sp);
    virtual ~Doorman();
    void setPositions(glm::vec3 begin, glm::vec3 end);
    void setRotation(float angle);
    void draw(Camera &camera, float delta);
private:
    float timer;
    float directionModifier;
    float animationProgress;
    float animationSpeed;
    float rotation;
    
    glm::vec3 interpolate();
    glm::vec3 begin;
    glm::vec3 end;
};

#endif /* Doorman_hpp */
