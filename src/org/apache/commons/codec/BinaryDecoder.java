package org.apache.commons.codec;

public abstract interface BinaryDecoder
  extends Decoder
{
  public abstract byte[] decode(byte[] paramArrayOfByte)
    throws DecoderException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/BinaryDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */