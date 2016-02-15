/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.router;

public class IndexField<T> {
    public final T defaultValue;
    public final String key;
    public final String name;

    public IndexField(String string2, String string3, T t2) {
        this.key = string2;
        this.name = string3;
        this.defaultValue = t2;
    }

    public IndexField<T> differentDefaultValue(T t2) {
        return new IndexField<T>(this.key, this.name, t2);
    }
}

