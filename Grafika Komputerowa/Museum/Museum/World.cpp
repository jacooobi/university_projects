//
//  World.cpp
//  Museum
//
//  Created by Tomasz Kasperek on 18.07.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#include "World.hpp"

// It needs to be global because keyCallback can't mutate object members
static double gScrollY = 0.0;

void errorCallback(int error, const char* description) {
    fprintf(stderr, "%s", description);
}

World::World(int w, int h) {
    if (!glfwInit()) {
        fprintf(stderr, "Couldn't initialize GLFW\n");
        exit(EXIT_FAILURE);
    }
    
    glfwSetErrorCallback(errorCallback);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
    
    
    window = glfwCreateWindow(w, h, "Museum Tour", nullptr, nullptr);
    if (!window) {
        fprintf(stderr, "Couldn't create window\n");
        glfwTerminate();
        exit(EXIT_FAILURE);
    }
    
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    glfwSetCursorPos(window, 0, 0);
    glfwSetScrollCallback(window, scrollCallback);
    glfwMakeContextCurrent(window);
    glfwSwapInterval(1);
    
    glewExperimental = true;
    if (glewInit() != GLEW_OK) {
        fprintf(stderr, "Couldn't initialize GLEW library\n");
        exit(EXIT_FAILURE);
    }
}

World::~World() {
    if (museum != nullptr) delete museum;
    
    for (auto it = people.begin(); it != people.end(); ++it) {
        delete (*it);
    }
    
    glfwDestroyWindow(window);
    glfwTerminate();
}

void World::initOpenGL() {
    glClearColor(0, 0, 0, 1);
    glEnable(GL_DEPTH_TEST);
    glDepthFunc(GL_LESS);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    prepareScene();
}

void World::prepareScene() {
    // Create lights
    LightsCollection lights = LightsCollection();

    lights.addDirectionalLight(glm::vec3(0, 20, 0), glm::vec3(.5f));
    lights.addPointLight(glm::vec3(-4.57, 1.0, 11.57), glm::vec3(0.3f, 0.0f, 0.0f));
    lights.addPointLight(glm::vec3(-12.77, 1.11, -4.441), glm::vec3(0.3f, 0.0f, 0.0f));
    lights.addPointLight(glm::vec3(5.064, 1.069, -11.51), glm::vec3(0.3f, 0.0f, 0.0f));
    lights.addPointLight(glm::vec3(11.20, 0.833, 4.874), glm::vec3(0.3f, 0.0f, 0.0f));
    
    museum = loader.load("models/museum_next_textures_fixed.obj",
                         "shaders/museum.vert.glsl",
                         "shaders/museum.frag.glsl");
    museum->setLights(lights);
    
    Doorman *doorman = loader.loadDoorman("models/doorman.obj",
                                          "shaders/doorman.vert.glsl",
                                          "shaders/doorman.frag.glsl");
    doorman->setLights(lights);
    
    addDoorman(*doorman,
               glm::vec3(8.34, -1.51, -4.076),
               glm::vec3(8.45, -1.51, 4.62),
               -90.0f);
    
    addDoorman(*doorman,
               glm::vec3(11.67, -1.51, 10.73),
               glm::vec3(-5.38, -1.51, 11.2),
               0.0f);
    
    addDoorman(*doorman,
               glm::vec3(4.19, -1.51, -8.58),
               glm::vec3(-4.59, -1.51, -8.56),
               0.0f);
    
    addDoorman(*doorman,
               glm::vec3(-8.72, -1.51, -3.86),
               glm::vec3(-8.6, -1.51, 4.60),
               90.0f);
    
    camera.setPosition(glm::vec3(10,0,4));
    camera.setViewportAspectRatio(800/800);
}

void World::addDoorman(Doorman &model, glm::vec3 start, glm::vec3 end, float rotation) {
    Doorman *d = new Doorman(model);
    d->setRotation(rotation);
    d->setPositions(start, end);
    people.push_back(d);
}

void World::run() {
    double lastTime = glfwGetTime();
    
    while (!glfwWindowShouldClose(window)) {
        
        glfwPollEvents();
        double currentTime = glfwGetTime();
        float delta = (float)(currentTime - lastTime);
        updateCamera(delta);
        lastTime = currentTime;
        
        if (glfwGetKey(window, 'P')) {
            glm::vec3 currentPos = camera.position();
            std::cout << "Pos: " << currentPos.x << ", " << currentPos.y << ", " << currentPos.z << std::endl;
        }
        
        draw(delta);
    }
}

void World::draw(float delta) {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    
    museum->draw(camera);
    for (auto it = people.begin(); it != people.end(); ++it) {
        (*it)->draw(camera, delta);
    }
    
    GLenum error = glGetError();
    if(error != GL_NO_ERROR)
    std::cerr << "OpenGL Error2 " << error << std::endl;
    
    glfwSwapBuffers(window);
}

void World::updateCamera(float secondsElapsed) {
    const GLfloat degreesPerSecond = 180.0f;
    gDegreesRotated += secondsElapsed * degreesPerSecond;
    while(gDegreesRotated > 360.0f) gDegreesRotated -= 360.0f;
    
    //move position of camera based on WASD keys, and XZ keys for up and down
    const float moveSpeed = 4.0; //units per second
    if(glfwGetKey(window, 'S')){
        camera.offsetPosition(secondsElapsed * moveSpeed * -camera.forward());
    } else if(glfwGetKey(window, 'W')){
        camera.offsetPosition(secondsElapsed * moveSpeed * camera.forward());
    }
    if(glfwGetKey(window, 'A')){
        camera.offsetPosition(secondsElapsed * moveSpeed * -camera.right());
    } else if(glfwGetKey(window, 'D')){
        camera.offsetPosition(secondsElapsed * moveSpeed * camera.right());
    }
    if(glfwGetKey(window, 'Z')){
        camera.offsetPosition(secondsElapsed * moveSpeed * -glm::vec3(0,1,0));
    } else if(glfwGetKey(window, 'X')){
        camera.offsetPosition(secondsElapsed * moveSpeed * glm::vec3(0,1,0));
    }
    
    //rotate camera based on mouse movement
    const float mouseSensitivity = 0.1f;
    double mouseX, mouseY;
    glfwGetCursorPos(window, &mouseX, &mouseY);
    camera.offsetOrientation(mouseSensitivity * (float)mouseY, mouseSensitivity * (float)mouseX);
    glfwSetCursorPos(window, 0, 0); //reset the mouse, so it doesn't go out of the window
    
    //increase or decrease field of view based on mouse wheel
    const float zoomSensitivity = -0.2f;
    float fieldOfView = camera.fieldOfView() + zoomSensitivity * (float)gScrollY;
    if(fieldOfView < 5.0f) fieldOfView = 5.0f;
    if(fieldOfView > 130.0f) fieldOfView = 130.0f;
    camera.setFieldOfView(fieldOfView);
    gScrollY = 0;
}

void World::scrollCallback(GLFWwindow* window, double deltaX, double deltaY) {
    gScrollY += deltaY;
}
