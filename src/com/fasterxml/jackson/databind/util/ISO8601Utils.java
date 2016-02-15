package com.fasterxml.jackson.databind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils
{
  private static final String GMT_ID = "GMT";
  private static final TimeZone TIMEZONE_GMT = TimeZone.getTimeZone("GMT");
  
  private static void checkOffset(String paramString, int paramInt, char paramChar)
    throws ParseException
  {
    char c = paramString.charAt(paramInt);
    if (c != paramChar) {
      throw new ParseException("Expected '" + paramChar + "' character but found '" + c + "'", paramInt);
    }
  }
  
  public static String format(Date paramDate)
  {
    return format(paramDate, false, TIMEZONE_GMT);
  }
  
  public static String format(Date paramDate, boolean paramBoolean)
  {
    return format(paramDate, paramBoolean, TIMEZONE_GMT);
  }
  
  public static String format(Date paramDate, boolean paramBoolean, TimeZone paramTimeZone)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(paramTimeZone, Locale.US);
    localGregorianCalendar.setTime(paramDate);
    int k = "yyyy-MM-ddThh:mm:ss".length();
    int i;
    int j;
    label51:
    char c;
    if (paramBoolean)
    {
      i = ".sss".length();
      if (paramTimeZone.getRawOffset() != 0) {
        break label320;
      }
      j = "Z".length();
      paramDate = new StringBuilder(k + i + j);
      padInt(paramDate, localGregorianCalendar.get(1), "yyyy".length());
      paramDate.append('-');
      padInt(paramDate, localGregorianCalendar.get(2) + 1, "MM".length());
      paramDate.append('-');
      padInt(paramDate, localGregorianCalendar.get(5), "dd".length());
      paramDate.append('T');
      padInt(paramDate, localGregorianCalendar.get(11), "hh".length());
      paramDate.append(':');
      padInt(paramDate, localGregorianCalendar.get(12), "mm".length());
      paramDate.append(':');
      padInt(paramDate, localGregorianCalendar.get(13), "ss".length());
      if (paramBoolean)
      {
        paramDate.append('.');
        padInt(paramDate, localGregorianCalendar.get(14), "sss".length());
      }
      i = paramTimeZone.getOffset(localGregorianCalendar.getTimeInMillis());
      if (i == 0) {
        break label336;
      }
      j = Math.abs(i / 60000 / 60);
      k = Math.abs(i / 60000 % 60);
      if (i >= 0) {
        break label330;
      }
      c = '-';
      label274:
      paramDate.append(c);
      padInt(paramDate, j, "hh".length());
      paramDate.append(':');
      padInt(paramDate, k, "mm".length());
    }
    for (;;)
    {
      return paramDate.toString();
      i = 0;
      break;
      label320:
      j = "+hh:mm".length();
      break label51;
      label330:
      c = '+';
      break label274;
      label336:
      paramDate.append('Z');
    }
  }
  
  private static void padInt(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
  {
    String str = Integer.toString(paramInt1);
    paramInt1 = paramInt2 - str.length();
    while (paramInt1 > 0)
    {
      paramStringBuilder.append('0');
      paramInt1 -= 1;
    }
    paramStringBuilder.append(str);
  }
  
  public static Date parse(String paramString, ParsePosition paramParsePosition)
    throws ParseException
  {
    try
    {
      j = paramParsePosition.getIndex();
      i = j + 4;
      k = parseInt(paramString, j, i);
      checkOffset(paramString, i, '-');
      j = i + 1;
      i = j + 2;
      m = parseInt(paramString, j, i);
      checkOffset(paramString, i, '-');
      j = i + 1;
      i = j + 2;
      n = parseInt(paramString, j, i);
      checkOffset(paramString, i, 'T');
      j = i + 1;
      i = j + 2;
      i1 = parseInt(paramString, j, i);
      checkOffset(paramString, i, ':');
      j = i + 1;
      i = j + 2;
      i2 = parseInt(paramString, j, i);
      checkOffset(paramString, i, ':');
      i += 1;
      j = i + 2;
      i3 = parseInt(paramString, i, j);
      i = 0;
      if (paramString.charAt(j) != '.') {
        break label500;
      }
      checkOffset(paramString, j, '.');
      i = j + 1;
      j = i + 3;
      i = parseInt(paramString, i, j);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      int j;
      int i;
      int k;
      int m;
      int n;
      int i1;
      int i2;
      int i3;
      char c;
      Object localObject2;
      for (;;)
      {
        String str;
        if (paramString != null) {
          break label472;
        }
        paramString = null;
        throw new ParseException("Failed to parse date [" + paramString + "]: " + localIndexOutOfBoundsException.getMessage(), paramParsePosition.getIndex());
        if (c != 'Z') {
          break;
        }
        localObject1 = "GMT";
        j += 1;
      }
      throw new IndexOutOfBoundsException("Invalid time zone indicator " + c);
      Object localObject1 = new GregorianCalendar((TimeZone)localObject2);
      ((Calendar)localObject1).setLenient(false);
      ((Calendar)localObject1).set(1, k);
      ((Calendar)localObject1).set(2, m - 1);
      ((Calendar)localObject1).set(5, n);
      ((Calendar)localObject1).set(11, i1);
      ((Calendar)localObject1).set(12, i2);
      ((Calendar)localObject1).set(13, i3);
      ((Calendar)localObject1).set(14, i);
      paramParsePosition.setIndex(j);
      localObject1 = ((Calendar)localObject1).getTime();
      return (Date)localObject1;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        continue;
        paramString = '"' + paramString + "'";
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      label373:
      label472:
      label500:
      for (;;) {}
    }
    c = paramString.charAt(j);
    if ((c == '+') || (c == '-'))
    {
      localObject2 = paramString.substring(j);
      str = "GMT" + (String)localObject2;
      j += ((String)localObject2).length();
      localObject2 = TimeZone.getTimeZone(str);
      if (((TimeZone)localObject2).getID().equals(str)) {
        break label373;
      }
      throw new IndexOutOfBoundsException();
    }
  }
  
  private static int parseInt(String paramString, int paramInt1, int paramInt2)
    throws NumberFormatException
  {
    if ((paramInt1 < 0) || (paramInt2 > paramString.length()) || (paramInt1 > paramInt2)) {
      throw new NumberFormatException(paramString);
    }
    int i = 0;
    int j;
    if (paramInt1 < paramInt2)
    {
      j = paramInt1 + 1;
      paramInt1 = Character.digit(paramString.charAt(paramInt1), 10);
      if (paramInt1 < 0) {
        throw new NumberFormatException("Invalid number: " + paramString);
      }
      i = -paramInt1;
      paramInt1 = j;
    }
    for (;;)
    {
      if (paramInt1 < paramInt2)
      {
        j = Character.digit(paramString.charAt(paramInt1), 10);
        if (j < 0) {
          throw new NumberFormatException("Invalid number: " + paramString);
        }
        i = i * 10 - j;
        paramInt1 += 1;
      }
      else
      {
        return -i;
      }
    }
  }
  
  public static TimeZone timeZoneGMT()
  {
    return TIMEZONE_GMT;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/ISO8601Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */