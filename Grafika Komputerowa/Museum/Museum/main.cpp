#include <iostream>
#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <string>
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "glm/gtc/matrix_transform.hpp"
#include "constants.h"
#include "ShaderProgram.hpp"
#include "tinyobjloader.hpp"
#include "World.hpp"
#include <stdlib.h>
#include <stdio.h>

// Window
static const GLuint WIDTH = 800, HEIGHT = 800;

int main(int argc, const char * argv[]) {
    World* world = new World(WIDTH, HEIGHT);
    world->initOpenGL();
    world->run();
    delete world;
    return 0;
}
