package com.crumby.lib.fragment;

import android.view.View;
import com.crumby.Analytics;
import com.crumby.lib.GalleryImage;

public class DoubleClickRunnable
  implements Runnable
{
  private int i;
  private GalleryImage image;
  private final GalleryListFragment listFragment;
  private View view;
  private boolean waiting;
  
  public DoubleClickRunnable(GalleryListFragment paramGalleryListFragment)
  {
    this.listFragment = paramGalleryListFragment;
  }
  
  public boolean isWaiting()
  {
    return this.waiting;
  }
  
  public void run()
  {
    Analytics.INSTANCE.newNavigationEvent("image click", this.i + " " + this.image.getLinkUrl());
    this.listFragment.goToImage(this.view, this.image, this.i);
    this.waiting = false;
  }
  
  public void set(int paramInt, View paramView, GalleryImage paramGalleryImage)
  {
    this.i = paramInt;
    this.view = paramView;
    this.image = paramGalleryImage;
    this.waiting = true;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/DoubleClickRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */