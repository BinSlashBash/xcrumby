/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;

public class IsMapContaining<K, V>
extends TypeSafeMatcher<Map<? extends K, ? extends V>> {
    private final Matcher<? super K> keyMatcher;
    private final Matcher<? super V> valueMatcher;

    public IsMapContaining(Matcher<? super K> matcher, Matcher<? super V> matcher2) {
        this.keyMatcher = matcher;
        this.valueMatcher = matcher2;
    }

    @Factory
    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(K k2, V v2) {
        return new IsMapContaining<K, V>(IsEqual.equalTo(k2), IsEqual.equalTo(v2));
    }

    @Factory
    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(Matcher<? super K> matcher, Matcher<? super V> matcher2) {
        return new IsMapContaining<K, V>(matcher, matcher2);
    }

    @Factory
    public static <K> Matcher<Map<? extends K, ?>> hasKey(K k2) {
        return new IsMapContaining<K, Object>(IsEqual.equalTo(k2), IsAnything.anything());
    }

    @Factory
    public static <K> Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> matcher) {
        return new IsMapContaining<K, Object>(matcher, IsAnything.anything());
    }

    @Factory
    public static <V> Matcher<Map<?, ? extends V>> hasValue(V v2) {
        return new IsMapContaining<Object, V>(IsAnything.anything(), IsEqual.equalTo(v2));
    }

    @Factory
    public static <V> Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> matcher) {
        return new IsMapContaining<Object, V>(IsAnything.anything(), matcher);
    }

    @Override
    public void describeMismatchSafely(Map<? extends K, ? extends V> map, Description description) {
        description.appendText("map was ").appendValueList("[", ", ", "]", map.entrySet());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("map containing [").appendDescriptionOf(this.keyMatcher).appendText("->").appendDescriptionOf(this.valueMatcher).appendText("]");
    }

    @Override
    public boolean matchesSafely(Map<? extends K, ? extends V> object) {
        for (Map.Entry entry : object.entrySet()) {
            if (!this.keyMatcher.matches(entry.getKey()) || !this.valueMatcher.matches(entry.getValue())) continue;
            return true;
        }
        return false;
    }
}

