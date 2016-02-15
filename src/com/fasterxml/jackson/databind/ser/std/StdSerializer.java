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
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public abstract class StdSerializer<T>
  extends JsonSerializer<T>
  implements JsonFormatVisitable, SchemaAware
{
  protected final Class<T> _handledType;
  
  protected StdSerializer(JavaType paramJavaType)
  {
    this._handledType = paramJavaType.getRawClass();
  }
  
  protected StdSerializer(Class<T> paramClass)
  {
    this._handledType = paramClass;
  }
  
  protected StdSerializer(Class<?> paramClass, boolean paramBoolean)
  {
    this._handledType = paramClass;
  }
  
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
    throws JsonMappingException
  {
    paramJsonFormatVisitorWrapper.expectAnyFormat(paramJavaType);
  }
  
  protected ObjectNode createObjectNode()
  {
    return JsonNodeFactory.instance.objectNode();
  }
  
  protected ObjectNode createSchemaNode(String paramString)
  {
    ObjectNode localObjectNode = createObjectNode();
    localObjectNode.put("type", paramString);
    return localObjectNode;
  }
  
  protected ObjectNode createSchemaNode(String paramString, boolean paramBoolean)
  {
    paramString = createSchemaNode(paramString);
    if (!paramBoolean) {
      if (paramBoolean) {
        break label26;
      }
    }
    label26:
    for (paramBoolean = true;; paramBoolean = false)
    {
      paramString.put("required", paramBoolean);
      return paramString;
    }
  }
  
  protected JsonSerializer<?> findConvertingContentSerializer(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer)
    throws JsonMappingException
  {
    Object localObject = paramSerializerProvider.getAnnotationIntrospector();
    if ((localObject != null) && (paramBeanProperty != null))
    {
      localObject = ((AnnotationIntrospector)localObject).findSerializationContentConverter(paramBeanProperty.getMember());
      if (localObject != null)
      {
        Converter localConverter = paramSerializerProvider.converterInstance(paramBeanProperty.getMember(), localObject);
        JavaType localJavaType = localConverter.getOutputType(paramSerializerProvider.getTypeFactory());
        localObject = paramJsonSerializer;
        if (paramJsonSerializer == null) {
          localObject = paramSerializerProvider.findValueSerializer(localJavaType, paramBeanProperty);
        }
        return new StdDelegatingSerializer(localConverter, localJavaType, (JsonSerializer)localObject);
      }
    }
    return paramJsonSerializer;
  }
  
  protected PropertyFilter findPropertyFilter(SerializerProvider paramSerializerProvider, Object paramObject1, Object paramObject2)
    throws JsonMappingException
  {
    paramSerializerProvider = paramSerializerProvider.getFilterProvider();
    if (paramSerializerProvider == null) {
      throw new JsonMappingException("Can not resolve PropertyFilter with id '" + paramObject1 + "'; no FilterProvider configured");
    }
    return paramSerializerProvider.findPropertyFilter(paramObject1, paramObject2);
  }
  
  public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    throws JsonMappingException
  {
    return createSchemaNode("string");
  }
  
  public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType, boolean paramBoolean)
    throws JsonMappingException
  {
    paramSerializerProvider = (ObjectNode)getSchema(paramSerializerProvider, paramType);
    if (!paramBoolean) {
      if (paramBoolean) {
        break label30;
      }
    }
    label30:
    for (paramBoolean = true;; paramBoolean = false)
    {
      paramSerializerProvider.put("required", paramBoolean);
      return paramSerializerProvider;
    }
  }
  
  public Class<T> handledType()
  {
    return this._handledType;
  }
  
  protected boolean isDefaultSerializer(JsonSerializer<?> paramJsonSerializer)
  {
    return ClassUtil.isJacksonStdImpl(paramJsonSerializer);
  }
  
  public abstract void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException;
  
  public void wrapAndThrow(SerializerProvider paramSerializerProvider, Throwable paramThrowable, Object paramObject, int paramInt)
    throws IOException
  {
    while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
      paramThrowable = paramThrowable.getCause();
    }
    if ((paramThrowable instanceof Error)) {
      throw ((Error)paramThrowable);
    }
    if ((paramSerializerProvider == null) || (paramSerializerProvider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS))) {}
    for (int i = 1; (paramThrowable instanceof IOException); i = 0)
    {
      if ((i != 0) && ((paramThrowable instanceof JsonMappingException))) {
        break label98;
      }
      throw ((IOException)paramThrowable);
    }
    if ((i == 0) && ((paramThrowable instanceof RuntimeException))) {
      throw ((RuntimeException)paramThrowable);
    }
    label98:
    throw JsonMappingException.wrapWithPath(paramThrowable, paramObject, paramInt);
  }
  
  public void wrapAndThrow(SerializerProvider paramSerializerProvider, Throwable paramThrowable, Object paramObject, String paramString)
    throws IOException
  {
    while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
      paramThrowable = paramThrowable.getCause();
    }
    if ((paramThrowable instanceof Error)) {
      throw ((Error)paramThrowable);
    }
    if ((paramSerializerProvider == null) || (paramSerializerProvider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS))) {}
    for (int i = 1; (paramThrowable instanceof IOException); i = 0)
    {
      if ((i != 0) && ((paramThrowable instanceof JsonMappingException))) {
        break label98;
      }
      throw ((IOException)paramThrowable);
    }
    if ((i == 0) && ((paramThrowable instanceof RuntimeException))) {
      throw ((RuntimeException)paramThrowable);
    }
    label98:
    throw JsonMappingException.wrapWithPath(paramThrowable, paramObject, paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/std/StdSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */