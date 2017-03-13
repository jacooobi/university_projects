//
//  Camera.hpp
//  Museum
//
//  Created by Jacek Kubiak on 27/09/16.
//  Copyright © 2016 TKJK. All rights reserved.
//

#ifndef Camera_hpp
#define Camera_hpp

#include <stdio.h>
#include "glm/glm.hpp"

class Camera {
public:
    Camera();
    
    const glm::vec3& position() const;

    float fieldOfView() const;
    float nearPlane() const;
    float farPlane() const;
    float viewportAspectRatio() const;
    
    void setFieldOfView(float fieldOfView);
    void setPosition(const glm::vec3& position);
    void setNearAndFarPlanes(float nearPlane, float farPlane);
    void setViewportAspectRatio(float viewportAspectRatio);
    
    void lookAt(glm::vec3 position);
    void offsetPosition(const glm::vec3& offset);
    void offsetOrientation(float upAngle, float rightAngle);
    
    glm::vec3 forward() const;
    glm::vec3 right() const;
    glm::vec3 up() const;
    glm::mat4 matrix() const;
    glm::mat4 projection() const;
    glm::mat4 view() const;
    glm::mat4 orientation() const;
    
private:
    glm::vec3 _position;
    float _horizontalAngle;
    float _verticalAngle;
    float _fieldOfView;
    float _nearPlane;
    float _farPlane;
    float _viewportAspectRatio;
    
    void normalizeAngles();
};

#endif /* Camera_hpp */
