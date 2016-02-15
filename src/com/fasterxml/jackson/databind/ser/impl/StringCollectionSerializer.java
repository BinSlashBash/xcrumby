package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
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
import java.util.Collection;
import java.util.Iterator;

@JacksonStdImpl
public class StringCollectionSerializer
  extends StaticListSerializerBase<Collection<String>>
  implements ContextualSerializer
{
  public static final StringCollectionSerializer instance = new StringCollectionSerializer();
  protected final JsonSerializer<String> _serializer;
  
  protected StringCollectionSerializer()
  {
    this(null);
  }
  
  protected StringCollectionSerializer(JsonSerializer<?> paramJsonSerializer)
  {
    super(Collection.class);
    this._serializer = paramJsonSerializer;
  }
  
  private final void _serializeUnwrapped(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    if (this._serializer == null)
    {
      serializeContents(paramCollection, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    serializeUsingCustom(paramCollection, paramJsonGenerator, paramSerializerProvider);
  }
  
  private final void serializeContents(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    if (this._serializer != null) {
      serializeUsingCustom(paramCollection, paramJsonGenerator, paramSerializerProvider);
    }
    for (;;)
    {
      return;
      int i = 0;
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str == null) {}
        try
        {
          paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
        }
        catch (Exception localException)
        {
          wrapAndThrow(paramSerializerProvider, localException, paramCollection, i);
        }
        paramJsonGenerator.writeString(str);
        break label85;
        continue;
        label85:
        i += 1;
      }
    }
  }
  
  private void serializeUsingCustom(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    JsonSerializer localJsonSerializer = this._serializer;
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str == null) {
        try
        {
          paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
        }
        catch (Exception localException)
        {
          wrapAndThrow(paramSerializerProvider, localException, paramCollection, 0);
        }
      } else {
        localJsonSerializer.serialize(localException, paramJsonGenerator, paramSerializerProvider);
      }
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
    return new StringCollectionSerializer(paramBeanProperty);
  }
  
  public void serialize(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    if ((paramCollection.size() == 1) && (paramSerializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)))
    {
      _serializeUnwrapped(paramCollection, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    paramJsonGenerator.writeStartArray();
    if (this._serializer == null) {
      serializeContents(paramCollection, paramJsonGenerator, paramSerializerProvider);
    }
    for (;;)
    {
      paramJsonGenerator.writeEndArray();
      return;
      serializeUsingCustom(paramCollection, paramJsonGenerator, paramSerializerProvider);
    }
  }
  
  public void serializeWithType(Collection<String> paramCollection, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException, JsonGenerationException
  {
    paramTypeSerializer.writeTypePrefixForArray(paramCollection, paramJsonGenerator);
    if (this._serializer == null) {
      serializeContents(paramCollection, paramJsonGenerator, paramSerializerProvider);
    }
    for (;;)
    {
      paramTypeSerializer.writeTypeSuffixForArray(paramCollection, paramJsonGenerator);
      return;
      serializeUsingCustom(paramCollection, paramJsonGenerator, paramSerializerProvider);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/impl/StringCollectionSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */