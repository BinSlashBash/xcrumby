package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import java.io.IOException;

public class MapProperty
  extends PropertyWriter
{
  protected Object _key;
  protected JsonSerializer<Object> _keySerializer;
  protected TypeSerializer _typeSerializer;
  protected Object _value;
  protected JsonSerializer<Object> _valueSerializer;
  
  public MapProperty(TypeSerializer paramTypeSerializer)
  {
    this._typeSerializer = paramTypeSerializer;
  }
  
  public void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor)
    throws JsonMappingException
  {}
  
  @Deprecated
  public void depositSchemaProperty(ObjectNode paramObjectNode, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {}
  
  public PropertyName getFullName()
  {
    return new PropertyName(getName());
  }
  
  public String getName()
  {
    if ((this._key instanceof String)) {
      return (String)this._key;
    }
    return String.valueOf(this._key);
  }
  
  public void reset(Object paramObject1, Object paramObject2, JsonSerializer<Object> paramJsonSerializer1, JsonSerializer<Object> paramJsonSerializer2)
  {
    this._key = paramObject1;
    this._value = paramObject2;
    this._keySerializer = paramJsonSerializer1;
    this._valueSerializer = paramJsonSerializer2;
  }
  
  public void serializeAsElement(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws Exception
  {
    if (this._typeSerializer == null)
    {
      this._valueSerializer.serialize(this._value, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    this._valueSerializer.serializeWithType(this._value, paramJsonGenerator, paramSerializerProvider, this._typeSerializer);
  }
  
  public void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException
  {
    this._keySerializer.serialize(this._key, paramJsonGenerator, paramSerializerProvider);
    if (this._typeSerializer == null)
    {
      this._valueSerializer.serialize(this._value, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    this._valueSerializer.serializeWithType(this._value, paramJsonGenerator, paramSerializerProvider, this._typeSerializer);
  }
  
  public void serializeAsOmittedField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws Exception
  {
    if (!paramJsonGenerator.canOmitFields()) {
      paramJsonGenerator.writeOmittedField(getName());
    }
  }
  
  public void serializeAsPlaceholder(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws Exception
  {
    paramJsonGenerator.writeNull();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/std/MapProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */