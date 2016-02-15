package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualIgnoringCase
  extends TypeSafeMatcher<String>
{
  private final String string;
  
  public IsEqualIgnoringCase(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
    }
    this.string = paramString;
  }
  
  @Factory
  public static Matcher<String> equalToIgnoringCase(String paramString)
  {
    return new IsEqualIgnoringCase(paramString);
  }
  
  public void describeMismatchSafely(String paramString, Description paramDescription)
  {
    paramDescription.appendText("was ").appendText(paramString);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("equalToIgnoringCase(").appendValue(this.string).appendText(")");
  }
  
  public boolean matchesSafely(String paramString)
  {
    return this.string.equalsIgnoreCase(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/text/IsEqualIgnoringCase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */