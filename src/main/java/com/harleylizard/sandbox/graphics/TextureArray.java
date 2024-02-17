package com.harleylizard.sandbox.graphics;

import com.harleylizard.sandbox.Resources;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.util.List;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.nstbi_image_free;
import static org.lwjgl.stb.STBImage.nstbi_load_from_memory;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memFree;

public final class TextureArray {

    private TextureArray() {}

    public static void of(List<String> paths, int width, int height, int binding) {
        try (var stack = MemoryStack.stackPush()) {
            var buffer = stack.callocInt(3);

            var texture = glCreateTextures(GL_TEXTURE_2D_ARRAY);

            glTextureParameteri(texture, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTextureParameteri(texture, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            var size = paths.size();
            glTextureStorage3D(texture, 1, GL_RGBA8, width, height, size);

            for (var i = 0; i < size; i++) {
                try {
                    var image = Resources.readImage(paths.get(i));

                    var pixels = nstbi_load_from_memory(memAddress(image), image.remaining(), memAddress(buffer), memAddress(buffer) + 4, memAddress(buffer) + 8, 4);

                    glTextureSubImage3D(texture, 0, 0, 0, i, buffer.get(0), buffer.get(1), 1, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

                    nstbi_image_free(pixels);

                    memFree(image);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            glBindTextureUnit(binding, texture);
        }
    }
}
