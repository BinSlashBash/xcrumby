package org.junit.runner.notification;

import org.junit.runner.Description;
import org.junit.runner.Result;

public class RunListener
{
  public void testAssumptionFailure(Failure paramFailure) {}
  
  public void testFailure(Failure paramFailure)
    throws Exception
  {}
  
  public void testFinished(Description paramDescription)
    throws Exception
  {}
  
  public void testIgnored(Description paramDescription)
    throws Exception
  {}
  
  public void testRunFinished(Result paramResult)
    throws Exception
  {}
  
  public void testRunStarted(Description paramDescription)
    throws Exception
  {}
  
  public void testStarted(Description paramDescription)
    throws Exception
  {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runner/notification/RunListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */