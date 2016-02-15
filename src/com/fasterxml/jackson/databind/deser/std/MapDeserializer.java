package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.Referring;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JacksonStdImpl
public class MapDeserializer
  extends ContainerDeserializerBase<Map<Object, Object>>
  implements ContextualDeserializer, ResolvableDeserializer
{
  private static final long serialVersionUID = -3378654289961736240L;
  protected JsonDeserializer<Object> _delegateDeserializer;
  protected final boolean _hasDefaultCreator;
  protected HashSet<String> _ignorableProperties;
  protected final KeyDeserializer _keyDeserializer;
  protected final JavaType _mapType;
  protected PropertyBasedCreator _propertyBasedCreator;
  protected boolean _standardStringKey;
  protected final JsonDeserializer<Object> _valueDeserializer;
  protected final ValueInstantiator _valueInstantiator;
  protected final TypeDeserializer _valueTypeDeserializer;
  
  public MapDeserializer(JavaType paramJavaType, ValueInstantiator paramValueInstantiator, KeyDeserializer paramKeyDeserializer, JsonDeserializer<Object> paramJsonDeserializer, TypeDeserializer paramTypeDeserializer)
  {
    super(paramJavaType);
    this._mapType = paramJavaType;
    this._keyDeserializer = paramKeyDeserializer;
    this._valueDeserializer = paramJsonDeserializer;
    this._valueTypeDeserializer = paramTypeDeserializer;
    this._valueInstantiator = paramValueInstantiator;
    this._hasDefaultCreator = paramValueInstantiator.canCreateUsingDefault();
    this._delegateDeserializer = null;
    this._propertyBasedCreator = null;
    this._standardStringKey = _isStdKeyDeser(paramJavaType, paramKeyDeserializer);
  }
  
  protected MapDeserializer(MapDeserializer paramMapDeserializer)
  {
    super(paramMapDeserializer._mapType);
    this._mapType = paramMapDeserializer._mapType;
    this._keyDeserializer = paramMapDeserializer._keyDeserializer;
    this._valueDeserializer = paramMapDeserializer._valueDeserializer;
    this._valueTypeDeserializer = paramMapDeserializer._valueTypeDeserializer;
    this._valueInstantiator = paramMapDeserializer._valueInstantiator;
    this._propertyBasedCreator = paramMapDeserializer._propertyBasedCreator;
    this._delegateDeserializer = paramMapDeserializer._delegateDeserializer;
    this._hasDefaultCreator = paramMapDeserializer._hasDefaultCreator;
    this._ignorableProperties = paramMapDeserializer._ignorableProperties;
    this._standardStringKey = paramMapDeserializer._standardStringKey;
  }
  
  protected MapDeserializer(MapDeserializer paramMapDeserializer, KeyDeserializer paramKeyDeserializer, JsonDeserializer<Object> paramJsonDeserializer, TypeDeserializer paramTypeDeserializer, HashSet<String> paramHashSet)
  {
    super(paramMapDeserializer._mapType);
    this._mapType = paramMapDeserializer._mapType;
    this._keyDeserializer = paramKeyDeserializer;
    this._valueDeserializer = paramJsonDeserializer;
    this._valueTypeDeserializer = paramTypeDeserializer;
    this._valueInstantiator = paramMapDeserializer._valueInstantiator;
    this._propertyBasedCreator = paramMapDeserializer._propertyBasedCreator;
    this._delegateDeserializer = paramMapDeserializer._delegateDeserializer;
    this._hasDefaultCreator = paramMapDeserializer._hasDefaultCreator;
    this._ignorableProperties = paramHashSet;
    this._standardStringKey = _isStdKeyDeser(this._mapType, paramKeyDeserializer);
  }
  
  private void handleUnresolvedReference(JsonParser paramJsonParser, MapReferringAccumulator paramMapReferringAccumulator, Object paramObject, UnresolvedForwardReference paramUnresolvedForwardReference)
    throws JsonMappingException
  {
    if (paramMapReferringAccumulator == null) {
      throw JsonMappingException.from(paramJsonParser, "Unresolved forward reference but no identity info.", paramUnresolvedForwardReference);
    }
    paramJsonParser = paramMapReferringAccumulator.handleUnresolvedReference(paramUnresolvedForwardReference, paramObject);
    paramUnresolvedForwardReference.getRoid().appendReferring(paramJsonParser);
  }
  
  public Map<Object, Object> _deserializeUsingCreator(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    PropertyBasedCreator localPropertyBasedCreator = this._propertyBasedCreator;
    PropertyValueBuffer localPropertyValueBuffer = localPropertyBasedCreator.startBuilding(paramJsonParser, paramDeserializationContext, null);
    Object localObject2 = paramJsonParser.getCurrentToken();
    Object localObject1 = localObject2;
    if (localObject2 == JsonToken.START_OBJECT) {
      localObject1 = paramJsonParser.nextToken();
    }
    localObject2 = this._valueDeserializer;
    TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
    if (localObject1 == JsonToken.FIELD_NAME)
    {
      Object localObject3 = paramJsonParser.getCurrentName();
      localObject1 = paramJsonParser.nextToken();
      if ((this._ignorableProperties != null) && (this._ignorableProperties.contains(localObject3))) {
        paramJsonParser.skipChildren();
      }
      do
      {
        localObject1 = paramJsonParser.nextToken();
        break;
        localObject3 = localPropertyBasedCreator.findCreatorProperty((String)localObject3);
        if (localObject3 == null) {
          break label177;
        }
        localObject1 = ((SettableBeanProperty)localObject3).deserialize(paramJsonParser, paramDeserializationContext);
      } while (!localPropertyValueBuffer.assignParameter(((SettableBeanProperty)localObject3).getCreatorIndex(), localObject1));
      paramJsonParser.nextToken();
      try
      {
        localObject1 = (Map)localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
        _readAndBind(paramJsonParser, paramDeserializationContext, (Map)localObject1);
        return (Map<Object, Object>)localObject1;
      }
      catch (Exception paramJsonParser)
      {
        wrapAndThrow(paramJsonParser, this._mapType.getRawClass());
        return null;
      }
      label177:
      localObject3 = paramJsonParser.getCurrentName();
      localObject3 = this._keyDeserializer.deserializeKey((String)localObject3, paramDeserializationContext);
      if (localObject1 == JsonToken.VALUE_NULL) {
        localObject1 = ((JsonDeserializer)localObject2).getNullValue();
      }
      for (;;)
      {
        localPropertyValueBuffer.bufferMapProperty(localObject3, localObject1);
        break;
        if (localTypeDeserializer == null) {
          localObject1 = ((JsonDeserializer)localObject2).deserialize(paramJsonParser, paramDeserializationContext);
        } else {
          localObject1 = ((JsonDeserializer)localObject2).deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
        }
      }
    }
    try
    {
      paramJsonParser = (Map)localPropertyBasedCreator.build(paramDeserializationContext, localPropertyValueBuffer);
      return paramJsonParser;
    }
    catch (Exception paramJsonParser)
    {
      wrapAndThrow(paramJsonParser, this._mapType.getRawClass());
    }
    return null;
  }
  
  protected final boolean _isStdKeyDeser(JavaType paramJavaType, KeyDeserializer paramKeyDeserializer)
  {
    if (paramKeyDeserializer == null) {}
    do
    {
      do
      {
        return true;
        paramJavaType = paramJavaType.getKeyType();
      } while (paramJavaType == null);
      paramJavaType = paramJavaType.getRawClass();
    } while (((paramJavaType == String.class) || (paramJavaType == Object.class)) && (isDefaultKeyDeserializer(paramKeyDeserializer)));
    return false;
  }
  
  protected final void _readAndBind(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Map<Object, Object> paramMap)
    throws IOException, JsonProcessingException
  {
    Object localObject3 = paramJsonParser.getCurrentToken();
    Object localObject1 = localObject3;
    if (localObject3 == JsonToken.START_OBJECT) {
      localObject1 = paramJsonParser.nextToken();
    }
    KeyDeserializer localKeyDeserializer = this._keyDeserializer;
    JsonDeserializer localJsonDeserializer = this._valueDeserializer;
    TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
    localObject3 = null;
    int i;
    Object localObject4;
    label89:
    JsonToken localJsonToken;
    if (localJsonDeserializer.getObjectIdReader() != null)
    {
      i = 1;
      localObject4 = localObject1;
      if (i != 0)
      {
        localObject3 = new MapReferringAccumulator(this._mapType.getContentType().getRawClass(), paramMap);
        localObject4 = localObject1;
      }
      if (localObject4 != JsonToken.FIELD_NAME) {
        return;
      }
      localObject1 = paramJsonParser.getCurrentName();
      localObject4 = localKeyDeserializer.deserializeKey((String)localObject1, paramDeserializationContext);
      localJsonToken = paramJsonParser.nextToken();
      if ((this._ignorableProperties == null) || (!this._ignorableProperties.contains(localObject1))) {
        break label158;
      }
      paramJsonParser.skipChildren();
    }
    for (;;)
    {
      localObject4 = paramJsonParser.nextToken();
      break label89;
      i = 0;
      break;
      label158:
      label206:
      Object localObject2;
      for (;;)
      {
        try
        {
          if (localJsonToken != JsonToken.VALUE_NULL) {
            break label206;
          }
          localObject1 = localJsonDeserializer.getNullValue();
          if (i == 0) {
            break label237;
          }
          ((MapReferringAccumulator)localObject3).put(localObject4, localObject1);
        }
        catch (UnresolvedForwardReference localUnresolvedForwardReference)
        {
          handleUnresolvedReference(paramJsonParser, (MapReferringAccumulator)localObject3, localObject4, localUnresolvedForwardReference);
        }
        break;
        if (localTypeDeserializer == null) {
          localObject2 = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
        } else {
          localObject2 = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
        }
      }
      label237:
      paramMap.put(localObject4, localObject2);
    }
  }
  
  protected final void _readAndBindStringMap(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Map<Object, Object> paramMap)
    throws IOException, JsonProcessingException
  {
    Object localObject3 = paramJsonParser.getCurrentToken();
    Object localObject1 = localObject3;
    if (localObject3 == JsonToken.START_OBJECT) {
      localObject1 = paramJsonParser.nextToken();
    }
    JsonDeserializer localJsonDeserializer = this._valueDeserializer;
    TypeDeserializer localTypeDeserializer = this._valueTypeDeserializer;
    localObject3 = null;
    int i;
    Object localObject4;
    if (localJsonDeserializer.getObjectIdReader() != null)
    {
      i = 1;
      localObject4 = localObject1;
      if (i != 0)
      {
        localObject3 = new MapReferringAccumulator(this._mapType.getContentType().getRawClass(), paramMap);
        localObject4 = localObject1;
      }
      label83:
      if (localObject4 != JsonToken.FIELD_NAME) {
        return;
      }
      localObject4 = paramJsonParser.getCurrentName();
      localObject1 = paramJsonParser.nextToken();
      if ((this._ignorableProperties == null) || (!this._ignorableProperties.contains(localObject4))) {
        break label142;
      }
      paramJsonParser.skipChildren();
    }
    for (;;)
    {
      localObject4 = paramJsonParser.nextToken();
      break label83;
      i = 0;
      break;
      label142:
      label190:
      Object localObject2;
      for (;;)
      {
        try
        {
          if (localObject1 != JsonToken.VALUE_NULL) {
            break label190;
          }
          localObject1 = localJsonDeserializer.getNullValue();
          if (i == 0) {
            break label221;
          }
          ((MapReferringAccumulator)localObject3).put(localObject4, localObject1);
        }
        catch (UnresolvedForwardReference localUnresolvedForwardReference)
        {
          handleUnresolvedReference(paramJsonParser, (MapReferringAccumulator)localObject3, localObject4, localUnresolvedForwardReference);
        }
        break;
        if (localTypeDeserializer == null) {
          localObject2 = localJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
        } else {
          localObject2 = localJsonDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, localTypeDeserializer);
        }
      }
      label221:
      paramMap.put(localObject4, localObject2);
    }
  }
  
  public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    Object localObject2 = this._keyDeserializer;
    Object localObject1;
    label56:
    Object localObject4;
    Object localObject3;
    HashSet localHashSet;
    if (localObject2 == null)
    {
      localObject1 = paramDeserializationContext.findKeyDeserializer(this._mapType.getKeyType(), paramBeanProperty);
      localObject2 = findConvertingContentDeserializer(paramDeserializationContext, paramBeanProperty, this._valueDeserializer);
      if (localObject2 != null) {
        break label197;
      }
      localObject2 = paramDeserializationContext.findContextualValueDeserializer(this._mapType.getContentType(), paramBeanProperty);
      localObject4 = this._valueTypeDeserializer;
      localObject3 = localObject4;
      if (localObject4 != null) {
        localObject3 = ((TypeDeserializer)localObject4).forProperty(paramBeanProperty);
      }
      localHashSet = this._ignorableProperties;
      paramDeserializationContext = paramDeserializationContext.getAnnotationIntrospector();
      localObject4 = localHashSet;
      if (paramDeserializationContext == null) {
        break label222;
      }
      localObject4 = localHashSet;
      if (paramBeanProperty == null) {
        break label222;
      }
      paramBeanProperty = paramDeserializationContext.findPropertiesToIgnore(paramBeanProperty.getMember());
      localObject4 = localHashSet;
      if (paramBeanProperty == null) {
        break label222;
      }
      if (localHashSet != null) {
        break label209;
      }
    }
    label197:
    label209:
    for (paramDeserializationContext = new HashSet();; paramDeserializationContext = new HashSet(localHashSet))
    {
      int j = paramBeanProperty.length;
      int i = 0;
      for (;;)
      {
        localObject4 = paramDeserializationContext;
        if (i >= j) {
          break;
        }
        paramDeserializationContext.add(paramBeanProperty[i]);
        i += 1;
      }
      localObject1 = localObject2;
      if (!(localObject2 instanceof ContextualKeyDeserializer)) {
        break;
      }
      localObject1 = ((ContextualKeyDeserializer)localObject2).createContextual(paramDeserializationContext, paramBeanProperty);
      break;
      localObject2 = paramDeserializationContext.handleSecondaryContextualization((JsonDeserializer)localObject2, paramBeanProperty);
      break label56;
    }
    label222:
    return withResolved((KeyDeserializer)localObject1, (TypeDeserializer)localObject3, (JsonDeserializer)localObject2, (HashSet)localObject4);
  }
  
  public Map<Object, Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    if (this._propertyBasedCreator != null) {
      return _deserializeUsingCreator(paramJsonParser, paramDeserializationContext);
    }
    if (this._delegateDeserializer != null) {
      return (Map)this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
    }
    if (!this._hasDefaultCreator) {
      throw paramDeserializationContext.instantiationException(getMapClass(), "No default constructor found");
    }
    Object localObject = paramJsonParser.getCurrentToken();
    if ((localObject != JsonToken.START_OBJECT) && (localObject != JsonToken.FIELD_NAME) && (localObject != JsonToken.END_OBJECT))
    {
      if (localObject == JsonToken.VALUE_STRING) {
        return (Map)this._valueInstantiator.createFromString(paramDeserializationContext, paramJsonParser.getText());
      }
      throw paramDeserializationContext.mappingException(getMapClass());
    }
    localObject = (Map)this._valueInstantiator.createUsingDefault(paramDeserializationContext);
    if (this._standardStringKey)
    {
      _readAndBindStringMap(paramJsonParser, paramDeserializationContext, (Map)localObject);
      return (Map<Object, Object>)localObject;
    }
    _readAndBind(paramJsonParser, paramDeserializationContext, (Map)localObject);
    return (Map<Object, Object>)localObject;
  }
  
  public Map<Object, Object> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Map<Object, Object> paramMap)
    throws IOException, JsonProcessingException
  {
    JsonToken localJsonToken = paramJsonParser.getCurrentToken();
    if ((localJsonToken != JsonToken.START_OBJECT) && (localJsonToken != JsonToken.FIELD_NAME)) {
      throw paramDeserializationContext.mappingException(getMapClass());
    }
    if (this._standardStringKey)
    {
      _readAndBindStringMap(paramJsonParser, paramDeserializationContext, paramMap);
      return paramMap;
    }
    _readAndBind(paramJsonParser, paramDeserializationContext, paramMap);
    return paramMap;
  }
  
  public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
    throws IOException, JsonProcessingException
  {
    return paramTypeDeserializer.deserializeTypedFromObject(paramJsonParser, paramDeserializationContext);
  }
  
  public JsonDeserializer<Object> getContentDeserializer()
  {
    return this._valueDeserializer;
  }
  
  public JavaType getContentType()
  {
    return this._mapType.getContentType();
  }
  
  public final Class<?> getMapClass()
  {
    return this._mapType.getRawClass();
  }
  
  public JavaType getValueType()
  {
    return this._mapType;
  }
  
  public void resolve(DeserializationContext paramDeserializationContext)
    throws JsonMappingException
  {
    Object localObject;
    if (this._valueInstantiator.canCreateUsingDelegate())
    {
      localObject = this._valueInstantiator.getDelegateType(paramDeserializationContext.getConfig());
      if (localObject == null) {
        throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._mapType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
      }
      this._delegateDeserializer = findDeserializer(paramDeserializationContext, (JavaType)localObject, null);
    }
    if (this._valueInstantiator.canCreateFromObjectWith())
    {
      localObject = this._valueInstantiator.getFromObjectArguments(paramDeserializationContext.getConfig());
      this._propertyBasedCreator = PropertyBasedCreator.construct(paramDeserializationContext, this._valueInstantiator, (SettableBeanProperty[])localObject);
    }
    this._standardStringKey = _isStdKeyDeser(this._mapType, this._keyDeserializer);
  }
  
  public void setIgnorableProperties(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {}
    for (paramArrayOfString = null;; paramArrayOfString = ArrayBuilders.arrayToSet(paramArrayOfString))
    {
      this._ignorableProperties = paramArrayOfString;
      return;
    }
  }
  
  protected MapDeserializer withResolved(KeyDeserializer paramKeyDeserializer, TypeDeserializer paramTypeDeserializer, JsonDeserializer<?> paramJsonDeserializer, HashSet<String> paramHashSet)
  {
    if ((this._keyDeserializer == paramKeyDeserializer) && (this._valueDeserializer == paramJsonDeserializer) && (this._valueTypeDeserializer == paramTypeDeserializer) && (this._ignorableProperties == paramHashSet)) {
      return this;
    }
    return new MapDeserializer(this, paramKeyDeserializer, paramJsonDeserializer, paramTypeDeserializer, paramHashSet);
  }
  
  protected void wrapAndThrow(Throwable paramThrowable, Object paramObject)
    throws IOException
  {
    while (((paramThrowable instanceof InvocationTargetException)) && (paramThrowable.getCause() != null)) {
      paramThrowable = paramThrowable.getCause();
    }
    if ((paramThrowable instanceof Error)) {
      throw ((Error)paramThrowable);
    }
    if (((paramThrowable instanceof IOException)) && (!(paramThrowable instanceof JsonMappingException))) {
      throw ((IOException)paramThrowable);
    }
    throw JsonMappingException.wrapWithPath(paramThrowable, paramObject, null);
  }
  
  private static final class MapReferring
    extends ReadableObjectId.Referring
  {
    private final MapDeserializer.MapReferringAccumulator _parent;
    public final Object key;
    public final Map<Object, Object> next = new LinkedHashMap();
    
    private MapReferring(MapDeserializer.MapReferringAccumulator paramMapReferringAccumulator, UnresolvedForwardReference paramUnresolvedForwardReference, Class<?> paramClass, Object paramObject)
    {
      super(paramClass);
      this._parent = paramMapReferringAccumulator;
      this.key = paramObject;
    }
    
    public void handleResolvedForwardReference(Object paramObject1, Object paramObject2)
      throws IOException
    {
      this._parent.resolveForwardReference(paramObject1, paramObject2);
    }
  }
  
  private static final class MapReferringAccumulator
  {
    private List<MapDeserializer.MapReferring> _accumulator = new ArrayList();
    private Map<Object, Object> _result;
    private final Class<?> _valueType;
    
    public MapReferringAccumulator(Class<?> paramClass, Map<Object, Object> paramMap)
    {
      this._valueType = paramClass;
      this._result = paramMap;
    }
    
    public ReadableObjectId.Referring handleUnresolvedReference(UnresolvedForwardReference paramUnresolvedForwardReference, Object paramObject)
    {
      paramUnresolvedForwardReference = new MapDeserializer.MapReferring(this, paramUnresolvedForwardReference, this._valueType, paramObject, null);
      this._accumulator.add(paramUnresolvedForwardReference);
      return paramUnresolvedForwardReference;
    }
    
    public void put(Object paramObject1, Object paramObject2)
    {
      if (this._accumulator.isEmpty())
      {
        this._result.put(paramObject1, paramObject2);
        return;
      }
      ((MapDeserializer.MapReferring)this._accumulator.get(this._accumulator.size() - 1)).next.put(paramObject1, paramObject2);
    }
    
    public void resolveForwardReference(Object paramObject1, Object paramObject2)
      throws IOException
    {
      Iterator localIterator = this._accumulator.iterator();
      MapDeserializer.MapReferring localMapReferring;
      for (Map localMap = this._result; localIterator.hasNext(); localMap = localMapReferring.next)
      {
        localMapReferring = (MapDeserializer.MapReferring)localIterator.next();
        if (localMapReferring.hasId(paramObject1))
        {
          localIterator.remove();
          localMap.put(localMapReferring.key, paramObject2);
          localMap.putAll(localMapReferring.next);
          return;
        }
      }
      throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + paramObject1 + "] that wasn't previously seen as unresolved.");
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/MapDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */