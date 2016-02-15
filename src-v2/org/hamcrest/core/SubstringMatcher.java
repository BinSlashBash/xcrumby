/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class SubstringMatcher
extends TypeSafeMatcher<String> {
    protected final String substring;

    protected SubstringMatcher(String string2) {
        this.substring = string2;
    }

    @Override
    public void describeMismatchSafely(String string2, Description description) {
        description.appendText("was \"").appendText(string2).appendText("\"");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string ").appendText(this.relationship()).appendText(" ").appendValue(this.substring);
    }

    protected abstract boolean evalSubstringOf(String var1);

    @Override
    public boolean matchesSafely(String string2) {
        return this.evalSubstringOf(string2);
    }

    protected abstract String relationship();
}

