package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;

@JacksonStdImpl
public final class StringArrayDeserializer
  extends StdDeserializer<String[]>
  implements ContextualDeserializer
{
  public static final StringArrayDeserializer instance = new StringArrayDeserializer();
  private static final long serialVersionUID = -7589512013334920693L;
  protected JsonDeserializer<String> _elementDeserializer;
  
  public StringArrayDeserializer()
  {
    super(String[].class);
    this._elementDeserializer = null;
  }
  
  protected StringArrayDeserializer(JsonDeserializer<?> paramJsonDeserializer)
  {
    super(String[].class);
    this._elementDeserializer = paramJsonDeserializer;
  }
  
  private final String[] handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    Object localObject = null;
    if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY))
    {
      if ((paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING) && (paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) && (paramJsonParser.getText().length() == 0)) {
        return null;
      }
      throw paramDeserializationContext.mappingException(this._valueClass);
    }
    if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {}
    for (paramJsonParser = (JsonParser)localObject;; paramJsonParser = _parseString(paramJsonParser, paramDeserializationContext)) {
      return new String[] { paramJsonParser };
    }
  }
  
  protected final String[] _deserializeCustom(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
    Object localObject1 = localObjectBuffer.resetAndStart();
    JsonDeserializer localJsonDeserializer = this._elementDeserializer;
    int i = 0;
    Object localObject2 = paramJsonParser.nextToken();
    if (localObject2 != JsonToken.END_ARRAY)
    {
      if (localObject2 == JsonToken.VALUE_NULL) {}
      for (localObject2 = (String)localJsonDeserializer.getNullValue();; localObject2 = (String)localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext))
      {
        Object localObject3 = localObject1;
        int j = i;
        if (i >= localObject1.length)
        {
          localObject3 = localObjectBuffer.appendCompletedChunk((Object[])localObject1);
          j = 0;
        }
        localObject3[j] = localObject2;
        i = j + 1;
        localObject1 = localObject3;
        break;
      }
    }
    paramJsonParser = (String[])localObjectBuffer.completeAndClearBuffer((Object[])localObject1, i, String.class);
    paramDeserializationContext.returnObjectBuffer(localObjectBuffer);
    return paramJsonParser;
  }
  
  public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    JsonDeserializer localJsonDeserializer = findConvertingContentDeserializer(paramDeserializationContext, paramBeanProperty, this._elementDeserializer);
    if (localJsonDeserializer == null) {}
    for (paramDeserializationContext = paramDeserializationContext.findContextualValueDeserializer(paramDeserializationContext.constructType(String.class), paramBeanProperty);; paramDeserializationContext = paramDeserializationContext.handleSecondaryContextualization(localJsonDeserializer, paramBeanProperty))
    {
      paramBeanProperty = paramDeserializationContext;
      if (paramDeserializationContext != null)
      {
        paramBeanProperty = paramDeserializationContext;
        if (isDefaultDeserializer(paramDeserializationContext)) {
          paramBeanProperty = null;
        }
      }
      paramDeserializationContext = this;
      if (this._elementDeserializer != paramBeanProperty) {
        paramDeserializationContext = new StringArrayDeserializer(paramBeanProperty);
      }
      return paramDeserializationContext;
    }
  }
  
  public String[] deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    if (!paramJsonParser.isExpectedStartArrayToken()) {
      return handleNonArray(paramJsonParser, paramDeserializationContext);
    }
    if (this._elementDeserializer != null) {
      return _deserializeCustom(paramJsonParser, paramDeserializationContext);
    }
    ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
    Object localObject2 = localObjectBuffer.resetAndStart();
    int i = 0;
    Object localObject1 = paramJsonParser.nextToken();
    if (localObject1 != JsonToken.END_ARRAY)
    {
      if (localObject1 == JsonToken.VALUE_STRING) {
        localObject1 = paramJsonParser.getText();
      }
      for (;;)
      {
        Object localObject3 = localObject2;
        int j = i;
        if (i >= localObject2.length)
        {
          localObject3 = localObjectBuffer.appendCompletedChunk((Object[])localObject2);
          j = 0;
        }
        localObject3[j] = localObject1;
        i = j + 1;
        localObject2 = localObject3;
        break;
        if (localObject1 == JsonToken.VALUE_NULL) {
          localObject1 = null;
        } else {
          localObject1 = _parseString(paramJsonParser, paramDeserializationContext);
        }
      }
    }
    paramJsonParser = (String[])localObjectBuffer.completeAndClearBuffer((Object[])localObject2, i, String.class);
    paramDeserializationContext.returnObjectBuffer(localObjectBuffer);
    return paramJsonParser;
  }
  
  public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
    throws IOException
  {
    return paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/StringArrayDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */