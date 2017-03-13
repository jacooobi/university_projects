//
//  Material.hpp
//  Museum
//
//  Created by Tomasz Kasperek on 24.08.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef Material_hpp
#define Material_hpp

#include "tinyobjloader.hpp"
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "ShaderProgram.hpp"
#include "Texture.hpp"
#include "utils.hpp"

class Material {
private:
    float shininess;
    float transmittance;
    float emission;
    float dissolve;
    float illum;
    
    glm::vec3 diffuse;
    glm::vec3 ambient;
    glm::vec3 specular;
    
    Texture *texture;
public:
    Material(const tinyobj::material_t &material, Texture *tex);
    virtual ~Material();
    void setUniform(ShaderProgram *sp);
};

#endif /* Material_hpp */
