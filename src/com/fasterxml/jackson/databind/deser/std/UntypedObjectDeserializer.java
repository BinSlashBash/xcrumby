package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JacksonStdImpl
public class UntypedObjectDeserializer
  extends StdDeserializer<Object>
  implements ResolvableDeserializer, ContextualDeserializer
{
  protected static final Object[] NO_OBJECTS = new Object[0];
  @Deprecated
  public static final UntypedObjectDeserializer instance = new UntypedObjectDeserializer();
  private static final long serialVersionUID = 1L;
  protected JsonDeserializer<Object> _listDeserializer;
  protected JsonDeserializer<Object> _mapDeserializer;
  protected JsonDeserializer<Object> _numberDeserializer;
  protected JsonDeserializer<Object> _stringDeserializer;
  
  public UntypedObjectDeserializer()
  {
    super(Object.class);
  }
  
  public UntypedObjectDeserializer(UntypedObjectDeserializer paramUntypedObjectDeserializer, JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2, JsonDeserializer<?> paramJsonDeserializer3, JsonDeserializer<?> paramJsonDeserializer4)
  {
    super(Object.class);
    this._mapDeserializer = paramJsonDeserializer1;
    this._listDeserializer = paramJsonDeserializer2;
    this._stringDeserializer = paramJsonDeserializer3;
    this._numberDeserializer = paramJsonDeserializer4;
  }
  
  protected JsonDeserializer<Object> _findCustomDeser(DeserializationContext paramDeserializationContext, JavaType paramJavaType)
    throws JsonMappingException
  {
    paramJavaType = paramDeserializationContext.findRootValueDeserializer(paramJavaType);
    paramDeserializationContext = paramJavaType;
    if (ClassUtil.isJacksonStdImpl(paramJavaType)) {
      paramDeserializationContext = null;
    }
    return paramDeserializationContext;
  }
  
  protected JsonDeserializer<?> _withResolved(JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2, JsonDeserializer<?> paramJsonDeserializer3, JsonDeserializer<?> paramJsonDeserializer4)
  {
    return new UntypedObjectDeserializer(this, paramJsonDeserializer1, paramJsonDeserializer2, paramJsonDeserializer3, paramJsonDeserializer4);
  }
  
  public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    if ((this._stringDeserializer == null) && (this._numberDeserializer == null) && (this._mapDeserializer == null) && (this._listDeserializer == null) && (getClass() == UntypedObjectDeserializer.class)) {
      paramDeserializationContext = Vanilla.std;
    }
    Object localObject2;
    Object localObject1;
    Object localObject3;
    Object localObject4;
    do
    {
      return paramDeserializationContext;
      localObject2 = this._mapDeserializer;
      localObject1 = localObject2;
      if ((localObject2 instanceof ContextualDeserializer)) {
        localObject1 = ((ContextualDeserializer)localObject2).createContextual(paramDeserializationContext, paramBeanProperty);
      }
      localObject3 = this._listDeserializer;
      localObject2 = localObject3;
      if ((localObject3 instanceof ContextualDeserializer)) {
        localObject2 = ((ContextualDeserializer)localObject3).createContextual(paramDeserializationContext, paramBeanProperty);
      }
      localObject4 = this._stringDeserializer;
      localObject3 = localObject4;
      if ((localObject4 instanceof ContextualDeserializer)) {
        localObject3 = ((ContextualDeserializer)localObject4).createContextual(paramDeserializationContext, paramBeanProperty);
      }
      JsonDeserializer localJsonDeserializer = this._numberDeserializer;
      localObject4 = localJsonDeserializer;
      if ((localJsonDeserializer instanceof ContextualDeserializer)) {
        localObject4 = ((ContextualDeserializer)localJsonDeserializer).createContextual(paramDeserializationContext, paramBeanProperty);
      }
      if ((localObject1 != this._mapDeserializer) || (localObject2 != this._listDeserializer) || (localObject3 != this._stringDeserializer)) {
        break;
      }
      paramDeserializationContext = this;
    } while (localObject4 == this._numberDeserializer);
    return _withResolved((JsonDeserializer)localObject1, (JsonDeserializer)localObject2, (JsonDeserializer)localObject3, (JsonDeserializer)localObject4);
  }
  
  public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    switch (paramJsonParser.getCurrentToken())
    {
    default: 
      throw paramDeserializationContext.mappingException(Object.class);
    case ???: 
    case ???: 
      if (this._mapDeserializer != null) {
        return this._mapDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      }
      return mapObject(paramJsonParser, paramDeserializationContext);
    case ???: 
      if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
        return mapArrayToArray(paramJsonParser, paramDeserializationContext);
      }
      if (this._listDeserializer != null) {
        return this._listDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      }
      return mapArray(paramJsonParser, paramDeserializationContext);
    case ???: 
      return paramJsonParser.getEmbeddedObject();
    case ???: 
      if (this._stringDeserializer != null) {
        return this._stringDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      }
      return paramJsonParser.getText();
    case ???: 
      if (this._numberDeserializer != null) {
        return this._numberDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      }
      if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
        return paramJsonParser.getBigIntegerValue();
      }
      return paramJsonParser.getNumberValue();
    case ???: 
      if (this._numberDeserializer != null) {
        return this._numberDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      }
      if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
        return paramJsonParser.getDecimalValue();
      }
      return Double.valueOf(paramJsonParser.getDoubleValue());
    case ???: 
      return Boolean.TRUE;
    case ???: 
      return Boolean.FALSE;
    }
    return null;
  }
  
  public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
    throws IOException
  {
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    switch (localJsonToken)
    {
    default: 
      throw paramDeserializationContext.mappingException(Object.class);
    case ???: 
    case ???: 
    case ???: 
      return paramTypeDeserializer.deserializeTypedFromAny(paramJsonParser, paramDeserializationContext);
    case ???: 
      if (this._stringDeserializer != null) {
        return this._stringDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      }
      return paramJsonParser.getText();
    case ???: 
      if (this._numberDeserializer != null) {
        return this._numberDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      }
      if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
        return paramJsonParser.getBigIntegerValue();
      }
      return paramJsonParser.getNumberValue();
    case ???: 
      if (this._numberDeserializer != null) {
        return this._numberDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      }
      if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
        return paramJsonParser.getDecimalValue();
      }
      return Double.valueOf(paramJsonParser.getDoubleValue());
    case ???: 
      return Boolean.TRUE;
    case ???: 
      return Boolean.FALSE;
    case ???: 
      return paramJsonParser.getEmbeddedObject();
    }
    return null;
  }
  
  protected Object mapArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
      return new ArrayList(2);
    }
    Object localObject1 = deserialize(paramJsonParser, paramDeserializationContext);
    if (paramJsonParser.nextToken() == JsonToken.END_ARRAY)
    {
      paramJsonParser = new ArrayList(2);
      paramJsonParser.add(localObject1);
      return paramJsonParser;
    }
    Object localObject3 = deserialize(paramJsonParser, paramDeserializationContext);
    if (paramJsonParser.nextToken() == JsonToken.END_ARRAY)
    {
      paramJsonParser = new ArrayList(2);
      paramJsonParser.add(localObject1);
      paramJsonParser.add(localObject3);
      return paramJsonParser;
    }
    ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
    Object localObject2 = localObjectBuffer.resetAndStart();
    int j = 0 + 1;
    localObject2[0] = localObject1;
    int i = j + 1;
    localObject2[j] = localObject3;
    j = i;
    for (;;)
    {
      localObject3 = deserialize(paramJsonParser, paramDeserializationContext);
      int k = j + 1;
      j = i;
      localObject1 = localObject2;
      if (i >= localObject2.length)
      {
        localObject1 = localObjectBuffer.appendCompletedChunk((Object[])localObject2);
        j = 0;
      }
      i = j + 1;
      localObject1[j] = localObject3;
      if (paramJsonParser.nextToken() == JsonToken.END_ARRAY)
      {
        paramJsonParser = new ArrayList(k);
        localObjectBuffer.completeAndClearBuffer((Object[])localObject1, i, paramJsonParser);
        return paramJsonParser;
      }
      j = k;
      localObject2 = localObject1;
    }
  }
  
  protected Object[] mapArrayToArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
      return NO_OBJECTS;
    }
    ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
    Object localObject1 = localObjectBuffer.resetAndStart();
    int i = 0;
    for (;;)
    {
      Object localObject3 = deserialize(paramJsonParser, paramDeserializationContext);
      int j = i;
      Object localObject2 = localObject1;
      if (i >= localObject1.length)
      {
        localObject2 = localObjectBuffer.appendCompletedChunk((Object[])localObject1);
        j = 0;
      }
      i = j + 1;
      localObject2[j] = localObject3;
      if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
        return localObjectBuffer.completeAndClearBuffer((Object[])localObject2, i);
      }
      localObject1 = localObject2;
    }
  }
  
  protected Object mapObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    Object localObject2 = paramJsonParser.getCurrentToken();
    Object localObject1 = localObject2;
    if (localObject2 == JsonToken.START_OBJECT) {
      localObject1 = paramJsonParser.nextToken();
    }
    if (localObject1 == JsonToken.END_OBJECT) {
      return new LinkedHashMap(2);
    }
    localObject1 = paramJsonParser.getCurrentName();
    paramJsonParser.nextToken();
    localObject2 = deserialize(paramJsonParser, paramDeserializationContext);
    if (paramJsonParser.nextToken() == JsonToken.END_OBJECT)
    {
      paramJsonParser = new LinkedHashMap(2);
      paramJsonParser.put(localObject1, localObject2);
      return paramJsonParser;
    }
    String str = paramJsonParser.getCurrentName();
    paramJsonParser.nextToken();
    Object localObject3 = deserialize(paramJsonParser, paramDeserializationContext);
    if (paramJsonParser.nextToken() == JsonToken.END_OBJECT)
    {
      paramJsonParser = new LinkedHashMap(4);
      paramJsonParser.put(localObject1, localObject2);
      paramJsonParser.put(str, localObject3);
      return paramJsonParser;
    }
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    localLinkedHashMap.put(localObject1, localObject2);
    localLinkedHashMap.put(str, localObject3);
    do
    {
      localObject1 = paramJsonParser.getCurrentName();
      paramJsonParser.nextToken();
      localLinkedHashMap.put(localObject1, deserialize(paramJsonParser, paramDeserializationContext));
    } while (paramJsonParser.nextToken() != JsonToken.END_OBJECT);
    return localLinkedHashMap;
  }
  
  public void resolve(DeserializationContext paramDeserializationContext)
    throws JsonMappingException
  {
    JavaType localJavaType1 = paramDeserializationContext.constructType(Object.class);
    JavaType localJavaType2 = paramDeserializationContext.constructType(String.class);
    TypeFactory localTypeFactory = paramDeserializationContext.getTypeFactory();
    this._mapDeserializer = _findCustomDeser(paramDeserializationContext, localTypeFactory.constructMapType(Map.class, localJavaType2, localJavaType1));
    this._listDeserializer = _findCustomDeser(paramDeserializationContext, localTypeFactory.constructCollectionType(List.class, localJavaType1));
    this._stringDeserializer = _findCustomDeser(paramDeserializationContext, localJavaType2);
    this._numberDeserializer = _findCustomDeser(paramDeserializationContext, localTypeFactory.constructType(Number.class));
  }
  
  @JacksonStdImpl
  public static class Vanilla
    extends StdDeserializer<Object>
  {
    private static final long serialVersionUID = 1L;
    public static final Vanilla std = new Vanilla();
    
    public Vanilla()
    {
      super();
    }
    
    public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      switch (paramJsonParser.getCurrentTokenId())
      {
      case 2: 
      case 4: 
      default: 
        throw paramDeserializationContext.mappingException(Object.class);
      case 1: 
        if (paramJsonParser.nextToken() == JsonToken.END_OBJECT) {
          return new LinkedHashMap(2);
        }
      case 5: 
        return mapObject(paramJsonParser, paramDeserializationContext);
      case 3: 
        if (paramJsonParser.nextToken() == JsonToken.END_ARRAY)
        {
          if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
            return UntypedObjectDeserializer.NO_OBJECTS;
          }
          return new ArrayList(2);
        }
        if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
          return mapArrayToArray(paramJsonParser, paramDeserializationContext);
        }
        return mapArray(paramJsonParser, paramDeserializationContext);
      case 12: 
        return paramJsonParser.getEmbeddedObject();
      case 6: 
        return paramJsonParser.getText();
      case 7: 
        if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
          return paramJsonParser.getBigIntegerValue();
        }
        return paramJsonParser.getNumberValue();
      case 8: 
        if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
          return paramJsonParser.getDecimalValue();
        }
        return Double.valueOf(paramJsonParser.getDoubleValue());
      case 9: 
        return Boolean.TRUE;
      case 10: 
        return Boolean.FALSE;
      }
      return null;
    }
    
    public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
      throws IOException
    {
      switch (paramJsonParser.getCurrentTokenId())
      {
      case 2: 
      case 4: 
      default: 
        throw paramDeserializationContext.mappingException(Object.class);
      case 1: 
      case 3: 
      case 5: 
        return paramTypeDeserializer.deserializeTypedFromAny(paramJsonParser, paramDeserializationContext);
      case 6: 
        return paramJsonParser.getText();
      case 7: 
        if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
          return paramJsonParser.getBigIntegerValue();
        }
        return paramJsonParser.getNumberValue();
      case 8: 
        if (paramDeserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
          return paramJsonParser.getDecimalValue();
        }
        return Double.valueOf(paramJsonParser.getDoubleValue());
      case 9: 
        return Boolean.TRUE;
      case 10: 
        return Boolean.FALSE;
      case 12: 
        return paramJsonParser.getEmbeddedObject();
      }
      return null;
    }
    
    protected Object mapArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      Object localObject1 = deserialize(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() == JsonToken.END_ARRAY)
      {
        paramJsonParser = new ArrayList(2);
        paramJsonParser.add(localObject1);
        return paramJsonParser;
      }
      Object localObject3 = deserialize(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() == JsonToken.END_ARRAY)
      {
        paramJsonParser = new ArrayList(2);
        paramJsonParser.add(localObject1);
        paramJsonParser.add(localObject3);
        return paramJsonParser;
      }
      ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
      Object localObject2 = localObjectBuffer.resetAndStart();
      int j = 0 + 1;
      localObject2[0] = localObject1;
      int i = j + 1;
      localObject2[j] = localObject3;
      j = i;
      for (;;)
      {
        localObject3 = deserialize(paramJsonParser, paramDeserializationContext);
        int k = j + 1;
        j = i;
        localObject1 = localObject2;
        if (i >= localObject2.length)
        {
          localObject1 = localObjectBuffer.appendCompletedChunk((Object[])localObject2);
          j = 0;
        }
        i = j + 1;
        localObject1[j] = localObject3;
        if (paramJsonParser.nextToken() == JsonToken.END_ARRAY)
        {
          paramJsonParser = new ArrayList(k);
          localObjectBuffer.completeAndClearBuffer((Object[])localObject1, i, paramJsonParser);
          return paramJsonParser;
        }
        j = k;
        localObject2 = localObject1;
      }
    }
    
    protected Object[] mapArrayToArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      ObjectBuffer localObjectBuffer = paramDeserializationContext.leaseObjectBuffer();
      Object localObject1 = localObjectBuffer.resetAndStart();
      int i = 0;
      for (;;)
      {
        Object localObject3 = deserialize(paramJsonParser, paramDeserializationContext);
        int j = i;
        Object localObject2 = localObject1;
        if (i >= localObject1.length)
        {
          localObject2 = localObjectBuffer.appendCompletedChunk((Object[])localObject1);
          j = 0;
        }
        i = j + 1;
        localObject2[j] = localObject3;
        if (paramJsonParser.nextToken() == JsonToken.END_ARRAY) {
          return localObjectBuffer.completeAndClearBuffer((Object[])localObject2, i);
        }
        localObject1 = localObject2;
      }
    }
    
    protected Object mapObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
      throws IOException
    {
      String str1 = paramJsonParser.getText();
      paramJsonParser.nextToken();
      Object localObject1 = deserialize(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() == JsonToken.END_OBJECT)
      {
        paramJsonParser = new LinkedHashMap(2);
        paramJsonParser.put(str1, localObject1);
        return paramJsonParser;
      }
      String str2 = paramJsonParser.getText();
      paramJsonParser.nextToken();
      Object localObject2 = deserialize(paramJsonParser, paramDeserializationContext);
      if (paramJsonParser.nextToken() == JsonToken.END_OBJECT)
      {
        paramJsonParser = new LinkedHashMap(4);
        paramJsonParser.put(str1, localObject1);
        paramJsonParser.put(str2, localObject2);
        return paramJsonParser;
      }
      LinkedHashMap localLinkedHashMap = new LinkedHashMap();
      localLinkedHashMap.put(str1, localObject1);
      localLinkedHashMap.put(str2, localObject2);
      do
      {
        str1 = paramJsonParser.getText();
        paramJsonParser.nextToken();
        localLinkedHashMap.put(str1, deserialize(paramJsonParser, paramDeserializationContext));
      } while (paramJsonParser.nextToken() != JsonToken.END_OBJECT);
      return localLinkedHashMap;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/UntypedObjectDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */