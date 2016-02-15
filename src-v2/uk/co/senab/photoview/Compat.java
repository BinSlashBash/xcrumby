/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 */
package uk.co.senab.photoview;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

public class Compat {
    private static final int SIXTY_FPS_INTERVAL = 16;

    public static int getPointerIndex(int n2) {
        if (Build.VERSION.SDK_INT >= 11) {
            return Compat.getPointerIndexHoneyComb(n2);
        }
        return Compat.getPointerIndexEclair(n2);
    }

    @TargetApi(value=5)
    private static int getPointerIndexEclair(int n2) {
        return (65280 & n2) >> 8;
    }

    @TargetApi(value=11)
    private static int getPointerIndexHoneyComb(int n2) {
        return (65280 & n2) >> 8;
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            Compat.postOnAnimationJellyBean(view, runnable);
            return;
        }
        view.postDelayed(runnable, 16);
    }

    @TargetApi(value=16)
    private static void postOnAnimationJellyBean(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }
}

