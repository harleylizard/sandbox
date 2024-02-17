#version 460 core

out vec4 fragColor;

in vec3 outTexture;

uniform sampler2DArray textureSampler;

void main() {
    fragColor = texture(textureSampler, outTexture) * vec4(1.0F, 1.0F, 1.0F, 1.0F);
}