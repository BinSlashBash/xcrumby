package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestFailure
{
  protected Test fFailedTest;
  protected Throwable fThrownException;
  
  public TestFailure(Test paramTest, Throwable paramThrowable)
  {
    this.fFailedTest = paramTest;
    this.fThrownException = paramThrowable;
  }
  
  public String exceptionMessage()
  {
    return thrownException().getMessage();
  }
  
  public Test failedTest()
  {
    return this.fFailedTest;
  }
  
  public boolean isFailure()
  {
    return thrownException() instanceof AssertionFailedError;
  }
  
  public Throwable thrownException()
  {
    return this.fThrownException;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(this.fFailedTest + ": " + this.fThrownException.getMessage());
    return localStringBuffer.toString();
  }
  
  public String trace()
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    thrownException().printStackTrace(localPrintWriter);
    return localStringWriter.getBuffer().toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/framework/TestFailure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */