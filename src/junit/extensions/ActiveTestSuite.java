package junit.extensions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class ActiveTestSuite
  extends TestSuite
{
  private volatile int fActiveTestDeathCount;
  
  public ActiveTestSuite() {}
  
  public ActiveTestSuite(Class<? extends TestCase> paramClass)
  {
    super(paramClass);
  }
  
  public ActiveTestSuite(Class<? extends TestCase> paramClass, String paramString)
  {
    super(paramClass, paramString);
  }
  
  public ActiveTestSuite(String paramString)
  {
    super(paramString);
  }
  
  public void run(TestResult paramTestResult)
  {
    this.fActiveTestDeathCount = 0;
    super.run(paramTestResult);
    waitUntilFinished();
  }
  
  public void runFinished()
  {
    try
    {
      this.fActiveTestDeathCount += 1;
      notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void runTest(final Test paramTest, final TestResult paramTestResult)
  {
    new Thread()
    {
      public void run()
      {
        try
        {
          paramTest.run(paramTestResult);
          return;
        }
        finally
        {
          ActiveTestSuite.this.runFinished();
        }
      }
    }.start();
  }
  
  void waitUntilFinished()
  {
    try
    {
      for (;;)
      {
        int i = this.fActiveTestDeathCount;
        int j = testCount();
        if (i < j) {
          try
          {
            wait();
          }
          catch (InterruptedException localInterruptedException) {}
        }
      }
      return;
    }
    finally {}
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/extensions/ActiveTestSuite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */