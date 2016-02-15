package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class ch extends dc {
    private static final String ID;
    private static final String Zb;

    static {
        ID = C0321a.REGEX.toString();
        Zb = C0325b.IGNORE_CASE.toString();
    }

    public ch() {
        super(ID);
    }

    protected boolean m3445a(String str, String str2, Map<String, C1367a> map) {
        try {
            return Pattern.compile(str2, dh.m1466n((C1367a) map.get(Zb)).booleanValue() ? 66 : 64).matcher(str).find();
        } catch (PatternSyntaxException e) {
            return false;
        }
    }
}
