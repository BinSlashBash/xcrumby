/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.util.HashMap;

public class SimpleKeyDeserializers
implements KeyDeserializers,
Serializable {
    private static final long serialVersionUID = -6786398737835438187L;
    protected HashMap<ClassKey, KeyDeserializer> _classMappings = null;

    public SimpleKeyDeserializers addDeserializer(Class<?> class_, KeyDeserializer keyDeserializer) {
        if (this._classMappings == null) {
            this._classMappings = new HashMap();
        }
        this._classMappings.put(new ClassKey(class_), keyDeserializer);
        return this;
    }

    @Override
    public KeyDeserializer findKeyDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(javaType.getRawClass()));
    }
}

