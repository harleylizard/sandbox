package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.graphics.ProgramPipeline;
import com.harleylizard.sandbox.graphics.Shader;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.*;

public final class SkyMesh {
    private final ProgramPipeline pipeline = new ProgramPipeline.Builder()
            .attach(Shader.FRAGMENT, "shaders/sky_fragment.glsl")
            .attach(Shader.VERTEX, "shaders/sky_vertex.glsl")
            .build();

    private final int vao;
    private final int vbo;
    private final int ebo;

    {
        try (var stack = MemoryStack.stackPush()) {
            var buffer = stack.callocInt(3);

            nglCreateVertexArrays(1, memAddress(buffer));
            nglCreateBuffers(2, memAddress(buffer) + 4);

            vao = buffer.get(0);
            vbo = buffer.get(1);
            ebo = buffer.get(2);

            glVertexArrayVertexBuffer(vao, 0, vbo, 0, 28);
            glVertexArrayAttribBinding(vao, 0, 0);
            glVertexArrayAttribBinding(vao, 1, 0);
            glVertexArrayAttribFormat(vao, 0, 4, GL_FLOAT, false, 0);
            glVertexArrayAttribFormat(vao, 1, 3, GL_FLOAT, false, 16);
            glVertexArrayElementBuffer(vao, ebo);
        }

        var buffer = memCalloc((28 + 6) * 4);

        var width = 854.0F / 16.0F;
        var height = 480.0F / 16.0F;

        buffer.putFloat(-width).putFloat(-height).putFloat(0.0F).putFloat(1.0F).putFloat(214.0F / 255.0F).putFloat(232.0F / 255.0F).putFloat(1.0F);
        buffer.putFloat( width).putFloat(-height).putFloat(0.0F).putFloat(1.0F).putFloat(214.0F / 255.0F).putFloat(232.0F / 255.0F).putFloat(1.0F);
        buffer.putFloat( width).putFloat( height).putFloat(0.0F).putFloat(1.0F).putFloat(130.0F / 255.0F).putFloat(186.0F / 255.0F).putFloat(1.0F);
        buffer.putFloat(-width).putFloat( height).putFloat(0.0F).putFloat(1.0F).putFloat(130.0F / 255.0F).putFloat(186.0F / 255.0F).putFloat(1.0F);

        buffer.putInt(0).putInt(1).putInt(2).putInt(2).putInt(3).putInt(0);

        buffer.flip();

        var vertices = 28 * 4;
        var elements = 6 * 4;

        buffer.limit(vertices);
        glNamedBufferStorage(vbo, buffer, 0);
        buffer.position(vertices);

        buffer.limit(vertices + elements);
        glNamedBufferStorage(ebo, buffer, 0);
        buffer.position(0);

        memFree(buffer);
    }

    public void draw() {
        pipeline.bind();

        glBindVertexArray(vao);

        glEnableVertexArrayAttrib(vao, 0);
        glEnableVertexArrayAttrib(vao, 1);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glDisableVertexArrayAttrib(vao, 0);
        glDisableVertexArrayAttrib(vao, 1);

        glBindVertexArray(0);

        ProgramPipeline.unbind();
    }
}
