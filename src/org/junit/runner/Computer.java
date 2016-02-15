package org.junit.runner;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Computer
{
  public static Computer serial()
  {
    return new Computer();
  }
  
  protected Runner getRunner(RunnerBuilder paramRunnerBuilder, Class<?> paramClass)
    throws Throwable
  {
    return paramRunnerBuilder.runnerForClass(paramClass);
  }
  
  public Runner getSuite(final RunnerBuilder paramRunnerBuilder, Class<?>[] paramArrayOfClass)
    throws InitializationError
  {
    new Suite(new RunnerBuilder()
    {
      public Runner runnerForClass(Class<?> paramAnonymousClass)
        throws Throwable
      {
        return Computer.this.getRunner(paramRunnerBuilder, paramAnonymousClass);
      }
    }, paramArrayOfClass);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runner/Computer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */