package org.json.zip;

import java.io.IOException;

public abstract interface BitReader
{
  public abstract boolean bit()
    throws IOException;
  
  public abstract long nrBits();
  
  public abstract boolean pad(int paramInt)
    throws IOException;
  
  public abstract int read(int paramInt)
    throws IOException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/BitReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */