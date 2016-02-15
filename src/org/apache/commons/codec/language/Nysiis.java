package org.apache.commons.codec.language;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class Nysiis
  implements StringEncoder
{
  private static final char[] CHARS_A = { 'A' };
  private static final char[] CHARS_AF = { 65, 70 };
  private static final char[] CHARS_C = { 'C' };
  private static final char[] CHARS_FF = { 70, 70 };
  private static final char[] CHARS_G = { 'G' };
  private static final char[] CHARS_N = { 'N' };
  private static final char[] CHARS_NN = { 78, 78 };
  private static final char[] CHARS_S = { 'S' };
  private static final char[] CHARS_SSS = { 83, 83, 83 };
  private static final Pattern PAT_DT_ETC = Pattern.compile("(DT|RT|RD|NT|ND)$");
  private static final Pattern PAT_EE_IE;
  private static final Pattern PAT_K;
  private static final Pattern PAT_KN;
  private static final Pattern PAT_MAC = Pattern.compile("^MAC");
  private static final Pattern PAT_PH_PF;
  private static final Pattern PAT_SCH;
  private static final char SPACE = ' ';
  private static final int TRUE_LENGTH = 6;
  private final boolean strict;
  
  static
  {
    PAT_KN = Pattern.compile("^KN");
    PAT_K = Pattern.compile("^K");
    PAT_PH_PF = Pattern.compile("^(PH|PF)");
    PAT_SCH = Pattern.compile("^SCH");
    PAT_EE_IE = Pattern.compile("(EE|IE)$");
  }
  
  public Nysiis()
  {
    this(true);
  }
  
  public Nysiis(boolean paramBoolean)
  {
    this.strict = paramBoolean;
  }
  
  private static boolean isVowel(char paramChar)
  {
    return (paramChar == 'A') || (paramChar == 'E') || (paramChar == 'I') || (paramChar == 'O') || (paramChar == 'U');
  }
  
  private static char[] transcodeRemaining(char paramChar1, char paramChar2, char paramChar3, char paramChar4)
  {
    if ((paramChar2 == 'E') && (paramChar3 == 'V')) {
      return CHARS_AF;
    }
    if (isVowel(paramChar2)) {
      return CHARS_A;
    }
    if (paramChar2 == 'Q') {
      return CHARS_G;
    }
    if (paramChar2 == 'Z') {
      return CHARS_S;
    }
    if (paramChar2 == 'M') {
      return CHARS_N;
    }
    if (paramChar2 == 'K')
    {
      if (paramChar3 == 'N') {
        return CHARS_NN;
      }
      return CHARS_C;
    }
    if ((paramChar2 == 'S') && (paramChar3 == 'C') && (paramChar4 == 'H')) {
      return CHARS_SSS;
    }
    if ((paramChar2 == 'P') && (paramChar3 == 'H')) {
      return CHARS_FF;
    }
    if ((paramChar2 == 'H') && ((!isVowel(paramChar1)) || (!isVowel(paramChar3)))) {
      return new char[] { paramChar1 };
    }
    if ((paramChar2 == 'W') && (isVowel(paramChar1))) {
      return new char[] { paramChar1 };
    }
    return new char[] { paramChar2 };
  }
  
  public Object encode(Object paramObject)
    throws EncoderException
  {
    if (!(paramObject instanceof String)) {
      throw new EncoderException("Parameter supplied to Nysiis encode is not of type java.lang.String");
    }
    return nysiis((String)paramObject);
  }
  
  public String encode(String paramString)
  {
    return nysiis(paramString);
  }
  
  public boolean isStrict()
  {
    return this.strict;
  }
  
  public String nysiis(String paramString)
  {
    if (paramString == null) {
      paramString = null;
    }
    Object localObject;
    label177:
    label266:
    do
    {
      return paramString;
      paramString = SoundexUtils.clean(paramString);
      if (paramString.length() == 0) {
        return paramString;
      }
      paramString = PAT_MAC.matcher(paramString).replaceFirst("MCC");
      paramString = PAT_KN.matcher(paramString).replaceFirst("NN");
      paramString = PAT_K.matcher(paramString).replaceFirst("C");
      paramString = PAT_PH_PF.matcher(paramString).replaceFirst("FF");
      paramString = PAT_SCH.matcher(paramString).replaceFirst("SSS");
      paramString = PAT_EE_IE.matcher(paramString).replaceFirst("Y");
      localObject = PAT_DT_ETC.matcher(paramString).replaceFirst("D");
      paramString = new StringBuilder(((String)localObject).length());
      paramString.append(((String)localObject).charAt(0));
      localObject = ((String)localObject).toCharArray();
      int j = localObject.length;
      int i = 1;
      if (i < j)
      {
        char c1;
        if (i < j - 1)
        {
          c1 = localObject[(i + 1)];
          if (i >= j - 2) {
            break label266;
          }
        }
        for (char c2 = localObject[(i + 2)];; c2 = ' ')
        {
          char[] arrayOfChar = transcodeRemaining(localObject[(i - 1)], localObject[i], c1, c2);
          System.arraycopy(arrayOfChar, 0, localObject, i, arrayOfChar.length);
          if (localObject[i] != localObject[(i - 1)]) {
            paramString.append(localObject[i]);
          }
          i += 1;
          break;
          c1 = ' ';
          break label177;
        }
      }
      if (paramString.length() > 1)
      {
        j = paramString.charAt(paramString.length() - 1);
        i = j;
        if (j == 83)
        {
          paramString.deleteCharAt(paramString.length() - 1);
          i = paramString.charAt(paramString.length() - 1);
        }
        if ((paramString.length() > 2) && (paramString.charAt(paramString.length() - 2) == 'A') && (i == 89)) {
          paramString.deleteCharAt(paramString.length() - 2);
        }
        if (i == 65) {
          paramString.deleteCharAt(paramString.length() - 1);
        }
      }
      localObject = paramString.toString();
      paramString = (String)localObject;
    } while (!isStrict());
    return ((String)localObject).substring(0, Math.min(6, ((String)localObject).length()));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/language/Nysiis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */