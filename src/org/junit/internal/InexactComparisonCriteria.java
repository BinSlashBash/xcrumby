package org.junit.internal;

import org.junit.Assert;

public class InexactComparisonCriteria
  extends ComparisonCriteria
{
  public Object fDelta;
  
  public InexactComparisonCriteria(double paramDouble)
  {
    this.fDelta = Double.valueOf(paramDouble);
  }
  
  public InexactComparisonCriteria(float paramFloat)
  {
    this.fDelta = Float.valueOf(paramFloat);
  }
  
  protected void assertElementsEqual(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 instanceof Double))
    {
      Assert.assertEquals(((Double)paramObject1).doubleValue(), ((Double)paramObject2).doubleValue(), ((Double)this.fDelta).doubleValue());
      return;
    }
    Assert.assertEquals(((Float)paramObject1).floatValue(), ((Float)paramObject2).floatValue(), ((Float)this.fDelta).floatValue());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/InexactComparisonCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */