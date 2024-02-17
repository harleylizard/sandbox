package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.world.Column;
import com.harleylizard.sandbox.world.World;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;

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

    public void upload(World world, Int2ObjectMap.Entry<Column> entry) {
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

        var vertices = (((4 + 3 + 3) * 4) * 4) * size;
        var elements = (6 * 4) * size;
        var buffer = memCalloc(vertices + elements);

        for (var i = 0; i < 16 * 16 * 16; i++) {
            var j = tiles[i];
            if (j > 0) {
                var offset = entry.getIntKey() << 4;

                var scale = 8.0F / 16.0F;
                var x = (i % 16) + offset;
                var y = i / 16;

                var width = scale * x;
                var height = scale * y;

                var texture = (float) TileTextureGetter.get(palette.getObject(j));
                var light = Lighting.lightFor(world, x, y);
                var r = light;
                var g = light;
                var b = light;
                vertex(buffer,  0.0F + width, 0.0F + height, 0.0F, 0.0F, 1.0F, texture, r, g, b);
                vertex(buffer, scale + width, 0.0F + height, 0.0F, 1.0F, 1.0F, texture, r, g, b);
                vertex(buffer, scale + width, scale + height, 0.0F, 1.0F, 0.0F, texture, r, g, b);
                vertex(buffer,  0.0F + width, scale + height, 0.0F, 0.0F, 0.0F, texture, r, g, b);
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
        glEnableVertexArrayAttrib(vao, 1);
        glEnableVertexArrayAttrib(vao, 2);

        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);

        glDisableVertexArrayAttrib(vao, 0);
        glDisableVertexArrayAttrib(vao, 1);
        glDisableVertexArrayAttrib(vao, 2);

        glBindVertexArray(0);
    }

    private static void vertex(ByteBuffer buffer, float x, float y, float z, float u, float v, float t, float r, float g, float b) {
        buffer.putFloat(x).putFloat(y).putFloat(z).putFloat(1.0F).putFloat(u).putFloat(v).putFloat(t).putFloat(r).putFloat(g).putFloat(b);
    }
}
