package com.harleylizard.sandbox.graphics.mesh.provider;

import com.harleylizard.sandbox.tile.Layer;
import com.harleylizard.sandbox.tile.TileLayers;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.world.World;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;

import java.util.List;

public final class ConnectedTextureProvider implements TextureProvider {
    private final Int2IntMap map;

    private ConnectedTextureProvider(Int2IntMap map) {
        this.map = map;
    }

    @Override
    public int getTexture(World world, int x, int y) {
        return map.get(getConnection(world, x, y));
    }

    public static ConnectedTextureProvider of(List<String> paths, String name) {
        var i = paths.indexOf("textures/tile/%s0.png".formatted(name));

        var map = new Int2IntArrayMap();
        put(map, false, false, false, false, i);
        put(map, true,  false, false, false, i + 1);
        put(map, true,  true,  false, false, i + 2);
        put(map, false, false, true,  false, i + 3);
        put(map, false, true, true, false, i + 4);
        put(map, true, false, true, false, i + 5);
        put(map, true, true, true, false, i + 6);
        put(map, false, false, false, true, i + 7);
        put(map, false, true, false, true, i + 8);
        put(map, true, false, false, true, i + 9);
        put(map, true,  true,  false, true, i + 10);
        put(map, false, false, true,  true, i + 11);
        put(map, false, true,  true,  true, i + 12);
        put(map, true,  false, true,  true, i + 13);
        put(map, true,  true,  true,  true, i + 14);
        put(map, false, true,  false, false, i + 15);
        return new ConnectedTextureProvider(Int2IntMaps.unmodifiable(map));
    }

    private static void put(Int2IntMap map, boolean up, boolean down, boolean left, boolean right, int i) {
        var j = toInt(up, down, left, right);
        if (map.containsKey(j)) {
            throw new RuntimeException("duplicated value " + j);
        }
        map.put(j, i);
    }

    private static int getConnection(World world, int x, int y) {
        var down = matches(world, x, y - 1);
        var up = matches(world, x, y + 1);
        var left = matches(world, x - 1, y);
        var right = matches(world, x + 1, y);

        return toInt(down, up, left, right);
    }

    private static boolean matches(World world, int x, int y) {
        var tile = world.getTile(x, y);
        return tile != Tile.AIR && TileLayers.getLayer(tile) == Layer.SOLID;
    }

    private static int toInt(boolean up, boolean down, boolean left, boolean right) {
        int i = 0;
        i |= (down ? 1 : 0) << 1;
        i |= (up ? 1 : 0) << 2;
        i |= (left ? 1 : 0) << 3;
        i |= (right ? 1 : 0) << 4;
        return i;
    }
}
