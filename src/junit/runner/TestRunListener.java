package junit.runner;

public abstract interface TestRunListener
{
  public static final int STATUS_ERROR = 1;
  public static final int STATUS_FAILURE = 2;
  
  public abstract void testEnded(String paramString);
  
  public abstract void testFailed(int paramInt, String paramString1, String paramString2);
  
  public abstract void testRunEnded(long paramLong);
  
  public abstract void testRunStarted(String paramString, int paramInt);
  
  public abstract void testRunStopped(long paramLong);
  
  public abstract void testStarted(String paramString);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/runner/TestRunListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */