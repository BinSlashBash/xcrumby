package org.junit.internal.requests;

import org.junit.internal.runners.ErrorReportingRunner;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;

public final class FilterRequest
  extends Request
{
  private final Filter fFilter;
  private final Request fRequest;
  
  public FilterRequest(Request paramRequest, Filter paramFilter)
  {
    this.fRequest = paramRequest;
    this.fFilter = paramFilter;
  }
  
  public Runner getRunner()
  {
    try
    {
      Runner localRunner = this.fRequest.getRunner();
      this.fFilter.apply(localRunner);
      return localRunner;
    }
    catch (NoTestsRemainException localNoTestsRemainException) {}
    return new ErrorReportingRunner(Filter.class, new Exception(String.format("No tests found matching %s from %s", tmp45_35)));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/requests/FilterRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */