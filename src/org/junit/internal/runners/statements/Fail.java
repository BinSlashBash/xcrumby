package org.junit.internal.runners.statements;

import org.junit.runners.model.Statement;

public class Fail
  extends Statement
{
  private final Throwable fError;
  
  public Fail(Throwable paramThrowable)
  {
    this.fError = paramThrowable;
  }
  
  public void evaluate()
    throws Throwable
  {
    throw this.fError;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/statements/Fail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */