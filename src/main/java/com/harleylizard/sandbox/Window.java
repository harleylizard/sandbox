package com.harleylizard.sandbox;

import com.harleylizard.sandbox.input.Keyboard;
import com.harleylizard.sandbox.input.Mouse;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

public final class Window {
    private final long window;

    private int width;
    private int height;

    {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_CONTEXT_RELEASE_BEHAVIOR, GLFW_RELEASE_BEHAVIOR_FLUSH);

        if ((window = glfwCreateWindow(854, 480, "Sandbox", NULL, NULL)) == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create a GLFW Window");
        }
        try (var stack = MemoryStack.stackPush()) {
            var buffer = stack.callocInt(2);

            nglfwGetWindowSize(window, memAddress(buffer), memAddress(buffer) + 4);

            width = buffer.get(0);
            height = buffer.get(1);
        }

        glfwSetWindowSizeCallback(window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            glViewport(0, 0, width, height);
        });

        glfwMakeContextCurrent(window);

        GL.createCapabilities();
    }

    public void step() {
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public float getAspectRatio() {
        return (float) width / (float) height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setInput(Keyboard keyboard, Mouse mouse) {
        glfwSetKeyCallback(window, keyboard::setKeyCallback);
        glfwSetMouseButtonCallback(window, mouse::setButtonCallback);
        glfwSetCursorPosCallback(window, mouse::setCursorCallback);
    }

    public void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }
}
