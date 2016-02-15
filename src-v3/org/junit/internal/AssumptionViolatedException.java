package org.junit.internal;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public class AssumptionViolatedException extends RuntimeException implements SelfDescribing {
    private static final long serialVersionUID = 2;
    private final String fAssumption;
    private final Matcher<?> fMatcher;
    private final Object fValue;
    private final boolean fValueMatcher;

    public AssumptionViolatedException(String assumption, boolean valueMatcher, Object value, Matcher<?> matcher) {
        super(value instanceof Throwable ? (Throwable) value : null);
        this.fAssumption = assumption;
        this.fValue = value;
        this.fMatcher = matcher;
        this.fValueMatcher = valueMatcher;
    }

    public AssumptionViolatedException(Object value, Matcher<?> matcher) {
        this(null, true, value, matcher);
    }

    public AssumptionViolatedException(String assumption, Object value, Matcher<?> matcher) {
        this(assumption, true, value, matcher);
    }

    public AssumptionViolatedException(String assumption) {
        this(assumption, false, null, null);
    }

    public AssumptionViolatedException(String assumption, Throwable t) {
        this(assumption, false, t, null);
    }

    public String getMessage() {
        return StringDescription.asString(this);
    }

    public void describeTo(Description description) {
        if (this.fAssumption != null) {
            description.appendText(this.fAssumption);
        }
        if (this.fValueMatcher) {
            if (this.fAssumption != null) {
                description.appendText(": ");
            }
            description.appendText("got: ");
            description.appendValue(this.fValue);
            if (this.fMatcher != null) {
                description.appendText(", expected: ");
                description.appendDescriptionOf(this.fMatcher);
            }
        }
    }
}
