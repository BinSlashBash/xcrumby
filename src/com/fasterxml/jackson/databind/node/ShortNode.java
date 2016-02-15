package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ShortNode
  extends NumericNode
{
  protected final short _value;
  
  public ShortNode(short paramShort)
  {
    this._value = paramShort;
  }
  
  public static ShortNode valueOf(short paramShort)
  {
    return new ShortNode(paramShort);
  }
  
  public boolean asBoolean(boolean paramBoolean)
  {
    return this._value != 0;
  }
  
  public String asText()
  {
    return NumberOutput.toString(this._value);
  }
  
  public JsonToken asToken()
  {
    return JsonToken.VALUE_NUMBER_INT;
  }
  
  public BigInteger bigIntegerValue()
  {
    return BigInteger.valueOf(this._value);
  }
  
  public boolean canConvertToInt()
  {
    return true;
  }
  
  public boolean canConvertToLong()
  {
    return true;
  }
  
  public BigDecimal decimalValue()
  {
    return BigDecimal.valueOf(this._value);
  }
  
  public double doubleValue()
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
      if (!(paramObject instanceof ShortNode)) {
        break;
      }
    } while (((ShortNode)paramObject)._value == this._value);
    return false;
    return false;
  }
  
  public float floatValue()
  {
    return this._value;
  }
  
  public int hashCode()
  {
    return this._value;
  }
  
  public int intValue()
  {
    return this._value;
  }
  
  public boolean isIntegralNumber()
  {
    return true;
  }
  
  public boolean isShort()
  {
    return true;
  }
  
  public long longValue()
  {
    return this._value;
  }
  
  public JsonParser.NumberType numberType()
  {
    return JsonParser.NumberType.INT;
  }
  
  public Number numberValue()
  {
    return Short.valueOf(this._value);
  }
  
  public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonProcessingException
  {
    paramJsonGenerator.writeNumber(this._value);
  }
  
  public short shortValue()
  {
    return this._value;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/node/ShortNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */