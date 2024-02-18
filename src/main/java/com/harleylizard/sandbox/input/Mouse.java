package com.harleylizard.sandbox.input;

import com.harleylizard.sandbox.Window;
import it.unimi.dsi.fastutil.ints.Int2BooleanArrayMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class Mouse {
    private final Int2BooleanMap map = new Int2BooleanArrayMap();

    private double x;
    private double y;

    public boolean isPressed(int button) {
        return map.get(button);
    }

    public Vector3f getWorldSpace(Window window, Matrix4f projection, Matrix4f view, Matrix4f model) {
        var normalisedX = 2.0D * x / (double) window.getWidth() - 1.0D;
        var normalisedY = 1.0D - 2.0D * y / (double) window.getHeight() - 1.0D;

        var vector4f = new Vector4f((float) normalisedX, (float) normalisedY, -1.0F, 1.0F);

        projection.invert(new Matrix4f()).transform(vector4f);
        vector4f.z = -1.0F;
        vector4f.w = 0.0F;

        view.invert(new Matrix4f()).transform(vector4f);

        model.invert(new Matrix4f()).transform(vector4f);

        return new Vector3f(vector4f.x, vector4f.y, vector4f.z);
    }

    public void setButtonCallback(long window, int button, int action, int mods) {
        map.put(button, action != GLFW_RELEASE);
    }

    public void setCursorCallback(long window, double x, double y) {
        this.x = x;
        this.y = y;
    }
}
