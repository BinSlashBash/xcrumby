package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class OkHttpDownloader
  implements Downloader
{
  static final String RESPONSE_SOURCE_ANDROID = "X-Android-Response-Source";
  static final String RESPONSE_SOURCE_OKHTTP = "OkHttp-Response-Source";
  private final OkUrlFactory urlFactory;
  
  public OkHttpDownloader(Context paramContext)
  {
    this(Utils.createDefaultCacheDir(paramContext));
  }
  
  public OkHttpDownloader(Context paramContext, long paramLong)
  {
    this(Utils.createDefaultCacheDir(paramContext), paramLong);
  }
  
  public OkHttpDownloader(OkHttpClient paramOkHttpClient)
  {
    this.urlFactory = new OkUrlFactory(paramOkHttpClient);
  }
  
  public OkHttpDownloader(File paramFile)
  {
    this(paramFile, Utils.calculateDiskCacheSize(paramFile));
  }
  
  public OkHttpDownloader(File paramFile, long paramLong)
  {
    this(new OkHttpClient());
    try
    {
      this.urlFactory.client().setCache(new Cache(paramFile, paramLong));
      return;
    }
    catch (IOException paramFile) {}
  }
  
  protected OkHttpClient getClient()
  {
    return this.urlFactory.client();
  }
  
  public Downloader.Response load(Uri paramUri, boolean paramBoolean)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = openConnection(paramUri);
    localHttpURLConnection.setUseCaches(true);
    localHttpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
    if (paramBoolean) {
      localHttpURLConnection.setRequestProperty("Cache-Control", "only-if-cached,max-age=2147483647");
    }
    int i = localHttpURLConnection.getResponseCode();
    if (i >= 300)
    {
      localHttpURLConnection.disconnect();
      throw new Downloader.ResponseException(i + " " + localHttpURLConnection.getResponseMessage());
    }
    String str = localHttpURLConnection.getHeaderField("OkHttp-Response-Source");
    paramUri = str;
    if (str == null) {
      paramUri = localHttpURLConnection.getHeaderField("X-Android-Response-Source");
    }
    long l = localHttpURLConnection.getHeaderFieldInt("Content-Length", -1);
    paramBoolean = Utils.parseResponseSourceHeader(paramUri);
    return new Downloader.Response(localHttpURLConnection.getInputStream(), paramBoolean, l);
  }
  
  protected HttpURLConnection openConnection(Uri paramUri)
    throws IOException
  {
    paramUri = this.urlFactory.open(new URL(paramUri.toString()));
    paramUri.setConnectTimeout(15000);
    paramUri.setReadTimeout(20000);
    return paramUri;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/picasso/OkHttpDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */