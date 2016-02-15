/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.view.MotionEvent
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;
import com.google.android.gms.internal.g;
import com.google.android.gms.internal.h;
import com.google.android.gms.internal.m;

public class l {
    private String kd = "googleads.g.doubleclick.net";
    private String ke = "/pagead/ads";
    private String[] kf = new String[]{".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};
    private h kg;
    private final g kh = new g();

    public l(h h2) {
        this.kg = h2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Uri a(Uri uri, Context object, String string2, boolean bl2) throws m {
        try {
            if (uri.getQueryParameter("ms") != null) {
                throw new m("Query parameter already exists: ms");
            }
            if (bl2) {
                object = this.kg.a((Context)object, string2);
                return this.a(uri, "ms", (String)object);
            }
        }
        catch (UnsupportedOperationException var1_2) {
            throw new m("Provided Uri is not in a valid state");
        }
        object = this.kg.a((Context)object);
        return this.a(uri, "ms", (String)object);
    }

    private Uri a(Uri uri, String string2, String string3) throws UnsupportedOperationException {
        int n2;
        String string4 = uri.toString();
        int n3 = n2 = string4.indexOf("&adurl");
        if (n2 == -1) {
            n3 = string4.indexOf("?adurl");
        }
        if (n3 != -1) {
            return Uri.parse((String)(string4.substring(0, n3 + 1) + string2 + "=" + string3 + "&" + string4.substring(n3 + 1)));
        }
        return uri.buildUpon().appendQueryParameter(string2, string3).build();
    }

    public Uri a(Uri uri, Context context) throws m {
        try {
            uri = this.a(uri, context, uri.getQueryParameter("ai"), true);
            return uri;
        }
        catch (UnsupportedOperationException var1_2) {
            throw new m("Provided Uri is not in a valid state");
        }
    }

    public void a(MotionEvent motionEvent) {
        this.kg.a(motionEvent);
    }

    public boolean a(Uri object) {
        String[] arrstring;
        int n2;
        int n3;
        boolean bl2 = false;
        if (object == null) {
            throw new NullPointerException();
        }
        try {
            object = object.getHost();
            arrstring = this.kf;
            n3 = arrstring.length;
            n2 = 0;
        }
        catch (NullPointerException var1_2) {
            return false;
        }
        do {
            block6 : {
                boolean bl3 = bl2;
                if (n2 < n3) {
                    bl3 = object.endsWith(arrstring[n2]);
                    if (!bl3) break block6;
                    bl3 = true;
                }
                return bl3;
            }
            ++n2;
        } while (true);
    }

    public h y() {
        return this.kg;
    }
}

