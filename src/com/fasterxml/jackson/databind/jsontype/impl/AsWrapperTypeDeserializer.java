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

public class AsWrapperTypeDeserializer
  extends TypeDeserializerBase
  implements Serializable
{
  private static final long serialVersionUID = 5345570420394408290L;
  
  public AsWrapperTypeDeserializer(JavaType paramJavaType, TypeIdResolver paramTypeIdResolver, String paramString, boolean paramBoolean, Class<?> paramClass)
  {
    super(paramJavaType, paramTypeIdResolver, paramString, paramBoolean, null);
  }
  
  protected AsWrapperTypeDeserializer(AsWrapperTypeDeserializer paramAsWrapperTypeDeserializer, BeanProperty paramBeanProperty)
  {
    super(paramAsWrapperTypeDeserializer, paramBeanProperty);
  }
  
  private final Object _deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    Object localObject;
    if (paramJsonParser.canReadTypeId())
    {
      localObject = paramJsonParser.getTypeId();
      if (localObject != null) {
        paramJsonParser = _deserializeWithNativeTypeId(paramJsonParser, paramDeserializationContext, localObject);
      }
    }
    do
    {
      return paramJsonParser;
      if (paramJsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.START_OBJECT, "need JSON Object to contain As.WRAPPER_OBJECT type information for class " + baseTypeName());
      }
      if (paramJsonParser.nextToken() != JsonToken.FIELD_NAME) {
        throw paramDeserializationContext.wrongTokenException(paramJsonParser, JsonToken.FIELD_NAME, "need JSON String that contains type id (for subtype of " + baseTypeName() + ")");
      }
      String str = paramJsonParser.getText();
      JsonDeserializer localJsonDeserializer = _findDeserializer(paramDeserializationContext, str);
      paramJsonParser.nextToken();
      localObject = paramJsonParser;
      if (this._typeIdVisible)
      {
        localObject = paramJsonParser;
        if (paramJsonParser.getCurrentToken() == JsonToken.START_OBJECT)
        {
          localObject = new TokenBuffer(null, false);
          ((TokenBuffer)localObject).writeStartObject();
          ((TokenBuffer)localObject).writeFieldName(this._typePropertyName);
          ((TokenBuffer)localObject).writeString(str);
          localObject = JsonParserSequence.createFlattened(((TokenBuffer)localObject).asParser(paramJsonParser), paramJsonParser);
          ((JsonParser)localObject).nextToken();
        }
      }
      paramJsonParser = localJsonDeserializer.deserialize((JsonParser)localObject, paramDeserializationContext);
    } while (((JsonParser)localObject).nextToken() == JsonToken.END_OBJECT);
    throw paramDeserializationContext.wrongTokenException((JsonParser)localObject, JsonToken.END_OBJECT, "expected closing END_OBJECT after type information and deserialized value");
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
    return new AsWrapperTypeDeserializer(this, paramBeanProperty);
  }
  
  public JsonTypeInfo.As getTypeInclusion()
  {
    return JsonTypeInfo.As.WRAPPER_OBJECT;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsontype/impl/AsWrapperTypeDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */