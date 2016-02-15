package org.junit.rules;

import java.util.ArrayList;
import java.util.List;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public abstract class TestWatcher
  implements TestRule
{
  private void failedQuietly(Throwable paramThrowable, Description paramDescription, List<Throwable> paramList)
  {
    try
    {
      failed(paramThrowable, paramDescription);
      return;
    }
    catch (Throwable paramThrowable)
    {
      paramList.add(paramThrowable);
    }
  }
  
  private void finishedQuietly(Description paramDescription, List<Throwable> paramList)
  {
    try
    {
      finished(paramDescription);
      return;
    }
    catch (Throwable paramDescription)
    {
      paramList.add(paramDescription);
    }
  }
  
  private void skippedQuietly(AssumptionViolatedException paramAssumptionViolatedException, Description paramDescription, List<Throwable> paramList)
  {
    try
    {
      skipped(paramAssumptionViolatedException, paramDescription);
      return;
    }
    catch (Throwable paramAssumptionViolatedException)
    {
      paramList.add(paramAssumptionViolatedException);
    }
  }
  
  private void startingQuietly(Description paramDescription, List<Throwable> paramList)
  {
    try
    {
      starting(paramDescription);
      return;
    }
    catch (Throwable paramDescription)
    {
      paramList.add(paramDescription);
    }
  }
  
  private void succeededQuietly(Description paramDescription, List<Throwable> paramList)
  {
    try
    {
      succeeded(paramDescription);
      return;
    }
    catch (Throwable paramDescription)
    {
      paramList.add(paramDescription);
    }
  }
  
  public Statement apply(final Statement paramStatement, final Description paramDescription)
  {
    new Statement()
    {
      public void evaluate()
        throws Throwable
      {
        localArrayList = new ArrayList();
        TestWatcher.this.startingQuietly(paramDescription, localArrayList);
        try
        {
          paramStatement.evaluate();
          TestWatcher.this.succeededQuietly(paramDescription, localArrayList);
        }
        catch (AssumptionViolatedException localAssumptionViolatedException)
        {
          for (;;)
          {
            localArrayList.add(localAssumptionViolatedException);
            TestWatcher.this.skippedQuietly(localAssumptionViolatedException, paramDescription, localArrayList);
            TestWatcher.this.finishedQuietly(paramDescription, localArrayList);
          }
        }
        catch (Throwable localThrowable)
        {
          for (;;)
          {
            localArrayList.add(localThrowable);
            TestWatcher.this.failedQuietly(localThrowable, paramDescription, localArrayList);
            TestWatcher.this.finishedQuietly(paramDescription, localArrayList);
          }
        }
        finally
        {
          TestWatcher.this.finishedQuietly(paramDescription, localArrayList);
        }
        MultipleFailureException.assertEmpty(localArrayList);
      }
    };
  }
  
  protected void failed(Throwable paramThrowable, Description paramDescription) {}
  
  protected void finished(Description paramDescription) {}
  
  protected void skipped(AssumptionViolatedException paramAssumptionViolatedException, Description paramDescription) {}
  
  protected void starting(Description paramDescription) {}
  
  protected void succeeded(Description paramDescription) {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/TestWatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */