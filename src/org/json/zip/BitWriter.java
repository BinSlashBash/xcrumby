package org.json.zip;

import java.io.IOException;

public abstract interface BitWriter
{
  public abstract long nrBits();
  
  public abstract void one()
    throws IOException;
  
  public abstract void pad(int paramInt)
    throws IOException;
  
  public abstract void write(int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract void zero()
    throws IOException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/BitWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */