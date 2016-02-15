package org.json.zip;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream
  implements BitReader
{
  static final int[] mask = { 0, 1, 3, 7, 15, 31, 63, 127, 255 };
  private int available = 0;
  private InputStream in;
  private long nrBits = 0L;
  private int unread = 0;
  
  public BitInputStream(InputStream paramInputStream)
  {
    this.in = paramInputStream;
  }
  
  public BitInputStream(InputStream paramInputStream, int paramInt)
  {
    this.in = paramInputStream;
    this.unread = paramInt;
    this.available = 8;
  }
  
  public boolean bit()
    throws IOException
  {
    return read(1) != 0;
  }
  
  public long nrBits()
  {
    return this.nrBits;
  }
  
  public boolean pad(int paramInt)
    throws IOException
  {
    int j = (int)(this.nrBits % paramInt);
    boolean bool = true;
    int i = 0;
    while (i < paramInt - j)
    {
      if (bit()) {
        bool = false;
      }
      i += 1;
    }
    return bool;
  }
  
  public int read(int paramInt)
    throws IOException
  {
    int j;
    if (paramInt == 0)
    {
      j = 0;
      return j;
    }
    if ((paramInt < 0) || (paramInt > 32)) {
      throw new IOException("Bad read width.");
    }
    int i = 0;
    for (;;)
    {
      j = i;
      if (paramInt <= 0) {
        break;
      }
      if (this.available == 0)
      {
        this.unread = this.in.read();
        if (this.unread < 0) {
          throw new IOException("Attempt to read past end.");
        }
        this.available = 8;
      }
      int k = paramInt;
      j = k;
      if (k > this.available) {
        j = this.available;
      }
      i |= (this.unread >>> this.available - j & mask[j]) << paramInt - j;
      this.nrBits += j;
      this.available -= j;
      paramInt -= j;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/BitInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */