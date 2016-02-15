package org.hamcrest;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class FeatureMatcher<T, U> extends TypeSafeDiagnosingMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER;
    private final String featureDescription;
    private final String featureName;
    private final Matcher<? super U> subMatcher;

    protected abstract U featureValueOf(T t);

    static {
        TYPE_FINDER = new ReflectiveTypeFinder("featureValueOf", 1, 0);
    }

    public FeatureMatcher(Matcher<? super U> subMatcher, String featureDescription, String featureName) {
        super(TYPE_FINDER);
        this.subMatcher = subMatcher;
        this.featureDescription = featureDescription;
        this.featureName = featureName;
    }

    protected boolean matchesSafely(T actual, Description mismatch) {
        U featureValue = featureValueOf(actual);
        if (this.subMatcher.matches(featureValue)) {
            return true;
        }
        mismatch.appendText(this.featureName).appendText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        this.subMatcher.describeMismatch(featureValue, mismatch);
        return false;
    }

    public final void describeTo(Description description) {
        description.appendText(this.featureDescription).appendText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).appendDescriptionOf(this.subMatcher);
    }
}
