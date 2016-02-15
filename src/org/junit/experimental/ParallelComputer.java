package org.junit.experimental;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.runner.Computer;
import org.junit.runner.Runner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.RunnerScheduler;

public class ParallelComputer
  extends Computer
{
  private final boolean fClasses;
  private final boolean fMethods;
  
  public ParallelComputer(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.fClasses = paramBoolean1;
    this.fMethods = paramBoolean2;
  }
  
  public static Computer classes()
  {
    return new ParallelComputer(true, false);
  }
  
  public static Computer methods()
  {
    return new ParallelComputer(false, true);
  }
  
  private static Runner parallelize(Runner paramRunner)
  {
    if ((paramRunner instanceof ParentRunner)) {
      ((ParentRunner)paramRunner).setScheduler(new RunnerScheduler()
      {
        private final ExecutorService fService = Executors.newCachedThreadPool();
        
        public void finished()
        {
          try
          {
            this.fService.shutdown();
            this.fService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace(System.err);
          }
        }
        
        public void schedule(Runnable paramAnonymousRunnable)
        {
          this.fService.submit(paramAnonymousRunnable);
        }
      });
    }
    return paramRunner;
  }
  
  protected Runner getRunner(RunnerBuilder paramRunnerBuilder, Class<?> paramClass)
    throws Throwable
  {
    paramClass = super.getRunner(paramRunnerBuilder, paramClass);
    paramRunnerBuilder = paramClass;
    if (this.fMethods) {
      paramRunnerBuilder = parallelize(paramClass);
    }
    return paramRunnerBuilder;
  }
  
  public Runner getSuite(RunnerBuilder paramRunnerBuilder, Class<?>[] paramArrayOfClass)
    throws InitializationError
  {
    paramArrayOfClass = super.getSuite(paramRunnerBuilder, paramArrayOfClass);
    paramRunnerBuilder = paramArrayOfClass;
    if (this.fClasses) {
      paramRunnerBuilder = parallelize(paramArrayOfClass);
    }
    return paramRunnerBuilder;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/ParallelComputer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */