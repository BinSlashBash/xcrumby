/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.POJOPropertiesCollector;
import com.fasterxml.jackson.databind.type.SimpleType;
import java.io.Serializable;
import java.lang.reflect.Type;

public class BasicClassIntrospector
extends ClassIntrospector
implements Serializable {
    protected static final BasicBeanDescription BOOLEAN_DESC;
    protected static final BasicBeanDescription INT_DESC;
    protected static final BasicBeanDescription LONG_DESC;
    protected static final BasicBeanDescription STRING_DESC;
    public static final BasicClassIntrospector instance;
    private static final long serialVersionUID = 1;

    static {
        AnnotatedClass annotatedClass = AnnotatedClass.constructWithoutSuperTypes(String.class, null, null);
        STRING_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(String.class), annotatedClass);
        annotatedClass = AnnotatedClass.constructWithoutSuperTypes(Boolean.TYPE, null, null);
        BOOLEAN_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Boolean.TYPE), annotatedClass);
        annotatedClass = AnnotatedClass.constructWithoutSuperTypes(Integer.TYPE, null, null);
        INT_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Integer.TYPE), annotatedClass);
        annotatedClass = AnnotatedClass.constructWithoutSuperTypes(Long.TYPE, null, null);
        LONG_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Long.TYPE), annotatedClass);
        instance = new BasicClassIntrospector();
    }

    protected BasicBeanDescription _findCachedDesc(JavaType type) {
        if ((type = type.getRawClass()) == String.class) {
            return STRING_DESC;
        }
        if (type == Boolean.TYPE) {
            return BOOLEAN_DESC;
        }
        if (type == Integer.TYPE) {
            return INT_DESC;
        }
        if (type == Long.TYPE) {
            return LONG_DESC;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected POJOPropertiesCollector collectProperties(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver, boolean bl2, String string2) {
        AnnotationIntrospector annotationIntrospector;
        boolean bl3 = mapperConfig.isAnnotationProcessingEnabled();
        Class class_ = javaType.getRawClass();
        if (bl3) {
            annotationIntrospector = mapperConfig.getAnnotationIntrospector();
            do {
                return this.constructPropertyCollector(mapperConfig, AnnotatedClass.construct(class_, annotationIntrospector, mixInResolver), javaType, bl2, string2).collect();
                break;
            } while (true);
        }
        annotationIntrospector = null;
        return this.constructPropertyCollector(mapperConfig, AnnotatedClass.construct(class_, annotationIntrospector, mixInResolver), javaType, bl2, string2).collect();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected POJOPropertiesCollector collectPropertiesWithBuilder(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver object, boolean bl2) {
        AnnotationIntrospector annotationIntrospector = mapperConfig.isAnnotationProcessingEnabled() ? mapperConfig.getAnnotationIntrospector() : null;
        AnnotatedClass annotatedClass = AnnotatedClass.construct(javaType.getRawClass(), annotationIntrospector, (ClassIntrospector.MixInResolver)object);
        object = annotationIntrospector == null ? null : annotationIntrospector.findPOJOBuilderConfig(annotatedClass);
        if (object == null) {
            object = "with";
            return this.constructPropertyCollector(mapperConfig, annotatedClass, javaType, bl2, (String)object).collect();
        }
        object = object.withPrefix;
        return this.constructPropertyCollector(mapperConfig, annotatedClass, javaType, bl2, (String)object).collect();
    }

    protected POJOPropertiesCollector constructPropertyCollector(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType, boolean bl2, String string2) {
        return new POJOPropertiesCollector(mapperConfig, bl2, javaType, annotatedClass, string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public BasicBeanDescription forClassAnnotations(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        AnnotationIntrospector annotationIntrospector;
        boolean bl2 = mapperConfig.isAnnotationProcessingEnabled();
        Class class_ = javaType.getRawClass();
        if (bl2) {
            annotationIntrospector = mapperConfig.getAnnotationIntrospector();
            do {
                return BasicBeanDescription.forOtherUse(mapperConfig, javaType, AnnotatedClass.construct(class_, annotationIntrospector, mixInResolver));
                break;
            } while (true);
        }
        annotationIntrospector = null;
        return BasicBeanDescription.forOtherUse(mapperConfig, javaType, AnnotatedClass.construct(class_, annotationIntrospector, mixInResolver));
    }

    @Override
    public BasicBeanDescription forCreation(DeserializationConfig deserializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription basicBeanDescription;
        BasicBeanDescription basicBeanDescription2 = basicBeanDescription = this._findCachedDesc(javaType);
        if (basicBeanDescription == null) {
            basicBeanDescription2 = BasicBeanDescription.forDeserialization(this.collectProperties(deserializationConfig, javaType, mixInResolver, false, "set"));
        }
        return basicBeanDescription2;
    }

    @Override
    public BasicBeanDescription forDeserialization(DeserializationConfig deserializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription basicBeanDescription;
        BasicBeanDescription basicBeanDescription2 = basicBeanDescription = this._findCachedDesc(javaType);
        if (basicBeanDescription == null) {
            basicBeanDescription2 = BasicBeanDescription.forDeserialization(this.collectProperties(deserializationConfig, javaType, mixInResolver, false, "set"));
        }
        return basicBeanDescription2;
    }

    @Override
    public BasicBeanDescription forDeserializationWithBuilder(DeserializationConfig deserializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        return BasicBeanDescription.forDeserialization(this.collectPropertiesWithBuilder(deserializationConfig, javaType, mixInResolver, false));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public BasicBeanDescription forDirectClassAnnotations(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        boolean bl2 = mapperConfig.isAnnotationProcessingEnabled();
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        Class class_ = javaType.getRawClass();
        if (bl2) {
            do {
                return BasicBeanDescription.forOtherUse(mapperConfig, javaType, AnnotatedClass.constructWithoutSuperTypes(class_, annotationIntrospector, mixInResolver));
                break;
            } while (true);
        }
        annotationIntrospector = null;
        return BasicBeanDescription.forOtherUse(mapperConfig, javaType, AnnotatedClass.constructWithoutSuperTypes(class_, annotationIntrospector, mixInResolver));
    }

    @Override
    public BasicBeanDescription forSerialization(SerializationConfig serializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription basicBeanDescription;
        BasicBeanDescription basicBeanDescription2 = basicBeanDescription = this._findCachedDesc(javaType);
        if (basicBeanDescription == null) {
            basicBeanDescription2 = BasicBeanDescription.forSerialization(this.collectProperties(serializationConfig, javaType, mixInResolver, true, "set"));
        }
        return basicBeanDescription2;
    }
}

