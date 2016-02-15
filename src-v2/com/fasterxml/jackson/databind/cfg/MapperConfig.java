/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

public abstract class MapperConfig<T extends MapperConfig<T>>
implements ClassIntrospector.MixInResolver,
Serializable {
    private static final long serialVersionUID = 8891625428805876137L;
    protected final BaseSettings _base;
    protected final int _mapperFeatures;

    protected MapperConfig(BaseSettings baseSettings, int n2) {
        this._base = baseSettings;
        this._mapperFeatures = n2;
    }

    protected MapperConfig(MapperConfig<T> mapperConfig) {
        this._base = mapperConfig._base;
        this._mapperFeatures = mapperConfig._mapperFeatures;
    }

    public static <F extends Enum<F>> int collectFeatureDefaults(Class<F> arrenum) {
        int n2 = 0;
        for (Enum enum_ : (Enum[])arrenum.getEnumConstants()) {
            int n3 = n2;
            if (((ConfigFeature)((Object)enum_)).enabledByDefault()) {
                n3 = n2 | ((ConfigFeature)((Object)enum_)).getMask();
            }
            n2 = n3;
        }
        return n2;
    }

    public final boolean canOverrideAccessModifiers() {
        return this.isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);
    }

    public SerializableString compileString(String string2) {
        return new SerializedString(string2);
    }

    public JavaType constructSpecializedType(JavaType javaType, Class<?> class_) {
        return this.getTypeFactory().constructSpecializedType(javaType, class_);
    }

    public final JavaType constructType(TypeReference<?> typeReference) {
        return this.getTypeFactory().constructType(typeReference.getType(), (TypeBindings)null);
    }

    public final JavaType constructType(Class<?> class_) {
        return this.getTypeFactory().constructType(class_, (TypeBindings)null);
    }

    public abstract Class<?> getActiveView();

    public AnnotationIntrospector getAnnotationIntrospector() {
        return this._base.getAnnotationIntrospector();
    }

    public abstract ContextAttributes getAttributes();

    public Base64Variant getBase64Variant() {
        return this._base.getBase64Variant();
    }

    public ClassIntrospector getClassIntrospector() {
        return this._base.getClassIntrospector();
    }

    public final DateFormat getDateFormat() {
        return this._base.getDateFormat();
    }

    public final TypeResolverBuilder<?> getDefaultTyper(JavaType javaType) {
        return this._base.getTypeResolverBuilder();
    }

    public VisibilityChecker<?> getDefaultVisibilityChecker() {
        return this._base.getVisibilityChecker();
    }

    public final HandlerInstantiator getHandlerInstantiator() {
        return this._base.getHandlerInstantiator();
    }

    public final Locale getLocale() {
        return this._base.getLocale();
    }

    public final PropertyNamingStrategy getPropertyNamingStrategy() {
        return this._base.getPropertyNamingStrategy();
    }

    public abstract SubtypeResolver getSubtypeResolver();

    public final TimeZone getTimeZone() {
        return this._base.getTimeZone();
    }

    public final TypeFactory getTypeFactory() {
        return this._base.getTypeFactory();
    }

    public final boolean hasMapperFeatures(int n2) {
        if ((this._mapperFeatures & n2) == n2) {
            return true;
        }
        return false;
    }

    public abstract BeanDescription introspectClassAnnotations(JavaType var1);

    public BeanDescription introspectClassAnnotations(Class<?> class_) {
        return this.introspectClassAnnotations(this.constructType(class_));
    }

    public abstract BeanDescription introspectDirectClassAnnotations(JavaType var1);

    public BeanDescription introspectDirectClassAnnotations(Class<?> class_) {
        return this.introspectDirectClassAnnotations(this.constructType(class_));
    }

    public final boolean isAnnotationProcessingEnabled() {
        return this.isEnabled(MapperFeature.USE_ANNOTATIONS);
    }

    public final boolean isEnabled(MapperFeature mapperFeature) {
        if ((this._mapperFeatures & mapperFeature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    public final boolean shouldSortPropertiesAlphabetically() {
        return this.isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    }

    public TypeIdResolver typeIdResolverInstance(Annotated object, Class<? extends TypeIdResolver> class_) {
        HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null && (object = handlerInstantiator.typeIdResolverInstance(this, (Annotated)object, class_)) != null) {
            return object;
        }
        return ClassUtil.createInstance(class_, this.canOverrideAccessModifiers());
    }

    public TypeResolverBuilder<?> typeResolverBuilderInstance(Annotated object, Class<? extends TypeResolverBuilder<?>> class_) {
        HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null && (object = handlerInstantiator.typeResolverBuilderInstance(this, (Annotated)object, class_)) != null) {
            return object;
        }
        return ClassUtil.createInstance(class_, this.canOverrideAccessModifiers());
    }

    public abstract boolean useRootWrapping();

    public abstract T with(MapperFeature var1, boolean var2);

    public /* varargs */ abstract T with(MapperFeature ... var1);

    public /* varargs */ abstract T without(MapperFeature ... var1);
}

