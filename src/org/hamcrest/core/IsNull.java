package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsNull<T>
  extends BaseMatcher<T>
{
  @Factory
  public static Matcher<Object> notNullValue()
  {
    return IsNot.not(nullValue());
  }
  
  @Factory
  public static <T> Matcher<T> notNullValue(Class<T> paramClass)
  {
    return IsNot.not(nullValue(paramClass));
  }
  
  @Factory
  public static Matcher<Object> nullValue()
  {
    return new IsNull();
  }
  
  @Factory
  public static <T> Matcher<T> nullValue(Class<T> paramClass)
  {
    return new IsNull();
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("null");
  }
  
  public boolean matches(Object paramObject)
  {
    return paramObject == null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/IsNull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */