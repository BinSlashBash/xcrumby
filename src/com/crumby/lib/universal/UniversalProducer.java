package com.crumby.lib.universal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class UniversalProducer
  extends GalleryProducer
  implements UniversalInterpreterInterface
{
  private static final int COULD_NOT_PARSE = 0;
  private static final long ONE_DAY = 86400000L;
  private static final int TIMEOUT = 1;
  private static Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
    }
  };
  protected static ObjectMapper mapper;
  private static Map<String, ScriptReference> scripts = new HashMap();
  private boolean finished;
  private UniversalInterpreter interpreter;
  
  static
  {
    mapper = new ObjectMapper();
  }
  
  private void checkPageNodeForError(JsonNode paramJsonNode)
    throws Exception
  {
    if (!paramJsonNode.has("error")) {
      return;
    }
    localObject = paramJsonNode.get("error");
    str = "Crumby could not download and/or parse this page.";
    for (;;)
    {
      try
      {
        boolean bool = ((JsonNode)localObject).get("type").asText().equals("async");
        if (!bool) {
          continue;
        }
      }
      catch (NullPointerException paramJsonNode)
      {
        int i;
        localObject = ((JsonNode)localObject).asText();
        paramJsonNode = str;
        if (localObject == null) {
          continue;
        }
        paramJsonNode = str;
        if (((String)localObject).equals("")) {
          continue;
        }
        paramJsonNode = (JsonNode)localObject;
        continue;
      }
      try
      {
        i = paramJsonNode.get("status").asInt(-1);
        if (i != 404) {
          continue;
        }
        paramJsonNode = "404, could not find the page you're looking for";
      }
      catch (NullPointerException paramJsonNode)
      {
        paramJsonNode = "Could not connect to website.";
        continue;
      }
      throw new UniversalProducerException(paramJsonNode);
      paramJsonNode = str;
      if (i == 503)
      {
        paramJsonNode = "The website has restricted you from seeing this image.";
        continue;
        i = ((JsonNode)localObject).get("status").asInt();
        if (i == 0)
        {
          paramJsonNode = "Crumby could not parse this page.";
        }
        else
        {
          paramJsonNode = str;
          if (i == 1) {
            paramJsonNode = "Crumby took way too long to get this image for you. Sorry :(";
          }
        }
      }
    }
  }
  
  private void deferInjectScriptAndLoadRemote(final int paramInt)
  {
    CrumbyApp.getHttpClient().newCall(new Request.Builder().url("http://i.getcrumby.com/crumby/" + getScriptFilenameOnServer()).build()).enqueue(new Callback()
    {
      public void onFailure(Request paramAnonymousRequest, IOException paramAnonymousIOException)
      {
        UniversalProducer.this.failedToLoadRemoteScript(paramInt);
      }
      
      public void onResponse(Response paramAnonymousResponse)
        throws IOException
      {
        if (UniversalProducer.this.interpreter == null) {
          return;
        }
        if (!paramAnonymousResponse.isSuccessful())
        {
          UniversalProducer.this.failedToLoadRemoteScript(paramInt);
          return;
        }
        Analytics.INSTANCE.newEvent(AnalyticsCategories.UNIVERSAL, "phone home", UniversalProducer.this.getScriptFileName() + " " + UniversalProducer.this.getHostUrl());
        CrDb.d("universal producer", "loaded script from server! " + UniversalProducer.this.getScriptFilenameOnServer());
        UniversalProducer.this.saveScript(UniversalProducer.this.interpreter.getContext(), paramAnonymousResponse.body().string());
        UniversalProducer.handler.post(new Runnable()
        {
          public void run()
          {
            UniversalProducer.this.injectScriptIntoInterpreter(UniversalProducer.4.this.val$pageNumber);
          }
        });
      }
    });
  }
  
  private void failedToLoadRemoteScript(final int paramInt)
  {
    if (this.interpreter == null) {
      return;
    }
    CrDb.d("universal producer", "could not load script (using assets): " + getScriptFilenameOnServer());
    try
    {
      putScript(GalleryProducer.readStreamIntoString(this.interpreter.getContext().openFileInput(getScriptFileName())));
      handler.post(new Runnable()
      {
        public void run()
        {
          UniversalProducer.this.injectScriptIntoInterpreter(paramInt);
        }
      });
      return;
    }
    catch (IOException localIOException)
    {
      Analytics.INSTANCE.newException(new Exception("Universal phone home failed: " + getScriptFileName() + " " + getHostUrl() + " " + localIOException.getMessage(), localIOException));
      if (Thread.currentThread() == Looper.getMainLooper().getThread())
      {
        webviewEncounteredAnException(localIOException);
        return;
      }
      handler.post(new Runnable()
      {
        public void run()
        {
          UniversalProducer.this.webviewEncounteredAnException(localIOException);
        }
      });
    }
  }
  
  private String getScriptFileName()
  {
    return getScriptName() + ".min.js";
  }
  
  private String getScriptFilenameOnServer()
  {
    return "js/" + "45/" + getScriptFileName();
  }
  
  private UniversalInterpreterInterface getThis()
  {
    return this;
  }
  
  private boolean hasLoadedMostCurrentScript()
  {
    ScriptReference localScriptReference = (ScriptReference)scripts.get(getScriptName());
    return (localScriptReference != null) && (System.currentTimeMillis() - localScriptReference.lastUpdated <= 86400000L);
  }
  
  private void injectScriptIntoInterpreter(int paramInt)
  {
    String str3 = ((ScriptReference)scripts.get(getScriptName())).script;
    if (str3 == null) {
      webviewEncounteredAnException(new Exception("Uh oh, some of Crumby's files got corrupted. Please restart your app!"));
    }
    if (this.interpreter == null) {
      return;
    }
    String str2 = getMatchingId();
    String str1 = str2;
    if (str2 != null) {
      str1 = "'" + str2 + "'";
    }
    str1 = "var matchedId = " + str1 + ";";
    this.interpreter.loadDataWithBaseURL(getBaseUrl(), "<html><body><iframe id='haxx'></iframe><div id='haxx1'></div>\n<script type='text/javascript'>" + UniversalInterpreterManager.INSTANCE.getLibraryJS() + "</script>" + "<script type='text/javascript'>" + "var pageNumber = " + paramInt + "; " + "var hostUrl = '" + getHostUrl() + "'; " + str1 + " " + getExtraScript() + " " + "</script>" + "<script type='text/javascript'>" + str3 + "</script>" + "</body>\n" + "</html>", "text/html", "utf-8", null);
  }
  
  private void putScript(String paramString)
  {
    scripts.put(getScriptName(), new ScriptReference(paramString, System.currentTimeMillis()));
  }
  
  private boolean removeInterpreter()
  {
    if (this.interpreter != null)
    {
      this.interpreter.removeInterface(getThis());
      this.interpreter = null;
      return true;
    }
    return false;
  }
  
  private void saveScript(Context paramContext, String paramString)
    throws IOException
  {
    paramContext.openFileOutput(getScriptFileName(), 0).write(paramString.getBytes());
    putScript(paramString);
  }
  
  private void webviewEncounteredAnException(Exception paramException)
  {
    removeInterpreter();
    handleFetchingException(paramException, true);
    Analytics.INSTANCE.newException(paramException);
    onFinishedDownloading(null, true);
  }
  
  protected ArrayList<GalleryImage> convertJsonNodeToGalleryImages(JsonNode paramJsonNode)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = paramJsonNode.size();
    if (i < j)
    {
      GalleryImage localGalleryImage = (GalleryImage)mapper.readValue(paramJsonNode.get(i).toString(), GalleryImage.class);
      if (localGalleryImage == null) {}
      for (;;)
      {
        i += 1;
        break;
        if (localGalleryImage.getHeight() == 0) {
          localGalleryImage.setHeight(150);
        }
        localArrayList.add(localGalleryImage);
      }
    }
    return localArrayList;
  }
  
  protected void fetch()
  {
    if (this.interpreter != null)
    {
      CrDb.d("crumby producer", getHostUrl() + " IS FETCHING! CANNOT FETCH AGAIN!");
      return;
    }
    produce();
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    this.interpreter = UniversalInterpreterManager.INSTANCE.getInterpreter();
    this.interpreter.setInterface(this);
    this.finished = false;
    if (hasLoadedMostCurrentScript()) {
      injectScriptIntoInterpreter(paramInt);
    }
    for (;;)
    {
      return null;
      deferInjectScriptAndLoadRemote(paramInt);
    }
  }
  
  @JavascriptInterface
  public void finish(final String paramString)
    throws Exception
  {
    if (this.finished)
    {
      CrDb.d("universal producer", "finished! will not accept anymore json!");
      return;
    }
    this.finished = true;
    handler.post(new Runnable()
    {
      public void run()
      {
        try
        {
          Object localObject = UniversalProducer.mapper.readTree(paramString);
          UniversalProducer.this.checkPageNodeForError((JsonNode)localObject);
          localObject = UniversalProducer.this.getImagesFromJson((JsonNode)localObject);
          UniversalProducer.this.onFinishedDownloading((ArrayList)localObject, false);
          return;
        }
        catch (Exception localException)
        {
          UniversalProducer.this.webviewEncounteredAnException(localException);
        }
      }
    });
  }
  
  protected abstract String getBaseUrl();
  
  protected String getExtraScript()
  {
    return "";
  }
  
  protected ArrayList<GalleryImage> getImagesFromJson(JsonNode paramJsonNode)
    throws Exception
  {
    GalleryImage localGalleryImage = (GalleryImage)mapper.readValue(paramJsonNode.get("hostImage").toString(), GalleryImage.class);
    if (getHostImage() != null)
    {
      getHostImage().copy(localGalleryImage);
      getHostImage().updateViews();
    }
    return convertJsonNodeToGalleryImages(paramJsonNode.get("images"));
  }
  
  public String getMatchingId()
  {
    String str = null;
    if (getRegexForMatchingId() != null) {
      str = GalleryViewerFragment.matchIdFromUrl(getRegexForMatchingId(), getHostUrl());
    }
    return str;
  }
  
  protected abstract String getRegexForMatchingId();
  
  protected abstract String getScriptName();
  
  protected void onFinishedDownloading(ArrayList<GalleryImage> paramArrayList, boolean paramBoolean)
  {
    removeInterpreter();
    super.onFinishedDownloading(paramArrayList, paramBoolean);
  }
  
  public void onInterfaceInvalidated() {}
  
  public void onReceivedError(WebView paramWebView, int paramInt, final String paramString1, final String paramString2)
  {
    if (Thread.currentThread() != Looper.getMainLooper().getThread())
    {
      handler.post(new Runnable()
      {
        public void run()
        {
          UniversalProducer.this.webviewEncounteredAnException(new Exception(paramString1 + "@" + paramString2));
        }
      });
      return;
    }
    webviewEncounteredAnException(new Exception(paramString1 + "@" + paramString2));
  }
  
  protected boolean tryToStopDownload()
  {
    return removeInterpreter();
  }
  
  class ScriptReference
  {
    long lastUpdated;
    String script;
    
    public ScriptReference(String paramString, long paramLong)
    {
      this.script = paramString;
      this.lastUpdated = paramLong;
    }
  }
  
  private class UniversalProducerException
    extends Exception
  {
    public UniversalProducerException(String paramString)
    {
      super();
    }
    
    public String toString()
    {
      return getMessage();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/universal/UniversalProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */