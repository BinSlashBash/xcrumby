package org.junit.internal.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ThrowableCauseMatcher<T extends Throwable>
  extends TypeSafeMatcher<T>
{
  private final Matcher<T> fMatcher;
  
  public ThrowableCauseMatcher(Matcher<T> paramMatcher)
  {
    this.fMatcher = paramMatcher;
  }
  
  @Factory
  public static <T extends Throwable> Matcher<T> hasCause(Matcher<T> paramMatcher)
  {
    return new ThrowableCauseMatcher(paramMatcher);
  }
  
  protected void describeMismatchSafely(T paramT, Description paramDescription)
  {
    paramDescription.appendText("cause ");
    this.fMatcher.describeMismatch(paramT.getCause(), paramDescription);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("exception with cause ");
    paramDescription.appendDescriptionOf(this.fMatcher);
  }
  
  protected boolean matchesSafely(T paramT)
  {
    return this.fMatcher.matches(paramT.getCause());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/matchers/ThrowableCauseMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */