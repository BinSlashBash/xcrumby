/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Handler
 *  android.os.Looper
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.TextView
 */
package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.dw;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public final class dv {
    public static final Handler rp = new Handler(Looper.getMainLooper());

    public static int a(Context context, int n2) {
        return dv.a(context.getResources().getDisplayMetrics(), n2);
    }

    public static int a(DisplayMetrics displayMetrics, int n2) {
        return (int)TypedValue.applyDimension((int)1, (float)n2, (DisplayMetrics)displayMetrics);
    }

    public static void a(ViewGroup viewGroup, ak ak2, String string2) {
        dv.a(viewGroup, ak2, string2, -16777216, -1);
    }

    private static void a(ViewGroup viewGroup, ak ak2, String string2, int n2, int n3) {
        if (viewGroup.getChildCount() != 0) {
            return;
        }
        Context context = viewGroup.getContext();
        TextView textView = new TextView(context);
        textView.setGravity(17);
        textView.setText((CharSequence)string2);
        textView.setTextColor(n2);
        textView.setBackgroundColor(n3);
        string2 = new FrameLayout(context);
        string2.setBackgroundColor(n2);
        n2 = dv.a(context, 3);
        string2.addView((View)textView, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(ak2.widthPixels - n2, ak2.heightPixels - n2, 17));
        viewGroup.addView((View)string2, ak2.widthPixels, ak2.heightPixels);
    }

    public static void a(ViewGroup viewGroup, ak ak2, String string2, String string3) {
        dw.z(string3);
        dv.a(viewGroup, ak2, string2, -65536, -16777216);
    }

    public static boolean bC() {
        return Build.DEVICE.startsWith("generic");
    }

    public static boolean bD() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return true;
        }
        return false;
    }

    public static String l(Context object) {
        if ((object = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)"android_id")) == null || dv.bC()) {
            object = "emulator";
        }
        return dv.u((String)object);
    }

    public static String u(String string2) {
        for (int i2 = 0; i2 < 2; ++i2) {
            try {
                Object object = MessageDigest.getInstance("MD5");
                object.update(string2.getBytes());
                object = String.format(Locale.US, "%032X", new BigInteger(1, object.digest()));
                return object;
            }
            catch (NoSuchAlgorithmException var2_2) {
                continue;
            }
        }
        return null;
    }
}

