/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;

public class IsArray<T>
extends TypeSafeMatcher<T[]> {
    private final Matcher<? super T>[] elementMatchers;

    public IsArray(Matcher<? super T>[] arrmatcher) {
        this.elementMatchers = (Matcher[])arrmatcher.clone();
    }

    @Factory
    public static /* varargs */ <T> IsArray<T> array(Matcher<? super T> ... arrmatcher) {
        return new IsArray<T>(arrmatcher);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void describeMismatchSafely(T[] arrT, Description description) {
        if (arrT.length != this.elementMatchers.length) {
            description.appendText("array length was " + arrT.length);
            return;
        }
        int n2 = 0;
        while (n2 < arrT.length) {
            if (!this.elementMatchers[n2].matches(arrT[n2])) {
                description.appendText("element " + n2 + " was ").appendValue(arrT[n2]);
                return;
            }
            ++n2;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendList(this.descriptionStart(), this.descriptionSeparator(), this.descriptionEnd(), Arrays.asList(this.elementMatchers));
    }

    protected String descriptionEnd() {
        return "]";
    }

    protected String descriptionSeparator() {
        return ", ";
    }

    protected String descriptionStart() {
        return "[";
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean matchesSafely(T[] arrT) {
        if (arrT.length != this.elementMatchers.length) {
            return false;
        }
        int n2 = 0;
        while (n2 < arrT.length) {
            if (!this.elementMatchers[n2].matches(arrT[n2])) return false;
            ++n2;
        }
        return true;
    }
}

