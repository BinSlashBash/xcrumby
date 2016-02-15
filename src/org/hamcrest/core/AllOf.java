package org.hamcrest.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class AllOf<T>
  extends DiagnosingMatcher<T>
{
  private final Iterable<Matcher<? super T>> matchers;
  
  public AllOf(Iterable<Matcher<? super T>> paramIterable)
  {
    this.matchers = paramIterable;
  }
  
  @Factory
  public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> paramIterable)
  {
    return new AllOf(paramIterable);
  }
  
  @Factory
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2)
  {
    ArrayList localArrayList = new ArrayList(2);
    localArrayList.add(paramMatcher1);
    localArrayList.add(paramMatcher2);
    return allOf(localArrayList);
  }
  
  @Factory
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3)
  {
    ArrayList localArrayList = new ArrayList(3);
    localArrayList.add(paramMatcher1);
    localArrayList.add(paramMatcher2);
    localArrayList.add(paramMatcher3);
    return allOf(localArrayList);
  }
  
  @Factory
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3, Matcher<? super T> paramMatcher4)
  {
    ArrayList localArrayList = new ArrayList(4);
    localArrayList.add(paramMatcher1);
    localArrayList.add(paramMatcher2);
    localArrayList.add(paramMatcher3);
    localArrayList.add(paramMatcher4);
    return allOf(localArrayList);
  }
  
  @Factory
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3, Matcher<? super T> paramMatcher4, Matcher<? super T> paramMatcher5)
  {
    ArrayList localArrayList = new ArrayList(5);
    localArrayList.add(paramMatcher1);
    localArrayList.add(paramMatcher2);
    localArrayList.add(paramMatcher3);
    localArrayList.add(paramMatcher4);
    localArrayList.add(paramMatcher5);
    return allOf(localArrayList);
  }
  
  @Factory
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3, Matcher<? super T> paramMatcher4, Matcher<? super T> paramMatcher5, Matcher<? super T> paramMatcher6)
  {
    ArrayList localArrayList = new ArrayList(6);
    localArrayList.add(paramMatcher1);
    localArrayList.add(paramMatcher2);
    localArrayList.add(paramMatcher3);
    localArrayList.add(paramMatcher4);
    localArrayList.add(paramMatcher5);
    localArrayList.add(paramMatcher6);
    return allOf(localArrayList);
  }
  
  @Factory
  public static <T> Matcher<T> allOf(Matcher<? super T>... paramVarArgs)
  {
    return allOf(Arrays.asList(paramVarArgs));
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendList("(", " and ", ")", this.matchers);
  }
  
  public boolean matches(Object paramObject, Description paramDescription)
  {
    Iterator localIterator = this.matchers.iterator();
    while (localIterator.hasNext())
    {
      Matcher localMatcher = (Matcher)localIterator.next();
      if (!localMatcher.matches(paramObject))
      {
        paramDescription.appendDescriptionOf(localMatcher).appendText(" ");
        localMatcher.describeMismatch(paramObject, paramDescription);
        return false;
      }
    }
    return true;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/AllOf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */