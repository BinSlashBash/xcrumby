package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCloseTo
  extends TypeSafeMatcher<Double>
{
  private final double delta;
  private final double value;
  
  public IsCloseTo(double paramDouble1, double paramDouble2)
  {
    this.delta = paramDouble2;
    this.value = paramDouble1;
  }
  
  private double actualDelta(Double paramDouble)
  {
    return Math.abs(paramDouble.doubleValue() - this.value) - this.delta;
  }
  
  @Factory
  public static Matcher<Double> closeTo(double paramDouble1, double paramDouble2)
  {
    return new IsCloseTo(paramDouble1, paramDouble2);
  }
  
  public void describeMismatchSafely(Double paramDouble, Description paramDescription)
  {
    paramDescription.appendValue(paramDouble).appendText(" differed by ").appendValue(Double.valueOf(actualDelta(paramDouble)));
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("a numeric value within ").appendValue(Double.valueOf(this.delta)).appendText(" of ").appendValue(Double.valueOf(this.value));
  }
  
  public boolean matchesSafely(Double paramDouble)
  {
    return actualDelta(paramDouble) <= 0.0D;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/number/IsCloseTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */