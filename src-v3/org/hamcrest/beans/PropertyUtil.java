package org.hamcrest.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class PropertyUtil {
    public static final Object[] NO_ARGUMENTS;

    public static PropertyDescriptor getPropertyDescriptor(String propertyName, Object fromObj) throws IllegalArgumentException {
        for (PropertyDescriptor property : propertyDescriptorsFor(fromObj, null)) {
            if (property.getName().equals(propertyName)) {
                return property;
            }
        }
        return null;
    }

    public static PropertyDescriptor[] propertyDescriptorsFor(Object fromObj, Class<Object> stopClass) throws IllegalArgumentException {
        try {
            return Introspector.getBeanInfo(fromObj.getClass(), stopClass).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new IllegalArgumentException("Could not get property descriptors for " + fromObj.getClass(), e);
        }
    }

    static {
        NO_ARGUMENTS = new Object[0];
    }
}
