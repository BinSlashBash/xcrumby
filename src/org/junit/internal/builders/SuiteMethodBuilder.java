package org.junit.internal.builders;

import org.junit.internal.runners.SuiteMethod;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class SuiteMethodBuilder
  extends RunnerBuilder
{
  public boolean hasSuiteMethod(Class<?> paramClass)
  {
    try
    {
      paramClass.getMethod("suite", new Class[0]);
      return true;
    }
    catch (NoSuchMethodException paramClass) {}
    return false;
  }
  
  public Runner runnerForClass(Class<?> paramClass)
    throws Throwable
  {
    if (hasSuiteMethod(paramClass)) {
      return new SuiteMethod(paramClass);
    }
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/builders/SuiteMethodBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */