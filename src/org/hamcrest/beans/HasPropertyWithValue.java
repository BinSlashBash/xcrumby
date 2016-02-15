package org.hamcrest.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import org.hamcrest.Condition;
import org.hamcrest.Condition.Step;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasPropertyWithValue<T>
  extends TypeSafeDiagnosingMatcher<T>
{
  private static final Condition.Step<PropertyDescriptor, Method> WITH_READ_METHOD = ;
  private final String propertyName;
  private final Matcher<Object> valueMatcher;
  
  public HasPropertyWithValue(String paramString, Matcher<?> paramMatcher)
  {
    this.propertyName = paramString;
    this.valueMatcher = nastyGenericsWorkaround(paramMatcher);
  }
  
  @Factory
  public static <T> Matcher<T> hasProperty(String paramString, Matcher<?> paramMatcher)
  {
    return new HasPropertyWithValue(paramString, paramMatcher);
  }
  
  private static Matcher<Object> nastyGenericsWorkaround(Matcher<?> paramMatcher)
  {
    return paramMatcher;
  }
  
  private Condition<PropertyDescriptor> propertyOn(T paramT, Description paramDescription)
  {
    paramT = PropertyUtil.getPropertyDescriptor(this.propertyName, paramT);
    if (paramT == null)
    {
      paramDescription.appendText("No property \"" + this.propertyName + "\"");
      return Condition.notMatched();
    }
    return Condition.matched(paramT, paramDescription);
  }
  
  private Condition.Step<Method, Object> withPropertyValue(final T paramT)
  {
    new Condition.Step()
    {
      public Condition<Object> apply(Method paramAnonymousMethod, Description paramAnonymousDescription)
      {
        try
        {
          paramAnonymousMethod = Condition.matched(paramAnonymousMethod.invoke(paramT, PropertyUtil.NO_ARGUMENTS), paramAnonymousDescription);
          return paramAnonymousMethod;
        }
        catch (Exception paramAnonymousMethod)
        {
          paramAnonymousDescription.appendText(paramAnonymousMethod.getMessage());
        }
        return Condition.notMatched();
      }
    };
  }
  
  private static Condition.Step<PropertyDescriptor, Method> withReadMethod()
  {
    new Condition.Step()
    {
      public Condition<Method> apply(PropertyDescriptor paramAnonymousPropertyDescriptor, Description paramAnonymousDescription)
      {
        Method localMethod = paramAnonymousPropertyDescriptor.getReadMethod();
        if (localMethod == null)
        {
          paramAnonymousDescription.appendText("property \"" + paramAnonymousPropertyDescriptor.getName() + "\" is not readable");
          return Condition.notMatched();
        }
        return Condition.matched(localMethod, paramAnonymousDescription);
      }
    };
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("hasProperty(").appendValue(this.propertyName).appendText(", ").appendDescriptionOf(this.valueMatcher).appendText(")");
  }
  
  public boolean matchesSafely(T paramT, Description paramDescription)
  {
    return propertyOn(paramT, paramDescription).and(WITH_READ_METHOD).and(withPropertyValue(paramT)).matching(this.valueMatcher, "property '" + this.propertyName + "' ");
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/beans/HasPropertyWithValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */