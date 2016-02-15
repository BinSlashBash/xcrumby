package org.junit.experimental.runners;

import org.junit.runners.Suite;
import org.junit.runners.model.RunnerBuilder;

public class Enclosed
  extends Suite
{
  public Enclosed(Class<?> paramClass, RunnerBuilder paramRunnerBuilder)
    throws Throwable
  {
    super(paramRunnerBuilder, paramClass, paramClass.getClasses());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/runners/Enclosed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */