package com.harleylizard.sandbox.graphics;

import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL45.*;

public final class Matrices {
    public final Matrix4f projection = new Matrix4f();
    public final Matrix4f view = new Matrix4f();
    public final Matrix4f model = new Matrix4f();

    private final int buffer = glCreateBuffers();

    {
        glBindBufferBase(GL_UNIFORM_BUFFER, 0, buffer);
        glNamedBufferStorage(buffer, 16 * 4 * 3, GL_DYNAMIC_STORAGE_BIT | GL_MAP_READ_BIT | GL_MAP_WRITE_BIT);
    }

    public void identity() {
        projection.identity();
        view.identity();
        model.identity();
    }

    public void upload() {
        var mapped = glMapNamedBuffer(buffer, GL_READ_WRITE);
        if (mapped != null) {
            projection.get(0, mapped);
            view.get(16 * 4, mapped);
            model.get(32 * 4, mapped);
        }
        glUnmapNamedBuffer(buffer);
    }
}
