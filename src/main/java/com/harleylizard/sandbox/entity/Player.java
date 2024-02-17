package com.harleylizard.sandbox.entity;

import com.harleylizard.sandbox.input.Keyboard;
import com.harleylizard.sandbox.math.BoundingBox;
import com.harleylizard.sandbox.math.Maths;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.world.World;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public final class Player implements Entity {
    private static final BoundingBox BOUNDING_BOX = new BoundingBox(0.0F, 0.0F, 1.0F, 1.0F);

    private final BoundingBox boundingBox = new BoundingBox(-0.5F, -0.5F, 0.5F, 0.5F);

    private final Vector2f position = new Vector2f();

    private final Vector2f velocity = new Vector2f();

    private final float speed = 0.25F;

    private int steps;

    {
        position.y = 150.0F;
    }

    public void stepWithInput(World world, Keyboard keyboard) {
        steps++;

        world.setTile((int) Math.floor(position.x), (int) position.y, Tile.GRASS);

        // moveOut(world);

        if (keyboard.isPressed(GLFW_KEY_A)) {
            velocity.x -= speed;
        }
        if (keyboard.isPressed(GLFW_KEY_D)) {
            velocity.x += speed;
        }
        if (keyboard.isPressed(GLFW_KEY_W)) {
            velocity.y += speed;
        }
        if (keyboard.isPressed(GLFW_KEY_S)) {
            velocity.y -= speed;
        }

        step(world);
    }

    @Override
    public void step(World world) {
        velocity.x = Maths.clamp(velocity.x, -speed, speed);
        velocity.y = Maths.clamp(velocity.y, -speed, speed);

        var friction = 0.5F;
        if (velocity.x != 0.0F) {
            velocity.x = Maths.coserp(velocity.x, 0.0F, friction);
        }
        if (velocity.y != 0.0F) {
            velocity.y = Maths.coserp(velocity.y, 0.0F, friction);
        }

        position.x += velocity.x;
        position.y += velocity.y;
    }

    private void moveTowards(Vector2f vector2f) {
        velocity.x += vector2f.x - position.x;
        velocity.y += vector2f.y - position.y;
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
