package org.hamcrest.collection;

import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsArray<T>
  extends TypeSafeMatcher<T[]>
{
  private final Matcher<? super T>[] elementMatchers;
  
  public IsArray(Matcher<? super T>[] paramArrayOfMatcher)
  {
    this.elementMatchers = ((Matcher[])paramArrayOfMatcher.clone());
  }
  
  @Factory
  public static <T> IsArray<T> array(Matcher<? super T>... paramVarArgs)
  {
    return new IsArray(paramVarArgs);
  }
  
  public void describeMismatchSafely(T[] paramArrayOfT, Description paramDescription)
  {
    if (paramArrayOfT.length != this.elementMatchers.length) {
      paramDescription.appendText("array length was " + paramArrayOfT.length);
    }
    for (;;)
    {
      return;
      int i = 0;
      while (i < paramArrayOfT.length)
      {
        if (!this.elementMatchers[i].matches(paramArrayOfT[i]))
        {
          paramDescription.appendText("element " + i + " was ").appendValue(paramArrayOfT[i]);
          return;
        }
        i += 1;
      }
    }
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendList(descriptionStart(), descriptionSeparator(), descriptionEnd(), Arrays.asList(this.elementMatchers));
  }
  
  protected String descriptionEnd()
  {
    return "]";
  }
  
  protected String descriptionSeparator()
  {
    return ", ";
  }
  
  protected String descriptionStart()
  {
    return "[";
  }
  
  public boolean matchesSafely(T[] paramArrayOfT)
  {
    if (paramArrayOfT.length != this.elementMatchers.length) {
      return false;
    }
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayOfT.length) {
        break label44;
      }
      if (!this.elementMatchers[i].matches(paramArrayOfT[i])) {
        break;
      }
      i += 1;
    }
    label44:
    return true;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */