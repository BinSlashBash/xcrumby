package org.apache.commons.codec.digest;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.Charsets;

public class Sha2Crypt
{
  private static final int ROUNDS_DEFAULT = 5000;
  private static final int ROUNDS_MAX = 999999999;
  private static final int ROUNDS_MIN = 1000;
  private static final String ROUNDS_PREFIX = "rounds=";
  private static final Pattern SALT_PATTERN = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");
  private static final int SHA256_BLOCKSIZE = 32;
  static final String SHA256_PREFIX = "$5$";
  private static final int SHA512_BLOCKSIZE = 64;
  static final String SHA512_PREFIX = "$6$";
  
  public static String sha256Crypt(byte[] paramArrayOfByte)
  {
    return sha256Crypt(paramArrayOfByte, null);
  }
  
  public static String sha256Crypt(byte[] paramArrayOfByte, String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "$5$" + B64.getRandomSalt(8);
    }
    return sha2Crypt(paramArrayOfByte, str, "$5$", 32, "SHA-256");
  }
  
  private static String sha2Crypt(byte[] paramArrayOfByte, String paramString1, String paramString2, int paramInt, String paramString3)
  {
    int m = paramArrayOfByte.length;
    int i = 5000;
    int j = 0;
    if (paramString1 == null) {
      throw new IllegalArgumentException("Salt must not be null");
    }
    Object localObject1 = SALT_PATTERN.matcher(paramString1);
    if ((localObject1 == null) || (!((Matcher)localObject1).find())) {
      throw new IllegalArgumentException("Invalid salt value: " + paramString1);
    }
    if (((Matcher)localObject1).group(3) != null)
    {
      i = Math.max(1000, Math.min(999999999, Integer.parseInt(((Matcher)localObject1).group(3))));
      j = 1;
    }
    String str = ((Matcher)localObject1).group(4);
    byte[] arrayOfByte1 = str.getBytes(Charsets.UTF_8);
    int n = arrayOfByte1.length;
    localObject1 = DigestUtils.getDigest(paramString3);
    ((MessageDigest)localObject1).update(paramArrayOfByte);
    ((MessageDigest)localObject1).update(arrayOfByte1);
    paramString1 = DigestUtils.getDigest(paramString3);
    paramString1.update(paramArrayOfByte);
    paramString1.update(arrayOfByte1);
    paramString1.update(paramArrayOfByte);
    paramString1 = paramString1.digest();
    int k = paramArrayOfByte.length;
    while (k > paramInt)
    {
      ((MessageDigest)localObject1).update(paramString1, 0, paramInt);
      k -= paramInt;
    }
    ((MessageDigest)localObject1).update(paramString1, 0, k);
    k = paramArrayOfByte.length;
    if (k > 0)
    {
      if ((k & 0x1) != 0) {
        ((MessageDigest)localObject1).update(paramString1, 0, paramInt);
      }
      for (;;)
      {
        k >>= 1;
        break;
        ((MessageDigest)localObject1).update(paramArrayOfByte);
      }
    }
    paramString1 = ((MessageDigest)localObject1).digest();
    Object localObject2 = DigestUtils.getDigest(paramString3);
    k = 1;
    while (k <= m)
    {
      ((MessageDigest)localObject2).update(paramArrayOfByte);
      k += 1;
    }
    Object localObject3 = ((MessageDigest)localObject2).digest();
    localObject2 = new byte[m];
    k = 0;
    while (k < m - paramInt)
    {
      System.arraycopy(localObject3, 0, localObject2, k, paramInt);
      k += paramInt;
    }
    System.arraycopy(localObject3, 0, localObject2, k, m - k);
    localObject3 = DigestUtils.getDigest(paramString3);
    k = 1;
    while (k <= (paramString1[0] & 0xFF) + 16)
    {
      ((MessageDigest)localObject3).update(arrayOfByte1);
      k += 1;
    }
    byte[] arrayOfByte2 = ((MessageDigest)localObject3).digest();
    byte[] arrayOfByte3 = new byte[n];
    k = 0;
    while (k < n - paramInt)
    {
      System.arraycopy(arrayOfByte2, 0, arrayOfByte3, k, paramInt);
      k += paramInt;
    }
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, k, n - k);
    k = 0;
    if (k <= i - 1)
    {
      localObject1 = DigestUtils.getDigest(paramString3);
      if ((k & 0x1) != 0)
      {
        ((MessageDigest)localObject1).update((byte[])localObject2, 0, m);
        label492:
        if (k % 3 != 0) {
          ((MessageDigest)localObject1).update(arrayOfByte3, 0, n);
        }
        if (k % 7 != 0) {
          ((MessageDigest)localObject1).update((byte[])localObject2, 0, m);
        }
        if ((k & 0x1) == 0) {
          break label568;
        }
        ((MessageDigest)localObject1).update(paramString1, 0, paramInt);
      }
      for (;;)
      {
        paramString1 = ((MessageDigest)localObject1).digest();
        k += 1;
        break;
        ((MessageDigest)localObject1).update(paramString1, 0, paramInt);
        break label492;
        label568:
        ((MessageDigest)localObject1).update((byte[])localObject2, 0, m);
      }
    }
    paramString2 = new StringBuilder(paramString2);
    if (j != 0)
    {
      paramString2.append("rounds=");
      paramString2.append(i);
      paramString2.append("$");
    }
    paramString2.append(str);
    paramString2.append("$");
    if (paramInt == 32)
    {
      B64.b64from24bit(paramString1[0], paramString1[10], paramString1[20], 4, paramString2);
      B64.b64from24bit(paramString1[21], paramString1[1], paramString1[11], 4, paramString2);
      B64.b64from24bit(paramString1[12], paramString1[22], paramString1[2], 4, paramString2);
      B64.b64from24bit(paramString1[3], paramString1[13], paramString1[23], 4, paramString2);
      B64.b64from24bit(paramString1[24], paramString1[4], paramString1[14], 4, paramString2);
      B64.b64from24bit(paramString1[15], paramString1[25], paramString1[5], 4, paramString2);
      B64.b64from24bit(paramString1[6], paramString1[16], paramString1[26], 4, paramString2);
      B64.b64from24bit(paramString1[27], paramString1[7], paramString1[17], 4, paramString2);
      B64.b64from24bit(paramString1[18], paramString1[28], paramString1[8], 4, paramString2);
      B64.b64from24bit(paramString1[9], paramString1[19], paramString1[29], 4, paramString2);
      B64.b64from24bit((byte)0, paramString1[31], paramString1[30], 3, paramString2);
    }
    for (;;)
    {
      Arrays.fill(arrayOfByte2, (byte)0);
      Arrays.fill((byte[])localObject2, (byte)0);
      Arrays.fill(arrayOfByte3, (byte)0);
      ((MessageDigest)localObject1).reset();
      ((MessageDigest)localObject3).reset();
      Arrays.fill(paramArrayOfByte, (byte)0);
      Arrays.fill(arrayOfByte1, (byte)0);
      return paramString2.toString();
      B64.b64from24bit(paramString1[0], paramString1[21], paramString1[42], 4, paramString2);
      B64.b64from24bit(paramString1[22], paramString1[43], paramString1[1], 4, paramString2);
      B64.b64from24bit(paramString1[44], paramString1[2], paramString1[23], 4, paramString2);
      B64.b64from24bit(paramString1[3], paramString1[24], paramString1[45], 4, paramString2);
      B64.b64from24bit(paramString1[25], paramString1[46], paramString1[4], 4, paramString2);
      B64.b64from24bit(paramString1[47], paramString1[5], paramString1[26], 4, paramString2);
      B64.b64from24bit(paramString1[6], paramString1[27], paramString1[48], 4, paramString2);
      B64.b64from24bit(paramString1[28], paramString1[49], paramString1[7], 4, paramString2);
      B64.b64from24bit(paramString1[50], paramString1[8], paramString1[29], 4, paramString2);
      B64.b64from24bit(paramString1[9], paramString1[30], paramString1[51], 4, paramString2);
      B64.b64from24bit(paramString1[31], paramString1[52], paramString1[10], 4, paramString2);
      B64.b64from24bit(paramString1[53], paramString1[11], paramString1[32], 4, paramString2);
      B64.b64from24bit(paramString1[12], paramString1[33], paramString1[54], 4, paramString2);
      B64.b64from24bit(paramString1[34], paramString1[55], paramString1[13], 4, paramString2);
      B64.b64from24bit(paramString1[56], paramString1[14], paramString1[35], 4, paramString2);
      B64.b64from24bit(paramString1[15], paramString1[36], paramString1[57], 4, paramString2);
      B64.b64from24bit(paramString1[37], paramString1[58], paramString1[16], 4, paramString2);
      B64.b64from24bit(paramString1[59], paramString1[17], paramString1[38], 4, paramString2);
      B64.b64from24bit(paramString1[18], paramString1[39], paramString1[60], 4, paramString2);
      B64.b64from24bit(paramString1[40], paramString1[61], paramString1[19], 4, paramString2);
      B64.b64from24bit(paramString1[62], paramString1[20], paramString1[41], 4, paramString2);
      B64.b64from24bit((byte)0, (byte)0, paramString1[63], 2, paramString2);
    }
  }
  
  public static String sha512Crypt(byte[] paramArrayOfByte)
  {
    return sha512Crypt(paramArrayOfByte, null);
  }
  
  public static String sha512Crypt(byte[] paramArrayOfByte, String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "$6$" + B64.getRandomSalt(8);
    }
    return sha2Crypt(paramArrayOfByte, str, "$6$", 64, "SHA-512");
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/digest/Sha2Crypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */