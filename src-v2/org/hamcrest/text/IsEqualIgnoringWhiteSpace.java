/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualIgnoringWhiteSpace
extends TypeSafeMatcher<String> {
    private final String string;

    public IsEqualIgnoringWhiteSpace(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
        }
        this.string = string2;
    }

    @Factory
    public static Matcher<String> equalToIgnoringWhiteSpace(String string2) {
        return new IsEqualIgnoringWhiteSpace(string2);
    }

    @Override
    public void describeMismatchSafely(String string2, Description description) {
        description.appendText("was  ").appendText(this.stripSpace(string2));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equalToIgnoringWhiteSpace(").appendValue(this.string).appendText(")");
    }

    @Override
    public boolean matchesSafely(String string2) {
        return this.stripSpace(this.string).equalsIgnoreCase(this.stripSpace(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public String stripSpace(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl2 = true;
        int n2 = 0;
        while (n2 < string2.length()) {
            char c2 = string2.charAt(n2);
            if (Character.isWhitespace(c2)) {
                if (!bl2) {
                    stringBuilder.append(' ');
                }
                bl2 = true;
            } else {
                stringBuilder.append(c2);
                bl2 = false;
            }
            ++n2;
        }
        return stringBuilder.toString().trim();
    }
}

