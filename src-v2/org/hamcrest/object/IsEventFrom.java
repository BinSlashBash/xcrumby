/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.object;

import java.util.EventObject;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsEventFrom
extends TypeSafeDiagnosingMatcher<EventObject> {
    private final Class<?> eventClass;
    private final Object source;

    public IsEventFrom(Class<?> class_, Object object) {
        this.eventClass = class_;
        this.source = object;
    }

    @Factory
    public static Matcher<EventObject> eventFrom(Class<? extends EventObject> class_, Object object) {
        return new IsEventFrom(class_, object);
    }

    @Factory
    public static Matcher<EventObject> eventFrom(Object object) {
        return IsEventFrom.eventFrom(EventObject.class, object);
    }

    private boolean eventHasSameSource(EventObject eventObject) {
        if (eventObject.getSource() == this.source) {
            return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an event of type ").appendText(this.eventClass.getName()).appendText(" from ").appendValue(this.source);
    }

    @Override
    public boolean matchesSafely(EventObject eventObject, Description description) {
        if (!this.eventClass.isInstance(eventObject)) {
            description.appendText("item type was " + eventObject.getClass().getName());
            return false;
        }
        if (!this.eventHasSameSource(eventObject)) {
            description.appendText("source was ").appendValue(eventObject.getSource());
            return false;
        }
        return true;
    }
}

