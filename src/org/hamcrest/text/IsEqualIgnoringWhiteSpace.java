package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualIgnoringWhiteSpace
  extends TypeSafeMatcher<String>
{
  private final String string;
  
  public IsEqualIgnoringWhiteSpace(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
    }
    this.string = paramString;
  }
  
  @Factory
  public static Matcher<String> equalToIgnoringWhiteSpace(String paramString)
  {
    return new IsEqualIgnoringWhiteSpace(paramString);
  }
  
  public void describeMismatchSafely(String paramString, Description paramDescription)
  {
    paramDescription.appendText("was  ").appendText(stripSpace(paramString));
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("equalToIgnoringWhiteSpace(").appendValue(this.string).appendText(")");
  }
  
  public boolean matchesSafely(String paramString)
  {
    return stripSpace(this.string).equalsIgnoreCase(stripSpace(paramString));
  }
  
  public String stripSpace(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 1;
    int j = 0;
    if (j < paramString.length())
    {
      char c = paramString.charAt(j);
      if (Character.isWhitespace(c)) {
        if (i == 0) {
          localStringBuilder.append(' ');
        }
      }
      for (i = 1;; i = 0)
      {
        j += 1;
        break;
        localStringBuilder.append(c);
      }
    }
    return localStringBuilder.toString().trim();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/text/IsEqualIgnoringWhiteSpace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */