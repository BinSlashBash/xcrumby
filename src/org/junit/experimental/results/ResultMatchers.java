package org.junit.experimental.results;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ResultMatchers
{
  public static Matcher<PrintableResult> failureCountIs(int paramInt)
  {
    new TypeSafeMatcher()
    {
      public void describeTo(Description paramAnonymousDescription)
      {
        paramAnonymousDescription.appendText("has " + this.val$count + " failures");
      }
      
      public boolean matchesSafely(PrintableResult paramAnonymousPrintableResult)
      {
        return paramAnonymousPrintableResult.failureCount() == this.val$count;
      }
    };
  }
  
  public static Matcher<PrintableResult> hasFailureContaining(String paramString)
  {
    new BaseMatcher()
    {
      public void describeTo(Description paramAnonymousDescription)
      {
        paramAnonymousDescription.appendText("has failure containing " + this.val$string);
      }
      
      public boolean matches(Object paramAnonymousObject)
      {
        return paramAnonymousObject.toString().contains(this.val$string);
      }
    };
  }
  
  public static Matcher<Object> hasSingleFailureContaining(String paramString)
  {
    new BaseMatcher()
    {
      public void describeTo(Description paramAnonymousDescription)
      {
        paramAnonymousDescription.appendText("has single failure containing " + this.val$string);
      }
      
      public boolean matches(Object paramAnonymousObject)
      {
        return (paramAnonymousObject.toString().contains(this.val$string)) && (ResultMatchers.failureCountIs(1).matches(paramAnonymousObject));
      }
    };
  }
  
  public static Matcher<PrintableResult> isSuccessful()
  {
    return failureCountIs(0);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/results/ResultMatchers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */