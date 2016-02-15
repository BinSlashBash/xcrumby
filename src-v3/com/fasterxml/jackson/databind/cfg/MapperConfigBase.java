package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public abstract class MapperConfigBase<CFG extends ConfigFeature, T extends MapperConfigBase<CFG, T>> extends MapperConfig<T> implements Serializable {
    private static final int DEFAULT_MAPPER_FEATURES;
    private static final long serialVersionUID = 6062961959359172474L;
    protected final ContextAttributes _attributes;
    protected final Map<ClassKey, Class<?>> _mixInAnnotations;
    protected final String _rootName;
    protected final SubtypeResolver _subtypeResolver;
    protected final Class<?> _view;

    public abstract T with(Base64Variant base64Variant);

    public abstract T with(AnnotationIntrospector annotationIntrospector);

    public abstract T with(PropertyNamingStrategy propertyNamingStrategy);

    public abstract T with(ContextAttributes contextAttributes);

    public abstract T with(HandlerInstantiator handlerInstantiator);

    public abstract T with(ClassIntrospector classIntrospector);

    public abstract T with(VisibilityChecker<?> visibilityChecker);

    public abstract T with(SubtypeResolver subtypeResolver);

    public abstract T with(TypeResolverBuilder<?> typeResolverBuilder);

    public abstract T with(TypeFactory typeFactory);

    public abstract T with(DateFormat dateFormat);

    public abstract T with(Locale locale);

    public abstract T with(TimeZone timeZone);

    public abstract T withAppendedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector);

    public abstract T withInsertedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector);

    public abstract T withRootName(String str);

    public abstract T withView(Class<?> cls);

    public abstract T withVisibility(PropertyAccessor propertyAccessor, Visibility visibility);

    static {
        DEFAULT_MAPPER_FEATURES = MapperConfig.collectFeatureDefaults(MapperFeature.class);
    }

    protected MapperConfigBase(BaseSettings base, SubtypeResolver str, Map<ClassKey, Class<?>> mixins) {
        super(base, DEFAULT_MAPPER_FEATURES);
        this._mixInAnnotations = mixins;
        this._subtypeResolver = str;
        this._rootName = null;
        this._view = null;
        this._attributes = ContextAttributes.getEmpty();
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src) {
        super(src);
        this._mixInAnnotations = src._mixInAnnotations;
        this._subtypeResolver = src._subtypeResolver;
        this._rootName = src._rootName;
        this._view = src._view;
        this._attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, BaseSettings base) {
        super(base, src._mapperFeatures);
        this._mixInAnnotations = src._mixInAnnotations;
        this._subtypeResolver = src._subtypeResolver;
        this._rootName = src._rootName;
        this._view = src._view;
        this._attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, int mapperFeatures) {
        super(src._base, mapperFeatures);
        this._mixInAnnotations = src._mixInAnnotations;
        this._subtypeResolver = src._subtypeResolver;
        this._rootName = src._rootName;
        this._view = src._view;
        this._attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, SubtypeResolver str) {
        super(src);
        this._mixInAnnotations = src._mixInAnnotations;
        this._subtypeResolver = str;
        this._rootName = src._rootName;
        this._view = src._view;
        this._attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, String rootName) {
        super(src);
        this._mixInAnnotations = src._mixInAnnotations;
        this._subtypeResolver = src._subtypeResolver;
        this._rootName = rootName;
        this._view = src._view;
        this._attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, Class<?> view) {
        super(src);
        this._mixInAnnotations = src._mixInAnnotations;
        this._subtypeResolver = src._subtypeResolver;
        this._rootName = src._rootName;
        this._view = view;
        this._attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, Map<ClassKey, Class<?>> mixins) {
        super(src);
        this._mixInAnnotations = mixins;
        this._subtypeResolver = src._subtypeResolver;
        this._rootName = src._rootName;
        this._view = src._view;
        this._attributes = src._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> src, ContextAttributes attr) {
        super(src);
        this._mixInAnnotations = src._mixInAnnotations;
        this._subtypeResolver = src._subtypeResolver;
        this._rootName = src._rootName;
        this._view = src._view;
        this._attributes = attr;
    }

    public T withAttributes(Map<Object, Object> attributes) {
        return with(getAttributes().withSharedAttributes(attributes));
    }

    public T withAttribute(Object key, Object value) {
        return with(getAttributes().withSharedAttribute(key, value));
    }

    public T withoutAttribute(Object key) {
        return with(getAttributes().withoutSharedAttribute(key));
    }

    public final SubtypeResolver getSubtypeResolver() {
        return this._subtypeResolver;
    }

    public final String getRootName() {
        return this._rootName;
    }

    public final Class<?> getActiveView() {
        return this._view;
    }

    public final ContextAttributes getAttributes() {
        return this._attributes;
    }

    public final Class<?> findMixInClassFor(Class<?> cls) {
        return this._mixInAnnotations == null ? null : (Class) this._mixInAnnotations.get(new ClassKey(cls));
    }

    public final int mixInCount() {
        return this._mixInAnnotations == null ? DEFAULT_MAPPER_FEATURES : this._mixInAnnotations.size();
    }
}
