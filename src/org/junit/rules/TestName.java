package org.junit.rules;

import org.junit.runner.Description;

public class TestName
  extends TestWatcher
{
  private String fName;
  
  public String getMethodName()
  {
    return this.fName;
  }
  
  protected void starting(Description paramDescription)
  {
    this.fName = paramDescription.getMethodName();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/TestName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */