package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class IsIterableContainingInAnyOrder<T>
  extends TypeSafeDiagnosingMatcher<Iterable<? extends T>>
{
  private final Collection<Matcher<? super T>> matchers;
  
  public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> paramCollection)
  {
    this.matchers = paramCollection;
  }
  
  @Factory
  public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> paramCollection)
  {
    return new IsIterableContainingInAnyOrder(paramCollection);
  }
  
  @Deprecated
  @Factory
  public static <E> Matcher<Iterable<? extends E>> containsInAnyOrder(Matcher<? super E> paramMatcher)
  {
    return containsInAnyOrder(new ArrayList(Arrays.asList(new Matcher[] { paramMatcher })));
  }
  
  @Factory
  public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      localArrayList.add(IsEqual.equalTo(paramVarArgs[i]));
      i += 1;
    }
    return new IsIterableContainingInAnyOrder(localArrayList);
  }
  
  @Factory
  public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... paramVarArgs)
  {
    return containsInAnyOrder(Arrays.asList(paramVarArgs));
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("iterable over ").appendList("[", ", ", "]", this.matchers).appendText(" in any order");
  }
  
  protected boolean matchesSafely(Iterable<? extends T> paramIterable, Description paramDescription)
  {
    paramDescription = new Matching(this.matchers, paramDescription);
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext()) {
      if (!paramDescription.matches(localIterator.next())) {
        return false;
      }
    }
    return paramDescription.isFinished(paramIterable);
  }
  
  private static class Matching<S>
  {
    private final Collection<Matcher<? super S>> matchers;
    private final Description mismatchDescription;
    
    public Matching(Collection<Matcher<? super S>> paramCollection, Description paramDescription)
    {
      this.matchers = new ArrayList(paramCollection);
      this.mismatchDescription = paramDescription;
    }
    
    private boolean isMatched(S paramS)
    {
      Iterator localIterator = this.matchers.iterator();
      while (localIterator.hasNext())
      {
        Matcher localMatcher = (Matcher)localIterator.next();
        if (localMatcher.matches(paramS))
        {
          this.matchers.remove(localMatcher);
          return true;
        }
      }
      this.mismatchDescription.appendText("Not matched: ").appendValue(paramS);
      return false;
    }
    
    private boolean isNotSurplus(S paramS)
    {
      if (this.matchers.isEmpty())
      {
        this.mismatchDescription.appendText("Not matched: ").appendValue(paramS);
        return false;
      }
      return true;
    }
    
    public boolean isFinished(Iterable<? extends S> paramIterable)
    {
      if (this.matchers.isEmpty()) {
        return true;
      }
      this.mismatchDescription.appendText("No item matches: ").appendList("", ", ", "", this.matchers).appendText(" in ").appendValueList("[", ", ", "]", paramIterable);
      return false;
    }
    
    public boolean matches(S paramS)
    {
      return (isNotSurplus(paramS)) && (isMatched(paramS));
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsIterableContainingInAnyOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */