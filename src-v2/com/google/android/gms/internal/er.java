/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;

public class er {
    private static boolean zC = false;
    private final String mTag;
    private boolean zD;
    private boolean zE;
    private String zF;

    public er(String string2) {
        this(string2, er.dR());
    }

    public er(String string2, boolean bl2) {
        this.mTag = string2;
        this.zD = bl2;
        this.zE = false;
    }

    public static boolean dR() {
        return zC;
    }

    private /* varargs */ String e(String object, Object ... object2) {
        object = object2 = String.format((String)object, (Object[])object2);
        if (!TextUtils.isEmpty((CharSequence)this.zF)) {
            object = this.zF + (String)object2;
        }
        return object;
    }

    public /* varargs */ void a(String string2, Object ... arrobject) {
        if (this.dQ()) {
            Log.v((String)this.mTag, (String)this.e(string2, arrobject));
        }
    }

    public /* varargs */ void a(Throwable throwable, String string2, Object ... arrobject) {
        if (this.dP() || zC) {
            Log.d((String)this.mTag, (String)this.e(string2, arrobject), (Throwable)throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void ab(String string2) {
        string2 = TextUtils.isEmpty((CharSequence)string2) ? null : String.format("[%s] ", string2);
        this.zF = string2;
    }

    public /* varargs */ void b(String string2, Object ... arrobject) {
        if (this.dP() || zC) {
            Log.d((String)this.mTag, (String)this.e(string2, arrobject));
        }
    }

    public /* varargs */ void c(String string2, Object ... arrobject) {
        Log.i((String)this.mTag, (String)this.e(string2, arrobject));
    }

    public /* varargs */ void d(String string2, Object ... arrobject) {
        Log.w((String)this.mTag, (String)this.e(string2, arrobject));
    }

    public boolean dP() {
        return this.zD;
    }

    public boolean dQ() {
        return this.zE;
    }
}

