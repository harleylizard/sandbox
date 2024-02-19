package com.harleylizard.sandbox.layer;

public sealed interface Palette<T> permits ImmutablePalette, MutablePalette {

    T getObject(int i);

    int getInt(T t);
}
