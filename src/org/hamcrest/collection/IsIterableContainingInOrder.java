package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class IsIterableContainingInOrder<E>
  extends TypeSafeDiagnosingMatcher<Iterable<? extends E>>
{
  private final List<Matcher<? super E>> matchers;
  
  public IsIterableContainingInOrder(List<Matcher<? super E>> paramList)
  {
    this.matchers = paramList;
  }
  
  @Factory
  public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> paramList)
  {
    return new IsIterableContainingInOrder(paramList);
  }
  
  @Factory
  public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> paramMatcher)
  {
    return contains(new ArrayList(Arrays.asList(new Matcher[] { paramMatcher })));
  }
  
  @Factory
  public static <E> Matcher<Iterable<? extends E>> contains(E... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      localArrayList.add(IsEqual.equalTo(paramVarArgs[i]));
      i += 1;
    }
    return contains(localArrayList);
  }
  
  @Factory
  public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... paramVarArgs)
  {
    return contains(Arrays.asList(paramVarArgs));
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("iterable containing ").appendList("[", ", ", "]", this.matchers);
  }
  
  protected boolean matchesSafely(Iterable<? extends E> paramIterable, Description paramDescription)
  {
    paramDescription = new MatchSeries(this.matchers, paramDescription);
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      if (!paramDescription.matches(paramIterable.next())) {
        return false;
      }
    }
    return paramDescription.isFinished();
  }
  
  private static class MatchSeries<F>
  {
    public final List<Matcher<? super F>> matchers;
    private final Description mismatchDescription;
    public int nextMatchIx = 0;
    
    public MatchSeries(List<Matcher<? super F>> paramList, Description paramDescription)
    {
      this.mismatchDescription = paramDescription;
      if (paramList.isEmpty()) {
        throw new IllegalArgumentException("Should specify at least one expected element");
      }
      this.matchers = paramList;
    }
    
    private void describeMismatch(Matcher<? super F> paramMatcher, F paramF)
    {
      this.mismatchDescription.appendText("item " + this.nextMatchIx + ": ");
      paramMatcher.describeMismatch(paramF, this.mismatchDescription);
    }
    
    private boolean isMatched(F paramF)
    {
      Matcher localMatcher = (Matcher)this.matchers.get(this.nextMatchIx);
      if (!localMatcher.matches(paramF))
      {
        describeMismatch(localMatcher, paramF);
        return false;
      }
      this.nextMatchIx += 1;
      return true;
    }
    
    private boolean isNotSurplus(F paramF)
    {
      if (this.matchers.size() <= this.nextMatchIx)
      {
        this.mismatchDescription.appendText("Not matched: ").appendValue(paramF);
        return false;
      }
      return true;
    }
    
    public boolean isFinished()
    {
      if (this.nextMatchIx < this.matchers.size())
      {
        this.mismatchDescription.appendText("No item matched: ").appendDescriptionOf((SelfDescribing)this.matchers.get(this.nextMatchIx));
        return false;
      }
      return true;
    }
    
    public boolean matches(F paramF)
    {
      return (isNotSurplus(paramF)) && (isMatched(paramF));
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsIterableContainingInOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */