#version 330 core

// Uniform
uniform mat4 M;
uniform vec3 viewPos;
uniform struct Material {
    sampler2D tex;
    vec3 texScale;
    
    vec3 specular;
    vec3 ambient;
    vec3 diffuse;
    
    float shininess;
} material;

#define MAX_LIGHTS 5
uniform int numPointLights;
uniform struct PointLight {
    vec4 position;
    
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    
    float quadratic;
    float linear;
    float constant;
} pointLights[MAX_LIGHTS];

uniform struct DirLight {
    vec4 position;
    
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
} dirLight;

// IN
in vec3 fragPosition;
in vec3 fragNormal;
in vec2 fragTexCoord;
// OUT
out vec4 pixelColor;

vec3 applyPointLight(PointLight light, vec3 texColor, vec3 normal, vec3 viewDir) {
    // Diffuse
    vec3 lightDir = normalize(light.position.xyz - fragPosition);
    float diff = max(dot(lightDir, normal), 0.0);
    
    // Specular
    vec3 reflectDir = reflect(-lightDir, normal);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(normal, halfwayDir), 0.0), material.shininess);
    
    // Combine
    vec3 diffuse = light.diffuse * diff * texColor;
    vec3 specular = light.specular * spec;
    vec3 ambient = light.ambient * texColor;
    
    // Attenuation
    float distance = length(light.position.xyz - fragPosition);
    float attenuation = 1.0f / (light.constant + light.linear * distance + light.quadratic * (distance * distance));
    
    ambient *= attenuation;
    specular *= attenuation;
    diffuse *= attenuation;
    
    return ambient + diffuse + specular;
}

vec3 applyDirLight(DirLight light, vec3 texColor, vec3 normal, vec3 viewDir) {
    // Diffuse
    vec3 lightDir = normalize(light.position.xyz - fragPosition);
    float diff = max(dot(lightDir, normal), 0.0);
    
    // Specular
    vec3 reflectDir = reflect(-lightDir, normal);
    vec3 halfwayDir = normalize(lightDir + viewDir);
    float spec = pow(max(dot(normal, halfwayDir), 0.0), material.shininess);
    
    // Combine
    vec3 diffuse = light.diffuse * diff * texColor;
    vec3 specular = light.specular * spec;
    vec3 ambient = light.ambient * texColor;
    
    return ambient + diffuse + specular;
}

void main() {
    vec3 color = texture(material.tex, fragTexCoord).rgb;
    
    vec3 normal = normalize(fragNormal);
    vec3 viewDir = normalize(viewPos - fragPosition);
    
    vec3 result = applyDirLight(dirLight, color, normal, viewDir);
    for (int i = 0; i < numPointLights; ++i) {
        result += applyPointLight(pointLights[i], color, normal, viewDir);
    }
    
    pixelColor = vec4(result, 1.0);
}
