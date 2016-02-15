package com.google.android.gms.tagmanager;

import android.os.Build;
import android.support.v4.os.EnvironmentCompat;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class aa extends aj {
    private static final String ID;

    static {
        ID = C0321a.DEVICE_NAME.toString();
    }

    public aa() {
        super(ID, new String[0]);
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2444x(Map<String, C1367a> map) {
        String str = Build.MANUFACTURER;
        Object obj = Build.MODEL;
        if (!(obj.startsWith(str) || str.equals(EnvironmentCompat.MEDIA_UNKNOWN))) {
            obj = str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + obj;
        }
        return dh.m1471r(obj);
    }
}
