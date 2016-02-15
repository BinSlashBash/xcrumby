/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.internal;

import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.internal.ke;
import com.google.android.gms.internal.kf;
import com.google.android.gms.wearable.c;
import com.google.android.gms.wearable.d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class kg
extends b
implements c {
    private final int LE;

    public kg(DataHolder dataHolder, int n2, int n3) {
        super(dataHolder, n2);
        this.LE = n3;
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.mg();
    }

    @Override
    public byte[] getData() {
        return this.getByteArray("data");
    }

    @Override
    public Uri getUri() {
        return Uri.parse((String)this.getString("path"));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Map<String, d> ma() {
        HashMap<String, d> hashMap = new HashMap<String, d>(this.LE);
        int n2 = 0;
        while (n2 < this.LE) {
            ke ke2 = new ke(this.BB, this.BD + n2);
            if (ke2.mc() != null) {
                hashMap.put(ke2.mc(), ke2);
            }
            ++n2;
        }
        return hashMap;
    }

    @Deprecated
    @Override
    public Set<String> mb() {
        HashSet<String> hashSet = new HashSet<String>();
        String[] arrstring = this.getString("tags");
        if (arrstring != null) {
            arrstring = arrstring.split("\\|");
            int n2 = arrstring.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                hashSet.add(arrstring[i2]);
            }
        }
        return hashSet;
    }

    public c mg() {
        return new kf(this);
    }
}

