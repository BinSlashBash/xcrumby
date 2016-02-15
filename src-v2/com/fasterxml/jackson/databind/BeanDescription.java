/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.Converter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BeanDescription {
    protected final JavaType _type;

    protected BeanDescription(JavaType javaType) {
        this._type = javaType;
    }

    public abstract TypeBindings bindingsForBeanType();

    public abstract AnnotatedMember findAnyGetter();

    public abstract AnnotatedMethod findAnySetter();

    public abstract Map<String, AnnotatedMember> findBackReferenceProperties();

    public abstract AnnotatedConstructor findDefaultConstructor();

    public abstract Converter<Object, Object> findDeserializationConverter();

    public abstract JsonFormat.Value findExpectedFormat(JsonFormat.Value var1);

    public /* varargs */ abstract Method findFactoryMethod(Class<?> ... var1);

    public abstract Map<Object, AnnotatedMember> findInjectables();

    public abstract AnnotatedMethod findJsonValueMethod();

    public abstract AnnotatedMethod findMethod(String var1, Class<?>[] var2);

    public abstract Class<?> findPOJOBuilder();

    public abstract JsonPOJOBuilder.Value findPOJOBuilderConfig();

    public abstract List<BeanPropertyDefinition> findProperties();

    public abstract Converter<Object, Object> findSerializationConverter();

    public abstract JsonInclude.Include findSerializationInclusion(JsonInclude.Include var1);

    public /* varargs */ abstract Constructor<?> findSingleArgConstructor(Class<?> ... var1);

    public Class<?> getBeanClass() {
        return this._type.getRawClass();
    }

    public abstract Annotations getClassAnnotations();

    public abstract AnnotatedClass getClassInfo();

    public abstract List<AnnotatedConstructor> getConstructors();

    public abstract List<AnnotatedMethod> getFactoryMethods();

    public abstract Set<String> getIgnoredPropertyNames();

    public abstract ObjectIdInfo getObjectIdInfo();

    public JavaType getType() {
        return this._type;
    }

    public abstract boolean hasKnownClassAnnotations();

    public abstract Object instantiateBean(boolean var1);

    public abstract JavaType resolveType(Type var1);
}

