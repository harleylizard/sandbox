package com.harleylizard.sandbox.input;

import it.unimi.dsi.fastutil.ints.Int2BooleanArrayMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class Keyboard {
    private final Int2BooleanMap map = new Int2BooleanArrayMap();

    public boolean isPressed(int key) {
        return map.get(key);
    }

    public void setKeyCallback(long window, int key, int scancode, int action, int mods) {
        map.put(key, action != GLFW_RELEASE);
    }
}
