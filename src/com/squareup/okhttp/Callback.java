package com.squareup.okhttp;

import java.io.IOException;

public abstract interface Callback
{
  public abstract void onFailure(Request paramRequest, IOException paramIOException);
  
  public abstract void onResponse(Response paramResponse)
    throws IOException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/okhttp/Callback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */