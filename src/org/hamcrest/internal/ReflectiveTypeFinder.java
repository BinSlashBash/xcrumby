package org.hamcrest.internal;

import java.lang.reflect.Method;

public class ReflectiveTypeFinder
{
  private final int expectedNumberOfParameters;
  private final String methodName;
  private final int typedParameter;
  
  public ReflectiveTypeFinder(String paramString, int paramInt1, int paramInt2)
  {
    this.methodName = paramString;
    this.expectedNumberOfParameters = paramInt1;
    this.typedParameter = paramInt2;
  }
  
  protected boolean canObtainExpectedTypeFrom(Method paramMethod)
  {
    return (paramMethod.getName().equals(this.methodName)) && (paramMethod.getParameterTypes().length == this.expectedNumberOfParameters) && (!paramMethod.isSynthetic());
  }
  
  protected Class<?> expectedTypeFrom(Method paramMethod)
  {
    return paramMethod.getParameterTypes()[this.typedParameter];
  }
  
  public Class<?> findExpectedType(Class<?> paramClass)
  {
    while (paramClass != Object.class)
    {
      Method[] arrayOfMethod = paramClass.getDeclaredMethods();
      int j = arrayOfMethod.length;
      int i = 0;
      while (i < j)
      {
        Method localMethod = arrayOfMethod[i];
        if (canObtainExpectedTypeFrom(localMethod)) {
          return expectedTypeFrom(localMethod);
        }
        i += 1;
      }
      paramClass = paramClass.getSuperclass();
    }
    throw new Error("Cannot determine correct type for " + this.methodName + "() method.");
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/internal/ReflectiveTypeFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */