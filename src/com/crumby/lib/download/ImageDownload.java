package com.crumby.lib.download;

import com.crumby.Analytics;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.firstparty.DisplayError;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Future;

public class ImageDownload
{
  public static final int ERROR = -1;
  public static final int FINISHED = 100;
  public static final int INVALID = -3;
  public static final int STARTED = 0;
  public static final int STOP = -2;
  private Set<ImageDownloadListener> deferAdd;
  private Set<ImageDownloadListener> deferRemove;
  private final String downloadUri;
  private Future future;
  private final GalleryImage image;
  private boolean iterating;
  private final ArrayList<ImageDownloadListener> listeners;
  private int progress;
  
  public ImageDownload(GalleryImage paramGalleryImage, Set<ImageDownloadListener> paramSet, String paramString, boolean paramBoolean)
  {
    this.image = paramGalleryImage;
    this.listeners = new ArrayList(paramSet);
    if ((paramGalleryImage == null) || (paramGalleryImage.getImageUrl() == null))
    {
      this.progress = -3;
      this.listeners.clear();
      this.downloadUri = null;
      return;
    }
    paramSet = paramString;
    if (paramBoolean) {
      paramSet = paramString + paramGalleryImage.buildPath();
    }
    paramSet = paramSet.replaceAll("\\s+", "_").replace(":", "_");
    new File(paramSet).mkdirs();
    this.downloadUri = (paramSet + paramGalleryImage.getRequestedFilename());
  }
  
  public boolean canBeRestarted()
  {
    return (hasStopped()) || (hasError());
  }
  
  public void deferAddListener(ImageDownloadListener paramImageDownloadListener)
  {
    if (!this.iterating) {
      this.listeners.add(0, paramImageDownloadListener);
    }
    if (this.deferAdd == null) {
      this.deferAdd = new HashSet();
    }
    this.deferAdd.add(paramImageDownloadListener);
  }
  
  public void deferRemoveListener(ImageDownloadListener paramImageDownloadListener)
  {
    if (!this.iterating) {
      this.listeners.remove(paramImageDownloadListener);
    }
    if (this.deferRemove == null) {
      this.deferRemove = new HashSet();
    }
    this.deferRemove.add(paramImageDownloadListener);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((!(paramObject instanceof ImageDownload)) || (this.downloadUri == null)) {
      return false;
    }
    return this.downloadUri.equals(((ImageDownload)paramObject).downloadUri);
  }
  
  public String getDownloadUri()
  {
    return this.downloadUri;
  }
  
  public GalleryImage getImage()
  {
    return this.image;
  }
  
  public int getProgress()
  {
    return this.progress;
  }
  
  public boolean hasError()
  {
    return this.progress == -1;
  }
  
  public boolean hasStopped()
  {
    return this.progress == -2;
  }
  
  public int hashCode()
  {
    if (this.downloadUri == null) {
      return 0;
    }
    return this.downloadUri.hashCode();
  }
  
  public void indicateAlreadySaved()
  {
    this.progress = 100;
    this.listeners.clear();
  }
  
  public void indicateQueued()
  {
    if (!isValid()) {
      return;
    }
    this.progress = 0;
  }
  
  public boolean isDone()
  {
    return this.progress == 100;
  }
  
  public boolean isDownloading()
  {
    return (this.downloadUri != null) && (this.progress >= 0) && (this.progress < 100);
  }
  
  public boolean isValid()
  {
    return (this.progress != -3) && (this.downloadUri != null);
  }
  
  public void setFuture(Future paramFuture)
  {
    this.future = paramFuture;
  }
  
  public void start()
  {
    if (this.downloadUri == null) {
      return;
    }
    localObject2 = null;
    try
    {
      l = System.currentTimeMillis();
      localObject1 = new FileOutputStream(this.downloadUri);
      try
      {
        localObject2 = new URL(this.image.getImageUrl());
        byte[] arrayOfByte = new byte['Ѐ'];
        localObject2 = ((URL)localObject2).openConnection();
        ((URLConnection)localObject2).setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        localObject2 = ((URLConnection)localObject2).getInputStream();
        for (;;)
        {
          int i = ((InputStream)localObject2).read(arrayOfByte);
          if (i == -1) {
            break;
          }
          ((FileOutputStream)localObject1).write(arrayOfByte, 0, i);
        }
        Analytics.INSTANCE.newError(DisplayError.COULD_NOT_DOWNLOAD_IMAGE, this.downloadUri + " " + localIOException1.toString());
      }
      catch (IOException localIOException1) {}
    }
    catch (IOException localIOException3)
    {
      for (;;)
      {
        long l;
        Object localObject1 = localObject2;
      }
    }
    this.future.cancel(true);
    if (localObject1 != null) {}
    try
    {
      ((FileOutputStream)localObject1).close();
      new File(this.downloadUri).delete();
      update(-1);
      return;
      update(100);
      Analytics.INSTANCE.newImageDownloadEvent(this, "finished", l);
      ImageDownloadManager.INSTANCE.broadcast(this);
      return;
    }
    catch (IOException localIOException2)
    {
      for (;;)
      {
        localIOException2.printStackTrace();
      }
    }
  }
  
  public boolean stop()
  {
    update(-2);
    Analytics.INSTANCE.newImageDownloadEvent(this, "stop");
    return this.future.cancel(true);
  }
  
  public void update(int paramInt)
  {
    try
    {
      CrDb.d("image download", "updating " + this.downloadUri + " " + paramInt);
      this.progress = paramInt;
      this.iterating = true;
      Iterator localIterator = this.listeners.iterator();
      while (localIterator.hasNext()) {
        ((ImageDownloadListener)localIterator.next()).update(this);
      }
      this.iterating = false;
    }
    finally {}
    if (this.deferRemove != null) {
      this.listeners.removeAll(this.deferRemove);
    }
    if (this.deferAdd != null) {
      this.listeners.addAll(0, this.deferAdd);
    }
    this.deferRemove = null;
    this.deferAdd = null;
    if (paramInt == 100) {
      this.listeners.clear();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/download/ImageDownload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */