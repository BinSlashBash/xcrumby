package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.IOException;
import java.lang.reflect.Method;

public class EnumDeserializer
  extends StdScalarDeserializer<Enum<?>>
{
  private static final long serialVersionUID = -5893263645879532318L;
  protected final EnumResolver<?> _resolver;
  
  public EnumDeserializer(EnumResolver<?> paramEnumResolver)
  {
    super(Enum.class);
    this._resolver = paramEnumResolver;
  }
  
  private final Enum<?> _deserializeAltString(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, String paramString)
    throws IOException
  {
    String str = paramString.trim();
    if (str.length() == 0)
    {
      if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
        break label73;
      }
      paramJsonParser = null;
    }
    for (;;)
    {
      return paramJsonParser;
      int i = str.charAt(0);
      if ((i >= 48) && (i <= 57)) {}
      try
      {
        i = Integer.parseInt(str);
        paramString = this._resolver.getEnum(i);
        paramJsonParser = paramString;
        if (paramString != null) {}
      }
      catch (NumberFormatException paramJsonParser)
      {
        label73:
        for (;;) {}
      }
    }
    if (!paramDeserializationContext.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
      throw paramDeserializationContext.weirdStringException(str, this._resolver.getEnumClass(), "value not one of declared Enum instance names: " + this._resolver.getEnums());
    }
    return null;
  }
  
  private final Enum<?> _deserializeOther(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    Enum localEnum;
    if ((paramJsonParser.getCurrentToken() == JsonToken.START_ARRAY) && (paramDeserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)))
    {
      paramJsonParser.nextToken();
      localEnum = deserialize(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._resolver.getEnumClass().getName() + "' value but there was more than a single value in the array");
      }
    }
    else
    {
      throw paramDeserializationContext.mappingException(this._resolver.getEnumClass());
    }
    return localEnum;
  }
  
  public static JsonDeserializer<?> deserializerForCreator(DeserializationConfig paramDeserializationConfig, Class<?> paramClass, AnnotatedMethod paramAnnotatedMethod)
  {
    Class localClass = paramAnnotatedMethod.getRawParameterType(0);
    if (localClass == String.class) {
      localClass = null;
    }
    for (;;)
    {
      if (paramDeserializationConfig.canOverrideAccessModifiers()) {
        ClassUtil.checkAndFixAccess(paramAnnotatedMethod.getMember());
      }
      return new FactoryBasedDeserializer(paramClass, paramAnnotatedMethod, localClass);
      if ((localClass == Integer.TYPE) || (localClass == Integer.class))
      {
        localClass = Integer.class;
      }
      else
      {
        if ((localClass != Long.TYPE) && (localClass != Long.class)) {
          break;
        }
        localClass = Long.class;
      }
    }
    throw new IllegalArgumentException("Parameter #0 type for factory method (" + paramAnnotatedMethod + ") not suitable, must be java.lang.String or int/Integer/long/Long");
  }
  
  public Enum<?> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    Object localObject = paramJsonParser.getCurrentToken();
    if ((localObject == JsonToken.VALUE_STRING) || (localObject == JsonToken.FIELD_NAME))
    {
      String str = paramJsonParser.getText();
      Enum localEnum = this._resolver.findEnum(str);
      localObject = localEnum;
      if (localEnum == null) {
        localObject = _deserializeAltString(paramJsonParser, paramDeserializationContext, str);
      }
    }
    int i;
    do
    {
      do
      {
        return (Enum<?>)localObject;
        if (localObject != JsonToken.VALUE_NUMBER_INT) {
          break;
        }
        if (paramDeserializationContext.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
          throw paramDeserializationContext.mappingException("Not allowed to deserialize Enum value out of JSON number (disable DeserializationConfig.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS to allow)");
        }
        i = paramJsonParser.getIntValue();
        paramJsonParser = this._resolver.getEnum(i);
        localObject = paramJsonParser;
      } while (paramJsonParser != null);
      localObject = paramJsonParser;
    } while (paramDeserializationContext.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL));
    throw paramDeserializationContext.weirdNumberException(Integer.valueOf(i), this._resolver.getEnumClass(), "index value outside legal index range [0.." + this._resolver.lastValidIndex() + "]");
    return _deserializeOther(paramJsonParser, paramDeserializationContext);
  }
  
  public boolean isCachable()
  {
    return true;
  }
  
  protected static class FactoryBasedDeserializer
    extends StdScalarDeserializer<Object>
  {
    private static final long serialVersionUID = -7775129435872564122L;
    protected final Class<?> _enumClass;
    protected final Method _factory;
    protected final Class<?> _inputType;
    
    public FactoryBasedDeserializer(Class<?> paramClass1, AnnotatedMethod paramAnnotatedMethod, Class<?> paramClass2)
    {
      super();
      this._enumClass = paramClass1;
      this._factory = paramAnnotatedMethod.getAnnotated();
      this._inputType = paramClass2;
    }
    
    public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException, JsonProcessingException
    {
      if (this._inputType == null) {
        paramJsonParser = paramJsonParser.getText();
      }
      for (;;)
      {
        try
        {
          paramJsonParser = this._factory.invoke(this._enumClass, new Object[] { paramJsonParser });
          return paramJsonParser;
        }
        catch (Exception paramJsonParser)
        {
          paramJsonParser = ClassUtil.getRootCause(paramJsonParser);
          if (!(paramJsonParser instanceof IOException)) {
            continue;
          }
          throw ((IOException)paramJsonParser);
          throw paramDeserializationContext.instantiationException(this._enumClass, paramJsonParser);
        }
        if (this._inputType == Integer.class)
        {
          paramJsonParser = Integer.valueOf(paramJsonParser.getValueAsInt());
        }
        else
        {
          if (this._inputType != Long.class) {
            continue;
          }
          paramJsonParser = Long.valueOf(paramJsonParser.getValueAsLong());
        }
      }
      throw paramDeserializationContext.mappingException(this._enumClass);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/EnumDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */