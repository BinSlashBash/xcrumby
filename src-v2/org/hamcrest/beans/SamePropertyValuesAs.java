/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.beans.PropertyUtil;
import org.hamcrest.core.IsEqual;

public class SamePropertyValuesAs<T>
extends TypeSafeDiagnosingMatcher<T> {
    private final T expectedBean;
    private final List<PropertyMatcher> propertyMatchers;
    private final Set<String> propertyNames;

    public SamePropertyValuesAs(T t2) {
        PropertyDescriptor[] arrpropertyDescriptor = PropertyUtil.propertyDescriptorsFor(t2, Object.class);
        this.expectedBean = t2;
        this.propertyNames = SamePropertyValuesAs.propertyNamesFrom(arrpropertyDescriptor);
        this.propertyMatchers = SamePropertyValuesAs.propertyMatchersFor(t2, arrpropertyDescriptor);
    }

    private boolean hasMatchingValues(T t2, Description description) {
        for (PropertyMatcher propertyMatcher : this.propertyMatchers) {
            if (propertyMatcher.matches(t2)) continue;
            propertyMatcher.describeMismatch(t2, description);
            return false;
        }
        return true;
    }

    private boolean hasNoExtraProperties(T object, Description description) {
        object = SamePropertyValuesAs.propertyNamesFrom(PropertyUtil.propertyDescriptorsFor(object, Object.class));
        object.removeAll(this.propertyNames);
        if (!object.isEmpty()) {
            description.appendText("has extra properties called " + object);
            return false;
        }
        return true;
    }

    private boolean isCompatibleType(T t2, Description description) {
        if (!this.expectedBean.getClass().isAssignableFrom(t2.getClass())) {
            description.appendText("is incompatible type: " + t2.getClass().getSimpleName());
            return false;
        }
        return true;
    }

    private static <T> List<PropertyMatcher> propertyMatchersFor(T t2, PropertyDescriptor[] arrpropertyDescriptor) {
        ArrayList<PropertyMatcher> arrayList = new ArrayList<PropertyMatcher>(arrpropertyDescriptor.length);
        int n2 = arrpropertyDescriptor.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(new PropertyMatcher(arrpropertyDescriptor[i2], t2));
        }
        return arrayList;
    }

    private static Set<String> propertyNamesFrom(PropertyDescriptor[] arrpropertyDescriptor) {
        HashSet<String> hashSet = new HashSet<String>();
        int n2 = arrpropertyDescriptor.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            hashSet.add(arrpropertyDescriptor[i2].getDisplayName());
        }
        return hashSet;
    }

    private static Object readProperty(Method method, Object object) {
        try {
            Object object2 = method.invoke(object, PropertyUtil.NO_ARGUMENTS);
            return object2;
        }
        catch (Exception var2_3) {
            throw new IllegalArgumentException("Could not invoke " + method + " on " + object, var2_3);
        }
    }

    @Factory
    public static <T> Matcher<T> samePropertyValuesAs(T t2) {
        return new SamePropertyValuesAs<T>(t2);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("same property values as " + this.expectedBean.getClass().getSimpleName()).appendList(" [", ", ", "]", this.propertyMatchers);
    }

    @Override
    public boolean matchesSafely(T t2, Description description) {
        if (this.isCompatibleType(t2, description) && this.hasNoExtraProperties(t2, description) && this.hasMatchingValues(t2, description)) {
            return true;
        }
        return false;
    }

    public static class PropertyMatcher
    extends DiagnosingMatcher<Object> {
        private final Matcher<Object> matcher;
        private final String propertyName;
        private final Method readMethod;

        public PropertyMatcher(PropertyDescriptor propertyDescriptor, Object object) {
            this.propertyName = propertyDescriptor.getDisplayName();
            this.readMethod = propertyDescriptor.getReadMethod();
            this.matcher = IsEqual.equalTo(SamePropertyValuesAs.readProperty(this.readMethod, object));
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(this.propertyName + ": ").appendDescriptionOf(this.matcher);
        }

        @Override
        public boolean matches(Object object, Description description) {
            if (!this.matcher.matches(object = SamePropertyValuesAs.readProperty(this.readMethod, object))) {
                description.appendText(this.propertyName + " ");
                this.matcher.describeMismatch(object, description);
                return false;
            }
            return true;
        }
    }

}

