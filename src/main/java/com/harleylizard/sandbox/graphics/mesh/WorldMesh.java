package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.graphics.ProgramPipeline;
import com.harleylizard.sandbox.graphics.Shader;
import com.harleylizard.sandbox.world.World;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WorldMesh {
    private final ProgramPipeline pipeline = new ProgramPipeline.Builder()
            .attach(Shader.FRAGMENT, "shaders/quad_fragment.glsl")
            .attach(Shader.VERTEX, "shaders/quad_vertex.glsl")
            .build();

    private final Cache cache = Cache.create(2);

    public void draw(World world) {
        var entries = world.getEntries();
        if (entries.isEmpty()) {
            return;
        }
        pipeline.bind();
        for (var entry : entries) {
            if (!cache.hasMore()) {
                continue;
            }
            var mesh = cache.get();
            if (mesh != null) {
                mesh.upload(entry);
            }
        }

        for (var mesh : cache.list) {
            mesh.draw();
        }
        ProgramPipeline.unbind();
    }

    private static final class Cache {
        private final Object2BooleanMap<ColumnMesh> map = new Object2BooleanArrayMap<>();
        private final List<ColumnMesh> list;

        private Cache(List<ColumnMesh> list) {
            this.list = list;
        }

        private boolean hasMore() {
            for (var mesh : list) {
                if (!map.getBoolean(mesh)) {
                    return true;
                }
            }
            return false;
        }

        public ColumnMesh get() {
            for (var mesh : list) {
                if (!map.getBoolean(mesh)) {
                    map.put(mesh, true);
                    return mesh;
                }
            }
            return null;
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
