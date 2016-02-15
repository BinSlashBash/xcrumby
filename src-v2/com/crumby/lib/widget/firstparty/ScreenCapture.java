/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.os.AsyncTask
 *  android.os.Handler
 *  android.view.View
 *  android.widget.ImageView
 */
package com.crumby.lib.widget.firstparty;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import com.crumby.CrDb;
import java.util.concurrent.Executor;

public class ScreenCapture {
    private Bitmap bitmap;
    private Canvas canvas;
    private View parent;
    private ImageView screenShot;

    public ScreenCapture(View view, ImageView imageView) {
        this.parent = view;
        this.screenShot = imageView;
    }

    private boolean cannotRecapture() {
        if (this.parent.getWidth() == 0 || this.bitmap != null && this.bitmap.getWidth() == this.parent.getWidth() && this.bitmap.getHeight() == this.parent.getHeight()) {
            return true;
        }
        return false;
    }

    private void drawOnCanvas(int n2) {
        this.canvas.drawColor(this.parent.getResources().getColor(2131427524));
        CrDb.logTime("screen capture", "capture draw on canvas ", true);
        if (this.canvas.getSaveCount() > 1) {
            this.canvas.restore();
        }
        this.canvas.save();
        if (n2 > 0) {
            this.canvas.translate(0.0f, (float)(- n2));
        }
        this.parent.draw(this.canvas);
        CrDb.logTime("screen capture", "capture draw on canvas ", false);
    }

    private void recapture(int n2, int n3) {
        if (this.cannotRecapture()) {
            return;
        }
        int n4 = this.parent.getWidth();
        n2 = n3 -= n2;
        if (n3 <= 0) {
            n2 = this.parent.getHeight();
        }
        CrDb.logTime("screen capture", "recaptured ", true);
        this.bitmap = Bitmap.createBitmap((int)n4, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        CrDb.d("screen capture", "bitmap initialize " + n4 + " " + n2);
        this.canvas = new Canvas(this.bitmap);
        CrDb.logTime("screen capture", "recaptured ", false);
    }

    public void captureAndShowScreen() {
        new Handler().post(new Runnable(){

            @Override
            public void run() {
                ScreenCapture.this.setupScreenShot();
                ScreenCapture.this.drawOnCanvas(0);
                ScreenCapture.this.parent.setVisibility(8);
                ScreenCapture.this.screenShot.setVisibility(0);
            }
        });
    }

    public void captureAndShowScreenAsync(final int n2, final int n3) {
        new AsyncTask<Void, Void, Void>(){

            protected /* varargs */ Void doInBackground(Void ... arrvoid) {
                ScreenCapture.this.recapture(n2, n3);
                return null;
            }

            protected void onPostExecute(Void void_) {
                ScreenCapture.this.drawOnCanvas(n2);
                ScreenCapture.this.screenShot.setImageBitmap(ScreenCapture.this.bitmap);
                ScreenCapture.this.parent.setVisibility(8);
                ScreenCapture.this.screenShot.setVisibility(0);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
    }

    public ImageView getScreenShot() {
        return this.screenShot;
    }

    public void hide() {
        this.screenShot.setVisibility(8);
    }

    public void recycle() {
        if (this.screenShot != null) {
            this.screenShot.setImageDrawable(null);
        }
        if (this.bitmap != null) {
            this.bitmap.recycle();
        }
        this.bitmap = null;
    }

    public void resetAlpha() {
        this.screenShot.setAlpha(1.0f);
    }

    public void resetScreenshot() {
        this.captureAndShowScreen();
        this.screenShot.setScaleX(1.0f);
        this.screenShot.setScaleY(1.0f);
        this.screenShot.setAlpha(1.0f);
    }

    public void scale(float f2) {
        this.screenShot.setScaleY(f2);
        this.screenShot.setScaleX(f2);
    }

    public void setPadding(int n2, int n3, int n4, int n5) {
        if (this.screenShot != null) {
            this.screenShot.setPadding(n2, n3, n4, n5);
        }
    }

    public void setupScreenShot() {
        this.recapture(0, 0);
        this.screenShot.setImageBitmap(this.bitmap);
    }

}

