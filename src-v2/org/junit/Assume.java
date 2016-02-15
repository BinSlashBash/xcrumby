/*
 * Decompiled with CFR 0_110.
 */
package org.junit;

import java.util.Arrays;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.internal.AssumptionViolatedException;

public class Assume {
    /*
     * Enabled aggressive block sorting
     */
    public static void assumeFalse(String string2, boolean bl2) {
        bl2 = !bl2;
        Assume.assumeTrue(string2, bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void assumeFalse(boolean bl2) {
        bl2 = !bl2;
        Assume.assumeTrue(bl2);
    }

    public static void assumeNoException(String string2, Throwable throwable) {
        Assume.assumeThat(string2, throwable, CoreMatchers.nullValue());
    }

    public static void assumeNoException(Throwable throwable) {
        Assume.assumeThat(throwable, CoreMatchers.nullValue());
    }

    public static /* varargs */ void assumeNotNull(Object ... arrobject) {
        Assume.assumeThat(Arrays.asList(arrobject), CoreMatchers.everyItem(CoreMatchers.notNullValue()));
    }

    public static <T> void assumeThat(T t2, Matcher<T> matcher) {
        if (!matcher.matches(t2)) {
            throw new AssumptionViolatedException(t2, matcher);
        }
    }

    public static <T> void assumeThat(String string2, T t2, Matcher<T> matcher) {
        if (!matcher.matches(t2)) {
            throw new AssumptionViolatedException(string2, t2, matcher);
        }
    }

    public static void assumeTrue(String string2, boolean bl2) {
        if (!bl2) {
            throw new AssumptionViolatedException(string2);
        }
    }

    public static void assumeTrue(boolean bl2) {
        Assume.assumeThat(bl2, CoreMatchers.is(true));
    }
}

