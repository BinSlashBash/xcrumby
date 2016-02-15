/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.plus.internal;

import android.content.Context;
import com.google.android.gms.plus.internal.PlusCommonExtras;
import com.google.android.gms.plus.internal.h;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class i {
    private String[] Um;
    private String Un;
    private String Uo;
    private String Up;
    private PlusCommonExtras Ur;
    private final ArrayList<String> Us = new ArrayList();
    private String[] Ut;
    private String wG;

    public i(Context context) {
        this.Uo = context.getPackageName();
        this.Un = context.getPackageName();
        this.Ur = new PlusCommonExtras();
        this.Us.add("https://www.googleapis.com/auth/plus.login");
    }

    public i bh(String string2) {
        this.wG = string2;
        return this;
    }

    public /* varargs */ i e(String ... arrstring) {
        this.Us.clear();
        this.Us.addAll(Arrays.asList(arrstring));
        return this;
    }

    public /* varargs */ i f(String ... arrstring) {
        this.Ut = arrstring;
        return this;
    }

    public i iY() {
        this.Us.clear();
        return this;
    }

    public h iZ() {
        if (this.wG == null) {
            this.wG = "<<default account>>";
        }
        String[] arrstring = this.Us.toArray(new String[this.Us.size()]);
        return new h(this.wG, arrstring, this.Ut, this.Um, this.Un, this.Uo, this.Up, this.Ur);
    }
}

