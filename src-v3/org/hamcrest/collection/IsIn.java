package org.hamcrest.collection;

import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsIn<T> extends BaseMatcher<T> {
    private final Collection<T> collection;

    public IsIn(Collection<T> collection) {
        this.collection = collection;
    }

    public IsIn(T[] elements) {
        this.collection = Arrays.asList(elements);
    }

    public boolean matches(Object o) {
        return this.collection.contains(o);
    }

    public void describeTo(Description buffer) {
        buffer.appendText("one of ");
        buffer.appendValueList("{", ", ", "}", this.collection);
    }

    @Factory
    public static <T> Matcher<T> isIn(Collection<T> collection) {
        return new IsIn((Collection) collection);
    }

    @Factory
    public static <T> Matcher<T> isIn(T[] elements) {
        return new IsIn((Object[]) elements);
    }

    @Factory
    public static <T> Matcher<T> isOneOf(T... elements) {
        return isIn((Object[]) elements);
    }
}
