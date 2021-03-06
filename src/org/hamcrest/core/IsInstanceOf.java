package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsInstanceOf
  extends DiagnosingMatcher<Object>
{
  private final Class<?> expectedClass;
  private final Class<?> matchableClass;
  
  public IsInstanceOf(Class<?> paramClass)
  {
    this.expectedClass = paramClass;
    this.matchableClass = matchableClass(paramClass);
  }
  
  @Factory
  public static <T> Matcher<T> any(Class<T> paramClass)
  {
    return new IsInstanceOf(paramClass);
  }
  
  @Factory
  public static <T> Matcher<T> instanceOf(Class<?> paramClass)
  {
    return new IsInstanceOf(paramClass);
  }
  
  private static Class<?> matchableClass(Class<?> paramClass)
  {
    Object localObject;
    if (Boolean.TYPE.equals(paramClass)) {
      localObject = Boolean.class;
    }
    do
    {
      return (Class<?>)localObject;
      if (Byte.TYPE.equals(paramClass)) {
        return Byte.class;
      }
      if (Character.TYPE.equals(paramClass)) {
        return Character.class;
      }
      if (Double.TYPE.equals(paramClass)) {
        return Double.class;
      }
      if (Float.TYPE.equals(paramClass)) {
        return Float.class;
      }
      if (Integer.TYPE.equals(paramClass)) {
        return Integer.class;
      }
      if (Long.TYPE.equals(paramClass)) {
        return Long.class;
      }
      localObject = paramClass;
    } while (!Short.TYPE.equals(paramClass));
    return Short.class;
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("an instance of ").appendText(this.expectedClass.getName());
  }
  
  protected boolean matches(Object paramObject, Description paramDescription)
  {
    if (paramObject == null)
    {
      paramDescription.appendText("null");
      return false;
    }
    if (!this.matchableClass.isInstance(paramObject))
    {
      paramDescription.appendValue(paramObject).appendText(" is a " + paramObject.getClass().getName());
      return false;
    }
    return true;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/IsInstanceOf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */