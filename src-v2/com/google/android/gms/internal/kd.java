/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.wearable.d;

public class kd
implements d {
    private final String Xy;
    private final String wp;

    public kd(d d2) {
        this.wp = d2.getId();
        this.Xy = d2.mc();
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.mf();
    }

    @Override
    public String getId() {
        return this.wp;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    @Override
    public String mc() {
        return this.Xy;
    }

    public d mf() {
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataItemAssetEntity[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        if (this.wp == null) {
            stringBuilder.append(",noid");
        } else {
            stringBuilder.append(",");
            stringBuilder.append(this.wp);
        }
        stringBuilder.append(", key=");
        stringBuilder.append(this.Xy);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

