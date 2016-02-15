package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap.SerializerAndMapResult;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@JacksonStdImpl
public class MapSerializer
  extends ContainerSerializer<Map<?, ?>>
  implements ContextualSerializer
{
  protected static final JavaType UNSPECIFIED_TYPE = ;
  protected PropertySerializerMap _dynamicValueSerializers;
  protected final Object _filterId;
  protected final HashSet<String> _ignoredEntries;
  protected JsonSerializer<Object> _keySerializer;
  protected final JavaType _keyType;
  protected final BeanProperty _property;
  protected final boolean _sortKeys;
  protected JsonSerializer<Object> _valueSerializer;
  protected final JavaType _valueType;
  protected final boolean _valueTypeIsStatic;
  protected final TypeSerializer _valueTypeSerializer;
  
  protected MapSerializer(MapSerializer paramMapSerializer, BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer1, JsonSerializer<?> paramJsonSerializer2, HashSet<String> paramHashSet)
  {
    super(Map.class, false);
    this._ignoredEntries = paramHashSet;
    this._keyType = paramMapSerializer._keyType;
    this._valueType = paramMapSerializer._valueType;
    this._valueTypeIsStatic = paramMapSerializer._valueTypeIsStatic;
    this._valueTypeSerializer = paramMapSerializer._valueTypeSerializer;
    this._keySerializer = paramJsonSerializer1;
    this._valueSerializer = paramJsonSerializer2;
    this._dynamicValueSerializers = paramMapSerializer._dynamicValueSerializers;
    this._property = paramBeanProperty;
    this._filterId = paramMapSerializer._filterId;
    this._sortKeys = paramMapSerializer._sortKeys;
  }
  
  protected MapSerializer(MapSerializer paramMapSerializer, TypeSerializer paramTypeSerializer)
  {
    super(Map.class, false);
    this._ignoredEntries = paramMapSerializer._ignoredEntries;
    this._keyType = paramMapSerializer._keyType;
    this._valueType = paramMapSerializer._valueType;
    this._valueTypeIsStatic = paramMapSerializer._valueTypeIsStatic;
    this._valueTypeSerializer = paramTypeSerializer;
    this._keySerializer = paramMapSerializer._keySerializer;
    this._valueSerializer = paramMapSerializer._valueSerializer;
    this._dynamicValueSerializers = paramMapSerializer._dynamicValueSerializers;
    this._property = paramMapSerializer._property;
    this._filterId = paramMapSerializer._filterId;
    this._sortKeys = paramMapSerializer._sortKeys;
  }
  
  protected MapSerializer(MapSerializer paramMapSerializer, Object paramObject, boolean paramBoolean)
  {
    super(Map.class, false);
    this._ignoredEntries = paramMapSerializer._ignoredEntries;
    this._keyType = paramMapSerializer._keyType;
    this._valueType = paramMapSerializer._valueType;
    this._valueTypeIsStatic = paramMapSerializer._valueTypeIsStatic;
    this._valueTypeSerializer = paramMapSerializer._valueTypeSerializer;
    this._keySerializer = paramMapSerializer._keySerializer;
    this._valueSerializer = paramMapSerializer._valueSerializer;
    this._dynamicValueSerializers = paramMapSerializer._dynamicValueSerializers;
    this._property = paramMapSerializer._property;
    this._filterId = paramObject;
    this._sortKeys = paramBoolean;
  }
  
  protected MapSerializer(HashSet<String> paramHashSet, JavaType paramJavaType1, JavaType paramJavaType2, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer1, JsonSerializer<?> paramJsonSerializer2)
  {
    super(Map.class, false);
    this._ignoredEntries = paramHashSet;
    this._keyType = paramJavaType1;
    this._valueType = paramJavaType2;
    this._valueTypeIsStatic = paramBoolean;
    this._valueTypeSerializer = paramTypeSerializer;
    this._keySerializer = paramJsonSerializer1;
    this._valueSerializer = paramJsonSerializer2;
    this._dynamicValueSerializers = PropertySerializerMap.emptyMap();
    this._property = null;
    this._filterId = null;
    this._sortKeys = false;
  }
  
  @Deprecated
  public static MapSerializer construct(String[] paramArrayOfString, JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer1, JsonSerializer<Object> paramJsonSerializer2)
  {
    return construct(paramArrayOfString, paramJavaType, paramBoolean, paramTypeSerializer, paramJsonSerializer1, paramJsonSerializer2, null);
  }
  
  public static MapSerializer construct(String[] paramArrayOfString, JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, JsonSerializer<Object> paramJsonSerializer1, JsonSerializer<Object> paramJsonSerializer2, Object paramObject)
  {
    HashSet localHashSet = toSet(paramArrayOfString);
    if (paramJavaType == null)
    {
      paramJavaType = UNSPECIFIED_TYPE;
      paramArrayOfString = paramJavaType;
      if (paramBoolean) {
        break label85;
      }
      if ((paramJavaType == null) || (!paramJavaType.isFinal())) {
        break label80;
      }
      paramBoolean = true;
    }
    for (;;)
    {
      paramJavaType = new MapSerializer(localHashSet, paramArrayOfString, paramJavaType, paramBoolean, paramTypeSerializer, paramJsonSerializer1, paramJsonSerializer2);
      paramArrayOfString = paramJavaType;
      if (paramObject != null) {
        paramArrayOfString = paramJavaType.withFilterId(paramObject);
      }
      return paramArrayOfString;
      paramArrayOfString = paramJavaType.getKeyType();
      paramJavaType = paramJavaType.getContentType();
      break;
      label80:
      paramBoolean = false;
      continue;
      label85:
      if (paramJavaType.getRawClass() == Object.class) {
        paramBoolean = false;
      }
    }
  }
  
  private static HashSet<String> toSet(String[] paramArrayOfString)
  {
    Object localObject;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localObject = null;
      return (HashSet<String>)localObject;
    }
    HashSet localHashSet = new HashSet(paramArrayOfString.length);
    int j = paramArrayOfString.length;
    int i = 0;
    for (;;)
    {
      localObject = localHashSet;
      if (i >= j) {
        break;
      }
      localHashSet.add(paramArrayOfString[i]);
      i += 1;
    }
  }
  
  protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, JavaType paramJavaType, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    paramJavaType = paramPropertySerializerMap.findAndAddSecondarySerializer(paramJavaType, paramSerializerProvider, this._property);
    if (paramPropertySerializerMap != paramJavaType.map) {
      this._dynamicValueSerializers = paramJavaType.map;
    }
    return paramJavaType.serializer;
  }
  
  protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, Class<?> paramClass, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    paramClass = paramPropertySerializerMap.findAndAddSecondarySerializer(paramClass, paramSerializerProvider, this._property);
    if (paramPropertySerializerMap != paramClass.map) {
      this._dynamicValueSerializers = paramClass.map;
    }
    return paramClass.serializer;
  }
  
  protected Map<?, ?> _orderEntries(Map<?, ?> paramMap)
  {
    if ((paramMap instanceof SortedMap)) {
      return paramMap;
    }
    return new TreeMap(paramMap);
  }
  
  public MapSerializer _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
  {
    return new MapSerializer(this, paramTypeSerializer);
  }
  
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
    throws JsonMappingException
  {
    if (paramJsonFormatVisitorWrapper == null) {}
    for (paramJavaType = null;; paramJavaType = paramJsonFormatVisitorWrapper.expectMapFormat(paramJavaType))
    {
      if (paramJavaType != null)
      {
        paramJavaType.keyFormat(this._keySerializer, this._keyType);
        JsonSerializer localJsonSerializer2 = this._valueSerializer;
        JsonSerializer localJsonSerializer1 = localJsonSerializer2;
        if (localJsonSerializer2 == null) {
          localJsonSerializer1 = _findAndAddDynamic(this._dynamicValueSerializers, this._valueType, paramJsonFormatVisitorWrapper.getProvider());
        }
        paramJavaType.valueFormat(localJsonSerializer1, this._valueType);
      }
      return;
    }
  }
  
  public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    String[] arrayOfString = null;
    Object localObject5 = null;
    Object localObject3 = null;
    AnnotationIntrospector localAnnotationIntrospector = paramSerializerProvider.getAnnotationIntrospector();
    Object localObject4;
    Object localObject1;
    if (paramBeanProperty == null)
    {
      localObject4 = null;
      localObject2 = localObject5;
      localObject1 = arrayOfString;
      if (localObject4 != null)
      {
        localObject2 = localObject5;
        localObject1 = arrayOfString;
        if (localAnnotationIntrospector != null)
        {
          localObject1 = localAnnotationIntrospector.findKeySerializer((Annotated)localObject4);
          if (localObject1 != null) {
            localObject3 = paramSerializerProvider.serializerInstance((Annotated)localObject4, localObject1);
          }
          localObject5 = localAnnotationIntrospector.findContentSerializer((Annotated)localObject4);
          localObject2 = localObject3;
          localObject1 = arrayOfString;
          if (localObject5 != null)
          {
            localObject1 = paramSerializerProvider.serializerInstance((Annotated)localObject4, localObject5);
            localObject2 = localObject3;
          }
        }
      }
      localObject3 = localObject1;
      if (localObject1 == null) {
        localObject3 = this._valueSerializer;
      }
      localObject1 = findConvertingContentSerializer(paramSerializerProvider, paramBeanProperty, (JsonSerializer)localObject3);
      if (localObject1 != null) {
        break label316;
      }
      if (((this._valueTypeIsStatic) && (this._valueType.getRawClass() != Object.class)) || (hasContentTypeAnnotation(paramSerializerProvider, paramBeanProperty))) {
        localObject1 = paramSerializerProvider.findValueSerializer(this._valueType, paramBeanProperty);
      }
      label177:
      localObject3 = localObject2;
      if (localObject2 == null) {
        localObject3 = this._keySerializer;
      }
      if (localObject3 != null) {
        break label328;
      }
      localObject3 = paramSerializerProvider.findKeySerializer(this._keyType, paramBeanProperty);
      label208:
      localObject2 = this._ignoredEntries;
      boolean bool2 = false;
      paramSerializerProvider = (SerializerProvider)localObject2;
      bool1 = bool2;
      if (localAnnotationIntrospector == null) {
        break label379;
      }
      paramSerializerProvider = (SerializerProvider)localObject2;
      bool1 = bool2;
      if (localObject4 == null) {
        break label379;
      }
      arrayOfString = localAnnotationIntrospector.findPropertiesToIgnore((Annotated)localObject4);
      paramSerializerProvider = (SerializerProvider)localObject2;
      if (arrayOfString == null) {
        break label354;
      }
      if (localObject2 != null) {
        break label340;
      }
    }
    label316:
    label328:
    label340:
    for (Object localObject2 = new HashSet();; localObject2 = new HashSet((Collection)localObject2))
    {
      int j = arrayOfString.length;
      int i = 0;
      for (;;)
      {
        paramSerializerProvider = (SerializerProvider)localObject2;
        if (i >= j) {
          break;
        }
        ((HashSet)localObject2).add(arrayOfString[i]);
        i += 1;
      }
      localObject4 = paramBeanProperty.getMember();
      break;
      localObject1 = paramSerializerProvider.handleSecondaryContextualization((JsonSerializer)localObject1, paramBeanProperty);
      break label177;
      localObject3 = paramSerializerProvider.handleSecondaryContextualization((JsonSerializer)localObject3, paramBeanProperty);
      break label208;
    }
    label354:
    localObject2 = localAnnotationIntrospector.findSerializationSortAlphabetically((Annotated)localObject4);
    if ((localObject2 != null) && (((Boolean)localObject2).booleanValue())) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      label379:
      localObject1 = withResolved(paramBeanProperty, (JsonSerializer)localObject3, (JsonSerializer)localObject1, paramSerializerProvider, bool1);
      paramSerializerProvider = (SerializerProvider)localObject1;
      if (paramBeanProperty != null)
      {
        paramBeanProperty = localAnnotationIntrospector.findFilterId(paramBeanProperty.getMember());
        paramSerializerProvider = (SerializerProvider)localObject1;
        if (paramBeanProperty != null) {
          paramSerializerProvider = ((MapSerializer)localObject1).withFilterId(paramBeanProperty);
        }
      }
      return paramSerializerProvider;
    }
  }
  
  public JsonSerializer<?> getContentSerializer()
  {
    return this._valueSerializer;
  }
  
  public JavaType getContentType()
  {
    return this._valueType;
  }
  
  public JsonSerializer<?> getKeySerializer()
  {
    return this._keySerializer;
  }
  
  public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
  {
    return createSchemaNode("object", true);
  }
  
  public boolean hasSingleElement(Map<?, ?> paramMap)
  {
    return paramMap.size() == 1;
  }
  
  public boolean isEmpty(Map<?, ?> paramMap)
  {
    return (paramMap == null) || (paramMap.isEmpty());
  }
  
  public void serialize(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    paramJsonGenerator.writeStartObject();
    Object localObject;
    if (!paramMap.isEmpty())
    {
      if (this._filterId != null)
      {
        serializeFilteredFields(paramMap, paramJsonGenerator, paramSerializerProvider, findPropertyFilter(paramSerializerProvider, this._filterId, paramMap));
        paramJsonGenerator.writeEndObject();
        return;
      }
      if (!this._sortKeys)
      {
        localObject = paramMap;
        if (!paramSerializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {}
      }
      else
      {
        localObject = _orderEntries(paramMap);
      }
      if (this._valueSerializer == null) {
        break label93;
      }
      serializeFieldsUsing((Map)localObject, paramJsonGenerator, paramSerializerProvider, this._valueSerializer);
    }
    for (;;)
    {
      paramJsonGenerator.writeEndObject();
      return;
      label93:
      serializeFields((Map)localObject, paramJsonGenerator, paramSerializerProvider);
    }
  }
  
  public void serializeFields(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    if (this._valueTypeSerializer != null)
    {
      serializeTypedFields(paramMap, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    JsonSerializer localJsonSerializer2 = this._keySerializer;
    HashSet localHashSet = this._ignoredEntries;
    int i;
    label59:
    Object localObject5;
    Object localObject6;
    if (!paramSerializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES))
    {
      i = 1;
      localObject1 = this._dynamicValueSerializers;
      Iterator localIterator = paramMap.entrySet().iterator();
      if (!localIterator.hasNext()) {
        break label298;
      }
      localObject3 = (Map.Entry)localIterator.next();
      localObject5 = ((Map.Entry)localObject3).getValue();
      localObject6 = ((Map.Entry)localObject3).getKey();
      if (localObject6 != null) {
        break label141;
      }
      paramSerializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, paramJsonGenerator, paramSerializerProvider);
    }
    for (;;)
    {
      if (localObject5 != null) {
        break label178;
      }
      paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
      break label59;
      i = 0;
      break;
      label141:
      if (((i != 0) && (localObject5 == null)) || ((localHashSet != null) && (localHashSet.contains(localObject6)))) {
        break label59;
      }
      localJsonSerializer2.serialize(localObject6, paramJsonGenerator, paramSerializerProvider);
    }
    label178:
    Class localClass = localObject5.getClass();
    JsonSerializer localJsonSerializer1 = ((PropertySerializerMap)localObject1).serializerFor(localClass);
    Object localObject4 = localJsonSerializer1;
    Object localObject3 = localObject1;
    if (localJsonSerializer1 == null) {
      if (!this._valueType.hasGenericTypes()) {
        break label300;
      }
    }
    Object localObject2;
    label298:
    label300:
    for (Object localObject1 = _findAndAddDynamic((PropertySerializerMap)localObject1, paramSerializerProvider.constructSpecializedType(this._valueType, localClass), paramSerializerProvider);; localObject2 = _findAndAddDynamic((PropertySerializerMap)localObject2, localClass, paramSerializerProvider))
    {
      localObject3 = this._dynamicValueSerializers;
      localObject4 = localObject1;
      try
      {
        ((JsonSerializer)localObject4).serialize(localObject5, paramJsonGenerator, paramSerializerProvider);
        localObject1 = localObject3;
      }
      catch (Exception localException)
      {
        wrapAndThrow(paramSerializerProvider, localException, paramMap, "" + localObject6);
        localObject2 = localObject3;
      }
      break label59;
      break;
    }
  }
  
  protected void serializeFieldsUsing(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, JsonSerializer<Object> paramJsonSerializer)
    throws IOException, JsonGenerationException
  {
    JsonSerializer localJsonSerializer = this._keySerializer;
    HashSet localHashSet = this._ignoredEntries;
    TypeSerializer localTypeSerializer = this._valueTypeSerializer;
    int i;
    Iterator localIterator;
    if (!paramSerializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES))
    {
      i = 1;
      localIterator = paramMap.entrySet().iterator();
    }
    for (;;)
    {
      label44:
      if (!localIterator.hasNext()) {
        return;
      }
      Object localObject2 = (Map.Entry)localIterator.next();
      Object localObject1 = ((Map.Entry)localObject2).getValue();
      localObject2 = ((Map.Entry)localObject2).getKey();
      if (localObject2 == null) {
        paramSerializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, paramJsonGenerator, paramSerializerProvider);
      }
      for (;;)
      {
        if (localObject1 != null) {
          break label163;
        }
        paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
        break label44;
        i = 0;
        break;
        if (((i != 0) && (localObject1 == null)) || ((localHashSet != null) && (localHashSet.contains(localObject2)))) {
          break label44;
        }
        localJsonSerializer.serialize(localObject2, paramJsonGenerator, paramSerializerProvider);
      }
      label163:
      if (localTypeSerializer == null) {
        try
        {
          paramJsonSerializer.serialize(localObject1, paramJsonGenerator, paramSerializerProvider);
        }
        catch (Exception localException)
        {
          wrapAndThrow(paramSerializerProvider, localException, paramMap, "" + localObject2);
        }
      } else {
        paramJsonSerializer.serializeWithType(localException, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
      }
    }
  }
  
  public void serializeFilteredFields(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, PropertyFilter paramPropertyFilter)
    throws IOException, JsonGenerationException
  {
    HashSet localHashSet = this._ignoredEntries;
    int i;
    Object localObject3;
    MapProperty localMapProperty;
    label51:
    Object localObject1;
    Object localObject5;
    Object localObject6;
    JsonSerializer localJsonSerializer1;
    label110:
    Object localObject4;
    if (!paramSerializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES))
    {
      i = 1;
      localObject3 = this._dynamicValueSerializers;
      localMapProperty = new MapProperty(this._valueTypeSerializer);
      Iterator localIterator = paramMap.entrySet().iterator();
      if (!localIterator.hasNext()) {
        return;
      }
      localObject1 = (Map.Entry)localIterator.next();
      localObject5 = ((Map.Entry)localObject1).getKey();
      localObject6 = ((Map.Entry)localObject1).getValue();
      if (localObject5 != null) {
        break label201;
      }
      localJsonSerializer1 = paramSerializerProvider.findNullKeySerializer(this._keyType, this._property);
      if (localObject6 != null) {
        break label235;
      }
      localObject1 = paramSerializerProvider.getDefaultNullValueSerializer();
      localObject4 = localObject3;
    }
    label201:
    label235:
    Class localClass;
    JsonSerializer localJsonSerializer2;
    do
    {
      localMapProperty.reset(localObject5, localObject6, localJsonSerializer1, (JsonSerializer)localObject1);
      try
      {
        paramPropertyFilter.serializeAsField(paramMap, paramJsonGenerator, paramSerializerProvider, localMapProperty);
        localObject3 = localObject4;
      }
      catch (Exception localException)
      {
        wrapAndThrow(paramSerializerProvider, localException, paramMap, "" + localObject5);
        localObject3 = localObject4;
      }
      break label51;
      i = 0;
      break;
      if (((i != 0) && (localObject6 == null)) || ((localHashSet != null) && (localHashSet.contains(localObject5)))) {
        break label51;
      }
      localJsonSerializer1 = this._keySerializer;
      break label110;
      localClass = localObject6.getClass();
      localJsonSerializer2 = ((PropertySerializerMap)localObject3).serializerFor(localClass);
      localObject4 = localObject3;
      localObject2 = localJsonSerializer2;
    } while (localJsonSerializer2 != null);
    if (this._valueType.hasGenericTypes()) {}
    for (Object localObject2 = _findAndAddDynamic((PropertySerializerMap)localObject3, paramSerializerProvider.constructSpecializedType(this._valueType, localClass), paramSerializerProvider);; localObject2 = _findAndAddDynamic((PropertySerializerMap)localObject3, localClass, paramSerializerProvider))
    {
      localObject4 = this._dynamicValueSerializers;
      break;
    }
  }
  
  protected void serializeTypedFields(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    JsonSerializer localJsonSerializer = this._keySerializer;
    Object localObject1 = null;
    Object localObject2 = null;
    HashSet localHashSet = this._ignoredEntries;
    int i;
    Iterator localIterator;
    if (!paramSerializerProvider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES))
    {
      i = 1;
      localIterator = paramMap.entrySet().iterator();
    }
    for (;;)
    {
      label44:
      if (localIterator.hasNext())
      {
        Object localObject3 = (Map.Entry)localIterator.next();
        Object localObject6 = ((Map.Entry)localObject3).getValue();
        Object localObject7 = ((Map.Entry)localObject3).getKey();
        if (localObject7 == null) {
          paramSerializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, paramJsonGenerator, paramSerializerProvider);
        }
        for (;;)
        {
          if (localObject6 != null) {
            break label163;
          }
          paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
          break label44;
          i = 0;
          break;
          if (((i != 0) && (localObject6 == null)) || ((localHashSet != null) && (localHashSet.contains(localObject7)))) {
            break label44;
          }
          localJsonSerializer.serialize(localObject7, paramJsonGenerator, paramSerializerProvider);
        }
        label163:
        Object localObject5 = localObject6.getClass();
        if (localObject5 == localObject2)
        {
          localObject3 = localObject1;
          localObject5 = localObject3;
          try
          {
            ((JsonSerializer)localObject5).serializeWithType(localObject6, paramJsonGenerator, paramSerializerProvider, this._valueTypeSerializer);
          }
          catch (Exception localException)
          {
            wrapAndThrow(paramSerializerProvider, localException, paramMap, "" + localObject7);
          }
        }
        else
        {
          if (this._valueType.hasGenericTypes()) {}
          for (localObject1 = paramSerializerProvider.findValueSerializer(paramSerializerProvider.constructSpecializedType(this._valueType, (Class)localObject5), this._property);; localObject1 = paramSerializerProvider.findValueSerializer((Class)localObject5, this._property))
          {
            Object localObject4 = localObject1;
            localObject2 = localObject5;
            localObject5 = localObject1;
            localObject1 = localObject4;
            break;
          }
        }
      }
    }
  }
  
  public void serializeWithType(Map<?, ?> paramMap, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException, JsonGenerationException
  {
    paramTypeSerializer.writeTypePrefixForObject(paramMap, paramJsonGenerator);
    Object localObject = paramMap;
    if (!paramMap.isEmpty())
    {
      if (!this._sortKeys)
      {
        localObject = paramMap;
        if (!paramSerializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {}
      }
      else
      {
        localObject = _orderEntries(paramMap);
      }
      if (this._valueSerializer == null) {
        break label74;
      }
      serializeFieldsUsing((Map)localObject, paramJsonGenerator, paramSerializerProvider, this._valueSerializer);
    }
    for (;;)
    {
      paramTypeSerializer.writeTypeSuffixForObject(localObject, paramJsonGenerator);
      return;
      label74:
      serializeFields((Map)localObject, paramJsonGenerator, paramSerializerProvider);
    }
  }
  
  public MapSerializer withFilterId(Object paramObject)
  {
    if (this._filterId == paramObject) {
      return this;
    }
    return new MapSerializer(this, paramObject, this._sortKeys);
  }
  
  @Deprecated
  public MapSerializer withResolved(BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer1, JsonSerializer<?> paramJsonSerializer2, HashSet<String> paramHashSet)
  {
    return withResolved(paramBeanProperty, paramJsonSerializer1, paramJsonSerializer2, paramHashSet, this._sortKeys);
  }
  
  public MapSerializer withResolved(BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer1, JsonSerializer<?> paramJsonSerializer2, HashSet<String> paramHashSet, boolean paramBoolean)
  {
    paramJsonSerializer1 = new MapSerializer(this, paramBeanProperty, paramJsonSerializer1, paramJsonSerializer2, paramHashSet);
    paramBeanProperty = paramJsonSerializer1;
    if (paramBoolean != paramJsonSerializer1._sortKeys) {
      paramBeanProperty = new MapSerializer(paramJsonSerializer1, this._filterId, paramBoolean);
    }
    return paramBeanProperty;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/std/MapSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */