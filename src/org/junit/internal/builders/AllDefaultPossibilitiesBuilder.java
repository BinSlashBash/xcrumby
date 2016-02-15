package org.junit.internal.builders;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class AllDefaultPossibilitiesBuilder
  extends RunnerBuilder
{
  private final boolean fCanUseSuiteMethod;
  
  public AllDefaultPossibilitiesBuilder(boolean paramBoolean)
  {
    this.fCanUseSuiteMethod = paramBoolean;
  }
  
  protected AnnotatedBuilder annotatedBuilder()
  {
    return new AnnotatedBuilder(this);
  }
  
  protected IgnoredBuilder ignoredBuilder()
  {
    return new IgnoredBuilder();
  }
  
  protected JUnit3Builder junit3Builder()
  {
    return new JUnit3Builder();
  }
  
  protected JUnit4Builder junit4Builder()
  {
    return new JUnit4Builder();
  }
  
  public Runner runnerForClass(Class<?> paramClass)
    throws Throwable
  {
    Iterator localIterator = Arrays.asList(new RunnerBuilder[] { ignoredBuilder(), annotatedBuilder(), suiteMethodBuilder(), junit3Builder(), junit4Builder() }).iterator();
    while (localIterator.hasNext())
    {
      Runner localRunner = ((RunnerBuilder)localIterator.next()).safeRunnerForClass(paramClass);
      if (localRunner != null) {
        return localRunner;
      }
    }
    return null;
  }
  
  protected RunnerBuilder suiteMethodBuilder()
  {
    if (this.fCanUseSuiteMethod) {
      return new SuiteMethodBuilder();
    }
    return new NullBuilder();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/builders/AllDefaultPossibilitiesBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */