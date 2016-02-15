package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.EnumMap;

public class EnumMapDeserializer
  extends StdDeserializer<EnumMap<?, ?>>
  implements ContextualDeserializer
{
  private static final long serialVersionUID = 4564890642370311174L;
  protected final Class<?> _enumClass;
  protected JsonDeserializer<Enum<?>> _keyDeserializer;
  protected final JavaType _mapType;
  protected JsonDeserializer<Object> _valueDeserializer;
  protected final TypeDeserializer _valueTypeDeserializer;
  
  public EnumMapDeserializer(JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2, TypeDeserializer paramTypeDeserializer)
  {
    super(EnumMap.class);
    this._mapType = paramJavaType;
    this._enumClass = paramJavaType.getKeyType().getRawClass();
    this._keyDeserializer = paramJsonDeserializer1;
    this._valueDeserializer = paramJsonDeserializer2;
    this._valueTypeDeserializer = paramTypeDeserializer;
  }
  
  private EnumMap<?, ?> constructMap()
  {
    return new EnumMap(this._enumClass);
  }
  
  public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    Object localObject2 = this._keyDeserializer;
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = paramDeserializationContext.findContextualValueDeserializer(this._mapType.getKeyType(), paramBeanProperty);
    }
    localObject2 = this._valueDeserializer;
    if (localObject2 == null) {}
    for (paramDeserializationContext = paramDeserializationContext.findContextualValueDeserializer(this._mapType.getContentType(), paramBeanProperty);; paramDeserializationContext = paramDeserializationContext.handleSecondaryContextualization((JsonDeserializer)localObject2, paramBeanProperty))
    {
      TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
      localObject2 = localTypeDeserializer;
      if (localTypeDeserializer != null) {
        localObject2 = localTypeDeserializer.forProperty(paramBeanProperty);
      }
      return withResolved((JsonDeserializer)localObject1, paramDeserializationContext, (TypeDeserializer)localObject2);
    }
  }
  
  public EnumMap<?, ?> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (paramJsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
      throw paramDeserializationContext.mappingException(EnumMap.class);
    }
    EnumMap localEnumMap = constructMap();
    JsonDeserializer localJsonDeserializer = this._valueDeserializer;
    TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
    for (;;)
    {
      Enum localEnum;
      Object localObject;
      if (paramJsonParser.nextToken() != JsonToken.END_OBJECT)
      {
        localEnum = (Enum)this._keyDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
        if (localEnum == null) {
          if (!paramDeserializationContext.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL))
          {
            localEnumMap = null;
            localObject = localEnumMap;
          }
        }
      }
      try
      {
        if (paramJsonParser.hasCurrentToken()) {
          localObject = paramJsonParser.getText();
        }
        throw paramDeserializationContext.weirdStringException((String)localObject, this._enumClass, "value not one of declared Enum instance names");
        paramJsonParser.nextToken();
        paramJsonParser.skipChildren();
        continue;
        if (paramJsonParser.nextToken() == JsonToken.VALUE_NULL) {
          localObject = localJsonDeserializer.getNullValue();
        }
        for (;;)
        {
          localEnumMap.put(localEnum, localObject);
          break;
          if (localTypeDeserializer == null) {
            localObject = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
          } else {
            localObject = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
          }
        }
        return localEnumMap;
      }
      catch (Exception paramJsonParser)
      {
        for (;;)
        {
          localObject = localEnumMap;
        }
      }
    }
  }
  
  public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
    throws IOException, JsonProcessingException
  {
    return paramTypeDeserializer.deserializeTypedFromObject(paramJsonParser, paramDeserializationContext);
  }
  
  public boolean isCachable()
  {
    return true;
  }
  
  public EnumMapDeserializer withResolved(JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2, TypeDeserializer paramTypeDeserializer)
  {
    if ((paramJsonDeserializer1 == this._keyDeserializer) && (paramJsonDeserializer2 == this._valueDeserializer) && (paramTypeDeserializer == this._valueTypeDeserializer)) {
      return this;
    }
    return new EnumMapDeserializer(this._mapType, paramJsonDeserializer1, paramJsonDeserializer2, this._valueTypeDeserializer);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/EnumMapDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */