package org.junit.experimental.results;

import java.util.Iterator;
import java.util.List;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

class FailureList
{
  private final List<Failure> failures;
  
  public FailureList(List<Failure> paramList)
  {
    this.failures = paramList;
  }
  
  public Result result()
  {
    Result localResult = new Result();
    RunListener localRunListener = localResult.createListener();
    Iterator localIterator = this.failures.iterator();
    while (localIterator.hasNext())
    {
      Failure localFailure = (Failure)localIterator.next();
      try
      {
        localRunListener.testFailure(localFailure);
      }
      catch (Exception localException)
      {
        throw new RuntimeException("I can't believe this happened");
      }
    }
    return localException;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/results/FailureList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */