package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsSame<T>
  extends BaseMatcher<T>
{
  private final T object;
  
  public IsSame(T paramT)
  {
    this.object = paramT;
  }
  
  @Factory
  public static <T> Matcher<T> sameInstance(T paramT)
  {
    return new IsSame(paramT);
  }
  
  @Factory
  public static <T> Matcher<T> theInstance(T paramT)
  {
    return new IsSame(paramT);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("sameInstance(").appendValue(this.object).appendText(")");
  }
  
  public boolean matches(Object paramObject)
  {
    return paramObject == this.object;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/IsSame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */