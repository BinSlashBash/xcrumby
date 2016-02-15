/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.FieldAttributes;

public interface ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> var1);

    public boolean shouldSkipField(FieldAttributes var1);
}

