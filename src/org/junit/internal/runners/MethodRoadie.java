package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

@Deprecated
public class MethodRoadie
{
  private final Description fDescription;
  private final RunNotifier fNotifier;
  private final Object fTest;
  private TestMethod fTestMethod;
  
  public MethodRoadie(Object paramObject, TestMethod paramTestMethod, RunNotifier paramRunNotifier, Description paramDescription)
  {
    this.fTest = paramObject;
    this.fNotifier = paramRunNotifier;
    this.fDescription = paramDescription;
    this.fTestMethod = paramTestMethod;
  }
  
  private void runAfters()
  {
    Iterator localIterator = this.fTestMethod.getAfters().iterator();
    while (localIterator.hasNext())
    {
      Method localMethod = (Method)localIterator.next();
      try
      {
        localMethod.invoke(this.fTest, new Object[0]);
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
      Iterator localIterator = this.fTestMethod.getBefores().iterator();
      while (localIterator.hasNext()) {
        ((Method)localIterator.next()).invoke(this.fTest, new Object[0]);
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
  
  private void runWithTimeout(final long paramLong)
  {
    runBeforesThenTestThenAfters(new Runnable()
    {
      public void run()
      {
        ExecutorService localExecutorService = Executors.newSingleThreadExecutor();
        Future localFuture = localExecutorService.submit(new Callable()
        {
          public Object call()
            throws Exception
          {
            MethodRoadie.this.runTestMethod();
            return null;
          }
        });
        localExecutorService.shutdown();
        try
        {
          if (!localExecutorService.awaitTermination(paramLong, TimeUnit.MILLISECONDS)) {
            localExecutorService.shutdownNow();
          }
          localFuture.get(0L, TimeUnit.MILLISECONDS);
          return;
        }
        catch (TimeoutException localTimeoutException)
        {
          MethodRoadie.this.addFailure(new Exception(String.format("test timed out after %d milliseconds", new Object[] { Long.valueOf(paramLong) })));
          return;
        }
        catch (Exception localException)
        {
          MethodRoadie.this.addFailure(localException);
        }
      }
    });
  }
  
  protected void addFailure(Throwable paramThrowable)
  {
    this.fNotifier.fireTestFailure(new Failure(this.fDescription, paramThrowable));
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 32	org/junit/internal/runners/MethodRoadie:fTestMethod	Lorg/junit/internal/runners/TestMethod;
    //   4: invokevirtual 108	org/junit/internal/runners/TestMethod:isIgnored	()Z
    //   7: ifeq +15 -> 22
    //   10: aload_0
    //   11: getfield 28	org/junit/internal/runners/MethodRoadie:fNotifier	Lorg/junit/runner/notification/RunNotifier;
    //   14: aload_0
    //   15: getfield 30	org/junit/internal/runners/MethodRoadie:fDescription	Lorg/junit/runner/Description;
    //   18: invokevirtual 112	org/junit/runner/notification/RunNotifier:fireTestIgnored	(Lorg/junit/runner/Description;)V
    //   21: return
    //   22: aload_0
    //   23: getfield 28	org/junit/internal/runners/MethodRoadie:fNotifier	Lorg/junit/runner/notification/RunNotifier;
    //   26: aload_0
    //   27: getfield 30	org/junit/internal/runners/MethodRoadie:fDescription	Lorg/junit/runner/Description;
    //   30: invokevirtual 115	org/junit/runner/notification/RunNotifier:fireTestStarted	(Lorg/junit/runner/Description;)V
    //   33: aload_0
    //   34: getfield 32	org/junit/internal/runners/MethodRoadie:fTestMethod	Lorg/junit/internal/runners/TestMethod;
    //   37: invokevirtual 119	org/junit/internal/runners/TestMethod:getTimeout	()J
    //   40: lstore_1
    //   41: lload_1
    //   42: lconst_0
    //   43: lcmp
    //   44: ifle +20 -> 64
    //   47: aload_0
    //   48: lload_1
    //   49: invokespecial 121	org/junit/internal/runners/MethodRoadie:runWithTimeout	(J)V
    //   52: aload_0
    //   53: getfield 28	org/junit/internal/runners/MethodRoadie:fNotifier	Lorg/junit/runner/notification/RunNotifier;
    //   56: aload_0
    //   57: getfield 30	org/junit/internal/runners/MethodRoadie:fDescription	Lorg/junit/runner/Description;
    //   60: invokevirtual 124	org/junit/runner/notification/RunNotifier:fireTestFinished	(Lorg/junit/runner/Description;)V
    //   63: return
    //   64: aload_0
    //   65: invokevirtual 127	org/junit/internal/runners/MethodRoadie:runTest	()V
    //   68: goto -16 -> 52
    //   71: astore_3
    //   72: aload_0
    //   73: getfield 28	org/junit/internal/runners/MethodRoadie:fNotifier	Lorg/junit/runner/notification/RunNotifier;
    //   76: aload_0
    //   77: getfield 30	org/junit/internal/runners/MethodRoadie:fDescription	Lorg/junit/runner/Description;
    //   80: invokevirtual 124	org/junit/runner/notification/RunNotifier:fireTestFinished	(Lorg/junit/runner/Description;)V
    //   83: aload_3
    //   84: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	this	MethodRoadie
    //   40	9	1	l	long
    //   71	13	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   33	41	71	finally
    //   47	52	71	finally
    //   64	68	71	finally
  }
  
  public void runBeforesThenTestThenAfters(Runnable paramRunnable)
  {
    try
    {
      runBefores();
      paramRunnable.run();
      return;
    }
    catch (FailedBefore paramRunnable) {}catch (Exception paramRunnable)
    {
      throw new RuntimeException("test should never throw an exception to this level");
    }
    finally
    {
      runAfters();
    }
  }
  
  public void runTest()
  {
    runBeforesThenTestThenAfters(new Runnable()
    {
      public void run()
      {
        MethodRoadie.this.runTestMethod();
      }
    });
  }
  
  protected void runTestMethod()
  {
    try
    {
      this.fTestMethod.invoke(this.fTest);
      if (this.fTestMethod.expectsException()) {
        addFailure(new AssertionError("Expected exception: " + this.fTestMethod.getExpectedException().getName()));
      }
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Throwable localThrowable1;
      do
      {
        do
        {
          localThrowable1 = localInvocationTargetException.getTargetException();
        } while ((localThrowable1 instanceof AssumptionViolatedException));
        if (!this.fTestMethod.expectsException())
        {
          addFailure(localThrowable1);
          return;
        }
      } while (!this.fTestMethod.isUnexpected(localThrowable1));
      addFailure(new Exception("Unexpected exception, expected<" + this.fTestMethod.getExpectedException().getName() + "> but was<" + localThrowable1.getClass().getName() + ">", localThrowable1));
      return;
    }
    catch (Throwable localThrowable2)
    {
      addFailure(localThrowable2);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/MethodRoadie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */