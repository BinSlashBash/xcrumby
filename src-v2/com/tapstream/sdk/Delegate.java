/*
 * Decompiled with CFR 0_110.
 */
package com.tapstream.sdk;

interface Delegate {
    public int getDelay();

    public boolean isRetryAllowed();

    public void setDelay(int var1);
}

