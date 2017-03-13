//
//  Model.hpp
//  Museum
//
//  Created by Tomasz Kasperek on 26.08.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef Model_hpp
#define Model_hpp

#include <stdio.h>
#include <vector>
#include "Mesh.hpp"
#include "Material.hpp"
#include "ShaderProgram.hpp"
#include "LightsCollection.hpp"
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "Camera.hpp"

class Model {
protected:
    void draw(Camera &camera, float rotation, glm::vec3 position);
    ShaderProgram *shaderProgram;
    std::vector<Mesh *> meshes;
    LightsCollection lights;
public:
    Model(ShaderProgram *sp, LightsCollection &lights);
    Model(ShaderProgram *sp);
    virtual ~Model();
    void setup();
    void setLights(LightsCollection &lights);
    void addMesh(Mesh *mesh);
    void draw(Camera &camera);
};

#endif /* Model_hpp */
