package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract interface TestRule
{
  public abstract Statement apply(Statement paramStatement, Description paramDescription);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/TestRule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */