/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Assert;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.matchers.ThrowableCauseMatcher;
import org.junit.internal.matchers.ThrowableMessageMatcher;
import org.junit.rules.ExpectedExceptionMatcherBuilder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ExpectedException
implements TestRule {
    private final ExpectedExceptionMatcherBuilder fMatcherBuilder = new ExpectedExceptionMatcherBuilder();
    private boolean handleAssertionErrors = false;
    private boolean handleAssumptionViolatedExceptions = false;

    private ExpectedException() {
    }

    private void failDueToMissingException() throws AssertionError {
        String string2 = StringDescription.toString(this.fMatcherBuilder.build());
        Assert.fail("Expected test to throw " + string2);
    }

    private void handleException(Throwable throwable) throws Throwable {
        if (this.fMatcherBuilder.expectsThrowable()) {
            Assert.assertThat(throwable, this.fMatcherBuilder.build());
            return;
        }
        throw throwable;
    }

    public static ExpectedException none() {
        return new ExpectedException();
    }

    private void optionallyHandleException(Throwable throwable, boolean bl2) throws Throwable {
        if (bl2) {
            this.handleException(throwable);
            return;
        }
        throw throwable;
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        return new ExpectedExceptionStatement(statement);
    }

    public void expect(Class<? extends Throwable> class_) {
        this.expect(CoreMatchers.instanceOf(class_));
    }

    public void expect(Matcher<?> matcher) {
        this.fMatcherBuilder.add(matcher);
    }

    public void expectCause(Matcher<? extends Throwable> matcher) {
        this.expect(ThrowableCauseMatcher.hasCause(matcher));
    }

    public void expectMessage(String string2) {
        this.expectMessage(CoreMatchers.containsString(string2));
    }

    public void expectMessage(Matcher<String> matcher) {
        this.expect(ThrowableMessageMatcher.hasMessage(matcher));
    }

    public ExpectedException handleAssertionErrors() {
        this.handleAssertionErrors = true;
        return this;
    }

    public ExpectedException handleAssumptionViolatedExceptions() {
        this.handleAssumptionViolatedExceptions = true;
        return this;
    }

    private class ExpectedExceptionStatement
    extends Statement {
        private final Statement fNext;

        public ExpectedExceptionStatement(Statement statement) {
            this.fNext = statement;
        }

        @Override
        public void evaluate() throws Throwable {
            try {
                this.fNext.evaluate();
                if (ExpectedException.this.fMatcherBuilder.expectsThrowable()) {
                    ExpectedException.this.failDueToMissingException();
                }
                return;
            }
            catch (AssumptionViolatedException var1_1) {
                ExpectedException.this.optionallyHandleException(var1_1, ExpectedException.this.handleAssumptionViolatedExceptions);
                return;
            }
            catch (AssertionError var1_2) {
                ExpectedException.this.optionallyHandleException((Throwable)((Object)var1_2), ExpectedException.this.handleAssertionErrors);
                return;
            }
            catch (Throwable var1_3) {
                ExpectedException.this.handleException(var1_3);
                return;
            }
        }
    }

}

