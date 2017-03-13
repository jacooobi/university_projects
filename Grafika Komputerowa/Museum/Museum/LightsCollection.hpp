#ifndef LightsCollection_hpp
#define LightsCollection_hpp

#include <stdio.h>
#include <vector>
#include <string>
#include <sstream>
#include <iostream>
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "Light.hpp"
#include "ShaderProgram.hpp"

class LightsCollection {
private:
    Light dirLight;
    std::vector<Light> pointLights;
    
    char* lightAttributeName(std::string name, const unsigned int index);
    char* lightAttributeName(std::string name);
    
    void setUniform(ShaderProgram *sp, const unsigned int index);
    void setUniform(ShaderProgram *sp);
public:
    LightsCollection();
    virtual ~LightsCollection();

    Light getLight(const unsigned int index);
    unsigned int getNumLights();
    void addPointLight(glm::vec3, glm::vec3);
    void addDirectionalLight(glm::vec3, glm::vec3);
    
    void setAllUniforms(ShaderProgram *sp);
};

#endif
