/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Xfermode
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 */
package com.google.android.gms.internal;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public final class ey {
    /*
     * Enabled aggressive block sorting
     */
    public static Bitmap a(Bitmap bitmap) {
        int n2;
        int n3;
        int n4 = 0;
        if (bitmap == null) {
            return null;
        }
        int n5 = bitmap.getWidth();
        if (n5 >= (n3 = bitmap.getHeight())) {
            n4 = n5 / 2 - n3 / 2;
            n2 = 0;
        } else {
            n2 = n3 / 2 - n5 / 2;
            n3 = n5;
        }
        Bitmap bitmap2 = Bitmap.createBitmap((int)n3, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint(1);
        paint.setColor(-16777216);
        canvas.drawCircle((float)(n3 / 2), (float)(n3 / 2), (float)(n3 / 2), paint);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, (float)n4, (float)n2, paint);
        return bitmap2;
    }

    private static Bitmap a(Drawable drawable2) {
        if (drawable2 == null) {
            return null;
        }
        if (drawable2 instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable2).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap((int)drawable2.getIntrinsicWidth(), (int)drawable2.getIntrinsicHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable2.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable2.draw(canvas);
        return bitmap;
    }

    public static Drawable a(Resources resources, Drawable drawable2) {
        return new BitmapDrawable(resources, ey.a(ey.a(drawable2)));
    }
}

