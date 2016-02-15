package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class cg extends aj {
    private static final String ID;
    private static final String YZ;
    private static final String Za;
    private static final String Zb;
    private static final String Zc;

    static {
        ID = C0321a.REGEX_GROUP.toString();
        YZ = C0325b.ARG0.toString();
        Za = C0325b.ARG1.toString();
        Zb = C0325b.IGNORE_CASE.toString();
        Zc = C0325b.GROUP.toString();
    }

    public cg() {
        super(ID, YZ, Za);
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2498x(Map<String, C1367a> map) {
        C1367a c1367a = (C1367a) map.get(YZ);
        C1367a c1367a2 = (C1367a) map.get(Za);
        if (c1367a == null || c1367a == dh.lT() || c1367a2 == null || c1367a2 == dh.lT()) {
            return dh.lT();
        }
        int intValue;
        int i = 64;
        if (dh.m1466n((C1367a) map.get(Zb)).booleanValue()) {
            i = 66;
        }
        C1367a c1367a3 = (C1367a) map.get(Zc);
        if (c1367a3 != null) {
            Long l = dh.m1462l(c1367a3);
            if (l == dh.lO()) {
                return dh.lT();
            }
            intValue = l.intValue();
            if (intValue < 0) {
                return dh.lT();
            }
        }
        intValue = 1;
        try {
            CharSequence j = dh.m1460j(c1367a);
            Object obj = null;
            Matcher matcher = Pattern.compile(dh.m1460j(c1367a2), i).matcher(j);
            if (matcher.find() && matcher.groupCount() >= intValue) {
                obj = matcher.group(intValue);
            }
            return obj == null ? dh.lT() : dh.m1471r(obj);
        } catch (PatternSyntaxException e) {
            return dh.lT();
        }
    }
}
