package junit.framework;

public abstract interface TestListener
{
  public abstract void addError(Test paramTest, Throwable paramThrowable);
  
  public abstract void addFailure(Test paramTest, AssertionFailedError paramAssertionFailedError);
  
  public abstract void endTest(Test paramTest);
  
  public abstract void startTest(Test paramTest);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/framework/TestListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */