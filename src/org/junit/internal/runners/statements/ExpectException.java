package org.junit.internal.runners.statements;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.model.Statement;

public class ExpectException
  extends Statement
{
  private final Class<? extends Throwable> fExpected;
  private Statement fNext;
  
  public ExpectException(Statement paramStatement, Class<? extends Throwable> paramClass)
  {
    this.fNext = paramStatement;
    this.fExpected = paramClass;
  }
  
  public void evaluate()
    throws Exception
  {
    int i = 0;
    try
    {
      this.fNext.evaluate();
      i = 1;
    }
    catch (AssumptionViolatedException localAssumptionViolatedException)
    {
      throw localAssumptionViolatedException;
    }
    catch (Throwable localThrowable)
    {
      while (this.fExpected.isAssignableFrom(localThrowable.getClass())) {}
      throw new Exception("Unexpected exception, expected<" + this.fExpected.getName() + "> but was<" + localThrowable.getClass().getName() + ">", localThrowable);
    }
    if (i != 0) {
      throw new AssertionError("Expected exception: " + this.fExpected.getName());
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/statements/ExpectException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */