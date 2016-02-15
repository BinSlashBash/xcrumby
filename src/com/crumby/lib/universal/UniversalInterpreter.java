package com.crumby.lib.universal;

import android.content.Context;
import android.net.http.SslError;
import android.os.Looper;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.crumby.CrDb;

public class UniversalInterpreter
  extends WebView
{
  private UniversalInterpreterInterface javascriptInterface;
  
  public UniversalInterpreter(Context paramContext)
  {
    super(paramContext);
  }
  
  public UniversalInterpreter(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public UniversalInterpreter(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public UniversalInterpreter(Context paramContext, AttributeSet paramAttributeSet, int paramInt, boolean paramBoolean)
  {
    super(paramContext, paramAttributeSet, paramInt, paramBoolean);
  }
  
  private void invalidateInterface()
  {
    if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
      CrDb.d(getClass().getSimpleName(), "INVALIDATE");
    }
    this.javascriptInterface = null;
    removeJavascriptInterface("Crumby");
  }
  
  public void forceInvalidateInterface()
  {
    if (this.javascriptInterface == null) {
      return;
    }
    this.javascriptInterface.onInterfaceInvalidated();
    invalidateInterface();
  }
  
  public UniversalInterpreterInterface getInterface()
  {
    return this.javascriptInterface;
  }
  
  public void removeInterface(UniversalInterpreterInterface paramUniversalInterpreterInterface)
  {
    if (paramUniversalInterpreterInterface == this.javascriptInterface) {
      invalidateInterface();
    }
  }
  
  public void setInterface(UniversalInterpreterInterface paramUniversalInterpreterInterface)
  {
    if (this.javascriptInterface != null) {
      removeJavascriptInterface("Crumby");
    }
    if (paramUniversalInterpreterInterface == null) {
      return;
    }
    this.javascriptInterface = paramUniversalInterpreterInterface;
    setWebViewClient(new WebViewClient()
    {
      public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
      {
        UniversalInterpreter.this.javascriptInterface.onReceivedError(paramAnonymousWebView, paramAnonymousInt, paramAnonymousString1, paramAnonymousString2);
      }
      
      public void onReceivedSslError(WebView paramAnonymousWebView, SslErrorHandler paramAnonymousSslErrorHandler, SslError paramAnonymousSslError)
      {
        paramAnonymousSslErrorHandler.proceed();
      }
    });
    addJavascriptInterface(this.javascriptInterface, "Crumby");
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/universal/UniversalInterpreter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */