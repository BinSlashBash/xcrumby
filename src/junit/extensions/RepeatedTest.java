package junit.extensions;

import junit.framework.Test;
import junit.framework.TestResult;

public class RepeatedTest
  extends TestDecorator
{
  private int fTimesRepeat;
  
  public RepeatedTest(Test paramTest, int paramInt)
  {
    super(paramTest);
    if (paramInt < 0) {
      throw new IllegalArgumentException("Repetition count must be >= 0");
    }
    this.fTimesRepeat = paramInt;
  }
  
  public int countTestCases()
  {
    return super.countTestCases() * this.fTimesRepeat;
  }
  
  public void run(TestResult paramTestResult)
  {
    int i = 0;
    for (;;)
    {
      if ((i >= this.fTimesRepeat) || (paramTestResult.shouldStop())) {
        return;
      }
      super.run(paramTestResult);
      i += 1;
    }
  }
  
  public String toString()
  {
    return super.toString() + "(repeated)";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/extensions/RepeatedTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */