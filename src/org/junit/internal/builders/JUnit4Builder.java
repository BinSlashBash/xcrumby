package org.junit.internal.builders;

import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.RunnerBuilder;

public class JUnit4Builder
  extends RunnerBuilder
{
  public Runner runnerForClass(Class<?> paramClass)
    throws Throwable
  {
    return new BlockJUnit4ClassRunner(paramClass);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/builders/JUnit4Builder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */