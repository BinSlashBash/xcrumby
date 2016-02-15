package org.jsoup.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class DataUtil
{
  private static final int bufferSize = 131072;
  private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
  static final String defaultCharset = "UTF-8";
  
  static String getCharsetFromContentType(String paramString)
  {
    if (paramString == null) {
      paramString = null;
    }
    for (;;)
    {
      return paramString;
      paramString = charsetPattern.matcher(paramString);
      String str;
      if (paramString.find())
      {
        str = paramString.group(1).trim().replace("charset=", "");
        if (str.isEmpty()) {
          return null;
        }
        paramString = str;
      }
      try
      {
        if (!Charset.isSupported(str))
        {
          paramString = str.toUpperCase(Locale.ENGLISH);
          boolean bool = Charset.isSupported(paramString);
          if (!bool) {
            return null;
          }
        }
      }
      catch (IllegalCharsetNameException paramString) {}
    }
    return null;
  }
  
  public static Document load(File paramFile, String paramString1, String paramString2)
    throws IOException
  {
    Object localObject = null;
    try
    {
      paramFile = new FileInputStream(paramFile);
      if (paramFile == null) {
        break label45;
      }
    }
    finally
    {
      try
      {
        paramString1 = parseByteData(readToByteBuffer(paramFile), paramString1, paramString2, Parser.htmlParser());
        if (paramFile != null) {
          paramFile.close();
        }
        return paramString1;
      }
      finally {}
      paramString1 = finally;
      paramFile = (File)localObject;
    }
    paramFile.close();
    label45:
    throw paramString1;
  }
  
  public static Document load(InputStream paramInputStream, String paramString1, String paramString2)
    throws IOException
  {
    return parseByteData(readToByteBuffer(paramInputStream), paramString1, paramString2, Parser.htmlParser());
  }
  
  public static Document load(InputStream paramInputStream, String paramString1, String paramString2, Parser paramParser)
    throws IOException
  {
    return parseByteData(readToByteBuffer(paramInputStream), paramString1, paramString2, paramParser);
  }
  
  static Document parseByteData(ByteBuffer paramByteBuffer, String paramString1, String paramString2, Parser paramParser)
  {
    Object localObject = null;
    String str5;
    Document localDocument;
    Element localElement;
    String str1;
    String str4;
    String str3;
    if (paramString1 == null)
    {
      str5 = Charset.forName("UTF-8").decode(paramByteBuffer).toString();
      localDocument = paramParser.parseInput(str5, paramString2);
      localElement = localDocument.select("meta[http-equiv=content-type], meta[charset]").first();
      localObject = localDocument;
      str1 = str5;
      str4 = paramString1;
      if (localElement != null)
      {
        if (!localElement.hasAttr("http-equiv")) {
          break label289;
        }
        str1 = getCharsetFromContentType(localElement.attr("content"));
        str3 = str1;
        if (str1 == null)
        {
          str3 = str1;
          if (localElement.hasAttr("charset")) {
            str3 = str1;
          }
        }
      }
    }
    for (;;)
    {
      try
      {
        if (Charset.isSupported(localElement.attr("charset"))) {
          str3 = localElement.attr("charset");
        }
        localObject = localDocument;
        str1 = str5;
        str4 = paramString1;
        if (str3 != null)
        {
          localObject = localDocument;
          str1 = str5;
          str4 = paramString1;
          if (str3.length() != 0)
          {
            localObject = localDocument;
            str1 = str5;
            str4 = paramString1;
            if (!str3.equals("UTF-8"))
            {
              paramString1 = str3.trim().replaceAll("[\"']", "");
              str4 = paramString1;
              paramByteBuffer.rewind();
              str1 = Charset.forName(paramString1).decode(paramByteBuffer).toString();
              localObject = null;
            }
          }
        }
        paramByteBuffer = (ByteBuffer)localObject;
        if (localObject == null)
        {
          paramByteBuffer = str1;
          if (str1.length() > 0)
          {
            paramByteBuffer = str1;
            if (str1.charAt(0) == 65279) {
              paramByteBuffer = str1.substring(1);
            }
          }
          paramByteBuffer = paramParser.parseInput(paramByteBuffer, paramString2);
          paramByteBuffer.outputSettings().charset(str4);
        }
        return paramByteBuffer;
      }
      catch (IllegalCharsetNameException localIllegalCharsetNameException)
      {
        str3 = null;
        continue;
      }
      label289:
      str3 = localElement.attr("charset");
      continue;
      Validate.notEmpty(paramString1, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
      String str2 = Charset.forName(paramString1).decode(paramByteBuffer).toString();
      str4 = paramString1;
    }
  }
  
  static ByteBuffer readToByteBuffer(InputStream paramInputStream)
    throws IOException
  {
    return readToByteBuffer(paramInputStream, 0);
  }
  
  static ByteBuffer readToByteBuffer(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    boolean bool;
    int i;
    label20:
    byte[] arrayOfByte;
    ByteArrayOutputStream localByteArrayOutputStream;
    if (paramInt >= 0)
    {
      bool = true;
      Validate.isTrue(bool, "maxSize must be 0 (unlimited) or larger");
      if (paramInt <= 0) {
        break label66;
      }
      i = 1;
      arrayOfByte = new byte[131072];
      localByteArrayOutputStream = new ByteArrayOutputStream(131072);
    }
    for (;;)
    {
      int k = paramInputStream.read(arrayOfByte);
      if (k == -1) {}
      for (;;)
      {
        return ByteBuffer.wrap(localByteArrayOutputStream.toByteArray());
        bool = false;
        break;
        label66:
        i = 0;
        break label20;
        j = paramInt;
        if (i == 0) {
          break label100;
        }
        if (k <= paramInt) {
          break label95;
        }
        localByteArrayOutputStream.write(arrayOfByte, 0, paramInt);
      }
      label95:
      int j = paramInt - k;
      label100:
      localByteArrayOutputStream.write(arrayOfByte, 0, k);
      paramInt = j;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/helper/DataUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */