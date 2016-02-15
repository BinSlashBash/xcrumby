package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Locale;
import java.util.Map;

class bc extends aj {
    private static final String ID;

    static {
        ID = C0321a.LANGUAGE.toString();
    }

    public bc() {
        super(ID, new String[0]);
    }

    public boolean jX() {
        return false;
    }

    public C1367a m2468x(Map<String, C1367a> map) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return dh.lT();
        }
        String language = locale.getLanguage();
        return language == null ? dh.lT() : dh.m1471r(language.toLowerCase());
    }
}
