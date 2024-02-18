package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.graphics.ProgramPipeline;
import com.harleylizard.sandbox.graphics.Shader;
import com.harleylizard.sandbox.graphics.TextureArray;
import com.harleylizard.sandbox.graphics.mesh.column.ColumnMeshes;
import com.harleylizard.sandbox.world.World;

public final class WorldMesh {

    static {
        TextureArray.of(TileTextureGetter.getTextures(), 8, 8, 0);
    }

    private final ProgramPipeline pipeline = new ProgramPipeline.Builder()
            .attach(Shader.FRAGMENT, "shaders/column_fragment.glsl")
            .attach(Shader.VERTEX, "shaders/column_vertex.glsl")
            .build();

    private final ColumnMeshes meshes = ColumnMeshes.create(16);


    public void draw(World world) {
        pipeline.bind();
        meshes.draw(world);
        ProgramPipeline.unbind();
    }
}
