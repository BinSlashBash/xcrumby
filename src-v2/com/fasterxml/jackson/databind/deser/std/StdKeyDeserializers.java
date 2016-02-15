/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class StdKeyDeserializers
implements KeyDeserializers,
Serializable {
    private static final long serialVersionUID = 923268084968181479L;

    public static KeyDeserializer constructDelegatingKeyDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, JsonDeserializer<?> jsonDeserializer) {
        return new StdKeyDeserializer.DelegatingKD(javaType.getRawClass(), jsonDeserializer);
    }

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver<?> enumResolver) {
        return new StdKeyDeserializer.EnumKD(enumResolver, null);
    }

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver<?> enumResolver, AnnotatedMethod annotatedMethod) {
        return new StdKeyDeserializer.EnumKD(enumResolver, annotatedMethod);
    }

    public static KeyDeserializer findStringBasedKeyDeserializer(DeserializationConfig deserializationConfig, JavaType object) {
        Constructor constructor = (object = deserializationConfig.introspect((JavaType)object)).findSingleArgConstructor(String.class);
        if (constructor != null) {
            if (deserializationConfig.canOverrideAccessModifiers()) {
                ClassUtil.checkAndFixAccess(constructor);
            }
            return new StdKeyDeserializer.StringCtorKeyDeserializer(constructor);
        }
        if ((object = object.findFactoryMethod(String.class)) != null) {
            if (deserializationConfig.canOverrideAccessModifiers()) {
                ClassUtil.checkAndFixAccess((Member)object);
            }
            return new StdKeyDeserializer.StringFactoryKeyDeserializer((Method)object);
        }
        return null;
    }

    @Override
    public KeyDeserializer findKeyDeserializer(JavaType serializable, DeserializationConfig serializable2, BeanDescription beanDescription) throws JsonMappingException {
        serializable = serializable2 = serializable.getRawClass();
        if (serializable2.isPrimitive()) {
            serializable = ClassUtil.wrapperType(serializable2);
        }
        return StdKeyDeserializer.forType(serializable);
    }
}

