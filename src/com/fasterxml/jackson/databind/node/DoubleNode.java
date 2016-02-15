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

public class DoubleNode
  extends NumericNode
{
  protected final double _value;
  
  public DoubleNode(double paramDouble)
  {
    this._value = paramDouble;
  }
  
  public static DoubleNode valueOf(double paramDouble)
  {
    return new DoubleNode(paramDouble);
  }
  
  public String asText()
  {
    return NumberOutput.toString(this._value);
  }
  
  public JsonToken asToken()
  {
    return JsonToken.VALUE_NUMBER_FLOAT;
  }
  
  public BigInteger bigIntegerValue()
  {
    return decimalValue().toBigInteger();
  }
  
  public boolean canConvertToInt()
  {
    return (this._value >= -2.147483648E9D) && (this._value <= 2.147483647E9D);
  }
  
  public boolean canConvertToLong()
  {
    return (this._value >= -9.223372036854776E18D) && (this._value <= 9.223372036854776E18D);
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
    double d;
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (!(paramObject instanceof DoubleNode)) {
        break;
      }
      d = ((DoubleNode)paramObject)._value;
    } while (Double.compare(this._value, d) == 0);
    return false;
    return false;
  }
  
  public float floatValue()
  {
    return (float)this._value;
  }
  
  public int hashCode()
  {
    long l = Double.doubleToLongBits(this._value);
    return (int)l ^ (int)(l >> 32);
  }
  
  public int intValue()
  {
    return (int)this._value;
  }
  
  public boolean isDouble()
  {
    return true;
  }
  
  public boolean isFloatingPointNumber()
  {
    return true;
  }
  
  public long longValue()
  {
    return this._value;
  }
  
  public JsonParser.NumberType numberType()
  {
    return JsonParser.NumberType.DOUBLE;
  }
  
  public Number numberValue()
  {
    return Double.valueOf(this._value);
  }
  
  public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonProcessingException
  {
    paramJsonGenerator.writeNumber(this._value);
  }
  
  public short shortValue()
  {
    return (short)(int)this._value;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/node/DoubleNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */