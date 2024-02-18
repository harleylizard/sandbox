package com.harleylizard.sandbox.graphics;

import static org.lwjgl.opengl.GL41.*;

public enum Shader {
    FRAGMENT(GL_FRAGMENT_SHADER, GL_FRAGMENT_SHADER_BIT),
    VERTEX(GL_VERTEX_SHADER, GL_VERTEX_SHADER_BIT);

    private final int type;
    private final int bit;

    Shader(int type, int bit) {
        this.type = type;
        this.bit = bit;
    }

    public int getType() {
        return type;
    }

    public int getBit() {
        return bit;
    }
}
