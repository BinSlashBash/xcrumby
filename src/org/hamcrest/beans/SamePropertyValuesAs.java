package org.hamcrest.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class SamePropertyValuesAs<T>
  extends TypeSafeDiagnosingMatcher<T>
{
  private final T expectedBean;
  private final List<PropertyMatcher> propertyMatchers;
  private final Set<String> propertyNames;
  
  public SamePropertyValuesAs(T paramT)
  {
    PropertyDescriptor[] arrayOfPropertyDescriptor = PropertyUtil.propertyDescriptorsFor(paramT, Object.class);
    this.expectedBean = paramT;
    this.propertyNames = propertyNamesFrom(arrayOfPropertyDescriptor);
    this.propertyMatchers = propertyMatchersFor(paramT, arrayOfPropertyDescriptor);
  }
  
  private boolean hasMatchingValues(T paramT, Description paramDescription)
  {
    Iterator localIterator = this.propertyMatchers.iterator();
    while (localIterator.hasNext())
    {
      PropertyMatcher localPropertyMatcher = (PropertyMatcher)localIterator.next();
      if (!localPropertyMatcher.matches(paramT))
      {
        localPropertyMatcher.describeMismatch(paramT, paramDescription);
        return false;
      }
    }
    return true;
  }
  
  private boolean hasNoExtraProperties(T paramT, Description paramDescription)
  {
    paramT = propertyNamesFrom(PropertyUtil.propertyDescriptorsFor(paramT, Object.class));
    paramT.removeAll(this.propertyNames);
    if (!paramT.isEmpty())
    {
      paramDescription.appendText("has extra properties called " + paramT);
      return false;
    }
    return true;
  }
  
  private boolean isCompatibleType(T paramT, Description paramDescription)
  {
    if (!this.expectedBean.getClass().isAssignableFrom(paramT.getClass()))
    {
      paramDescription.appendText("is incompatible type: " + paramT.getClass().getSimpleName());
      return false;
    }
    return true;
  }
  
  private static <T> List<PropertyMatcher> propertyMatchersFor(T paramT, PropertyDescriptor[] paramArrayOfPropertyDescriptor)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfPropertyDescriptor.length);
    int j = paramArrayOfPropertyDescriptor.length;
    int i = 0;
    while (i < j)
    {
      localArrayList.add(new PropertyMatcher(paramArrayOfPropertyDescriptor[i], paramT));
      i += 1;
    }
    return localArrayList;
  }
  
  private static Set<String> propertyNamesFrom(PropertyDescriptor[] paramArrayOfPropertyDescriptor)
  {
    HashSet localHashSet = new HashSet();
    int j = paramArrayOfPropertyDescriptor.length;
    int i = 0;
    while (i < j)
    {
      localHashSet.add(paramArrayOfPropertyDescriptor[i].getDisplayName());
      i += 1;
    }
    return localHashSet;
  }
  
  private static Object readProperty(Method paramMethod, Object paramObject)
  {
    try
    {
      Object localObject = paramMethod.invoke(paramObject, PropertyUtil.NO_ARGUMENTS);
      return localObject;
    }
    catch (Exception localException)
    {
      throw new IllegalArgumentException("Could not invoke " + paramMethod + " on " + paramObject, localException);
    }
  }
  
  @Factory
  public static <T> Matcher<T> samePropertyValuesAs(T paramT)
  {
    return new SamePropertyValuesAs(paramT);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("same property values as " + this.expectedBean.getClass().getSimpleName()).appendList(" [", ", ", "]", this.propertyMatchers);
  }
  
  public boolean matchesSafely(T paramT, Description paramDescription)
  {
    return (isCompatibleType(paramT, paramDescription)) && (hasNoExtraProperties(paramT, paramDescription)) && (hasMatchingValues(paramT, paramDescription));
  }
  
  public static class PropertyMatcher
    extends DiagnosingMatcher<Object>
  {
    private final Matcher<Object> matcher;
    private final String propertyName;
    private final Method readMethod;
    
    public PropertyMatcher(PropertyDescriptor paramPropertyDescriptor, Object paramObject)
    {
      this.propertyName = paramPropertyDescriptor.getDisplayName();
      this.readMethod = paramPropertyDescriptor.getReadMethod();
      this.matcher = IsEqual.equalTo(SamePropertyValuesAs.readProperty(this.readMethod, paramObject));
    }
    
    public void describeTo(Description paramDescription)
    {
      paramDescription.appendText(this.propertyName + ": ").appendDescriptionOf(this.matcher);
    }
    
    public boolean matches(Object paramObject, Description paramDescription)
    {
      paramObject = SamePropertyValuesAs.readProperty(this.readMethod, paramObject);
      if (!this.matcher.matches(paramObject))
      {
        paramDescription.appendText(this.propertyName + " ");
        this.matcher.describeMismatch(paramObject, paramDescription);
        return false;
      }
      return true;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/beans/SamePropertyValuesAs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */