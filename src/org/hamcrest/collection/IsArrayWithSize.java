package org.hamcrest.collection;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.IsEqual;

public class IsArrayWithSize<E>
  extends FeatureMatcher<E[], Integer>
{
  public IsArrayWithSize(Matcher<? super Integer> paramMatcher)
  {
    super(paramMatcher, "an array with size", "array size");
  }
  
  @Factory
  public static <E> Matcher<E[]> arrayWithSize(int paramInt)
  {
    return arrayWithSize(IsEqual.equalTo(Integer.valueOf(paramInt)));
  }
  
  @Factory
  public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> paramMatcher)
  {
    return new IsArrayWithSize(paramMatcher);
  }
  
  @Factory
  public static <E> Matcher<E[]> emptyArray()
  {
    return DescribedAs.describedAs("an empty array", arrayWithSize(0), new Object[0]);
  }
  
  protected Integer featureValueOf(E[] paramArrayOfE)
  {
    return Integer.valueOf(paramArrayOfE.length);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsArrayWithSize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */