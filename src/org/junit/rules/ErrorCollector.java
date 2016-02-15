package org.junit.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.runners.model.MultipleFailureException;

public class ErrorCollector
  extends Verifier
{
  private List<Throwable> errors = new ArrayList();
  
  public void addError(Throwable paramThrowable)
  {
    this.errors.add(paramThrowable);
  }
  
  public Object checkSucceeds(Callable<Object> paramCallable)
  {
    try
    {
      paramCallable = paramCallable.call();
      return paramCallable;
    }
    catch (Throwable paramCallable)
    {
      addError(paramCallable);
    }
    return null;
  }
  
  public <T> void checkThat(T paramT, Matcher<T> paramMatcher)
  {
    checkThat("", paramT, paramMatcher);
  }
  
  public <T> void checkThat(final String paramString, final T paramT, final Matcher<T> paramMatcher)
  {
    checkSucceeds(new Callable()
    {
      public Object call()
        throws Exception
      {
        Assert.assertThat(paramString, paramT, paramMatcher);
        return paramT;
      }
    });
  }
  
  protected void verify()
    throws Throwable
  {
    MultipleFailureException.assertEmpty(this.errors);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/ErrorCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */