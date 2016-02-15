package org.hamcrest.collection;

import java.util.Collection;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class IsCollectionWithSize<E>
  extends FeatureMatcher<Collection<? extends E>, Integer>
{
  public IsCollectionWithSize(Matcher<? super Integer> paramMatcher)
  {
    super(paramMatcher, "a collection with size", "collection size");
  }
  
  @Factory
  public static <E> Matcher<Collection<? extends E>> hasSize(int paramInt)
  {
    return hasSize(IsEqual.equalTo(Integer.valueOf(paramInt)));
  }
  
  @Factory
  public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> paramMatcher)
  {
    return new IsCollectionWithSize(paramMatcher);
  }
  
  protected Integer featureValueOf(Collection<? extends E> paramCollection)
  {
    return Integer.valueOf(paramCollection.size());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsCollectionWithSize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */