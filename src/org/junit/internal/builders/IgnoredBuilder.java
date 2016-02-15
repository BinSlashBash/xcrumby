package org.junit.internal.builders;

import org.junit.Ignore;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class IgnoredBuilder
  extends RunnerBuilder
{
  public Runner runnerForClass(Class<?> paramClass)
  {
    if (paramClass.getAnnotation(Ignore.class) != null) {
      return new IgnoredClassRunner(paramClass);
    }
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/builders/IgnoredBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */