package com.crumby.lib.widget.firstparty;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import com.crumby.CrDb;

public class ScreenCapture
{
  private Bitmap bitmap;
  private Canvas canvas;
  private View parent;
  private ImageView screenShot;
  
  public ScreenCapture(View paramView, ImageView paramImageView)
  {
    this.parent = paramView;
    this.screenShot = paramImageView;
  }
  
  private boolean cannotRecapture()
  {
    return (this.parent.getWidth() == 0) || ((this.bitmap != null) && (this.bitmap.getWidth() == this.parent.getWidth()) && (this.bitmap.getHeight() == this.parent.getHeight()));
  }
  
  private void drawOnCanvas(int paramInt)
  {
    this.canvas.drawColor(this.parent.getResources().getColor(2131427524));
    CrDb.logTime("screen capture", "capture draw on canvas ", true);
    if (this.canvas.getSaveCount() > 1) {
      this.canvas.restore();
    }
    this.canvas.save();
    if (paramInt > 0) {
      this.canvas.translate(0.0F, -paramInt);
    }
    this.parent.draw(this.canvas);
    CrDb.logTime("screen capture", "capture draw on canvas ", false);
  }
  
  private void recapture(int paramInt1, int paramInt2)
  {
    if (cannotRecapture()) {
      return;
    }
    int i = this.parent.getWidth();
    paramInt2 -= paramInt1;
    paramInt1 = paramInt2;
    if (paramInt2 <= 0) {
      paramInt1 = this.parent.getHeight();
    }
    CrDb.logTime("screen capture", "recaptured ", true);
    this.bitmap = Bitmap.createBitmap(i, paramInt1, Bitmap.Config.ARGB_8888);
    CrDb.d("screen capture", "bitmap initialize " + i + " " + paramInt1);
    this.canvas = new Canvas(this.bitmap);
    CrDb.logTime("screen capture", "recaptured ", false);
  }
  
  public void captureAndShowScreen()
  {
    new Handler().post(new Runnable()
    {
      public void run()
      {
        ScreenCapture.this.setupScreenShot();
        ScreenCapture.this.drawOnCanvas(0);
        ScreenCapture.this.parent.setVisibility(8);
        ScreenCapture.this.screenShot.setVisibility(0);
      }
    });
  }
  
  public void captureAndShowScreenAsync(final int paramInt1, final int paramInt2)
  {
    new AsyncTask()
    {
      protected Void doInBackground(Void... paramAnonymousVarArgs)
      {
        ScreenCapture.this.recapture(paramInt1, paramInt2);
        return null;
      }
      
      protected void onPostExecute(Void paramAnonymousVoid)
      {
        ScreenCapture.this.drawOnCanvas(paramInt1);
        ScreenCapture.this.screenShot.setImageBitmap(ScreenCapture.this.bitmap);
        ScreenCapture.this.parent.setVisibility(8);
        ScreenCapture.this.screenShot.setVisibility(0);
      }
    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
  }
  
  public ImageView getScreenShot()
  {
    return this.screenShot;
  }
  
  public void hide()
  {
    this.screenShot.setVisibility(8);
  }
  
  public void recycle()
  {
    if (this.screenShot != null) {
      this.screenShot.setImageDrawable(null);
    }
    if (this.bitmap != null) {
      this.bitmap.recycle();
    }
    this.bitmap = null;
  }
  
  public void resetAlpha()
  {
    this.screenShot.setAlpha(1.0F);
  }
  
  public void resetScreenshot()
  {
    captureAndShowScreen();
    this.screenShot.setScaleX(1.0F);
    this.screenShot.setScaleY(1.0F);
    this.screenShot.setAlpha(1.0F);
  }
  
  public void scale(float paramFloat)
  {
    this.screenShot.setScaleY(paramFloat);
    this.screenShot.setScaleX(paramFloat);
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.screenShot != null) {
      this.screenShot.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void setupScreenShot()
  {
    recapture(0, 0);
    this.screenShot.setImageBitmap(this.bitmap);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/ScreenCapture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */