package com.harleylizard.sandbox.graphics.mesh.column;

import com.harleylizard.sandbox.world.World;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ColumnMeshes {
    private final IntPriorityQueue queue = new IntArrayFIFOQueue();

    private final Int2ObjectMap<ColumnMesh> map = new Int2ObjectArrayMap<>();
    private final List<ColumnMesh> list;

    private ColumnMeshes(int size) {
        var list = new ArrayList<ColumnMesh>(size);
        for (var i = 0; i < size; i++) {
            list.add(new ColumnMesh());
        }
        this.list = Collections.unmodifiableList(list);
    }

    public void free(int position) {
        map.remove(position);
    }

    public void draw(World world) {
        var queue = world.getQueue();
        if (!queue.isEmpty()) {
            this.queue.enqueue(queue.poll().firstInt());
        }

        var entries = world.getEntries();
        if (entries.isEmpty()) {
            return;
        }
        for (var entry : entries) {
            if (!hasNext()) {
                continue;
            }
            var mesh = getNext(entry.getIntKey());
            if (mesh != null) {
                mesh.upload(world, entry.getIntKey(), entry.getValue());
            }
        }

        if (!this.queue.isEmpty()) {
            var position = this.queue.dequeueInt();
            var column = world.getColumn(position);
            var mesh = map.get(position);

            if (column != null && mesh != null) {
                mesh.upload(world, position, column);
            }
        }

        for (var mesh : list) {
            mesh.draw();
        }
    }

    private boolean hasNext() {
        for (var mesh : list) {
            if (!map.containsValue(mesh)) {
                return true;
            }
        }
        return false;
    }

    private ColumnMesh getNext(int position) {
        for (var mesh : list) {
            if (!map.containsValue(mesh)) {
                map.put(position, mesh);
                return mesh;
            }
        }
        return null;
    }

    public static ColumnMeshes create(int size) {
        return new ColumnMeshes(size);
    }
}
