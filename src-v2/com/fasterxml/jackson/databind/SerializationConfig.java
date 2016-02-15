/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class SerializationConfig
extends MapperConfigBase<SerializationFeature, SerializationConfig>
implements Serializable {
    private static final long serialVersionUID = -1278867172535832879L;
    protected final FilterProvider _filterProvider;
    protected final int _serFeatures;
    protected JsonInclude.Include _serializationInclusion = null;

    private SerializationConfig(SerializationConfig serializationConfig, int n2, int n3) {
        super(serializationConfig, n2);
        this._serFeatures = n3;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._filterProvider = serializationConfig._filterProvider;
    }

    private SerializationConfig(SerializationConfig serializationConfig, JsonInclude.Include include) {
        super(serializationConfig);
        this._serFeatures = serializationConfig._serFeatures;
        this._serializationInclusion = include;
        this._filterProvider = serializationConfig._filterProvider;
    }

    private SerializationConfig(SerializationConfig serializationConfig, BaseSettings baseSettings) {
        super(serializationConfig, baseSettings);
        this._serFeatures = serializationConfig._serFeatures;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._filterProvider = serializationConfig._filterProvider;
    }

    protected SerializationConfig(SerializationConfig serializationConfig, ContextAttributes contextAttributes) {
        super(serializationConfig, contextAttributes);
        this._serFeatures = serializationConfig._serFeatures;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._filterProvider = serializationConfig._filterProvider;
    }

    private SerializationConfig(SerializationConfig serializationConfig, SubtypeResolver subtypeResolver) {
        super(serializationConfig, subtypeResolver);
        this._serFeatures = serializationConfig._serFeatures;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._filterProvider = serializationConfig._filterProvider;
    }

    private SerializationConfig(SerializationConfig serializationConfig, FilterProvider filterProvider) {
        super(serializationConfig);
        this._serFeatures = serializationConfig._serFeatures;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._filterProvider = filterProvider;
    }

    private SerializationConfig(SerializationConfig serializationConfig, Class<?> class_) {
        super(serializationConfig, class_);
        this._serFeatures = serializationConfig._serFeatures;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._filterProvider = serializationConfig._filterProvider;
    }

    private SerializationConfig(SerializationConfig serializationConfig, String string2) {
        super(serializationConfig, string2);
        this._serFeatures = serializationConfig._serFeatures;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._filterProvider = serializationConfig._filterProvider;
    }

    protected SerializationConfig(SerializationConfig serializationConfig, Map<ClassKey, Class<?>> map) {
        super(serializationConfig, map);
        this._serFeatures = serializationConfig._serFeatures;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._filterProvider = serializationConfig._filterProvider;
    }

    public SerializationConfig(BaseSettings baseSettings, SubtypeResolver subtypeResolver, Map<ClassKey, Class<?>> map) {
        super(baseSettings, subtypeResolver, map);
        this._serFeatures = SerializationConfig.collectFeatureDefaults(SerializationFeature.class);
        this._filterProvider = null;
    }

    private final SerializationConfig _withBase(BaseSettings baseSettings) {
        if (this._base == baseSettings) {
            return this;
        }
        return new SerializationConfig(this, baseSettings);
    }

    @Override
    public AnnotationIntrospector getAnnotationIntrospector() {
        if (this.isEnabled(MapperFeature.USE_ANNOTATIONS)) {
            return super.getAnnotationIntrospector();
        }
        return AnnotationIntrospector.nopInstance();
    }

    @Override
    public VisibilityChecker<?> getDefaultVisibilityChecker() {
        VisibilityChecker visibilityChecker;
        VisibilityChecker visibilityChecker2 = visibilityChecker = super.getDefaultVisibilityChecker();
        if (!this.isEnabled(MapperFeature.AUTO_DETECT_GETTERS)) {
            visibilityChecker2 = visibilityChecker.withGetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        visibilityChecker = visibilityChecker2;
        if (!this.isEnabled(MapperFeature.AUTO_DETECT_IS_GETTERS)) {
            visibilityChecker = visibilityChecker2.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        visibilityChecker2 = visibilityChecker;
        if (!this.isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
            visibilityChecker2 = visibilityChecker.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
        }
        return visibilityChecker2;
    }

    public FilterProvider getFilterProvider() {
        return this._filterProvider;
    }

    public final int getSerializationFeatures() {
        return this._serFeatures;
    }

    public JsonInclude.Include getSerializationInclusion() {
        if (this._serializationInclusion != null) {
            return this._serializationInclusion;
        }
        return JsonInclude.Include.ALWAYS;
    }

    public final boolean hasSerializationFeatures(int n2) {
        if ((this._serFeatures & n2) == n2) {
            return true;
        }
        return false;
    }

    public <T extends BeanDescription> T introspect(JavaType javaType) {
        return (T)this.getClassIntrospector().forSerialization(this, javaType, this);
    }

    @Override
    public BeanDescription introspectClassAnnotations(JavaType javaType) {
        return this.getClassIntrospector().forClassAnnotations(this, javaType, this);
    }

    @Override
    public BeanDescription introspectDirectClassAnnotations(JavaType javaType) {
        return this.getClassIntrospector().forDirectClassAnnotations(this, javaType, this);
    }

    public final boolean isEnabled(SerializationFeature serializationFeature) {
        if ((this._serFeatures & serializationFeature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "[SerializationConfig: flags=0x" + Integer.toHexString(this._serFeatures) + "]";
    }

    @Override
    public boolean useRootWrapping() {
        if (this._rootName != null) {
            if (this._rootName.length() > 0) {
                return true;
            }
            return false;
        }
        return this.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Override
    public SerializationConfig with(Base64Variant base64Variant) {
        return this._withBase(this._base.with(base64Variant));
    }

    @Override
    public SerializationConfig with(AnnotationIntrospector annotationIntrospector) {
        return this._withBase(this._base.withAnnotationIntrospector(annotationIntrospector));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public SerializationConfig with(MapperFeature mapperFeature, boolean bl2) {
        int n2 = bl2 ? this._mapperFeatures | mapperFeature.getMask() : this._mapperFeatures & ~ mapperFeature.getMask();
        if (n2 == this._mapperFeatures) {
            return this;
        }
        return new SerializationConfig(this, n2, this._serFeatures);
    }

    @Override
    public SerializationConfig with(PropertyNamingStrategy propertyNamingStrategy) {
        return this._withBase(this._base.withPropertyNamingStrategy(propertyNamingStrategy));
    }

    public SerializationConfig with(SerializationFeature serializationFeature) {
        int n2 = this._serFeatures | serializationFeature.getMask();
        if (n2 == this._serFeatures) {
            return this;
        }
        return new SerializationConfig(this, this._mapperFeatures, n2);
    }

    public /* varargs */ SerializationConfig with(SerializationFeature serializationFeature, SerializationFeature ... arrserializationFeature) {
        int n2 = this._serFeatures | serializationFeature.getMask();
        int n3 = arrserializationFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 |= arrserializationFeature[i2].getMask();
        }
        if (n2 == this._serFeatures) {
            return this;
        }
        return new SerializationConfig(this, this._mapperFeatures, n2);
    }

    @Override
    public SerializationConfig with(ContextAttributes contextAttributes) {
        if (contextAttributes == this._attributes) {
            return this;
        }
        return new SerializationConfig(this, contextAttributes);
    }

    @Override
    public SerializationConfig with(HandlerInstantiator handlerInstantiator) {
        return this._withBase(this._base.withHandlerInstantiator(handlerInstantiator));
    }

    @Override
    public SerializationConfig with(ClassIntrospector classIntrospector) {
        return this._withBase(this._base.withClassIntrospector(classIntrospector));
    }

    @Override
    public SerializationConfig with(VisibilityChecker<?> visibilityChecker) {
        return this._withBase(this._base.withVisibilityChecker(visibilityChecker));
    }

    @Override
    public SerializationConfig with(SubtypeResolver subtypeResolver) {
        if (subtypeResolver == this._subtypeResolver) {
            return this;
        }
        return new SerializationConfig(this, subtypeResolver);
    }

    @Override
    public SerializationConfig with(TypeResolverBuilder<?> typeResolverBuilder) {
        return this._withBase(this._base.withTypeResolverBuilder(typeResolverBuilder));
    }

    @Override
    public SerializationConfig with(TypeFactory typeFactory) {
        return this._withBase(this._base.withTypeFactory(typeFactory));
    }

    @Override
    public SerializationConfig with(DateFormat dateFormat) {
        SerializationConfig serializationConfig = new SerializationConfig(this, this._base.withDateFormat(dateFormat));
        if (dateFormat == null) {
            return serializationConfig.with(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return serializationConfig.without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public SerializationConfig with(Locale locale) {
        return this._withBase(this._base.with(locale));
    }

    @Override
    public SerializationConfig with(TimeZone timeZone) {
        return this._withBase(this._base.with(timeZone));
    }

    @Override
    public /* varargs */ SerializationConfig with(MapperFeature ... arrmapperFeature) {
        int n2 = this._mapperFeatures;
        int n3 = arrmapperFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 |= arrmapperFeature[i2].getMask();
        }
        if (n2 == this._mapperFeatures) {
            return this;
        }
        return new SerializationConfig(this, n2, this._serFeatures);
    }

    @Override
    public SerializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return this._withBase(this._base.withAppendedAnnotationIntrospector(annotationIntrospector));
    }

    public /* varargs */ SerializationConfig withFeatures(SerializationFeature ... arrserializationFeature) {
        int n2 = this._serFeatures;
        int n3 = arrserializationFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 |= arrserializationFeature[i2].getMask();
        }
        if (n2 == this._serFeatures) {
            return this;
        }
        return new SerializationConfig(this, this._mapperFeatures, n2);
    }

    public SerializationConfig withFilters(FilterProvider filterProvider) {
        if (filterProvider == this._filterProvider) {
            return this;
        }
        return new SerializationConfig(this, filterProvider);
    }

    @Override
    public SerializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return this._withBase(this._base.withInsertedAnnotationIntrospector(annotationIntrospector));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public SerializationConfig withRootName(String string2) {
        if (string2 == null ? this._rootName == null : string2.equals(this._rootName)) {
            return this;
        }
        return new SerializationConfig(this, string2);
    }

    public SerializationConfig withSerializationInclusion(JsonInclude.Include include) {
        if (this._serializationInclusion == include) {
            return this;
        }
        return new SerializationConfig(this, include);
    }

    @Override
    public SerializationConfig withView(Class<?> class_) {
        if (this._view == class_) {
            return this;
        }
        return new SerializationConfig(this, class_);
    }

    @Override
    public SerializationConfig withVisibility(PropertyAccessor propertyAccessor, JsonAutoDetect.Visibility visibility) {
        return this._withBase(this._base.withVisibility(propertyAccessor, visibility));
    }

    public SerializationConfig without(SerializationFeature serializationFeature) {
        int n2 = this._serFeatures & ~ serializationFeature.getMask();
        if (n2 == this._serFeatures) {
            return this;
        }
        return new SerializationConfig(this, this._mapperFeatures, n2);
    }

    public /* varargs */ SerializationConfig without(SerializationFeature serializationFeature, SerializationFeature ... arrserializationFeature) {
        int n2 = this._serFeatures & ~ serializationFeature.getMask();
        int n3 = arrserializationFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 &= ~ arrserializationFeature[i2].getMask();
        }
        if (n2 == this._serFeatures) {
            return this;
        }
        return new SerializationConfig(this, this._mapperFeatures, n2);
    }

    @Override
    public /* varargs */ SerializationConfig without(MapperFeature ... arrmapperFeature) {
        int n2 = this._mapperFeatures;
        int n3 = arrmapperFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 &= ~ arrmapperFeature[i2].getMask();
        }
        if (n2 == this._mapperFeatures) {
            return this;
        }
        return new SerializationConfig(this, n2, this._serFeatures);
    }

    public /* varargs */ SerializationConfig withoutFeatures(SerializationFeature ... arrserializationFeature) {
        int n2 = this._serFeatures;
        int n3 = arrserializationFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 &= ~ arrserializationFeature[i2].getMask();
        }
        if (n2 == this._serFeatures) {
            return this;
        }
        return new SerializationConfig(this, this._mapperFeatures, n2);
    }
}

