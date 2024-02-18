package com.harleylizard.sandbox.graphics.mesh.column;

import com.harleylizard.sandbox.tile.Layer;
import com.harleylizard.sandbox.tile.TileLayers;
import com.harleylizard.sandbox.math.Maths;
import com.harleylizard.sandbox.world.World;

public final class LightingCalculator {

    private LightingCalculator() {}

    public static float lightFor(World world, int x, int y) {
        var intensity = 0.0F;
        var sum = 0.0F;
        var smoothness = 8;

        for (var j = -smoothness; j < smoothness + 1; j++) {
            for (var k = -smoothness; k < smoothness + 1; k++) {
                var distance = (float) Math.sqrt(j * j + k * k);
                if (distance < smoothness) {
                    var weight = 1.0F / (distance + 1.0F);

                    var tile = world.getTile(x + j, y + k);
                    if (TileLayers.getLayer(tile) != Layer.TRANSPARENT) {
                        intensity += weight;
                    }
                    sum += weight;
                }
            }
        }

        var f = intensity / sum;
        return Maths.clamp(Maths.coserp(3.0F, 0.0F, f), 0.0F, 1.0F);
    }
}
