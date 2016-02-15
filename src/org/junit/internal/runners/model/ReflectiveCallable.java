package org.junit.internal.runners.model;

import java.lang.reflect.InvocationTargetException;

public abstract class ReflectiveCallable
{
  public Object run()
    throws Throwable
  {
    try
    {
      Object localObject = runReflectiveCall();
      return localObject;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw localInvocationTargetException.getTargetException();
    }
  }
  
  protected abstract Object runReflectiveCall()
    throws Throwable;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/model/ReflectiveCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */