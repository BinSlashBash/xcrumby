package org.junit.rules;

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.matchers.JUnitMatchers;

class ExpectedExceptionMatcherBuilder
{
  private final List<Matcher<?>> fMatchers = new ArrayList();
  
  private Matcher<Throwable> allOfTheMatchers()
  {
    if (this.fMatchers.size() == 1) {
      return cast((Matcher)this.fMatchers.get(0));
    }
    return CoreMatchers.allOf(castedMatchers());
  }
  
  private Matcher<Throwable> cast(Matcher<?> paramMatcher)
  {
    return paramMatcher;
  }
  
  private List<Matcher<? super Throwable>> castedMatchers()
  {
    return new ArrayList(this.fMatchers);
  }
  
  void add(Matcher<?> paramMatcher)
  {
    this.fMatchers.add(paramMatcher);
  }
  
  Matcher<Throwable> build()
  {
    return JUnitMatchers.isThrowable(allOfTheMatchers());
  }
  
  boolean expectsThrowable()
  {
    return !this.fMatchers.isEmpty();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/ExpectedExceptionMatcherBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */