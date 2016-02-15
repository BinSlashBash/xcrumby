package org.hamcrest.beans;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class SamePropertyValuesAs<T> extends TypeSafeDiagnosingMatcher<T> {
    private final T expectedBean;
    private final List<PropertyMatcher> propertyMatchers;
    private final Set<String> propertyNames;

    public static class PropertyMatcher extends DiagnosingMatcher<Object> {
        private final Matcher<Object> matcher;
        private final String propertyName;
        private final Method readMethod;

        public PropertyMatcher(PropertyDescriptor descriptor, Object expectedObject) {
            this.propertyName = descriptor.getDisplayName();
            this.readMethod = descriptor.getReadMethod();
            this.matcher = IsEqual.equalTo(SamePropertyValuesAs.readProperty(this.readMethod, expectedObject));
        }

        public boolean matches(Object actual, Description mismatch) {
            Object actualValue = SamePropertyValuesAs.readProperty(this.readMethod, actual);
            if (this.matcher.matches(actualValue)) {
                return true;
            }
            mismatch.appendText(this.propertyName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            this.matcher.describeMismatch(actualValue, mismatch);
            return false;
        }

        public void describeTo(Description description) {
            description.appendText(this.propertyName + ": ").appendDescriptionOf(this.matcher);
        }
    }

    public SamePropertyValuesAs(T expectedBean) {
        PropertyDescriptor[] descriptors = PropertyUtil.propertyDescriptorsFor(expectedBean, Object.class);
        this.expectedBean = expectedBean;
        this.propertyNames = propertyNamesFrom(descriptors);
        this.propertyMatchers = propertyMatchersFor(expectedBean, descriptors);
    }

    public boolean matchesSafely(T bean, Description mismatch) {
        return isCompatibleType(bean, mismatch) && hasNoExtraProperties(bean, mismatch) && hasMatchingValues(bean, mismatch);
    }

    public void describeTo(Description description) {
        description.appendText("same property values as " + this.expectedBean.getClass().getSimpleName()).appendList(" [", ", ", "]", this.propertyMatchers);
    }

    private boolean isCompatibleType(T item, Description mismatchDescription) {
        if (this.expectedBean.getClass().isAssignableFrom(item.getClass())) {
            return true;
        }
        mismatchDescription.appendText("is incompatible type: " + item.getClass().getSimpleName());
        return false;
    }

    private boolean hasNoExtraProperties(T item, Description mismatchDescription) {
        Set<String> actualPropertyNames = propertyNamesFrom(PropertyUtil.propertyDescriptorsFor(item, Object.class));
        actualPropertyNames.removeAll(this.propertyNames);
        if (actualPropertyNames.isEmpty()) {
            return true;
        }
        mismatchDescription.appendText("has extra properties called " + actualPropertyNames);
        return false;
    }

    private boolean hasMatchingValues(T item, Description mismatchDescription) {
        for (PropertyMatcher propertyMatcher : this.propertyMatchers) {
            if (!propertyMatcher.matches(item)) {
                propertyMatcher.describeMismatch(item, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    private static <T> List<PropertyMatcher> propertyMatchersFor(T bean, PropertyDescriptor[] descriptors) {
        List<PropertyMatcher> result = new ArrayList(descriptors.length);
        for (PropertyDescriptor propertyDescriptor : descriptors) {
            result.add(new PropertyMatcher(propertyDescriptor, bean));
        }
        return result;
    }

    private static Set<String> propertyNamesFrom(PropertyDescriptor[] descriptors) {
        HashSet<String> result = new HashSet();
        for (PropertyDescriptor propertyDescriptor : descriptors) {
            result.add(propertyDescriptor.getDisplayName());
        }
        return result;
    }

    private static Object readProperty(Method method, Object target) {
        try {
            return method.invoke(target, PropertyUtil.NO_ARGUMENTS);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not invoke " + method + " on " + target, e);
        }
    }

    @Factory
    public static <T> Matcher<T> samePropertyValuesAs(T expectedBean) {
        return new SamePropertyValuesAs(expectedBean);
    }
}
