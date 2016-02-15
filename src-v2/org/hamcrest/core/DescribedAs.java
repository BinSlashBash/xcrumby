/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import java.util.regex.Pattern;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class DescribedAs<T>
extends BaseMatcher<T> {
    private static final Pattern ARG_PATTERN = Pattern.compile("%([0-9]+)");
    private final String descriptionTemplate;
    private final Matcher<T> matcher;
    private final Object[] values;

    public DescribedAs(String string2, Matcher<T> matcher, Object[] arrobject) {
        this.descriptionTemplate = string2;
        this.matcher = matcher;
        this.values = (Object[])arrobject.clone();
    }

    @Factory
    public static /* varargs */ <T> Matcher<T> describedAs(String string2, Matcher<T> matcher, Object ... arrobject) {
        return new DescribedAs<T>(string2, matcher, arrobject);
    }

    @Override
    public void describeMismatch(Object object, Description description) {
        this.matcher.describeMismatch(object, description);
    }

    @Override
    public void describeTo(Description description) {
        java.util.regex.Matcher matcher = ARG_PATTERN.matcher(this.descriptionTemplate);
        int n2 = 0;
        while (matcher.find()) {
            description.appendText(this.descriptionTemplate.substring(n2, matcher.start()));
            description.appendValue(this.values[Integer.parseInt(matcher.group(1))]);
            n2 = matcher.end();
        }
        if (n2 < this.descriptionTemplate.length()) {
            description.appendText(this.descriptionTemplate.substring(n2));
        }
    }

    @Override
    public boolean matches(Object object) {
        return this.matcher.matches(object);
    }
}

