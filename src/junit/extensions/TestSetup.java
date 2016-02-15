package junit.extensions;

import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestResult;

public class TestSetup
  extends TestDecorator
{
  public TestSetup(Test paramTest)
  {
    super(paramTest);
  }
  
  public void run(final TestResult paramTestResult)
  {
    paramTestResult.runProtected(this, new Protectable()
    {
      public void protect()
        throws Exception
      {
        TestSetup.this.setUp();
        TestSetup.this.basicRun(paramTestResult);
        TestSetup.this.tearDown();
      }
    });
  }
  
  protected void setUp()
    throws Exception
  {}
  
  protected void tearDown()
    throws Exception
  {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/extensions/TestSetup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */