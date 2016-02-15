package org.junit.matchers;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.core.CombinableMatcher.CombinableBothMatcher;
import org.hamcrest.core.CombinableMatcher.CombinableEitherMatcher;
import org.junit.internal.matchers.StacktracePrintingMatcher;

public class JUnitMatchers
{
  @Deprecated
  public static <T> CombinableMatcher.CombinableBothMatcher<T> both(Matcher<? super T> paramMatcher)
  {
    return CoreMatchers.both(paramMatcher);
  }
  
  @Deprecated
  public static Matcher<String> containsString(String paramString)
  {
    return CoreMatchers.containsString(paramString);
  }
  
  @Deprecated
  public static <T> CombinableMatcher.CombinableEitherMatcher<T> either(Matcher<? super T> paramMatcher)
  {
    return CoreMatchers.either(paramMatcher);
  }
  
  @Deprecated
  public static <T> Matcher<Iterable<T>> everyItem(Matcher<T> paramMatcher)
  {
    return CoreMatchers.everyItem(paramMatcher);
  }
  
  @Deprecated
  public static <T> Matcher<Iterable<? super T>> hasItem(T paramT)
  {
    return CoreMatchers.hasItem(paramT);
  }
  
  @Deprecated
  public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> paramMatcher)
  {
    return CoreMatchers.hasItem(paramMatcher);
  }
  
  @Deprecated
  public static <T> Matcher<Iterable<T>> hasItems(T... paramVarArgs)
  {
    return CoreMatchers.hasItems(paramVarArgs);
  }
  
  @Deprecated
  public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... paramVarArgs)
  {
    return CoreMatchers.hasItems(paramVarArgs);
  }
  
  public static <T extends Exception> Matcher<T> isException(Matcher<T> paramMatcher)
  {
    return StacktracePrintingMatcher.isException(paramMatcher);
  }
  
  public static <T extends Throwable> Matcher<T> isThrowable(Matcher<T> paramMatcher)
  {
    return StacktracePrintingMatcher.isThrowable(paramMatcher);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/matchers/JUnitMatchers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */