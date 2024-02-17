package com.harleylizard.sandbox.math;

public final class Maths {

    private Maths() {}

    public static float coserp(float a, float b, float t) {
        var ft = t * (float) Math.PI;
        var f = (1 - (float) Math.cos(ft)) * 0.5f;
        return a * (1 - f) + b * f;
    }

    public static float clamp(float f, float min, float max) {
        return Math.min(Math.max(f, min), max);
    }
}
