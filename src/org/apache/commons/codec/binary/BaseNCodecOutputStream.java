package org.apache.commons.codec.binary;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BaseNCodecOutputStream
  extends FilterOutputStream
{
  private final BaseNCodec baseNCodec;
  private final BaseNCodec.Context context = new BaseNCodec.Context();
  private final boolean doEncode;
  private final byte[] singleByte = new byte[1];
  
  public BaseNCodecOutputStream(OutputStream paramOutputStream, BaseNCodec paramBaseNCodec, boolean paramBoolean)
  {
    super(paramOutputStream);
    this.baseNCodec = paramBaseNCodec;
    this.doEncode = paramBoolean;
  }
  
  private void flush(boolean paramBoolean)
    throws IOException
  {
    int i = this.baseNCodec.available(this.context);
    if (i > 0)
    {
      byte[] arrayOfByte = new byte[i];
      i = this.baseNCodec.readResults(arrayOfByte, 0, i, this.context);
      if (i > 0) {
        this.out.write(arrayOfByte, 0, i);
      }
    }
    if (paramBoolean) {
      this.out.flush();
    }
  }
  
  public void close()
    throws IOException
  {
    if (this.doEncode) {
      this.baseNCodec.encode(this.singleByte, 0, -1, this.context);
    }
    for (;;)
    {
      flush();
      this.out.close();
      return;
      this.baseNCodec.decode(this.singleByte, 0, -1, this.context);
    }
  }
  
  public void flush()
    throws IOException
  {
    flush(true);
  }
  
  public void write(int paramInt)
    throws IOException
  {
    this.singleByte[0] = ((byte)paramInt);
    write(this.singleByte, 0, 1);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
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
    if (paramInt2 > 0)
    {
      if (!this.doEncode) {
        break label81;
      }
      this.baseNCodec.encode(paramArrayOfByte, paramInt1, paramInt2, this.context);
    }
    for (;;)
    {
      flush(false);
      return;
      label81:
      this.baseNCodec.decode(paramArrayOfByte, paramInt1, paramInt2, this.context);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/binary/BaseNCodecOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */