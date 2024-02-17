package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.graphics.ProgramPipeline;
import com.harleylizard.sandbox.graphics.Shader;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.*;

public final class EntityMesh {
    private final ProgramPipeline pipeline = new ProgramPipeline.Builder()
            .attach(Shader.FRAGMENT, "shaders/quad_fragment.glsl")
            .attach(Shader.VERTEX, "shaders/quad_vertex.glsl")
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

            glVertexArrayVertexBuffer(vao, 0, vbo, 0, 16);
            glVertexArrayAttribBinding(vao, 0, 0);
            glVertexArrayAttribFormat(vao, 0, 4, GL_FLOAT, false, 0);
            glVertexArrayElementBuffer(vao, ebo);
        }

        var buffer = memCalloc((16 + 6) * 4);

        buffer.putFloat(-0.5F).putFloat(-0.5F).putFloat(0.0F).putFloat(1.0F);
        buffer.putFloat( 0.5F).putFloat(-0.5F).putFloat(0.0F).putFloat(1.0F);
        buffer.putFloat( 0.5F).putFloat( 0.5F).putFloat(0.0F).putFloat(1.0F);
        buffer.putFloat(-0.5F).putFloat( 0.5F).putFloat(0.0F).putFloat(1.0F);

        buffer.putInt(0).putInt(1).putInt(2).putInt(2).putInt(3).putInt(0);

        buffer.flip();

        var vertices = 16 * 4;
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

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glDisableVertexArrayAttrib(vao, 0);

        glBindVertexArray(0);

        ProgramPipeline.unbind();
    }
}
