package org.hamcrest;

public abstract class Condition<T>
{
  public static final NotMatched<Object> NOT_MATCHED = new NotMatched(null);
  
  public static <T> Condition<T> matched(T paramT, Description paramDescription)
  {
    return new Matched(paramT, paramDescription, null);
  }
  
  public static <T> Condition<T> notMatched()
  {
    return NOT_MATCHED;
  }
  
  public abstract <U> Condition<U> and(Step<? super T, U> paramStep);
  
  public final boolean matching(Matcher<T> paramMatcher)
  {
    return matching(paramMatcher, "");
  }
  
  public abstract boolean matching(Matcher<T> paramMatcher, String paramString);
  
  public final <U> Condition<U> then(Step<? super T, U> paramStep)
  {
    return and(paramStep);
  }
  
  private static final class Matched<T>
    extends Condition<T>
  {
    private final Description mismatch;
    private final T theValue;
    
    private Matched(T paramT, Description paramDescription)
    {
      super();
      this.theValue = paramT;
      this.mismatch = paramDescription;
    }
    
    public <U> Condition<U> and(Condition.Step<? super T, U> paramStep)
    {
      return paramStep.apply(this.theValue, this.mismatch);
    }
    
    public boolean matching(Matcher<T> paramMatcher, String paramString)
    {
      if (paramMatcher.matches(this.theValue)) {
        return true;
      }
      this.mismatch.appendText(paramString);
      paramMatcher.describeMismatch(this.theValue, this.mismatch);
      return false;
    }
  }
  
  private static final class NotMatched<T>
    extends Condition<T>
  {
    private NotMatched()
    {
      super();
    }
    
    public <U> Condition<U> and(Condition.Step<? super T, U> paramStep)
    {
      return notMatched();
    }
    
    public boolean matching(Matcher<T> paramMatcher, String paramString)
    {
      return false;
    }
  }
  
  public static abstract interface Step<I, O>
  {
    public abstract Condition<O> apply(I paramI, Description paramDescription);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/Condition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */