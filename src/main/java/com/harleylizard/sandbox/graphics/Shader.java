package com.harleylizard.sandbox.graphics;

import static org.lwjgl.opengl.GL41.*;

public enum Shader {
    FRAGMENT(GL_FRAGMENT_SHADER, GL_FRAGMENT_SHADER_BIT),
    VERTEX(GL_VERTEX_SHADER, GL_VERTEX_SHADER_BIT);

    public final int type;
    public final int bit;

    private Shader(int type, int bit) {
        this.type = type;
        this.bit = bit;
    }
}
