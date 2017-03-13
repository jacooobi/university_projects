//
//  Material.cpp
//  Museum
//
//  Created by Tomasz Kasperek on 24.08.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#include "Material.hpp"

Material::Material(const tinyobj::material_t &material, Texture *tex) : texture(tex) {
    // Always assume the 3 values in vectors are the same
    ambient = glm::vec3(material.ambient[0], material.ambient[1], material.ambient[2]);
    diffuse = glm::vec3(material.diffuse[0], material.diffuse[1], material.diffuse[2]);
    specular = glm::vec3(material.specular[0], material.specular[1], material.specular[2]);
    
    transmittance = material.transmittance[0];
    emission = material.emission[0];
    shininess = material.shininess;
    dissolve = material.dissolve;
}

Material::~Material() {}

void Material::setUniform(ShaderProgram *sp) {
    sp->setUniform((char*)"material.shininess", shininess);
    
    sp->setUniform((char*)"material.specular", 1, specular);
    sp->setUniform((char*)"material.ambient", 1, ambient);
    sp->setUniform((char*)"material.diffuse", 1, diffuse);
    
    if (texture != nullptr) {
        texture->setUniform(sp);
    }
}
