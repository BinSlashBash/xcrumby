/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.appstate;

import com.google.android.gms.appstate.AppState;
import com.google.android.gms.internal.fo;

public final class a
implements AppState {
    private final int wr;
    private final String ws;
    private final byte[] wt;
    private final boolean wu;
    private final String wv;
    private final byte[] ww;

    public a(AppState appState) {
        this.wr = appState.getKey();
        this.ws = appState.getLocalVersion();
        this.wt = appState.getLocalData();
        this.wu = appState.hasConflict();
        this.wv = appState.getConflictVersion();
        this.ww = appState.getConflictData();
    }

    static int a(AppState appState) {
        return fo.hashCode(appState.getKey(), appState.getLocalVersion(), appState.getLocalData(), appState.hasConflict(), appState.getConflictVersion(), appState.getConflictData());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(AppState appState, Object object) {
        boolean bl2 = true;
        if (!(object instanceof AppState)) {
            return false;
        }
        boolean bl3 = bl2;
        if (appState == object) return bl3;
        if (!fo.equal((object = (AppState)object).getKey(), appState.getKey())) return false;
        if (!fo.equal(object.getLocalVersion(), appState.getLocalVersion())) return false;
        if (!fo.equal(object.getLocalData(), appState.getLocalData())) return false;
        if (!fo.equal(object.hasConflict(), appState.hasConflict())) return false;
        if (!fo.equal(object.getConflictVersion(), appState.getConflictVersion())) return false;
        bl3 = bl2;
        if (fo.equal(object.getConflictData(), appState.getConflictData())) return bl3;
        return false;
    }

    static String b(AppState appState) {
        return fo.e(appState).a("Key", appState.getKey()).a("LocalVersion", appState.getLocalVersion()).a("LocalData", appState.getLocalData()).a("HasConflict", appState.hasConflict()).a("ConflictVersion", appState.getConflictVersion()).a("ConflictData", appState.getConflictData()).toString();
    }

    public AppState dt() {
        return this;
    }

    public boolean equals(Object object) {
        return a.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.dt();
    }

    @Override
    public byte[] getConflictData() {
        return this.ww;
    }

    @Override
    public String getConflictVersion() {
        return this.wv;
    }

    @Override
    public int getKey() {
        return this.wr;
    }

    @Override
    public byte[] getLocalData() {
        return this.wt;
    }

    @Override
    public String getLocalVersion() {
        return this.ws;
    }

    @Override
    public boolean hasConflict() {
        return this.wu;
    }

    public int hashCode() {
        return a.a(this);
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return a.b(this);
    }
}

