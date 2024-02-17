package com.harleylizard.sandbox.entity;

import com.harleylizard.sandbox.input.Keyboard;
import com.harleylizard.sandbox.math.BoundingBox;
import com.harleylizard.sandbox.math.Maths;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.world.World;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public final class Player implements Entity {
    private static final BoundingBox BOUNDING_BOX = new BoundingBox(0.0F, 0.0F, 0.5F, 0.5F);

    private final BoundingBox boundingBox = new BoundingBox(-0.5F, -0.5F, 0.5F, 0.5F);

    private final Vector2f position = new Vector2f();

    private final Vector2f velocity = new Vector2f();

    private final float speed = 0.25F;

    public void stepWithInput(World world, Keyboard keyboard) {
        if (keyboard.isPressed(GLFW_KEY_A)) {
            velocity.x -= speed;
        }
        if (keyboard.isPressed(GLFW_KEY_D)) {
            velocity.x += speed;
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
        if (!collidesX(world)) {
            position.x += velocity.x;
        }
        if (!collidesY(world)) {
            position.y += velocity.y;
        }
    }

    private boolean collidesX(World world) {
        var boundingBox = this.boundingBox.move(position);
        return false;
    }

    private boolean collidesY(World world) {
        var boundingBox = this.boundingBox.move(position);
        return false;
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
