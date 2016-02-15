package org.junit.internal.matchers;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class StacktracePrintingMatcher<T extends Throwable>
  extends TypeSafeMatcher<T>
{
  private final Matcher<T> fThrowableMatcher;
  
  public StacktracePrintingMatcher(Matcher<T> paramMatcher)
  {
    this.fThrowableMatcher = paramMatcher;
  }
  
  @Factory
  public static <T extends Exception> Matcher<T> isException(Matcher<T> paramMatcher)
  {
    return new StacktracePrintingMatcher(paramMatcher);
  }
  
  @Factory
  public static <T extends Throwable> Matcher<T> isThrowable(Matcher<T> paramMatcher)
  {
    return new StacktracePrintingMatcher(paramMatcher);
  }
  
  private String readStacktrace(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
    return localStringWriter.toString();
  }
  
  protected void describeMismatchSafely(T paramT, Description paramDescription)
  {
    this.fThrowableMatcher.describeMismatch(paramT, paramDescription);
    paramDescription.appendText("\nStacktrace was: ");
    paramDescription.appendText(readStacktrace(paramT));
  }
  
  public void describeTo(Description paramDescription)
  {
    this.fThrowableMatcher.describeTo(paramDescription);
  }
  
  protected boolean matchesSafely(T paramT)
  {
    return this.fThrowableMatcher.matches(paramT);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/matchers/StacktracePrintingMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */