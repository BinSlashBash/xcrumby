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

public class IsArrayContainingInAnyOrder<E>
  extends TypeSafeMatcher<E[]>
{
  private final IsIterableContainingInAnyOrder<E> iterableMatcher;
  private final Collection<Matcher<? super E>> matchers;
  
  public IsArrayContainingInAnyOrder(Collection<Matcher<? super E>> paramCollection)
  {
    this.iterableMatcher = new IsIterableContainingInAnyOrder(paramCollection);
    this.matchers = paramCollection;
  }
  
  @Factory
  public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> paramCollection)
  {
    return new IsArrayContainingInAnyOrder(paramCollection);
  }
  
  @Factory
  public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      localArrayList.add(IsEqual.equalTo(paramVarArgs[i]));
      i += 1;
    }
    return new IsArrayContainingInAnyOrder(localArrayList);
  }
  
  @Factory
  public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... paramVarArgs)
  {
    return arrayContainingInAnyOrder(Arrays.asList(paramVarArgs));
  }
  
  public void describeMismatchSafely(E[] paramArrayOfE, Description paramDescription)
  {
    this.iterableMatcher.describeMismatch(Arrays.asList(paramArrayOfE), paramDescription);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendList("[", ", ", "]", this.matchers).appendText(" in any order");
  }
  
  public boolean matchesSafely(E[] paramArrayOfE)
  {
    return this.iterableMatcher.matches(Arrays.asList(paramArrayOfE));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsArrayContainingInAnyOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */