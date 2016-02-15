package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public final class NullNode
  extends ValueNode
{
  public static final NullNode instance = new NullNode();
  
  public static NullNode getInstance()
  {
    return instance;
  }
  
  public String asText()
  {
    return "null";
  }
  
  public String asText(String paramString)
  {
    return paramString;
  }
  
  public JsonToken asToken()
  {
    return JsonToken.VALUE_NULL;
  }
  
  public boolean equals(Object paramObject)
  {
    return paramObject == this;
  }
  
  public JsonNodeType getNodeType()
  {
    return JsonNodeType.NULL;
  }
  
  public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonProcessingException
  {
    paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/node/NullNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */