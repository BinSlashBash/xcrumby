package org.junit.internal.runners.model;

import java.util.Iterator;
import java.util.List;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.MultipleFailureException;

public class EachTestNotifier
{
  private final Description fDescription;
  private final RunNotifier fNotifier;
  
  public EachTestNotifier(RunNotifier paramRunNotifier, Description paramDescription)
  {
    this.fNotifier = paramRunNotifier;
    this.fDescription = paramDescription;
  }
  
  private void addMultipleFailureException(MultipleFailureException paramMultipleFailureException)
  {
    paramMultipleFailureException = paramMultipleFailureException.getFailures().iterator();
    while (paramMultipleFailureException.hasNext()) {
      addFailure((Throwable)paramMultipleFailureException.next());
    }
  }
  
  public void addFailedAssumption(AssumptionViolatedException paramAssumptionViolatedException)
  {
    this.fNotifier.fireTestAssumptionFailed(new Failure(this.fDescription, paramAssumptionViolatedException));
  }
  
  public void addFailure(Throwable paramThrowable)
  {
    if ((paramThrowable instanceof MultipleFailureException))
    {
      addMultipleFailureException((MultipleFailureException)paramThrowable);
      return;
    }
    this.fNotifier.fireTestFailure(new Failure(this.fDescription, paramThrowable));
  }
  
  public void fireTestFinished()
  {
    this.fNotifier.fireTestFinished(this.fDescription);
  }
  
  public void fireTestIgnored()
  {
    this.fNotifier.fireTestIgnored(this.fDescription);
  }
  
  public void fireTestStarted()
  {
    this.fNotifier.fireTestStarted(this.fDescription);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/model/EachTestNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */