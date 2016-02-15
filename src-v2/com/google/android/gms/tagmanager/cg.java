/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class cg
extends aj {
    private static final String ID = a.ae.toString();
    private static final String YZ = b.bi.toString();
    private static final String Za = b.bj.toString();
    private static final String Zb = b.cF.toString();
    private static final String Zc = b.cz.toString();

    public cg() {
        super(ID, YZ, Za);
    }

    @Override
    public boolean jX() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public d.a x(Map<String, d.a> object) {
        block12 : {
            int n2;
            Object object2 = (d.a)object.get(YZ);
            d.a a2 = (d.a)object.get(Za);
            if (object2 == null) return dh.lT();
            if (object2 == dh.lT()) return dh.lT();
            if (a2 == null) return dh.lT();
            if (a2 == dh.lT()) {
                return dh.lT();
            }
            int n3 = 64;
            if (dh.n((d.a)object.get(Zb)).booleanValue()) {
                n3 = 66;
            }
            if ((object = (d.a)object.get(Zc)) != null) {
                int n4;
                if ((object = dh.l((d.a)object)) == dh.lO()) {
                    return dh.lT();
                }
                n2 = n4 = object.intValue();
                if (n4 < 0) {
                    return dh.lT();
                }
            } else {
                n2 = 1;
            }
            try {
                object = dh.j((d.a)object2);
                object2 = dh.j(a2);
                a2 = null;
            }
            catch (PatternSyntaxException var1_2) {
                return dh.lT();
            }
            object2 = Pattern.compile((String)object2, n3).matcher((CharSequence)object);
            object = a2;
            if (!object2.find()) break block12;
            object = a2;
            if (object2.groupCount() < n2) break block12;
            object = object2.group(n2);
        }
        if (object != null) return dh.r(object);
        return dh.lT();
    }
}

