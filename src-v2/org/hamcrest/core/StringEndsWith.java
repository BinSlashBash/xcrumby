/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;

public class StringEndsWith
extends SubstringMatcher {
    public StringEndsWith(String string2) {
        super(string2);
    }

    @Factory
    public static Matcher<String> endsWith(String string2) {
        return new StringEndsWith(string2);
    }

    @Override
    protected boolean evalSubstringOf(String string2) {
        return string2.endsWith(this.substring);
    }

    @Override
    protected String relationship() {
        return "ending with";
    }
}

