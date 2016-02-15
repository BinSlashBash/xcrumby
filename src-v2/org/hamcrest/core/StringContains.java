/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.SubstringMatcher;

public class StringContains
extends SubstringMatcher {
    public StringContains(String string2) {
        super(string2);
    }

    @Factory
    public static Matcher<String> containsString(String string2) {
        return new StringContains(string2);
    }

    @Override
    protected boolean evalSubstringOf(String string2) {
        if (string2.indexOf(this.substring) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    protected String relationship() {
        return "containing";
    }
}

