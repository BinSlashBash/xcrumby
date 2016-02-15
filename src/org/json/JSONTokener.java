package org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class JSONTokener
{
  private long character;
  private boolean eof;
  private long index;
  private long line;
  private char previous;
  private Reader reader;
  private boolean usePrevious;
  
  public JSONTokener(InputStream paramInputStream)
    throws JSONException
  {
    this(new InputStreamReader(paramInputStream));
  }
  
  public JSONTokener(Reader paramReader)
  {
    if (paramReader.markSupported()) {}
    for (;;)
    {
      this.reader = paramReader;
      this.eof = false;
      this.usePrevious = false;
      this.previous = '\000';
      this.index = 0L;
      this.character = 1L;
      this.line = 1L;
      return;
      paramReader = new BufferedReader(paramReader);
    }
  }
  
  public JSONTokener(String paramString)
  {
    this(new StringReader(paramString));
  }
  
  public static int dehexchar(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9')) {
      return paramChar - '0';
    }
    if ((paramChar >= 'A') && (paramChar <= 'F')) {
      return paramChar - '7';
    }
    if ((paramChar >= 'a') && (paramChar <= 'f')) {
      return paramChar - 'W';
    }
    return -1;
  }
  
  public void back()
    throws JSONException
  {
    if ((this.usePrevious) || (this.index <= 0L)) {
      throw new JSONException("Stepping back two steps is not supported");
    }
    this.index -= 1L;
    this.character -= 1L;
    this.usePrevious = true;
    this.eof = false;
  }
  
  public boolean end()
  {
    return (this.eof) && (!this.usePrevious);
  }
  
  public boolean more()
    throws JSONException
  {
    next();
    if (end()) {
      return false;
    }
    back();
    return true;
  }
  
  public char next()
    throws JSONException
  {
    long l = 0L;
    int i;
    if (this.usePrevious)
    {
      this.usePrevious = false;
      i = this.previous;
      this.index += 1L;
      if (this.previous != '\r') {
        break label111;
      }
      this.line += 1L;
      if (i != 10) {
        break label106;
      }
      label54:
      this.character = l;
    }
    for (;;)
    {
      this.previous = ((char)i);
      return this.previous;
      try
      {
        int j = this.reader.read();
        i = j;
        if (j > 0) {
          break;
        }
        this.eof = true;
        i = 0;
      }
      catch (IOException localIOException)
      {
        throw new JSONException(localIOException);
      }
      label106:
      l = 1L;
      break label54;
      label111:
      if (i == 10)
      {
        this.line = (1L + this.line);
        this.character = 0L;
      }
      else
      {
        this.character += 1L;
      }
    }
  }
  
  public char next(char paramChar)
    throws JSONException
  {
    char c = next();
    if (c != paramChar) {
      throw syntaxError("Expected '" + paramChar + "' and instead saw '" + c + "'");
    }
    return c;
  }
  
  public String next(int paramInt)
    throws JSONException
  {
    if (paramInt == 0) {
      return "";
    }
    char[] arrayOfChar = new char[paramInt];
    int i = 0;
    while (i < paramInt)
    {
      arrayOfChar[i] = next();
      if (end()) {
        throw syntaxError("Substring bounds error");
      }
      i += 1;
    }
    return new String(arrayOfChar);
  }
  
  public char nextClean()
    throws JSONException
  {
    char c;
    do
    {
      c = next();
    } while ((c != 0) && (c <= ' '));
    return c;
  }
  
  public String nextString(char paramChar)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (;;)
    {
      char c = next();
      switch (c)
      {
      default: 
        if (c == paramChar) {
          return localStringBuffer.toString();
        }
        break;
      case '\000': 
      case '\n': 
      case '\r': 
        throw syntaxError("Unterminated string");
      case '\\': 
        c = next();
        switch (c)
        {
        default: 
          throw syntaxError("Illegal escape.");
        case 'b': 
          localStringBuffer.append('\b');
          break;
        case 't': 
          localStringBuffer.append('\t');
          break;
        case 'n': 
          localStringBuffer.append('\n');
          break;
        case 'f': 
          localStringBuffer.append('\f');
          break;
        case 'r': 
          localStringBuffer.append('\r');
          break;
        case 'u': 
          localStringBuffer.append((char)Integer.parseInt(next(4), 16));
          break;
        case '"': 
        case '\'': 
        case '/': 
        case '\\': 
          localStringBuffer.append(c);
        }
        break;
      }
      localStringBuffer.append(c);
    }
  }
  
  public String nextTo(char paramChar)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (;;)
    {
      char c = next();
      if ((c == paramChar) || (c == 0) || (c == '\n') || (c == '\r'))
      {
        if (c != 0) {
          back();
        }
        return localStringBuffer.toString().trim();
      }
      localStringBuffer.append(c);
    }
  }
  
  public String nextTo(String paramString)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (;;)
    {
      char c = next();
      if ((paramString.indexOf(c) >= 0) || (c == 0) || (c == '\n') || (c == '\r'))
      {
        if (c != 0) {
          back();
        }
        return localStringBuffer.toString().trim();
      }
      localStringBuffer.append(c);
    }
  }
  
  public Object nextValue()
    throws JSONException
  {
    char c = nextClean();
    switch (c)
    {
    default: 
      localObject = new StringBuffer();
    }
    while ((c >= ' ') && (",:]}/\\\"[{;=#".indexOf(c) < 0))
    {
      ((StringBuffer)localObject).append(c);
      c = next();
      continue;
      return nextString(c);
      back();
      return new JSONObject(this);
      back();
      return new JSONArray(this);
    }
    back();
    Object localObject = ((StringBuffer)localObject).toString().trim();
    if ("".equals(localObject)) {
      throw syntaxError("Missing value");
    }
    return JSONObject.stringToValue((String)localObject);
  }
  
  public char skipTo(char paramChar)
    throws JSONException
  {
    try
    {
      long l1 = this.index;
      long l2 = this.character;
      long l3 = this.line;
      this.reader.mark(1000000);
      char c;
      do
      {
        c = next();
        if (c == 0)
        {
          this.reader.reset();
          this.index = l1;
          this.character = l2;
          this.line = l3;
          return c;
        }
      } while (c != paramChar);
      back();
      return c;
    }
    catch (IOException localIOException)
    {
      throw new JSONException(localIOException);
    }
  }
  
  public JSONException syntaxError(String paramString)
  {
    return new JSONException(paramString + toString());
  }
  
  public String toString()
  {
    return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/JSONTokener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */