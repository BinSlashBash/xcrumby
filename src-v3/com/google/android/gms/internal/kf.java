package com.google.android.gms.internal;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.wearable.C1102c;
import com.google.android.gms.wearable.C1103d;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class kf implements C1102c {
    private byte[] Nf;
    private Map<String, C1103d> adD;
    private Set<String> adE;
    private Uri mUri;

    public kf(C1102c c1102c) {
        this.mUri = c1102c.getUri();
        this.Nf = c1102c.getData();
        Map hashMap = new HashMap();
        for (Entry entry : c1102c.ma().entrySet()) {
            if (entry.getKey() != null) {
                hashMap.put(entry.getKey(), ((C1103d) entry.getValue()).freeze());
            }
        }
        this.adD = Collections.unmodifiableMap(hashMap);
        this.adE = Collections.unmodifiableSet(c1102c.mb());
    }

    public /* synthetic */ Object freeze() {
        return mg();
    }

    public byte[] getData() {
        return this.Nf;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean isDataValid() {
        return true;
    }

    public Map<String, C1103d> ma() {
        return this.adD;
    }

    @Deprecated
    public Set<String> mb() {
        return this.adE;
    }

    public C1102c mg() {
        return this;
    }

    public String toString() {
        return toString(Log.isLoggable("DataItem", 3));
    }

    public String toString(boolean verbose) {
        StringBuilder stringBuilder = new StringBuilder("DataItemEntity[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(hashCode()));
        stringBuilder.append(",dataSz=" + (this.Nf == null ? "null" : Integer.valueOf(this.Nf.length)));
        stringBuilder.append(", numAssets=" + this.adD.size());
        stringBuilder.append(", uri=" + this.mUri);
        if (verbose) {
            stringBuilder.append("\n  tags=[");
            Object obj = null;
            for (String str : this.adE) {
                if (obj != null) {
                    stringBuilder.append(", ");
                } else {
                    obj = 1;
                }
                stringBuilder.append(str);
            }
            stringBuilder.append("]\n  assets: ");
            for (String str2 : this.adD.keySet()) {
                stringBuilder.append("\n    " + str2 + ": " + this.adD.get(str2));
            }
            stringBuilder.append("\n  ]");
            return stringBuilder.toString();
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
