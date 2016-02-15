package com.crumby.lib.universal;

import android.content.Context;
import android.os.Build.VERSION;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.crumby.CrDb;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public enum UniversalInterpreterManager
{
  INSTANCE;
  
  public static String LIBRARY_JS;
  private int MAX_WEBVIEWS_ON_LOAD = 6;
  private Context context;
  private Queue<UniversalInterpreter> interpreters;
  
  private UniversalInterpreterManager() {}
  
  private void addNewInterpreter()
  {
    CrDb.d("universal producer", "Adding New interpreter");
    UniversalInterpreter localUniversalInterpreter = new UniversalInterpreter(this.context);
    WebSettings localWebSettings = localUniversalInterpreter.getSettings();
    if (Build.VERSION.SDK_INT >= 16)
    {
      localWebSettings.setAllowUniversalAccessFromFileURLs(true);
      localWebSettings.setAllowFileAccessFromFileURLs(true);
      if (Build.VERSION.SDK_INT >= 19) {
        WebView.setWebContentsDebuggingEnabled(true);
      }
    }
    localWebSettings.setAllowFileAccess(true);
    localWebSettings.setAllowContentAccess(true);
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setDomStorageEnabled(true);
    this.interpreters.add(localUniversalInterpreter);
  }
  
  public UniversalInterpreter getInterpreter()
  {
    UniversalInterpreter localUniversalInterpreter = (UniversalInterpreter)this.interpreters.remove();
    this.interpreters.add(localUniversalInterpreter);
    localUniversalInterpreter.forceInvalidateInterface();
    return localUniversalInterpreter;
  }
  
  public String getLibraryJS()
  {
    return LIBRARY_JS;
  }
  
  public void initialize(Context paramContext)
  {
    this.context = paramContext;
    try
    {
      LIBRARY_JS = GalleryProducer.readStreamIntoString(paramContext.openFileInput("library.min.js"));
      this.interpreters = new ArrayDeque();
      int i = 0;
      while (i < this.MAX_WEBVIEWS_ON_LOAD)
      {
        addNewInterpreter();
        i += 1;
      }
    }
    catch (IOException paramContext)
    {
      for (;;)
      {
        paramContext.printStackTrace();
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/universal/UniversalInterpreterManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */