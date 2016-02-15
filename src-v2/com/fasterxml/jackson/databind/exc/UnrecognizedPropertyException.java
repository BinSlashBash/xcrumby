/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import java.util.Collection;

public class UnrecognizedPropertyException
extends PropertyBindingException {
    private static final long serialVersionUID = 1;

    public UnrecognizedPropertyException(String string2, JsonLocation jsonLocation, Class<?> class_, String string3, Collection<Object> collection) {
        super(string2, jsonLocation, class_, string3, collection);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static UnrecognizedPropertyException from(JsonParser object, Object object2, String string2, Collection<Object> collection) {
        if (object2 == null) {
            throw new IllegalArgumentException();
        }
        Class class_ = object2 instanceof Class ? (Class)object2 : object2.getClass();
        object = new UnrecognizedPropertyException("Unrecognized field \"" + string2 + "\" (class " + class_.getName() + "), not marked as ignorable", object.getCurrentLocation(), class_, string2, collection);
        object.prependPath(object2, string2);
        return object;
    }

    @Deprecated
    public String getUnrecognizedPropertyName() {
        return this.getPropertyName();
    }
}

