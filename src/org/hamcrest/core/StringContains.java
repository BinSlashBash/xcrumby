package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class StringContains
  extends SubstringMatcher
{
  public StringContains(String paramString)
  {
    super(paramString);
  }
  
  @Factory
  public static Matcher<String> containsString(String paramString)
  {
    return new StringContains(paramString);
  }
  
  protected boolean evalSubstringOf(String paramString)
  {
    return paramString.indexOf(this.substring) >= 0;
  }
  
  protected String relationship()
  {
    return "containing";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/StringContains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */