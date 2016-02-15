package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class OrderingComparison<T extends Comparable<T>>
  extends TypeSafeMatcher<T>
{
  private static final int EQUAL = 0;
  private static final int GREATER_THAN = 1;
  private static final int LESS_THAN = -1;
  private static final String[] comparisonDescriptions = { "less than", "equal to", "greater than" };
  private final T expected;
  private final int maxCompare;
  private final int minCompare;
  
  private OrderingComparison(T paramT, int paramInt1, int paramInt2)
  {
    this.expected = paramT;
    this.minCompare = paramInt1;
    this.maxCompare = paramInt2;
  }
  
  private static String asText(int paramInt)
  {
    return comparisonDescriptions[(Integer.signum(paramInt) + 1)];
  }
  
  @Factory
  public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T paramT)
  {
    return new OrderingComparison(paramT, 0, 0);
  }
  
  @Factory
  public static <T extends Comparable<T>> Matcher<T> greaterThan(T paramT)
  {
    return new OrderingComparison(paramT, 1, 1);
  }
  
  @Factory
  public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T paramT)
  {
    return new OrderingComparison(paramT, 0, 1);
  }
  
  @Factory
  public static <T extends Comparable<T>> Matcher<T> lessThan(T paramT)
  {
    return new OrderingComparison(paramT, -1, -1);
  }
  
  @Factory
  public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T paramT)
  {
    return new OrderingComparison(paramT, -1, 0);
  }
  
  public void describeMismatchSafely(T paramT, Description paramDescription)
  {
    paramDescription.appendValue(paramT).appendText(" was ").appendText(asText(paramT.compareTo(this.expected))).appendText(" ").appendValue(this.expected);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("a value ").appendText(asText(this.minCompare));
    if (this.minCompare != this.maxCompare) {
      paramDescription.appendText(" or ").appendText(asText(this.maxCompare));
    }
    paramDescription.appendText(" ").appendValue(this.expected);
  }
  
  public boolean matchesSafely(T paramT)
  {
    int i = Integer.signum(paramT.compareTo(this.expected));
    return (this.minCompare <= i) && (i <= this.maxCompare);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/number/OrderingComparison.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */