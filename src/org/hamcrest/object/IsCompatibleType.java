package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCompatibleType<T>
  extends TypeSafeMatcher<Class<?>>
{
  private final Class<T> type;
  
  public IsCompatibleType(Class<T> paramClass)
  {
    this.type = paramClass;
  }
  
  @Factory
  public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> paramClass)
  {
    return new IsCompatibleType(paramClass);
  }
  
  public void describeMismatchSafely(Class<?> paramClass, Description paramDescription)
  {
    paramDescription.appendValue(paramClass.getName());
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("type < ").appendText(this.type.getName());
  }
  
  public boolean matchesSafely(Class<?> paramClass)
  {
    return this.type.isAssignableFrom(paramClass);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/object/IsCompatibleType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */