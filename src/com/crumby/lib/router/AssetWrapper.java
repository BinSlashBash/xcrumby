package com.crumby.lib.router;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

public class AssetWrapper
  implements FileOpener
{
  private AssetManager manager;
  
  public AssetWrapper(AssetManager paramAssetManager)
  {
    this.manager = paramAssetManager;
  }
  
  public InputStream open(String paramString)
  {
    try
    {
      paramString = this.manager.open(paramString);
      return paramString;
    }
    catch (IOException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/AssetWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */