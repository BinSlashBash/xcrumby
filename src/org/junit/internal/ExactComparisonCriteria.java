package org.junit.internal;

import org.junit.Assert;

public class ExactComparisonCriteria
  extends ComparisonCriteria
{
  protected void assertElementsEqual(Object paramObject1, Object paramObject2)
  {
    Assert.assertEquals(paramObject1, paramObject2);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/ExactComparisonCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */