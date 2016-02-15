package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;

public class AsArrayTypeDeserializer
  extends TypeDeserializerBase
  implements Serializable
{
  private static final long serialVersionUID = 5345570420394408290L;
  
  public AsArrayTypeDeserializer(JavaType paramJavaType, TypeIdResolver paramTypeIdResolver, String paramString, boolean paramBoolean, Class<?> paramClass)
  {
    super(paramJavaType, paramTypeIdResolver, paramString, paramBoolean, paramClass);
  }
  
  public AsArrayTypeDeserializer(AsArrayTypeDeserializer paramAsArrayTypeDeserializer, BeanProperty paramBeanProperty)
  {
    super(paramAsArrayTypeDeserializer, paramBeanProperty);
  }
  
  private final Object _deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    Object localObject1;
    if (paramJsonParser.canReadTypeId())
    {
      localObject1 = paramJsonParser.getTypeId();
      if (localObject1 != null) {
        paramJsonParser = _deserializeWithNativeTypeId(paramJsonParser, paramDeserializationContext, localObject1);
      }
    }
    do
    {
      boolean bool;
      Object localObject2;
      do
      {
        return paramJsonParser;
        bool = paramJsonParser.isExpectedStartArrayToken();
        String str = _locateTypeId(paramJsonParser, paramDeserializationContext);
        localObject2 = _findDeserializer(paramDeserializationContext, str);
        localObject1 = paramJsonParser;
        if (this._typeIdVisible)
        {
          localObject1 = paramJsonParser;
          if (paramJsonParser.getCurrentToken() == JsonToken.START_OBJECT)
          {
            localObject1 = new TokenBuffer(null, false);
            ((TokenBuffer)localObject1).writeStartObject();
            ((TokenBuffer)localObject1).writeFieldName(this._typePropertyName);
            ((TokenBuffer)localObject1).writeString(str);
            localObject1 = JsonParserSequence.createFlattened(((TokenBuffer)localObject1).asParser(paramJsonParser), paramJsonParser);
            ((JsonParser)localObject1).nextToken();
          }
        }
        localObject2 = ((JsonDeserializer)localObject2).deserialize((JsonParser)localObject1, paramDeserializationContext);
        paramJsonParser = (JsonParser)localObject2;
      } while (!bool);
      paramJsonParser = (JsonParser)localObject2;
    } while (((JsonParser)localObject1).nextToken() == JsonToken.END_ARRAY);
    throw paramDeserializationContext.wrongTokenException((JsonParser)localObject1, JsonToken.END_ARRAY, "expected closing END_ARRAY after type information and deserialized value");
  }
  
  protected final String _locateTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    if (!paramJsonParser.isExpectedStartArrayToken())
    {
      if (this._defaultImpl != null) {
        return this._idResolver.idFromBaseType();
      }
      throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.START_ARRAY, "need JSON Array to contain As.WRAPPER_ARRAY type information for class " + baseTypeName());
    }
    if (paramJsonParser.nextToken() == JsonToken.VALUE_STRING)
    {
      paramDeserializationContext = paramJsonParser.getText();
      paramJsonParser.nextToken();
      return paramDeserializationContext;
    }
    if (this._defaultImpl != null) {
      return this._idResolver.idFromBaseType();
    }
    throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.VALUE_STRING, "need JSON String that contains type id (for subtype of " + baseTypeName() + ")");
  }
  
  public Object deserializeTypedFromAny(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    return _deserialize(paramJsonParser, paramDeserializationContext);
  }
  
  public Object deserializeTypedFromArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    return _deserialize(paramJsonParser, paramDeserializationContext);
  }
  
  public Object deserializeTypedFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    return _deserialize(paramJsonParser, paramDeserializationContext);
  }
  
  public Object deserializeTypedFromScalar(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    return _deserialize(paramJsonParser, paramDeserializationContext);
  }
  
  public TypeDeserializer forProperty(BeanProperty paramBeanProperty)
  {
    if (paramBeanProperty == this._property) {
      return this;
    }
    return new AsArrayTypeDeserializer(this, paramBeanProperty);
  }
  
  public JsonTypeInfo.As getTypeInclusion()
  {
    return JsonTypeInfo.As.WRAPPER_ARRAY;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsontype/impl/AsArrayTypeDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */