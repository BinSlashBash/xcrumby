package org.junit.runners.model;

public abstract interface RunnerScheduler
{
  public abstract void finished();
  
  public abstract void schedule(Runnable paramRunnable);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/model/RunnerScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */