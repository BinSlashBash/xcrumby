package org.hamcrest.core;

import java.util.ArrayList;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class CombinableMatcher<T>
  extends TypeSafeDiagnosingMatcher<T>
{
  private final Matcher<? super T> matcher;
  
  public CombinableMatcher(Matcher<? super T> paramMatcher)
  {
    this.matcher = paramMatcher;
  }
  
  @Factory
  public static <LHS> CombinableBothMatcher<LHS> both(Matcher<? super LHS> paramMatcher)
  {
    return new CombinableBothMatcher(paramMatcher);
  }
  
  @Factory
  public static <LHS> CombinableEitherMatcher<LHS> either(Matcher<? super LHS> paramMatcher)
  {
    return new CombinableEitherMatcher(paramMatcher);
  }
  
  private ArrayList<Matcher<? super T>> templatedListWith(Matcher<? super T> paramMatcher)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this.matcher);
    localArrayList.add(paramMatcher);
    return localArrayList;
  }
  
  public CombinableMatcher<T> and(Matcher<? super T> paramMatcher)
  {
    return new CombinableMatcher(new AllOf(templatedListWith(paramMatcher)));
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendDescriptionOf(this.matcher);
  }
  
  protected boolean matchesSafely(T paramT, Description paramDescription)
  {
    if (!this.matcher.matches(paramT))
    {
      this.matcher.describeMismatch(paramT, paramDescription);
      return false;
    }
    return true;
  }
  
  public CombinableMatcher<T> or(Matcher<? super T> paramMatcher)
  {
    return new CombinableMatcher(new AnyOf(templatedListWith(paramMatcher)));
  }
  
  public static final class CombinableBothMatcher<X>
  {
    private final Matcher<? super X> first;
    
    public CombinableBothMatcher(Matcher<? super X> paramMatcher)
    {
      this.first = paramMatcher;
    }
    
    public CombinableMatcher<X> and(Matcher<? super X> paramMatcher)
    {
      return new CombinableMatcher(this.first).and(paramMatcher);
    }
  }
  
  public static final class CombinableEitherMatcher<X>
  {
    private final Matcher<? super X> first;
    
    public CombinableEitherMatcher(Matcher<? super X> paramMatcher)
    {
      this.first = paramMatcher;
    }
    
    public CombinableMatcher<X> or(Matcher<? super X> paramMatcher)
    {
      return new CombinableMatcher(this.first).or(paramMatcher);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/CombinableMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */