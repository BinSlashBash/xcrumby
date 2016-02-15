package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.Base64;

public class BCodec
  extends RFC1522Codec
  implements StringEncoder, StringDecoder
{
  private final Charset charset;
  
  public BCodec()
  {
    this(Charsets.UTF_8);
  }
  
  public BCodec(String paramString)
  {
    this(Charset.forName(paramString));
  }
  
  public BCodec(Charset paramCharset)
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
    throw new DecoderException("Objects of type " + paramObject.getClass().getName() + " cannot be decoded using BCodec");
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
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    return Base64.decodeBase64(paramArrayOfByte);
  }
  
  protected byte[] doEncoding(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    return Base64.encodeBase64(paramArrayOfByte);
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
    throw new EncoderException("Objects of type " + paramObject.getClass().getName() + " cannot be encoded using BCodec");
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
    return "B";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/net/BCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */