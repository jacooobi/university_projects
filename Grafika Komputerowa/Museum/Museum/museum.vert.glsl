#version 330

// Uniform
uniform mat4 camera;
uniform mat4 model;
uniform vec3 viewPos;

// Attributes
in vec3 vertex;
in vec3 normal;
in vec2 texCoord;

out vec3 fragPosition;
out vec3 fragNormal;
out vec2 fragTexCoord;

void main(void) {
    gl_Position = camera * model * vec4(vertex, 1);
    
    mat3 normalMatrix = transpose(inverse(mat3(model)));
    fragNormal = normalize(normalMatrix * normal);
    fragPosition = vec3(model * vec4(vertex, 1));
    fragTexCoord = texCoord;
}
