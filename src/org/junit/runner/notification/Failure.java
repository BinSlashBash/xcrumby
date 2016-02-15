package org.junit.runner.notification;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import org.junit.runner.Description;

public class Failure
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private final Description fDescription;
  private final Throwable fThrownException;
  
  public Failure(Description paramDescription, Throwable paramThrowable)
  {
    this.fThrownException = paramThrowable;
    this.fDescription = paramDescription;
  }
  
  public Description getDescription()
  {
    return this.fDescription;
  }
  
  public Throwable getException()
  {
    return this.fThrownException;
  }
  
  public String getMessage()
  {
    return getException().getMessage();
  }
  
  public String getTestHeader()
  {
    return this.fDescription.getDisplayName();
  }
  
  public String getTrace()
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    getException().printStackTrace(localPrintWriter);
    return localStringWriter.getBuffer().toString();
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(getTestHeader() + ": " + this.fThrownException.getMessage());
    return localStringBuffer.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runner/notification/Failure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */