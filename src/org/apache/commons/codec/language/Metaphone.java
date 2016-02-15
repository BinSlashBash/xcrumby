package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class Metaphone
  implements StringEncoder
{
  private static final String FRONTV = "EIY";
  private static final String VARSON = "CSPTG";
  private static final String VOWELS = "AEIOU";
  private int maxCodeLen = 4;
  
  private boolean isLastChar(int paramInt1, int paramInt2)
  {
    return paramInt2 + 1 == paramInt1;
  }
  
  private boolean isNextChar(StringBuilder paramStringBuilder, int paramInt, char paramChar)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramInt >= 0)
    {
      bool1 = bool2;
      if (paramInt < paramStringBuilder.length() - 1)
      {
        if (paramStringBuilder.charAt(paramInt + 1) != paramChar) {
          break label42;
        }
        bool1 = true;
      }
    }
    return bool1;
    label42:
    return false;
  }
  
  private boolean isPreviousChar(StringBuilder paramStringBuilder, int paramInt, char paramChar)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramInt > 0)
    {
      bool1 = bool2;
      if (paramInt < paramStringBuilder.length())
      {
        if (paramStringBuilder.charAt(paramInt - 1) != paramChar) {
          break label40;
        }
        bool1 = true;
      }
    }
    return bool1;
    label40:
    return false;
  }
  
  private boolean isVowel(StringBuilder paramStringBuilder, int paramInt)
  {
    return "AEIOU".indexOf(paramStringBuilder.charAt(paramInt)) >= 0;
  }
  
  private boolean regionMatch(StringBuilder paramStringBuilder, int paramInt, String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramInt >= 0)
    {
      bool1 = bool2;
      if (paramString.length() + paramInt - 1 < paramStringBuilder.length()) {
        bool1 = paramStringBuilder.substring(paramInt, paramString.length() + paramInt).equals(paramString);
      }
    }
    return bool1;
  }
  
  public Object encode(Object paramObject)
    throws EncoderException
  {
    if (!(paramObject instanceof String)) {
      throw new EncoderException("Parameter supplied to Metaphone encode is not of type java.lang.String");
    }
    return metaphone((String)paramObject);
  }
  
  public String encode(String paramString)
  {
    return metaphone(paramString);
  }
  
  public int getMaxCodeLen()
  {
    return this.maxCodeLen;
  }
  
  public boolean isMetaphoneEqual(String paramString1, String paramString2)
  {
    return metaphone(paramString1).equals(metaphone(paramString2));
  }
  
  public String metaphone(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    if (paramString.length() == 1) {
      return paramString.toUpperCase(Locale.ENGLISH);
    }
    paramString = paramString.toUpperCase(Locale.ENGLISH).toCharArray();
    StringBuilder localStringBuilder1 = new StringBuilder(40);
    StringBuilder localStringBuilder2 = new StringBuilder(10);
    switch (paramString[0])
    {
    default: 
      localStringBuilder1.append(paramString);
    }
    int k;
    int i;
    char c;
    int j;
    for (;;)
    {
      k = localStringBuilder1.length();
      i = 0;
      for (;;)
      {
        if ((localStringBuilder2.length() >= getMaxCodeLen()) || (i >= k)) {
          break label1443;
        }
        c = localStringBuilder1.charAt(i);
        if ((c == 'C') || (!isPreviousChar(localStringBuilder1, i, c))) {
          break;
        }
        j = i + 1;
        i = j;
        if (localStringBuilder2.length() > getMaxCodeLen())
        {
          localStringBuilder2.setLength(getMaxCodeLen());
          i = j;
        }
      }
      if (paramString[1] == 'N')
      {
        localStringBuilder1.append(paramString, 1, paramString.length - 1);
      }
      else
      {
        localStringBuilder1.append(paramString);
        continue;
        if (paramString[1] == 'E')
        {
          localStringBuilder1.append(paramString, 1, paramString.length - 1);
        }
        else
        {
          localStringBuilder1.append(paramString);
          continue;
          if (paramString[1] == 'R')
          {
            localStringBuilder1.append(paramString, 1, paramString.length - 1);
          }
          else if (paramString[1] == 'H')
          {
            localStringBuilder1.append(paramString, 1, paramString.length - 1);
            localStringBuilder1.setCharAt(0, 'W');
          }
          else
          {
            localStringBuilder1.append(paramString);
            continue;
            paramString[0] = 83;
            localStringBuilder1.append(paramString);
          }
        }
      }
    }
    switch (c)
    {
    default: 
      j = i;
    }
    for (;;)
    {
      j += 1;
      break;
      j = i;
      if (i == 0)
      {
        localStringBuilder2.append(c);
        j = i;
        continue;
        if (isPreviousChar(localStringBuilder1, i, 'M'))
        {
          j = i;
          if (isLastChar(k, i)) {}
        }
        else
        {
          localStringBuilder2.append(c);
          j = i;
          continue;
          if ((isPreviousChar(localStringBuilder1, i, 'S')) && (!isLastChar(k, i)))
          {
            j = i;
            if ("EIY".indexOf(localStringBuilder1.charAt(i + 1)) >= 0) {}
          }
          else if (regionMatch(localStringBuilder1, i, "CIA"))
          {
            localStringBuilder2.append('X');
            j = i;
          }
          else if ((!isLastChar(k, i)) && ("EIY".indexOf(localStringBuilder1.charAt(i + 1)) >= 0))
          {
            localStringBuilder2.append('S');
            j = i;
          }
          else if ((isPreviousChar(localStringBuilder1, i, 'S')) && (isNextChar(localStringBuilder1, i, 'H')))
          {
            localStringBuilder2.append('K');
            j = i;
          }
          else if (isNextChar(localStringBuilder1, i, 'H'))
          {
            if ((i == 0) && (k >= 3) && (isVowel(localStringBuilder1, 2)))
            {
              localStringBuilder2.append('K');
              j = i;
            }
            else
            {
              localStringBuilder2.append('X');
              j = i;
            }
          }
          else
          {
            localStringBuilder2.append('K');
            j = i;
            continue;
            if ((!isLastChar(k, i + 1)) && (isNextChar(localStringBuilder1, i, 'G')) && ("EIY".indexOf(localStringBuilder1.charAt(i + 2)) >= 0))
            {
              localStringBuilder2.append('J');
              j = i + 2;
            }
            else
            {
              localStringBuilder2.append('T');
              j = i;
              continue;
              if (isLastChar(k, i + 1))
              {
                j = i;
                if (isNextChar(localStringBuilder1, i, 'H')) {}
              }
              else if ((!isLastChar(k, i + 1)) && (isNextChar(localStringBuilder1, i, 'H')))
              {
                j = i;
                if (!isVowel(localStringBuilder1, i + 2)) {}
              }
              else if (i > 0)
              {
                j = i;
                if (!regionMatch(localStringBuilder1, i, "GN"))
                {
                  j = i;
                  if (regionMatch(localStringBuilder1, i, "GNED")) {}
                }
              }
              else
              {
                if (isPreviousChar(localStringBuilder1, i, 'G')) {}
                for (j = 1;; j = 0)
                {
                  if ((isLastChar(k, i)) || ("EIY".indexOf(localStringBuilder1.charAt(i + 1)) < 0) || (j != 0)) {
                    break label1005;
                  }
                  localStringBuilder2.append('J');
                  j = i;
                  break;
                }
                label1005:
                localStringBuilder2.append('K');
                j = i;
                continue;
                j = i;
                if (!isLastChar(k, i)) {
                  if (i > 0)
                  {
                    j = i;
                    if ("CSPTG".indexOf(localStringBuilder1.charAt(i - 1)) >= 0) {}
                  }
                  else
                  {
                    j = i;
                    if (isVowel(localStringBuilder1, i + 1))
                    {
                      localStringBuilder2.append('H');
                      j = i;
                      continue;
                      localStringBuilder2.append(c);
                      j = i;
                      continue;
                      if (i > 0)
                      {
                        j = i;
                        if (!isPreviousChar(localStringBuilder1, i, 'C'))
                        {
                          localStringBuilder2.append(c);
                          j = i;
                        }
                      }
                      else
                      {
                        localStringBuilder2.append(c);
                        j = i;
                        continue;
                        if (isNextChar(localStringBuilder1, i, 'H'))
                        {
                          localStringBuilder2.append('F');
                          j = i;
                        }
                        else
                        {
                          localStringBuilder2.append(c);
                          j = i;
                          continue;
                          localStringBuilder2.append('K');
                          j = i;
                          continue;
                          if ((regionMatch(localStringBuilder1, i, "SH")) || (regionMatch(localStringBuilder1, i, "SIO")) || (regionMatch(localStringBuilder1, i, "SIA")))
                          {
                            localStringBuilder2.append('X');
                            j = i;
                          }
                          else
                          {
                            localStringBuilder2.append('S');
                            j = i;
                            continue;
                            if ((regionMatch(localStringBuilder1, i, "TIA")) || (regionMatch(localStringBuilder1, i, "TIO")))
                            {
                              localStringBuilder2.append('X');
                              j = i;
                            }
                            else
                            {
                              j = i;
                              if (!regionMatch(localStringBuilder1, i, "TCH")) {
                                if (regionMatch(localStringBuilder1, i, "TH"))
                                {
                                  localStringBuilder2.append('0');
                                  j = i;
                                }
                                else
                                {
                                  localStringBuilder2.append('T');
                                  j = i;
                                  continue;
                                  localStringBuilder2.append('F');
                                  j = i;
                                  continue;
                                  j = i;
                                  if (!isLastChar(k, i))
                                  {
                                    j = i;
                                    if (isVowel(localStringBuilder1, i + 1))
                                    {
                                      localStringBuilder2.append(c);
                                      j = i;
                                      continue;
                                      localStringBuilder2.append('K');
                                      localStringBuilder2.append('S');
                                      j = i;
                                      continue;
                                      localStringBuilder2.append('S');
                                      j = i;
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    label1443:
    return localStringBuilder2.toString();
  }
  
  public void setMaxCodeLen(int paramInt)
  {
    this.maxCodeLen = paramInt;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/language/Metaphone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */