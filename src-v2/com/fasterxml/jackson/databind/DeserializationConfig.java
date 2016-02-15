/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.LinkedNode;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class DeserializationConfig
extends MapperConfigBase<DeserializationFeature, DeserializationConfig>
implements Serializable {
    private static final long serialVersionUID = -4227480407273773599L;
    protected final int _deserFeatures;
    protected final JsonNodeFactory _nodeFactory;
    protected final LinkedNode<DeserializationProblemHandler> _problemHandlers;

    private DeserializationConfig(DeserializationConfig deserializationConfig, int n2, int n3) {
        super(deserializationConfig, n2);
        this._deserFeatures = n3;
        this._nodeFactory = deserializationConfig._nodeFactory;
        this._problemHandlers = deserializationConfig._problemHandlers;
    }

    private DeserializationConfig(DeserializationConfig deserializationConfig, BaseSettings baseSettings) {
        super(deserializationConfig, baseSettings);
        this._deserFeatures = deserializationConfig._deserFeatures;
        this._nodeFactory = deserializationConfig._nodeFactory;
        this._problemHandlers = deserializationConfig._problemHandlers;
    }

    protected DeserializationConfig(DeserializationConfig deserializationConfig, ContextAttributes contextAttributes) {
        super(deserializationConfig, contextAttributes);
        this._deserFeatures = deserializationConfig._deserFeatures;
        this._problemHandlers = deserializationConfig._problemHandlers;
        this._nodeFactory = deserializationConfig._nodeFactory;
    }

    private DeserializationConfig(DeserializationConfig deserializationConfig, SubtypeResolver subtypeResolver) {
        super(deserializationConfig, subtypeResolver);
        this._deserFeatures = deserializationConfig._deserFeatures;
        this._nodeFactory = deserializationConfig._nodeFactory;
        this._problemHandlers = deserializationConfig._problemHandlers;
    }

    private DeserializationConfig(DeserializationConfig deserializationConfig, JsonNodeFactory jsonNodeFactory) {
        super(deserializationConfig);
        this._deserFeatures = deserializationConfig._deserFeatures;
        this._problemHandlers = deserializationConfig._problemHandlers;
        this._nodeFactory = jsonNodeFactory;
    }

    private DeserializationConfig(DeserializationConfig deserializationConfig, LinkedNode<DeserializationProblemHandler> linkedNode) {
        super(deserializationConfig);
        this._deserFeatures = deserializationConfig._deserFeatures;
        this._problemHandlers = linkedNode;
        this._nodeFactory = deserializationConfig._nodeFactory;
    }

    private DeserializationConfig(DeserializationConfig deserializationConfig, Class<?> class_) {
        super(deserializationConfig, class_);
        this._deserFeatures = deserializationConfig._deserFeatures;
        this._problemHandlers = deserializationConfig._problemHandlers;
        this._nodeFactory = deserializationConfig._nodeFactory;
    }

    private DeserializationConfig(DeserializationConfig deserializationConfig, String string2) {
        super(deserializationConfig, string2);
        this._deserFeatures = deserializationConfig._deserFeatures;
        this._problemHandlers = deserializationConfig._problemHandlers;
        this._nodeFactory = deserializationConfig._nodeFactory;
    }

    protected DeserializationConfig(DeserializationConfig deserializationConfig, Map<ClassKey, Class<?>> map) {
        super(deserializationConfig, map);
        this._deserFeatures = deserializationConfig._deserFeatures;
        this._problemHandlers = deserializationConfig._problemHandlers;
        this._nodeFactory = deserializationConfig._nodeFactory;
    }

    public DeserializationConfig(BaseSettings baseSettings, SubtypeResolver subtypeResolver, Map<ClassKey, Class<?>> map) {
        super(baseSettings, subtypeResolver, map);
        this._deserFeatures = DeserializationConfig.collectFeatureDefaults(DeserializationFeature.class);
        this._nodeFactory = JsonNodeFactory.instance;
        this._problemHandlers = null;
    }

    private final DeserializationConfig _withBase(BaseSettings baseSettings) {
        if (this._base == baseSettings) {
            return this;
        }
        return new DeserializationConfig(this, baseSettings);
    }

    public TypeDeserializer findTypeDeserializer(JavaType javaType) throws JsonMappingException {
        Object object = this.introspectClassAnnotations(javaType.getRawClass()).getClassInfo();
        Object object2 = this.getAnnotationIntrospector().findTypeResolver(this, (AnnotatedClass)object, javaType);
        Collection<NamedType> collection = null;
        if (object2 == null) {
            object2 = object = this.getDefaultTyper(javaType);
            if (object == null) {
                return null;
            }
        } else {
            collection = this.getSubtypeResolver().collectAndResolveSubtypes((AnnotatedClass)object, this, this.getAnnotationIntrospector());
        }
        return object2.buildTypeDeserializer(this, javaType, collection);
    }

    @Override
    public AnnotationIntrospector getAnnotationIntrospector() {
        if (this.isEnabled(MapperFeature.USE_ANNOTATIONS)) {
            return super.getAnnotationIntrospector();
        }
        return NopAnnotationIntrospector.instance;
    }

    protected BaseSettings getBaseSettings() {
        return this._base;
    }

    @Override
    public VisibilityChecker<?> getDefaultVisibilityChecker() {
        VisibilityChecker visibilityChecker;
        VisibilityChecker visibilityChecker2 = visibilityChecker = super.getDefaultVisibilityChecker();
        if (!this.isEnabled(MapperFeature.AUTO_DETECT_SETTERS)) {
            visibilityChecker2 = visibilityChecker.withSetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        visibilityChecker = visibilityChecker2;
        if (!this.isEnabled(MapperFeature.AUTO_DETECT_CREATORS)) {
            visibilityChecker = visibilityChecker2.withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
        }
        visibilityChecker2 = visibilityChecker;
        if (!this.isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
            visibilityChecker2 = visibilityChecker.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
        }
        return visibilityChecker2;
    }

    public final int getDeserializationFeatures() {
        return this._deserFeatures;
    }

    public final JsonNodeFactory getNodeFactory() {
        return this._nodeFactory;
    }

    public LinkedNode<DeserializationProblemHandler> getProblemHandlers() {
        return this._problemHandlers;
    }

    public final boolean hasDeserializationFeatures(int n2) {
        if ((this._deserFeatures & n2) == n2) {
            return true;
        }
        return false;
    }

    public <T extends BeanDescription> T introspect(JavaType javaType) {
        return (T)this.getClassIntrospector().forDeserialization(this, javaType, this);
    }

    @Override
    public BeanDescription introspectClassAnnotations(JavaType javaType) {
        return this.getClassIntrospector().forClassAnnotations(this, javaType, this);
    }

    @Override
    public BeanDescription introspectDirectClassAnnotations(JavaType javaType) {
        return this.getClassIntrospector().forDirectClassAnnotations(this, javaType, this);
    }

    public <T extends BeanDescription> T introspectForBuilder(JavaType javaType) {
        return (T)this.getClassIntrospector().forDeserializationWithBuilder(this, javaType, this);
    }

    public <T extends BeanDescription> T introspectForCreation(JavaType javaType) {
        return (T)this.getClassIntrospector().forCreation(this, javaType, this);
    }

    public final boolean isEnabled(DeserializationFeature deserializationFeature) {
        if ((this._deserFeatures & deserializationFeature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean useRootWrapping() {
        if (this._rootName != null) {
            if (this._rootName.length() > 0) {
                return true;
            }
            return false;
        }
        return this.isEnabled(DeserializationFeature.UNWRAP_ROOT_VALUE);
    }

    @Override
    public DeserializationConfig with(Base64Variant base64Variant) {
        return this._withBase(this._base.with(base64Variant));
    }

    @Override
    public DeserializationConfig with(AnnotationIntrospector annotationIntrospector) {
        return this._withBase(this._base.withAnnotationIntrospector(annotationIntrospector));
    }

    public DeserializationConfig with(DeserializationFeature deserializationFeature) {
        int n2 = this._deserFeatures | deserializationFeature.getMask();
        if (n2 == this._deserFeatures) {
            return this;
        }
        return new DeserializationConfig(this, this._mapperFeatures, n2);
    }

    public /* varargs */ DeserializationConfig with(DeserializationFeature deserializationFeature, DeserializationFeature ... arrdeserializationFeature) {
        int n2 = this._deserFeatures | deserializationFeature.getMask();
        int n3 = arrdeserializationFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 |= arrdeserializationFeature[i2].getMask();
        }
        if (n2 == this._deserFeatures) {
            return this;
        }
        return new DeserializationConfig(this, this._mapperFeatures, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public DeserializationConfig with(MapperFeature mapperFeature, boolean bl2) {
        int n2 = bl2 ? this._mapperFeatures | mapperFeature.getMask() : this._mapperFeatures & ~ mapperFeature.getMask();
        if (n2 == this._mapperFeatures) {
            return this;
        }
        return new DeserializationConfig(this, n2, this._deserFeatures);
    }

    @Override
    public DeserializationConfig with(PropertyNamingStrategy propertyNamingStrategy) {
        return this._withBase(this._base.withPropertyNamingStrategy(propertyNamingStrategy));
    }

    @Override
    public DeserializationConfig with(ContextAttributes contextAttributes) {
        if (contextAttributes == this._attributes) {
            return this;
        }
        return new DeserializationConfig(this, contextAttributes);
    }

    @Override
    public DeserializationConfig with(HandlerInstantiator handlerInstantiator) {
        return this._withBase(this._base.withHandlerInstantiator(handlerInstantiator));
    }

    @Override
    public DeserializationConfig with(ClassIntrospector classIntrospector) {
        return this._withBase(this._base.withClassIntrospector(classIntrospector));
    }

    @Override
    public DeserializationConfig with(VisibilityChecker<?> visibilityChecker) {
        return this._withBase(this._base.withVisibilityChecker(visibilityChecker));
    }

    @Override
    public DeserializationConfig with(SubtypeResolver subtypeResolver) {
        if (this._subtypeResolver == subtypeResolver) {
            return this;
        }
        return new DeserializationConfig(this, subtypeResolver);
    }

    @Override
    public DeserializationConfig with(TypeResolverBuilder<?> typeResolverBuilder) {
        return this._withBase(this._base.withTypeResolverBuilder(typeResolverBuilder));
    }

    public DeserializationConfig with(JsonNodeFactory jsonNodeFactory) {
        if (this._nodeFactory == jsonNodeFactory) {
            return this;
        }
        return new DeserializationConfig(this, jsonNodeFactory);
    }

    @Override
    public DeserializationConfig with(TypeFactory typeFactory) {
        return this._withBase(this._base.withTypeFactory(typeFactory));
    }

    @Override
    public DeserializationConfig with(DateFormat dateFormat) {
        return this._withBase(this._base.withDateFormat(dateFormat));
    }

    @Override
    public DeserializationConfig with(Locale locale) {
        return this._withBase(this._base.with(locale));
    }

    @Override
    public DeserializationConfig with(TimeZone timeZone) {
        return this._withBase(this._base.with(timeZone));
    }

    @Override
    public /* varargs */ DeserializationConfig with(MapperFeature ... arrmapperFeature) {
        int n2 = this._mapperFeatures;
        int n3 = arrmapperFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 |= arrmapperFeature[i2].getMask();
        }
        if (n2 == this._mapperFeatures) {
            return this;
        }
        return new DeserializationConfig(this, n2, this._deserFeatures);
    }

    @Override
    public DeserializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return this._withBase(this._base.withAppendedAnnotationIntrospector(annotationIntrospector));
    }

    public /* varargs */ DeserializationConfig withFeatures(DeserializationFeature ... arrdeserializationFeature) {
        int n2 = this._deserFeatures;
        int n3 = arrdeserializationFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 |= arrdeserializationFeature[i2].getMask();
        }
        if (n2 == this._deserFeatures) {
            return this;
        }
        return new DeserializationConfig(this, this._mapperFeatures, n2);
    }

    public DeserializationConfig withHandler(DeserializationProblemHandler deserializationProblemHandler) {
        if (LinkedNode.contains(this._problemHandlers, deserializationProblemHandler)) {
            return this;
        }
        return new DeserializationConfig(this, new LinkedNode<DeserializationProblemHandler>(deserializationProblemHandler, this._problemHandlers));
    }

    @Override
    public DeserializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return this._withBase(this._base.withInsertedAnnotationIntrospector(annotationIntrospector));
    }

    public DeserializationConfig withNoProblemHandlers() {
        if (this._problemHandlers == null) {
            return this;
        }
        return new DeserializationConfig(this, (LinkedNode)null);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public DeserializationConfig withRootName(String string2) {
        if (string2 == null ? this._rootName == null : string2.equals(this._rootName)) {
            return this;
        }
        return new DeserializationConfig(this, string2);
    }

    @Override
    public DeserializationConfig withView(Class<?> class_) {
        if (this._view == class_) {
            return this;
        }
        return new DeserializationConfig(this, class_);
    }

    @Override
    public DeserializationConfig withVisibility(PropertyAccessor propertyAccessor, JsonAutoDetect.Visibility visibility) {
        return this._withBase(this._base.withVisibility(propertyAccessor, visibility));
    }

    public DeserializationConfig without(DeserializationFeature deserializationFeature) {
        int n2 = this._deserFeatures & ~ deserializationFeature.getMask();
        if (n2 == this._deserFeatures) {
            return this;
        }
        return new DeserializationConfig(this, this._mapperFeatures, n2);
    }

    public /* varargs */ DeserializationConfig without(DeserializationFeature deserializationFeature, DeserializationFeature ... arrdeserializationFeature) {
        int n2 = this._deserFeatures & ~ deserializationFeature.getMask();
        int n3 = arrdeserializationFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 &= ~ arrdeserializationFeature[i2].getMask();
        }
        if (n2 == this._deserFeatures) {
            return this;
        }
        return new DeserializationConfig(this, this._mapperFeatures, n2);
    }

    @Override
    public /* varargs */ DeserializationConfig without(MapperFeature ... arrmapperFeature) {
        int n2 = this._mapperFeatures;
        int n3 = arrmapperFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 &= ~ arrmapperFeature[i2].getMask();
        }
        if (n2 == this._mapperFeatures) {
            return this;
        }
        return new DeserializationConfig(this, n2, this._deserFeatures);
    }

    public /* varargs */ DeserializationConfig withoutFeatures(DeserializationFeature ... arrdeserializationFeature) {
        int n2 = this._deserFeatures;
        int n3 = arrdeserializationFeature.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 &= ~ arrdeserializationFeature[i2].getMask();
        }
        if (n2 == this._deserFeatures) {
            return this;
        }
        return new DeserializationConfig(this, this._mapperFeatures, n2);
    }
}

