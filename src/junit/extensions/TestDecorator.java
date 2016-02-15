package junit.extensions;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestResult;

public class TestDecorator
  extends Assert
  implements Test
{
  protected Test fTest;
  
  public TestDecorator(Test paramTest)
  {
    this.fTest = paramTest;
  }
  
  public void basicRun(TestResult paramTestResult)
  {
    this.fTest.run(paramTestResult);
  }
  
  public int countTestCases()
  {
    return this.fTest.countTestCases();
  }
  
  public Test getTest()
  {
    return this.fTest;
  }
  
  public void run(TestResult paramTestResult)
  {
    basicRun(paramTestResult);
  }
  
  public String toString()
  {
    return this.fTest.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/extensions/TestDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */