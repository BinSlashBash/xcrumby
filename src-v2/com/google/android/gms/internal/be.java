/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.SystemClock
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.cc;
import com.google.android.gms.internal.cg;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dz;
import java.util.Map;

public final class be
implements bb {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int a(DisplayMetrics displayMetrics, Map<String, String> object, String string2, int n2) {
        object = (String)object.get(string2);
        int n3 = n2;
        if (object == null) return n3;
        try {
            return dv.a(displayMetrics, Integer.parseInt((String)object));
        }
        catch (NumberFormatException numberFormatException) {
            dw.z("Could not parse " + string2 + " in a video GMSG: " + (String)object);
            return n2;
        }
    }

    @Override
    public void b(dz object, Map<String, String> map) {
        String string2 = map.get("action");
        if (string2 == null) {
            dw.z("Action missing from video GMSG.");
            return;
        }
        Object object2 = object.bH();
        if (object2 == null) {
            dw.z("Could not get ad overlay for a video GMSG.");
            return;
        }
        boolean bl2 = "new".equalsIgnoreCase(string2);
        boolean bl3 = "position".equalsIgnoreCase(string2);
        if (bl2 || bl3) {
            object = object.getContext().getResources().getDisplayMetrics();
            int n2 = be.a((DisplayMetrics)object, map, "x", 0);
            int n3 = be.a((DisplayMetrics)object, map, "y", 0);
            int n4 = be.a((DisplayMetrics)object, map, "w", -1);
            int n5 = be.a((DisplayMetrics)object, map, "h", -1);
            if (bl2 && object2.aK() == null) {
                object2.c(n2, n3, n4, n5);
                return;
            }
            object2.b(n2, n3, n4, n5);
            return;
        }
        if ((object2 = object2.aK()) == null) {
            cg.a((dz)((Object)object), "no_video_view", null);
            return;
        }
        if ("click".equalsIgnoreCase(string2)) {
            object = object.getContext().getResources().getDisplayMetrics();
            int n6 = be.a((DisplayMetrics)object, map, "x", 0);
            int n7 = be.a((DisplayMetrics)object, map, "y", 0);
            long l2 = SystemClock.uptimeMillis();
            object = MotionEvent.obtain((long)l2, (long)l2, (int)0, (float)n6, (float)n7, (int)0);
            object2.b((MotionEvent)object);
            object.recycle();
            return;
        }
        if ("controls".equalsIgnoreCase(string2)) {
            object = map.get("enabled");
            if (object == null) {
                dw.z("Enabled parameter missing from controls video GMSG.");
                return;
            }
            object2.k(Boolean.parseBoolean((String)object));
            return;
        }
        if ("currentTime".equalsIgnoreCase(string2)) {
            object = map.get("time");
            if (object == null) {
                dw.z("Time parameter missing from currentTime video GMSG.");
                return;
            }
            try {
                object2.seekTo((int)(Float.parseFloat((String)object) * 1000.0f));
                return;
            }
            catch (NumberFormatException var2_3) {
                dw.z("Could not parse time parameter from currentTime video GMSG: " + (String)object);
                return;
            }
        }
        if ("hide".equalsIgnoreCase(string2)) {
            object2.setVisibility(4);
            return;
        }
        if ("load".equalsIgnoreCase(string2)) {
            object2.aU();
            return;
        }
        if ("pause".equalsIgnoreCase(string2)) {
            object2.pause();
            return;
        }
        if ("play".equalsIgnoreCase(string2)) {
            object2.play();
            return;
        }
        if ("show".equalsIgnoreCase(string2)) {
            object2.setVisibility(0);
            return;
        }
        if ("src".equalsIgnoreCase(string2)) {
            object2.o(map.get("src"));
            return;
        }
        dw.z("Unknown video action: " + string2);
    }
}

