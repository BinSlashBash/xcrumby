package com.crumby.lib.widget.firstparty.multiselect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownloadManager;
import it.sephiroth.android.library.tooltip.TooltipManager;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MultiSelectBar
  extends LinearLayout
  implements View.OnClickListener, MultiSelect
{
  private boolean animating;
  private ImageButton bookmark;
  private ImageButton close;
  private TextView galleries;
  private int galleryCount;
  private int imageCount;
  private LinkedHashSet<GalleryImage> images;
  private ImageButton save;
  private ImageButton share;
  private GalleryViewer viewer;
  
  public MultiSelectBar(Context paramContext)
  {
    super(paramContext);
  }
  
  public MultiSelectBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public MultiSelectBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void countImages()
  {
    this.imageCount = 0;
    this.galleryCount = 0;
    Iterator localIterator = this.images.iterator();
    while (localIterator.hasNext()) {
      if (((GalleryImage)localIterator.next()).isALinkToGallery()) {
        this.galleryCount += 1;
      } else {
        this.imageCount += 1;
      }
    }
  }
  
  private void resetAnimation()
  {
    clearAnimation();
    setAlpha(1.0F);
    this.animating = false;
  }
  
  private void show()
  {
    if (this.images.isEmpty()) {
      return;
    }
    setVisibility(0);
    if (this.animating)
    {
      this.animating = false;
      setVisibility(0);
      resetAnimation();
      return;
    }
    this.animating = true;
    animate().alpha(1.0F).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        MultiSelectBar.access$002(MultiSelectBar.this, false);
      }
    });
  }
  
  private void updateText()
  {
    countImages();
    Object localObject1 = "";
    if (this.galleryCount != 0)
    {
      localObject2 = "" + this.galleryCount + " galleries";
      localObject1 = localObject2;
      if (this.imageCount != 0) {
        localObject1 = (String)localObject2 + ", ";
      }
    }
    Object localObject2 = localObject1;
    if (this.imageCount != 0) {
      localObject2 = (String)localObject1 + this.imageCount + " images";
    }
    this.galleries.setText((CharSequence)localObject2);
  }
  
  public void add(GalleryImage paramGalleryImage)
  {
    this.images.add(paramGalleryImage);
    show();
    updateText();
  }
  
  public void add(Collection<GalleryImage> paramCollection)
  {
    this.images.addAll(paramCollection);
    show();
    updateText();
  }
  
  public void clear()
  {
    if ((this.images != null) && (this.images.isEmpty())) {
      return;
    }
    Iterator localIterator = this.images.iterator();
    while (localIterator.hasNext()) {
      ((GalleryImage)localIterator.next()).setChecked(false);
    }
    this.images.clear();
    hide();
    this.viewer.redrawPage();
  }
  
  public boolean hasImages()
  {
    if (this.images == null) {}
    while (this.images.isEmpty()) {
      return false;
    }
    return true;
  }
  
  public void hide()
  {
    if (this.animating)
    {
      setVisibility(8);
      resetAnimation();
      return;
    }
    this.animating = true;
    animate().alpha(0.0F).setDuration(300L).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        MultiSelectBar.this.setVisibility(8);
        MultiSelectBar.access$002(MultiSelectBar.this, false);
      }
    });
  }
  
  public void initialize(GalleryViewer paramGalleryViewer)
  {
    setVisibility(4);
    setAlpha(0.0F);
    this.images = new LinkedHashSet();
    this.galleries = ((TextView)findViewById(2131493067));
    this.close = ((ImageButton)findViewById(2131493066));
    this.close.setOnClickListener(this);
    this.save = ((ImageButton)findViewById(2131493068));
    this.share = ((ImageButton)findViewById(2131493069));
    this.bookmark = ((ImageButton)findViewById(2131493070));
    this.save.setOnClickListener(this);
    this.share.setOnClickListener(this);
    this.bookmark.setOnClickListener(this);
    this.viewer = paramGalleryViewer;
  }
  
  public boolean isOpen()
  {
    return getVisibility() == 0;
  }
  
  public void onClick(View paramView)
  {
    countImages();
    if (paramView == this.close) {
      clear();
    }
    do
    {
      return;
      if (paramView == this.save)
      {
        TooltipManager.getInstance(this.viewer).remove(0);
        TooltipManager.getInstance(this.viewer).remove(1);
        this.viewer.showOmnibar();
        ImageDownloadManager.INSTANCE.downloadAll(this.images);
        clear();
        return;
      }
    } while ((paramView == this.share) || (paramView != this.bookmark));
  }
  
  public void remove(Collection<GalleryImage> paramCollection)
  {
    if (this.images.removeAll(paramCollection))
    {
      updateText();
      if (this.images.isEmpty()) {
        hide();
      }
    }
  }
  
  public boolean remove(GalleryImage paramGalleryImage)
  {
    if (this.images.remove(paramGalleryImage))
    {
      updateText();
      if (this.images.isEmpty())
      {
        hide();
        return true;
      }
    }
    return false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/multiselect/MultiSelectBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */