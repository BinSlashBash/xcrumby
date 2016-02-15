package org.hamcrest;

import java.io.IOException;

public class StringDescription
  extends BaseDescription
{
  private final Appendable out;
  
  public StringDescription()
  {
    this(new StringBuilder());
  }
  
  public StringDescription(Appendable paramAppendable)
  {
    this.out = paramAppendable;
  }
  
  public static String asString(SelfDescribing paramSelfDescribing)
  {
    return toString(paramSelfDescribing);
  }
  
  public static String toString(SelfDescribing paramSelfDescribing)
  {
    return new StringDescription().appendDescriptionOf(paramSelfDescribing).toString();
  }
  
  protected void append(char paramChar)
  {
    try
    {
      this.out.append(paramChar);
      return;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Could not write description", localIOException);
    }
  }
  
  protected void append(String paramString)
  {
    try
    {
      this.out.append(paramString);
      return;
    }
    catch (IOException paramString)
    {
      throw new RuntimeException("Could not write description", paramString);
    }
  }
  
  public String toString()
  {
    return this.out.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/StringDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */