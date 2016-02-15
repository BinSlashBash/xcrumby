package org.junit.experimental.results;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class PrintableResult
{
  private Result result;
  
  public PrintableResult(List<Failure> paramList)
  {
    this(new FailureList(paramList).result());
  }
  
  private PrintableResult(Result paramResult)
  {
    this.result = paramResult;
  }
  
  public static PrintableResult testResult(Class<?> paramClass)
  {
    return testResult(Request.aClass(paramClass));
  }
  
  public static PrintableResult testResult(Request paramRequest)
  {
    return new PrintableResult(new JUnitCore().run(paramRequest));
  }
  
  public int failureCount()
  {
    return this.result.getFailures().size();
  }
  
  public String toString()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    new TextListener(new PrintStream(localByteArrayOutputStream)).testRunFinished(this.result);
    return localByteArrayOutputStream.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/results/PrintableResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */