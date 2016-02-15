/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.dc;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class ch
extends dc {
    private static final String ID = a.ag.toString();
    private static final String Zb = b.cF.toString();

    public ch() {
        super(ID);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean a(String string2, String string3, Map<String, d.a> map) {
        int n2 = dh.n(map.get(Zb)) != false ? 66 : 64;
        try {
            return Pattern.compile(string3, n2).matcher(string2).find();
        }
        catch (PatternSyntaxException var1_2) {
            return false;
        }
    }
}

