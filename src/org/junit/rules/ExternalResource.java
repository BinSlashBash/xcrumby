package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class ExternalResource
  implements TestRule
{
  private Statement statement(final Statement paramStatement)
  {
    new Statement()
    {
      public void evaluate()
        throws Throwable
      {
        ExternalResource.this.before();
        try
        {
          paramStatement.evaluate();
          return;
        }
        finally
        {
          ExternalResource.this.after();
        }
      }
    };
  }
  
  protected void after() {}
  
  public Statement apply(Statement paramStatement, Description paramDescription)
  {
    return statement(paramStatement);
  }
  
  protected void before()
    throws Throwable
  {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/ExternalResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */