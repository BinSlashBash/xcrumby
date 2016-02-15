/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.wearable.c;
import com.google.android.gms.wearable.d;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class kf
implements c {
    private byte[] Nf;
    private Map<String, d> adD;
    private Set<String> adE;
    private Uri mUri;

    public kf(c c2) {
        this.mUri = c2.getUri();
        this.Nf = c2.getData();
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, d> entry : c2.ma().entrySet()) {
            if (entry.getKey() == null) continue;
            hashMap.put(entry.getKey(), entry.getValue().freeze());
        }
        this.adD = Collections.unmodifiableMap(hashMap);
        this.adE = Collections.unmodifiableSet(c2.mb());
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.mg();
    }

    @Override
    public byte[] getData() {
        return this.Nf;
    }

    @Override
    public Uri getUri() {
        return this.mUri;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    @Override
    public Map<String, d> ma() {
        return this.adD;
    }

    @Deprecated
    @Override
    public Set<String> mb() {
        return this.adE;
    }

    public c mg() {
        return this;
    }

    public String toString() {
        return this.toString(Log.isLoggable((String)"DataItem", (int)3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString(boolean bl2) {
        StringBuilder stringBuilder = new StringBuilder("DataItemEntity[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        StringBuilder stringBuilder2 = new StringBuilder().append(",dataSz=");
        Object object = this.Nf == null ? "null" : Integer.valueOf(this.Nf.length);
        stringBuilder.append(stringBuilder2.append(object).toString());
        stringBuilder.append(", numAssets=" + this.adD.size());
        stringBuilder.append(", uri=" + (Object)this.mUri);
        if (!bl2) {
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        stringBuilder.append("\n  tags=[");
        object = this.adE.iterator();
        boolean bl3 = false;
        while (object.hasNext()) {
            String string2 = object.next();
            if (bl3) {
                stringBuilder.append(", ");
            } else {
                bl3 = true;
            }
            stringBuilder.append(string2);
        }
        stringBuilder.append("]\n  assets: ");
        object = this.adD.keySet().iterator();
        do {
            if (!object.hasNext()) {
                stringBuilder.append("\n  ]");
                return stringBuilder.toString();
            }
            String string3 = object.next();
            stringBuilder.append("\n    " + string3 + ": " + this.adD.get(string3));
        } while (true);
    }
}

