package org.junit.internal.builders;

import junit.framework.TestCase;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class JUnit3Builder
  extends RunnerBuilder
{
  boolean isPre4Test(Class<?> paramClass)
  {
    return TestCase.class.isAssignableFrom(paramClass);
  }
  
  public Runner runnerForClass(Class<?> paramClass)
    throws Throwable
  {
    if (isPre4Test(paramClass)) {
      return new JUnit38ClassRunner(paramClass);
    }
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/builders/JUnit3Builder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */