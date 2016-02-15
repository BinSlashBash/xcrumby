package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;

/* renamed from: com.google.android.gms.internal.l */
public class C0410l {
    private String kd;
    private String ke;
    private String[] kf;
    private C0401h kg;
    private final C0399g kh;

    public C0410l(C0401h c0401h) {
        this.kd = "googleads.g.doubleclick.net";
        this.ke = "/pagead/ads";
        this.kf = new String[]{".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};
        this.kh = new C0399g();
        this.kg = c0401h;
    }

    private Uri m1178a(Uri uri, Context context, String str, boolean z) throws C0411m {
        try {
            if (uri.getQueryParameter("ms") != null) {
                throw new C0411m("Query parameter already exists: ms");
            }
            return m1179a(uri, "ms", z ? this.kg.m1041a(context, str) : this.kg.m1040a(context));
        } catch (UnsupportedOperationException e) {
            throw new C0411m("Provided Uri is not in a valid state");
        }
    }

    private Uri m1179a(Uri uri, String str, String str2) throws UnsupportedOperationException {
        String uri2 = uri.toString();
        int indexOf = uri2.indexOf("&adurl");
        if (indexOf == -1) {
            indexOf = uri2.indexOf("?adurl");
        }
        return indexOf != -1 ? Uri.parse(new StringBuilder(uri2.substring(0, indexOf + 1)).append(str).append("=").append(str2).append("&").append(uri2.substring(indexOf + 1)).toString()) : uri.buildUpon().appendQueryParameter(str, str2).build();
    }

    public Uri m1180a(Uri uri, Context context) throws C0411m {
        try {
            return m1178a(uri, context, uri.getQueryParameter("ai"), true);
        } catch (UnsupportedOperationException e) {
            throw new C0411m("Provided Uri is not in a valid state");
        }
    }

    public void m1181a(MotionEvent motionEvent) {
        this.kg.m1043a(motionEvent);
    }

    public boolean m1182a(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        try {
            String host = uri.getHost();
            for (String endsWith : this.kf) {
                if (host.endsWith(endsWith)) {
                    return true;
                }
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public C0401h m1183y() {
        return this.kg;
    }
}
