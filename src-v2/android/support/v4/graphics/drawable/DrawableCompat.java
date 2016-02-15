/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompatHoneycomb;
import android.support.v4.graphics.drawable.DrawableCompatKitKat;

public class DrawableCompat {
    static final DrawableImpl IMPL;

    static {
        int n2 = Build.VERSION.SDK_INT;
        IMPL = n2 >= 19 ? new KitKatDrawableImpl() : (n2 >= 11 ? new HoneycombDrawableImpl() : new BaseDrawableImpl());
    }

    public static boolean isAutoMirrored(Drawable drawable2) {
        return IMPL.isAutoMirrored(drawable2);
    }

    public static void jumpToCurrentState(Drawable drawable2) {
        IMPL.jumpToCurrentState(drawable2);
    }

    public static void setAutoMirrored(Drawable drawable2, boolean bl2) {
        IMPL.setAutoMirrored(drawable2, bl2);
    }

    static class BaseDrawableImpl
    implements DrawableImpl {
        BaseDrawableImpl() {
        }

        @Override
        public boolean isAutoMirrored(Drawable drawable2) {
            return false;
        }

        @Override
        public void jumpToCurrentState(Drawable drawable2) {
        }

        @Override
        public void setAutoMirrored(Drawable drawable2, boolean bl2) {
        }
    }

    static interface DrawableImpl {
        public boolean isAutoMirrored(Drawable var1);

        public void jumpToCurrentState(Drawable var1);

        public void setAutoMirrored(Drawable var1, boolean var2);
    }

    static class HoneycombDrawableImpl
    extends BaseDrawableImpl {
        HoneycombDrawableImpl() {
        }

        @Override
        public void jumpToCurrentState(Drawable drawable2) {
            DrawableCompatHoneycomb.jumpToCurrentState(drawable2);
        }
    }

    static class KitKatDrawableImpl
    extends HoneycombDrawableImpl {
        KitKatDrawableImpl() {
        }

        @Override
        public boolean isAutoMirrored(Drawable drawable2) {
            return DrawableCompatKitKat.isAutoMirrored(drawable2);
        }

        @Override
        public void setAutoMirrored(Drawable drawable2, boolean bl2) {
            DrawableCompatKitKat.setAutoMirrored(drawable2, bl2);
        }
    }

}

