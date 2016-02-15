/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class PropertyUtil {
    public static final Object[] NO_ARGUMENTS = new Object[0];

    public static PropertyDescriptor getPropertyDescriptor(String string2, Object arrpropertyDescriptor) throws IllegalArgumentException {
        for (PropertyDescriptor propertyDescriptor : PropertyUtil.propertyDescriptorsFor(arrpropertyDescriptor, null)) {
            if (!propertyDescriptor.getName().equals(string2)) continue;
            return propertyDescriptor;
        }
        return null;
    }

    public static PropertyDescriptor[] propertyDescriptorsFor(Object object, Class<Object> arrpropertyDescriptor) throws IllegalArgumentException {
        try {
            arrpropertyDescriptor = Introspector.getBeanInfo(object.getClass(), arrpropertyDescriptor).getPropertyDescriptors();
            return arrpropertyDescriptor;
        }
        catch (IntrospectionException var1_2) {
            throw new IllegalArgumentException("Could not get property descriptors for " + object.getClass(), var1_2);
        }
    }
}

