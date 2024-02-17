package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.graphics.mesh.provider.ConnectedTextureProvider;
import com.harleylizard.sandbox.graphics.mesh.provider.SingleTextureProvider;
import com.harleylizard.sandbox.graphics.mesh.provider.TextureProvider;
import com.harleylizard.sandbox.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class TileTextureGetter {
    private static final List<String> TEXTURES = createTextures();

    private static final Map<Tile, TextureProvider> MAP = Map.of(
            Tile.DIRT,
            ConnectedTextureProvider.of(TEXTURES, "dirt"),
            Tile.GRASS,
            SingleTextureProvider.of(TEXTURES, "textures/tile/grass.png"),
            Tile.TALL_GRASS,
            SingleTextureProvider.of(TEXTURES, "textures/tile/tall_grass.png")
    );

    private TileTextureGetter() {}

    private static List<String> createTextures() {
        var list = new ArrayList<String>();
        addConnected(list, "dirt");

        list.add("textures/tile/grass.png");
        list.add("textures/tile/tall_grass.png");

        return Collections.unmodifiableList(list);
    }

    private static void addConnected(List<String> list, String name) {
        for (var i = 0; i < 16; i++) {
            var s = "textures/tile/%s%d.png".formatted(name, i);
            list.add(s);
        }
    }

    public static TextureProvider getTextureProvider(Tile tile) {
        return MAP.get(tile);
    }

    public static List<String> getTextures() {
        return TEXTURES;
    }
}
