package org.junit.runner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class Result
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AtomicInteger fCount = new AtomicInteger();
  private final List<Failure> fFailures = Collections.synchronizedList(new ArrayList());
  private AtomicInteger fIgnoreCount = new AtomicInteger();
  private long fRunTime = 0L;
  private long fStartTime;
  
  public RunListener createListener()
  {
    return new Listener(null);
  }
  
  public int getFailureCount()
  {
    return this.fFailures.size();
  }
  
  public List<Failure> getFailures()
  {
    return this.fFailures;
  }
  
  public int getIgnoreCount()
  {
    return this.fIgnoreCount.get();
  }
  
  public int getRunCount()
  {
    return this.fCount.get();
  }
  
  public long getRunTime()
  {
    return this.fRunTime;
  }
  
  public boolean wasSuccessful()
  {
    return getFailureCount() == 0;
  }
  
  private class Listener
    extends RunListener
  {
    private Listener() {}
    
    public void testAssumptionFailure(Failure paramFailure) {}
    
    public void testFailure(Failure paramFailure)
      throws Exception
    {
      Result.this.fFailures.add(paramFailure);
    }
    
    public void testFinished(Description paramDescription)
      throws Exception
    {
      Result.this.fCount.getAndIncrement();
    }
    
    public void testIgnored(Description paramDescription)
      throws Exception
    {
      Result.this.fIgnoreCount.getAndIncrement();
    }
    
    public void testRunFinished(Result paramResult)
      throws Exception
    {
      long l = System.currentTimeMillis();
      Result.access$114(Result.this, l - Result.this.fStartTime);
    }
    
    public void testRunStarted(Description paramDescription)
      throws Exception
    {
      Result.access$002(Result.this, System.currentTimeMillis());
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runner/Result.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */