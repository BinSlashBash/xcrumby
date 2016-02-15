package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

public abstract class JsonSerializer<T>
  implements JsonFormatVisitable
{
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
    throws JsonMappingException
  {
    if (paramJsonFormatVisitorWrapper != null) {
      paramJsonFormatVisitorWrapper.expectAnyFormat(paramJavaType);
    }
  }
  
  public JsonSerializer<?> getDelegatee()
  {
    return null;
  }
  
  public Class<T> handledType()
  {
    return null;
  }
  
  public boolean isEmpty(T paramT)
  {
    return paramT == null;
  }
  
  public boolean isUnwrappingSerializer()
  {
    return false;
  }
  
  public JsonSerializer<T> replaceDelegatee(JsonSerializer<?> paramJsonSerializer)
  {
    throw new UnsupportedOperationException();
  }
  
  public abstract void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonProcessingException;
  
  public void serializeWithType(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException, JsonProcessingException
  {
    paramSerializerProvider = handledType();
    paramJsonGenerator = paramSerializerProvider;
    if (paramSerializerProvider == null) {
      paramJsonGenerator = paramT.getClass();
    }
    throw new UnsupportedOperationException("Type id handling not implemented for type " + paramJsonGenerator.getName());
  }
  
  public JsonSerializer<T> unwrappingSerializer(NameTransformer paramNameTransformer)
  {
    return this;
  }
  
  public boolean usesObjectId()
  {
    return false;
  }
  
  public static abstract class None
    extends JsonSerializer<Object>
  {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/JsonSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */