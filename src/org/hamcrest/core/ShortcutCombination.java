package org.hamcrest.core;

import java.util.Iterator;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

abstract class ShortcutCombination<T>
  extends BaseMatcher<T>
{
  private final Iterable<Matcher<? super T>> matchers;
  
  public ShortcutCombination(Iterable<Matcher<? super T>> paramIterable)
  {
    this.matchers = paramIterable;
  }
  
  public abstract void describeTo(Description paramDescription);
  
  public void describeTo(Description paramDescription, String paramString)
  {
    paramDescription.appendList("(", " " + paramString + " ", ")", this.matchers);
  }
  
  public abstract boolean matches(Object paramObject);
  
  protected boolean matches(Object paramObject, boolean paramBoolean)
  {
    Iterator localIterator = this.matchers.iterator();
    while (localIterator.hasNext()) {
      if (((Matcher)localIterator.next()).matches(paramObject) == paramBoolean) {
        return paramBoolean;
      }
    }
    if (!paramBoolean) {}
    for (paramBoolean = true;; paramBoolean = false) {
      return paramBoolean;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/core/ShortcutCombination.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */