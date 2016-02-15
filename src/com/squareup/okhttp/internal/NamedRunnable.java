package com.squareup.okhttp.internal;

public abstract class NamedRunnable
  implements Runnable
{
  private final String name;
  
  public NamedRunnable(String paramString, Object... paramVarArgs)
  {
    this.name = String.format(paramString, paramVarArgs);
  }
  
  protected abstract void execute();
  
  public final void run()
  {
    String str = Thread.currentThread().getName();
    Thread.currentThread().setName(this.name);
    try
    {
      execute();
      return;
    }
    finally
    {
      Thread.currentThread().setName(str);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/okhttp/internal/NamedRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */