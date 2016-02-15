package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StaticListSerializerBase;
import java.io.IOException;
import java.util.List;

@JacksonStdImpl
public final class IndexedStringListSerializer
  extends StaticListSerializerBase<List<String>>
  implements ContextualSerializer
{
  public static final IndexedStringListSerializer instance = new IndexedStringListSerializer();
  protected final JsonSerializer<String> _serializer;
  
  protected IndexedStringListSerializer()
  {
    this(null);
  }
  
  public IndexedStringListSerializer(JsonSerializer<?> paramJsonSerializer)
  {
    super(List.class);
    this._serializer = paramJsonSerializer;
  }
  
  private final void _serializeUnwrapped(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException
  {
    if (this._serializer == null)
    {
      serializeContents(paramList, paramJsonGenerator, paramSerializerProvider, 1);
      return;
    }
    serializeUsingCustom(paramList, paramJsonGenerator, paramSerializerProvider, 1);
  }
  
  private final void serializeContents(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, int paramInt)
    throws IOException
  {
    int i = 0;
    for (;;)
    {
      if (i < paramInt) {
        try
        {
          String str = (String)paramList.get(i);
          if (str == null) {
            paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
          } else {
            paramJsonGenerator.writeString(str);
          }
        }
        catch (Exception paramJsonGenerator)
        {
          wrapAndThrow(paramSerializerProvider, paramJsonGenerator, paramList, i);
        }
      }
      return;
      i += 1;
    }
  }
  
  private final void serializeUsingCustom(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, int paramInt)
    throws IOException
  {
    int j = 0;
    for (;;)
    {
      int i;
      try
      {
        JsonSerializer localJsonSerializer = this._serializer;
        i = 0;
        if (i < paramInt)
        {
          j = i;
          String str = (String)paramList.get(i);
          if (str == null)
          {
            j = i;
            paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
          }
          else
          {
            j = i;
            localJsonSerializer.serialize(str, paramJsonGenerator, paramSerializerProvider);
          }
        }
      }
      catch (Exception paramJsonGenerator)
      {
        wrapAndThrow(paramSerializerProvider, paramJsonGenerator, paramList, j);
      }
      return;
      i += 1;
    }
  }
  
  protected void acceptContentVisitor(JsonArrayFormatVisitor paramJsonArrayFormatVisitor)
    throws JsonMappingException
  {
    paramJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.STRING);
  }
  
  protected JsonNode contentSchema()
  {
    return createSchemaNode("string", true);
  }
  
  public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (paramBeanProperty != null)
    {
      AnnotatedMember localAnnotatedMember = paramBeanProperty.getMember();
      localObject1 = localObject2;
      if (localAnnotatedMember != null)
      {
        Object localObject3 = paramSerializerProvider.getAnnotationIntrospector().findContentSerializer(localAnnotatedMember);
        localObject1 = localObject2;
        if (localObject3 != null) {
          localObject1 = paramSerializerProvider.serializerInstance(localAnnotatedMember, localObject3);
        }
      }
    }
    localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = this._serializer;
    }
    localObject1 = findConvertingContentSerializer(paramSerializerProvider, paramBeanProperty, (JsonSerializer)localObject2);
    if (localObject1 == null) {}
    for (paramSerializerProvider = paramSerializerProvider.findValueSerializer(String.class, paramBeanProperty);; paramSerializerProvider = paramSerializerProvider.handleSecondaryContextualization((JsonSerializer)localObject1, paramBeanProperty))
    {
      paramBeanProperty = paramSerializerProvider;
      if (isDefaultSerializer(paramSerializerProvider)) {
        paramBeanProperty = null;
      }
      if (paramBeanProperty != this._serializer) {
        break;
      }
      return this;
    }
    return new IndexedStringListSerializer(paramBeanProperty);
  }
  
  public void serialize(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException
  {
    int i = paramList.size();
    if ((i == 1) && (paramSerializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)))
    {
      _serializeUnwrapped(paramList, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    paramJsonGenerator.writeStartArray();
    if (this._serializer == null) {
      serializeContents(paramList, paramJsonGenerator, paramSerializerProvider, i);
    }
    for (;;)
    {
      paramJsonGenerator.writeEndArray();
      return;
      serializeUsingCustom(paramList, paramJsonGenerator, paramSerializerProvider, i);
    }
  }
  
  public void serializeWithType(List<String> paramList, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException
  {
    int i = paramList.size();
    paramTypeSerializer.writeTypePrefixForArray(paramList, paramJsonGenerator);
    if (this._serializer == null) {
      serializeContents(paramList, paramJsonGenerator, paramSerializerProvider, i);
    }
    for (;;)
    {
      paramTypeSerializer.writeTypeSuffixForArray(paramList, paramJsonGenerator);
      return;
      serializeUsingCustom(paramList, paramJsonGenerator, paramSerializerProvider, i);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/impl/IndexedStringListSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */