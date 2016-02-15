/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public class MatcherAssert {
    public static <T> void assertThat(T t2, Matcher<? super T> matcher) {
        MatcherAssert.assertThat("", t2, matcher);
    }

    public static <T> void assertThat(String string2, T t2, Matcher<? super T> matcher) {
        if (!matcher.matches(t2)) {
            StringDescription stringDescription = new StringDescription();
            stringDescription.appendText(string2).appendText("\nExpected: ").appendDescriptionOf(matcher).appendText("\n     but: ");
            matcher.describeMismatch(t2, stringDescription);
            throw new AssertionError((Object)stringDescription.toString());
        }
    }

    public static void assertThat(String string2, boolean bl2) {
        if (!bl2) {
            throw new AssertionError((Object)string2);
        }
    }
}

