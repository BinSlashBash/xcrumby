package com.crumby.lib.router;

import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract interface FileOpener
{
  public abstract InputStream open(String paramString)
    throws FileNotFoundException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/FileOpener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */