package com.crumby.lib.fragment.producer;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import com.crumby.Analytics;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.SilentUrlRedirectEvent;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.otto.Bus;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public abstract class GalleryProducer
{
  public static final int DO_NOT_FOCUS = -1;
  protected static final JsonParser JSON_PARSER;
  private static Queue<DownloadGalleryImageTask> tasks;
  private volatile boolean available;
  private int currentImageFocus;
  private int currentPage;
  private AsyncTask<Integer, Void, ArrayList<GalleryImage>> downloader;
  private Exception fetchingException;
  protected Set<GalleryConsumer> galleryConsumers;
  private GalleryImage hostImage;
  private List<GalleryImage> images;
  private boolean initialized;
  private boolean metadataLoaded;
  protected String pageArg;
  private boolean shareable;
  private String silentRedirectUrl;
  
  static
  {
    if (!GalleryProducer.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      JSON_PARSER = new JsonParser();
      tasks = new ArrayDeque();
      return;
    }
  }
  
  private boolean canStartFetching()
  {
    return (this.images.isEmpty()) || (this.fetchingException != null);
  }
  
  public static String fetchUrl(String paramString)
    throws IOException
  {
    return fetchUrl(paramString, null, null);
  }
  
  public static String fetchUrl(String paramString1, String paramString2)
    throws IOException
  {
    return fetchUrl(paramString1, paramString2, null);
  }
  
  public static String fetchUrl(String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    paramString1 = new Request.Builder().url(paramString1);
    if (paramString3 != null) {
      paramString1.addHeader("X-Mashape-Key", paramString3);
    }
    if (paramString2 != null) {
      paramString1.addHeader("Authorization", paramString2);
    }
    return CrumbyApp.getHttpClient().newCall(paramString1.build()).execute().body().string();
  }
  
  protected static String getParameterInUrl(String paramString1, String paramString2)
  {
    Uri localUri = Uri.parse(paramString1);
    paramString1 = getQueryParameter(localUri, paramString1, paramString2);
    if ((paramString1 != null) && (!paramString1.equals(""))) {
      return localUri.getQueryParameter(paramString2);
    }
    return null;
  }
  
  public static String getQueryParameter(Uri paramUri, String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return paramUri.getQueryParameter(paramString2);
    }
    return Uri.parse(paramString1.replace("+", "%20").toString()).getQueryParameter(paramString2);
  }
  
  public static String legacyfetchUrl(String paramString)
    throws IOException
  {
    paramString = (HttpURLConnection)new URL(paramString).openConnection();
    paramString.connect();
    return readStreamIntoString(paramString.getInputStream());
  }
  
  public static String readStreamIntoString(InputStream paramInputStream)
    throws IOException
  {
    paramInputStream = new BufferedReader(new InputStreamReader(paramInputStream, "utf-8"));
    StringBuffer localStringBuffer = new StringBuffer();
    char[] arrayOfChar = new char['Ѐ'];
    for (;;)
    {
      int i = paramInputStream.read(arrayOfChar);
      if (i == -1) {
        break;
      }
      localStringBuffer.append(arrayOfChar, 0, i);
    }
    return localStringBuffer.toString();
  }
  
  public static void sendRequest() {}
  
  private void signalError()
  {
    if (this.fetchingException == null) {}
    for (;;)
    {
      return;
      Iterator localIterator = this.galleryConsumers.iterator();
      while (localIterator.hasNext()) {
        ((GalleryConsumer)localIterator.next()).showError(this.fetchingException);
      }
    }
  }
  
  private void signalNoMoreLoading()
  {
    Iterator localIterator = this.galleryConsumers.iterator();
    while (localIterator.hasNext()) {
      ((GalleryConsumer)localIterator.next()).finishLoading();
    }
  }
  
  private void threadSafeUpdateHostImageViews()
  {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      this.hostImage.updateViews();
    }
    while ((this.downloader == null) || (this.downloader.getStatus() == AsyncTask.Status.FINISHED)) {
      return;
    }
    ((DownloadGalleryImageTask)this.downloader).flagUpdateHostImageViewsOnEnd();
  }
  
  public void addConsumer(GalleryConsumer paramGalleryConsumer)
  {
    this.galleryConsumers.add(paramGalleryConsumer);
  }
  
  protected boolean addImagesToConsumers(List<GalleryImage> paramList)
  {
    boolean bool = false;
    Iterator localIterator = this.galleryConsumers.iterator();
    while (localIterator.hasNext()) {
      if (((GalleryConsumer)localIterator.next()).addImages(paramList)) {
        bool = true;
      }
    }
    return bool;
  }
  
  protected void addProducedImagesToCache(List<GalleryImage> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      GalleryImage localGalleryImage = (GalleryImage)paramList.next();
      localGalleryImage.setPosition(this.images.size());
      if (this.hostImage != null) {
        localGalleryImage.setPath(this.hostImage.getPath());
      }
      this.images.add(localGalleryImage);
    }
  }
  
  public void alterImageAtPosition(int paramInt, String paramString)
  {
    if (paramInt >= this.images.size()) {
      return;
    }
    ((GalleryImage)this.images.get(paramInt)).setLinkUrl(paramString);
  }
  
  public void clearInitialized()
  {
    this.initialized = false;
  }
  
  public void destroy()
  {
    if (this.images != null)
    {
      Iterator localIterator = this.images.iterator();
      while (localIterator.hasNext()) {
        ((GalleryImage)localIterator.next()).clearViews();
      }
      this.images.clear();
    }
    if (this.galleryConsumers != null) {
      this.galleryConsumers.clear();
    }
    haltDownload();
  }
  
  protected void fetch()
  {
    if ((this.downloader != null) && (this.downloader.getStatus() != AsyncTask.Status.FINISHED)) {
      return;
    }
    DownloadGalleryImageTask localDownloadGalleryImageTask = new DownloadGalleryImageTask();
    CrDb.d("gallery producer", "fetching " + tasks.size());
    String str = "";
    if (getHostUrl() != null) {
      str = getHostUrl();
    }
    CrDb.d("gallery producer", "ASYNC MANAGER: starting async task, " + str);
    if (tasks.size() > 4)
    {
      CrDb.d("gallery producer", "ASYNC MANAGER: removing async task, taking too long! " + ((DownloadGalleryImageTask)tasks.peek()).getUrl());
      ((DownloadGalleryImageTask)tasks.remove()).cancel(true);
    }
    tasks.add(localDownloadGalleryImageTask);
    this.downloader = localDownloadGalleryImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[0]);
  }
  
  protected abstract ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception;
  
  protected boolean fetchMetadata()
    throws Exception
  {
    return false;
  }
  
  protected void filterOutUndesiredImages(ArrayList<GalleryImage> paramArrayList) {}
  
  public int getCurrentImageFocus()
  {
    CrDb.d("producer", "get current image focus " + this.currentImageFocus);
    return this.currentImageFocus;
  }
  
  protected int getCurrentPage()
  {
    return this.currentPage;
  }
  
  protected GalleryImage getHostImage()
  {
    return this.hostImage;
  }
  
  public String getHostUrl()
  {
    return this.hostImage.getLinkUrl();
  }
  
  public List<GalleryImage> getImages()
  {
    return this.images;
  }
  
  public boolean haltDownload()
  {
    CrDb.d("producer", "Downloader halted");
    if (tryToStopDownload())
    {
      CrDb.d("producer", "Resetting download.");
      this.currentPage -= 1;
      this.available = true;
      return true;
    }
    return false;
  }
  
  protected ArrayList<GalleryImage> handleFetchingException(Exception paramException, boolean paramBoolean)
  {
    if (paramBoolean) {
      this.currentPage -= 1;
    }
    this.fetchingException = paramException;
    return null;
  }
  
  public void initialize(GalleryConsumer paramGalleryConsumer, GalleryImage paramGalleryImage, Bundle paramBundle)
  {
    preInitialize();
    if (!initialize())
    {
      paramBundle = this.galleryConsumers.iterator();
      while (paramBundle.hasNext())
      {
        GalleryConsumer localGalleryConsumer = (GalleryConsumer)paramBundle.next();
        if ((!$assertionsDisabled) && (localGalleryConsumer == paramGalleryConsumer)) {
          throw new AssertionError();
        }
      }
      paramGalleryConsumer.addImages(this.images);
      this.shareable = true;
    }
    this.hostImage = paramGalleryImage;
    this.galleryConsumers.add(paramGalleryConsumer);
    postInitialize();
  }
  
  public void initialize(GalleryConsumer paramGalleryConsumer, GalleryImage paramGalleryImage, Bundle paramBundle, boolean paramBoolean)
  {
    initialize(paramGalleryConsumer, paramGalleryImage, paramBundle);
    if (paramBoolean) {
      requestStartFetch();
    }
  }
  
  public boolean initialize()
  {
    if (this.initialized) {
      return false;
    }
    this.images = new ArrayList();
    this.available = false;
    this.galleryConsumers = new HashSet();
    this.currentImageFocus = -1;
    this.initialized = true;
    return true;
  }
  
  public boolean isAvailable()
  {
    return this.available;
  }
  
  public boolean isInitialized()
  {
    return this.initialized;
  }
  
  public void makeShareable()
  {
    this.shareable = true;
  }
  
  protected void notifyHandlerDataSetsChanged()
  {
    Iterator localIterator = this.galleryConsumers.iterator();
    while (localIterator.hasNext()) {
      ((GalleryConsumer)localIterator.next()).notifyDataSetChanged();
    }
  }
  
  protected void onFinishedDownloading(ArrayList<GalleryImage> paramArrayList, boolean paramBoolean)
  {
    if (this.silentRedirectUrl != null)
    {
      BusProvider.BUS.get().post(new SilentUrlRedirectEvent(this.silentRedirectUrl, getHostImage()));
      this.silentRedirectUrl = null;
    }
    if (paramArrayList == null) {}
    for (int i = 0;; i = paramArrayList.size())
    {
      CrDb.d("producer", this.currentPage + " Size:" + i);
      if ((paramArrayList != null) && (!paramArrayList.isEmpty())) {
        break;
      }
      signalNoMoreLoading();
      if (paramArrayList == null) {
        signalError();
      }
      return;
    }
    filterOutUndesiredImages(paramArrayList);
    addProducedImagesToCache(paramArrayList);
    if (this.downloader != null)
    {
      String str = "";
      if (getHostUrl() != null) {
        str = getHostUrl();
      }
      CrDb.d("gallery producer", "ASYNC MANAGER: removing async task, finished! " + str);
      tasks.remove(this.downloader);
    }
    if (addImagesToConsumers(paramArrayList))
    {
      CrDb.d("producer", "fetching again");
      this.downloader = null;
      fetch();
    }
    for (;;)
    {
      if (paramBoolean) {
        this.hostImage.updateViews();
      }
      notifyHandlerDataSetsChanged();
      return;
      this.available = true;
    }
  }
  
  protected void postInitialize() {}
  
  protected void preInitialize() {}
  
  protected ArrayList<GalleryImage> produce()
  {
    CrDb.d("producer", "Fetching gallery images for page " + (this.currentPage + 1));
    try
    {
      ArrayList localArrayList = tryToFetchNextBatchOfImages();
      return localArrayList;
    }
    catch (Exception localException)
    {
      return handleFetchingException(localException, true);
    }
  }
  
  public void removeConsumer(GalleryConsumer paramGalleryConsumer)
  {
    if (this.galleryConsumers == null) {
      return;
    }
    this.galleryConsumers.remove(paramGalleryConsumer);
  }
  
  public void requestFetch()
  {
    if (this.hostImage != null) {}
    for (String str = getHostUrl();; str = "null")
    {
      CrDb.d("producer", str + ": trying to start download...");
      if (this.available) {
        break;
      }
      CrDb.d("producer", str + ": not available.");
      return;
    }
    CrDb.d("producer", str + ": download started.");
    this.available = false;
    fetch();
  }
  
  public boolean requestStartFetch()
  {
    if (!canStartFetching()) {
      return false;
    }
    this.available = true;
    requestFetch();
    return true;
  }
  
  public void setCurrentImageFocus(int paramInt)
  {
    if (this.shareable) {
      this.currentImageFocus = paramInt;
    }
  }
  
  protected boolean setGalleryMetadata(String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      this.hostImage.setTitle(paramString1);
    }
    if (paramString2 != null) {
      this.hostImage.setDescription(paramString2);
    }
    if ((paramString1 != null) || (paramString2 != null))
    {
      threadSafeUpdateHostImageViews();
      return true;
    }
    return false;
  }
  
  protected void setSilentRedirectUrl(String paramString)
  {
    this.silentRedirectUrl = paramString;
  }
  
  protected void setStartPage(int paramInt)
  {
    if (this.currentPage != 0) {
      return;
    }
    this.currentPage = paramInt;
  }
  
  public void shareAndSetCurrentImageFocus(int paramInt)
  {
    this.shareable = true;
    setCurrentImageFocus(paramInt);
  }
  
  protected ArrayList<GalleryImage> tryToFetchNextBatchOfImages()
    throws Exception
  {
    this.currentPage += 1;
    if (!this.metadataLoaded) {}
    try
    {
      if (fetchMetadata()) {
        threadSafeUpdateHostImageViews();
      }
      this.metadataLoaded = true;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Analytics.INSTANCE.newException(localException);
      }
    }
    this.fetchingException = null;
    return fetchGalleryImages(this.currentPage - 1);
  }
  
  protected boolean tryToStopDownload()
  {
    return (this.downloader != null) && (this.downloader.cancel(true));
  }
  
  class DownloadGalleryImageTask
    extends AsyncTask<Integer, Void, ArrayList<GalleryImage>>
  {
    private boolean updateHostImageViewsOnEnd;
    
    DownloadGalleryImageTask() {}
    
    protected ArrayList<GalleryImage> doInBackground(Integer... paramVarArgs)
    {
      GalleryProducer.access$002(GalleryProducer.this, false);
      return GalleryProducer.this.produce();
    }
    
    public void flagUpdateHostImageViewsOnEnd()
    {
      this.updateHostImageViewsOnEnd = true;
    }
    
    public String getUrl()
    {
      if (GalleryProducer.this.getHostUrl() == null) {
        return "";
      }
      return GalleryProducer.this.getHostUrl();
    }
    
    protected void onPostExecute(ArrayList<GalleryImage> paramArrayList)
    {
      GalleryProducer.this.onFinishedDownloading(paramArrayList, this.updateHostImageViewsOnEnd);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/producer/GalleryProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */