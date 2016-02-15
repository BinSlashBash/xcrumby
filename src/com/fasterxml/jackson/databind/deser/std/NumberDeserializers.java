package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

public class NumberDeserializers
{
  private static final HashSet<String> _classNames = new HashSet();
  
  static
  {
    Class[] arrayOfClass = new Class[11];
    arrayOfClass[0] = Boolean.class;
    arrayOfClass[1] = Byte.class;
    arrayOfClass[2] = Short.class;
    arrayOfClass[3] = Character.class;
    arrayOfClass[4] = Integer.class;
    arrayOfClass[5] = Long.class;
    arrayOfClass[6] = Float.class;
    arrayOfClass[7] = Double.class;
    arrayOfClass[8] = Number.class;
    arrayOfClass[9] = BigDecimal.class;
    arrayOfClass[10] = BigInteger.class;
    int j = arrayOfClass.length;
    int i = 0;
    while (i < j)
    {
      Class localClass = arrayOfClass[i];
      _classNames.add(localClass.getName());
      i += 1;
    }
  }
  
  public static JsonDeserializer<?> find(Class<?> paramClass, String paramString)
  {
    if (paramClass.isPrimitive())
    {
      if (paramClass == Integer.TYPE) {
        return IntegerDeserializer.primitiveInstance;
      }
      if (paramClass == Boolean.TYPE) {
        return BooleanDeserializer.primitiveInstance;
      }
      if (paramClass == Long.TYPE) {
        return LongDeserializer.primitiveInstance;
      }
      if (paramClass == Double.TYPE) {
        return DoubleDeserializer.primitiveInstance;
      }
      if (paramClass == Character.TYPE) {
        return CharacterDeserializer.primitiveInstance;
      }
      if (paramClass == Byte.TYPE) {
        return ByteDeserializer.primitiveInstance;
      }
      if (paramClass == Short.TYPE) {
        return ShortDeserializer.primitiveInstance;
      }
      if (paramClass == Float.TYPE) {
        return FloatDeserializer.primitiveInstance;
      }
    }
    else if (_classNames.contains(paramString))
    {
      if (paramClass == Integer.class) {
        return IntegerDeserializer.wrapperInstance;
      }
      if (paramClass == Boolean.class) {
        return BooleanDeserializer.wrapperInstance;
      }
      if (paramClass == Long.class) {
        return LongDeserializer.wrapperInstance;
      }
      if (paramClass == Double.class) {
        return DoubleDeserializer.wrapperInstance;
      }
      if (paramClass == Character.class) {
        return CharacterDeserializer.wrapperInstance;
      }
      if (paramClass == Byte.class) {
        return ByteDeserializer.wrapperInstance;
      }
      if (paramClass == Short.class) {
        return ShortDeserializer.wrapperInstance;
      }
      if (paramClass == Float.class) {
        return FloatDeserializer.wrapperInstance;
      }
      if (paramClass == Number.class) {
        return NumberDeserializer.instance;
      }
      if (paramClass == BigDecimal.class) {
        return BigDecimalDeserializer.instance;
      }
      if (paramClass == BigInteger.class) {
        return BigIntegerDeserializer.instance;
      }
    }
    else
    {
      return null;
    }
    throw new IllegalArgumentException("Internal error: can't find deserializer for " + paramClass.getName());
  }
  
  @JacksonStdImpl
  public static class BigDecimalDeserializer
    extends StdScalarDeserializer<BigDecimal>
  {
    public static final BigDecimalDeserializer instance = new BigDecimalDeserializer();
    
    public BigDecimalDeserializer()
    {
      super();
    }
    
    public BigDecimal deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      Object localObject = paramJsonParser.getCurrentToken();
      if ((localObject == JsonToken.VALUE_NUMBER_INT) || (localObject == JsonToken.VALUE_NUMBER_FLOAT)) {
        localObject = paramJsonParser.getDecimalValue();
      }
      BigDecimal localBigDecimal;
      do
      {
        return (BigDecimal)localObject;
        if (localObject == JsonToken.VALUE_STRING)
        {
          paramJsonParser = paramJsonParser.getText().trim();
          if (paramJsonParser.length() == 0) {
            return null;
          }
          try
          {
            localObject = new BigDecimal(paramJsonParser);
            return (BigDecimal)localObject;
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid representation");
          }
        }
        if ((localIllegalArgumentException != JsonToken.START_ARRAY) || (!paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS))) {
          break;
        }
        paramJsonParser.nextToken();
        localBigDecimal = deserialize(paramJsonParser, paramDeserializationContext);
      } while (paramJsonParser.nextToken() == JsonToken.END_ARRAY);
      throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'BigDecimal' value but there was more than a single value in the array");
      throw paramDeserializationContext.mappingException(this._valueClass, localBigDecimal);
    }
  }
  
  @JacksonStdImpl
  public static class BigIntegerDeserializer
    extends StdScalarDeserializer<BigInteger>
  {
    public static final BigIntegerDeserializer instance = new BigIntegerDeserializer();
    
    public BigIntegerDeserializer()
    {
      super();
    }
    
    public BigInteger deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      Object localObject = paramJsonParser.getCurrentToken();
      if (localObject == JsonToken.VALUE_NUMBER_INT) {
        switch (NumberDeserializers.1.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[paramJsonParser.getNumberType().ordinal()])
        {
        }
      }
      do
      {
        paramJsonParser = paramJsonParser.getText().trim();
        if (paramJsonParser.length() != 0) {
          break;
        }
        localObject = null;
        do
        {
          return (BigInteger)localObject;
          return BigInteger.valueOf(paramJsonParser.getLongValue());
          if (localObject == JsonToken.VALUE_NUMBER_FLOAT) {
            return paramJsonParser.getDecimalValue().toBigInteger();
          }
          if ((localObject != JsonToken.START_ARRAY) || (!paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS))) {
            break;
          }
          paramJsonParser.nextToken();
          localObject = deserialize(paramJsonParser, paramDeserializationContext);
        } while (paramJsonParser.nextToken() == JsonToken.END_ARRAY);
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'BigInteger' value but there was more than a single value in the array");
      } while (localObject == JsonToken.VALUE_STRING);
      throw paramDeserializationContext.mappingException(this._valueClass, (JsonToken)localObject);
      try
      {
        localObject = new BigInteger(paramJsonParser);
        return (BigInteger)localObject;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid representation");
      }
    }
  }
  
  @JacksonStdImpl
  public static final class BooleanDeserializer
    extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Boolean>
  {
    private static final BooleanDeserializer primitiveInstance = new BooleanDeserializer(Boolean.class, Boolean.FALSE);
    private static final long serialVersionUID = 1L;
    private static final BooleanDeserializer wrapperInstance = new BooleanDeserializer(Boolean.TYPE, null);
    
    public BooleanDeserializer(Class<Boolean> paramClass, Boolean paramBoolean)
    {
      super(paramBoolean);
    }
    
    public Boolean deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      return _parseBoolean(paramJsonParser, paramDeserializationContext);
    }
    
    public Boolean deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
      throws IOException, JsonProcessingException
    {
      return _parseBoolean(paramJsonParser, paramDeserializationContext);
    }
  }
  
  @JacksonStdImpl
  public static class ByteDeserializer
    extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Byte>
  {
    private static final ByteDeserializer primitiveInstance = new ByteDeserializer(Byte.TYPE, Byte.valueOf((byte)0));
    private static final long serialVersionUID = 1L;
    private static final ByteDeserializer wrapperInstance = new ByteDeserializer(Byte.class, null);
    
    public ByteDeserializer(Class<Byte> paramClass, Byte paramByte)
    {
      super(paramByte);
    }
    
    public Byte deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      return _parseByte(paramJsonParser, paramDeserializationContext);
    }
  }
  
  @JacksonStdImpl
  public static class CharacterDeserializer
    extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Character>
  {
    private static final CharacterDeserializer primitiveInstance = new CharacterDeserializer(Character.class, Character.valueOf('\000'));
    private static final long serialVersionUID = 1L;
    private static final CharacterDeserializer wrapperInstance = new CharacterDeserializer(Character.TYPE, null);
    
    public CharacterDeserializer(Class<Character> paramClass, Character paramCharacter)
    {
      super(paramCharacter);
    }
    
    public Character deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      Object localObject = paramJsonParser.getCurrentToken();
      if (localObject == JsonToken.VALUE_NUMBER_INT)
      {
        int i = paramJsonParser.getIntValue();
        if ((i >= 0) && (i <= 65535)) {
          return Character.valueOf((char)i);
        }
      }
      else if (localObject == JsonToken.VALUE_STRING)
      {
        paramJsonParser = paramJsonParser.getText();
        if (paramJsonParser.length() == 1) {
          return Character.valueOf(paramJsonParser.charAt(0));
        }
        if (paramJsonParser.length() == 0) {
          return (Character)getEmptyValue();
        }
      }
      else if ((localObject == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
      {
        paramJsonParser.nextToken();
        localObject = deserialize(paramJsonParser, paramDeserializationContext);
        if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
          throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._valueClass.getName() + "' value but there was more than a single value in the array");
        }
        return (Character)localObject;
      }
      throw paramDeserializationContext.mappingException(this._valueClass, (JsonToken)localObject);
    }
  }
  
  @JacksonStdImpl
  public static class DoubleDeserializer
    extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Double>
  {
    private static final DoubleDeserializer primitiveInstance = new DoubleDeserializer(Double.class, Double.valueOf(0.0D));
    private static final long serialVersionUID = 1L;
    private static final DoubleDeserializer wrapperInstance = new DoubleDeserializer(Double.TYPE, null);
    
    public DoubleDeserializer(Class<Double> paramClass, Double paramDouble)
    {
      super(paramDouble);
    }
    
    public Double deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      return _parseDouble(paramJsonParser, paramDeserializationContext);
    }
    
    public Double deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
      throws IOException, JsonProcessingException
    {
      return _parseDouble(paramJsonParser, paramDeserializationContext);
    }
  }
  
  @JacksonStdImpl
  public static class FloatDeserializer
    extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Float>
  {
    private static final FloatDeserializer primitiveInstance = new FloatDeserializer(Float.class, Float.valueOf(0.0F));
    private static final long serialVersionUID = 1L;
    private static final FloatDeserializer wrapperInstance = new FloatDeserializer(Float.TYPE, null);
    
    public FloatDeserializer(Class<Float> paramClass, Float paramFloat)
    {
      super(paramFloat);
    }
    
    public Float deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      return _parseFloat(paramJsonParser, paramDeserializationContext);
    }
  }
  
  @JacksonStdImpl
  public static final class IntegerDeserializer
    extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Integer>
  {
    private static final IntegerDeserializer primitiveInstance = new IntegerDeserializer(Integer.class, Integer.valueOf(0));
    private static final long serialVersionUID = 1L;
    private static final IntegerDeserializer wrapperInstance = new IntegerDeserializer(Integer.TYPE, null);
    
    public IntegerDeserializer(Class<Integer> paramClass, Integer paramInteger)
    {
      super(paramInteger);
    }
    
    public Integer deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      return _parseInteger(paramJsonParser, paramDeserializationContext);
    }
    
    public Integer deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
      throws IOException, JsonProcessingException
    {
      return _parseInteger(paramJsonParser, paramDeserializationContext);
    }
  }
  
  @JacksonStdImpl
  public static final class LongDeserializer
    extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Long>
  {
    private static final LongDeserializer primitiveInstance = new LongDeserializer(Long.class, Long.valueOf(0L));
    private static final long serialVersionUID = 1L;
    private static final LongDeserializer wrapperInstance = new LongDeserializer(Long.TYPE, null);
    
    public LongDeserializer(Class<Long> paramClass, Long paramLong)
    {
      super(paramLong);
    }
    
    public Long deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      return _parseLong(paramJsonParser, paramDeserializationContext);
    }
  }
  
  @JacksonStdImpl
  public static class NumberDeserializer
    extends StdScalarDeserializer<Number>
  {
    public static final NumberDeserializer instance = new NumberDeserializer();
    
    public NumberDeserializer()
    {
      super();
    }
    
    public Number deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      Object localObject = paramJsonParser.getCurrentToken();
      if (localObject == JsonToken.VALUE_NUMBER_INT)
      {
        if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
          return paramJsonParser.getBigIntegerValue();
        }
        return paramJsonParser.getNumberValue();
      }
      if (localObject == JsonToken.VALUE_NUMBER_FLOAT)
      {
        if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
          return paramJsonParser.getDecimalValue();
        }
        return Double.valueOf(paramJsonParser.getDoubleValue());
      }
      if (localObject == JsonToken.VALUE_STRING)
      {
        paramJsonParser = paramJsonParser.getText().trim();
        if (paramJsonParser.length() == 0) {
          return (Number)getEmptyValue();
        }
        if (_hasTextualNull(paramJsonParser)) {
          return (Number)getNullValue();
        }
        if (_isPosInf(paramJsonParser)) {
          return Double.valueOf(Double.POSITIVE_INFINITY);
        }
        if (_isNegInf(paramJsonParser)) {
          return Double.valueOf(Double.NEGATIVE_INFINITY);
        }
        if (_isNaN(paramJsonParser)) {
          return Double.valueOf(NaN.0D);
        }
        try
        {
          if (paramJsonParser.indexOf('.') < 0) {
            break label212;
          }
          if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS))
          {
            localObject = new BigDecimal(paramJsonParser);
            return (Number)localObject;
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid number");
        }
        return new Double(paramJsonParser);
        label212:
        if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
          return new BigInteger(paramJsonParser);
        }
        long l = Long.parseLong(paramJsonParser);
        if ((l <= 2147483647L) && (l >= -2147483648L)) {
          return Integer.valueOf((int)l);
        }
        return Long.valueOf(l);
      }
      Number localNumber;
      if ((localIllegalArgumentException == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
      {
        paramJsonParser.nextToken();
        localNumber = deserialize(paramJsonParser, paramDeserializationContext);
        if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
          throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._valueClass.getName() + "' value but there was more than a single value in the array");
        }
        return localNumber;
      }
      throw paramDeserializationContext.mappingException(this._valueClass, localNumber);
    }
    
    public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
      throws IOException, JsonProcessingException
    {
      switch (NumberDeserializers.1.$SwitchMap$com$fasterxml$jackson$core$JsonToken[paramJsonParser.getCurrentToken().ordinal()])
      {
      default: 
        return paramTypeDeserializer.deserializeTypedFromScalar(paramJsonParser, paramDeserializationContext);
      }
      return deserialize(paramJsonParser, paramDeserializationContext);
    }
  }
  
  protected static abstract class PrimitiveOrWrapperDeserializer<T>
    extends StdScalarDeserializer<T>
  {
    private static final long serialVersionUID = 1L;
    protected final T _nullValue;
    
    protected PrimitiveOrWrapperDeserializer(Class<T> paramClass, T paramT)
    {
      super();
      this._nullValue = paramT;
    }
    
    public final T getNullValue()
    {
      return (T)this._nullValue;
    }
  }
  
  @JacksonStdImpl
  public static class ShortDeserializer
    extends NumberDeserializers.PrimitiveOrWrapperDeserializer<Short>
  {
    private static final ShortDeserializer primitiveInstance = new ShortDeserializer(Short.class, Short.valueOf((short)0));
    private static final long serialVersionUID = 1L;
    private static final ShortDeserializer wrapperInstance = new ShortDeserializer(Short.TYPE, null);
    
    public ShortDeserializer(Class<Short> paramClass, Short paramShort)
    {
      super(paramShort);
    }
    
    public Short deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      return _parseShort(paramJsonParser, paramDeserializationContext);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/NumberDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */