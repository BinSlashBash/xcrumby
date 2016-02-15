package com.crumby.lib.widget.firstparty;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryClickHandler;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.producer.GalleryProducer;
import twowayview.TwoWayView;

public class GalleryHorizontalList
  extends RelativeLayout
  implements GalleryList, GalleryClickHandler
{
  private ErrorView errorView;
  private Fragment fragment;
  private GalleryImage image;
  private TwoWayView list;
  private GalleryProducer producer;
  
  public GalleryHorizontalList(Context paramContext)
  {
    super(paramContext);
  }
  
  public GalleryHorizontalList(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public GalleryHorizontalList(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public Bundle getArguments()
  {
    return null;
  }
  
  public GalleryImage getImage()
  {
    return this.image;
  }
  
  public AdapterView getList()
  {
    return this.list;
  }
  
  public GalleryProducer getProducer()
  {
    return null;
  }
  
  public boolean getUserVisibleHint()
  {
    return this.fragment.getUserVisibleHint();
  }
  
  public void goToImage(View paramView, GalleryImage paramGalleryImage, int paramInt) {}
  
  public void indicateProgressChange(float paramFloat) {}
  
  public void initialize(GalleryImage paramGalleryImage, GalleryProducer paramGalleryProducer, Fragment paramFragment)
  {
    this.image = paramGalleryImage;
    this.producer = paramGalleryProducer;
    this.fragment = paramFragment;
  }
  
  public void showError(DisplayError paramDisplayError, String paramString1, String paramString2)
  {
    if (this.errorView != null) {
      this.errorView.show(paramDisplayError, paramString1, paramString2);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/GalleryHorizontalList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */