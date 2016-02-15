package com.squareup.picasso;

import android.graphics.Bitmap;

public abstract interface Transformation
{
  public abstract String key();
  
  public abstract Bitmap transform(Bitmap paramBitmap);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/picasso/Transformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */