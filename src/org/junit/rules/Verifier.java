package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class Verifier
  implements TestRule
{
  public Statement apply(final Statement paramStatement, Description paramDescription)
  {
    new Statement()
    {
      public void evaluate()
        throws Throwable
      {
        paramStatement.evaluate();
        Verifier.this.verify();
      }
    };
  }
  
  protected void verify()
    throws Throwable
  {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/Verifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */