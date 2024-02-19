package com.harleylizard.sandbox.graphics.mesh.column;

import com.harleylizard.sandbox.layer.MutableLayeredColumn;
import com.harleylizard.sandbox.graphics.mesh.TileTextureGetter;
import com.harleylizard.sandbox.graphics.tile.Lighting;
import com.harleylizard.sandbox.world.World;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

import static org.lwjgl.system.MemoryUtil.memCalloc;

public final class ColumnBuffer {
    private final int vertices;
    private final int elements;
    private final int count;

    private final ByteBuffer buffer;

    private ColumnBuffer(int vertices, int elements, int count, ByteBuffer buffer) {
        this.vertices = vertices;
        this.elements = elements;
        this.count = count;
        this.buffer = buffer;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public int getVertices() {
        return vertices;
    }

    public int getElements() {
        return elements;
    }

    public int getCount() {
        return count;
    }

    public static CompletableFuture<ColumnBuffer> of(World world, MutableLayeredColumn column, int position) {
        return CompletableFuture.supplyAsync(() -> {
            var size = 0;
            for (var entry : column) {
                if (entry.getTile() > 0) {
                    size++;
                }
            }
            var palette = column.getPalette();
            var count = 6 * size;

            var vertices = (((4 + 3 + 3) * 4) * 4) * size;
            var elements = (6 * 4) * size;
            var buffer = memCalloc(vertices + elements);

            for (var entry : column) {
                var tile = entry.getTile();
                if (tile > 0) {
                    var offset = position << 4;

                    var scale = 16.0F / 16.0F;
                    var x = entry.getX() + offset;
                    var y = entry.getY();

                    var width = scale * x;
                    var height = scale * y;

                    var texture = (float) TileTextureGetter.getTextureProvider(palette.getObject(tile)).getTexture(world, x, y);
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

            return new ColumnBuffer(vertices, elements, count, buffer);
        });
    }

    private static void vertex(ByteBuffer buffer, float x, float y, float z, float u, float v, float t, float r, float g, float b) {
        buffer.putFloat(x).putFloat(y).putFloat(z).putFloat(1.0F).putFloat(u).putFloat(v).putFloat(t).putFloat(r).putFloat(g).putFloat(b);
    }
}
