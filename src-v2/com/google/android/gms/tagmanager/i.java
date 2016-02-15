/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.net.Uri
 *  android.net.Uri$Builder
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aq;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cy;
import com.google.android.gms.tagmanager.df;
import com.google.android.gms.tagmanager.dh;
import com.google.android.gms.tagmanager.y;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class i
extends df {
    private static final String ID = com.google.android.gms.internal.a.ap.toString();
    private static final String URL = b.eo.toString();
    private static final String WC = b.aX.toString();
    private static final String WD = b.en.toString();
    static final String WE = "gtm_" + ID + "_unrepeatable";
    private static final Set<String> WF = new HashSet<String>();
    private final a WG;
    private final Context mContext;

    public i(Context context) {
        this(context, new a(){

            @Override
            public aq jY() {
                return y.F(Context.this);
            }
        });
    }

    i(Context context, a a2) {
        super(ID, URL);
        this.WG = a2;
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean bj(String string2) {
        boolean bl2 = true;
        synchronized (this) {
            boolean bl3 = this.bl(string2);
            if (bl3) return bl2;
            if (!this.bk(string2)) return false;
            WF.add(string2);
            return bl2;
        }
    }

    boolean bk(String string2) {
        return this.mContext.getSharedPreferences(WE, 0).contains(string2);
    }

    boolean bl(String string2) {
        return WF.contains(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void z(Map<String, d.a> iterator) {
        String string2 = iterator.get(WD) != null ? dh.j((d.a)iterator.get(WD)) : null;
        if (string2 != null && this.bj(string2)) {
            return;
        }
        Uri.Builder builder = Uri.parse((String)dh.j((d.a)iterator.get(URL))).buildUpon();
        if ((iterator = (d.a)iterator.get(WC)) != null) {
            if (!((iterator = dh.o((d.a)((Object)iterator))) instanceof List)) {
                bh.w("ArbitraryPixel: additional params not a list: not sending partial hit: " + builder.build().toString());
                return;
            }
            iterator = ((List)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                Object e2 = iterator.next();
                if (!(e2 instanceof Map)) {
                    bh.w("ArbitraryPixel: additional params contains non-map: not sending partial hit: " + builder.build().toString());
                    return;
                }
                for (Map.Entry entry : ((Map)e2).entrySet()) {
                    builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        }
        iterator = builder.build().toString();
        this.WG.jY().bz((String)((Object)iterator));
        bh.y("ArbitraryPixel: url = " + iterator);
        if (string2 == null) return;
        // MONITORENTER : com.google.android.gms.tagmanager.i.class
        WF.add(string2);
        cy.a(this.mContext, WE, string2, "true");
        // MONITOREXIT : com.google.android.gms.tagmanager.i.class
    }

    public static interface a {
        public aq jY();
    }

}

