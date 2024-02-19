package com.harleylizard.sandbox;

import com.harleylizard.sandbox.entity.Player;
import com.harleylizard.sandbox.graphics.Matrices;
import com.harleylizard.sandbox.graphics.mesh.EntityMesh;
import com.harleylizard.sandbox.graphics.mesh.SkyMesh;
import com.harleylizard.sandbox.graphics.mesh.WorldMesh;
import com.harleylizard.sandbox.input.Keyboard;
import com.harleylizard.sandbox.input.Mouse;
import com.harleylizard.sandbox.world.World;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        var world = new World();
        for (var i = -8; i < 8; i++) {
            world.generate(i);
        }
        for (var i = -8; i < 8; i++) {
            world.generateStructures(i);
        }


        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }
        var window = new Window();

        var keyboard = new Keyboard();
        var mouse = new Mouse();

        window.setInput(keyboard, mouse);

        var matrices = new Matrices();
        var worldMesh = new WorldMesh();
        var entityMesh = new EntityMesh();
        var skyMesh = new SkyMesh();

        var player = new Player();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            matrices.identity();

            var aspectRatio = window.getAspectRatio();
            var fovy = (float) Math.toRadians(1000.0F);
            matrices.projection.ortho(-fovy * aspectRatio, fovy * aspectRatio, -fovy, fovy, 1.0F, -1.0F);

            skyMesh.draw();

            var position = player.getPosition();
            var x = position.x;
            var y = position.y;

            matrices.view.translate(-x, -y, 0.0F);
            player.stepWithInput(world, keyboard, mouse, mouse.getWorldSpace(window, matrices.projection, matrices.view, matrices.model));

            matrices.upload();
            worldMesh.draw(world);

            matrices.model.translate(x, y, 0.0F);
            matrices.upload();


            entityMesh.draw();

            window.step();
        }
        window.destroy();

        glfwTerminate();
    }
}
