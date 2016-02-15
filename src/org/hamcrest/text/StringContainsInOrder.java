package org.hamcrest.text;

import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class StringContainsInOrder
  extends TypeSafeMatcher<String>
{
  private final Iterable<String> substrings;
  
  public StringContainsInOrder(Iterable<String> paramIterable)
  {
    this.substrings = paramIterable;
  }
  
  @Factory
  public static Matcher<String> stringContainsInOrder(Iterable<String> paramIterable)
  {
    return new StringContainsInOrder(paramIterable);
  }
  
  public void describeMismatchSafely(String paramString, Description paramDescription)
  {
    paramDescription.appendText("was \"").appendText(paramString).appendText("\"");
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("a string containing ").appendValueList("", ", ", "", this.substrings).appendText(" in order");
  }
  
  public boolean matchesSafely(String paramString)
  {
    int i = 0;
    Iterator localIterator = this.substrings.iterator();
    while (localIterator.hasNext())
    {
      int j = paramString.indexOf((String)localIterator.next(), i);
      i = j;
      if (j == -1) {
        return false;
      }
    }
    return true;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/text/StringContainsInOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */