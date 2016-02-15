package org.junit.internal.builders;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class IgnoredClassRunner
  extends Runner
{
  private final Class<?> fTestClass;
  
  public IgnoredClassRunner(Class<?> paramClass)
  {
    this.fTestClass = paramClass;
  }
  
  public Description getDescription()
  {
    return Description.createSuiteDescription(this.fTestClass);
  }
  
  public void run(RunNotifier paramRunNotifier)
  {
    paramRunNotifier.fireTestIgnored(getDescription());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/builders/IgnoredClassRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */