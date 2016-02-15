package org.junit.rules;

import java.util.Iterator;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RunRules
  extends Statement
{
  private final Statement statement;
  
  public RunRules(Statement paramStatement, Iterable<TestRule> paramIterable, Description paramDescription)
  {
    this.statement = applyAll(paramStatement, paramIterable, paramDescription);
  }
  
  private static Statement applyAll(Statement paramStatement, Iterable<TestRule> paramIterable, Description paramDescription)
  {
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      paramStatement = ((TestRule)paramIterable.next()).apply(paramStatement, paramDescription);
    }
    return paramStatement;
  }
  
  public void evaluate()
    throws Throwable
  {
    this.statement.evaluate();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/RunRules.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */