/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.matchers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class StacktracePrintingMatcher<T extends Throwable>
extends TypeSafeMatcher<T> {
    private final Matcher<T> fThrowableMatcher;

    public StacktracePrintingMatcher(Matcher<T> matcher) {
        this.fThrowableMatcher = matcher;
    }

    @Factory
    public static <T extends Exception> Matcher<T> isException(Matcher<T> matcher) {
        return new StacktracePrintingMatcher<T>(matcher);
    }

    @Factory
    public static <T extends Throwable> Matcher<T> isThrowable(Matcher<T> matcher) {
        return new StacktracePrintingMatcher<T>(matcher);
    }

    private String readStacktrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @Override
    protected void describeMismatchSafely(T t2, Description description) {
        this.fThrowableMatcher.describeMismatch(t2, description);
        description.appendText("\nStacktrace was: ");
        description.appendText(this.readStacktrace((Throwable)t2));
    }

    @Override
    public void describeTo(Description description) {
        this.fThrowableMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(T t2) {
        return this.fThrowableMatcher.matches(t2);
    }
}

