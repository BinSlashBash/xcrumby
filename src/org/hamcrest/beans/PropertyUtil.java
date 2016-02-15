package org.hamcrest.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class PropertyUtil
{
  public static final Object[] NO_ARGUMENTS = new Object[0];
  
  public static PropertyDescriptor getPropertyDescriptor(String paramString, Object paramObject)
    throws IllegalArgumentException
  {
    paramObject = propertyDescriptorsFor(paramObject, null);
    int j = paramObject.length;
    int i = 0;
    while (i < j)
    {
      PropertyDescriptor localPropertyDescriptor = paramObject[i];
      if (localPropertyDescriptor.getName().equals(paramString)) {
        return localPropertyDescriptor;
      }
      i += 1;
    }
    return null;
  }
  
  public static PropertyDescriptor[] propertyDescriptorsFor(Object paramObject, Class<Object> paramClass)
    throws IllegalArgumentException
  {
    try
    {
      paramClass = Introspector.getBeanInfo(paramObject.getClass(), paramClass).getPropertyDescriptors();
      return paramClass;
    }
    catch (IntrospectionException paramClass)
    {
      throw new IllegalArgumentException("Could not get property descriptors for " + paramObject.getClass(), paramClass);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/beans/PropertyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */