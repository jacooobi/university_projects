//
//  Light.hpp
//  Museum
//
//  Created by Tomasz Kasperek on 24.08.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef Light_hpp
#define Light_hpp

#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"

enum LightType {
    POINT,
    DIRECTIONAL,
};

typedef enum LightType ELightType;

struct Light {
    ELightType type;
    
    glm::vec4 position;
    
    glm::vec3 ambient;
    glm::vec3 specular;
    glm::vec3 diffuse;
    
    // Point light specifc
    float constant;
    float linear;
    float quadratic;
};

typedef struct Light Light;


#endif /* Light_hpp */
