package org.json.zip;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream
  implements BitWriter
{
  private long nrBits = 0L;
  private OutputStream out;
  private int unwritten;
  private int vacant = 8;
  
  public BitOutputStream(OutputStream paramOutputStream)
  {
    this.out = paramOutputStream;
  }
  
  public long nrBits()
  {
    return this.nrBits;
  }
  
  public void one()
    throws IOException
  {
    write(1, 1);
  }
  
  public void pad(int paramInt)
    throws IOException
  {
    int i = paramInt - (int)(this.nrBits % paramInt);
    int j = i & 0x7;
    paramInt = i;
    if (j > 0)
    {
      write(0, j);
      paramInt = i - j;
    }
    while (paramInt > 0)
    {
      write(0, 8);
      paramInt -= 8;
    }
    this.out.flush();
  }
  
  public void write(int paramInt1, int paramInt2)
    throws IOException
  {
    if ((paramInt1 == 0) && (paramInt2 == 0)) {}
    for (;;)
    {
      return;
      int i;
      if (paramInt2 > 0)
      {
        i = paramInt2;
        if (paramInt2 <= 32) {}
      }
      else
      {
        throw new IOException("Bad write width.");
      }
      while (i > 0)
      {
        int j = i;
        paramInt2 = j;
        if (j > this.vacant) {
          paramInt2 = this.vacant;
        }
        this.unwritten |= (paramInt1 >>> i - paramInt2 & BitInputStream.mask[paramInt2]) << this.vacant - paramInt2;
        j = i - paramInt2;
        this.nrBits += paramInt2;
        this.vacant -= paramInt2;
        i = j;
        if (this.vacant == 0)
        {
          this.out.write(this.unwritten);
          this.unwritten = 0;
          this.vacant = 8;
          i = j;
        }
      }
    }
  }
  
  public void zero()
    throws IOException
  {
    write(0, 1);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/BitOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */