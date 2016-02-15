package com.crumby.lib.download;

import java.util.ArrayList;

public abstract interface ImageDownloadListener
{
  public abstract void terminate();
  
  public abstract void update(ImageDownload paramImageDownload);
  
  public abstract void update(ArrayList<ImageDownload> paramArrayList);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/download/ImageDownloadListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */