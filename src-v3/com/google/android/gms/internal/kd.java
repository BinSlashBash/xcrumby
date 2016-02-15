package com.google.android.gms.internal;

import com.google.android.gms.wearable.C1103d;

public class kd implements C1103d {
    private final String Xy;
    private final String wp;

    public kd(C1103d c1103d) {
        this.wp = c1103d.getId();
        this.Xy = c1103d.mc();
    }

    public /* synthetic */ Object freeze() {
        return mf();
    }

    public String getId() {
        return this.wp;
    }

    public boolean isDataValid() {
        return true;
    }

    public String mc() {
        return this.Xy;
    }

    public C1103d mf() {
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataItemAssetEntity[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(hashCode()));
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
