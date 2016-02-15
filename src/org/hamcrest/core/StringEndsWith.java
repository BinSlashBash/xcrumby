package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class StringEndsWith
  extends SubstringMatcher
{
  public StringEndsWith(String paramString)
  {
    super(paramString);
  }
  
  @Factory
  public static Matcher<String> endsWith(String paramString)
  {
    return new StringEndsWith(paramString);
  }
  
  protected boolean evalSubstringOf(String paramString)
  {
    return paramString.endsWith(this.substring);
  }
  
  protected String relationship()
  {
    return "ending with";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/StringEndsWith.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */