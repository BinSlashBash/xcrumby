package org.junit.runners;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Suite
  extends ParentRunner<Runner>
{
  private final List<Runner> fRunners;
  
  protected Suite(Class<?> paramClass, List<Runner> paramList)
    throws InitializationError
  {
    super(paramClass);
    this.fRunners = paramList;
  }
  
  public Suite(Class<?> paramClass, RunnerBuilder paramRunnerBuilder)
    throws InitializationError
  {
    this(paramRunnerBuilder, paramClass, getAnnotatedClasses(paramClass));
  }
  
  protected Suite(Class<?> paramClass, Class<?>[] paramArrayOfClass)
    throws InitializationError
  {
    this(new AllDefaultPossibilitiesBuilder(true), paramClass, paramArrayOfClass);
  }
  
  protected Suite(RunnerBuilder paramRunnerBuilder, Class<?> paramClass, Class<?>[] paramArrayOfClass)
    throws InitializationError
  {
    this(paramClass, paramRunnerBuilder.runners(paramClass, paramArrayOfClass));
  }
  
  public Suite(RunnerBuilder paramRunnerBuilder, Class<?>[] paramArrayOfClass)
    throws InitializationError
  {
    this(null, paramRunnerBuilder.runners(null, paramArrayOfClass));
  }
  
  public static Runner emptySuite()
  {
    try
    {
      Suite localSuite = new Suite((Class)null, new Class[0]);
      return localSuite;
    }
    catch (InitializationError localInitializationError)
    {
      throw new RuntimeException("This shouldn't be possible");
    }
  }
  
  private static Class<?>[] getAnnotatedClasses(Class<?> paramClass)
    throws InitializationError
  {
    SuiteClasses localSuiteClasses = (SuiteClasses)paramClass.getAnnotation(SuiteClasses.class);
    if (localSuiteClasses == null) {
      throw new InitializationError(String.format("class '%s' must have a SuiteClasses annotation", new Object[] { paramClass.getName() }));
    }
    return localSuiteClasses.value();
  }
  
  protected Description describeChild(Runner paramRunner)
  {
    return paramRunner.getDescription();
  }
  
  protected List<Runner> getChildren()
  {
    return this.fRunners;
  }
  
  protected void runChild(Runner paramRunner, RunNotifier paramRunNotifier)
  {
    paramRunner.run(paramRunNotifier);
  }
  
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.TYPE})
  public static @interface SuiteClasses
  {
    Class<?>[] value();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/Suite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */