package org.hamcrest.collection;

import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

public class IsArrayContaining<T>
  extends TypeSafeMatcher<T[]>
{
  private final Matcher<? super T> elementMatcher;
  
  public IsArrayContaining(Matcher<? super T> paramMatcher)
  {
    this.elementMatcher = paramMatcher;
  }
  
  @Factory
  public static <T> Matcher<T[]> hasItemInArray(T paramT)
  {
    return hasItemInArray(IsEqual.equalTo(paramT));
  }
  
  @Factory
  public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> paramMatcher)
  {
    return new IsArrayContaining(paramMatcher);
  }
  
  public void describeMismatchSafely(T[] paramArrayOfT, Description paramDescription)
  {
    super.describeMismatch(Arrays.asList(paramArrayOfT), paramDescription);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("an array containing ").appendDescriptionOf(this.elementMatcher);
  }
  
  public boolean matchesSafely(T[] paramArrayOfT)
  {
    int j = paramArrayOfT.length;
    int i = 0;
    while (i < j)
    {
      T ? = paramArrayOfT[i];
      if (this.elementMatcher.matches(?)) {
        return true;
      }
      i += 1;
    }
    return false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsArrayContaining.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */