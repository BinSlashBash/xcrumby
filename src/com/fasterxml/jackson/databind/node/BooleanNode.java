package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class BooleanNode
  extends ValueNode
{
  public static final BooleanNode FALSE = new BooleanNode(false);
  public static final BooleanNode TRUE = new BooleanNode(true);
  private final boolean _value;
  
  private BooleanNode(boolean paramBoolean)
  {
    this._value = paramBoolean;
  }
  
  public static BooleanNode getFalse()
  {
    return FALSE;
  }
  
  public static BooleanNode getTrue()
  {
    return TRUE;
  }
  
  public static BooleanNode valueOf(boolean paramBoolean)
  {
    if (paramBoolean) {
      return TRUE;
    }
    return FALSE;
  }
  
  public boolean asBoolean()
  {
    return this._value;
  }
  
  public boolean asBoolean(boolean paramBoolean)
  {
    return this._value;
  }
  
  public double asDouble(double paramDouble)
  {
    if (this._value) {
      return 1.0D;
    }
    return 0.0D;
  }
  
  public int asInt(int paramInt)
  {
    if (this._value) {
      return 1;
    }
    return 0;
  }
  
  public long asLong(long paramLong)
  {
    if (this._value) {
      return 1L;
    }
    return 0L;
  }
  
  public String asText()
  {
    if (this._value) {
      return "true";
    }
    return "false";
  }
  
  public JsonToken asToken()
  {
    if (this._value) {
      return JsonToken.VALUE_TRUE;
    }
    return JsonToken.VALUE_FALSE;
  }
  
  public boolean booleanValue()
  {
    return this._value;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (!(paramObject instanceof BooleanNode)) {
        return false;
      }
    } while (this._value == ((BooleanNode)paramObject)._value);
    return false;
  }
  
  public JsonNodeType getNodeType()
  {
    return JsonNodeType.BOOLEAN;
  }
  
  public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonProcessingException
  {
    paramJsonGenerator.writeBoolean(this._value);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/node/BooleanNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */