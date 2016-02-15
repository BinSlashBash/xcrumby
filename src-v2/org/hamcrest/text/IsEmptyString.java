/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.text;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsNull;

public final class IsEmptyString
extends BaseMatcher<String> {
    private static final IsEmptyString INSTANCE = new IsEmptyString();
    private static final Matcher<String> NULL_OR_EMPTY_INSTANCE = AnyOf.anyOf(new Matcher[]{IsNull.nullValue(), INSTANCE});

    @Factory
    public static Matcher<String> isEmptyOrNullString() {
        return NULL_OR_EMPTY_INSTANCE;
    }

    @Factory
    public static Matcher<String> isEmptyString() {
        return INSTANCE;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty string");
    }

    @Override
    public boolean matches(Object object) {
        if (object != null && object instanceof String && ((String)object).equals("")) {
            return true;
        }
        return false;
    }
}

