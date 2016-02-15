/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.do;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dw;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public final class du
extends do {
    private final String lh;
    private final Context mContext;
    private final String ro;

    public du(Context context, String string2, String string3) {
        this.mContext = context;
        this.lh = string2;
        this.ro = string3;
    }

    @Override
    public void aY() {
        HttpURLConnection httpURLConnection;
        block8 : {
            int n2;
            dw.y("Pinging URL: " + this.ro);
            httpURLConnection = (HttpURLConnection)new URL(this.ro).openConnection();
            try {
                dq.a(this.mContext, this.lh, true, httpURLConnection);
                n2 = httpURLConnection.getResponseCode();
                if (n2 >= 200 && n2 < 300) break block8;
            }
            catch (Throwable var3_5) {
                try {
                    httpURLConnection.disconnect();
                    throw var3_5;
                }
                catch (IndexOutOfBoundsException var2_2) {
                    dw.z("Error while parsing ping URL: " + this.ro + ". " + var2_2.getMessage());
                    return;
                }
                catch (IOException var2_3) {
                    dw.z("Error while pinging URL: " + this.ro + ". " + var2_3.getMessage());
                    return;
                }
            }
            dw.z("Received non-success response code " + n2 + " from pinging URL: " + this.ro);
        }
        httpURLConnection.disconnect();
    }

    @Override
    public void onStop() {
    }
}

