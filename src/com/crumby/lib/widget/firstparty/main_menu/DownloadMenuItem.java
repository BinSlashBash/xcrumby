package com.crumby.lib.widget.firstparty.main_menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.download.ImageDownloadManager;
import com.crumby.lib.download.ScrollStateKeeper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class DownloadMenuItem
  extends RelativeLayout
{
  private static int defaultColor;
  private static int defaultFlags;
  private String bgImageUri;
  private ImageView bgView;
  private String bgViewSrc;
  private ImageDownload download;
  private TextView filename;
  private TextView id;
  private ImageView imageView;
  private boolean initialized;
  ScrollStateKeeper keeper;
  int lastProgress;
  
  public DownloadMenuItem(Context paramContext)
  {
    super(paramContext);
  }
  
  public DownloadMenuItem(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public DownloadMenuItem(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void mainThreadUpdate(ImageDownload paramImageDownload)
  {
    int i;
    Drawable localDrawable1;
    if ((this.download != paramImageDownload) || (this.lastProgress != paramImageDownload.getProgress()))
    {
      i = 1;
      this.lastProgress = paramImageDownload.getProgress();
      this.download = paramImageDownload;
      CrDb.d("download menu item", "checking if download has changed. " + paramImageDownload.getDownloadUri());
      if (i != 0)
      {
        CrDb.d("download menu item", "download has changed. " + paramImageDownload.getDownloadUri());
        this.imageView.setAlpha(1.0F);
        Drawable localDrawable2 = getResources().getDrawable(2130837610);
        this.filename.setText(paramImageDownload.getImage().getRequestedFilename());
        this.filename.setTextColor(defaultColor);
        this.bgView.setImageBitmap(null);
        Picasso.with(getContext()).cancelRequest(this.bgView);
        localDrawable1 = localDrawable2;
        switch (paramImageDownload.getProgress())
        {
        default: 
          localDrawable1 = localDrawable2;
        }
      }
    }
    for (;;)
    {
      this.imageView.setImageDrawable(localDrawable1);
      invalidate();
      return;
      i = 0;
      break;
      localDrawable1 = getResources().getDrawable(2130837635);
      this.filename.setText("Err: " + this.filename.getText());
      this.filename.setTextColor(getResources().getColor(2131427541));
      continue;
      localDrawable1 = getResources().getDrawable(2130837635);
      continue;
      localDrawable1 = getResources().getDrawable(2130837604);
      this.imageView.setAlpha(0.5F);
      setBackground();
    }
  }
  
  private void setBackground()
  {
    if ((getMeasuredWidth() == 0) || (!hasDownloaded()) || (!this.keeper.canShowListItems())) {
      return;
    }
    this.bgViewSrc = this.download.getDownloadUri();
    CrDb.d("download menu item", "setting background for " + this.download.getDownloadUri());
    Picasso.with(getContext()).load("file://" + this.download.getDownloadUri()).resize(getMeasuredWidth(), getMeasuredHeight() - 2).centerCrop().into(this.bgView);
  }
  
  public boolean canBeRestarted()
  {
    return this.download.canBeRestarted();
  }
  
  public void cancel() {}
  
  public String getURI()
  {
    return this.download.getDownloadUri();
  }
  
  public boolean hasDownloaded()
  {
    if (this.download == null) {
      return false;
    }
    return this.download.isDone();
  }
  
  public void invalidateDownloadChangeFlag()
  {
    this.lastProgress = -9999999;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    setBackground();
  }
  
  public void restartDownload()
  {
    invalidateDownloadChangeFlag();
    this.imageView.setImageDrawable(getResources().getDrawable(2130837610));
    ImageDownloadManager.INSTANCE.restart(this.download);
  }
  
  public void setKeeper(ScrollStateKeeper paramScrollStateKeeper)
  {
    this.keeper = paramScrollStateKeeper;
  }
  
  public void stopDownload()
  {
    invalidateDownloadChangeFlag();
    this.imageView.setImageDrawable(getResources().getDrawable(2130837635));
    this.download.stop();
  }
  
  public void update(ImageDownload paramImageDownload, int paramInt)
  {
    if (!this.initialized)
    {
      this.id = ((TextView)findViewById(2131493060));
      this.filename = ((TextView)findViewById(2131493061));
      this.imageView = ((ImageView)findViewById(2131493062));
      this.bgView = ((ImageView)findViewById(2131493059));
      if (defaultColor == 0) {
        defaultColor = this.filename.getCurrentTextColor();
      }
      this.initialized = true;
    }
    this.id.setText(paramInt + "");
    mainThreadUpdate(paramImageDownload);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/main_menu/DownloadMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */