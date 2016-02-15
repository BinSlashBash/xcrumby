package org.hamcrest.collection;

import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsArray<T> extends TypeSafeMatcher<T[]> {
    private final Matcher<? super T>[] elementMatchers;

    public IsArray(Matcher<? super T>[] elementMatchers) {
        this.elementMatchers = (Matcher[]) elementMatchers.clone();
    }

    public boolean matchesSafely(T[] array) {
        if (array.length != this.elementMatchers.length) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (!this.elementMatchers[i].matches(array[i])) {
                return false;
            }
        }
        return true;
    }

    public void describeMismatchSafely(T[] actual, Description mismatchDescription) {
        if (actual.length != this.elementMatchers.length) {
            mismatchDescription.appendText("array length was " + actual.length);
            return;
        }
        int i = 0;
        while (i < actual.length) {
            if (this.elementMatchers[i].matches(actual[i])) {
                i++;
            } else {
                mismatchDescription.appendText("element " + i + " was ").appendValue(actual[i]);
                return;
            }
        }
    }

    public void describeTo(Description description) {
        description.appendList(descriptionStart(), descriptionSeparator(), descriptionEnd(), Arrays.asList(this.elementMatchers));
    }

    protected String descriptionStart() {
        return "[";
    }

    protected String descriptionSeparator() {
        return ", ";
    }

    protected String descriptionEnd() {
        return "]";
    }

    @Factory
    public static <T> IsArray<T> array(Matcher<? super T>... elementMatchers) {
        return new IsArray(elementMatchers);
    }
}
