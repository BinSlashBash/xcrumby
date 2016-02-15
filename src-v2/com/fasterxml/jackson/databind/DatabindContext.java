/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.lang.reflect.Type;

public abstract class DatabindContext {
    public final boolean canOverrideAccessModifiers() {
        return this.getConfig().canOverrideAccessModifiers();
    }

    public JavaType constructSpecializedType(JavaType javaType, Class<?> class_) {
        if (javaType.getRawClass() == class_) {
            return javaType;
        }
        return this.getConfig().constructSpecializedType(javaType, class_);
    }

    public JavaType constructType(Type type) {
        return this.getTypeFactory().constructType(type);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Converter<Object, Object> converterInstance(Annotated converter, Object object) throws JsonMappingException {
        Object var3_3 = null;
        if (object == null) {
            return null;
        }
        if (object instanceof Converter) {
            return (Converter)object;
        }
        if (!(object instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector returned Converter definition of type " + object.getClass().getName() + "; expected type Converter or Class<Converter> instead");
        }
        Class class_ = (Class)object;
        if (class_ == Converter.None.class) return null;
        if (ClassUtil.isBogusClass(class_)) {
            return null;
        }
        if (!Converter.class.isAssignableFrom(class_)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + class_.getName() + "; expected Class<Converter>");
        }
        MapperConfig mapperConfig = this.getConfig();
        object = mapperConfig.getHandlerInstantiator();
        converter = object == null ? var3_3 : object.converterInstance(mapperConfig, (Annotated)((Object)converter), class_);
        object = converter;
        if (converter != null) return object;
        return (Converter)ClassUtil.createInstance(class_, mapperConfig.canOverrideAccessModifiers());
    }

    public abstract Class<?> getActiveView();

    public abstract AnnotationIntrospector getAnnotationIntrospector();

    public abstract Object getAttribute(Object var1);

    public abstract MapperConfig<?> getConfig();

    public abstract TypeFactory getTypeFactory();

    public final boolean isEnabled(MapperFeature mapperFeature) {
        return this.getConfig().isEnabled(mapperFeature);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ObjectIdGenerator<?> objectIdGeneratorInstance(Annotated objectIdGenerator, ObjectIdInfo objectIdInfo) throws JsonMappingException {
        Class<? extends ObjectIdGenerator<?>> class_ = objectIdInfo.getGeneratorType();
        MapperConfig<?> mapperConfig = this.getConfig();
        Object object = mapperConfig.getHandlerInstantiator();
        objectIdGenerator = object == null ? null : object.objectIdGeneratorInstance(mapperConfig, (Annotated)((Object)objectIdGenerator), class_);
        object = objectIdGenerator;
        if (objectIdGenerator == null) {
            object = ClassUtil.createInstance(class_, mapperConfig.canOverrideAccessModifiers());
        }
        return object.forScope(objectIdInfo.getScope());
    }

    /*
     * Enabled aggressive block sorting
     */
    public ObjectIdResolver objectIdResolverInstance(Annotated object, ObjectIdInfo object2) {
        Class<? extends ObjectIdResolver> class_ = object2.getResolverType();
        MapperConfig<?> mapperConfig = this.getConfig();
        object2 = mapperConfig.getHandlerInstantiator();
        object = object2 == null ? null : object2.resolverIdGeneratorInstance(mapperConfig, (Annotated)object, class_);
        object2 = object;
        if (object != null) return object2;
        return ClassUtil.createInstance(class_, mapperConfig.canOverrideAccessModifiers());
    }

    public abstract DatabindContext setAttribute(Object var1, Object var2);
}

