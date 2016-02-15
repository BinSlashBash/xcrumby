/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.ad;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.ea;
import org.json.JSONObject;

public class ae
implements ad {
    private final dz lC;

    public ae(Context context, dx dx2) {
        this.lC = dz.a(context, new ak(), false, false, null, dx2);
    }

    @Override
    public void a(final ad.a a2) {
        this.lC.bI().a(new ea.a(){

            @Override
            public void a(dz dz2) {
                a2.ay();
            }
        });
    }

    @Override
    public void a(String string2, bb bb2) {
        this.lC.bI().a(string2, bb2);
    }

    @Override
    public void a(String string2, JSONObject jSONObject) {
        this.lC.a(string2, jSONObject);
    }

    @Override
    public void d(String string2) {
        this.lC.loadUrl(string2);
    }

    @Override
    public void e(String string2) {
        this.lC.bI().a(string2, null);
    }

}

