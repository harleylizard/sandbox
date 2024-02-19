package com.harleylizard.sandbox.graphics.mesh.column;

import com.harleylizard.sandbox.layer.MutableLayeredColumn;
import com.harleylizard.sandbox.world.World;
import org.lwjgl.system.MemoryStack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memFree;

public final class ColumnMesh {
    private final AtomicReference<Runnable> reference = new AtomicReference<>();

    private final AtomicInteger count = new AtomicInteger();
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

            glVertexArrayVertexBuffer(vao, 0, vbo, 0, (4 + 3 + 3) * 4);
            glVertexArrayAttribBinding(vao, 0, 0);
            glVertexArrayAttribBinding(vao, 1, 0);
            glVertexArrayAttribBinding(vao, 2, 0);
            glVertexArrayAttribFormat(vao, 0, 4, GL_FLOAT, false, 0);
            glVertexArrayAttribFormat(vao, 1, 3, GL_FLOAT, false, 16);
            glVertexArrayAttribFormat(vao, 2, 3, GL_FLOAT, false, 28);
            glVertexArrayElementBuffer(vao, ebo);
        }
    }

    public void upload(World world, int position, MutableLayeredColumn column) {
        ColumnBuffer.of(world, column, position).thenAcceptAsync(columnBuffer -> {
            var buffer = columnBuffer.getBuffer();
            var vertices = columnBuffer.getVertices();
            var elements = columnBuffer.getElements();

            count.set(columnBuffer.getCount());

            buffer.limit(vertices);
            glNamedBufferData(vbo, buffer, GL_DYNAMIC_DRAW);
            buffer.position(vertices);

            buffer.limit(vertices + elements);
            glNamedBufferData(ebo, buffer, GL_DYNAMIC_DRAW);
            buffer.position(0);

            memFree(buffer);
        }, reference::set);
    }

    public void draw() {
        var runnable = reference.get();
        if (runnable != null) {
            runnable.run();
            reference.set(null);
        }

        glBindVertexArray(vao);

        glEnableVertexArrayAttrib(vao, 0);
        glEnableVertexArrayAttrib(vao, 1);
        glEnableVertexArrayAttrib(vao, 2);

        glDrawElements(GL_TRIANGLES, count.get(), GL_UNSIGNED_INT, 0);

        glDisableVertexArrayAttrib(vao, 0);
        glDisableVertexArrayAttrib(vao, 1);
        glDisableVertexArrayAttrib(vao, 2);

        glBindVertexArray(0);
    }
}
