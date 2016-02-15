package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public class JSONPObject
  implements JsonSerializable
{
  protected final String _function;
  protected final JavaType _serializationType;
  protected final Object _value;
  
  public JSONPObject(String paramString, Object paramObject)
  {
    this(paramString, paramObject, (JavaType)null);
  }
  
  public JSONPObject(String paramString, Object paramObject, JavaType paramJavaType)
  {
    this._function = paramString;
    this._value = paramObject;
    this._serializationType = paramJavaType;
  }
  
  public String getFunction()
  {
    return this._function;
  }
  
  public JavaType getSerializationType()
  {
    return this._serializationType;
  }
  
  public Object getValue()
  {
    return this._value;
  }
  
  public void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonProcessingException
  {
    paramJsonGenerator.writeRaw(this._function);
    paramJsonGenerator.writeRaw('(');
    if (this._value == null) {
      paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
    }
    for (;;)
    {
      paramJsonGenerator.writeRaw(')');
      return;
      if (this._serializationType != null) {
        paramSerializerProvider.findTypedValueSerializer(this._serializationType, true, null).serialize(this._value, paramJsonGenerator, paramSerializerProvider);
      } else {
        paramSerializerProvider.findTypedValueSerializer(this._value.getClass(), true, null).serialize(this._value, paramJsonGenerator, paramSerializerProvider);
      }
    }
  }
  
  public void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException, JsonProcessingException
  {
    serialize(paramJsonGenerator, paramSerializerProvider);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/JSONPObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */