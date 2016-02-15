package org.junit.internal.runners.statements;

import org.junit.runners.model.Statement;

public class FailOnTimeout
  extends Statement
{
  private final Statement fOriginalStatement;
  private final long fTimeout;
  
  public FailOnTimeout(Statement paramStatement, long paramLong)
  {
    this.fOriginalStatement = paramStatement;
    this.fTimeout = paramLong;
  }
  
  private StatementThread evaluateStatement()
    throws InterruptedException
  {
    StatementThread localStatementThread = new StatementThread(this.fOriginalStatement);
    localStatementThread.start();
    localStatementThread.join(this.fTimeout);
    if (!localStatementThread.fFinished) {
      localStatementThread.recordStackTrace();
    }
    localStatementThread.interrupt();
    return localStatementThread;
  }
  
  private void throwExceptionForUnfinishedThread(StatementThread paramStatementThread)
    throws Throwable
  {
    if (paramStatementThread.fExceptionThrownByOriginalStatement != null) {
      throw paramStatementThread.fExceptionThrownByOriginalStatement;
    }
    throwTimeoutException(paramStatementThread);
  }
  
  private void throwTimeoutException(StatementThread paramStatementThread)
    throws Exception
  {
    Exception localException = new Exception(String.format("test timed out after %d milliseconds", new Object[] { Long.valueOf(this.fTimeout) }));
    localException.setStackTrace(paramStatementThread.getRecordedStackTrace());
    throw localException;
  }
  
  public void evaluate()
    throws Throwable
  {
    StatementThread localStatementThread = evaluateStatement();
    if (!localStatementThread.fFinished) {
      throwExceptionForUnfinishedThread(localStatementThread);
    }
  }
  
  private static class StatementThread
    extends Thread
  {
    private Throwable fExceptionThrownByOriginalStatement = null;
    private boolean fFinished = false;
    private StackTraceElement[] fRecordedStackTrace = null;
    private final Statement fStatement;
    
    public StatementThread(Statement paramStatement)
    {
      this.fStatement = paramStatement;
    }
    
    public StackTraceElement[] getRecordedStackTrace()
    {
      return this.fRecordedStackTrace;
    }
    
    public void recordStackTrace()
    {
      this.fRecordedStackTrace = getStackTrace();
    }
    
    public void run()
    {
      try
      {
        this.fStatement.evaluate();
        this.fFinished = true;
        return;
      }
      catch (Throwable localThrowable)
      {
        this.fExceptionThrownByOriginalStatement = localThrowable;
        return;
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/statements/FailOnTimeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */