package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.impl.BeanAsArrayDeserializer;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;

public class BeanDeserializer
  extends BeanDeserializerBase
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  protected BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase)
  {
    super(paramBeanDeserializerBase, paramBeanDeserializerBase._ignoreAllUnknown);
  }
  
  public BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase, ObjectIdReader paramObjectIdReader)
  {
    super(paramBeanDeserializerBase, paramObjectIdReader);
  }
  
  protected BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase, NameTransformer paramNameTransformer)
  {
    super(paramBeanDeserializerBase, paramNameTransformer);
  }
  
  public BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase, HashSet<String> paramHashSet)
  {
    super(paramBeanDeserializerBase, paramHashSet);
  }
  
  protected BeanDeserializer(BeanDeserializerBase paramBeanDeserializerBase, boolean paramBoolean)
  {
    super(paramBeanDeserializerBase, paramBoolean);
  }
  
  public BeanDeserializer(BeanDeserializerBuilder paramBeanDeserializerBuilder, BeanDescription paramBeanDescription, BeanPropertyMap paramBeanPropertyMap, Map<String, SettableBeanProperty> paramMap, HashSet<String> paramHashSet, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramBeanDeserializerBuilder, paramBeanDescription, paramBeanPropertyMap, paramMap, paramHashSet, paramBoolean1, paramBoolean2);
  }
  
  private final Object vanillaDeserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonToken paramJsonToken)
    throws IOException, JsonProcessingException
  {
    Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
    if (paramJsonToken == JsonToken.FIELD_NAME)
    {
      paramJsonToken = paramJsonParser.getCurrentName();
      paramJsonParser.nextToken();
      SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(paramJsonToken);
      if (localSettableBeanProperty != null) {}
      for (;;)
      {
        try
        {
          localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject);
          paramJsonToken = paramJsonParser.nextToken();
        }
        catch (Exception localException)
        {
          wrapAndThrow(localException, localObject, paramJsonToken, paramDeserializationContext);
          continue;
        }
        handleUnknownVanilla(paramJsonParser, paramDeserializationContext, localObject, paramJsonToken);
      }
    }
    return localObject;
  }
  
  protected final Object _deserializeOther(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonToken paramJsonToken)
    throws IOException, JsonProcessingException
  {
    if (paramJsonToken == null) {
      return _missingToken(paramJsonParser, paramDeserializationContext);
    }
    switch (paramJsonToken)
    {
    default: 
      throw paramDeserializationContext.mappingException(handledType());
    case ???: 
      return deserializeFromString(paramJsonParser, paramDeserializationContext);
    case ???: 
      return deserializeFromNumber(paramJsonParser, paramDeserializationContext);
    case ???: 
      return deserializeFromDouble(paramJsonParser, paramDeserializationContext);
    case ???: 
      return paramJsonParser.getEmbeddedObject();
    case ???: 
    case ???: 
      return deserializeFromBoolean(paramJsonParser, paramDeserializationContext);
    case ???: 
      return deserializeFromArray(paramJsonParser, paramDeserializationContext);
    }
    if (this._vanillaProcessing) {
      return vanillaDeserialize(paramJsonParser, paramDeserializationContext, paramJsonToken);
    }
    if (this._objectIdReader != null) {
      return deserializeWithObjectId(paramJsonParser, paramDeserializationContext);
    }
    return deserializeFromObject(paramJsonParser, paramDeserializationContext);
  }
  
  protected Object _deserializeUsingPropertyBased(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
    PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
    Object localObject1 = null;
    Object localObject4 = paramJsonParser.getCurrentToken();
    Object localObject2;
    if (localObject4 == JsonToken.FIELD_NAME)
    {
      localObject4 = paramJsonParser.getCurrentName();
      paramJsonParser.nextToken();
      SettableBeanProperty localSettableBeanProperty = localPropertyBasedCreator.findCreatorProperty((String)localObject4);
      if (localSettableBeanProperty != null)
      {
        Object localObject5 = localSettableBeanProperty.deserialize(paramJsonParser, paramDeserializationContext);
        localObject2 = localObject1;
        if (!localPropertyValueBuffer.assignParameter(localSettableBeanProperty.getCreatorIndex(), localObject5)) {
          break label195;
        }
        paramJsonParser.nextToken();
      }
    }
    for (;;)
    {
      Object localObject3;
      try
      {
        localObject2 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
        if (localObject2.getClass() != this._beanType.getRawClass())
        {
          localObject2 = handlePolymorphic(paramJsonParser, paramDeserializationContext, localObject2, (TokenBuffer)localObject1);
          return localObject2;
        }
      }
      catch (Exception localException)
      {
        wrapAndThrow(localException, this._beanType.getRawClass(), (String)localObject4, paramDeserializationContext);
        localObject3 = null;
        continue;
        localObject4 = localObject3;
        if (localObject1 != null) {
          localObject4 = handleUnknownProperties(paramDeserializationContext, localObject3, (TokenBuffer)localObject1);
        }
        return deserialize(paramJsonParser, paramDeserializationContext, localObject4);
      }
      if (localPropertyValueBuffer.readIdProperty((String)localObject4)) {
        localObject3 = localObject1;
      }
      for (;;)
      {
        label195:
        localObject4 = paramJsonParser.nextToken();
        localObject1 = localObject3;
        break;
        localObject3 = this._beanProperties.find((String)localObject4);
        if (localObject3 != null)
        {
          localPropertyValueBuffer.bufferProperty((SettableBeanProperty)localObject3, ((SettableBeanProperty)localObject3).deserialize(paramJsonParser, paramDeserializationContext));
          localObject3 = localObject1;
        }
        else if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject4)))
        {
          handleIgnoredProperty(paramJsonParser, paramDeserializationContext, handledType(), (String)localObject4);
          localObject3 = localObject1;
        }
        else if (this._anySetter != null)
        {
          localPropertyValueBuffer.bufferAnyProperty(this._anySetter, (String)localObject4, this._anySetter.deserialize(paramJsonParser, paramDeserializationContext));
          localObject3 = localObject1;
        }
        else
        {
          localObject3 = localObject1;
          if (localObject1 == null) {
            localObject3 = new TokenBuffer(paramJsonParser);
          }
          ((TokenBuffer)localObject3).writeFieldName((String)localObject4);
          ((TokenBuffer)localObject3).copyCurrentStructure(paramJsonParser);
        }
      }
      try
      {
        paramJsonParser = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
        localObject3 = paramJsonParser;
        if (localObject1 != null) {
          if (paramJsonParser.getClass() != this._beanType.getRawClass()) {
            return handlePolymorphic(null, paramDeserializationContext, paramJsonParser, (TokenBuffer)localObject1);
          }
        }
      }
      catch (Exception paramJsonParser)
      {
        for (;;)
        {
          wrapInstantiationProblem(paramJsonParser, paramDeserializationContext);
          paramJsonParser = null;
        }
      }
    }
    return handleUnknownProperties(paramDeserializationContext, paramJsonParser, (TokenBuffer)localObject1);
  }
  
  protected Object _missingToken(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws JsonProcessingException
  {
    throw paramDeserializationContext.endOfInputException(handledType());
  }
  
  protected BeanDeserializerBase asArrayDeserializer()
  {
    return new BeanAsArrayDeserializer(this, this._beanProperties.getPropertiesInInsertionOrder());
  }
  
  public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    if (localJsonToken == JsonToken.START_OBJECT)
    {
      if (this._vanillaProcessing) {
        return vanillaDeserialize(paramJsonParser, paramDeserializationContext, paramJsonParser.nextToken());
      }
      paramJsonParser.nextToken();
      if (this._objectIdReader != null) {
        return deserializeWithObjectId(paramJsonParser, paramDeserializationContext);
      }
      return deserializeFromObject(paramJsonParser, paramDeserializationContext);
    }
    return _deserializeOther(paramJsonParser, paramDeserializationContext, localJsonToken);
  }
  
  public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    if (this._injectables != null) {
      injectValues(paramDeserializationContext, paramObject);
    }
    if (this._unwrappedPropertyHandler != null) {
      localObject1 = deserializeWithUnwrapped(paramJsonParser, paramDeserializationContext, paramObject);
    }
    do
    {
      return localObject1;
      if (this._externalTypeIdHandler != null) {
        return deserializeWithExternalTypeId(paramJsonParser, paramDeserializationContext, paramObject);
      }
      localObject2 = paramJsonParser.getCurrentToken();
      localObject1 = localObject2;
      if (localObject2 == JsonToken.START_OBJECT) {
        localObject1 = paramJsonParser.nextToken();
      }
      localObject2 = localObject1;
      if (this._needViewProcesing)
      {
        Class localClass = paramDeserializationContext.getActiveView();
        localObject2 = localObject1;
        if (localClass != null) {
          return deserializeWithView(paramJsonParser, paramDeserializationContext, paramObject, localClass);
        }
      }
      localObject1 = paramObject;
    } while (localObject2 != JsonToken.FIELD_NAME);
    Object localObject1 = paramJsonParser.getCurrentName();
    paramJsonParser.nextToken();
    Object localObject2 = this._beanProperties.find((String)localObject1);
    if (localObject2 != null) {}
    for (;;)
    {
      try
      {
        ((SettableBeanProperty)localObject2).deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
        localObject2 = paramJsonParser.nextToken();
      }
      catch (Exception localException)
      {
        wrapAndThrow(localException, paramObject, (String)localObject1, paramDeserializationContext);
        continue;
      }
      handleUnknownVanilla(paramJsonParser, paramDeserializationContext, paramObject, (String)localObject1);
    }
  }
  
  public Object deserializeFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (this._nonStandardCreation) {
      if (this._unwrappedPropertyHandler != null) {
        localObject1 = deserializeWithUnwrapped(paramJsonParser, paramDeserializationContext);
      }
    }
    Object localObject3;
    do
    {
      do
      {
        return localObject1;
        if (this._externalTypeIdHandler != null) {
          return deserializeWithExternalTypeId(paramJsonParser, paramDeserializationContext);
        }
        paramJsonParser = deserializeFromObjectUsingNonDefault(paramJsonParser, paramDeserializationContext);
        localObject1 = paramJsonParser;
      } while (this._injectables == null);
      injectValues(paramDeserializationContext, paramJsonParser);
      return paramJsonParser;
      localObject3 = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
      if (paramJsonParser.canReadObjectId())
      {
        localObject1 = paramJsonParser.getObjectId();
        if (localObject1 != null) {
          _handleTypedObjectId(paramJsonParser, paramDeserializationContext, localObject3, localObject1);
        }
      }
      if (this._injectables != null) {
        injectValues(paramDeserializationContext, localObject3);
      }
      if (this._needViewProcesing)
      {
        localObject1 = paramDeserializationContext.getActiveView();
        if (localObject1 != null) {
          return deserializeWithView(paramJsonParser, paramDeserializationContext, localObject3, (Class)localObject1);
        }
      }
      localObject2 = paramJsonParser.getCurrentToken();
      localObject1 = localObject3;
    } while (localObject2 != JsonToken.FIELD_NAME);
    Object localObject1 = paramJsonParser.getCurrentName();
    paramJsonParser.nextToken();
    Object localObject2 = this._beanProperties.find((String)localObject1);
    if (localObject2 != null) {}
    for (;;)
    {
      try
      {
        ((SettableBeanProperty)localObject2).deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject3);
        localObject2 = paramJsonParser.nextToken();
      }
      catch (Exception localException)
      {
        wrapAndThrow(localException, localObject3, (String)localObject1, paramDeserializationContext);
        continue;
      }
      handleUnknownVanilla(paramJsonParser, paramDeserializationContext, localObject3, (String)localObject1);
    }
  }
  
  protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    ExternalTypeHandler localExternalTypeHandler = this._externalTypeIdHandler.start();
    PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
    PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
    TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser);
    localTokenBuffer.writeStartObject();
    Object localObject1 = paramJsonParser.getCurrentToken();
    if (localObject1 == JsonToken.FIELD_NAME)
    {
      String str = paramJsonParser.getCurrentName();
      paramJsonParser.nextToken();
      localObject1 = localPropertyBasedCreator.findCreatorProperty(str);
      if (localObject1 != null) {
        if (!localExternalTypeHandler.handlePropertyValue(paramJsonParser, paramDeserializationContext, str, localPropertyValueBuffer)) {}
      }
      for (;;)
      {
        localObject1 = paramJsonParser.nextToken();
        break;
        Object localObject2 = ((SettableBeanProperty)localObject1).deserialize(paramJsonParser, paramDeserializationContext);
        if (localPropertyValueBuffer.assignParameter(((SettableBeanProperty)localObject1).getCreatorIndex(), localObject2))
        {
          localObject1 = paramJsonParser.nextToken();
          try
          {
            localObject2 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
            while (localObject1 == JsonToken.FIELD_NAME)
            {
              paramJsonParser.nextToken();
              localTokenBuffer.copyCurrentStructure(paramJsonParser);
              localObject1 = paramJsonParser.nextToken();
            }
          }
          catch (Exception localException)
          {
            wrapAndThrow(localException, this._beanType.getRawClass(), str, paramDeserializationContext);
          }
          if (localObject2.getClass() != this._beanType.getRawClass()) {
            throw paramDeserializationContext.mappingException("Can not create polymorphic instances with unwrapped values");
          }
          return localExternalTypeHandler.complete(paramJsonParser, paramDeserializationContext, localObject2);
          if (!localPropertyValueBuffer.readIdProperty(str))
          {
            SettableBeanProperty localSettableBeanProperty = this._beanProperties.find(str);
            if (localSettableBeanProperty != null) {
              localPropertyValueBuffer.bufferProperty(localSettableBeanProperty, localSettableBeanProperty.deserialize(paramJsonParser, paramDeserializationContext));
            } else if (!localExternalTypeHandler.handlePropertyValue(paramJsonParser, paramDeserializationContext, str, null)) {
              if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
                handleIgnoredProperty(paramJsonParser, paramDeserializationContext, handledType(), str);
              } else if (this._anySetter != null) {
                localPropertyValueBuffer.bufferAnyProperty(this._anySetter, str, this._anySetter.deserialize(paramJsonParser, paramDeserializationContext));
              }
            }
          }
        }
      }
    }
    try
    {
      paramJsonParser = localExternalTypeHandler.complete(paramJsonParser, paramDeserializationContext, localPropertyValueBuffer, localPropertyBasedCreator);
      return paramJsonParser;
    }
    catch (Exception paramJsonParser)
    {
      wrapInstantiationProblem(paramJsonParser, paramDeserializationContext);
    }
    return null;
  }
  
  protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
    PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, this._objectIdReader);
    TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser);
    localTokenBuffer.writeStartObject();
    Object localObject1 = paramJsonParser.getCurrentToken();
    Object localObject2;
    if (localObject1 == JsonToken.FIELD_NAME)
    {
      String str = paramJsonParser.getCurrentName();
      paramJsonParser.nextToken();
      localObject1 = localPropertyBasedCreator.findCreatorProperty(str);
      Object localObject3;
      if (localObject1 != null)
      {
        localObject3 = ((SettableBeanProperty)localObject1).deserialize(paramJsonParser, paramDeserializationContext);
        if (localPropertyValueBuffer.assignParameter(((SettableBeanProperty)localObject1).getCreatorIndex(), localObject3))
        {
          localObject1 = paramJsonParser.nextToken();
          try
          {
            localObject3 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
            while (localObject1 == JsonToken.FIELD_NAME)
            {
              paramJsonParser.nextToken();
              localTokenBuffer.copyCurrentStructure(paramJsonParser);
              localObject1 = paramJsonParser.nextToken();
              continue;
              localObject2 = paramJsonParser.nextToken();
            }
          }
          catch (Exception localException)
          {
            wrapAndThrow(localException, this._beanType.getRawClass(), str, paramDeserializationContext);
          }
        }
      }
      for (;;)
      {
        break;
        localTokenBuffer.writeEndObject();
        if (localObject3.getClass() != this._beanType.getRawClass())
        {
          localTokenBuffer.close();
          throw paramDeserializationContext.mappingException("Can not create polymorphic instances with unwrapped values");
        }
        return this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, localObject3, localTokenBuffer);
        if (!localPropertyValueBuffer.readIdProperty(str))
        {
          localObject2 = this._beanProperties.find(str);
          if (localObject2 != null)
          {
            localPropertyValueBuffer.bufferProperty((SettableBeanProperty)localObject2, ((SettableBeanProperty)localObject2).deserialize(paramJsonParser, paramDeserializationContext));
          }
          else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str)))
          {
            handleIgnoredProperty(paramJsonParser, paramDeserializationContext, handledType(), str);
          }
          else
          {
            localTokenBuffer.writeFieldName(str);
            localTokenBuffer.copyCurrentStructure(paramJsonParser);
            if (this._anySetter != null) {
              localPropertyValueBuffer.bufferAnyProperty(this._anySetter, str, this._anySetter.deserialize(paramJsonParser, paramDeserializationContext));
            }
          }
        }
      }
    }
    try
    {
      localObject2 = localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
      return this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, localObject2, localTokenBuffer);
    }
    catch (Exception paramJsonParser)
    {
      wrapInstantiationProblem(paramJsonParser, paramDeserializationContext);
    }
    return null;
  }
  
  protected Object deserializeWithExternalTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (this._propertyBasedCreator != null) {
      return deserializeUsingPropertyBasedWithExternalTypeId(paramJsonParser, paramDeserializationContext);
    }
    return deserializeWithExternalTypeId(paramJsonParser, paramDeserializationContext, this._valueInstantiator.createUsingDefault(paramDeserializationContext));
  }
  
  protected Object deserializeWithExternalTypeId(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    Class localClass;
    ExternalTypeHandler localExternalTypeHandler;
    Object localObject;
    label28:
    SettableBeanProperty localSettableBeanProperty;
    if (this._needViewProcesing)
    {
      localClass = paramDeserializationContext.getActiveView();
      localExternalTypeHandler = this._externalTypeIdHandler.start();
      localObject = paramJsonParser.getCurrentToken();
      if (localObject != JsonToken.FIELD_NAME) {
        break label238;
      }
      localObject = paramJsonParser.getCurrentName();
      paramJsonParser.nextToken();
      localSettableBeanProperty = this._beanProperties.find((String)localObject);
      if (localSettableBeanProperty == null) {
        break label145;
      }
      if (paramJsonParser.getCurrentToken().isScalarValue()) {
        localExternalTypeHandler.handleTypePropertyValue(paramJsonParser, paramDeserializationContext, (String)localObject, paramObject);
      }
      if ((localClass == null) || (localSettableBeanProperty.visibleInView(localClass))) {
        break label119;
      }
      paramJsonParser.skipChildren();
    }
    for (;;)
    {
      localObject = paramJsonParser.nextToken();
      break label28;
      localClass = null;
      break;
      try
      {
        label119:
        localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
      }
      catch (Exception localException1)
      {
        wrapAndThrow(localException1, paramObject, (String)localObject, paramDeserializationContext);
      }
      continue;
      label145:
      if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject))) {
        handleIgnoredProperty(paramJsonParser, paramDeserializationContext, paramObject, (String)localObject);
      } else if (!localExternalTypeHandler.handlePropertyValue(paramJsonParser, paramDeserializationContext, (String)localObject, paramObject)) {
        if (this._anySetter != null) {
          try
          {
            this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, (String)localObject);
          }
          catch (Exception localException2)
          {
            wrapAndThrow(localException2, paramObject, (String)localObject, paramDeserializationContext);
          }
        } else {
          handleUnknownProperty(paramJsonParser, paramDeserializationContext, paramObject, (String)localObject);
        }
      }
    }
    label238:
    return localExternalTypeHandler.complete(paramJsonParser, paramDeserializationContext, paramObject);
  }
  
  protected Object deserializeWithUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (this._delegateDeserializer != null) {
      return this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
    }
    if (this._propertyBasedCreator != null) {
      return deserializeUsingPropertyBasedWithUnwrapped(paramJsonParser, paramDeserializationContext);
    }
    TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser);
    localTokenBuffer.writeStartObject();
    Object localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
    if (this._injectables != null) {
      injectValues(paramDeserializationContext, localObject);
    }
    Class localClass;
    String str;
    SettableBeanProperty localSettableBeanProperty;
    if (this._needViewProcesing)
    {
      localClass = paramDeserializationContext.getActiveView();
      if (paramJsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
        break label270;
      }
      str = paramJsonParser.getCurrentName();
      paramJsonParser.nextToken();
      localSettableBeanProperty = this._beanProperties.find(str);
      if (localSettableBeanProperty == null) {
        break label186;
      }
      if ((localClass == null) || (localSettableBeanProperty.visibleInView(localClass))) {
        break label158;
      }
      paramJsonParser.skipChildren();
    }
    for (;;)
    {
      paramJsonParser.nextToken();
      break;
      localClass = null;
      break;
      try
      {
        label158:
        localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject);
      }
      catch (Exception localException1)
      {
        wrapAndThrow(localException1, localObject, str, paramDeserializationContext);
      }
      continue;
      label186:
      if ((this._ignorableProps != null) && (this._ignorableProps.contains(str)))
      {
        handleIgnoredProperty(paramJsonParser, paramDeserializationContext, localObject, str);
      }
      else
      {
        localTokenBuffer.writeFieldName(str);
        localTokenBuffer.copyCurrentStructure(paramJsonParser);
        if (this._anySetter != null) {
          try
          {
            this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject, str);
          }
          catch (Exception localException2)
          {
            wrapAndThrow(localException2, localObject, str, paramDeserializationContext);
          }
        }
      }
    }
    label270:
    localTokenBuffer.writeEndObject();
    this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, localObject, localTokenBuffer);
    return localObject;
  }
  
  protected Object deserializeWithUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    Object localObject2 = paramJsonParser.getCurrentToken();
    Object localObject1 = localObject2;
    if (localObject2 == JsonToken.START_OBJECT) {
      localObject1 = paramJsonParser.nextToken();
    }
    TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser);
    localTokenBuffer.writeStartObject();
    SettableBeanProperty localSettableBeanProperty;
    if (this._needViewProcesing)
    {
      localObject2 = paramDeserializationContext.getActiveView();
      if (localObject1 != JsonToken.FIELD_NAME) {
        break label214;
      }
      localObject1 = paramJsonParser.getCurrentName();
      localSettableBeanProperty = this._beanProperties.find((String)localObject1);
      paramJsonParser.nextToken();
      if (localSettableBeanProperty == null) {
        break label148;
      }
      if ((localObject2 == null) || (localSettableBeanProperty.visibleInView((Class)localObject2))) {
        break label122;
      }
      paramJsonParser.skipChildren();
    }
    for (;;)
    {
      localObject1 = paramJsonParser.nextToken();
      break;
      localObject2 = null;
      break;
      try
      {
        label122:
        localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
      }
      catch (Exception localException)
      {
        wrapAndThrow(localException, paramObject, (String)localObject1, paramDeserializationContext);
      }
      continue;
      label148:
      if ((this._ignorableProps != null) && (this._ignorableProps.contains(localObject1)))
      {
        handleIgnoredProperty(paramJsonParser, paramDeserializationContext, paramObject, (String)localObject1);
      }
      else
      {
        localTokenBuffer.writeFieldName((String)localObject1);
        localTokenBuffer.copyCurrentStructure(paramJsonParser);
        if (this._anySetter != null) {
          this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, (String)localObject1);
        }
      }
    }
    label214:
    localTokenBuffer.writeEndObject();
    this._unwrappedPropertyHandler.processUnwrapped(paramJsonParser, paramDeserializationContext, paramObject, localTokenBuffer);
    return paramObject;
  }
  
  protected final Object deserializeWithView(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, Class<?> paramClass)
    throws IOException, JsonProcessingException
  {
    Object localObject = paramJsonParser.getCurrentToken();
    if (localObject == JsonToken.FIELD_NAME)
    {
      localObject = paramJsonParser.getCurrentName();
      paramJsonParser.nextToken();
      SettableBeanProperty localSettableBeanProperty = this._beanProperties.find((String)localObject);
      if (localSettableBeanProperty != null) {
        if (!localSettableBeanProperty.visibleInView(paramClass)) {
          paramJsonParser.skipChildren();
        }
      }
      for (;;)
      {
        localObject = paramJsonParser.nextToken();
        break;
        try
        {
          localSettableBeanProperty.deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject);
        }
        catch (Exception localException)
        {
          wrapAndThrow(localException, paramObject, (String)localObject, paramDeserializationContext);
        }
        continue;
        handleUnknownVanilla(paramJsonParser, paramDeserializationContext, paramObject, (String)localObject);
      }
    }
    return paramObject;
  }
  
  public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer paramNameTransformer)
  {
    if (getClass() != BeanDeserializer.class) {
      return this;
    }
    return new BeanDeserializer(this, paramNameTransformer);
  }
  
  public BeanDeserializer withIgnorableProperties(HashSet<String> paramHashSet)
  {
    return new BeanDeserializer(this, paramHashSet);
  }
  
  public BeanDeserializer withObjectIdReader(ObjectIdReader paramObjectIdReader)
  {
    return new BeanDeserializer(this, paramObjectIdReader);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/BeanDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */