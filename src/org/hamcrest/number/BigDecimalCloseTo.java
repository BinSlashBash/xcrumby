package org.hamcrest.number;

import java.math.BigDecimal;
import java.math.MathContext;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class BigDecimalCloseTo
  extends TypeSafeMatcher<BigDecimal>
{
  private final BigDecimal delta;
  private final BigDecimal value;
  
  public BigDecimalCloseTo(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    this.delta = paramBigDecimal2;
    this.value = paramBigDecimal1;
  }
  
  private BigDecimal actualDelta(BigDecimal paramBigDecimal)
  {
    return paramBigDecimal.subtract(this.value, MathContext.DECIMAL128).abs().subtract(this.delta, MathContext.DECIMAL128).stripTrailingZeros();
  }
  
  @Factory
  public static Matcher<BigDecimal> closeTo(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    return new BigDecimalCloseTo(paramBigDecimal1, paramBigDecimal2);
  }
  
  public void describeMismatchSafely(BigDecimal paramBigDecimal, Description paramDescription)
  {
    paramDescription.appendValue(paramBigDecimal).appendText(" differed by ").appendValue(actualDelta(paramBigDecimal));
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("a numeric value within ").appendValue(this.delta).appendText(" of ").appendValue(this.value);
  }
  
  public boolean matchesSafely(BigDecimal paramBigDecimal)
  {
    return actualDelta(paramBigDecimal).compareTo(BigDecimal.ZERO) <= 0;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/number/BigDecimalCloseTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */