package org.apache.commons.codec.binary;

import java.nio.charset.Charset;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;

public class Hex
  implements BinaryEncoder, BinaryDecoder
{
  public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
  public static final String DEFAULT_CHARSET_NAME = "UTF-8";
  private static final char[] DIGITS_LOWER = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  private static final char[] DIGITS_UPPER = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
  private final Charset charset;
  
  public Hex()
  {
    this.charset = DEFAULT_CHARSET;
  }
  
  public Hex(String paramString)
  {
    this(Charset.forName(paramString));
  }
  
  public Hex(Charset paramCharset)
  {
    this.charset = paramCharset;
  }
  
  public static byte[] decodeHex(char[] paramArrayOfChar)
    throws DecoderException
  {
    int k = paramArrayOfChar.length;
    if ((k & 0x1) != 0) {
      throw new DecoderException("Odd number of characters.");
    }
    byte[] arrayOfByte = new byte[k >> 1];
    int i = 0;
    int j = 0;
    while (j < k)
    {
      int m = toDigit(paramArrayOfChar[j], j);
      j += 1;
      int n = toDigit(paramArrayOfChar[j], j);
      j += 1;
      arrayOfByte[i] = ((byte)((m << 4 | n) & 0xFF));
      i += 1;
    }
    return arrayOfByte;
  }
  
  public static char[] encodeHex(byte[] paramArrayOfByte)
  {
    return encodeHex(paramArrayOfByte, true);
  }
  
  public static char[] encodeHex(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (char[] arrayOfChar = DIGITS_LOWER;; arrayOfChar = DIGITS_UPPER) {
      return encodeHex(paramArrayOfByte, arrayOfChar);
    }
  }
  
  protected static char[] encodeHex(byte[] paramArrayOfByte, char[] paramArrayOfChar)
  {
    int k = paramArrayOfByte.length;
    char[] arrayOfChar = new char[k << 1];
    int i = 0;
    int j = 0;
    while (i < k)
    {
      int m = j + 1;
      arrayOfChar[j] = paramArrayOfChar[((paramArrayOfByte[i] & 0xF0) >>> 4)];
      j = m + 1;
      arrayOfChar[m] = paramArrayOfChar[(paramArrayOfByte[i] & 0xF)];
      i += 1;
    }
    return arrayOfChar;
  }
  
  public static String encodeHexString(byte[] paramArrayOfByte)
  {
    return new String(encodeHex(paramArrayOfByte));
  }
  
  protected static int toDigit(char paramChar, int paramInt)
    throws DecoderException
  {
    int i = Character.digit(paramChar, 16);
    if (i == -1) {
      throw new DecoderException("Illegal hexadecimal character " + paramChar + " at index " + paramInt);
    }
    return i;
  }
  
  /* Error */
  public Object decode(Object paramObject)
    throws DecoderException
  {
    // Byte code:
    //   0: aload_1
    //   1: instanceof 94
    //   4: ifeq +16 -> 20
    //   7: aload_1
    //   8: checkcast 94	java/lang/String
    //   11: invokevirtual 133	java/lang/String:toCharArray	()[C
    //   14: astore_1
    //   15: aload_1
    //   16: invokestatic 135	org/apache/commons/codec/binary/Hex:decodeHex	([C)[B
    //   19: areturn
    //   20: aload_1
    //   21: checkcast 136	[C
    //   24: checkcast 136	[C
    //   27: astore_1
    //   28: goto -13 -> 15
    //   31: astore_1
    //   32: new 73	org/apache/commons/codec/DecoderException
    //   35: dup
    //   36: aload_1
    //   37: invokevirtual 139	java/lang/ClassCastException:getMessage	()Ljava/lang/String;
    //   40: aload_1
    //   41: invokespecial 142	org/apache/commons/codec/DecoderException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   44: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	45	0	this	Hex
    //   0	45	1	paramObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	15	31	java/lang/ClassCastException
    //   15	20	31	java/lang/ClassCastException
    //   20	28	31	java/lang/ClassCastException
  }
  
  public byte[] decode(byte[] paramArrayOfByte)
    throws DecoderException
  {
    return decodeHex(new String(paramArrayOfByte, getCharset()).toCharArray());
  }
  
  /* Error */
  public Object encode(Object paramObject)
    throws org.apache.commons.codec.EncoderException
  {
    // Byte code:
    //   0: aload_1
    //   1: instanceof 94
    //   4: ifeq +20 -> 24
    //   7: aload_1
    //   8: checkcast 94	java/lang/String
    //   11: aload_0
    //   12: invokevirtual 147	org/apache/commons/codec/binary/Hex:getCharset	()Ljava/nio/charset/Charset;
    //   15: invokevirtual 157	java/lang/String:getBytes	(Ljava/nio/charset/Charset;)[B
    //   18: astore_1
    //   19: aload_1
    //   20: invokestatic 96	org/apache/commons/codec/binary/Hex:encodeHex	([B)[C
    //   23: areturn
    //   24: aload_1
    //   25: checkcast 159	[B
    //   28: checkcast 159	[B
    //   31: astore_1
    //   32: goto -13 -> 19
    //   35: astore_1
    //   36: new 153	org/apache/commons/codec/EncoderException
    //   39: dup
    //   40: aload_1
    //   41: invokevirtual 139	java/lang/ClassCastException:getMessage	()Ljava/lang/String;
    //   44: aload_1
    //   45: invokespecial 160	org/apache/commons/codec/EncoderException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   48: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	49	0	this	Hex
    //   0	49	1	paramObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	19	35	java/lang/ClassCastException
    //   19	24	35	java/lang/ClassCastException
    //   24	32	35	java/lang/ClassCastException
  }
  
  public byte[] encode(byte[] paramArrayOfByte)
  {
    return encodeHexString(paramArrayOfByte).getBytes(getCharset());
  }
  
  public Charset getCharset()
  {
    return this.charset;
  }
  
  public String getCharsetName()
  {
    return this.charset.name();
  }
  
  public String toString()
  {
    return super.toString() + "[charsetName=" + this.charset + "]";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/binary/Hex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */