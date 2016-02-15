package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public abstract class StdDeserializer<T>
  extends JsonDeserializer<T>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected final Class<?> _valueClass;
  
  protected StdDeserializer(JavaType paramJavaType)
  {
    if (paramJavaType == null) {}
    for (paramJavaType = null;; paramJavaType = paramJavaType.getRawClass())
    {
      this._valueClass = paramJavaType;
      return;
    }
  }
  
  protected StdDeserializer(Class<?> paramClass)
  {
    this._valueClass = paramClass;
  }
  
  protected static final double parseDouble(String paramString)
    throws NumberFormatException
  {
    if ("2.2250738585072012e-308".equals(paramString)) {
      return Double.MIN_VALUE;
    }
    return Double.parseDouble(paramString);
  }
  
  protected boolean _hasTextualNull(String paramString)
  {
    return "null".equals(paramString);
  }
  
  protected final boolean _isNaN(String paramString)
  {
    return "NaN".equals(paramString);
  }
  
  protected final boolean _isNegInf(String paramString)
  {
    return ("-Infinity".equals(paramString)) || ("-INF".equals(paramString));
  }
  
  protected final boolean _isPosInf(String paramString)
  {
    return ("Infinity".equals(paramString)) || ("INF".equals(paramString));
  }
  
  protected final Boolean _parseBoolean(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    Object localObject = paramJsonParser.getCurrentToken();
    if (localObject == JsonToken.VALUE_TRUE) {
      return Boolean.TRUE;
    }
    if (localObject == JsonToken.VALUE_FALSE) {
      return Boolean.FALSE;
    }
    if (localObject == JsonToken.VALUE_NUMBER_INT)
    {
      if (paramJsonParser.getNumberType() == JsonParser.NumberType.INT)
      {
        if (paramJsonParser.getIntValue() == 0) {
          return Boolean.FALSE;
        }
        return Boolean.TRUE;
      }
      return Boolean.valueOf(_parseBooleanFromNumber(paramJsonParser, paramDeserializationContext));
    }
    if (localObject == JsonToken.VALUE_NULL) {
      return (Boolean)getNullValue();
    }
    if (localObject == JsonToken.VALUE_STRING)
    {
      paramJsonParser = paramJsonParser.getText().trim();
      if (("true".equals(paramJsonParser)) || ("True".equals(paramJsonParser))) {
        return Boolean.TRUE;
      }
      if (("false".equals(paramJsonParser)) || ("False".equals(paramJsonParser))) {
        return Boolean.FALSE;
      }
      if (paramJsonParser.length() == 0) {
        return (Boolean)getEmptyValue();
      }
      if (_hasTextualNull(paramJsonParser)) {
        return (Boolean)getNullValue();
      }
      throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "only \"true\" or \"false\" recognized");
    }
    if ((localObject == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      localObject = _parseBoolean(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Boolean' value but there was more than a single value in the array");
      }
      return (Boolean)localObject;
    }
    throw paramDeserializationContext.mappingException(this._valueClass, (JsonToken)localObject);
  }
  
  protected final boolean _parseBooleanFromNumber(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    if (paramJsonParser.getNumberType() == JsonParser.NumberType.LONG)
    {
      if (paramJsonParser.getLongValue() == 0L) {}
      for (paramJsonParser = Boolean.FALSE;; paramJsonParser = Boolean.TRUE) {
        return paramJsonParser.booleanValue();
      }
    }
    paramJsonParser = paramJsonParser.getText();
    if (("0.0".equals(paramJsonParser)) || ("0".equals(paramJsonParser))) {
      return Boolean.FALSE.booleanValue();
    }
    return Boolean.TRUE.booleanValue();
  }
  
  protected final boolean _parseBooleanPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    boolean bool2 = true;
    boolean bool3 = false;
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    boolean bool1;
    if (localJsonToken == JsonToken.VALUE_TRUE) {
      bool1 = true;
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                return bool1;
                bool1 = bool3;
              } while (localJsonToken == JsonToken.VALUE_FALSE);
              bool1 = bool3;
            } while (localJsonToken == JsonToken.VALUE_NULL);
            if (localJsonToken == JsonToken.VALUE_NUMBER_INT)
            {
              if (paramJsonParser.getNumberType() == JsonParser.NumberType.INT)
              {
                if (paramJsonParser.getIntValue() != 0) {}
                for (bool1 = bool2;; bool1 = false) {
                  return bool1;
                }
              }
              return _parseBooleanFromNumber(paramJsonParser, paramDeserializationContext);
            }
            if (localJsonToken != JsonToken.VALUE_STRING) {
              break;
            }
            paramJsonParser = paramJsonParser.getText().trim();
            if (("true".equals(paramJsonParser)) || ("True".equals(paramJsonParser))) {
              return true;
            }
            bool1 = bool3;
          } while ("false".equals(paramJsonParser));
          bool1 = bool3;
        } while ("False".equals(paramJsonParser));
        bool1 = bool3;
      } while (paramJsonParser.length() == 0);
      bool1 = bool3;
    } while (_hasTextualNull(paramJsonParser));
    throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "only \"true\" or \"false\" recognized");
    if ((localJsonToken == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      bool1 = _parseBooleanPrimitive(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'boolean' value but there was more than a single value in the array");
      }
      return bool1;
    }
    throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
  }
  
  protected Byte _parseByte(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      return Byte.valueOf(paramJsonParser.getByteValue());
    }
    if (localJsonToken == JsonToken.VALUE_STRING)
    {
      paramJsonParser = paramJsonParser.getText().trim();
      if (_hasTextualNull(paramJsonParser)) {
        return (Byte)getNullValue();
      }
      int i;
      try
      {
        if (paramJsonParser.length() == 0) {
          return (Byte)getEmptyValue();
        }
        i = NumberInput.parseInt(paramJsonParser);
        if ((i < -128) || (i > 255)) {
          throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "overflow, value can not be represented as 8-bit value");
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid Byte value");
      }
      return Byte.valueOf((byte)i);
    }
    if (localIllegalArgumentException == JsonToken.VALUE_NULL) {
      return (Byte)getNullValue();
    }
    Byte localByte;
    if ((localIllegalArgumentException == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      localByte = _parseByte(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Byte' value but there was more than a single value in the array");
      }
      return localByte;
    }
    throw paramDeserializationContext.mappingException(this._valueClass, localByte);
  }
  
  protected Date _parseDate(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    Object localObject = paramJsonParser.getCurrentToken();
    if (localObject == JsonToken.VALUE_NUMBER_INT) {
      return new Date(paramJsonParser.getLongValue());
    }
    if (localObject == JsonToken.VALUE_NULL) {
      return (Date)getNullValue();
    }
    if (localObject == JsonToken.VALUE_STRING)
    {
      localObject = null;
      try
      {
        paramJsonParser = paramJsonParser.getText().trim();
        localObject = paramJsonParser;
        if (paramJsonParser.length() == 0)
        {
          localObject = paramJsonParser;
          return (Date)getEmptyValue();
        }
        localObject = paramJsonParser;
        if (_hasTextualNull(paramJsonParser))
        {
          localObject = paramJsonParser;
          return (Date)getNullValue();
        }
        localObject = paramJsonParser;
        paramJsonParser = paramDeserializationContext.parseDate(paramJsonParser);
        return paramJsonParser;
      }
      catch (IllegalArgumentException paramJsonParser)
      {
        throw paramDeserializationContext.weirdStringException((String)localObject, this._valueClass, "not a valid representation (error: " + paramJsonParser.getMessage() + ")");
      }
    }
    if ((localObject == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      localObject = _parseDate(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'java.util.Date' value but there was more than a single value in the array");
      }
      return (Date)localObject;
    }
    throw paramDeserializationContext.mappingException(this._valueClass, (JsonToken)localObject);
  }
  
  protected final Double _parseDouble(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      return Double.valueOf(paramJsonParser.getDoubleValue());
    }
    if (localJsonToken == JsonToken.VALUE_STRING)
    {
      paramJsonParser = paramJsonParser.getText().trim();
      if (paramJsonParser.length() == 0) {
        return (Double)getEmptyValue();
      }
      if (_hasTextualNull(paramJsonParser)) {
        return (Double)getNullValue();
      }
      switch (paramJsonParser.charAt(0))
      {
      }
      for (;;)
      {
        try
        {
          double d = parseDouble(paramJsonParser);
          return Double.valueOf(d);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid Double value");
        }
        if (_isPosInf(paramJsonParser))
        {
          return Double.valueOf(Double.POSITIVE_INFINITY);
          if (_isNaN(paramJsonParser))
          {
            return Double.valueOf(NaN.0D);
            if (_isNegInf(paramJsonParser)) {
              return Double.valueOf(Double.NEGATIVE_INFINITY);
            }
          }
        }
      }
    }
    if (localIllegalArgumentException == JsonToken.VALUE_NULL) {
      return (Double)getNullValue();
    }
    Double localDouble;
    if ((localIllegalArgumentException == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      localDouble = _parseDouble(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Double' value but there was more than a single value in the array");
      }
      return localDouble;
    }
    throw paramDeserializationContext.mappingException(this._valueClass, localDouble);
  }
  
  protected final double _parseDoublePrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    double d2 = 0.0D;
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    double d1;
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      d1 = paramJsonParser.getDoubleValue();
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return d1;
            if (localJsonToken != JsonToken.VALUE_STRING) {
              break;
            }
            paramJsonParser = paramJsonParser.getText().trim();
            d1 = d2;
          } while (paramJsonParser.length() == 0);
          d1 = d2;
        } while (_hasTextualNull(paramJsonParser));
        switch (paramJsonParser.charAt(0))
        {
        }
        for (;;)
        {
          try
          {
            d1 = parseDouble(paramJsonParser);
            return d1;
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid double value");
          }
          if (_isPosInf(paramJsonParser))
          {
            return Double.POSITIVE_INFINITY;
            if (_isNaN(paramJsonParser))
            {
              return NaN.0D;
              if (_isNegInf(paramJsonParser)) {
                return Double.NEGATIVE_INFINITY;
              }
            }
          }
        }
        d1 = d2;
      } while (localIllegalArgumentException == JsonToken.VALUE_NULL);
      if ((localIllegalArgumentException != JsonToken.START_ARRAY) || (!paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS))) {
        break;
      }
      paramJsonParser.nextToken();
      d1 = _parseDoublePrimitive(paramJsonParser, paramDeserializationContext);
    } while (paramJsonParser.nextToken() == JsonToken.END_ARRAY);
    throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Byte' value but there was more than a single value in the array");
    throw paramDeserializationContext.mappingException(this._valueClass, localIllegalArgumentException);
  }
  
  protected final Float _parseFloat(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      return Float.valueOf(paramJsonParser.getFloatValue());
    }
    if (localJsonToken == JsonToken.VALUE_STRING)
    {
      paramJsonParser = paramJsonParser.getText().trim();
      if (paramJsonParser.length() == 0) {
        return (Float)getEmptyValue();
      }
      if (_hasTextualNull(paramJsonParser)) {
        return (Float)getNullValue();
      }
      switch (paramJsonParser.charAt(0))
      {
      }
      for (;;)
      {
        try
        {
          float f = Float.parseFloat(paramJsonParser);
          return Float.valueOf(f);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid Float value");
        }
        if (_isPosInf(paramJsonParser))
        {
          return Float.valueOf(Float.POSITIVE_INFINITY);
          if (_isNaN(paramJsonParser))
          {
            return Float.valueOf(NaN.0F);
            if (_isNegInf(paramJsonParser)) {
              return Float.valueOf(Float.NEGATIVE_INFINITY);
            }
          }
        }
      }
    }
    if (localIllegalArgumentException == JsonToken.VALUE_NULL) {
      return (Float)getNullValue();
    }
    Float localFloat;
    if ((localIllegalArgumentException == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      localFloat = _parseFloat(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Byte' value but there was more than a single value in the array");
      }
      return localFloat;
    }
    throw paramDeserializationContext.mappingException(this._valueClass, localFloat);
  }
  
  protected final float _parseFloatPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    float f2 = 0.0F;
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    float f1;
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      f1 = paramJsonParser.getFloatValue();
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return f1;
            if (localJsonToken != JsonToken.VALUE_STRING) {
              break;
            }
            paramJsonParser = paramJsonParser.getText().trim();
            f1 = f2;
          } while (paramJsonParser.length() == 0);
          f1 = f2;
        } while (_hasTextualNull(paramJsonParser));
        switch (paramJsonParser.charAt(0))
        {
        }
        for (;;)
        {
          try
          {
            f1 = Float.parseFloat(paramJsonParser);
            return f1;
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid float value");
          }
          if (_isPosInf(paramJsonParser))
          {
            return Float.POSITIVE_INFINITY;
            if (_isNaN(paramJsonParser))
            {
              return NaN.0F;
              if (_isNegInf(paramJsonParser)) {
                return Float.NEGATIVE_INFINITY;
              }
            }
          }
        }
        f1 = f2;
      } while (localIllegalArgumentException == JsonToken.VALUE_NULL);
      if ((localIllegalArgumentException != JsonToken.START_ARRAY) || (!paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS))) {
        break;
      }
      paramJsonParser.nextToken();
      f1 = _parseFloatPrimitive(paramJsonParser, paramDeserializationContext);
    } while (paramJsonParser.nextToken() == JsonToken.END_ARRAY);
    throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'float' value but there was more than a single value in the array");
    throw paramDeserializationContext.mappingException(this._valueClass, localIllegalArgumentException);
  }
  
  protected final int _parseIntPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    int j = 0;
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    int i;
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      i = paramJsonParser.getIntValue();
    }
    label175:
    do
    {
      do
      {
        int k;
        do
        {
          do
          {
            return i;
            if (localJsonToken != JsonToken.VALUE_STRING) {
              break;
            }
            paramJsonParser = paramJsonParser.getText().trim();
            i = j;
          } while (_hasTextualNull(paramJsonParser));
          long l;
          try
          {
            k = paramJsonParser.length();
            if (k <= 9) {
              break label175;
            }
            l = Long.parseLong(paramJsonParser);
            if ((l < -2147483648L) || (l > 2147483647L)) {
              throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "Overflow: numeric value (" + paramJsonParser + ") out of range of int (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
            }
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid int value");
          }
          return (int)l;
          i = j;
        } while (k == 0);
        i = NumberInput.parseInt(paramJsonParser);
        return i;
        i = j;
      } while (localIllegalArgumentException == JsonToken.VALUE_NULL);
      if ((localIllegalArgumentException != JsonToken.START_ARRAY) || (!paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS))) {
        break;
      }
      paramJsonParser.nextToken();
      i = _parseIntPrimitive(paramJsonParser, paramDeserializationContext);
    } while (paramJsonParser.nextToken() == JsonToken.END_ARRAY);
    throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'int' value but there was more than a single value in the array");
    throw paramDeserializationContext.mappingException(this._valueClass, localIllegalArgumentException);
  }
  
  protected final Integer _parseInteger(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      return Integer.valueOf(paramJsonParser.getIntValue());
    }
    if (localJsonToken == JsonToken.VALUE_STRING)
    {
      paramJsonParser = paramJsonParser.getText().trim();
      long l;
      try
      {
        i = paramJsonParser.length();
        if (_hasTextualNull(paramJsonParser)) {
          return (Integer)getNullValue();
        }
        if (i <= 9) {
          break label181;
        }
        l = Long.parseLong(paramJsonParser);
        if ((l < -2147483648L) || (l > 2147483647L)) {
          throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "Overflow: numeric value (" + paramJsonParser + ") out of range of Integer (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid Integer value");
      }
      int i = (int)l;
      return Integer.valueOf(i);
      label181:
      if (i == 0) {
        return (Integer)getEmptyValue();
      }
      i = NumberInput.parseInt(paramJsonParser);
      return Integer.valueOf(i);
    }
    if (localIllegalArgumentException == JsonToken.VALUE_NULL) {
      return (Integer)getNullValue();
    }
    Integer localInteger;
    if ((localIllegalArgumentException == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      localInteger = _parseInteger(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Integer' value but there was more than a single value in the array");
      }
      return localInteger;
    }
    throw paramDeserializationContext.mappingException(this._valueClass, localInteger);
  }
  
  protected final Long _parseLong(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      return Long.valueOf(paramJsonParser.getLongValue());
    }
    if (localJsonToken == JsonToken.VALUE_STRING)
    {
      paramJsonParser = paramJsonParser.getText().trim();
      if (paramJsonParser.length() == 0) {
        return (Long)getEmptyValue();
      }
      if (_hasTextualNull(paramJsonParser)) {
        return (Long)getNullValue();
      }
      try
      {
        long l = NumberInput.parseLong(paramJsonParser);
        return Long.valueOf(l);
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid Long value");
      }
    }
    if (localIllegalArgumentException == JsonToken.VALUE_NULL) {
      return (Long)getNullValue();
    }
    Long localLong;
    if ((localIllegalArgumentException == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      localLong = _parseLong(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Long' value but there was more than a single value in the array");
      }
      return localLong;
    }
    throw paramDeserializationContext.mappingException(this._valueClass, localLong);
  }
  
  protected final long _parseLongPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    long l2 = 0L;
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    long l1;
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      l1 = paramJsonParser.getLongValue();
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return l1;
            if (localJsonToken != JsonToken.VALUE_STRING) {
              break;
            }
            paramJsonParser = paramJsonParser.getText().trim();
            l1 = l2;
          } while (paramJsonParser.length() == 0);
          l1 = l2;
        } while (_hasTextualNull(paramJsonParser));
        try
        {
          l1 = NumberInput.parseLong(paramJsonParser);
          return l1;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid long value");
        }
        l1 = l2;
      } while (localIllegalArgumentException == JsonToken.VALUE_NULL);
      if ((localIllegalArgumentException != JsonToken.START_ARRAY) || (!paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS))) {
        break;
      }
      paramJsonParser.nextToken();
      l1 = _parseLongPrimitive(paramJsonParser, paramDeserializationContext);
    } while (paramJsonParser.nextToken() == JsonToken.END_ARRAY);
    throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'long' value but there was more than a single value in the array");
    throw paramDeserializationContext.mappingException(this._valueClass, localIllegalArgumentException);
  }
  
  protected Short _parseShort(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    if ((localJsonToken == JsonToken.VALUE_NUMBER_INT) || (localJsonToken == JsonToken.VALUE_NUMBER_FLOAT)) {
      return Short.valueOf(paramJsonParser.getShortValue());
    }
    if (localJsonToken == JsonToken.VALUE_STRING)
    {
      paramJsonParser = paramJsonParser.getText().trim();
      int i;
      try
      {
        if (paramJsonParser.length() == 0) {
          return (Short)getEmptyValue();
        }
        if (_hasTextualNull(paramJsonParser)) {
          return (Short)getNullValue();
        }
        i = NumberInput.parseInt(paramJsonParser);
        if ((i < 32768) || (i > 32767)) {
          throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "overflow, value can not be represented as 16-bit value");
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw paramDeserializationContext.weirdStringException(paramJsonParser, this._valueClass, "not a valid Short value");
      }
      return Short.valueOf((short)i);
    }
    if (localIllegalArgumentException == JsonToken.VALUE_NULL) {
      return (Short)getNullValue();
    }
    Short localShort;
    if ((localIllegalArgumentException == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      localShort = _parseShort(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Short' value but there was more than a single value in the array");
      }
      return localShort;
    }
    throw paramDeserializationContext.mappingException(this._valueClass, localShort);
  }
  
  protected final short _parseShortPrimitive(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    int i = _parseIntPrimitive(paramJsonParser, paramDeserializationContext);
    if ((i < 32768) || (i > 32767)) {
      throw paramDeserializationContext.weirdStringException(String.valueOf(i), this._valueClass, "overflow, value can not be represented as 16-bit value");
    }
    return (short)i;
  }
  
  protected final String _parseString(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    Object localObject = paramJsonParser.getCurrentToken();
    if (localObject == JsonToken.VALUE_STRING) {
      localObject = paramJsonParser.getText();
    }
    do
    {
      return (String)localObject;
      if ((localObject != JsonToken.START_ARRAY) || (!paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS))) {
        break;
      }
      paramJsonParser.nextToken();
      localObject = _parseString(paramJsonParser, paramDeserializationContext);
    } while (paramJsonParser.nextToken() == JsonToken.END_ARRAY);
    throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'String' value but there was more than a single value in the array");
    localObject = paramJsonParser.getValueAsString();
    if (localObject != null) {
      return (String)localObject;
    }
    throw paramDeserializationContext.mappingException(String.class, paramJsonParser.getCurrentToken());
  }
  
  public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
    throws IOException
  {
    return paramTypeDeserializer.deserializeTypedFromAny(paramJsonParser, paramDeserializationContext);
  }
  
  protected JsonDeserializer<?> findConvertingContentDeserializer(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty, JsonDeserializer<?> paramJsonDeserializer)
    throws JsonMappingException
  {
    Object localObject = paramDeserializationContext.getAnnotationIntrospector();
    if ((localObject != null) && (paramBeanProperty != null))
    {
      localObject = ((AnnotationIntrospector)localObject).findDeserializationContentConverter(paramBeanProperty.getMember());
      if (localObject != null)
      {
        Converter localConverter = paramDeserializationContext.converterInstance(paramBeanProperty.getMember(), localObject);
        JavaType localJavaType = localConverter.getInputType(paramDeserializationContext.getTypeFactory());
        localObject = paramJsonDeserializer;
        if (paramJsonDeserializer == null) {
          localObject = paramDeserializationContext.findContextualValueDeserializer(localJavaType, paramBeanProperty);
        }
        return new StdDelegatingDeserializer(localConverter, localJavaType, (JsonDeserializer)localObject);
      }
    }
    return paramJsonDeserializer;
  }
  
  protected JsonDeserializer<Object> findDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    return paramDeserializationContext.findContextualValueDeserializer(paramJavaType, paramBeanProperty);
  }
  
  @Deprecated
  public final Class<?> getValueClass()
  {
    return this._valueClass;
  }
  
  public JavaType getValueType()
  {
    return null;
  }
  
  protected void handleUnknownProperty(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, String paramString)
    throws IOException
  {
    Object localObject = paramObject;
    if (paramObject == null) {
      localObject = handledType();
    }
    if (paramDeserializationContext.handleUnknownProperty(paramJsonParser, this, localObject, paramString)) {
      return;
    }
    paramDeserializationContext.reportUnknownProperty(localObject, paramString, this);
    paramJsonParser.skipChildren();
  }
  
  public Class<?> handledType()
  {
    return this._valueClass;
  }
  
  protected boolean isDefaultDeserializer(JsonDeserializer<?> paramJsonDeserializer)
  {
    return ClassUtil.isJacksonStdImpl(paramJsonDeserializer);
  }
  
  protected boolean isDefaultKeyDeserializer(KeyDeserializer paramKeyDeserializer)
  {
    return ClassUtil.isJacksonStdImpl(paramKeyDeserializer);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/StdDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */