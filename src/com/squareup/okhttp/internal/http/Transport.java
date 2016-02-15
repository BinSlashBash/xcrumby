package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response.Builder;
import java.io.IOException;
import java.net.CacheRequest;
import okio.Sink;
import okio.Source;

public abstract interface Transport
{
  public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;
  
  public abstract boolean canReuseConnection();
  
  public abstract Sink createRequestBody(Request paramRequest)
    throws IOException;
  
  public abstract void disconnect(HttpEngine paramHttpEngine)
    throws IOException;
  
  public abstract void emptyTransferStream()
    throws IOException;
  
  public abstract void flushRequest()
    throws IOException;
  
  public abstract Source getTransferStream(CacheRequest paramCacheRequest)
    throws IOException;
  
  public abstract Response.Builder readResponseHeaders()
    throws IOException;
  
  public abstract void releaseConnectionOnIdle()
    throws IOException;
  
  public abstract void writeRequestBody(RetryableSink paramRetryableSink)
    throws IOException;
  
  public abstract void writeRequestHeaders(Request paramRequest)
    throws IOException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/okhttp/internal/http/Transport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */