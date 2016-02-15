package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

public class IsArrayContainingInOrder<E>
  extends TypeSafeMatcher<E[]>
{
  private final IsIterableContainingInOrder<E> iterableMatcher;
  private final Collection<Matcher<? super E>> matchers;
  
  public IsArrayContainingInOrder(List<Matcher<? super E>> paramList)
  {
    this.iterableMatcher = new IsIterableContainingInOrder(paramList);
    this.matchers = paramList;
  }
  
  @Factory
  public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> paramList)
  {
    return new IsArrayContainingInOrder(paramList);
  }
  
  @Factory
  public static <E> Matcher<E[]> arrayContaining(E... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      localArrayList.add(IsEqual.equalTo(paramVarArgs[i]));
      i += 1;
    }
    return arrayContaining(localArrayList);
  }
  
  @Factory
  public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... paramVarArgs)
  {
    return arrayContaining(Arrays.asList(paramVarArgs));
  }
  
  public void describeMismatchSafely(E[] paramArrayOfE, Description paramDescription)
  {
    this.iterableMatcher.describeMismatch(Arrays.asList(paramArrayOfE), paramDescription);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendList("[", ", ", "]", this.matchers);
  }
  
  public boolean matchesSafely(E[] paramArrayOfE)
  {
    return this.iterableMatcher.matches(Arrays.asList(paramArrayOfE));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsArrayContainingInOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */