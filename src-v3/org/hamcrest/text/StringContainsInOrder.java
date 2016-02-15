package org.hamcrest.text;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class StringContainsInOrder extends TypeSafeMatcher<String> {
    private final Iterable<String> substrings;

    public StringContainsInOrder(Iterable<String> substrings) {
        this.substrings = substrings;
    }

    public boolean matchesSafely(String s) {
        int fromIndex = 0;
        for (String substring : this.substrings) {
            fromIndex = s.indexOf(substring, fromIndex);
            if (fromIndex == -1) {
                return false;
            }
        }
        return true;
    }

    public void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("was \"").appendText(item).appendText("\"");
    }

    public void describeTo(Description description) {
        description.appendText("a string containing ").appendValueList(UnsupportedUrlFragment.DISPLAY_NAME, ", ", UnsupportedUrlFragment.DISPLAY_NAME, this.substrings).appendText(" in order");
    }

    @Factory
    public static Matcher<String> stringContainsInOrder(Iterable<String> substrings) {
        return new StringContainsInOrder(substrings);
    }
}
