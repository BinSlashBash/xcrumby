package com.google.android.gms.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class ci extends aj {
    private static final String ID;
    private final Context mContext;

    static {
        ID = C0321a.RESOLUTION.toString();
    }

    public ci(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2499x(Map<String, C1367a> map) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        return dh.m1471r(i + "x" + displayMetrics.heightPixels);
    }
}
