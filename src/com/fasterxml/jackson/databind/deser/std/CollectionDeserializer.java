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
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.Referring;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@JacksonStdImpl
public class CollectionDeserializer
  extends ContainerDeserializerBase<Collection<Object>>
  implements ContextualDeserializer
{
  private static final long serialVersionUID = 3917273725180652224L;
  protected final JavaType _collectionType;
  protected final JsonDeserializer<Object> _delegateDeserializer;
  protected final JsonDeserializer<Object> _valueDeserializer;
  protected final ValueInstantiator _valueInstantiator;
  protected final TypeDeserializer _valueTypeDeserializer;
  
  public CollectionDeserializer(JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer, TypeDeserializer paramTypeDeserializer, ValueInstantiator paramValueInstantiator)
  {
    this(paramJavaType, paramJsonDeserializer, paramTypeDeserializer, paramValueInstantiator, null);
  }
  
  protected CollectionDeserializer(JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer1, TypeDeserializer paramTypeDeserializer, ValueInstantiator paramValueInstantiator, JsonDeserializer<Object> paramJsonDeserializer2)
  {
    super(paramJavaType);
    this._collectionType = paramJavaType;
    this._valueDeserializer = paramJsonDeserializer1;
    this._valueTypeDeserializer = paramTypeDeserializer;
    this._valueInstantiator = paramValueInstantiator;
    this._delegateDeserializer = paramJsonDeserializer2;
  }
  
  protected CollectionDeserializer(CollectionDeserializer paramCollectionDeserializer)
  {
    super(paramCollectionDeserializer._collectionType);
    this._collectionType = paramCollectionDeserializer._collectionType;
    this._valueDeserializer = paramCollectionDeserializer._valueDeserializer;
    this._valueTypeDeserializer = paramCollectionDeserializer._valueTypeDeserializer;
    this._valueInstantiator = paramCollectionDeserializer._valueInstantiator;
    this._delegateDeserializer = paramCollectionDeserializer._delegateDeserializer;
  }
  
  public CollectionDeserializer createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (this._valueInstantiator != null)
    {
      localObject1 = localObject2;
      if (this._valueInstantiator.canCreateUsingDelegate())
      {
        localObject1 = this._valueInstantiator.getDelegateType(paramDeserializationContext.getConfig());
        if (localObject1 == null) {
          throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._collectionType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
        }
        localObject1 = findDeserializer(paramDeserializationContext, (JavaType)localObject1, paramBeanProperty);
      }
    }
    localObject2 = findConvertingContentDeserializer(paramDeserializationContext, paramBeanProperty, this._valueDeserializer);
    if (localObject2 == null) {}
    for (paramDeserializationContext = paramDeserializationContext.findContextualValueDeserializer(this._collectionType.getContentType(), paramBeanProperty);; paramDeserializationContext = paramDeserializationContext.handleSecondaryContextualization((JsonDeserializer)localObject2, paramBeanProperty))
    {
      TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
      localObject2 = localTypeDeserializer;
      if (localTypeDeserializer != null) {
        localObject2 = localTypeDeserializer.forProperty(paramBeanProperty);
      }
      return withResolved((JsonDeserializer)localObject1, paramDeserializationContext, (TypeDeserializer)localObject2);
    }
  }
  
  public Collection<Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (this._delegateDeserializer != null) {
      return (Collection)this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
    }
    if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING)
    {
      String str = paramJsonParser.getText();
      if (str.length() == 0) {
        return (Collection)this._valueInstantiator.createFromString(paramDeserializationContext, str);
      }
    }
    return deserialize(paramJsonParser, paramDeserializationContext, (Collection)this._valueInstantiator.createUsingDefault(paramDeserializationContext));
  }
  
  public Collection<Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Collection<Object> paramCollection)
    throws IOException, JsonProcessingException
  {
    Object localObject1;
    if (!paramJsonParser.isExpectedStartArrayToken())
    {
      localObject1 = handleNonArray(paramJsonParser, paramDeserializationContext, paramCollection);
      return (Collection<Object>)localObject1;
    }
    JsonDeserializer localJsonDeserializer = this._valueDeserializer;
    TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
    CollectionReferringAccumulator localCollectionReferringAccumulator;
    if (localJsonDeserializer.getObjectIdReader() == null) {
      localCollectionReferringAccumulator = null;
    }
    for (;;)
    {
      Object localObject3 = paramJsonParser.nextToken();
      localObject1 = paramCollection;
      if (localObject3 == JsonToken.END_ARRAY) {
        break;
      }
      label128:
      Object localObject2;
      for (;;)
      {
        try
        {
          if (localObject3 != JsonToken.VALUE_NULL) {
            break label128;
          }
          localObject1 = localJsonDeserializer.getNullValue();
          if (localCollectionReferringAccumulator == null) {
            break label159;
          }
          localCollectionReferringAccumulator.add(localObject1);
        }
        catch (UnresolvedForwardReference localUnresolvedForwardReference)
        {
          if (localCollectionReferringAccumulator != null) {
            break label171;
          }
        }
        throw JsonMappingException.from(paramJsonParser, "Unresolved forward reference but no identity info.", localUnresolvedForwardReference);
        localCollectionReferringAccumulator = new CollectionReferringAccumulator(this._collectionType.getContentType().getRawClass(), paramCollection);
        break;
        if (localTypeDeserializer == null) {
          localObject2 = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
        } else {
          localObject2 = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
        }
      }
      label159:
      paramCollection.add(localObject2);
      continue;
      label171:
      localObject3 = localCollectionReferringAccumulator.handleUnresolvedReference((UnresolvedForwardReference)localObject2);
      ((UnresolvedForwardReference)localObject2).getRoid().appendReferring((ReadableObjectId.Referring)localObject3);
    }
  }
  
  public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
    throws IOException, JsonProcessingException
  {
    return paramTypeDeserializer.deserializeTypedFromArray(paramJsonParser, paramDeserializationContext);
  }
  
  public JsonDeserializer<Object> getContentDeserializer()
  {
    return this._valueDeserializer;
  }
  
  public JavaType getContentType()
  {
    return this._collectionType.getContentType();
  }
  
  protected final Collection<Object> handleNonArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Collection<Object> paramCollection)
    throws IOException, JsonProcessingException
  {
    if (!paramDeserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
      throw paramDeserializationContext.mappingException(this._collectionType.getRawClass());
    }
    JsonDeserializer localJsonDeserializer = this._valueDeserializer;
    TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
    if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
      paramJsonParser = localJsonDeserializer.getNullValue();
    }
    for (;;)
    {
      paramCollection.add(paramJsonParser);
      return paramCollection;
      if (localTypeDeserializer == null) {
        paramJsonParser = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
      } else {
        paramJsonParser = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
      }
    }
  }
  
  protected CollectionDeserializer withResolved(JsonDeserializer<?> paramJsonDeserializer1, JsonDeserializer<?> paramJsonDeserializer2, TypeDeserializer paramTypeDeserializer)
  {
    if ((paramJsonDeserializer1 == this._delegateDeserializer) && (paramJsonDeserializer2 == this._valueDeserializer) && (paramTypeDeserializer == this._valueTypeDeserializer)) {
      return this;
    }
    return new CollectionDeserializer(this._collectionType, paramJsonDeserializer2, paramTypeDeserializer, this._valueInstantiator, paramJsonDeserializer1);
  }
  
  private static final class CollectionReferring
    extends ReadableObjectId.Referring
  {
    private final CollectionDeserializer.CollectionReferringAccumulator _parent;
    public final List<Object> next = new ArrayList();
    
    private CollectionReferring(CollectionDeserializer.CollectionReferringAccumulator paramCollectionReferringAccumulator, UnresolvedForwardReference paramUnresolvedForwardReference, Class<?> paramClass)
    {
      super(paramClass);
      this._parent = paramCollectionReferringAccumulator;
    }
    
    public void handleResolvedForwardReference(Object paramObject1, Object paramObject2)
      throws IOException
    {
      this._parent.resolveForwardReference(paramObject1, paramObject2);
    }
  }
  
  public static final class CollectionReferringAccumulator
  {
    private List<CollectionDeserializer.CollectionReferring> _accumulator = new ArrayList();
    private final Class<?> _elementType;
    private final Collection<Object> _result;
    
    public CollectionReferringAccumulator(Class<?> paramClass, Collection<Object> paramCollection)
    {
      this._elementType = paramClass;
      this._result = paramCollection;
    }
    
    public void add(Object paramObject)
    {
      if (this._accumulator.isEmpty())
      {
        this._result.add(paramObject);
        return;
      }
      ((CollectionDeserializer.CollectionReferring)this._accumulator.get(this._accumulator.size() - 1)).next.add(paramObject);
    }
    
    public ReadableObjectId.Referring handleUnresolvedReference(UnresolvedForwardReference paramUnresolvedForwardReference)
    {
      paramUnresolvedForwardReference = new CollectionDeserializer.CollectionReferring(this, paramUnresolvedForwardReference, this._elementType, null);
      this._accumulator.add(paramUnresolvedForwardReference);
      return paramUnresolvedForwardReference;
    }
    
    public void resolveForwardReference(Object paramObject1, Object paramObject2)
      throws IOException
    {
      Iterator localIterator = this._accumulator.iterator();
      CollectionDeserializer.CollectionReferring localCollectionReferring;
      for (Object localObject = this._result; localIterator.hasNext(); localObject = localCollectionReferring.next)
      {
        localCollectionReferring = (CollectionDeserializer.CollectionReferring)localIterator.next();
        if (localCollectionReferring.hasId(paramObject1))
        {
          localIterator.remove();
          ((Collection)localObject).add(paramObject2);
          ((Collection)localObject).addAll(localCollectionReferring.next);
          return;
        }
      }
      throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + paramObject1 + "] that wasn't previously seen as unresolved.");
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/CollectionDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */