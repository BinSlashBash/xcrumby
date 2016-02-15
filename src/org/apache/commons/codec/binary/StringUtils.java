package org.apache.commons.codec.binary;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.Charsets;

public class StringUtils
{
  private static byte[] getBytes(String paramString, Charset paramCharset)
  {
    if (paramString == null) {
      return null;
    }
    return paramString.getBytes(paramCharset);
  }
  
  public static byte[] getBytesIso8859_1(String paramString)
  {
    return getBytes(paramString, Charsets.ISO_8859_1);
  }
  
  public static byte[] getBytesUnchecked(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return null;
    }
    try
    {
      paramString1 = paramString1.getBytes(paramString2);
      return paramString1;
    }
    catch (UnsupportedEncodingException paramString1)
    {
      throw newIllegalStateException(paramString2, paramString1);
    }
  }
  
  public static byte[] getBytesUsAscii(String paramString)
  {
    return getBytes(paramString, Charsets.US_ASCII);
  }
  
  public static byte[] getBytesUtf16(String paramString)
  {
    return getBytes(paramString, Charsets.UTF_16);
  }
  
  public static byte[] getBytesUtf16Be(String paramString)
  {
    return getBytes(paramString, Charsets.UTF_16BE);
  }
  
  public static byte[] getBytesUtf16Le(String paramString)
  {
    return getBytes(paramString, Charsets.UTF_16LE);
  }
  
  public static byte[] getBytesUtf8(String paramString)
  {
    return getBytes(paramString, Charsets.UTF_8);
  }
  
  private static IllegalStateException newIllegalStateException(String paramString, UnsupportedEncodingException paramUnsupportedEncodingException)
  {
    return new IllegalStateException(paramString + ": " + paramUnsupportedEncodingException);
  }
  
  public static String newString(byte[] paramArrayOfByte, String paramString)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = new String(paramArrayOfByte, paramString);
      return paramArrayOfByte;
    }
    catch (UnsupportedEncodingException paramArrayOfByte)
    {
      throw newIllegalStateException(paramString, paramArrayOfByte);
    }
  }
  
  private static String newString(byte[] paramArrayOfByte, Charset paramCharset)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    return new String(paramArrayOfByte, paramCharset);
  }
  
  public static String newStringIso8859_1(byte[] paramArrayOfByte)
  {
    return new String(paramArrayOfByte, Charsets.ISO_8859_1);
  }
  
  public static String newStringUsAscii(byte[] paramArrayOfByte)
  {
    return new String(paramArrayOfByte, Charsets.US_ASCII);
  }
  
  public static String newStringUtf16(byte[] paramArrayOfByte)
  {
    return new String(paramArrayOfByte, Charsets.UTF_16);
  }
  
  public static String newStringUtf16Be(byte[] paramArrayOfByte)
  {
    return new String(paramArrayOfByte, Charsets.UTF_16BE);
  }
  
  public static String newStringUtf16Le(byte[] paramArrayOfByte)
  {
    return new String(paramArrayOfByte, Charsets.UTF_16LE);
  }
  
  public static String newStringUtf8(byte[] paramArrayOfByte)
  {
    return newString(paramArrayOfByte, Charsets.UTF_8);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/binary/StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */