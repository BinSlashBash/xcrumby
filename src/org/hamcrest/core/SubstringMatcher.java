package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class SubstringMatcher
  extends TypeSafeMatcher<String>
{
  protected final String substring;
  
  protected SubstringMatcher(String paramString)
  {
    this.substring = paramString;
  }
  
  public void describeMismatchSafely(String paramString, Description paramDescription)
  {
    paramDescription.appendText("was \"").appendText(paramString).appendText("\"");
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("a string ").appendText(relationship()).appendText(" ").appendValue(this.substring);
  }
  
  protected abstract boolean evalSubstringOf(String paramString);
  
  public boolean matchesSafely(String paramString)
  {
    return evalSubstringOf(paramString);
  }
  
  protected abstract String relationship();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/SubstringMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */