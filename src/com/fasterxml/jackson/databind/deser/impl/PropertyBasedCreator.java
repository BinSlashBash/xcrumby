package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public final class PropertyBasedCreator
{
  protected final Object[] _defaultValues;
  protected final HashMap<String, SettableBeanProperty> _properties;
  protected final SettableBeanProperty[] _propertiesWithInjectables;
  protected final int _propertyCount;
  protected final ValueInstantiator _valueInstantiator;
  
  protected PropertyBasedCreator(ValueInstantiator paramValueInstantiator, SettableBeanProperty[] paramArrayOfSettableBeanProperty, Object[] paramArrayOfObject)
  {
    this._valueInstantiator = paramValueInstantiator;
    this._properties = new HashMap();
    paramValueInstantiator = null;
    int j = paramArrayOfSettableBeanProperty.length;
    this._propertyCount = j;
    int i = 0;
    while (i < j)
    {
      SettableBeanProperty localSettableBeanProperty = paramArrayOfSettableBeanProperty[i];
      this._properties.put(localSettableBeanProperty.getName(), localSettableBeanProperty);
      Object localObject = paramValueInstantiator;
      if (localSettableBeanProperty.getInjectableValueId() != null)
      {
        localObject = paramValueInstantiator;
        if (paramValueInstantiator == null) {
          localObject = new SettableBeanProperty[j];
        }
        localObject[i] = localSettableBeanProperty;
      }
      i += 1;
      paramValueInstantiator = (ValueInstantiator)localObject;
    }
    this._defaultValues = paramArrayOfObject;
    this._propertiesWithInjectables = paramValueInstantiator;
  }
  
  public static PropertyBasedCreator construct(DeserializationContext paramDeserializationContext, ValueInstantiator paramValueInstantiator, SettableBeanProperty[] paramArrayOfSettableBeanProperty)
    throws JsonMappingException
  {
    int j = paramArrayOfSettableBeanProperty.length;
    SettableBeanProperty[] arrayOfSettableBeanProperty = new SettableBeanProperty[j];
    Object localObject1 = null;
    int i = 0;
    if (i < j)
    {
      Object localObject2 = paramArrayOfSettableBeanProperty[i];
      Object localObject3 = localObject2;
      if (!((SettableBeanProperty)localObject2).hasValueDeserializer()) {
        localObject3 = ((SettableBeanProperty)localObject2).withValueDeserializer(paramDeserializationContext.findContextualValueDeserializer(((SettableBeanProperty)localObject2).getType(), (BeanProperty)localObject2));
      }
      arrayOfSettableBeanProperty[i] = localObject3;
      localObject2 = ((SettableBeanProperty)localObject3).getValueDeserializer();
      if (localObject2 == null) {}
      for (localObject2 = null;; localObject2 = ((JsonDeserializer)localObject2).getNullValue())
      {
        Object localObject4 = localObject2;
        if (localObject2 == null)
        {
          localObject4 = localObject2;
          if (((SettableBeanProperty)localObject3).getType().isPrimitive()) {
            localObject4 = ClassUtil.defaultValue(((SettableBeanProperty)localObject3).getType().getRawClass());
          }
        }
        localObject2 = localObject1;
        if (localObject4 != null)
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new Object[j];
          }
          localObject2[i] = localObject4;
        }
        i += 1;
        localObject1 = localObject2;
        break;
      }
    }
    return new PropertyBasedCreator(paramValueInstantiator, arrayOfSettableBeanProperty, (Object[])localObject1);
  }
  
  public void assignDeserializer(SettableBeanProperty paramSettableBeanProperty, JsonDeserializer<Object> paramJsonDeserializer)
  {
    paramSettableBeanProperty = paramSettableBeanProperty.withValueDeserializer(paramJsonDeserializer);
    this._properties.put(paramSettableBeanProperty.getName(), paramSettableBeanProperty);
  }
  
  public Object build(DeserializationContext paramDeserializationContext, PropertyValueBuffer paramPropertyValueBuffer)
    throws IOException
  {
    Object localObject = paramPropertyValueBuffer.handleIdValue(paramDeserializationContext, this._valueInstantiator.createFromObjectWith(paramDeserializationContext, paramPropertyValueBuffer.getParameters(this._defaultValues)));
    for (paramDeserializationContext = paramPropertyValueBuffer.buffered(); paramDeserializationContext != null; paramDeserializationContext = paramDeserializationContext.next) {
      paramDeserializationContext.assign(localObject);
    }
    return localObject;
  }
  
  public SettableBeanProperty findCreatorProperty(int paramInt)
  {
    Iterator localIterator = this._properties.values().iterator();
    while (localIterator.hasNext())
    {
      SettableBeanProperty localSettableBeanProperty = (SettableBeanProperty)localIterator.next();
      if (localSettableBeanProperty.getPropertyIndex() == paramInt) {
        return localSettableBeanProperty;
      }
    }
    return null;
  }
  
  public SettableBeanProperty findCreatorProperty(String paramString)
  {
    return (SettableBeanProperty)this._properties.get(paramString);
  }
  
  public Collection<SettableBeanProperty> properties()
  {
    return this._properties.values();
  }
  
  public PropertyValueBuffer startBuilding(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, ObjectIdReader paramObjectIdReader)
  {
    paramJsonParser = new PropertyValueBuffer(paramJsonParser, paramDeserializationContext, this._propertyCount, paramObjectIdReader);
    if (this._propertiesWithInjectables != null) {
      paramJsonParser.inject(this._propertiesWithInjectables);
    }
    return paramJsonParser;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/impl/PropertyBasedCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */