package com.harleylizard.sandbox.graphics.tile.provider;

import com.harleylizard.sandbox.world.World;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;

import java.util.List;

public final class VariationTextureProvider implements TextureProvider {
    private final IntList variants;

    private VariationTextureProvider(IntList variants) {
        this.variants = variants;
    }

    @Override
    public int getTexture(World world, int x, int y) {
        return variants.getInt(getIndex(x, y));
    }

    private int getIndex(int x, int y) {
        var i = ((x << 16) | y) & 31;

        var f = ((float) i / 31.0F) * (variants.size() - 1.0F);

        return (int) f;
    }

    public static TextureProvider of(List<String> paths, String name, int variants) {
        var i = paths.indexOf("textures/tile/%s0.png".formatted(name));

        var list = new IntArrayList(variants);
        for (var j = 0; j < variants; j++) {
            list.add(i + j);
        }
        return new VariationTextureProvider(IntLists.unmodifiable(list));
    }
}
