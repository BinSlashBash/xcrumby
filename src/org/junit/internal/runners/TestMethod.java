package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Test.None;

@Deprecated
public class TestMethod
{
  private final Method fMethod;
  private TestClass fTestClass;
  
  public TestMethod(Method paramMethod, TestClass paramTestClass)
  {
    this.fMethod = paramMethod;
    this.fTestClass = paramTestClass;
  }
  
  boolean expectsException()
  {
    return getExpectedException() != null;
  }
  
  List<Method> getAfters()
  {
    return this.fTestClass.getAnnotatedMethods(After.class);
  }
  
  List<Method> getBefores()
  {
    return this.fTestClass.getAnnotatedMethods(Before.class);
  }
  
  protected Class<? extends Throwable> getExpectedException()
  {
    Test localTest = (Test)this.fMethod.getAnnotation(Test.class);
    if ((localTest == null) || (localTest.expected() == Test.None.class)) {
      return null;
    }
    return localTest.expected();
  }
  
  public long getTimeout()
  {
    Test localTest = (Test)this.fMethod.getAnnotation(Test.class);
    if (localTest == null) {
      return 0L;
    }
    return localTest.timeout();
  }
  
  public void invoke(Object paramObject)
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
    this.fMethod.invoke(paramObject, new Object[0]);
  }
  
  public boolean isIgnored()
  {
    return this.fMethod.getAnnotation(Ignore.class) != null;
  }
  
  boolean isUnexpected(Throwable paramThrowable)
  {
    return !getExpectedException().isAssignableFrom(paramThrowable.getClass());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/TestMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */