package org.junit.runners.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MultipleFailureException
  extends Exception
{
  private static final long serialVersionUID = 1L;
  private final List<Throwable> fErrors;
  
  public MultipleFailureException(List<Throwable> paramList)
  {
    this.fErrors = new ArrayList(paramList);
  }
  
  public static void assertEmpty(List<Throwable> paramList)
    throws Throwable
  {
    if (paramList.isEmpty()) {
      return;
    }
    if (paramList.size() == 1) {
      throw ((Throwable)paramList.get(0));
    }
    throw new org.junit.internal.runners.model.MultipleFailureException(paramList);
  }
  
  public List<Throwable> getFailures()
  {
    return Collections.unmodifiableList(this.fErrors);
  }
  
  public String getMessage()
  {
    StringBuilder localStringBuilder = new StringBuilder(String.format("There were %d errors:", new Object[] { Integer.valueOf(this.fErrors.size()) }));
    Iterator localIterator = this.fErrors.iterator();
    while (localIterator.hasNext())
    {
      Throwable localThrowable = (Throwable)localIterator.next();
      localStringBuilder.append(String.format("\n  %s(%s)", new Object[] { localThrowable.getClass().getName(), localThrowable.getMessage() }));
    }
    return localStringBuilder.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/model/MultipleFailureException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */