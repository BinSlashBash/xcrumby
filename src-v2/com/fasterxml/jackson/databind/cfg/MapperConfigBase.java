/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
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

public abstract class MapperConfigBase<CFG extends ConfigFeature, T extends MapperConfigBase<CFG, T>>
extends MapperConfig<T>
implements Serializable {
    private static final int DEFAULT_MAPPER_FEATURES = MapperConfigBase.collectFeatureDefaults(MapperFeature.class);
    private static final long serialVersionUID = 6062961959359172474L;
    protected final ContextAttributes _attributes;
    protected final Map<ClassKey, Class<?>> _mixInAnnotations;
    protected final String _rootName;
    protected final SubtypeResolver _subtypeResolver;
    protected final Class<?> _view;

    protected MapperConfigBase(BaseSettings baseSettings, SubtypeResolver subtypeResolver, Map<ClassKey, Class<?>> map) {
        super(baseSettings, DEFAULT_MAPPER_FEATURES);
        this._mixInAnnotations = map;
        this._subtypeResolver = subtypeResolver;
        this._rootName = null;
        this._view = null;
        this._attributes = ContextAttributes.getEmpty();
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase) {
        super(mapperConfigBase);
        this._mixInAnnotations = mapperConfigBase._mixInAnnotations;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, int n2) {
        super(mapperConfigBase._base, n2);
        this._mixInAnnotations = mapperConfigBase._mixInAnnotations;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, BaseSettings baseSettings) {
        super(baseSettings, mapperConfigBase._mapperFeatures);
        this._mixInAnnotations = mapperConfigBase._mixInAnnotations;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, ContextAttributes contextAttributes) {
        super(mapperConfigBase);
        this._mixInAnnotations = mapperConfigBase._mixInAnnotations;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = contextAttributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, SubtypeResolver subtypeResolver) {
        super(mapperConfigBase);
        this._mixInAnnotations = mapperConfigBase._mixInAnnotations;
        this._subtypeResolver = subtypeResolver;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, Class<?> class_) {
        super(mapperConfigBase);
        this._mixInAnnotations = mapperConfigBase._mixInAnnotations;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootName = mapperConfigBase._rootName;
        this._view = class_;
        this._attributes = mapperConfigBase._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, String string2) {
        super(mapperConfigBase);
        this._mixInAnnotations = mapperConfigBase._mixInAnnotations;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootName = string2;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, Map<ClassKey, Class<?>> map) {
        super(mapperConfigBase);
        this._mixInAnnotations = map;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
    }

    @Override
    public final Class<?> findMixInClassFor(Class<?> class_) {
        if (this._mixInAnnotations == null) {
            return null;
        }
        return this._mixInAnnotations.get(new ClassKey(class_));
    }

    @Override
    public final Class<?> getActiveView() {
        return this._view;
    }

    @Override
    public final ContextAttributes getAttributes() {
        return this._attributes;
    }

    public final String getRootName() {
        return this._rootName;
    }

    @Override
    public final SubtypeResolver getSubtypeResolver() {
        return this._subtypeResolver;
    }

    public final int mixInCount() {
        if (this._mixInAnnotations == null) {
            return 0;
        }
        return this._mixInAnnotations.size();
    }

    public abstract T with(Base64Variant var1);

    public abstract T with(AnnotationIntrospector var1);

    public abstract T with(PropertyNamingStrategy var1);

    public abstract T with(ContextAttributes var1);

    public abstract T with(HandlerInstantiator var1);

    public abstract T with(ClassIntrospector var1);

    public abstract T with(VisibilityChecker<?> var1);

    public abstract T with(SubtypeResolver var1);

    public abstract T with(TypeResolverBuilder<?> var1);

    public abstract T with(TypeFactory var1);

    public abstract T with(DateFormat var1);

    public abstract T with(Locale var1);

    public abstract T with(TimeZone var1);

    public abstract T withAppendedAnnotationIntrospector(AnnotationIntrospector var1);

    public T withAttribute(Object object, Object object2) {
        return this.with(this.getAttributes().withSharedAttribute(object, object2));
    }

    public T withAttributes(Map<Object, Object> map) {
        return this.with(this.getAttributes().withSharedAttributes(map));
    }

    public abstract T withInsertedAnnotationIntrospector(AnnotationIntrospector var1);

    public abstract T withRootName(String var1);

    public abstract T withView(Class<?> var1);

    public abstract T withVisibility(PropertyAccessor var1, JsonAutoDetect.Visibility var2);

    public T withoutAttribute(Object object) {
        return this.with(this.getAttributes().withoutSharedAttribute(object));
    }
}

