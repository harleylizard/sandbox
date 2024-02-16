package com.harleylizard.sandbox;

import com.harleylizard.sandbox.graphics.Matrices;
import com.harleylizard.sandbox.graphics.mesh.QuadMesh;
import com.harleylizard.sandbox.world.World;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        var world = new World();

        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }
        var window = new Window();

        var quad = new QuadMesh();

        glClearColor(0.0F, 0.0F, 0.0F, 1.0F);

        var matrices = new Matrices();

        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            matrices.identity();

            var aspectRatio = window.aspectRatio();
            var fovy = (float) Math.toRadians(70.0F);
            matrices.projection.ortho(-fovy * aspectRatio, fovy * aspectRatio, -fovy, fovy, 1.0F, -1.0F);
            matrices.upload();

            quad.draw();

            window.step();
        }

        glfwTerminate();
    }
}
