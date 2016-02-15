package org.junit.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayComparisonFailure
  extends AssertionError
{
  private static final long serialVersionUID = 1L;
  private final AssertionError fCause;
  private List<Integer> fIndices = new ArrayList();
  private final String fMessage;
  
  public ArrayComparisonFailure(String paramString, AssertionError paramAssertionError, int paramInt)
  {
    this.fMessage = paramString;
    this.fCause = paramAssertionError;
    addDimension(paramInt);
  }
  
  public void addDimension(int paramInt)
  {
    this.fIndices.add(0, Integer.valueOf(paramInt));
  }
  
  public String getMessage()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.fMessage != null) {
      localStringBuilder.append(this.fMessage);
    }
    localStringBuilder.append("arrays first differed at element ");
    Iterator localIterator = this.fIndices.iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      localStringBuilder.append("[");
      localStringBuilder.append(i);
      localStringBuilder.append("]");
    }
    localStringBuilder.append("; ");
    localStringBuilder.append(this.fCause.getMessage());
    return localStringBuilder.toString();
  }
  
  public String toString()
  {
    return getMessage();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/ArrayComparisonFailure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */