package org.hamcrest.collection;

import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyCollection<E> extends TypeSafeMatcher<Collection<? extends E>> {
    public boolean matchesSafely(Collection<? extends E> item) {
        return item.isEmpty();
    }

    public void describeMismatchSafely(Collection<? extends E> item, Description mismatchDescription) {
        mismatchDescription.appendValue(item);
    }

    public void describeTo(Description description) {
        description.appendText("an empty collection");
    }

    @Factory
    public static <E> Matcher<Collection<? extends E>> empty() {
        return new IsEmptyCollection();
    }

    @Factory
    public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> cls) {
        return empty();
    }
}
