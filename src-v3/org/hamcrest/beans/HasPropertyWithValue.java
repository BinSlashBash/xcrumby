package org.hamcrest.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import org.hamcrest.Condition;
import org.hamcrest.Condition.Step;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasPropertyWithValue<T> extends TypeSafeDiagnosingMatcher<T> {
    private static final Step<PropertyDescriptor, Method> WITH_READ_METHOD;
    private final String propertyName;
    private final Matcher<Object> valueMatcher;

    /* renamed from: org.hamcrest.beans.HasPropertyWithValue.1 */
    class C12241 implements Step<Method, Object> {
        final /* synthetic */ Object val$bean;

        C12241(Object obj) {
            this.val$bean = obj;
        }

        public Condition<Object> apply(Method readMethod, Description mismatch) {
            try {
                return Condition.matched(readMethod.invoke(this.val$bean, PropertyUtil.NO_ARGUMENTS), mismatch);
            } catch (Exception e) {
                mismatch.appendText(e.getMessage());
                return Condition.notMatched();
            }
        }
    }

    /* renamed from: org.hamcrest.beans.HasPropertyWithValue.2 */
    static class C12252 implements Step<PropertyDescriptor, Method> {
        C12252() {
        }

        public Condition<Method> apply(PropertyDescriptor property, Description mismatch) {
            Method readMethod = property.getReadMethod();
            if (readMethod != null) {
                return Condition.matched(readMethod, mismatch);
            }
            mismatch.appendText("property \"" + property.getName() + "\" is not readable");
            return Condition.notMatched();
        }
    }

    static {
        WITH_READ_METHOD = withReadMethod();
    }

    public HasPropertyWithValue(String propertyName, Matcher<?> valueMatcher) {
        this.propertyName = propertyName;
        this.valueMatcher = nastyGenericsWorkaround(valueMatcher);
    }

    public boolean matchesSafely(T bean, Description mismatch) {
        return propertyOn(bean, mismatch).and(WITH_READ_METHOD).and(withPropertyValue(bean)).matching(this.valueMatcher, "property '" + this.propertyName + "' ");
    }

    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(this.propertyName).appendText(", ").appendDescriptionOf(this.valueMatcher).appendText(")");
    }

    private Condition<PropertyDescriptor> propertyOn(T bean, Description mismatch) {
        PropertyDescriptor property = PropertyUtil.getPropertyDescriptor(this.propertyName, bean);
        if (property != null) {
            return Condition.matched(property, mismatch);
        }
        mismatch.appendText("No property \"" + this.propertyName + "\"");
        return Condition.notMatched();
    }

    private Step<Method, Object> withPropertyValue(T bean) {
        return new C12241(bean);
    }

    private static Matcher<Object> nastyGenericsWorkaround(Matcher<?> valueMatcher) {
        return valueMatcher;
    }

    private static Step<PropertyDescriptor, Method> withReadMethod() {
        return new C12252();
    }

    @Factory
    public static <T> Matcher<T> hasProperty(String propertyName, Matcher<?> valueMatcher) {
        return new HasPropertyWithValue(propertyName, valueMatcher);
    }
}
