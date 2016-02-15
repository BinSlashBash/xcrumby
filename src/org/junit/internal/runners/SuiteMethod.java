package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import junit.framework.Test;

public class SuiteMethod
  extends JUnit38ClassRunner
{
  public SuiteMethod(Class<?> paramClass)
    throws Throwable
  {
    super(testFromSuiteMethod(paramClass));
  }
  
  public static Test testFromSuiteMethod(Class<?> paramClass)
    throws Throwable
  {
    Method localMethod;
    try
    {
      localMethod = paramClass.getMethod("suite", new Class[0]);
      if (!Modifier.isStatic(localMethod.getModifiers())) {
        throw new Exception(paramClass.getName() + ".suite() must be static");
      }
    }
    catch (InvocationTargetException paramClass)
    {
      throw paramClass.getCause();
    }
    paramClass = (Test)localMethod.invoke(null, new Object[0]);
    return paramClass;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/SuiteMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */