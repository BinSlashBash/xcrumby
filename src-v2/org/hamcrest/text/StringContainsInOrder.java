/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.text;

import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class StringContainsInOrder
extends TypeSafeMatcher<String> {
    private final Iterable<String> substrings;

    public StringContainsInOrder(Iterable<String> iterable) {
        this.substrings = iterable;
    }

    @Factory
    public static Matcher<String> stringContainsInOrder(Iterable<String> iterable) {
        return new StringContainsInOrder(iterable);
    }

    @Override
    public void describeMismatchSafely(String string2, Description description) {
        description.appendText("was \"").appendText(string2).appendText("\"");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string containing ").appendValueList("", ", ", "", this.substrings).appendText(" in order");
    }

    @Override
    public boolean matchesSafely(String string2) {
        int n2 = 0;
        Iterator<String> iterator = this.substrings.iterator();
        while (iterator.hasNext()) {
            int n3;
            n2 = n3 = string2.indexOf(iterator.next(), n2);
            if (n3 != -1) continue;
            return false;
        }
        return true;
    }
}

