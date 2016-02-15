/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfig;

public abstract class ClassIntrospector {
    protected ClassIntrospector() {
    }

    public abstract BeanDescription forClassAnnotations(MapperConfig<?> var1, JavaType var2, MixInResolver var3);

    public abstract BeanDescription forCreation(DeserializationConfig var1, JavaType var2, MixInResolver var3);

    public abstract BeanDescription forDeserialization(DeserializationConfig var1, JavaType var2, MixInResolver var3);

    public abstract BeanDescription forDeserializationWithBuilder(DeserializationConfig var1, JavaType var2, MixInResolver var3);

    public abstract BeanDescription forDirectClassAnnotations(MapperConfig<?> var1, JavaType var2, MixInResolver var3);

    public abstract BeanDescription forSerialization(SerializationConfig var1, JavaType var2, MixInResolver var3);

    public static interface MixInResolver {
        public Class<?> findMixInClassFor(Class<?> var1);
    }

}

