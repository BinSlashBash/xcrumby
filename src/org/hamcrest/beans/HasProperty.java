package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HasProperty<T>
  extends TypeSafeMatcher<T>
{
  private final String propertyName;
  
  public HasProperty(String paramString)
  {
    this.propertyName = paramString;
  }
  
  @Factory
  public static <T> Matcher<T> hasProperty(String paramString)
  {
    return new HasProperty(paramString);
  }
  
  public void describeMismatchSafely(T paramT, Description paramDescription)
  {
    paramDescription.appendText("no ").appendValue(this.propertyName).appendText(" in ").appendValue(paramT);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("hasProperty(").appendValue(this.propertyName).appendText(")");
  }
  
  public boolean matchesSafely(T paramT)
  {
    boolean bool = false;
    try
    {
      paramT = PropertyUtil.getPropertyDescriptor(this.propertyName, paramT);
      if (paramT != null) {
        bool = true;
      }
      return bool;
    }
    catch (IllegalArgumentException paramT) {}
    return false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/beans/HasProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */