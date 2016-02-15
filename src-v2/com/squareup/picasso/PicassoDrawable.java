/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Path
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.graphics.drawable.AnimationDrawable
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.SystemClock
 *  android.util.DisplayMetrics
 *  android.widget.ImageView
 */
package com.squareup.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

final class PicassoDrawable
extends BitmapDrawable {
    private static final Paint DEBUG_PAINT = new Paint();
    private static final float FADE_DURATION = 200.0f;
    int alpha = 255;
    boolean animating;
    private final boolean debugging;
    private final float density;
    private final Picasso.LoadedFrom loadedFrom;
    Drawable placeholder;
    long startTimeMillis;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    PicassoDrawable(Context context, Bitmap bitmap, Drawable drawable2, Picasso.LoadedFrom loadedFrom, boolean bl2, boolean bl3) {
        super(context.getResources(), bitmap);
        this.debugging = bl3;
        this.density = context.getResources().getDisplayMetrics().density;
        this.loadedFrom = loadedFrom;
        if (loadedFrom == Picasso.LoadedFrom.MEMORY || bl2) return;
        boolean bl4 = true;
        if (!bl4) return;
        this.placeholder = drawable2;
        this.animating = true;
        this.startTimeMillis = SystemClock.uptimeMillis();
    }

    private void drawDebugIndicator(Canvas canvas) {
        DEBUG_PAINT.setColor(-1);
        canvas.drawPath(PicassoDrawable.getTrianglePath(new Point(0, 0), (int)(16.0f * this.density)), DEBUG_PAINT);
        DEBUG_PAINT.setColor(this.loadedFrom.debugColor);
        canvas.drawPath(PicassoDrawable.getTrianglePath(new Point(0, 0), (int)(15.0f * this.density)), DEBUG_PAINT);
    }

    private static Path getTrianglePath(Point point, int n2) {
        Point point2 = new Point(point.x + n2, point.y);
        Point point3 = new Point(point.x, point.y + n2);
        Path path = new Path();
        path.moveTo((float)point.x, (float)point.y);
        path.lineTo((float)point2.x, (float)point2.y);
        path.lineTo((float)point3.x, (float)point3.y);
        return path;
    }

    static void setBitmap(ImageView imageView, Context context, Bitmap bitmap, Picasso.LoadedFrom loadedFrom, boolean bl2, boolean bl3) {
        Drawable drawable2 = imageView.getDrawable();
        if (drawable2 instanceof AnimationDrawable) {
            ((AnimationDrawable)drawable2).stop();
        }
        imageView.setImageDrawable((Drawable)new PicassoDrawable(context, bitmap, drawable2, loadedFrom, bl2, bl3));
    }

    /*
     * Enabled aggressive block sorting
     */
    static void setPlaceholder(ImageView imageView, int n2, Drawable drawable2) {
        if (n2 != 0) {
            imageView.setImageResource(n2);
        } else {
            imageView.setImageDrawable(drawable2);
        }
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable)imageView.getDrawable()).start();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        if (!this.animating) {
            super.draw(canvas);
        } else {
            float f2 = (float)(SystemClock.uptimeMillis() - this.startTimeMillis) / 200.0f;
            if (f2 >= 1.0f) {
                this.animating = false;
                this.placeholder = null;
                super.draw(canvas);
            } else {
                if (this.placeholder != null) {
                    this.placeholder.draw(canvas);
                }
                super.setAlpha((int)((float)this.alpha * f2));
                super.draw(canvas);
                super.setAlpha(this.alpha);
                if (Build.VERSION.SDK_INT <= 10) {
                    this.invalidateSelf();
                }
            }
        }
        if (this.debugging) {
            this.drawDebugIndicator(canvas);
        }
    }

    protected void onBoundsChange(Rect rect) {
        if (this.placeholder != null) {
            this.placeholder.setBounds(rect);
        }
        super.onBoundsChange(rect);
    }

    public void setAlpha(int n2) {
        this.alpha = n2;
        if (this.placeholder != null) {
            this.placeholder.setAlpha(n2);
        }
        super.setAlpha(n2);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.placeholder != null) {
            this.placeholder.setColorFilter(colorFilter);
        }
        super.setColorFilter(colorFilter);
    }
}

