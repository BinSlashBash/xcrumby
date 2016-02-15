package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerNode
  extends NumericNode
{
  private static final BigInteger MAX_INTEGER;
  private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
  private static final BigInteger MIN_INTEGER = BigInteger.valueOf(-2147483648L);
  private static final BigInteger MIN_LONG;
  protected final BigInteger _value;
  
  static
  {
    MAX_INTEGER = BigInteger.valueOf(2147483647L);
    MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
  }
  
  public BigIntegerNode(BigInteger paramBigInteger)
  {
    this._value = paramBigInteger;
  }
  
  public static BigIntegerNode valueOf(BigInteger paramBigInteger)
  {
    return new BigIntegerNode(paramBigInteger);
  }
  
  public boolean asBoolean(boolean paramBoolean)
  {
    return !BigInteger.ZERO.equals(this._value);
  }
  
  public String asText()
  {
    return this._value.toString();
  }
  
  public JsonToken asToken()
  {
    return JsonToken.VALUE_NUMBER_INT;
  }
  
  public BigInteger bigIntegerValue()
  {
    return this._value;
  }
  
  public boolean canConvertToInt()
  {
    return (this._value.compareTo(MIN_INTEGER) >= 0) && (this._value.compareTo(MAX_INTEGER) <= 0);
  }
  
  public boolean canConvertToLong()
  {
    return (this._value.compareTo(MIN_LONG) >= 0) && (this._value.compareTo(MAX_LONG) <= 0);
  }
  
  public BigDecimal decimalValue()
  {
    return new BigDecimal(this._value);
  }
  
  public double doubleValue()
  {
    return this._value.doubleValue();
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
    } while (!(paramObject instanceof BigIntegerNode));
    return ((BigIntegerNode)paramObject)._value.equals(this._value);
  }
  
  public float floatValue()
  {
    return this._value.floatValue();
  }
  
  public int hashCode()
  {
    return this._value.hashCode();
  }
  
  public int intValue()
  {
    return this._value.intValue();
  }
  
  public boolean isBigInteger()
  {
    return true;
  }
  
  public boolean isIntegralNumber()
  {
    return true;
  }
  
  public long longValue()
  {
    return this._value.longValue();
  }
  
  public JsonParser.NumberType numberType()
  {
    return JsonParser.NumberType.BIG_INTEGER;
  }
  
  public Number numberValue()
  {
    return this._value;
  }
  
  public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonProcessingException
  {
    paramJsonGenerator.writeNumber(this._value);
  }
  
  public short shortValue()
  {
    return this._value.shortValue();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/node/BigIntegerNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */