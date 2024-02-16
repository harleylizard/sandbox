package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.world.Column;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.*;

public final class ColumnMesh {
    private final int vao;
    private final int vbo;
    private final int ebo;

    private int count;

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
    }

    public void upload(Int2ObjectMap.Entry<Column> entry) {
        var column = entry.getValue();
        var tiles = column.copyOf();
        var palette = column.getPalette();

        var size = 0;
        for (var i = 0; i < 16 * 16 * 16; i++) {
            var j = tiles[i];
            if (j > 0) {
                size++;
            }
        }
        count = 6 * size;
        var buffer = memCalloc(((16 + 6) * 4) * size);

        for (var i = 0; i < 16 * 16 * 16; i++) {
            var j = tiles[i];
            if (j > 0) {
                var offset = entry.getIntKey() << 4;
                var x = (i % 16) + offset;
                var y = i / 16;
                buffer.putFloat(-0.5F + x).putFloat(-0.5F + y).putFloat(0.0F).putFloat(1.0F);
                buffer.putFloat( 0.5F + x).putFloat(-0.5F + y).putFloat(0.0F).putFloat(1.0F);
                buffer.putFloat( 0.5F + x).putFloat( 0.5F + y).putFloat(0.0F).putFloat(1.0F);
                buffer.putFloat(-0.5F + x).putFloat( 0.5F + y).putFloat(0.0F).putFloat(1.0F);
            }
        }

        for (var i = 0; i < size; i++) {
            var offset = 4 * i;
            buffer
                    .putInt(0 + offset)
                    .putInt(1 + offset)
                    .putInt(2 + offset)
                    .putInt(2 + offset)
                    .putInt(3 + offset)
                    .putInt(0 + offset);
        }

        buffer.flip();

        var vertices = (16 * 4) * size;
        var elements = (6 * 4) * size;

        buffer.limit(vertices);
        glNamedBufferData(vbo, buffer, GL_DYNAMIC_DRAW);
        buffer.position(vertices);

        buffer.limit(vertices + elements);
        glNamedBufferData(ebo, buffer, GL_DYNAMIC_DRAW);
        buffer.position(0);

        memFree(buffer);
    }

    public void draw() {
        glBindVertexArray(vao);

        glEnableVertexArrayAttrib(vao, 0);

        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);

        glDisableVertexArrayAttrib(vao, 0);

        glBindVertexArray(0);
    }
}
