package com.crumby.lib.router;

public class IndexField<T> {
    public final T defaultValue;
    public final String key;
    public final String name;

    public IndexField(String key, String name, T value) {
        this.key = key;
        this.name = name;
        this.defaultValue = value;
    }

    public IndexField<T> differentDefaultValue(T defaultValue) {
        return new IndexField(this.key, this.name, defaultValue);
    }
}
