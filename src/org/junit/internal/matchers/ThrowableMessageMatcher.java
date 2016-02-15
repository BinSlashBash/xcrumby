package org.junit.internal.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ThrowableMessageMatcher<T extends Throwable>
  extends TypeSafeMatcher<T>
{
  private final Matcher<String> fMatcher;
  
  public ThrowableMessageMatcher(Matcher<String> paramMatcher)
  {
    this.fMatcher = paramMatcher;
  }
  
  @Factory
  public static <T extends Throwable> Matcher<T> hasMessage(Matcher<String> paramMatcher)
  {
    return new ThrowableMessageMatcher(paramMatcher);
  }
  
  protected void describeMismatchSafely(T paramT, Description paramDescription)
  {
    paramDescription.appendText("message ");
    this.fMatcher.describeMismatch(paramT.getMessage(), paramDescription);
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("exception with message ");
    paramDescription.appendDescriptionOf(this.fMatcher);
  }
  
  protected boolean matchesSafely(T paramT)
  {
    return this.fMatcher.matches(paramT.getMessage());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/matchers/ThrowableMessageMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */