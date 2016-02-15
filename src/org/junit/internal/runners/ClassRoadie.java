package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

@Deprecated
public class ClassRoadie
{
  private Description fDescription;
  private RunNotifier fNotifier;
  private final Runnable fRunnable;
  private TestClass fTestClass;
  
  public ClassRoadie(RunNotifier paramRunNotifier, TestClass paramTestClass, Description paramDescription, Runnable paramRunnable)
  {
    this.fNotifier = paramRunNotifier;
    this.fTestClass = paramTestClass;
    this.fDescription = paramDescription;
    this.fRunnable = paramRunnable;
  }
  
  private void runAfters()
  {
    Iterator localIterator = this.fTestClass.getAfters().iterator();
    while (localIterator.hasNext())
    {
      Method localMethod = (Method)localIterator.next();
      try
      {
        localMethod.invoke(null, new Object[0]);
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        addFailure(localInvocationTargetException.getTargetException());
      }
      catch (Throwable localThrowable)
      {
        addFailure(localThrowable);
      }
    }
  }
  
  private void runBefores()
    throws FailedBefore
  {
    try
    {
      Iterator localIterator = this.fTestClass.getBefores().iterator();
      while (localIterator.hasNext()) {
        ((Method)localIterator.next()).invoke(null, new Object[0]);
      }
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw localInvocationTargetException.getTargetException();
    }
    catch (AssumptionViolatedException localAssumptionViolatedException)
    {
      throw new FailedBefore();
    }
    catch (Throwable localThrowable)
    {
      addFailure(localThrowable);
      throw new FailedBefore();
    }
  }
  
  protected void addFailure(Throwable paramThrowable)
  {
    this.fNotifier.fireTestFailure(new Failure(this.fDescription, paramThrowable));
  }
  
  public void runProtected()
  {
    try
    {
      runBefores();
      runUnprotected();
      runAfters();
      return;
    }
    catch (FailedBefore localFailedBefore)
    {
      localFailedBefore = localFailedBefore;
      runAfters();
      return;
    }
    finally
    {
      localObject = finally;
      runAfters();
      throw ((Throwable)localObject);
    }
  }
  
  protected void runUnprotected()
  {
    this.fRunnable.run();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/ClassRoadie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */