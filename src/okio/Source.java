package okio;

import java.io.Closeable;
import java.io.IOException;

public abstract interface Source
  extends Closeable
{
  public abstract void close()
    throws IOException;
  
  public abstract long read(Buffer paramBuffer, long paramLong)
    throws IOException;
  
  public abstract Timeout timeout();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/okio/Source.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */