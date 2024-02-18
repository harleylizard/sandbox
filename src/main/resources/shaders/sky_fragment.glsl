#version 460 core

out vec4 fragColor;

in vec3 outColour;

void main() {
    fragColor = vec4(outColour, 1.0F) * vec4(1.0F, 1.0F, 1.0F, 1.0F);
}