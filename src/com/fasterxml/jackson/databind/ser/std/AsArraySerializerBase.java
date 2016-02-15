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
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap.SerializerAndMapResult;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AsArraySerializerBase<T>
  extends ContainerSerializer<T>
  implements ContextualSerializer
{
  protected PropertySerializerMap _dynamicSerializers;
  protected final JsonSerializer<Object> _elementSerializer;
  protected final JavaType _elementType;
  protected final BeanProperty _property;
  protected final boolean _staticTyping;
  protected final TypeSerializer _valueTypeSerializer;
  
  protected AsArraySerializerBase(AsArraySerializerBase<?> paramAsArraySerializerBase, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
  {
    super(paramAsArraySerializerBase);
    this._elementType = paramAsArraySerializerBase._elementType;
    this._staticTyping = paramAsArraySerializerBase._staticTyping;
    this._valueTypeSerializer = paramTypeSerializer;
    this._property = paramBeanProperty;
    this._elementSerializer = paramJsonSerializer;
    this._dynamicSerializers = paramAsArraySerializerBase._dynamicSerializers;
  }
  
  protected AsArraySerializerBase(Class<?> paramClass, JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty, JsonSerializer<Object> paramJsonSerializer)
  {
    super(paramClass, false);
    this._elementType = paramJavaType;
    if (!paramBoolean)
    {
      paramBoolean = bool;
      if (paramJavaType != null)
      {
        paramBoolean = bool;
        if (!paramJavaType.isFinal()) {}
      }
    }
    else
    {
      paramBoolean = true;
    }
    this._staticTyping = paramBoolean;
    this._valueTypeSerializer = paramTypeSerializer;
    this._property = paramBeanProperty;
    this._elementSerializer = paramJsonSerializer;
    this._dynamicSerializers = PropertySerializerMap.emptyMap();
  }
  
  protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, JavaType paramJavaType, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    paramJavaType = paramPropertySerializerMap.findAndAddSecondarySerializer(paramJavaType, paramSerializerProvider, this._property);
    if (paramPropertySerializerMap != paramJavaType.map) {
      this._dynamicSerializers = paramJavaType.map;
    }
    return paramJavaType.serializer;
  }
  
  protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, Class<?> paramClass, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    paramClass = paramPropertySerializerMap.findAndAddSecondarySerializer(paramClass, paramSerializerProvider, this._property);
    if (paramPropertySerializerMap != paramClass.map) {
      this._dynamicSerializers = paramClass.map;
    }
    return paramClass.serializer;
  }
  
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
    throws JsonMappingException
  {
    JsonArrayFormatVisitor localJsonArrayFormatVisitor;
    if (paramJsonFormatVisitorWrapper == null) {
      localJsonArrayFormatVisitor = null;
    }
    while (localJsonArrayFormatVisitor != null)
    {
      JavaType localJavaType = paramJsonFormatVisitorWrapper.getProvider().getTypeFactory().moreSpecificType(this._elementType, paramJavaType.getContentType());
      if (localJavaType == null)
      {
        throw new JsonMappingException("Could not resolve type");
        localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
      }
      else
      {
        JsonSerializer localJsonSerializer = this._elementSerializer;
        paramJavaType = localJsonSerializer;
        if (localJsonSerializer == null) {
          paramJavaType = paramJsonFormatVisitorWrapper.getProvider().findValueSerializer(localJavaType, this._property);
        }
        localJsonArrayFormatVisitor.itemsFormat(paramJavaType, localJavaType);
      }
    }
  }
  
  public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    Object localObject1 = this._valueTypeSerializer;
    Object localObject2 = localObject1;
    if (localObject1 != null) {
      localObject2 = ((TypeSerializer)localObject1).forProperty(paramBeanProperty);
    }
    Object localObject3 = null;
    localObject1 = localObject3;
    if (paramBeanProperty != null)
    {
      AnnotatedMember localAnnotatedMember = paramBeanProperty.getMember();
      localObject1 = localObject3;
      if (localAnnotatedMember != null)
      {
        Object localObject4 = paramSerializerProvider.getAnnotationIntrospector().findContentSerializer(localAnnotatedMember);
        localObject1 = localObject3;
        if (localObject4 != null) {
          localObject1 = paramSerializerProvider.serializerInstance(localAnnotatedMember, localObject4);
        }
      }
    }
    localObject3 = localObject1;
    if (localObject1 == null) {
      localObject3 = this._elementSerializer;
    }
    localObject3 = findConvertingContentSerializer(paramSerializerProvider, paramBeanProperty, (JsonSerializer)localObject3);
    if (localObject3 == null)
    {
      localObject1 = localObject3;
      if (this._elementType != null) {
        if ((!this._staticTyping) || (this._elementType.getRawClass() == Object.class))
        {
          localObject1 = localObject3;
          if (!hasContentTypeAnnotation(paramSerializerProvider, paramBeanProperty)) {
            break label152;
          }
        }
      }
    }
    for (localObject1 = paramSerializerProvider.findValueSerializer(this._elementType, paramBeanProperty);; localObject1 = paramSerializerProvider.handleSecondaryContextualization((JsonSerializer)localObject3, paramBeanProperty))
    {
      label152:
      if ((localObject1 == this._elementSerializer) && (paramBeanProperty == this._property))
      {
        paramSerializerProvider = this;
        if (this._valueTypeSerializer == localObject2) {}
      }
      else
      {
        paramSerializerProvider = withResolved(paramBeanProperty, (TypeSerializer)localObject2, (JsonSerializer)localObject1);
      }
      return paramSerializerProvider;
    }
  }
  
  public JsonSerializer<?> getContentSerializer()
  {
    return this._elementSerializer;
  }
  
  public JavaType getContentType()
  {
    return this._elementType;
  }
  
  public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    throws JsonMappingException
  {
    ObjectNode localObjectNode = createSchemaNode("array", true);
    Object localObject1 = null;
    if (paramType != null)
    {
      localObject2 = paramSerializerProvider.constructType(paramType).getContentType();
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = localObject2;
        if ((paramType instanceof ParameterizedType))
        {
          paramType = ((ParameterizedType)paramType).getActualTypeArguments();
          localObject1 = localObject2;
          if (paramType.length == 1) {
            localObject1 = paramSerializerProvider.constructType(paramType[0]);
          }
        }
      }
    }
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject2 = localObject1;
      if (this._elementType != null) {
        localObject2 = this._elementType;
      }
    }
    if (localObject2 != null)
    {
      localObject1 = null;
      paramType = (Type)localObject1;
      if (((JavaType)localObject2).getRawClass() != Object.class)
      {
        localObject2 = paramSerializerProvider.findValueSerializer((JavaType)localObject2, this._property);
        paramType = (Type)localObject1;
        if ((localObject2 instanceof SchemaAware)) {
          paramType = ((SchemaAware)localObject2).getSchema(paramSerializerProvider, null);
        }
      }
      paramSerializerProvider = paramType;
      if (paramType == null) {
        paramSerializerProvider = JsonSchema.getDefaultSchemaNode();
      }
      localObjectNode.put("items", paramSerializerProvider);
    }
    return localObjectNode;
  }
  
  public final void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    if ((paramSerializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) && (hasSingleElement(paramT)))
    {
      serializeContents(paramT, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    paramJsonGenerator.writeStartArray();
    serializeContents(paramT, paramJsonGenerator, paramSerializerProvider);
    paramJsonGenerator.writeEndArray();
  }
  
  protected abstract void serializeContents(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException;
  
  public void serializeWithType(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException, JsonGenerationException
  {
    paramTypeSerializer.writeTypePrefixForArray(paramT, paramJsonGenerator);
    serializeContents(paramT, paramJsonGenerator, paramSerializerProvider);
    paramTypeSerializer.writeTypeSuffixForArray(paramT, paramJsonGenerator);
  }
  
  public abstract AsArraySerializerBase<T> withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/std/AsArraySerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */