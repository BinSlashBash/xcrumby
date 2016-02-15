package org.hamcrest.collection;

import java.util.Iterator;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class IsIterableWithSize<E>
  extends FeatureMatcher<Iterable<E>, Integer>
{
  public IsIterableWithSize(Matcher<? super Integer> paramMatcher)
  {
    super(paramMatcher, "an iterable with size", "iterable size");
  }
  
  @Factory
  public static <E> Matcher<Iterable<E>> iterableWithSize(int paramInt)
  {
    return iterableWithSize(IsEqual.equalTo(Integer.valueOf(paramInt)));
  }
  
  @Factory
  public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> paramMatcher)
  {
    return new IsIterableWithSize(paramMatcher);
  }
  
  protected Integer featureValueOf(Iterable<E> paramIterable)
  {
    int i = 0;
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext())
    {
      i += 1;
      paramIterable.next();
    }
    return Integer.valueOf(i);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsIterableWithSize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */