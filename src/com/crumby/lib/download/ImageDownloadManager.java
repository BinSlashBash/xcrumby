package com.crumby.lib.download;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.firstparty.main_menu.MainMenu;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum ImageDownloadManager
{
  INSTANCE;
  
  private HashSet<ImageDownload> completedDownloads = new HashSet();
  private Set<ImageDownloadListener> defaultListeners;
  private Collection<GalleryImage> deferredImages;
  private String downloadPath;
  private ExecutorService executor;
  private MainMenu mainMenu;
  private HashSet<ImageDownload> recentDownloads = new HashSet();
  
  private ImageDownloadManager() {}
  
  private ImageDownload download(GalleryImage paramGalleryImage, boolean paramBoolean)
  {
    paramGalleryImage = new ImageDownload(paramGalleryImage, this.defaultListeners, getSaveDirectory(), paramBoolean);
    if (paramGalleryImage.isValid())
    {
      paramBoolean = this.recentDownloads.contains(paramGalleryImage);
      boolean bool = this.completedDownloads.contains(paramGalleryImage);
      if ((!paramBoolean) && (!bool)) {
        submitRequest(paramGalleryImage);
      }
    }
    else
    {
      return paramGalleryImage;
    }
    if (paramBoolean)
    {
      restart(paramGalleryImage);
      return paramGalleryImage;
    }
    paramGalleryImage.indicateAlreadySaved();
    return paramGalleryImage;
  }
  
  private void submitRequest(final ImageDownload paramImageDownload)
  {
    paramImageDownload.indicateQueued();
    if (this.executor == null) {
      this.executor = Executors.newFixedThreadPool(2);
    }
    paramImageDownload.setFuture(this.executor.submit(new Runnable()
    {
      public void run()
      {
        paramImageDownload.start();
      }
    }));
  }
  
  public void addDefaultListener(ImageDownloadListener paramImageDownloadListener)
  {
    if (this.defaultListeners == null) {
      this.defaultListeners = new HashSet();
    }
    this.defaultListeners.add(paramImageDownloadListener);
  }
  
  public void broadcast(ImageDownload paramImageDownload)
  {
    this.mainMenu.getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(paramImageDownload.getDownloadUri()))));
  }
  
  public boolean clearDeferredImageDownloadAndCheckIfDownloadPathIsNull()
  {
    if (this.deferredImages != null) {
      this.deferredImages.clear();
    }
    this.deferredImages = null;
    return this.downloadPath == null;
  }
  
  public void clearFinishedDownloads()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.recentDownloads.iterator();
    while (localIterator.hasNext())
    {
      ImageDownload localImageDownload = (ImageDownload)localIterator.next();
      if (localImageDownload.isDone()) {
        localArrayList.add(localImageDownload);
      }
    }
    this.recentDownloads.removeAll(localArrayList);
    this.completedDownloads.addAll(localArrayList);
  }
  
  public void downloadAll(Collection<GalleryImage> paramCollection)
  {
    Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "downloading images", "", paramCollection.size());
    Object localObject;
    if (this.downloadPath == null) {
      if (!Environment.getExternalStorageState().equals("mounted"))
      {
        localObject = Toast.makeText(this.mainMenu.getContext(), "", 1);
        ((Toast)localObject).setGravity(5, 0, 0);
        TextView localTextView = (TextView)View.inflate(this.mainMenu.getContext(), 2130903118, null);
        localTextView.setText("Crumby could not access your download folder. Please check that your SD card is mounted!");
        ((Toast)localObject).setView(localTextView);
        ((Toast)localObject).show();
        this.deferredImages = paramCollection;
      }
    }
    for (;;)
    {
      return;
      this.downloadPath = Environment.getExternalStorageDirectory().getPath();
      PreferenceManager.getDefaultSharedPreferences(this.mainMenu.getContext()).edit().putString("crumbyDownloadDirectory", this.downloadPath).commit();
      this.mainMenu.showDownloads();
      clearFinishedDownloads();
      localObject = new ArrayList();
      boolean bool = PreferenceManager.getDefaultSharedPreferences(this.mainMenu.getContext()).getBoolean("crumbyUseBreadcrumbPathForDownload", false);
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext()) {
        ((ArrayList)localObject).add(download((GalleryImage)paramCollection.next(), bool));
      }
      this.recentDownloads.addAll((Collection)localObject);
      paramCollection = this.defaultListeners.iterator();
      while (paramCollection.hasNext()) {
        ((ImageDownloadListener)paramCollection.next()).update((ArrayList)localObject);
      }
    }
  }
  
  public void downloadOne(GalleryImage paramGalleryImage)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramGalleryImage);
    downloadAll(localArrayList);
  }
  
  public String getDownloadPath()
  {
    return this.downloadPath;
  }
  
  public int getDownloadingCount()
  {
    int i = 0;
    Iterator localIterator = this.recentDownloads.iterator();
    while (localIterator.hasNext()) {
      if (((ImageDownload)localIterator.next()).isDownloading()) {
        i += 1;
      }
    }
    return i;
  }
  
  public String getSaveDirectory()
  {
    if (this.downloadPath == null) {
      this.downloadPath = Environment.getExternalStorageDirectory().getPath();
    }
    return this.downloadPath + "/crumby/";
  }
  
  public void initialize(MainMenu paramMainMenu)
  {
    this.mainMenu = paramMainMenu;
  }
  
  public void restart(ImageDownload paramImageDownload)
  {
    Iterator localIterator = this.recentDownloads.iterator();
    while (localIterator.hasNext())
    {
      ImageDownload localImageDownload = (ImageDownload)localIterator.next();
      if ((paramImageDownload.equals(localImageDownload)) && (localImageDownload.canBeRestarted()))
      {
        submitRequest(localImageDownload);
        paramImageDownload = this.defaultListeners.iterator();
        while (paramImageDownload.hasNext()) {
          ((ImageDownloadListener)paramImageDownload.next()).update(localImageDownload);
        }
        Analytics.INSTANCE.newImageDownloadEvent(localImageDownload, "restart");
      }
    }
  }
  
  public void setDownloadPath(String paramString)
  {
    this.downloadPath = paramString;
    if ((this.deferredImages == null) || (this.deferredImages.isEmpty())) {
      return;
    }
    downloadAll(this.deferredImages);
    this.deferredImages = null;
  }
  
  public void terminateDownloads()
  {
    Analytics.INSTANCE.newEvent(AnalyticsCategories.DOWNLOADS, "terminate", this.recentDownloads.size() + this.completedDownloads.size() + "");
    if (this.executor != null) {
      this.executor.shutdownNow();
    }
    this.executor = null;
    this.recentDownloads.clear();
    this.completedDownloads.clear();
    Iterator localIterator = this.defaultListeners.iterator();
    while (localIterator.hasNext()) {
      ((ImageDownloadListener)localIterator.next()).terminate();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/download/ImageDownloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */