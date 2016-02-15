package org.junit.runner.notification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Result;

public class RunNotifier
{
  private final List<RunListener> fListeners = Collections.synchronizedList(new ArrayList());
  private volatile boolean fPleaseStop = false;
  
  private void fireTestFailures(List<RunListener> paramList, final List<Failure> paramList1)
  {
    if (!paramList1.isEmpty()) {
      new SafeNotifier(paramList, paramList1)
      {
        protected void notifyListener(RunListener paramAnonymousRunListener)
          throws Exception
        {
          Iterator localIterator = paramList1.iterator();
          while (localIterator.hasNext()) {
            paramAnonymousRunListener.testFailure((Failure)localIterator.next());
          }
        }
      }.run();
    }
  }
  
  public void addFirstListener(RunListener paramRunListener)
  {
    this.fListeners.add(0, paramRunListener);
  }
  
  public void addListener(RunListener paramRunListener)
  {
    this.fListeners.add(paramRunListener);
  }
  
  public void fireTestAssumptionFailed(final Failure paramFailure)
  {
    new SafeNotifier(paramFailure)
    {
      protected void notifyListener(RunListener paramAnonymousRunListener)
        throws Exception
      {
        paramAnonymousRunListener.testAssumptionFailure(paramFailure);
      }
    }.run();
  }
  
  public void fireTestFailure(Failure paramFailure)
  {
    fireTestFailures(this.fListeners, Arrays.asList(new Failure[] { paramFailure }));
  }
  
  public void fireTestFinished(final Description paramDescription)
  {
    new SafeNotifier(paramDescription)
    {
      protected void notifyListener(RunListener paramAnonymousRunListener)
        throws Exception
      {
        paramAnonymousRunListener.testFinished(paramDescription);
      }
    }.run();
  }
  
  public void fireTestIgnored(final Description paramDescription)
  {
    new SafeNotifier(paramDescription)
    {
      protected void notifyListener(RunListener paramAnonymousRunListener)
        throws Exception
      {
        paramAnonymousRunListener.testIgnored(paramDescription);
      }
    }.run();
  }
  
  public void fireTestRunFinished(final Result paramResult)
  {
    new SafeNotifier(paramResult)
    {
      protected void notifyListener(RunListener paramAnonymousRunListener)
        throws Exception
      {
        paramAnonymousRunListener.testRunFinished(paramResult);
      }
    }.run();
  }
  
  public void fireTestRunStarted(final Description paramDescription)
  {
    new SafeNotifier(paramDescription)
    {
      protected void notifyListener(RunListener paramAnonymousRunListener)
        throws Exception
      {
        paramAnonymousRunListener.testRunStarted(paramDescription);
      }
    }.run();
  }
  
  public void fireTestStarted(final Description paramDescription)
    throws StoppedByUserException
  {
    if (this.fPleaseStop) {
      throw new StoppedByUserException();
    }
    new SafeNotifier(paramDescription)
    {
      protected void notifyListener(RunListener paramAnonymousRunListener)
        throws Exception
      {
        paramAnonymousRunListener.testStarted(paramDescription);
      }
    }.run();
  }
  
  public void pleaseStop()
  {
    this.fPleaseStop = true;
  }
  
  public void removeListener(RunListener paramRunListener)
  {
    this.fListeners.remove(paramRunListener);
  }
  
  private abstract class SafeNotifier
  {
    private final List<RunListener> fCurrentListeners;
    
    SafeNotifier()
    {
      this(RunNotifier.this.fListeners);
    }
    
    SafeNotifier()
    {
      List localList;
      this.fCurrentListeners = localList;
    }
    
    protected abstract void notifyListener(RunListener paramRunListener)
      throws Exception;
    
    void run()
    {
      ArrayList localArrayList2;
      synchronized (RunNotifier.this.fListeners)
      {
        ArrayList localArrayList1 = new ArrayList();
        localArrayList2 = new ArrayList();
        Iterator localIterator = this.fCurrentListeners.iterator();
        for (;;)
        {
          boolean bool = localIterator.hasNext();
          if (bool) {
            try
            {
              RunListener localRunListener = (RunListener)localIterator.next();
              notifyListener(localRunListener);
              localArrayList1.add(localRunListener);
            }
            catch (Exception localException)
            {
              localArrayList2.add(new Failure(Description.TEST_MECHANISM, localException));
            }
          }
        }
      }
      RunNotifier.this.fireTestFailures(localList2, localArrayList2);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runner/notification/RunNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */