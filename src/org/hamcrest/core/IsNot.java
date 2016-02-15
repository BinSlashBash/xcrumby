package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsNot<T>
  extends BaseMatcher<T>
{
  private final Matcher<T> matcher;
  
  public IsNot(Matcher<T> paramMatcher)
  {
    this.matcher = paramMatcher;
  }
  
  @Factory
  public static <T> Matcher<T> not(T paramT)
  {
    return not(IsEqual.equalTo(paramT));
  }
  
  @Factory
  public static <T> Matcher<T> not(Matcher<T> paramMatcher)
  {
    return new IsNot(paramMatcher);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("not ").appendDescriptionOf(this.matcher);
  }
  
  public boolean matches(Object paramObject)
  {
    return !this.matcher.matches(paramObject);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/IsNot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */