package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import java.io.File;

class ak {
    static boolean m1367G(String str) {
        if (version() < 9) {
            return false;
        }
        File file = new File(str);
        file.setReadable(false, false);
        file.setWritable(false, false);
        file.setReadable(true, true);
        file.setWritable(true, true);
        return true;
    }

    public static int version() {
        try {
            return Integer.parseInt(VERSION.SDK);
        } catch (NumberFormatException e) {
            bh.m1384w("Invalid version number: " + VERSION.SDK);
            return 0;
        }
    }
}
