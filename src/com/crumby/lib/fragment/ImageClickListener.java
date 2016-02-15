package com.crumby.lib.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import com.crumby.Analytics;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.widget.firstparty.grow.ImagePressWrapper;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;

public class ImageClickListener
  implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
  private static final String ACTION = "image click";
  private static final long WRAPPER_CLICK_TIME = 100L;
  private final GalleryClickHandler clickHandler;
  private boolean disabled;
  private MultiSelect multiSelect;
  private final String source;
  
  public ImageClickListener(GalleryClickHandler paramGalleryClickHandler, MultiSelect paramMultiSelect, String paramString)
  {
    this.multiSelect = paramMultiSelect;
    this.clickHandler = paramGalleryClickHandler;
    this.source = paramString;
  }
  
  private void wiggleView(final View paramView)
  {
    paramView.animate().rotation(5.0F).setDuration(100L).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        paramView.animate().rotation(-5.0F).setDuration(100L).setListener(new AnimatorListenerAdapter()
        {
          public void onAnimationEnd(Animator paramAnonymous2Animator)
          {
            ImageClickListener.1.this.val$v.animate().rotation(0.0F).setDuration(100L).setListener(new AnimatorListenerAdapter()
            {
              public void onAnimationEnd(Animator paramAnonymous3Animator) {}
            });
          }
        });
      }
    });
  }
  
  public void disable()
  {
    this.disabled = true;
  }
  
  public void enable()
  {
    this.disabled = false;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.multiSelect.isOpen()) {
      onItemLongClick(paramAdapterView, paramView, paramInt, paramLong);
    }
    while (paramView.getTag() == null) {
      return;
    }
    paramAdapterView = ((GridImageWrapper)paramView.getTag()).getImage();
    if (paramAdapterView == null)
    {
      Analytics.INSTANCE.newNavigationEvent("not loaded", paramInt + "");
      wiggleView(paramView);
      return;
    }
    Analytics.INSTANCE.newNavigationEvent(this.source + " image click", paramInt + " " + paramAdapterView.getLinkUrl());
    this.clickHandler.goToImage(paramView, paramAdapterView, paramInt);
  }
  
  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (!(paramView instanceof ImagePressWrapper)) {}
    do
    {
      return false;
      paramAdapterView = (ImagePressWrapper)paramView;
    } while ((this.disabled) || (paramAdapterView.getImage() == null));
    paramAdapterView.toggle();
    if (paramAdapterView.isChecked()) {
      this.multiSelect.add(paramAdapterView.getImage());
    }
    for (;;)
    {
      return true;
      this.multiSelect.remove(paramAdapterView.getImage());
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/ImageClickListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */