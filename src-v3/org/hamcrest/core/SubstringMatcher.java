package org.hamcrest.core;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class SubstringMatcher extends TypeSafeMatcher<String> {
    protected final String substring;

    protected abstract boolean evalSubstringOf(String str);

    protected abstract String relationship();

    protected SubstringMatcher(String substring) {
        this.substring = substring;
    }

    public boolean matchesSafely(String item) {
        return evalSubstringOf(item);
    }

    public void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("was \"").appendText(item).appendText("\"");
    }

    public void describeTo(Description description) {
        description.appendText("a string ").appendText(relationship()).appendText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).appendValue(this.substring);
    }
}
