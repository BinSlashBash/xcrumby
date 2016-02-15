package com.crumby.lib.fragment.adapter;

import android.graphics.Bitmap;
import com.crumby.lib.GalleryImage;
import com.squareup.picasso.Transformation;

public class CropTransformation
  implements Transformation
{
  private GalleryImage image;
  private int index;
  
  public CropTransformation(GalleryImage paramGalleryImage, int paramInt)
  {
    this.image = paramGalleryImage;
    this.index = paramInt;
  }
  
  public String key()
  {
    return "crop" + this.index;
  }
  
  public Bitmap transform(Bitmap paramBitmap)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, this.image.getOffset(), 0, this.image.getWidth(), this.image.getHeight());
    if (localBitmap != paramBitmap) {
      paramBitmap.recycle();
    }
    return localBitmap;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/CropTransformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */