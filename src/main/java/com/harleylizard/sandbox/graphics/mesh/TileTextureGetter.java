package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.graphics.tile.provider.ConnectedTextureProvider;
import com.harleylizard.sandbox.graphics.tile.provider.SingleTextureProvider;
import com.harleylizard.sandbox.graphics.tile.provider.TextureProvider;
import com.harleylizard.sandbox.graphics.tile.provider.VariationTextureProvider;
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
            Tile.ROOTED_DIRT,
            SingleTextureProvider.of(TEXTURES, "textures/tile/rooted_dirt.png"),
            Tile.GRASS,
            ConnectedTextureProvider.of(TEXTURES, "grass"),
            Tile.TALL_GRASS,
            VariationTextureProvider.of(TEXTURES, "tall_grass", 5),
            Tile.STONE_BRICK,
            SingleTextureProvider.of(TEXTURES, "textures/tile/stone_brick.png"),
            Tile.LOG,
            SingleTextureProvider.of(TEXTURES, "textures/tile/log.png"),
            Tile.LEAVES,
            SingleTextureProvider.of(TEXTURES, "textures/tile/leaves.png")
    );

    private TileTextureGetter() {}

    private static List<String> createTextures() {
        var list = new ArrayList<String>();
        addMultiple(list, "dirt", 16);
        addMultiple(list, "grass", 16);
        addMultiple(list, "tall_grass", 5);

        list.add("textures/tile/rooted_dirt.png");
        list.add("textures/tile/stone_brick.png");
        list.add("textures/tile/log.png");
        list.add("textures/tile/leaves.png");

        return Collections.unmodifiableList(list);
    }

    private static void addMultiple(List<String> list, String name, int size) {
        for (var i = 0; i < size; i++) {
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
