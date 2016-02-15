package com.crumby.lib.widget.firstparty;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class ScreenCapture {
    private Bitmap bitmap;
    private Canvas canvas;
    private View parent;
    private ImageView screenShot;

    /* renamed from: com.crumby.lib.widget.firstparty.ScreenCapture.1 */
    class C01291 extends AsyncTask<Void, Void, Void> {
        final /* synthetic */ int val$bottom;
        final /* synthetic */ int val$top;

        C01291(int i, int i2) {
            this.val$top = i;
            this.val$bottom = i2;
        }

        protected Void doInBackground(Void... params) {
            ScreenCapture.this.recapture(this.val$top, this.val$bottom);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            ScreenCapture.this.drawOnCanvas(this.val$top);
            ScreenCapture.this.screenShot.setImageBitmap(ScreenCapture.this.bitmap);
            ScreenCapture.this.parent.setVisibility(8);
            ScreenCapture.this.screenShot.setVisibility(0);
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.ScreenCapture.2 */
    class C01302 implements Runnable {
        C01302() {
        }

        public void run() {
            ScreenCapture.this.setupScreenShot();
            ScreenCapture.this.drawOnCanvas(0);
            ScreenCapture.this.parent.setVisibility(8);
            ScreenCapture.this.screenShot.setVisibility(0);
        }
    }

    public ScreenCapture(View parent, ImageView screenShot) {
        this.parent = parent;
        this.screenShot = screenShot;
    }

    public void captureAndShowScreenAsync(int top, int bottom) {
        new C01291(top, bottom).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public void captureAndShowScreen() {
        new Handler().post(new C01302());
    }

    private void drawOnCanvas(int top) {
        this.canvas.drawColor(this.parent.getResources().getColor(C0065R.color.BackgroundGray));
        CrDb.logTime("screen capture", "capture draw on canvas ", true);
        if (this.canvas.getSaveCount() > 1) {
            this.canvas.restore();
        }
        this.canvas.save();
        if (top > 0) {
            this.canvas.translate(0.0f, (float) (-top));
        }
        this.parent.draw(this.canvas);
        CrDb.logTime("screen capture", "capture draw on canvas ", false);
    }

    public void setupScreenShot() {
        recapture(0, 0);
        this.screenShot.setImageBitmap(this.bitmap);
    }

    private boolean cannotRecapture() {
        return this.parent.getWidth() == 0 || (this.bitmap != null && this.bitmap.getWidth() == this.parent.getWidth() && this.bitmap.getHeight() == this.parent.getHeight());
    }

    private void recapture(int top, int bottom) {
        if (!cannotRecapture()) {
            int width = this.parent.getWidth();
            int height = bottom - top;
            if (height <= 0) {
                height = this.parent.getHeight();
            }
            CrDb.logTime("screen capture", "recaptured ", true);
            this.bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            CrDb.m0d("screen capture", "bitmap initialize " + width + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + height);
            this.canvas = new Canvas(this.bitmap);
            CrDb.logTime("screen capture", "recaptured ", false);
        }
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

    public void resetScreenshot() {
        captureAndShowScreen();
        this.screenShot.setScaleX(GalleryViewer.PROGRESS_COMPLETED);
        this.screenShot.setScaleY(GalleryViewer.PROGRESS_COMPLETED);
        this.screenShot.setAlpha(GalleryViewer.PROGRESS_COMPLETED);
    }

    public void hide() {
        this.screenShot.setVisibility(8);
    }

    public void scale(float original) {
        this.screenShot.setScaleY(original);
        this.screenShot.setScaleX(original);
    }

    public void resetAlpha() {
        this.screenShot.setAlpha(GalleryViewer.PROGRESS_COMPLETED);
    }

    public void setPadding(int i, int i1, int i2, int i3) {
        if (this.screenShot != null) {
            this.screenShot.setPadding(i, i1, i2, i3);
        }
    }

    public ImageView getScreenShot() {
        return this.screenShot;
    }
}
