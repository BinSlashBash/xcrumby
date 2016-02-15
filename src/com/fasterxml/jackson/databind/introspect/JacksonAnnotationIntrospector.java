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
import com.fasterxml.jackson.annotation.JsonTypeInfo.None;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.None;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonDeserializer.None;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.JsonSerializer.None;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer.None;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.std.RawSerializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter.None;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class JacksonAnnotationIntrospector
  extends AnnotationIntrospector
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  private final Boolean _findSortAlpha(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonPropertyOrder)paramAnnotated.getAnnotation(JsonPropertyOrder.class);
    if (paramAnnotated == null) {
      return null;
    }
    return Boolean.valueOf(paramAnnotated.alphabetic());
  }
  
  protected Class<?> _classIfExplicit(Class<?> paramClass)
  {
    Class<?> localClass;
    if (paramClass != null)
    {
      localClass = paramClass;
      if (!ClassUtil.isBogusClass(paramClass)) {}
    }
    else
    {
      localClass = null;
    }
    return localClass;
  }
  
  protected Class<?> _classIfExplicit(Class<?> paramClass1, Class<?> paramClass2)
  {
    Class localClass = _classIfExplicit(paramClass1);
    if (localClass != null)
    {
      paramClass1 = localClass;
      if (localClass != paramClass2) {}
    }
    else
    {
      paramClass1 = null;
    }
    return paramClass1;
  }
  
  protected StdTypeResolverBuilder _constructNoTypeResolverBuilder()
  {
    return StdTypeResolverBuilder.noTypeInfoBuilder();
  }
  
  protected StdTypeResolverBuilder _constructStdTypeResolverBuilder()
  {
    return new StdTypeResolverBuilder();
  }
  
  protected final Object _findFilterId(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonFilter)paramAnnotated.getAnnotation(JsonFilter.class);
    if (paramAnnotated != null)
    {
      paramAnnotated = paramAnnotated.value();
      if (paramAnnotated.length() > 0) {
        return paramAnnotated;
      }
    }
    return null;
  }
  
  protected TypeResolverBuilder<?> _findTypeResolver(MapperConfig<?> paramMapperConfig, Annotated paramAnnotated, JavaType paramJavaType)
  {
    JsonTypeInfo localJsonTypeInfo = (JsonTypeInfo)paramAnnotated.getAnnotation(JsonTypeInfo.class);
    Object localObject = (JsonTypeResolver)paramAnnotated.getAnnotation(JsonTypeResolver.class);
    JsonTypeIdResolver localJsonTypeIdResolver;
    if (localObject != null)
    {
      if (localJsonTypeInfo == null) {
        return null;
      }
      localObject = paramMapperConfig.typeResolverBuilderInstance(paramAnnotated, ((JsonTypeResolver)localObject).value());
      localJsonTypeIdResolver = (JsonTypeIdResolver)paramAnnotated.getAnnotation(JsonTypeIdResolver.class);
      if (localJsonTypeIdResolver != null) {
        break label217;
      }
    }
    label217:
    for (paramMapperConfig = null;; paramMapperConfig = paramMapperConfig.typeIdResolverInstance(paramAnnotated, localJsonTypeIdResolver.value()))
    {
      if (paramMapperConfig != null) {
        paramMapperConfig.init(paramJavaType);
      }
      localObject = ((TypeResolverBuilder)localObject).init(localJsonTypeInfo.use(), paramMapperConfig);
      paramJavaType = localJsonTypeInfo.include();
      paramMapperConfig = paramJavaType;
      if (paramJavaType == JsonTypeInfo.As.EXTERNAL_PROPERTY)
      {
        paramMapperConfig = paramJavaType;
        if ((paramAnnotated instanceof AnnotatedClass)) {
          paramMapperConfig = JsonTypeInfo.As.PROPERTY;
        }
      }
      paramAnnotated = ((TypeResolverBuilder)localObject).inclusion(paramMapperConfig).typeProperty(localJsonTypeInfo.property());
      paramJavaType = localJsonTypeInfo.defaultImpl();
      paramMapperConfig = paramAnnotated;
      if (paramJavaType != JsonTypeInfo.None.class) {
        paramMapperConfig = paramAnnotated.defaultImpl(paramJavaType);
      }
      return paramMapperConfig.typeIdVisibility(localJsonTypeInfo.visible());
      if (localJsonTypeInfo == null) {
        return null;
      }
      if (localJsonTypeInfo.use() == JsonTypeInfo.Id.NONE) {
        return _constructNoTypeResolverBuilder();
      }
      localObject = _constructStdTypeResolverBuilder();
      break;
    }
  }
  
  protected boolean _isIgnorable(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonIgnore)paramAnnotated.getAnnotation(JsonIgnore.class);
    return (paramAnnotated != null) && (paramAnnotated.value());
  }
  
  public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass paramAnnotatedClass, VisibilityChecker<?> paramVisibilityChecker)
  {
    paramAnnotatedClass = (JsonAutoDetect)paramAnnotatedClass.getAnnotation(JsonAutoDetect.class);
    if (paramAnnotatedClass == null) {
      return paramVisibilityChecker;
    }
    return paramVisibilityChecker.with(paramAnnotatedClass);
  }
  
  public Class<? extends JsonDeserializer<?>> findContentDeserializer(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
    if (paramAnnotated != null)
    {
      paramAnnotated = paramAnnotated.contentUsing();
      if (paramAnnotated != JsonDeserializer.None.class) {
        return paramAnnotated;
      }
    }
    return null;
  }
  
  public Class<? extends JsonSerializer<?>> findContentSerializer(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
    if (paramAnnotated != null)
    {
      paramAnnotated = paramAnnotated.contentUsing();
      if (paramAnnotated != JsonSerializer.None.class) {
        return paramAnnotated;
      }
    }
    return null;
  }
  
  public Object findDeserializationContentConverter(AnnotatedMember paramAnnotatedMember)
  {
    paramAnnotatedMember = (JsonDeserialize)paramAnnotatedMember.getAnnotation(JsonDeserialize.class);
    if (paramAnnotatedMember == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotatedMember.contentConverter(), Converter.None.class);
  }
  
  public Class<?> findDeserializationContentType(Annotated paramAnnotated, JavaType paramJavaType)
  {
    paramAnnotated = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
    if (paramAnnotated == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotated.contentAs());
  }
  
  public Object findDeserializationConverter(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
    if (paramAnnotated == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotated.converter(), Converter.None.class);
  }
  
  public Class<?> findDeserializationKeyType(Annotated paramAnnotated, JavaType paramJavaType)
  {
    paramAnnotated = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
    if (paramAnnotated == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotated.keyAs());
  }
  
  public Class<?> findDeserializationType(Annotated paramAnnotated, JavaType paramJavaType)
  {
    paramAnnotated = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
    if (paramAnnotated == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotated.as());
  }
  
  public Class<? extends JsonDeserializer<?>> findDeserializer(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
    if (paramAnnotated != null)
    {
      paramAnnotated = paramAnnotated.using();
      if (paramAnnotated != JsonDeserializer.None.class) {
        return paramAnnotated;
      }
    }
    return null;
  }
  
  public Object findFilterId(Annotated paramAnnotated)
  {
    return _findFilterId(paramAnnotated);
  }
  
  @Deprecated
  public Object findFilterId(AnnotatedClass paramAnnotatedClass)
  {
    return _findFilterId(paramAnnotatedClass);
  }
  
  public JsonFormat.Value findFormat(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonFormat)paramAnnotated.getAnnotation(JsonFormat.class);
    if (paramAnnotated == null) {
      return null;
    }
    return new JsonFormat.Value(paramAnnotated);
  }
  
  public Boolean findIgnoreUnknownProperties(AnnotatedClass paramAnnotatedClass)
  {
    paramAnnotatedClass = (JsonIgnoreProperties)paramAnnotatedClass.getAnnotation(JsonIgnoreProperties.class);
    if (paramAnnotatedClass == null) {
      return null;
    }
    return Boolean.valueOf(paramAnnotatedClass.ignoreUnknown());
  }
  
  public String findImplicitPropertyName(AnnotatedMember paramAnnotatedMember)
  {
    return null;
  }
  
  public Object findInjectableValueId(AnnotatedMember paramAnnotatedMember)
  {
    Object localObject = (JacksonInject)paramAnnotatedMember.getAnnotation(JacksonInject.class);
    if (localObject == null) {
      localObject = null;
    }
    String str;
    do
    {
      return localObject;
      str = ((JacksonInject)localObject).value();
      localObject = str;
    } while (str.length() != 0);
    if (!(paramAnnotatedMember instanceof AnnotatedMethod)) {
      return paramAnnotatedMember.getRawType().getName();
    }
    localObject = (AnnotatedMethod)paramAnnotatedMember;
    if (((AnnotatedMethod)localObject).getParameterCount() == 0) {
      return paramAnnotatedMember.getRawType().getName();
    }
    return ((AnnotatedMethod)localObject).getRawParameterType(0).getName();
  }
  
  public Class<? extends KeyDeserializer> findKeyDeserializer(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonDeserialize)paramAnnotated.getAnnotation(JsonDeserialize.class);
    if (paramAnnotated != null)
    {
      paramAnnotated = paramAnnotated.keyUsing();
      if (paramAnnotated != KeyDeserializer.None.class) {
        return paramAnnotated;
      }
    }
    return null;
  }
  
  public Class<? extends JsonSerializer<?>> findKeySerializer(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
    if (paramAnnotated != null)
    {
      paramAnnotated = paramAnnotated.keyUsing();
      if (paramAnnotated != JsonSerializer.None.class) {
        return paramAnnotated;
      }
    }
    return null;
  }
  
  public PropertyName findNameForDeserialization(Annotated paramAnnotated)
  {
    Object localObject = (JsonSetter)paramAnnotated.getAnnotation(JsonSetter.class);
    if (localObject != null) {
      paramAnnotated = ((JsonSetter)localObject).value();
    }
    while (paramAnnotated.length() == 0)
    {
      return PropertyName.USE_DEFAULT;
      localObject = (JsonProperty)paramAnnotated.getAnnotation(JsonProperty.class);
      if (localObject != null) {
        paramAnnotated = ((JsonProperty)localObject).value();
      } else if ((paramAnnotated.hasAnnotation(JsonDeserialize.class)) || (paramAnnotated.hasAnnotation(JsonView.class)) || (paramAnnotated.hasAnnotation(JsonUnwrapped.class)) || (paramAnnotated.hasAnnotation(JsonBackReference.class)) || (paramAnnotated.hasAnnotation(JsonManagedReference.class))) {
        paramAnnotated = "";
      } else {
        return null;
      }
    }
    return new PropertyName(paramAnnotated);
  }
  
  public PropertyName findNameForSerialization(Annotated paramAnnotated)
  {
    Object localObject = (JsonGetter)paramAnnotated.getAnnotation(JsonGetter.class);
    if (localObject != null) {
      paramAnnotated = ((JsonGetter)localObject).value();
    }
    while (paramAnnotated.length() == 0)
    {
      return PropertyName.USE_DEFAULT;
      localObject = (JsonProperty)paramAnnotated.getAnnotation(JsonProperty.class);
      if (localObject != null) {
        paramAnnotated = ((JsonProperty)localObject).value();
      } else if ((paramAnnotated.hasAnnotation(JsonSerialize.class)) || (paramAnnotated.hasAnnotation(JsonView.class))) {
        paramAnnotated = "";
      } else {
        return null;
      }
    }
    return new PropertyName(paramAnnotated);
  }
  
  public Object findNamingStrategy(AnnotatedClass paramAnnotatedClass)
  {
    paramAnnotatedClass = (JsonNaming)paramAnnotatedClass.getAnnotation(JsonNaming.class);
    if (paramAnnotatedClass == null) {
      return null;
    }
    return paramAnnotatedClass.value();
  }
  
  public Object findNullSerializer(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
    if (paramAnnotated != null)
    {
      paramAnnotated = paramAnnotated.nullsUsing();
      if (paramAnnotated != JsonSerializer.None.class) {
        return paramAnnotated;
      }
    }
    return null;
  }
  
  public ObjectIdInfo findObjectIdInfo(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonIdentityInfo)paramAnnotated.getAnnotation(JsonIdentityInfo.class);
    if ((paramAnnotated == null) || (paramAnnotated.generator() == ObjectIdGenerators.None.class)) {
      return null;
    }
    return new ObjectIdInfo(new PropertyName(paramAnnotated.property()), paramAnnotated.scope(), paramAnnotated.generator(), paramAnnotated.resolver());
  }
  
  public ObjectIdInfo findObjectReferenceInfo(Annotated paramAnnotated, ObjectIdInfo paramObjectIdInfo)
  {
    JsonIdentityReference localJsonIdentityReference = (JsonIdentityReference)paramAnnotated.getAnnotation(JsonIdentityReference.class);
    paramAnnotated = paramObjectIdInfo;
    if (localJsonIdentityReference != null) {
      paramAnnotated = paramObjectIdInfo.withAlwaysAsId(localJsonIdentityReference.alwaysAsId());
    }
    return paramAnnotated;
  }
  
  public Class<?> findPOJOBuilder(AnnotatedClass paramAnnotatedClass)
  {
    paramAnnotatedClass = (JsonDeserialize)paramAnnotatedClass.getAnnotation(JsonDeserialize.class);
    if (paramAnnotatedClass == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotatedClass.builder());
  }
  
  public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass paramAnnotatedClass)
  {
    paramAnnotatedClass = (JsonPOJOBuilder)paramAnnotatedClass.getAnnotation(JsonPOJOBuilder.class);
    if (paramAnnotatedClass == null) {
      return null;
    }
    return new JsonPOJOBuilder.Value(paramAnnotatedClass);
  }
  
  public String[] findPropertiesToIgnore(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonIgnoreProperties)paramAnnotated.getAnnotation(JsonIgnoreProperties.class);
    if (paramAnnotated == null) {
      return null;
    }
    return paramAnnotated.value();
  }
  
  public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember, JavaType paramJavaType)
  {
    if (!paramJavaType.isContainerType()) {
      throw new IllegalArgumentException("Must call method with a container type (got " + paramJavaType + ")");
    }
    return _findTypeResolver(paramMapperConfig, paramAnnotatedMember, paramJavaType);
  }
  
  public String findPropertyDescription(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonPropertyDescription)paramAnnotated.getAnnotation(JsonPropertyDescription.class);
    if (paramAnnotated == null) {
      return null;
    }
    return paramAnnotated.value();
  }
  
  public Integer findPropertyIndex(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonProperty)paramAnnotated.getAnnotation(JsonProperty.class);
    if (paramAnnotated != null)
    {
      int i = paramAnnotated.index();
      if (i != -1) {
        return Integer.valueOf(i);
      }
    }
    return null;
  }
  
  public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember, JavaType paramJavaType)
  {
    if (paramJavaType.isContainerType()) {
      return null;
    }
    return _findTypeResolver(paramMapperConfig, paramAnnotatedMember, paramJavaType);
  }
  
  public AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember paramAnnotatedMember)
  {
    JsonManagedReference localJsonManagedReference = (JsonManagedReference)paramAnnotatedMember.getAnnotation(JsonManagedReference.class);
    if (localJsonManagedReference != null) {
      return AnnotationIntrospector.ReferenceProperty.managed(localJsonManagedReference.value());
    }
    paramAnnotatedMember = (JsonBackReference)paramAnnotatedMember.getAnnotation(JsonBackReference.class);
    if (paramAnnotatedMember != null) {
      return AnnotationIntrospector.ReferenceProperty.back(paramAnnotatedMember.value());
    }
    return null;
  }
  
  public PropertyName findRootName(AnnotatedClass paramAnnotatedClass)
  {
    JsonRootName localJsonRootName = (JsonRootName)paramAnnotatedClass.getAnnotation(JsonRootName.class);
    if (localJsonRootName == null) {
      return null;
    }
    String str = localJsonRootName.namespace();
    paramAnnotatedClass = str;
    if (str != null)
    {
      paramAnnotatedClass = str;
      if (str.length() == 0) {
        paramAnnotatedClass = null;
      }
    }
    return PropertyName.construct(localJsonRootName.value(), paramAnnotatedClass);
  }
  
  public Object findSerializationContentConverter(AnnotatedMember paramAnnotatedMember)
  {
    paramAnnotatedMember = (JsonSerialize)paramAnnotatedMember.getAnnotation(JsonSerialize.class);
    if (paramAnnotatedMember == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotatedMember.contentConverter(), Converter.None.class);
  }
  
  public Class<?> findSerializationContentType(Annotated paramAnnotated, JavaType paramJavaType)
  {
    paramAnnotated = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
    if (paramAnnotated == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotated.contentAs());
  }
  
  public Object findSerializationConverter(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
    if (paramAnnotated == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotated.converter(), Converter.None.class);
  }
  
  public JsonInclude.Include findSerializationInclusion(Annotated paramAnnotated, JsonInclude.Include paramInclude)
  {
    Object localObject = (JsonInclude)paramAnnotated.getAnnotation(JsonInclude.class);
    if (localObject != null) {
      paramAnnotated = ((JsonInclude)localObject).value();
    }
    do
    {
      return paramAnnotated;
      localObject = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
      paramAnnotated = paramInclude;
    } while (localObject == null);
    paramAnnotated = ((JsonSerialize)localObject).include();
    switch (paramAnnotated)
    {
    default: 
      return paramInclude;
    case ???: 
      return JsonInclude.Include.ALWAYS;
    case ???: 
      return JsonInclude.Include.NON_NULL;
    case ???: 
      return JsonInclude.Include.NON_DEFAULT;
    }
    return JsonInclude.Include.NON_EMPTY;
  }
  
  public Class<?> findSerializationKeyType(Annotated paramAnnotated, JavaType paramJavaType)
  {
    paramAnnotated = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
    if (paramAnnotated == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotated.keyAs());
  }
  
  public String[] findSerializationPropertyOrder(AnnotatedClass paramAnnotatedClass)
  {
    paramAnnotatedClass = (JsonPropertyOrder)paramAnnotatedClass.getAnnotation(JsonPropertyOrder.class);
    if (paramAnnotatedClass == null) {
      return null;
    }
    return paramAnnotatedClass.value();
  }
  
  public Boolean findSerializationSortAlphabetically(Annotated paramAnnotated)
  {
    return _findSortAlpha(paramAnnotated);
  }
  
  @Deprecated
  public Boolean findSerializationSortAlphabetically(AnnotatedClass paramAnnotatedClass)
  {
    return _findSortAlpha(paramAnnotatedClass);
  }
  
  public Class<?> findSerializationType(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
    if (paramAnnotated == null) {
      return null;
    }
    return _classIfExplicit(paramAnnotated.as());
  }
  
  public JsonSerialize.Typing findSerializationTyping(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
    if (paramAnnotated == null) {
      return null;
    }
    return paramAnnotated.typing();
  }
  
  public Object findSerializer(Annotated paramAnnotated)
  {
    Object localObject = (JsonSerialize)paramAnnotated.getAnnotation(JsonSerialize.class);
    if (localObject != null)
    {
      localObject = ((JsonSerialize)localObject).using();
      if (localObject != JsonSerializer.None.class) {
        return localObject;
      }
    }
    localObject = (JsonRawValue)paramAnnotated.getAnnotation(JsonRawValue.class);
    if ((localObject != null) && (((JsonRawValue)localObject).value())) {
      return new RawSerializer(paramAnnotated.getRawType());
    }
    return null;
  }
  
  public List<NamedType> findSubtypes(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonSubTypes)paramAnnotated.getAnnotation(JsonSubTypes.class);
    if (paramAnnotated == null)
    {
      paramAnnotated = null;
      return paramAnnotated;
    }
    JsonSubTypes.Type[] arrayOfType = paramAnnotated.value();
    ArrayList localArrayList = new ArrayList(arrayOfType.length);
    int j = arrayOfType.length;
    int i = 0;
    for (;;)
    {
      paramAnnotated = localArrayList;
      if (i >= j) {
        break;
      }
      paramAnnotated = arrayOfType[i];
      localArrayList.add(new NamedType(paramAnnotated.value(), paramAnnotated.name()));
      i += 1;
    }
  }
  
  public String findTypeName(AnnotatedClass paramAnnotatedClass)
  {
    paramAnnotatedClass = (JsonTypeName)paramAnnotatedClass.getAnnotation(JsonTypeName.class);
    if (paramAnnotatedClass == null) {
      return null;
    }
    return paramAnnotatedClass.value();
  }
  
  public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedClass paramAnnotatedClass, JavaType paramJavaType)
  {
    return _findTypeResolver(paramMapperConfig, paramAnnotatedClass, paramJavaType);
  }
  
  public NameTransformer findUnwrappingNameTransformer(AnnotatedMember paramAnnotatedMember)
  {
    paramAnnotatedMember = (JsonUnwrapped)paramAnnotatedMember.getAnnotation(JsonUnwrapped.class);
    if ((paramAnnotatedMember == null) || (!paramAnnotatedMember.enabled())) {
      return null;
    }
    return NameTransformer.simpleTransformer(paramAnnotatedMember.prefix(), paramAnnotatedMember.suffix());
  }
  
  public Object findValueInstantiator(AnnotatedClass paramAnnotatedClass)
  {
    paramAnnotatedClass = (JsonValueInstantiator)paramAnnotatedClass.getAnnotation(JsonValueInstantiator.class);
    if (paramAnnotatedClass == null) {
      return null;
    }
    return paramAnnotatedClass.value();
  }
  
  public Class<?>[] findViews(Annotated paramAnnotated)
  {
    paramAnnotated = (JsonView)paramAnnotated.getAnnotation(JsonView.class);
    if (paramAnnotated == null) {
      return null;
    }
    return paramAnnotated.value();
  }
  
  public boolean hasAnyGetterAnnotation(AnnotatedMethod paramAnnotatedMethod)
  {
    return paramAnnotatedMethod.hasAnnotation(JsonAnyGetter.class);
  }
  
  public boolean hasAnySetterAnnotation(AnnotatedMethod paramAnnotatedMethod)
  {
    return paramAnnotatedMethod.hasAnnotation(JsonAnySetter.class);
  }
  
  public boolean hasAsValueAnnotation(AnnotatedMethod paramAnnotatedMethod)
  {
    paramAnnotatedMethod = (JsonValue)paramAnnotatedMethod.getAnnotation(JsonValue.class);
    return (paramAnnotatedMethod != null) && (paramAnnotatedMethod.value());
  }
  
  public boolean hasCreatorAnnotation(Annotated paramAnnotated)
  {
    return paramAnnotated.hasAnnotation(JsonCreator.class);
  }
  
  public boolean hasIgnoreMarker(AnnotatedMember paramAnnotatedMember)
  {
    return _isIgnorable(paramAnnotatedMember);
  }
  
  public Boolean hasRequiredMarker(AnnotatedMember paramAnnotatedMember)
  {
    paramAnnotatedMember = (JsonProperty)paramAnnotatedMember.getAnnotation(JsonProperty.class);
    if (paramAnnotatedMember != null) {
      return Boolean.valueOf(paramAnnotatedMember.required());
    }
    return null;
  }
  
  public boolean isAnnotationBundle(Annotation paramAnnotation)
  {
    return paramAnnotation.annotationType().getAnnotation(JacksonAnnotationsInside.class) != null;
  }
  
  public Boolean isIgnorableType(AnnotatedClass paramAnnotatedClass)
  {
    paramAnnotatedClass = (JsonIgnoreType)paramAnnotatedClass.getAnnotation(JsonIgnoreType.class);
    if (paramAnnotatedClass == null) {
      return null;
    }
    return Boolean.valueOf(paramAnnotatedClass.value());
  }
  
  public Boolean isTypeId(AnnotatedMember paramAnnotatedMember)
  {
    return Boolean.valueOf(paramAnnotatedMember.hasAnnotation(JsonTypeId.class));
  }
  
  public Version version()
  {
    return PackageVersion.VERSION;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/introspect/JacksonAnnotationIntrospector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */