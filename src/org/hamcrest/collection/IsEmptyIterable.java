package org.hamcrest.collection;

import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyIterable<E>
  extends TypeSafeMatcher<Iterable<? extends E>>
{
  @Factory
  public static <E> Matcher<Iterable<? extends E>> emptyIterable()
  {
    return new IsEmptyIterable();
  }
  
  @Factory
  public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> paramClass)
  {
    return emptyIterable();
  }
  
  public void describeMismatchSafely(Iterable<? extends E> paramIterable, Description paramDescription)
  {
    paramDescription.appendValueList("[", ",", "]", paramIterable);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("an empty iterable");
  }
  
  public boolean matchesSafely(Iterable<? extends E> paramIterable)
  {
    return !paramIterable.iterator().hasNext();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsEmptyIterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */