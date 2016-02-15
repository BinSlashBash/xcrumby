/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public class AssumptionViolatedException
extends RuntimeException
implements SelfDescribing {
    private static final long serialVersionUID = 2;
    private final String fAssumption;
    private final Matcher<?> fMatcher;
    private final Object fValue;
    private final boolean fValueMatcher;

    public AssumptionViolatedException(Object object, Matcher<?> matcher) {
        this(null, true, object, matcher);
    }

    public AssumptionViolatedException(String string2) {
        this(string2, false, null, null);
    }

    public AssumptionViolatedException(String string2, Object object, Matcher<?> matcher) {
        this(string2, true, object, matcher);
    }

    public AssumptionViolatedException(String string2, Throwable throwable) {
        this(string2, false, throwable, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public AssumptionViolatedException(String string2, boolean bl2, Object object, Matcher<?> matcher) {
        Throwable throwable = object instanceof Throwable ? (Throwable)object : null;
        super(throwable);
        this.fAssumption = string2;
        this.fValue = object;
        this.fMatcher = matcher;
        this.fValueMatcher = bl2;
    }

    @Override
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

    @Override
    public String getMessage() {
        return StringDescription.asString(this);
    }
}

