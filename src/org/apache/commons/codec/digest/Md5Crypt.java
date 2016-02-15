package org.apache.commons.codec.digest;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.Charsets;

public class Md5Crypt
{
  static final String APR1_PREFIX = "$apr1$";
  private static final int BLOCKSIZE = 16;
  static final String MD5_PREFIX = "$1$";
  private static final int ROUNDS = 1000;
  
  public static String apr1Crypt(String paramString)
  {
    return apr1Crypt(paramString.getBytes(Charsets.UTF_8));
  }
  
  public static String apr1Crypt(String paramString1, String paramString2)
  {
    return apr1Crypt(paramString1.getBytes(Charsets.UTF_8), paramString2);
  }
  
  public static String apr1Crypt(byte[] paramArrayOfByte)
  {
    return apr1Crypt(paramArrayOfByte, "$apr1$" + B64.getRandomSalt(8));
  }
  
  public static String apr1Crypt(byte[] paramArrayOfByte, String paramString)
  {
    String str = paramString;
    if (paramString != null)
    {
      str = paramString;
      if (!paramString.startsWith("$apr1$")) {
        str = "$apr1$" + paramString;
      }
    }
    return md5Crypt(paramArrayOfByte, str, "$apr1$");
  }
  
  public static String md5Crypt(byte[] paramArrayOfByte)
  {
    return md5Crypt(paramArrayOfByte, "$1$" + B64.getRandomSalt(8));
  }
  
  public static String md5Crypt(byte[] paramArrayOfByte, String paramString)
  {
    return md5Crypt(paramArrayOfByte, paramString, "$1$");
  }
  
  public static String md5Crypt(byte[] paramArrayOfByte, String paramString1, String paramString2)
  {
    int j = paramArrayOfByte.length;
    byte[] arrayOfByte;
    MessageDigest localMessageDigest;
    Object localObject1;
    if (paramString1 == null)
    {
      paramString1 = B64.getRandomSalt(8);
      arrayOfByte = paramString1.getBytes(Charsets.UTF_8);
      localMessageDigest = DigestUtils.getMd5Digest();
      localMessageDigest.update(paramArrayOfByte);
      localMessageDigest.update(paramString2.getBytes(Charsets.UTF_8));
      localMessageDigest.update(arrayOfByte);
      localObject1 = DigestUtils.getMd5Digest();
      ((MessageDigest)localObject1).update(paramArrayOfByte);
      ((MessageDigest)localObject1).update(arrayOfByte);
      ((MessageDigest)localObject1).update(paramArrayOfByte);
      localObject2 = ((MessageDigest)localObject1).digest();
      i = j;
      label87:
      if (i <= 0) {
        break label215;
      }
      if (i <= 16) {
        break label209;
      }
    }
    label209:
    for (int k = 16;; k = i)
    {
      localMessageDigest.update((byte[])localObject2, 0, k);
      i -= 16;
      break label87;
      localObject1 = Pattern.compile("^" + paramString2.replace("$", "\\$") + "([\\.\\/a-zA-Z0-9]{1,8}).*").matcher(paramString1);
      if ((localObject1 == null) || (!((Matcher)localObject1).find())) {
        throw new IllegalArgumentException("Invalid salt value: " + paramString1);
      }
      paramString1 = ((Matcher)localObject1).group(1);
      break;
    }
    label215:
    Arrays.fill((byte[])localObject2, (byte)0);
    if (j > 0)
    {
      if ((j & 0x1) == 1) {
        localMessageDigest.update(localObject2[0]);
      }
      for (;;)
      {
        j >>= 1;
        break;
        localMessageDigest.update(paramArrayOfByte[0]);
      }
    }
    Object localObject2 = new StringBuilder(paramString2 + paramString1 + "$");
    paramString1 = localMessageDigest.digest();
    int i = 0;
    paramString2 = (String)localObject1;
    if (i < 1000)
    {
      paramString2 = DigestUtils.getMd5Digest();
      if ((i & 0x1) != 0)
      {
        paramString2.update(paramArrayOfByte);
        label328:
        if (i % 3 != 0) {
          paramString2.update(arrayOfByte);
        }
        if (i % 7 != 0) {
          paramString2.update(paramArrayOfByte);
        }
        if ((i & 0x1) == 0) {
          break label389;
        }
        paramString2.update(paramString1, 0, 16);
      }
      for (;;)
      {
        paramString1 = paramString2.digest();
        i += 1;
        break;
        paramString2.update(paramString1, 0, 16);
        break label328;
        label389:
        paramString2.update(paramArrayOfByte);
      }
    }
    B64.b64from24bit(paramString1[0], paramString1[6], paramString1[12], 4, (StringBuilder)localObject2);
    B64.b64from24bit(paramString1[1], paramString1[7], paramString1[13], 4, (StringBuilder)localObject2);
    B64.b64from24bit(paramString1[2], paramString1[8], paramString1[14], 4, (StringBuilder)localObject2);
    B64.b64from24bit(paramString1[3], paramString1[9], paramString1[15], 4, (StringBuilder)localObject2);
    B64.b64from24bit(paramString1[4], paramString1[10], paramString1[5], 4, (StringBuilder)localObject2);
    B64.b64from24bit((byte)0, (byte)0, paramString1[11], 2, (StringBuilder)localObject2);
    localMessageDigest.reset();
    paramString2.reset();
    Arrays.fill(paramArrayOfByte, (byte)0);
    Arrays.fill(arrayOfByte, (byte)0);
    Arrays.fill(paramString1, (byte)0);
    return ((StringBuilder)localObject2).toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/digest/Md5Crypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */