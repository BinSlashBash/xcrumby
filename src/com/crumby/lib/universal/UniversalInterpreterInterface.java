package com.crumby.lib.universal;

import android.webkit.WebView;

public abstract interface UniversalInterpreterInterface
{
  public abstract void onInterfaceInvalidated();
  
  public abstract void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/universal/UniversalInterpreterInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */