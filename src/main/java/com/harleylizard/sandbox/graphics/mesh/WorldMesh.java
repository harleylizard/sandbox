package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.graphics.ProgramPipeline;
import com.harleylizard.sandbox.graphics.Shader;
import com.harleylizard.sandbox.graphics.TextureArray;
import com.harleylizard.sandbox.graphics.mesh.column.ColumnMesh;
import com.harleylizard.sandbox.graphics.mesh.column.ColumnMeshes;
import com.harleylizard.sandbox.world.Column;
import com.harleylizard.sandbox.world.World;

import java.util.*;

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

    private static final class Cache {
        private final Map<Column, ColumnMesh> map = new HashMap<>();
        private final List<ColumnMesh> list;

        private Cache(List<ColumnMesh> list) {
            this.list = list;
        }

        private boolean hasMore() {
            for (var mesh : list) {
                if (!map.containsValue(mesh)) {
                    return true;
                }
            }
            return false;
        }

        public ColumnMesh get(Column column) {
            for (var mesh : list) {
                if (!map.containsValue(mesh)) {
                    map.put(column, mesh);
                    return mesh;
                }
            }
            return null;
        }

        public void free(Column column) {
            map.remove(column);
        }

        public boolean has(Column column) {
            return map.containsKey(column);
        }

        private static Cache create(int size) {
            var list = new ArrayList<ColumnMesh>(size);
            for (var i = 0; i < size; i++) {
                list.add(new ColumnMesh());
            }
            return new Cache(Collections.unmodifiableList(list));
        }
    }
}
