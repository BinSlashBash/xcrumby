/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class SimpleAbstractTypeResolver
extends AbstractTypeResolver
implements Serializable {
    private static final long serialVersionUID = 8635483102371490919L;
    protected final HashMap<ClassKey, Class<?>> _mappings = new HashMap();

    public <T> SimpleAbstractTypeResolver addMapping(Class<T> class_, Class<? extends T> class_2) {
        if (class_ == class_2) {
            throw new IllegalArgumentException("Can not add mapping from class to itself");
        }
        if (!class_.isAssignableFrom(class_2)) {
            throw new IllegalArgumentException("Can not add mapping from class " + class_.getName() + " to " + class_2.getName() + ", as latter is not a subtype of former");
        }
        if (!Modifier.isAbstract(class_.getModifiers())) {
            throw new IllegalArgumentException("Can not add mapping from class " + class_.getName() + " since it is not abstract");
        }
        this._mappings.put(new ClassKey(class_), class_2);
        return this;
    }

    @Override
    public JavaType findTypeMapping(DeserializationConfig serializable, JavaType javaType) {
        serializable = javaType.getRawClass();
        if ((serializable = this._mappings.get(new ClassKey(serializable))) == null) {
            return null;
        }
        return javaType.narrowBy(serializable);
    }

    @Override
    public JavaType resolveAbstractType(DeserializationConfig deserializationConfig, JavaType javaType) {
        return null;
    }
}

