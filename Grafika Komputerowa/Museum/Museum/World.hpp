//
//  World.hpp
//  Museum
//
//  Created by Tomasz Kasperek on 18.07.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef World_hpp
#define World_hpp

#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string>
#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "glm/gtc/matrix_transform.hpp"
#include "constants.h"
#include "utils.hpp"
#include "ModelLoader.hpp"
#include "Model.hpp"
#include "Doorman.hpp"
#include "LightsCollection.hpp"
#include "Camera.hpp"

static GLfloat gDegreesRotated = 0.0f;

class World {
public:
    World(int w, int h);
    ~World();
    void run();
    void initOpenGL();
private:
    float angleX, angleY;
    
    GLuint vao;
    ModelLoader loader;
    GLFWwindow* window;
    Camera camera;
    
    Model* museum = nullptr;
    std::vector<Doorman*> people;
    
    glm::vec3 viewPosition;
    glm::mat4 perspective;
    glm::mat4 view;
    glm::mat4 model;
    
    void draw(float delta);
    void prepareScene();
    void updateCamera(float secondsElapsed);
    void addDoorman(Doorman &model, glm::vec3 start, glm::vec3 end, float rotation);
    static void scrollCallback(GLFWwindow* window, double deltaX, double deltaY);
};

#endif /* World_hpp */
