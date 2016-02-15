package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class ErrorReportingRunner
  extends Runner
{
  private final List<Throwable> fCauses;
  private final Class<?> fTestClass;
  
  public ErrorReportingRunner(Class<?> paramClass, Throwable paramThrowable)
  {
    this.fTestClass = paramClass;
    this.fCauses = getCauses(paramThrowable);
  }
  
  private Description describeCause(Throwable paramThrowable)
  {
    return Description.createTestDescription(this.fTestClass, "initializationError");
  }
  
  private List<Throwable> getCauses(Throwable paramThrowable)
  {
    if ((paramThrowable instanceof InvocationTargetException)) {
      return getCauses(paramThrowable.getCause());
    }
    if ((paramThrowable instanceof org.junit.runners.model.InitializationError)) {
      return ((org.junit.runners.model.InitializationError)paramThrowable).getCauses();
    }
    if ((paramThrowable instanceof InitializationError)) {
      return ((InitializationError)paramThrowable).getCauses();
    }
    return Arrays.asList(new Throwable[] { paramThrowable });
  }
  
  private void runCause(Throwable paramThrowable, RunNotifier paramRunNotifier)
  {
    Description localDescription = describeCause(paramThrowable);
    paramRunNotifier.fireTestStarted(localDescription);
    paramRunNotifier.fireTestFailure(new Failure(localDescription, paramThrowable));
    paramRunNotifier.fireTestFinished(localDescription);
  }
  
  public Description getDescription()
  {
    Description localDescription = Description.createSuiteDescription(this.fTestClass);
    Iterator localIterator = this.fCauses.iterator();
    while (localIterator.hasNext()) {
      localDescription.addChild(describeCause((Throwable)localIterator.next()));
    }
    return localDescription;
  }
  
  public void run(RunNotifier paramRunNotifier)
  {
    Iterator localIterator = this.fCauses.iterator();
    while (localIterator.hasNext()) {
      runCause((Throwable)localIterator.next(), paramRunNotifier);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/ErrorReportingRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */