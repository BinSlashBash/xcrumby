package com.crumby.impl.deviantart;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.GalleryImageView;

public class DeviantArtUserBadge
  extends LinearLayout
  implements GalleryImageView
{
  private ImageView avatar;
  private TextView badge;
  private GalleryImage image;
  
  public DeviantArtUserBadge(Context paramContext)
  {
    super(paramContext);
  }
  
  public DeviantArtUserBadge(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public DeviantArtUserBadge(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void initialize(GalleryImage paramGalleryImage) {}
  
  public void update() {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/deviantart/DeviantArtUserBadge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */