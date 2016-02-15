package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class POJONode
  extends ValueNode
{
  protected final Object _value;
  
  public POJONode(Object paramObject)
  {
    this._value = paramObject;
  }
  
  protected boolean _pojoEquals(POJONode paramPOJONode)
  {
    if (this._value == null) {
      return paramPOJONode._value == null;
    }
    return this._value.equals(paramPOJONode._value);
  }
  
  public boolean asBoolean(boolean paramBoolean)
  {
    boolean bool = paramBoolean;
    if (this._value != null)
    {
      bool = paramBoolean;
      if ((this._value instanceof Boolean)) {
        bool = ((Boolean)this._value).booleanValue();
      }
    }
    return bool;
  }
  
  public double asDouble(double paramDouble)
  {
    if ((this._value instanceof Number)) {
      paramDouble = ((Number)this._value).doubleValue();
    }
    return paramDouble;
  }
  
  public int asInt(int paramInt)
  {
    if ((this._value instanceof Number)) {
      paramInt = ((Number)this._value).intValue();
    }
    return paramInt;
  }
  
  public long asLong(long paramLong)
  {
    if ((this._value instanceof Number)) {
      paramLong = ((Number)this._value).longValue();
    }
    return paramLong;
  }
  
  public String asText()
  {
    if (this._value == null) {
      return "null";
    }
    return this._value.toString();
  }
  
  public String asText(String paramString)
  {
    if (this._value == null) {
      return paramString;
    }
    return this._value.toString();
  }
  
  public JsonToken asToken()
  {
    return JsonToken.VALUE_EMBEDDED_OBJECT;
  }
  
  public byte[] binaryValue()
    throws IOException
  {
    if ((this._value instanceof byte[])) {
      return (byte[])this._value;
    }
    return super.binaryValue();
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    boolean bool1;
    if (paramObject == this) {
      bool1 = true;
    }
    do
    {
      do
      {
        return bool1;
        bool1 = bool2;
      } while (paramObject == null);
      bool1 = bool2;
    } while (!(paramObject instanceof POJONode));
    return _pojoEquals((POJONode)paramObject);
  }
  
  public JsonNodeType getNodeType()
  {
    return JsonNodeType.POJO;
  }
  
  public Object getPojo()
  {
    return this._value;
  }
  
  public int hashCode()
  {
    return this._value.hashCode();
  }
  
  public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonProcessingException
  {
    if (this._value == null)
    {
      paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
      return;
    }
    paramJsonGenerator.writeObject(this._value);
  }
  
  public String toString()
  {
    return String.valueOf(this._value);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/node/POJONode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */