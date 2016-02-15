package com.crumby.impl.imgur;

import android.view.View;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;

public abstract class ImgurBaseFragment
  extends GalleryGridFragment
{
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    postUrlChangeToBusWithProducer(paramGalleryImage);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurBaseFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */