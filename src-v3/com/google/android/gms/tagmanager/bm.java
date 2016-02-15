package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;

class bm {
    bm() {
    }

    int jZ() {
        return VERSION.SDK_INT;
    }

    public bl kH() {
        return jZ() < 8 ? new av() : new aw();
    }
}
