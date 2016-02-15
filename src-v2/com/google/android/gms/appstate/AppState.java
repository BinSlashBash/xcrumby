/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.appstate;

import com.google.android.gms.common.data.Freezable;

public interface AppState
extends Freezable<AppState> {
    public byte[] getConflictData();

    public String getConflictVersion();

    public int getKey();

    public byte[] getLocalData();

    public String getLocalVersion();

    public boolean hasConflict();
}

