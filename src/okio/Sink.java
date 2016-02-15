package okio;

import java.io.Closeable;
import java.io.IOException;

public abstract interface Sink
  extends Closeable
{
  public abstract void close()
    throws IOException;
  
  public abstract void flush()
    throws IOException;
  
  public abstract Timeout timeout();
  
  public abstract void write(Buffer paramBuffer, long paramLong)
    throws IOException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/okio/Sink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */