package org.junit.internal.builders;

import java.lang.reflect.Constructor;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class AnnotatedBuilder
  extends RunnerBuilder
{
  private static final String CONSTRUCTOR_ERROR_FORMAT = "Custom runner class %s should have a public constructor with signature %s(Class testClass)";
  private RunnerBuilder fSuiteBuilder;
  
  public AnnotatedBuilder(RunnerBuilder paramRunnerBuilder)
  {
    this.fSuiteBuilder = paramRunnerBuilder;
  }
  
  public Runner buildRunner(Class<? extends Runner> paramClass, Class<?> paramClass1)
    throws Exception
  {
    try
    {
      Runner localRunner = (Runner)paramClass.getConstructor(new Class[] { Class.class }).newInstance(new Object[] { paramClass1 });
      return localRunner;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      try
      {
        paramClass1 = (Runner)paramClass.getConstructor(new Class[] { Class.class, RunnerBuilder.class }).newInstance(new Object[] { paramClass1, this.fSuiteBuilder });
        return paramClass1;
      }
      catch (NoSuchMethodException paramClass1)
      {
        paramClass = paramClass.getSimpleName();
        throw new InitializationError(String.format("Custom runner class %s should have a public constructor with signature %s(Class testClass)", new Object[] { paramClass, paramClass }));
      }
    }
  }
  
  public Runner runnerForClass(Class<?> paramClass)
    throws Exception
  {
    RunWith localRunWith = (RunWith)paramClass.getAnnotation(RunWith.class);
    if (localRunWith != null) {
      return buildRunner(localRunWith.value(), paramClass);
    }
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/builders/AnnotatedBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */