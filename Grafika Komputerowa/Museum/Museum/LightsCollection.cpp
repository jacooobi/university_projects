#include "LightsCollection.hpp"

LightsCollection::LightsCollection() {}

LightsCollection::~LightsCollection() {}

Light LightsCollection::getLight(const unsigned int index) {
    return pointLights[index];
}

void LightsCollection::addPointLight(glm::vec3 position,
                                     glm::vec3 color) {
    
    glm::vec3 ambient = color;
    glm::vec3 diffuse = glm::vec3(0.5);
    glm::vec3 specular = glm::vec3(0.5);
    
    Light _light = {
        .type = ELightType::POINT,
        .position = glm::vec4(position, 1.0),
        
        .ambient = ambient,
        .specular = specular,
        .diffuse = diffuse,
        
        .constant = 0.3f,
        .linear = 0.5f,
        .quadratic = 0.2f,
    };
    
    pointLights.push_back(_light);
}

void LightsCollection::addDirectionalLight(glm::vec3 position,
                                           glm::vec3 color) {
    dirLight.type = ELightType::DIRECTIONAL;
    dirLight.position = glm::vec4(position, 1.0);

    dirLight.ambient = color;
    dirLight.specular = glm::vec3(.5f);
    dirLight.diffuse = glm::vec3(.3f);
    
    dirLight.constant = 0.0f;
    dirLight.quadratic = 0.0f;
    dirLight.linear = 0.0f;
}

unsigned int LightsCollection::getNumLights() {
    return (unsigned int)pointLights.size();
}

char* LightsCollection::lightAttributeName(std::string name) {
    std::ostringstream ss;
    ss << "dirLight." << name;
    return (char*)ss.str().c_str();
}

char* LightsCollection::lightAttributeName(std::string name, const unsigned int index) {
    std::ostringstream ss;
    ss << "pointLights[" << index << "]." << name;
    return (char*)ss.str().c_str();
}

void LightsCollection::setUniform(ShaderProgram *sp) {
    sp->setUniform(lightAttributeName("position"), 1, dirLight.position);
    
    sp->setUniform(lightAttributeName("ambient"), 1, dirLight.ambient);
    sp->setUniform(lightAttributeName("diffuse"), 1, dirLight.diffuse);
    sp->setUniform(lightAttributeName("specular"), 1, dirLight.specular);
}

void LightsCollection::setUniform(ShaderProgram *sp, const unsigned int index) {
    Light _light = getLight(index);

    sp->setUniform(lightAttributeName("position", index), 1, _light.position);
    
    sp->setUniform(lightAttributeName("ambient", index), 1, _light.ambient);
    sp->setUniform(lightAttributeName("diffuse", index), 1, _light.diffuse);
    sp->setUniform(lightAttributeName("specular", index), 1, _light.specular);
    
    sp->setUniform(lightAttributeName("constant", index), _light.constant);
    sp->setUniform(lightAttributeName("linear", index), _light.linear);
    sp->setUniform(lightAttributeName("quadratic", index), _light.quadratic);
}

void LightsCollection::setAllUniforms(ShaderProgram *sp) {
    setUniform(sp); // dir light
    
    unsigned int numLights = getNumLights();
    sp->setUniformInt((char*)"numPointLights", numLights);
    for (int i = 0; i < numLights; ++i) {
        setUniform(sp, i); // point light
    }
}
