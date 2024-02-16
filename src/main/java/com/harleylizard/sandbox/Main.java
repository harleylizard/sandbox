package com.harleylizard.sandbox;

import com.harleylizard.sandbox.graphics.Matrices;
import com.harleylizard.sandbox.graphics.mesh.WorldMesh;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.world.World;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        var world = new World();
        world.set(-1, 0, Tile.DIRT);
        world.set(0, 18, Tile.DIRT);
        world.set(1, 17, Tile.DIRT);
        world.set(2, 16, Tile.DIRT);

        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }
        var window = new Window();

        glClearColor(0.0F, 0.0F, 0.0F, 1.0F);

        var matrices = new Matrices();
        var worldMesh = new WorldMesh();

        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            matrices.identity();

            var aspectRatio = window.aspectRatio();
            var fovy = (float) Math.toRadians(1170.0F);
            matrices.projection.ortho(-fovy * aspectRatio, fovy * aspectRatio, -fovy, fovy, 1.0F, -1.0F);
            matrices.upload();

            worldMesh.draw(world);

            window.step();
        }
        window.destroy();

        glfwTerminate();
    }
}
