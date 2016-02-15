package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.BitSet;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;

public class QCodec
  extends RFC1522Codec
  implements StringEncoder, StringDecoder
{
  private static final byte BLANK = 32;
  private static final BitSet PRINTABLE_CHARS = new BitSet(256);
  private static final byte UNDERSCORE = 95;
  private final Charset charset;
  private boolean encodeBlanks = false;
  
  static
  {
    PRINTABLE_CHARS.set(32);
    PRINTABLE_CHARS.set(33);
    PRINTABLE_CHARS.set(34);
    PRINTABLE_CHARS.set(35);
    PRINTABLE_CHARS.set(36);
    PRINTABLE_CHARS.set(37);
    PRINTABLE_CHARS.set(38);
    PRINTABLE_CHARS.set(39);
    PRINTABLE_CHARS.set(40);
    PRINTABLE_CHARS.set(41);
    PRINTABLE_CHARS.set(42);
    PRINTABLE_CHARS.set(43);
    PRINTABLE_CHARS.set(44);
    PRINTABLE_CHARS.set(45);
    PRINTABLE_CHARS.set(46);
    PRINTABLE_CHARS.set(47);
    int i = 48;
    while (i <= 57)
    {
      PRINTABLE_CHARS.set(i);
      i += 1;
    }
    PRINTABLE_CHARS.set(58);
    PRINTABLE_CHARS.set(59);
    PRINTABLE_CHARS.set(60);
    PRINTABLE_CHARS.set(62);
    PRINTABLE_CHARS.set(64);
    i = 65;
    while (i <= 90)
    {
      PRINTABLE_CHARS.set(i);
      i += 1;
    }
    PRINTABLE_CHARS.set(91);
    PRINTABLE_CHARS.set(92);
    PRINTABLE_CHARS.set(93);
    PRINTABLE_CHARS.set(94);
    PRINTABLE_CHARS.set(96);
    i = 97;
    while (i <= 122)
    {
      PRINTABLE_CHARS.set(i);
      i += 1;
    }
    PRINTABLE_CHARS.set(123);
    PRINTABLE_CHARS.set(124);
    PRINTABLE_CHARS.set(125);
    PRINTABLE_CHARS.set(126);
  }
  
  public QCodec()
  {
    this(Charsets.UTF_8);
  }
  
  public QCodec(String paramString)
  {
    this(Charset.forName(paramString));
  }
  
  public QCodec(Charset paramCharset)
  {
    this.charset = paramCharset;
  }
  
  public Object decode(Object paramObject)
    throws DecoderException
  {
    if (paramObject == null) {
      return null;
    }
    if ((paramObject instanceof String)) {
      return decode((String)paramObject);
    }
    throw new DecoderException("Objects of type " + paramObject.getClass().getName() + " cannot be decoded using Q codec");
  }
  
  public String decode(String paramString)
    throws DecoderException
  {
    if (paramString == null) {
      return null;
    }
    try
    {
      paramString = decodeText(paramString);
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new DecoderException(paramString.getMessage(), paramString);
    }
  }
  
  protected byte[] doDecoding(byte[] paramArrayOfByte)
    throws DecoderException
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    int m = 0;
    int n = paramArrayOfByte.length;
    int j = 0;
    int k = m;
    byte[] arrayOfByte;
    if (j < n)
    {
      if (paramArrayOfByte[j] == 95) {
        k = 1;
      }
    }
    else
    {
      if (k == 0) {
        break label99;
      }
      arrayOfByte = new byte[paramArrayOfByte.length];
      j = 0;
      label49:
      if (j >= paramArrayOfByte.length) {
        break label93;
      }
      int i = paramArrayOfByte[j];
      if (i == 95) {
        break label84;
      }
      arrayOfByte[j] = i;
    }
    for (;;)
    {
      j += 1;
      break label49;
      j += 1;
      break;
      label84:
      arrayOfByte[j] = 32;
    }
    label93:
    return QuotedPrintableCodec.decodeQuotedPrintable(arrayOfByte);
    label99:
    return QuotedPrintableCodec.decodeQuotedPrintable(paramArrayOfByte);
  }
  
  protected byte[] doEncoding(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      paramArrayOfByte = null;
    }
    byte[] arrayOfByte;
    do
    {
      return paramArrayOfByte;
      arrayOfByte = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, paramArrayOfByte);
      paramArrayOfByte = arrayOfByte;
    } while (!this.encodeBlanks);
    int i = 0;
    for (;;)
    {
      paramArrayOfByte = arrayOfByte;
      if (i >= arrayOfByte.length) {
        break;
      }
      if (arrayOfByte[i] == 32) {
        arrayOfByte[i] = 95;
      }
      i += 1;
    }
  }
  
  public Object encode(Object paramObject)
    throws EncoderException
  {
    if (paramObject == null) {
      return null;
    }
    if ((paramObject instanceof String)) {
      return encode((String)paramObject);
    }
    throw new EncoderException("Objects of type " + paramObject.getClass().getName() + " cannot be encoded using Q codec");
  }
  
  public String encode(String paramString)
    throws EncoderException
  {
    if (paramString == null) {
      return null;
    }
    return encode(paramString, getCharset());
  }
  
  public String encode(String paramString1, String paramString2)
    throws EncoderException
  {
    if (paramString1 == null) {
      return null;
    }
    try
    {
      paramString1 = encodeText(paramString1, paramString2);
      return paramString1;
    }
    catch (UnsupportedEncodingException paramString1)
    {
      throw new EncoderException(paramString1.getMessage(), paramString1);
    }
  }
  
  public String encode(String paramString, Charset paramCharset)
    throws EncoderException
  {
    if (paramString == null) {
      return null;
    }
    return encodeText(paramString, paramCharset);
  }
  
  public Charset getCharset()
  {
    return this.charset;
  }
  
  public String getDefaultCharset()
  {
    return this.charset.name();
  }
  
  protected String getEncoding()
  {
    return "Q";
  }
  
  public boolean isEncodeBlanks()
  {
    return this.encodeBlanks;
  }
  
  public void setEncodeBlanks(boolean paramBoolean)
  {
    this.encodeBlanks = paramBoolean;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/net/QCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */