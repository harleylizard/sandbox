package com.harleylizard.sandbox.graphics.mesh.provider;

import com.harleylizard.sandbox.world.World;

public sealed interface TextureProvider permits ConnectedTextureProvider, SingleTextureProvider {

    int getTexture(World world, int x, int y);
}
