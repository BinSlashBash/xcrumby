package com.google.android.gms.internal;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public final class dv {
    public static final Handler rp;

    static {
        rp = new Handler(Looper.getMainLooper());
    }

    public static int m808a(Context context, int i) {
        return m809a(context.getResources().getDisplayMetrics(), i);
    }

    public static int m809a(DisplayMetrics displayMetrics, int i) {
        return (int) TypedValue.applyDimension(1, (float) i, displayMetrics);
    }

    public static void m810a(ViewGroup viewGroup, ak akVar, String str) {
        m811a(viewGroup, akVar, str, ViewCompat.MEASURED_STATE_MASK, -1);
    }

    private static void m811a(ViewGroup viewGroup, ak akVar, String str, int i, int i2) {
        if (viewGroup.getChildCount() == 0) {
            Context context = viewGroup.getContext();
            View textView = new TextView(context);
            textView.setGravity(17);
            textView.setText(str);
            textView.setTextColor(i);
            textView.setBackgroundColor(i2);
            View frameLayout = new FrameLayout(context);
            frameLayout.setBackgroundColor(i);
            int a = m808a(context, 3);
            frameLayout.addView(textView, new LayoutParams(akVar.widthPixels - a, akVar.heightPixels - a, 17));
            viewGroup.addView(frameLayout, akVar.widthPixels, akVar.heightPixels);
        }
    }

    public static void m812a(ViewGroup viewGroup, ak akVar, String str, String str2) {
        dw.m823z(str2);
        m811a(viewGroup, akVar, str, SupportMenu.CATEGORY_MASK, ViewCompat.MEASURED_STATE_MASK);
    }

    public static boolean bC() {
        return Build.DEVICE.startsWith("generic");
    }

    public static boolean bD() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static String m813l(Context context) {
        String string = Secure.getString(context.getContentResolver(), "android_id");
        if (string == null || bC()) {
            string = "emulator";
        }
        return m814u(string);
    }

    public static String m814u(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest.getInstance(MessageDigestAlgorithms.MD5).update(str.getBytes());
                return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, r1.digest())});
            } catch (NoSuchAlgorithmException e) {
                i++;
            }
        }
        return null;
    }
}
