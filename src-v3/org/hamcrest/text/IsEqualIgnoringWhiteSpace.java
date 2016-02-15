package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualIgnoringWhiteSpace extends TypeSafeMatcher<String> {
    private final String string;

    public IsEqualIgnoringWhiteSpace(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
        }
        this.string = string;
    }

    public boolean matchesSafely(String item) {
        return stripSpace(this.string).equalsIgnoreCase(stripSpace(item));
    }

    public void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("was  ").appendText(stripSpace(item));
    }

    public void describeTo(Description description) {
        description.appendText("equalToIgnoringWhiteSpace(").appendValue(this.string).appendText(")");
    }

    public String stripSpace(String toBeStripped) {
        StringBuilder result = new StringBuilder();
        boolean lastWasSpace = true;
        for (int i = 0; i < toBeStripped.length(); i++) {
            char c = toBeStripped.charAt(i);
            if (Character.isWhitespace(c)) {
                if (!lastWasSpace) {
                    result.append(' ');
                }
                lastWasSpace = true;
            } else {
                result.append(c);
                lastWasSpace = false;
            }
        }
        return result.toString().trim();
    }

    @Factory
    public static Matcher<String> equalToIgnoringWhiteSpace(String expectedString) {
        return new IsEqualIgnoringWhiteSpace(expectedString);
    }
}
