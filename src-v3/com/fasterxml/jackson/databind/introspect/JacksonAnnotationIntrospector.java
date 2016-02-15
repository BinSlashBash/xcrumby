package com.fasterxml.jackson.databind.introspect;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.JsonSerializer.None;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.std.RawSerializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class JacksonAnnotationIntrospector extends AnnotationIntrospector implements Serializable {
    private static final long serialVersionUID = 1;

    /* renamed from: com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector.1 */
    static /* synthetic */ class C01821 {
        static final /* synthetic */ int[] f7xfdbfedae;

        static {
            f7xfdbfedae = new int[Inclusion.values().length];
            try {
                f7xfdbfedae[Inclusion.ALWAYS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f7xfdbfedae[Inclusion.NON_NULL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f7xfdbfedae[Inclusion.NON_DEFAULT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f7xfdbfedae[Inclusion.NON_EMPTY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f7xfdbfedae[Inclusion.DEFAULT_INCLUSION.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public boolean isAnnotationBundle(Annotation ann) {
        return ann.annotationType().getAnnotation(JacksonAnnotationsInside.class) != null;
    }

    public PropertyName findRootName(AnnotatedClass ac) {
        JsonRootName ann = (JsonRootName) ac.getAnnotation(JsonRootName.class);
        if (ann == null) {
            return null;
        }
        String ns = ann.namespace();
        if (ns != null && ns.length() == 0) {
            ns = null;
        }
        return PropertyName.construct(ann.value(), ns);
    }

    public String[] findPropertiesToIgnore(Annotated ac) {
        JsonIgnoreProperties ignore = (JsonIgnoreProperties) ac.getAnnotation(JsonIgnoreProperties.class);
        return ignore == null ? null : ignore.value();
    }

    public Boolean findIgnoreUnknownProperties(AnnotatedClass ac) {
        JsonIgnoreProperties ignore = (JsonIgnoreProperties) ac.getAnnotation(JsonIgnoreProperties.class);
        return ignore == null ? null : Boolean.valueOf(ignore.ignoreUnknown());
    }

    public Boolean isIgnorableType(AnnotatedClass ac) {
        JsonIgnoreType ignore = (JsonIgnoreType) ac.getAnnotation(JsonIgnoreType.class);
        return ignore == null ? null : Boolean.valueOf(ignore.value());
    }

    @Deprecated
    public Object findFilterId(AnnotatedClass ac) {
        return _findFilterId(ac);
    }

    public Object findFilterId(Annotated a) {
        return _findFilterId(a);
    }

    protected final Object _findFilterId(Annotated a) {
        JsonFilter ann = (JsonFilter) a.getAnnotation(JsonFilter.class);
        if (ann != null) {
            String id = ann.value();
            if (id.length() > 0) {
                return id;
            }
        }
        return null;
    }

    public Object findNamingStrategy(AnnotatedClass ac) {
        JsonNaming ann = (JsonNaming) ac.getAnnotation(JsonNaming.class);
        return ann == null ? null : ann.value();
    }

    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
        JsonAutoDetect ann = (JsonAutoDetect) ac.getAnnotation(JsonAutoDetect.class);
        return ann == null ? checker : checker.with(ann);
    }

    public ReferenceProperty findReferenceType(AnnotatedMember member) {
        JsonManagedReference ref1 = (JsonManagedReference) member.getAnnotation(JsonManagedReference.class);
        if (ref1 != null) {
            return ReferenceProperty.managed(ref1.value());
        }
        JsonBackReference ref2 = (JsonBackReference) member.getAnnotation(JsonBackReference.class);
        if (ref2 != null) {
            return ReferenceProperty.back(ref2.value());
        }
        return null;
    }

    public NameTransformer findUnwrappingNameTransformer(AnnotatedMember member) {
        JsonUnwrapped ann = (JsonUnwrapped) member.getAnnotation(JsonUnwrapped.class);
        if (ann == null || !ann.enabled()) {
            return null;
        }
        return NameTransformer.simpleTransformer(ann.prefix(), ann.suffix());
    }

    public boolean hasIgnoreMarker(AnnotatedMember m) {
        return _isIgnorable(m);
    }

    public Boolean hasRequiredMarker(AnnotatedMember m) {
        JsonProperty ann = (JsonProperty) m.getAnnotation(JsonProperty.class);
        if (ann != null) {
            return Boolean.valueOf(ann.required());
        }
        return null;
    }

    public Object findInjectableValueId(AnnotatedMember m) {
        JacksonInject ann = (JacksonInject) m.getAnnotation(JacksonInject.class);
        if (ann == null) {
            return null;
        }
        Object id = ann.value();
        if (id.length() != 0) {
            return id;
        }
        if (!(m instanceof AnnotatedMethod)) {
            return m.getRawType().getName();
        }
        AnnotatedMethod am = (AnnotatedMethod) m;
        if (am.getParameterCount() == 0) {
            return m.getRawType().getName();
        }
        return am.getRawParameterType(0).getName();
    }

    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
        return _findTypeResolver(config, ac, baseType);
    }

    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
        if (baseType.isContainerType()) {
            return null;
        }
        return _findTypeResolver(config, am, baseType);
    }

    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType containerType) {
        if (containerType.isContainerType()) {
            return _findTypeResolver(config, am, containerType);
        }
        throw new IllegalArgumentException("Must call method with a container type (got " + containerType + ")");
    }

    public List<NamedType> findSubtypes(Annotated a) {
        JsonSubTypes t = (JsonSubTypes) a.getAnnotation(JsonSubTypes.class);
        if (t == null) {
            return null;
        }
        Type[] types = t.value();
        List<NamedType> result = new ArrayList(types.length);
        for (Type type : types) {
            result.add(new NamedType(type.value(), type.name()));
        }
        return result;
    }

    public String findTypeName(AnnotatedClass ac) {
        JsonTypeName tn = (JsonTypeName) ac.getAnnotation(JsonTypeName.class);
        return tn == null ? null : tn.value();
    }

    public Object findSerializer(Annotated a) {
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann != null) {
            Class<? extends JsonSerializer<?>> serClass = ann.using();
            if (serClass != None.class) {
                return serClass;
            }
        }
        JsonRawValue annRaw = (JsonRawValue) a.getAnnotation(JsonRawValue.class);
        if (annRaw == null || !annRaw.value()) {
            return null;
        }
        return new RawSerializer(a.getRawType());
    }

    public Class<? extends JsonSerializer<?>> findKeySerializer(Annotated a) {
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann != null) {
            Class<? extends JsonSerializer<?>> serClass = ann.keyUsing();
            if (serClass != None.class) {
                return serClass;
            }
        }
        return null;
    }

    public Class<? extends JsonSerializer<?>> findContentSerializer(Annotated a) {
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann != null) {
            Class<? extends JsonSerializer<?>> serClass = ann.contentUsing();
            if (serClass != None.class) {
                return serClass;
            }
        }
        return null;
    }

    public Object findNullSerializer(Annotated a) {
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann != null) {
            Class<? extends JsonSerializer<?>> serClass = ann.nullsUsing();
            if (serClass != None.class) {
                return serClass;
            }
        }
        return null;
    }

    public Include findSerializationInclusion(Annotated a, Include defValue) {
        JsonInclude inc = (JsonInclude) a.getAnnotation(JsonInclude.class);
        if (inc != null) {
            return inc.value();
        }
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann == null) {
            return defValue;
        }
        switch (C01821.f7xfdbfedae[ann.include().ordinal()]) {
            case Std.STD_FILE /*1*/:
                return Include.ALWAYS;
            case Std.STD_URL /*2*/:
                return Include.NON_NULL;
            case Std.STD_URI /*3*/:
                return Include.NON_DEFAULT;
            case Std.STD_CLASS /*4*/:
                return Include.NON_EMPTY;
            default:
                return defValue;
        }
    }

    public Class<?> findSerializationType(Annotated am) {
        JsonSerialize ann = (JsonSerialize) am.getAnnotation(JsonSerialize.class);
        return ann == null ? null : _classIfExplicit(ann.as());
    }

    public Class<?> findSerializationKeyType(Annotated am, JavaType baseType) {
        JsonSerialize ann = (JsonSerialize) am.getAnnotation(JsonSerialize.class);
        return ann == null ? null : _classIfExplicit(ann.keyAs());
    }

    public Class<?> findSerializationContentType(Annotated am, JavaType baseType) {
        JsonSerialize ann = (JsonSerialize) am.getAnnotation(JsonSerialize.class);
        return ann == null ? null : _classIfExplicit(ann.contentAs());
    }

    public Typing findSerializationTyping(Annotated a) {
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        return ann == null ? null : ann.typing();
    }

    public Object findSerializationConverter(Annotated a) {
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        return ann == null ? null : _classIfExplicit(ann.converter(), Converter.None.class);
    }

    public Object findSerializationContentConverter(AnnotatedMember a) {
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        return ann == null ? null : _classIfExplicit(ann.contentConverter(), Converter.None.class);
    }

    public Class<?>[] findViews(Annotated a) {
        JsonView ann = (JsonView) a.getAnnotation(JsonView.class);
        return ann == null ? null : ann.value();
    }

    public Boolean isTypeId(AnnotatedMember member) {
        return Boolean.valueOf(member.hasAnnotation(JsonTypeId.class));
    }

    public ObjectIdInfo findObjectIdInfo(Annotated ann) {
        JsonIdentityInfo info = (JsonIdentityInfo) ann.getAnnotation(JsonIdentityInfo.class);
        if (info == null || info.generator() == ObjectIdGenerators.None.class) {
            return null;
        }
        return new ObjectIdInfo(new PropertyName(info.property()), info.scope(), info.generator(), info.resolver());
    }

    public ObjectIdInfo findObjectReferenceInfo(Annotated ann, ObjectIdInfo objectIdInfo) {
        JsonIdentityReference ref = (JsonIdentityReference) ann.getAnnotation(JsonIdentityReference.class);
        if (ref != null) {
            return objectIdInfo.withAlwaysAsId(ref.alwaysAsId());
        }
        return objectIdInfo;
    }

    public Value findFormat(Annotated annotated) {
        JsonFormat ann = (JsonFormat) annotated.getAnnotation(JsonFormat.class);
        return ann == null ? null : new Value(ann);
    }

    public String findPropertyDescription(Annotated annotated) {
        JsonPropertyDescription desc = (JsonPropertyDescription) annotated.getAnnotation(JsonPropertyDescription.class);
        return desc == null ? null : desc.value();
    }

    public Integer findPropertyIndex(Annotated annotated) {
        JsonProperty ann = (JsonProperty) annotated.getAnnotation(JsonProperty.class);
        if (ann != null) {
            int ix = ann.index();
            if (ix != -1) {
                return Integer.valueOf(ix);
            }
        }
        return null;
    }

    public String findImplicitPropertyName(AnnotatedMember param) {
        return null;
    }

    public String[] findSerializationPropertyOrder(AnnotatedClass ac) {
        JsonPropertyOrder order = (JsonPropertyOrder) ac.getAnnotation(JsonPropertyOrder.class);
        return order == null ? null : order.value();
    }

    public Boolean findSerializationSortAlphabetically(Annotated ann) {
        return _findSortAlpha(ann);
    }

    @Deprecated
    public Boolean findSerializationSortAlphabetically(AnnotatedClass ac) {
        return _findSortAlpha(ac);
    }

    private final Boolean _findSortAlpha(Annotated ann) {
        JsonPropertyOrder order = (JsonPropertyOrder) ann.getAnnotation(JsonPropertyOrder.class);
        return order == null ? null : Boolean.valueOf(order.alphabetic());
    }

    public PropertyName findNameForSerialization(Annotated a) {
        String name;
        JsonGetter jg = (JsonGetter) a.getAnnotation(JsonGetter.class);
        if (jg != null) {
            name = jg.value();
        } else {
            JsonProperty pann = (JsonProperty) a.getAnnotation(JsonProperty.class);
            if (pann != null) {
                name = pann.value();
            } else if (!a.hasAnnotation(JsonSerialize.class) && !a.hasAnnotation(JsonView.class)) {
                return null;
            } else {
                name = UnsupportedUrlFragment.DISPLAY_NAME;
            }
        }
        if (name.length() == 0) {
            return PropertyName.USE_DEFAULT;
        }
        return new PropertyName(name);
    }

    public boolean hasAsValueAnnotation(AnnotatedMethod am) {
        JsonValue ann = (JsonValue) am.getAnnotation(JsonValue.class);
        return ann != null && ann.value();
    }

    public Class<? extends JsonDeserializer<?>> findDeserializer(Annotated a) {
        JsonDeserialize ann = (JsonDeserialize) a.getAnnotation(JsonDeserialize.class);
        if (ann != null) {
            Class<? extends JsonDeserializer<?>> deserClass = ann.using();
            if (deserClass != JsonDeserializer.None.class) {
                return deserClass;
            }
        }
        return null;
    }

    public Class<? extends KeyDeserializer> findKeyDeserializer(Annotated a) {
        JsonDeserialize ann = (JsonDeserialize) a.getAnnotation(JsonDeserialize.class);
        if (ann != null) {
            Class<? extends KeyDeserializer> deserClass = ann.keyUsing();
            if (deserClass != KeyDeserializer.None.class) {
                return deserClass;
            }
        }
        return null;
    }

    public Class<? extends JsonDeserializer<?>> findContentDeserializer(Annotated a) {
        JsonDeserialize ann = (JsonDeserialize) a.getAnnotation(JsonDeserialize.class);
        if (ann != null) {
            Class<? extends JsonDeserializer<?>> deserClass = ann.contentUsing();
            if (deserClass != JsonDeserializer.None.class) {
                return deserClass;
            }
        }
        return null;
    }

    public Class<?> findDeserializationType(Annotated am, JavaType baseType) {
        JsonDeserialize ann = (JsonDeserialize) am.getAnnotation(JsonDeserialize.class);
        return ann == null ? null : _classIfExplicit(ann.as());
    }

    public Class<?> findDeserializationKeyType(Annotated am, JavaType baseKeyType) {
        JsonDeserialize ann = (JsonDeserialize) am.getAnnotation(JsonDeserialize.class);
        return ann == null ? null : _classIfExplicit(ann.keyAs());
    }

    public Class<?> findDeserializationContentType(Annotated am, JavaType baseContentType) {
        JsonDeserialize ann = (JsonDeserialize) am.getAnnotation(JsonDeserialize.class);
        return ann == null ? null : _classIfExplicit(ann.contentAs());
    }

    public Object findDeserializationConverter(Annotated a) {
        JsonDeserialize ann = (JsonDeserialize) a.getAnnotation(JsonDeserialize.class);
        return ann == null ? null : _classIfExplicit(ann.converter(), Converter.None.class);
    }

    public Object findDeserializationContentConverter(AnnotatedMember a) {
        JsonDeserialize ann = (JsonDeserialize) a.getAnnotation(JsonDeserialize.class);
        return ann == null ? null : _classIfExplicit(ann.contentConverter(), Converter.None.class);
    }

    public Object findValueInstantiator(AnnotatedClass ac) {
        JsonValueInstantiator ann = (JsonValueInstantiator) ac.getAnnotation(JsonValueInstantiator.class);
        return ann == null ? null : ann.value();
    }

    public Class<?> findPOJOBuilder(AnnotatedClass ac) {
        JsonDeserialize ann = (JsonDeserialize) ac.getAnnotation(JsonDeserialize.class);
        return ann == null ? null : _classIfExplicit(ann.builder());
    }

    public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
        JsonPOJOBuilder ann = (JsonPOJOBuilder) ac.getAnnotation(JsonPOJOBuilder.class);
        return ann == null ? null : new JsonPOJOBuilder.Value(ann);
    }

    public PropertyName findNameForDeserialization(Annotated a) {
        String name;
        JsonSetter js = (JsonSetter) a.getAnnotation(JsonSetter.class);
        if (js != null) {
            name = js.value();
        } else {
            JsonProperty pann = (JsonProperty) a.getAnnotation(JsonProperty.class);
            if (pann != null) {
                name = pann.value();
            } else if (!a.hasAnnotation(JsonDeserialize.class) && !a.hasAnnotation(JsonView.class) && !a.hasAnnotation(JsonUnwrapped.class) && !a.hasAnnotation(JsonBackReference.class) && !a.hasAnnotation(JsonManagedReference.class)) {
                return null;
            } else {
                name = UnsupportedUrlFragment.DISPLAY_NAME;
            }
        }
        if (name.length() == 0) {
            return PropertyName.USE_DEFAULT;
        }
        return new PropertyName(name);
    }

    public boolean hasAnySetterAnnotation(AnnotatedMethod am) {
        return am.hasAnnotation(JsonAnySetter.class);
    }

    public boolean hasAnyGetterAnnotation(AnnotatedMethod am) {
        return am.hasAnnotation(JsonAnyGetter.class);
    }

    public boolean hasCreatorAnnotation(Annotated a) {
        return a.hasAnnotation(JsonCreator.class);
    }

    protected boolean _isIgnorable(Annotated a) {
        JsonIgnore ann = (JsonIgnore) a.getAnnotation(JsonIgnore.class);
        return ann != null && ann.value();
    }

    protected Class<?> _classIfExplicit(Class<?> cls) {
        if (cls == null || ClassUtil.isBogusClass(cls)) {
            return null;
        }
        return cls;
    }

    protected Class<?> _classIfExplicit(Class<?> cls, Class<?> implicit) {
        cls = _classIfExplicit(cls);
        return (cls == null || cls == implicit) ? null : cls;
    }

    protected TypeResolverBuilder<?> _findTypeResolver(MapperConfig<?> config, Annotated ann, JavaType baseType) {
        TypeResolverBuilder<?> b;
        JsonTypeInfo info = (JsonTypeInfo) ann.getAnnotation(JsonTypeInfo.class);
        JsonTypeResolver resAnn = (JsonTypeResolver) ann.getAnnotation(JsonTypeResolver.class);
        if (resAnn != null) {
            if (info == null) {
                return null;
            }
            b = config.typeResolverBuilderInstance(ann, resAnn.value());
        } else if (info == null) {
            return null;
        } else {
            if (info.use() == Id.NONE) {
                return _constructNoTypeResolverBuilder();
            }
            b = _constructStdTypeResolverBuilder();
        }
        JsonTypeIdResolver idResInfo = (JsonTypeIdResolver) ann.getAnnotation(JsonTypeIdResolver.class);
        TypeIdResolver idRes = idResInfo == null ? null : config.typeIdResolverInstance(ann, idResInfo.value());
        if (idRes != null) {
            idRes.init(baseType);
        }
        b = b.init(info.use(), idRes);
        As inclusion = info.include();
        if (inclusion == As.EXTERNAL_PROPERTY && (ann instanceof AnnotatedClass)) {
            inclusion = As.PROPERTY;
        }
        b = b.inclusion(inclusion).typeProperty(info.property());
        Class<?> defaultImpl = info.defaultImpl();
        if (defaultImpl != JsonTypeInfo.None.class) {
            b = b.defaultImpl(defaultImpl);
        }
        return b.typeIdVisibility(info.visible());
    }

    protected StdTypeResolverBuilder _constructStdTypeResolverBuilder() {
        return new StdTypeResolverBuilder();
    }

    protected StdTypeResolverBuilder _constructNoTypeResolverBuilder() {
        return StdTypeResolverBuilder.noTypeInfoBuilder();
    }
}
