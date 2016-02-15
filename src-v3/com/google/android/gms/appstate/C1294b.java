package com.google.android.gms.appstate;

import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;

/* renamed from: com.google.android.gms.appstate.b */
public final class C1294b extends C0251b implements AppState {
    C1294b(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    public AppState dt() {
        return new C1293a(this);
    }

    public boolean equals(Object obj) {
        return C1293a.m2634a(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return dt();
    }

    public byte[] getConflictData() {
        return getByteArray("conflict_data");
    }

    public String getConflictVersion() {
        return getString("conflict_version");
    }

    public int getKey() {
        return getInteger("key");
    }

    public byte[] getLocalData() {
        return getByteArray("local_data");
    }

    public String getLocalVersion() {
        return getString("local_version");
    }

    public boolean hasConflict() {
        return !ai("conflict_version");
    }

    public int hashCode() {
        return C1293a.m2633a(this);
    }

    public String toString() {
        return C1293a.m2635b(this);
    }
}
