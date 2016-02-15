package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class Is<T>
  extends BaseMatcher<T>
{
  private final Matcher<T> matcher;
  
  public Is(Matcher<T> paramMatcher)
  {
    this.matcher = paramMatcher;
  }
  
  @Deprecated
  @Factory
  public static <T> Matcher<T> is(Class<T> paramClass)
  {
    return is(IsInstanceOf.instanceOf(paramClass));
  }
  
  @Factory
  public static <T> Matcher<T> is(T paramT)
  {
    return is(IsEqual.equalTo(paramT));
  }
  
  @Factory
  public static <T> Matcher<T> is(Matcher<T> paramMatcher)
  {
    return new Is(paramMatcher);
  }
  
  @Factory
  public static <T> Matcher<T> isA(Class<T> paramClass)
  {
    return is(IsInstanceOf.instanceOf(paramClass));
  }
  
  public void describeMismatch(Object paramObject, Description paramDescription)
  {
    this.matcher.describeMismatch(paramObject, paramDescription);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("is ").appendDescriptionOf(this.matcher);
  }
  
  public boolean matches(Object paramObject)
  {
    return this.matcher.matches(paramObject);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/Is.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */