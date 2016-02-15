package org.apache.commons.codec.binary;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseNCodecInputStream
  extends FilterInputStream
{
  private final BaseNCodec baseNCodec;
  private final BaseNCodec.Context context = new BaseNCodec.Context();
  private final boolean doEncode;
  private final byte[] singleByte = new byte[1];
  
  protected BaseNCodecInputStream(InputStream paramInputStream, BaseNCodec paramBaseNCodec, boolean paramBoolean)
  {
    super(paramInputStream);
    this.doEncode = paramBoolean;
    this.baseNCodec = paramBaseNCodec;
  }
  
  public int available()
    throws IOException
  {
    if (this.context.eof) {
      return 0;
    }
    return 1;
  }
  
  public void mark(int paramInt) {}
  
  public boolean markSupported()
  {
    return false;
  }
  
  public int read()
    throws IOException
  {
    for (int i = read(this.singleByte, 0, 1); i == 0; i = read(this.singleByte, 0, 1)) {}
    if (i > 0)
    {
      int j = this.singleByte[0];
      i = j;
      if (j < 0) {
        i = j + 256;
      }
      return i;
    }
    return -1;
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramArrayOfByte == null) {
      throw new NullPointerException();
    }
    if ((paramInt1 < 0) || (paramInt2 < 0)) {
      throw new IndexOutOfBoundsException();
    }
    if ((paramInt1 > paramArrayOfByte.length) || (paramInt1 + paramInt2 > paramArrayOfByte.length)) {
      throw new IndexOutOfBoundsException();
    }
    int j;
    if (paramInt2 == 0) {
      j = 0;
    }
    int i;
    do
    {
      return j;
      i = 0;
      j = i;
    } while (i != 0);
    label98:
    byte[] arrayOfByte;
    if (!this.baseNCodec.hasData(this.context))
    {
      if (!this.doEncode) {
        break label157;
      }
      i = 4096;
      arrayOfByte = new byte[i];
      i = this.in.read(arrayOfByte);
      if (!this.doEncode) {
        break label165;
      }
      this.baseNCodec.encode(arrayOfByte, 0, i, this.context);
    }
    for (;;)
    {
      i = this.baseNCodec.readResults(paramArrayOfByte, paramInt1, paramInt2, this.context);
      break;
      label157:
      i = 8192;
      break label98;
      label165:
      this.baseNCodec.decode(arrayOfByte, 0, i, this.context);
    }
  }
  
  public void reset()
    throws IOException
  {
    try
    {
      throw new IOException("mark/reset not supported");
    }
    finally {}
  }
  
  public long skip(long paramLong)
    throws IOException
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("Negative skip length: " + paramLong);
    }
    byte[] arrayOfByte = new byte['Ȁ'];
    int i;
    for (long l = paramLong;; l -= i) {
      if (l > 0L)
      {
        i = read(arrayOfByte, 0, (int)Math.min(arrayOfByte.length, l));
        if (i != -1) {}
      }
      else
      {
        return paramLong - l;
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/binary/BaseNCodecInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */