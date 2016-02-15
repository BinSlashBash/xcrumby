package org.hamcrest.object;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class HasToString<T>
  extends FeatureMatcher<T, String>
{
  public HasToString(Matcher<? super String> paramMatcher)
  {
    super(paramMatcher, "with toString()", "toString()");
  }
  
  @Factory
  public static <T> Matcher<T> hasToString(String paramString)
  {
    return new HasToString(IsEqual.equalTo(paramString));
  }
  
  @Factory
  public static <T> Matcher<T> hasToString(Matcher<? super String> paramMatcher)
  {
    return new HasToString(paramMatcher);
  }
  
  protected String featureValueOf(T paramT)
  {
    return String.valueOf(paramT);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/object/HasToString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */