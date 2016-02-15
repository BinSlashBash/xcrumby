package com.uservoice.uservoicesdk.image;

import android.graphics.Bitmap;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageCache
{
  private static ImageCache instance;
  private Map<String, Bitmap> cache = new HashMap(this.capacity);
  private int capacity = 20;
  private List<String> mru = new ArrayList();
  
  public static ImageCache getInstance()
  {
    if (instance == null) {
      instance = new ImageCache();
    }
    return instance;
  }
  
  public void loadImage(String paramString, ImageView paramImageView)
  {
    if (this.cache.containsKey(paramString))
    {
      paramImageView.setImageBitmap((Bitmap)this.cache.get(paramString));
      this.mru.remove(paramString);
      this.mru.add(paramString);
      return;
    }
    new DownloadImageTask(paramString, paramImageView).execute(new Void[0]);
  }
  
  public void purge()
  {
    this.cache.clear();
    this.mru.clear();
  }
  
  public void set(String paramString, Bitmap paramBitmap)
  {
    if (this.cache.containsKey(paramString))
    {
      this.cache.put(paramString, paramBitmap);
      this.mru.remove(paramString);
      this.mru.add(paramString);
      return;
    }
    if (this.cache.size() == this.capacity)
    {
      String str = (String)this.mru.get(0);
      this.cache.remove(str);
      this.mru.remove(0);
    }
    this.cache.put(paramString, paramBitmap);
    this.mru.add(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/image/ImageCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */