package org.json;

import java.util.Arrays;

public class Kim
{
  private byte[] bytes = null;
  private int hashcode = 0;
  public int length = 0;
  private String string = null;
  
  public Kim(String paramString)
    throws JSONException
  {
    int i2 = paramString.length();
    this.hashcode = 0;
    this.length = 0;
    if (i2 > 0)
    {
      int i = 0;
      int k;
      if (i < i2)
      {
        k = paramString.charAt(i);
        if (k <= 127) {
          this.length += 1;
        }
        for (;;)
        {
          i += 1;
          break;
          if (k <= 16383)
          {
            this.length += 2;
          }
          else
          {
            j = i;
            if (k >= 55296)
            {
              j = i;
              if (k <= 57343)
              {
                j = i + 1;
                i = paramString.charAt(j);
                if ((k > 56319) || (i < 56320) || (i > 57343)) {
                  throw new JSONException("Bad UTF16");
                }
              }
            }
            this.length += 3;
            i = j;
          }
        }
      }
      this.bytes = new byte[this.length];
      int j = 0;
      int m = 1;
      i = 0;
      if (i < i2)
      {
        int i1 = paramString.charAt(i);
        if (i1 <= 127)
        {
          this.bytes[j] = ((byte)i1);
          k = m + i1;
          this.hashcode += k;
          j += 1;
        }
        for (;;)
        {
          i += 1;
          m = k;
          break;
          if (i1 <= 16383)
          {
            k = i1 >>> 7 | 0x80;
            this.bytes[j] = ((byte)k);
            k = m + k;
            this.hashcode += k;
            j += 1;
            m = i1 & 0x7F;
            this.bytes[j] = ((byte)m);
            k += m;
            this.hashcode += k;
            j += 1;
          }
          else
          {
            int n = i1;
            k = i;
            if (i1 >= 55296)
            {
              n = i1;
              k = i;
              if (i1 <= 56319)
              {
                k = i + 1;
                n = ((i1 & 0x3FF) << 10 | paramString.charAt(k) & 0x3FF) + 65536;
              }
            }
            i = n >>> 14 | 0x80;
            this.bytes[j] = ((byte)i);
            i = m + i;
            this.hashcode += i;
            j += 1;
            m = n >>> 7 & 0xFF | 0x80;
            this.bytes[j] = ((byte)m);
            i += m;
            this.hashcode += i;
            j += 1;
            m = n & 0x7F;
            this.bytes[j] = ((byte)m);
            m = i + m;
            this.hashcode += m;
            j += 1;
            i = k;
            k = m;
          }
        }
      }
      this.hashcode += (m << 16);
    }
  }
  
  public Kim(Kim paramKim, int paramInt1, int paramInt2)
  {
    this(paramKim.bytes, paramInt1, paramInt2);
  }
  
  public Kim(byte[] paramArrayOfByte, int paramInt)
  {
    this(paramArrayOfByte, 0, paramInt);
  }
  
  public Kim(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 1;
    this.hashcode = 0;
    this.length = (paramInt2 - paramInt1);
    if (this.length > 0)
    {
      this.bytes = new byte[this.length];
      paramInt2 = 0;
      while (paramInt2 < this.length)
      {
        int j = paramArrayOfByte[(paramInt2 + paramInt1)] & 0xFF;
        i += j;
        this.hashcode += i;
        this.bytes[paramInt2] = ((byte)j);
        paramInt2 += 1;
      }
      this.hashcode += (i << 16);
    }
  }
  
  public static int characterSize(int paramInt)
    throws JSONException
  {
    if ((paramInt < 0) || (paramInt > 1114111)) {
      throw new JSONException("Bad character " + paramInt);
    }
    if (paramInt <= 127) {
      return 1;
    }
    if (paramInt <= 16383) {
      return 2;
    }
    return 3;
  }
  
  public int characterAt(int paramInt)
    throws JSONException
  {
    int i = get(paramInt);
    if ((i & 0x80) == 0) {}
    int j;
    do
    {
      return i;
      j = get(paramInt + 1);
      if ((j & 0x80) != 0) {
        break;
      }
      j = (i & 0x7F) << 7 | j;
      i = j;
    } while (j > 127);
    do
    {
      int k;
      do
      {
        throw new JSONException("Bad character at " + paramInt);
        k = get(paramInt + 2);
        j = (i & 0x7F) << 14 | (j & 0x7F) << 7 | k;
      } while (((k & 0x80) != 0) || (j <= 16383) || (j > 1114111));
      i = j;
      if (j < 55296) {
        break;
      }
    } while (j <= 57343);
    return j;
  }
  
  public int copy(byte[] paramArrayOfByte, int paramInt)
  {
    System.arraycopy(this.bytes, 0, paramArrayOfByte, paramInt, this.length);
    return this.length + paramInt;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Kim)) {}
    do
    {
      return false;
      paramObject = (Kim)paramObject;
      if (this == paramObject) {
        return true;
      }
    } while (this.hashcode != ((Kim)paramObject).hashcode);
    return Arrays.equals(this.bytes, ((Kim)paramObject).bytes);
  }
  
  public int get(int paramInt)
    throws JSONException
  {
    if ((paramInt < 0) || (paramInt > this.length)) {
      throw new JSONException("Bad character at " + paramInt);
    }
    return this.bytes[paramInt] & 0xFF;
  }
  
  public int hashCode()
  {
    return this.hashcode;
  }
  
  public String toString()
    throws JSONException
  {
    if (this.string == null)
    {
      int i = 0;
      char[] arrayOfChar = new char[this.length];
      int j = 0;
      if (j < this.length)
      {
        int k = characterAt(j);
        if (k < 65536)
        {
          arrayOfChar[i] = ((char)k);
          i += 1;
        }
        for (;;)
        {
          j += characterSize(k);
          break;
          arrayOfChar[i] = ((char)(0xD800 | k - 65536 >>> 10));
          i += 1;
          arrayOfChar[i] = ((char)(0xDC00 | k & 0x3FF));
          i += 1;
        }
      }
      this.string = new String(arrayOfChar, 0, i);
    }
    return this.string;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/Kim.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */