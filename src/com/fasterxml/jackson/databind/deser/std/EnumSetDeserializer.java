package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.EnumSet;

public class EnumSetDeserializer
  extends StdDeserializer<EnumSet<?>>
  implements ContextualDeserializer
{
  private static final long serialVersionUID = 3479455075597887177L;
  protected final Class<Enum> _enumClass;
  protected JsonDeserializer<Enum<?>> _enumDeserializer;
  protected final JavaType _enumType;
  
  public EnumSetDeserializer(JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer)
  {
    super(EnumSet.class);
    this._enumType = paramJavaType;
    this._enumClass = paramJavaType.getRawClass();
    this._enumDeserializer = paramJsonDeserializer;
  }
  
  private EnumSet constructSet()
  {
    return EnumSet.noneOf(this._enumClass);
  }
  
  public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    JsonDeserializer localJsonDeserializer = this._enumDeserializer;
    if (localJsonDeserializer == null) {}
    for (paramDeserializationContext = paramDeserializationContext.findContextualValueDeserializer(this._enumType, paramBeanProperty);; paramDeserializationContext = paramDeserializationContext.handleSecondaryContextualization(localJsonDeserializer, paramBeanProperty)) {
      return withDeserializer(paramDeserializationContext);
    }
  }
  
  public EnumSet<?> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (!paramJsonParser.isExpectedStartArrayToken()) {
      throw paramDeserializationContext.mappingException(EnumSet.class);
    }
    EnumSet localEnumSet = constructSet();
    for (;;)
    {
      Object localObject = paramJsonParser.nextToken();
      if (localObject == JsonToken.END_ARRAY) {
        break;
      }
      if (localObject == JsonToken.VALUE_NULL) {
        throw paramDeserializationContext.mappingException(this._enumClass);
      }
      localObject = (Enum)this._enumDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      if (localObject != null) {
        localEnumSet.add(localObject);
      }
    }
    return localEnumSet;
  }
  
  public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
    throws IOException, JsonProcessingException
  {
    return paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
  }
  
  public boolean isCachable()
  {
    return true;
  }
  
  public EnumSetDeserializer withDeserializer(JsonDeserializer<?> paramJsonDeserializer)
  {
    if (this._enumDeserializer == paramJsonDeserializer) {
      return this;
    }
    return new EnumSetDeserializer(this._enumType, paramJsonDeserializer);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/EnumSetDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */