package org.junit.rules;

import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class Timeout
  implements TestRule
{
  private final int fMillis;
  
  public Timeout(int paramInt)
  {
    this.fMillis = paramInt;
  }
  
  public Statement apply(Statement paramStatement, Description paramDescription)
  {
    return new FailOnTimeout(paramStatement, this.fMillis);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/Timeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */