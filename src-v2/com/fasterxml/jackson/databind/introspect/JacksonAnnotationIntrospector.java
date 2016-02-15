/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
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

public class JacksonAnnotationIntrospector
extends AnnotationIntrospector
implements Serializable {
    private static final long serialVersionUID = 1;

    private final Boolean _findSortAlpha(Annotated object) {
        if ((object = (JsonPropertyOrder)object.getAnnotation(JsonPropertyOrder.class)) == null) {
            return null;
        }
        return object.alphabetic();
    }

    protected Class<?> _classIfExplicit(Class<?> class_) {
        Class class_2;
        block2 : {
            if (class_ != null) {
                class_2 = class_;
                if (!ClassUtil.isBogusClass(class_)) break block2;
            }
            class_2 = null;
        }
        return class_2;
    }

    protected Class<?> _classIfExplicit(Class<?> class_, Class<?> class_2) {
        block2 : {
            Class class_3 = this._classIfExplicit(class_);
            if (class_3 != null) {
                class_ = class_3;
                if (class_3 != class_2) break block2;
            }
            class_ = null;
        }
        return class_;
    }

    protected StdTypeResolverBuilder _constructNoTypeResolverBuilder() {
        return StdTypeResolverBuilder.noTypeInfoBuilder();
    }

    protected StdTypeResolverBuilder _constructStdTypeResolverBuilder() {
        return new StdTypeResolverBuilder();
    }

    protected final Object _findFilterId(Annotated object) {
        if ((object = (JsonFilter)object.getAnnotation(JsonFilter.class)) != null && (object = object.value()).length() > 0) {
            return object;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected TypeResolverBuilder<?> _findTypeResolver(MapperConfig<?> object, Annotated annotated, JavaType class_) {
        Object object2;
        JsonTypeIdResolver jsonTypeIdResolver;
        JsonTypeInfo jsonTypeInfo = (JsonTypeInfo)annotated.getAnnotation(JsonTypeInfo.class);
        Object object3 = (JsonTypeResolver)annotated.getAnnotation(JsonTypeResolver.class);
        if (object3 != null) {
            if (jsonTypeInfo == null) {
                return null;
            }
            object3 = object.typeResolverBuilderInstance(annotated, object3.value());
        } else {
            if (jsonTypeInfo == null) {
                return null;
            }
            if (jsonTypeInfo.use() == JsonTypeInfo.Id.NONE) {
                return this._constructNoTypeResolverBuilder();
            }
            object3 = this._constructStdTypeResolverBuilder();
        }
        object = (jsonTypeIdResolver = (JsonTypeIdResolver)annotated.getAnnotation(JsonTypeIdResolver.class)) == null ? null : object.typeIdResolverInstance(annotated, jsonTypeIdResolver.value());
        if (object != null) {
            object.init((JavaType)object2);
        }
        object3 = object3.init(jsonTypeInfo.use(), (TypeIdResolver)object);
        object = object2 = jsonTypeInfo.include();
        if (object2 == JsonTypeInfo.As.EXTERNAL_PROPERTY) {
            object = object2;
            if (annotated instanceof AnnotatedClass) {
                object = JsonTypeInfo.As.PROPERTY;
            }
        }
        Object t2 = object3.inclusion((JsonTypeInfo.As)((Object)object)).typeProperty(jsonTypeInfo.property());
        object2 = jsonTypeInfo.defaultImpl();
        object = t2;
        if (object2 != JsonTypeInfo.None.class) {
            object = t2.defaultImpl(object2);
        }
        return object.typeIdVisibility(jsonTypeInfo.visible());
    }

    protected boolean _isIgnorable(Annotated object) {
        if ((object = (JsonIgnore)object.getAnnotation(JsonIgnore.class)) != null && object.value()) {
            return true;
        }
        return false;
    }

    @Override
    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass object, VisibilityChecker<?> visibilityChecker) {
        if ((object = (JsonAutoDetect)object.getAnnotation(JsonAutoDetect.class)) == null) {
            return visibilityChecker;
        }
        return visibilityChecker.with((JsonAutoDetect)object);
    }

    @Override
    public Class<? extends JsonDeserializer<?>> findContentDeserializer(Annotated class_) {
        if ((class_ = (JsonDeserialize)class_.getAnnotation(JsonDeserialize.class)) != null && (class_ = class_.contentUsing()) != JsonDeserializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public Class<? extends JsonSerializer<?>> findContentSerializer(Annotated class_) {
        if ((class_ = (JsonSerialize)class_.getAnnotation(JsonSerialize.class)) != null && (class_ = class_.contentUsing()) != JsonSerializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public Object findDeserializationContentConverter(AnnotatedMember object) {
        if ((object = (JsonDeserialize)object.getAnnotation(JsonDeserialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.contentConverter(), Converter.None.class);
    }

    @Override
    public Class<?> findDeserializationContentType(Annotated object, JavaType javaType) {
        if ((object = (JsonDeserialize)object.getAnnotation(JsonDeserialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.contentAs());
    }

    @Override
    public Object findDeserializationConverter(Annotated object) {
        if ((object = (JsonDeserialize)object.getAnnotation(JsonDeserialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.converter(), Converter.None.class);
    }

    @Override
    public Class<?> findDeserializationKeyType(Annotated object, JavaType javaType) {
        if ((object = (JsonDeserialize)object.getAnnotation(JsonDeserialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.keyAs());
    }

    @Override
    public Class<?> findDeserializationType(Annotated object, JavaType javaType) {
        if ((object = (JsonDeserialize)object.getAnnotation(JsonDeserialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.as());
    }

    @Override
    public Class<? extends JsonDeserializer<?>> findDeserializer(Annotated class_) {
        if ((class_ = (JsonDeserialize)class_.getAnnotation(JsonDeserialize.class)) != null && (class_ = class_.using()) != JsonDeserializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public Object findFilterId(Annotated annotated) {
        return this._findFilterId(annotated);
    }

    @Deprecated
    @Override
    public Object findFilterId(AnnotatedClass annotatedClass) {
        return this._findFilterId(annotatedClass);
    }

    @Override
    public JsonFormat.Value findFormat(Annotated object) {
        if ((object = (JsonFormat)object.getAnnotation(JsonFormat.class)) == null) {
            return null;
        }
        return new JsonFormat.Value((JsonFormat)object);
    }

    @Override
    public Boolean findIgnoreUnknownProperties(AnnotatedClass object) {
        if ((object = (JsonIgnoreProperties)object.getAnnotation(JsonIgnoreProperties.class)) == null) {
            return null;
        }
        return object.ignoreUnknown();
    }

    @Override
    public String findImplicitPropertyName(AnnotatedMember annotatedMember) {
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object findInjectableValueId(AnnotatedMember annotatedMember) {
        String string2;
        Object object = (JacksonInject)annotatedMember.getAnnotation(JacksonInject.class);
        if (object == null) {
            return null;
        }
        object = string2 = object.value();
        if (string2.length() != 0) return object;
        if (!(annotatedMember instanceof AnnotatedMethod)) {
            return annotatedMember.getRawType().getName();
        }
        object = (AnnotatedMethod)annotatedMember;
        if (object.getParameterCount() != 0) return object.getRawParameterType(0).getName();
        return annotatedMember.getRawType().getName();
    }

    @Override
    public Class<? extends KeyDeserializer> findKeyDeserializer(Annotated class_) {
        if ((class_ = (JsonDeserialize)class_.getAnnotation(JsonDeserialize.class)) != null && (class_ = class_.keyUsing()) != KeyDeserializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public Class<? extends JsonSerializer<?>> findKeySerializer(Annotated class_) {
        if ((class_ = (JsonSerialize)class_.getAnnotation(JsonSerialize.class)) != null && (class_ = class_.keyUsing()) != JsonSerializer.None.class) {
            return class_;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PropertyName findNameForDeserialization(Annotated object) {
        JsonSetter jsonSetter = (JsonSetter)object.getAnnotation(JsonSetter.class);
        if (jsonSetter != null) {
            object = jsonSetter.value();
        } else {
            JsonProperty jsonProperty = (JsonProperty)object.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                object = jsonProperty.value();
            } else {
                if (!(object.hasAnnotation(JsonDeserialize.class) || object.hasAnnotation(JsonView.class) || object.hasAnnotation(JsonUnwrapped.class) || object.hasAnnotation(JsonBackReference.class) || object.hasAnnotation(JsonManagedReference.class))) {
                    return null;
                }
                object = "";
            }
        }
        if (object.length() == 0) {
            return PropertyName.USE_DEFAULT;
        }
        return new PropertyName((String)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PropertyName findNameForSerialization(Annotated object) {
        JsonGetter jsonGetter = (JsonGetter)object.getAnnotation(JsonGetter.class);
        if (jsonGetter != null) {
            object = jsonGetter.value();
        } else {
            JsonProperty jsonProperty = (JsonProperty)object.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                object = jsonProperty.value();
            } else {
                if (!object.hasAnnotation(JsonSerialize.class) && !object.hasAnnotation(JsonView.class)) {
                    return null;
                }
                object = "";
            }
        }
        if (object.length() == 0) {
            return PropertyName.USE_DEFAULT;
        }
        return new PropertyName((String)object);
    }

    @Override
    public Object findNamingStrategy(AnnotatedClass object) {
        if ((object = (JsonNaming)object.getAnnotation(JsonNaming.class)) == null) {
            return null;
        }
        return object.value();
    }

    @Override
    public Object findNullSerializer(Annotated class_) {
        if ((class_ = (JsonSerialize)class_.getAnnotation(JsonSerialize.class)) != null && (class_ = class_.nullsUsing()) != JsonSerializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public ObjectIdInfo findObjectIdInfo(Annotated object) {
        if ((object = (JsonIdentityInfo)object.getAnnotation(JsonIdentityInfo.class)) == null || object.generator() == ObjectIdGenerators.None.class) {
            return null;
        }
        return new ObjectIdInfo(new PropertyName(object.property()), object.scope(), object.generator(), object.resolver());
    }

    @Override
    public ObjectIdInfo findObjectReferenceInfo(Annotated object, ObjectIdInfo objectIdInfo) {
        JsonIdentityReference jsonIdentityReference = (JsonIdentityReference)object.getAnnotation(JsonIdentityReference.class);
        object = objectIdInfo;
        if (jsonIdentityReference != null) {
            object = objectIdInfo.withAlwaysAsId(jsonIdentityReference.alwaysAsId());
        }
        return object;
    }

    @Override
    public Class<?> findPOJOBuilder(AnnotatedClass object) {
        if ((object = (JsonDeserialize)object.getAnnotation(JsonDeserialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.builder());
    }

    @Override
    public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass object) {
        if ((object = (JsonPOJOBuilder)object.getAnnotation(JsonPOJOBuilder.class)) == null) {
            return null;
        }
        return new JsonPOJOBuilder.Value((JsonPOJOBuilder)object);
    }

    @Override
    public String[] findPropertiesToIgnore(Annotated object) {
        if ((object = (JsonIgnoreProperties)object.getAnnotation(JsonIgnoreProperties.class)) == null) {
            return null;
        }
        return object.value();
    }

    @Override
    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        if (!javaType.isContainerType()) {
            throw new IllegalArgumentException("Must call method with a container type (got " + javaType + ")");
        }
        return this._findTypeResolver(mapperConfig, annotatedMember, javaType);
    }

    @Override
    public String findPropertyDescription(Annotated object) {
        if ((object = (JsonPropertyDescription)object.getAnnotation(JsonPropertyDescription.class)) == null) {
            return null;
        }
        return object.value();
    }

    @Override
    public Integer findPropertyIndex(Annotated object) {
        int n2;
        if ((object = (JsonProperty)object.getAnnotation(JsonProperty.class)) != null && (n2 = object.index()) != -1) {
            return n2;
        }
        return null;
    }

    @Override
    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        if (javaType.isContainerType()) {
            return null;
        }
        return this._findTypeResolver(mapperConfig, annotatedMember, javaType);
    }

    @Override
    public AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember object) {
        JsonManagedReference jsonManagedReference = (JsonManagedReference)object.getAnnotation(JsonManagedReference.class);
        if (jsonManagedReference != null) {
            return AnnotationIntrospector.ReferenceProperty.managed(jsonManagedReference.value());
        }
        if ((object = (JsonBackReference)object.getAnnotation(JsonBackReference.class)) != null) {
            return AnnotationIntrospector.ReferenceProperty.back(object.value());
        }
        return null;
    }

    @Override
    public PropertyName findRootName(AnnotatedClass object) {
        String string2;
        JsonRootName jsonRootName = (JsonRootName)object.getAnnotation(JsonRootName.class);
        if (jsonRootName == null) {
            return null;
        }
        object = string2 = jsonRootName.namespace();
        if (string2 != null) {
            object = string2;
            if (string2.length() == 0) {
                object = null;
            }
        }
        return PropertyName.construct(jsonRootName.value(), (String)object);
    }

    @Override
    public Object findSerializationContentConverter(AnnotatedMember object) {
        if ((object = (JsonSerialize)object.getAnnotation(JsonSerialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.contentConverter(), Converter.None.class);
    }

    @Override
    public Class<?> findSerializationContentType(Annotated object, JavaType javaType) {
        if ((object = (JsonSerialize)object.getAnnotation(JsonSerialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.contentAs());
    }

    @Override
    public Object findSerializationConverter(Annotated object) {
        if ((object = (JsonSerialize)object.getAnnotation(JsonSerialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.converter(), Converter.None.class);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonInclude.Include findSerializationInclusion(Annotated object, JsonInclude.Include include) {
        JsonInclude jsonInclude = (JsonInclude)object.getAnnotation(JsonInclude.class);
        if (jsonInclude != null) {
            return jsonInclude.value();
        }
        JsonSerialize jsonSerialize = (JsonSerialize)object.getAnnotation(JsonSerialize.class);
        object = include;
        if (jsonSerialize == null) return object;
        object = jsonSerialize.include();
        switch (.$SwitchMap$com$fasterxml$jackson$databind$annotation$JsonSerialize$Inclusion[object.ordinal()]) {
            default: {
                return include;
            }
            case 1: {
                return JsonInclude.Include.ALWAYS;
            }
            case 2: {
                return JsonInclude.Include.NON_NULL;
            }
            case 3: {
                return JsonInclude.Include.NON_DEFAULT;
            }
            case 4: 
        }
        return JsonInclude.Include.NON_EMPTY;
    }

    @Override
    public Class<?> findSerializationKeyType(Annotated object, JavaType javaType) {
        if ((object = (JsonSerialize)object.getAnnotation(JsonSerialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.keyAs());
    }

    @Override
    public String[] findSerializationPropertyOrder(AnnotatedClass object) {
        if ((object = (JsonPropertyOrder)object.getAnnotation(JsonPropertyOrder.class)) == null) {
            return null;
        }
        return object.value();
    }

    @Override
    public Boolean findSerializationSortAlphabetically(Annotated annotated) {
        return this._findSortAlpha(annotated);
    }

    @Deprecated
    @Override
    public Boolean findSerializationSortAlphabetically(AnnotatedClass annotatedClass) {
        return this._findSortAlpha(annotatedClass);
    }

    @Override
    public Class<?> findSerializationType(Annotated object) {
        if ((object = (JsonSerialize)object.getAnnotation(JsonSerialize.class)) == null) {
            return null;
        }
        return this._classIfExplicit(object.as());
    }

    @Override
    public JsonSerialize.Typing findSerializationTyping(Annotated object) {
        if ((object = (JsonSerialize)object.getAnnotation(JsonSerialize.class)) == null) {
            return null;
        }
        return object.typing();
    }

    @Override
    public Object findSerializer(Annotated annotated) {
        Object object = (JsonSerialize)annotated.getAnnotation(JsonSerialize.class);
        if (object != null && (object = object.using()) != JsonSerializer.None.class) {
            return object;
        }
        object = (JsonRawValue)annotated.getAnnotation(JsonRawValue.class);
        if (object != null && object.value()) {
            return new RawSerializer(annotated.getRawType());
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<NamedType> findSubtypes(Annotated object) {
        if ((object = (JsonSubTypes)object.getAnnotation(JsonSubTypes.class)) == null) {
            return null;
        }
        JsonSubTypes.Type[] arrtype = object.value();
        ArrayList<NamedType> arrayList = new ArrayList<NamedType>(arrtype.length);
        int n2 = arrtype.length;
        int n3 = 0;
        do {
            object = arrayList;
            if (n3 >= n2) return object;
            object = arrtype[n3];
            arrayList.add(new NamedType(object.value(), object.name()));
            ++n3;
        } while (true);
    }

    @Override
    public String findTypeName(AnnotatedClass object) {
        if ((object = (JsonTypeName)object.getAnnotation(JsonTypeName.class)) == null) {
            return null;
        }
        return object.value();
    }

    @Override
    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType) {
        return this._findTypeResolver(mapperConfig, annotatedClass, javaType);
    }

    @Override
    public NameTransformer findUnwrappingNameTransformer(AnnotatedMember object) {
        if ((object = (JsonUnwrapped)object.getAnnotation(JsonUnwrapped.class)) == null || !object.enabled()) {
            return null;
        }
        return NameTransformer.simpleTransformer(object.prefix(), object.suffix());
    }

    @Override
    public Object findValueInstantiator(AnnotatedClass object) {
        if ((object = (JsonValueInstantiator)object.getAnnotation(JsonValueInstantiator.class)) == null) {
            return null;
        }
        return object.value();
    }

    @Override
    public Class<?>[] findViews(Annotated object) {
        if ((object = (JsonView)object.getAnnotation(JsonView.class)) == null) {
            return null;
        }
        return object.value();
    }

    @Override
    public boolean hasAnyGetterAnnotation(AnnotatedMethod annotatedMethod) {
        return annotatedMethod.hasAnnotation(JsonAnyGetter.class);
    }

    @Override
    public boolean hasAnySetterAnnotation(AnnotatedMethod annotatedMethod) {
        return annotatedMethod.hasAnnotation(JsonAnySetter.class);
    }

    @Override
    public boolean hasAsValueAnnotation(AnnotatedMethod object) {
        if ((object = (JsonValue)object.getAnnotation(JsonValue.class)) != null && object.value()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasCreatorAnnotation(Annotated annotated) {
        return annotated.hasAnnotation(JsonCreator.class);
    }

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
        return this._isIgnorable(annotatedMember);
    }

    @Override
    public Boolean hasRequiredMarker(AnnotatedMember object) {
        if ((object = (JsonProperty)object.getAnnotation(JsonProperty.class)) != null) {
            return object.required();
        }
        return null;
    }

    @Override
    public boolean isAnnotationBundle(Annotation annotation) {
        if (annotation.annotationType().getAnnotation(JacksonAnnotationsInside.class) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isIgnorableType(AnnotatedClass object) {
        if ((object = (JsonIgnoreType)object.getAnnotation(JsonIgnoreType.class)) == null) {
            return null;
        }
        return object.value();
    }

    @Override
    public Boolean isTypeId(AnnotatedMember annotatedMember) {
        return annotatedMember.hasAnnotation(JsonTypeId.class);
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

}

