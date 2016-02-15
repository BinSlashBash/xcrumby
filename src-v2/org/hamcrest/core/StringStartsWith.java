/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;

public class StringStartsWith
extends SubstringMatcher {
    public StringStartsWith(String string2) {
        super(string2);
    }

    @Factory
    public static Matcher<String> startsWith(String string2) {
        return new StringStartsWith(string2);
    }

    @Override
    protected boolean evalSubstringOf(String string2) {
        return string2.startsWith(this.substring);
    }

    @Override
    protected String relationship() {
        return "starting with";
    }
}

