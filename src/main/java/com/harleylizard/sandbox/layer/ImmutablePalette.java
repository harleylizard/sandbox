package com.harleylizard.sandbox.layer;

public final class ImmutablePalette<T> implements Palette<T> {
    private final Palette<T> palette;

    public ImmutablePalette(Palette<T> palette) {
        this.palette = palette;
    }

    @Override
    public T getObject(int i) {
        return palette.getObject(i);
    }

    @Override
    public int getInt(T t) {
        return palette.getInt(t);
    }
}
