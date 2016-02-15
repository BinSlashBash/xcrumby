package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExternalTypeHandler
{
  private final HashMap<String, Integer> _nameToPropertyIndex;
  private final ExtTypedProperty[] _properties;
  private final TokenBuffer[] _tokens;
  private final String[] _typeIds;
  
  protected ExternalTypeHandler(ExternalTypeHandler paramExternalTypeHandler)
  {
    this._properties = paramExternalTypeHandler._properties;
    this._nameToPropertyIndex = paramExternalTypeHandler._nameToPropertyIndex;
    int i = this._properties.length;
    this._typeIds = new String[i];
    this._tokens = new TokenBuffer[i];
  }
  
  protected ExternalTypeHandler(ExtTypedProperty[] paramArrayOfExtTypedProperty, HashMap<String, Integer> paramHashMap, String[] paramArrayOfString, TokenBuffer[] paramArrayOfTokenBuffer)
  {
    this._properties = paramArrayOfExtTypedProperty;
    this._nameToPropertyIndex = paramHashMap;
    this._typeIds = paramArrayOfString;
    this._tokens = paramArrayOfTokenBuffer;
  }
  
  protected final Object _deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, int paramInt, String paramString)
    throws IOException, JsonProcessingException
  {
    TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser);
    localTokenBuffer.writeStartArray();
    localTokenBuffer.writeString(paramString);
    paramString = this._tokens[paramInt].asParser(paramJsonParser);
    paramString.nextToken();
    localTokenBuffer.copyCurrentStructure(paramString);
    localTokenBuffer.writeEndArray();
    paramJsonParser = localTokenBuffer.asParser(paramJsonParser);
    paramJsonParser.nextToken();
    return this._properties[paramInt].getProperty().deserialize(paramJsonParser, paramDeserializationContext);
  }
  
  protected final void _deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, int paramInt, String paramString)
    throws IOException, JsonProcessingException
  {
    TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser);
    localTokenBuffer.writeStartArray();
    localTokenBuffer.writeString(paramString);
    paramString = this._tokens[paramInt].asParser(paramJsonParser);
    paramString.nextToken();
    localTokenBuffer.copyCurrentStructure(paramString);
    localTokenBuffer.writeEndArray();
    paramJsonParser = localTokenBuffer.asParser(paramJsonParser);
    paramJsonParser.nextToken();
    this._properties[paramInt].getProperty().deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
  }
  
  public Object complete(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, PropertyValueBuffer paramPropertyValueBuffer, PropertyBasedCreator paramPropertyBasedCreator)
    throws IOException, JsonProcessingException
  {
    int j = this._properties.length;
    Object[] arrayOfObject = new Object[j];
    int i = 0;
    while (i < j)
    {
      String str = this._typeIds[i];
      if (str == null)
      {
        if (this._tokens[i] == null)
        {
          i += 1;
        }
        else
        {
          if (!this._properties[i].hasDefaultType()) {
            throw paramDeserializationContext.mappingException("Missing external type id property '" + this._properties[i].getTypePropertyName() + "'");
          }
          str = this._properties[i].getDefaultTypeId();
        }
      }
      else
      {
        while (this._tokens[i] != null) {
          for (;;)
          {
            arrayOfObject[i] = _deserialize(paramJsonParser, paramDeserializationContext, i, str);
          }
        }
        paramJsonParser = this._properties[i].getProperty();
        throw paramDeserializationContext.mappingException("Missing property '" + paramJsonParser.getName() + "' for external type id '" + this._properties[i].getTypePropertyName());
      }
    }
    i = 0;
    while (i < j)
    {
      paramJsonParser = this._properties[i].getProperty();
      if (paramPropertyBasedCreator.findCreatorProperty(paramJsonParser.getName()) != null) {
        paramPropertyValueBuffer.assignParameter(paramJsonParser.getCreatorIndex(), arrayOfObject[i]);
      }
      i += 1;
    }
    paramJsonParser = paramPropertyBasedCreator.build(paramDeserializationContext, paramPropertyValueBuffer);
    i = 0;
    while (i < j)
    {
      paramDeserializationContext = this._properties[i].getProperty();
      if (paramPropertyBasedCreator.findCreatorProperty(paramDeserializationContext.getName()) == null) {
        paramDeserializationContext.set(paramJsonParser, arrayOfObject[i]);
      }
      i += 1;
    }
    return paramJsonParser;
  }
  
  public Object complete(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    int i = 0;
    int j = this._properties.length;
    if (i < j)
    {
      Object localObject2 = this._typeIds[i];
      Object localObject1;
      if (localObject2 == null)
      {
        TokenBuffer localTokenBuffer = this._tokens[i];
        if (localTokenBuffer == null) {}
        for (;;)
        {
          i += 1;
          break;
          JsonToken localJsonToken = localTokenBuffer.firstToken();
          localObject1 = localObject2;
          if (localJsonToken == null) {
            break label200;
          }
          localObject1 = localObject2;
          if (!localJsonToken.isScalarValue()) {
            break label200;
          }
          localObject2 = localTokenBuffer.asParser(paramJsonParser);
          ((JsonParser)localObject2).nextToken();
          localObject1 = this._properties[i].getProperty();
          localObject2 = TypeDeserializer.deserializeIfNatural((JsonParser)localObject2, paramDeserializationContext, ((SettableBeanProperty)localObject1).getType());
          if (localObject2 == null) {
            break label137;
          }
          ((SettableBeanProperty)localObject1).set(paramObject, localObject2);
        }
        label137:
        if (!this._properties[i].hasDefaultType()) {
          throw paramDeserializationContext.mappingException("Missing external type id property '" + this._properties[i].getTypePropertyName() + "'");
        }
        localObject1 = this._properties[i].getDefaultTypeId();
      }
      label200:
      do
      {
        _deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, i, (String)localObject1);
        break;
        localObject1 = localObject2;
      } while (this._tokens[i] != null);
      paramJsonParser = this._properties[i].getProperty();
      throw paramDeserializationContext.mappingException("Missing property '" + paramJsonParser.getName() + "' for external type id '" + this._properties[i].getTypePropertyName());
    }
    return paramObject;
  }
  
  public boolean handlePropertyValue(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, String paramString, Object paramObject)
    throws IOException, JsonProcessingException
  {
    Integer localInteger = (Integer)this._nameToPropertyIndex.get(paramString);
    if (localInteger == null) {
      return false;
    }
    int j = localInteger.intValue();
    if (this._properties[j].hasTypePropertyName(paramString))
    {
      this._typeIds[j] = paramJsonParser.getText();
      paramJsonParser.skipChildren();
      if ((paramObject != null) && (this._tokens[j] != null)) {}
      for (i = 1;; i = 0)
      {
        if (i != 0)
        {
          paramString = this._typeIds[j];
          this._typeIds[j] = null;
          _deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, j, paramString);
          this._tokens[j] = null;
        }
        return true;
      }
    }
    paramString = new TokenBuffer(paramJsonParser);
    paramString.copyCurrentStructure(paramJsonParser);
    this._tokens[j] = paramString;
    if ((paramObject != null) && (this._typeIds[j] != null)) {}
    for (int i = 1;; i = 0) {
      break;
    }
  }
  
  public boolean handleTypePropertyValue(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, String paramString, Object paramObject)
    throws IOException, JsonProcessingException
  {
    Integer localInteger = (Integer)this._nameToPropertyIndex.get(paramString);
    if (localInteger == null) {}
    int j;
    do
    {
      return false;
      j = localInteger.intValue();
    } while (!this._properties[j].hasTypePropertyName(paramString));
    paramString = paramJsonParser.getText();
    int i;
    if ((paramObject != null) && (this._tokens[j] != null))
    {
      i = 1;
      if (i == 0) {
        break label96;
      }
      _deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, j, paramString);
      this._tokens[j] = null;
    }
    for (;;)
    {
      return true;
      i = 0;
      break;
      label96:
      this._typeIds[j] = paramString;
    }
  }
  
  public ExternalTypeHandler start()
  {
    return new ExternalTypeHandler(this);
  }
  
  public static class Builder
  {
    private final HashMap<String, Integer> _nameToPropertyIndex = new HashMap();
    private final ArrayList<ExternalTypeHandler.ExtTypedProperty> _properties = new ArrayList();
    
    public void addExternal(SettableBeanProperty paramSettableBeanProperty, TypeDeserializer paramTypeDeserializer)
    {
      Integer localInteger = Integer.valueOf(this._properties.size());
      this._properties.add(new ExternalTypeHandler.ExtTypedProperty(paramSettableBeanProperty, paramTypeDeserializer));
      this._nameToPropertyIndex.put(paramSettableBeanProperty.getName(), localInteger);
      this._nameToPropertyIndex.put(paramTypeDeserializer.getPropertyName(), localInteger);
    }
    
    public ExternalTypeHandler build()
    {
      return new ExternalTypeHandler((ExternalTypeHandler.ExtTypedProperty[])this._properties.toArray(new ExternalTypeHandler.ExtTypedProperty[this._properties.size()]), this._nameToPropertyIndex, null, null);
    }
  }
  
  private static final class ExtTypedProperty
  {
    private final SettableBeanProperty _property;
    private final TypeDeserializer _typeDeserializer;
    private final String _typePropertyName;
    
    public ExtTypedProperty(SettableBeanProperty paramSettableBeanProperty, TypeDeserializer paramTypeDeserializer)
    {
      this._property = paramSettableBeanProperty;
      this._typeDeserializer = paramTypeDeserializer;
      this._typePropertyName = paramTypeDeserializer.getPropertyName();
    }
    
    public String getDefaultTypeId()
    {
      Class localClass = this._typeDeserializer.getDefaultImpl();
      if (localClass == null) {
        return null;
      }
      return this._typeDeserializer.getTypeIdResolver().idFromValueAndType(null, localClass);
    }
    
    public SettableBeanProperty getProperty()
    {
      return this._property;
    }
    
    public String getTypePropertyName()
    {
      return this._typePropertyName;
    }
    
    public boolean hasDefaultType()
    {
      return this._typeDeserializer.getDefaultImpl() != null;
    }
    
    public boolean hasTypePropertyName(String paramString)
    {
      return paramString.equals(this._typePropertyName);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/impl/ExternalTypeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */