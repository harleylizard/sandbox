package com.harleylizard.sandbox.column;

public interface Palette<T> {

    T getObject(int i);

    int getInt(T t);
}
