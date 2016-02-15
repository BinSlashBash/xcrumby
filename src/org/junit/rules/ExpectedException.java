package org.junit.rules;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.matchers.ThrowableCauseMatcher;
import org.junit.internal.matchers.ThrowableMessageMatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ExpectedException
  implements TestRule
{
  private final ExpectedExceptionMatcherBuilder fMatcherBuilder = new ExpectedExceptionMatcherBuilder();
  private boolean handleAssertionErrors = false;
  private boolean handleAssumptionViolatedExceptions = false;
  
  private void failDueToMissingException()
    throws AssertionError
  {
    String str = StringDescription.toString(this.fMatcherBuilder.build());
    Assert.fail("Expected test to throw " + str);
  }
  
  private void handleException(Throwable paramThrowable)
    throws Throwable
  {
    if (this.fMatcherBuilder.expectsThrowable())
    {
      Assert.assertThat(paramThrowable, this.fMatcherBuilder.build());
      return;
    }
    throw paramThrowable;
  }
  
  public static ExpectedException none()
  {
    return new ExpectedException();
  }
  
  private void optionallyHandleException(Throwable paramThrowable, boolean paramBoolean)
    throws Throwable
  {
    if (paramBoolean)
    {
      handleException(paramThrowable);
      return;
    }
    throw paramThrowable;
  }
  
  public Statement apply(Statement paramStatement, Description paramDescription)
  {
    return new ExpectedExceptionStatement(paramStatement);
  }
  
  public void expect(Class<? extends Throwable> paramClass)
  {
    expect(CoreMatchers.instanceOf(paramClass));
  }
  
  public void expect(Matcher<?> paramMatcher)
  {
    this.fMatcherBuilder.add(paramMatcher);
  }
  
  public void expectCause(Matcher<? extends Throwable> paramMatcher)
  {
    expect(ThrowableCauseMatcher.hasCause(paramMatcher));
  }
  
  public void expectMessage(String paramString)
  {
    expectMessage(CoreMatchers.containsString(paramString));
  }
  
  public void expectMessage(Matcher<String> paramMatcher)
  {
    expect(ThrowableMessageMatcher.hasMessage(paramMatcher));
  }
  
  public ExpectedException handleAssertionErrors()
  {
    this.handleAssertionErrors = true;
    return this;
  }
  
  public ExpectedException handleAssumptionViolatedExceptions()
  {
    this.handleAssumptionViolatedExceptions = true;
    return this;
  }
  
  private class ExpectedExceptionStatement
    extends Statement
  {
    private final Statement fNext;
    
    public ExpectedExceptionStatement(Statement paramStatement)
    {
      this.fNext = paramStatement;
    }
    
    public void evaluate()
      throws Throwable
    {
      try
      {
        this.fNext.evaluate();
        if (ExpectedException.this.fMatcherBuilder.expectsThrowable()) {
          ExpectedException.this.failDueToMissingException();
        }
        return;
      }
      catch (AssumptionViolatedException localAssumptionViolatedException)
      {
        ExpectedException.this.optionallyHandleException(localAssumptionViolatedException, ExpectedException.this.handleAssumptionViolatedExceptions);
        return;
      }
      catch (AssertionError localAssertionError)
      {
        ExpectedException.this.optionallyHandleException(localAssertionError, ExpectedException.this.handleAssertionErrors);
        return;
      }
      catch (Throwable localThrowable)
      {
        ExpectedException.this.handleException(localThrowable);
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/ExpectedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */