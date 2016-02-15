package org.hamcrest.collection;

import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyCollection<E>
  extends TypeSafeMatcher<Collection<? extends E>>
{
  @Factory
  public static <E> Matcher<Collection<? extends E>> empty()
  {
    return new IsEmptyCollection();
  }
  
  @Factory
  public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> paramClass)
  {
    return empty();
  }
  
  public void describeMismatchSafely(Collection<? extends E> paramCollection, Description paramDescription)
  {
    paramDescription.appendValue(paramCollection);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("an empty collection");
  }
  
  public boolean matchesSafely(Collection<? extends E> paramCollection)
  {
    return paramCollection.isEmpty();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsEmptyCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */