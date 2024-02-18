package com.harleylizard.sandbox.layer;

public interface Palette<T> {

    T getObject(int i);

    int getInt(T t);
}
