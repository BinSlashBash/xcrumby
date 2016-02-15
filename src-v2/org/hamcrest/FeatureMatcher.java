/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class FeatureMatcher<T, U>
extends TypeSafeDiagnosingMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("featureValueOf", 1, 0);
    private final String featureDescription;
    private final String featureName;
    private final Matcher<? super U> subMatcher;

    public FeatureMatcher(Matcher<? super U> matcher, String string2, String string3) {
        super(TYPE_FINDER);
        this.subMatcher = matcher;
        this.featureDescription = string2;
        this.featureName = string3;
    }

    @Override
    public final void describeTo(Description description) {
        description.appendText(this.featureDescription).appendText(" ").appendDescriptionOf(this.subMatcher);
    }

    protected abstract U featureValueOf(T var1);

    @Override
    protected boolean matchesSafely(T object, Description description) {
        if (!this.subMatcher.matches(object = this.featureValueOf(object))) {
            description.appendText(this.featureName).appendText(" ");
            this.subMatcher.describeMismatch(object, description);
            return false;
        }
        return true;
    }
}

