#version 460 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec3 texture;
layout (location = 2) in vec3 colour;

layout (binding = 0, column_major) uniform matrices {
    mat4 projection;
    mat4 view;
    mat4 model;
};

out gl_PerVertex {
    vec4 gl_Position;
};

out vec3 outTexture;
out vec3 outColour;

void main() {
    gl_Position = projection * view * model * position;

    outTexture = texture;
    outColour = colour;
}