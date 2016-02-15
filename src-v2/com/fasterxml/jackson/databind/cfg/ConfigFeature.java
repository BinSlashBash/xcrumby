/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.cfg;

public interface ConfigFeature {
    public boolean enabledByDefault();

    public int getMask();
}

