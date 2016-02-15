package junit.framework;

import org.junit.runner.Describable;
import org.junit.runner.Description;

public class JUnit4TestCaseFacade
  implements Test, Describable
{
  private final Description fDescription;
  
  JUnit4TestCaseFacade(Description paramDescription)
  {
    this.fDescription = paramDescription;
  }
  
  public int countTestCases()
  {
    return 1;
  }
  
  public Description getDescription()
  {
    return this.fDescription;
  }
  
  public void run(TestResult paramTestResult)
  {
    throw new RuntimeException("This test stub created only for informational purposes.");
  }
  
  public String toString()
  {
    return getDescription().toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/framework/JUnit4TestCaseFacade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */