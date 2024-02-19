package com.harleylizard.sandbox.graphics.tile.provider;

import com.harleylizard.sandbox.world.World;

import java.util.List;

public final class SingleTextureProvider implements TextureProvider {
    private final int i;

    private SingleTextureProvider(int i) {
        this.i = i;
    }

    @Override
    public int getTexture(World world, int x, int y) {
        return i;
    }

    public static TextureProvider of(List<String> paths, String path) {
        return new SingleTextureProvider(paths.indexOf(path));
    }
}
