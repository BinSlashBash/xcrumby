/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.beans.PropertyUtil;

public class HasPropertyWithValue<T>
extends TypeSafeDiagnosingMatcher<T> {
    private static final Condition.Step<PropertyDescriptor, Method> WITH_READ_METHOD = HasPropertyWithValue.withReadMethod();
    private final String propertyName;
    private final Matcher<Object> valueMatcher;

    public HasPropertyWithValue(String string2, Matcher<?> matcher) {
        this.propertyName = string2;
        this.valueMatcher = HasPropertyWithValue.nastyGenericsWorkaround(matcher);
    }

    @Factory
    public static <T> Matcher<T> hasProperty(String string2, Matcher<?> matcher) {
        return new HasPropertyWithValue<T>(string2, matcher);
    }

    private static Matcher<Object> nastyGenericsWorkaround(Matcher<?> matcher) {
        return matcher;
    }

    private Condition<PropertyDescriptor> propertyOn(T object, Description description) {
        if ((object = PropertyUtil.getPropertyDescriptor(this.propertyName, object)) == null) {
            description.appendText("No property \"" + this.propertyName + "\"");
            return Condition.notMatched();
        }
        return Condition.matched(object, description);
    }

    private Condition.Step<Method, Object> withPropertyValue(final T t2) {
        return new Condition.Step<Method, Object>(){

            @Override
            public Condition<Object> apply(Method object, Description description) {
                try {
                    object = Condition.matched(object.invoke(t2, PropertyUtil.NO_ARGUMENTS), description);
                    return object;
                }
                catch (Exception var1_2) {
                    description.appendText(var1_2.getMessage());
                    return Condition.notMatched();
                }
            }
        };
    }

    private static Condition.Step<PropertyDescriptor, Method> withReadMethod() {
        return new Condition.Step<PropertyDescriptor, Method>(){

            @Override
            public Condition<Method> apply(PropertyDescriptor propertyDescriptor, Description description) {
                Method method = propertyDescriptor.getReadMethod();
                if (method == null) {
                    description.appendText("property \"" + propertyDescriptor.getName() + "\" is not readable");
                    return Condition.notMatched();
                }
                return Condition.matched(method, description);
            }
        };
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(this.propertyName).appendText(", ").appendDescriptionOf(this.valueMatcher).appendText(")");
    }

    @Override
    public boolean matchesSafely(T t2, Description description) {
        return this.propertyOn(t2, description).and(WITH_READ_METHOD).and(this.withPropertyValue(t2)).matching(this.valueMatcher, "property '" + this.propertyName + "' ");
    }

}

