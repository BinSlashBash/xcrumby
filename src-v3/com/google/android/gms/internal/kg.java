package com.google.android.gms.internal;

import android.net.Uri;
import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.C1102c;
import com.google.android.gms.wearable.C1103d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class kg extends C0251b implements C1102c {
    private final int LE;

    public kg(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.LE = i2;
    }

    public /* synthetic */ Object freeze() {
        return mg();
    }

    public byte[] getData() {
        return getByteArray("data");
    }

    public Uri getUri() {
        return Uri.parse(getString("path"));
    }

    public Map<String, C1103d> ma() {
        Map<String, C1103d> hashMap = new HashMap(this.LE);
        for (int i = 0; i < this.LE; i++) {
            ke keVar = new ke(this.BB, this.BD + i);
            if (keVar.mc() != null) {
                hashMap.put(keVar.mc(), keVar);
            }
        }
        return hashMap;
    }

    @Deprecated
    public Set<String> mb() {
        Set hashSet = new HashSet();
        String string = getString("tags");
        if (string != null) {
            for (Object add : string.split("\\|")) {
                hashSet.add(add);
            }
        }
        return hashSet;
    }

    public C1102c mg() {
        return new kf(this);
    }
}
