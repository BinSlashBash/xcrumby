package org.junit.internal.requests;

import java.util.Comparator;
import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Sorter;

public class SortingRequest
  extends Request
{
  private final Comparator<Description> fComparator;
  private final Request fRequest;
  
  public SortingRequest(Request paramRequest, Comparator<Description> paramComparator)
  {
    this.fRequest = paramRequest;
    this.fComparator = paramComparator;
  }
  
  public Runner getRunner()
  {
    Runner localRunner = this.fRequest.getRunner();
    new Sorter(this.fComparator).apply(localRunner);
    return localRunner;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/requests/SortingRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */