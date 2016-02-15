package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.Annotations;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BeanDeserializerBuilder
{
  protected SettableAnyProperty _anySetter;
  protected HashMap<String, SettableBeanProperty> _backRefProperties;
  protected final BeanDescription _beanDesc;
  protected AnnotatedMethod _buildMethod;
  protected JsonPOJOBuilder.Value _builderConfig;
  protected final boolean _defaultViewInclusion;
  protected HashSet<String> _ignorableProps;
  protected boolean _ignoreAllUnknown;
  protected List<ValueInjector> _injectables;
  protected ObjectIdReader _objectIdReader;
  protected final Map<String, SettableBeanProperty> _properties = new LinkedHashMap();
  protected ValueInstantiator _valueInstantiator;
  
  public BeanDeserializerBuilder(BeanDescription paramBeanDescription, DeserializationConfig paramDeserializationConfig)
  {
    this._beanDesc = paramBeanDescription;
    this._defaultViewInclusion = paramDeserializationConfig.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
  }
  
  protected BeanDeserializerBuilder(BeanDeserializerBuilder paramBeanDeserializerBuilder)
  {
    this._beanDesc = paramBeanDeserializerBuilder._beanDesc;
    this._defaultViewInclusion = paramBeanDeserializerBuilder._defaultViewInclusion;
    this._properties.putAll(paramBeanDeserializerBuilder._properties);
    this._injectables = _copy(paramBeanDeserializerBuilder._injectables);
    this._backRefProperties = _copy(paramBeanDeserializerBuilder._backRefProperties);
    this._ignorableProps = paramBeanDeserializerBuilder._ignorableProps;
    this._valueInstantiator = paramBeanDeserializerBuilder._valueInstantiator;
    this._objectIdReader = paramBeanDeserializerBuilder._objectIdReader;
    this._anySetter = paramBeanDeserializerBuilder._anySetter;
    this._ignoreAllUnknown = paramBeanDeserializerBuilder._ignoreAllUnknown;
    this._buildMethod = paramBeanDeserializerBuilder._buildMethod;
    this._builderConfig = paramBeanDeserializerBuilder._builderConfig;
  }
  
  private static HashMap<String, SettableBeanProperty> _copy(HashMap<String, SettableBeanProperty> paramHashMap)
  {
    if (paramHashMap == null) {
      return null;
    }
    return new HashMap(paramHashMap);
  }
  
  private static <T> List<T> _copy(List<T> paramList)
  {
    if (paramList == null) {
      return null;
    }
    return new ArrayList(paramList);
  }
  
  public void addBackReferenceProperty(String paramString, SettableBeanProperty paramSettableBeanProperty)
  {
    if (this._backRefProperties == null) {
      this._backRefProperties = new HashMap(4);
    }
    this._backRefProperties.put(paramString, paramSettableBeanProperty);
    if (this._properties != null) {
      this._properties.remove(paramSettableBeanProperty.getName());
    }
  }
  
  public void addCreatorProperty(SettableBeanProperty paramSettableBeanProperty)
  {
    addProperty(paramSettableBeanProperty);
  }
  
  public void addIgnorable(String paramString)
  {
    if (this._ignorableProps == null) {
      this._ignorableProps = new HashSet();
    }
    this._ignorableProps.add(paramString);
  }
  
  public void addInjectable(PropertyName paramPropertyName, JavaType paramJavaType, Annotations paramAnnotations, AnnotatedMember paramAnnotatedMember, Object paramObject)
  {
    if (this._injectables == null) {
      this._injectables = new ArrayList();
    }
    this._injectables.add(new ValueInjector(paramPropertyName, paramJavaType, paramAnnotations, paramAnnotatedMember, paramObject));
  }
  
  @Deprecated
  public void addInjectable(String paramString, JavaType paramJavaType, Annotations paramAnnotations, AnnotatedMember paramAnnotatedMember, Object paramObject)
  {
    addInjectable(new PropertyName(paramString), paramJavaType, paramAnnotations, paramAnnotatedMember, paramObject);
  }
  
  public void addOrReplaceProperty(SettableBeanProperty paramSettableBeanProperty, boolean paramBoolean)
  {
    this._properties.put(paramSettableBeanProperty.getName(), paramSettableBeanProperty);
  }
  
  public void addProperty(SettableBeanProperty paramSettableBeanProperty)
  {
    SettableBeanProperty localSettableBeanProperty = (SettableBeanProperty)this._properties.put(paramSettableBeanProperty.getName(), paramSettableBeanProperty);
    if ((localSettableBeanProperty != null) && (localSettableBeanProperty != paramSettableBeanProperty)) {
      throw new IllegalArgumentException("Duplicate property '" + paramSettableBeanProperty.getName() + "' for " + this._beanDesc.getType());
    }
  }
  
  public JsonDeserializer<?> build()
  {
    Object localObject = this._properties.values();
    BeanPropertyMap localBeanPropertyMap = new BeanPropertyMap((Collection)localObject);
    localBeanPropertyMap.assignIndexes();
    if (!this._defaultViewInclusion) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      boolean bool2 = bool1;
      if (!bool1)
      {
        localObject = ((Collection)localObject).iterator();
        do
        {
          bool2 = bool1;
          if (!((Iterator)localObject).hasNext()) {
            break;
          }
        } while (!((SettableBeanProperty)((Iterator)localObject).next()).hasViews());
        bool2 = true;
      }
      localObject = localBeanPropertyMap;
      if (this._objectIdReader != null) {
        localObject = localBeanPropertyMap.withProperty(new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED));
      }
      return new BeanDeserializer(this, this._beanDesc, (BeanPropertyMap)localObject, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, bool2);
    }
  }
  
  public AbstractDeserializer buildAbstract()
  {
    return new AbstractDeserializer(this, this._beanDesc, this._backRefProperties);
  }
  
  public JsonDeserializer<?> buildBuilderBased(JavaType paramJavaType, String paramString)
  {
    if (this._buildMethod == null) {
      throw new IllegalArgumentException("Builder class " + this._beanDesc.getBeanClass().getName() + " does not have build method '" + paramString + "()'");
    }
    paramString = this._buildMethod.getRawReturnType();
    if (!paramJavaType.getRawClass().isAssignableFrom(paramString)) {
      throw new IllegalArgumentException("Build method '" + this._buildMethod.getFullName() + " has bad return type (" + paramString.getName() + "), not compatible with POJO type (" + paramJavaType.getRawClass().getName() + ")");
    }
    paramJavaType = this._properties.values();
    paramString = new BeanPropertyMap(paramJavaType);
    paramString.assignIndexes();
    if (!this._defaultViewInclusion) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      boolean bool2 = bool1;
      if (!bool1)
      {
        paramJavaType = paramJavaType.iterator();
        do
        {
          bool2 = bool1;
          if (!paramJavaType.hasNext()) {
            break;
          }
        } while (!((SettableBeanProperty)paramJavaType.next()).hasViews());
        bool2 = true;
      }
      paramJavaType = paramString;
      if (this._objectIdReader != null) {
        paramJavaType = paramString.withProperty(new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED));
      }
      return new BuilderBasedDeserializer(this, this._beanDesc, paramJavaType, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, bool2);
    }
  }
  
  public SettableBeanProperty findProperty(PropertyName paramPropertyName)
  {
    return (SettableBeanProperty)this._properties.get(paramPropertyName.getSimpleName());
  }
  
  @Deprecated
  public SettableBeanProperty findProperty(String paramString)
  {
    return (SettableBeanProperty)this._properties.get(paramString);
  }
  
  public SettableAnyProperty getAnySetter()
  {
    return this._anySetter;
  }
  
  public AnnotatedMethod getBuildMethod()
  {
    return this._buildMethod;
  }
  
  public JsonPOJOBuilder.Value getBuilderConfig()
  {
    return this._builderConfig;
  }
  
  public List<ValueInjector> getInjectables()
  {
    return this._injectables;
  }
  
  public ObjectIdReader getObjectIdReader()
  {
    return this._objectIdReader;
  }
  
  public Iterator<SettableBeanProperty> getProperties()
  {
    return this._properties.values().iterator();
  }
  
  public ValueInstantiator getValueInstantiator()
  {
    return this._valueInstantiator;
  }
  
  public boolean hasProperty(PropertyName paramPropertyName)
  {
    return findProperty(paramPropertyName) != null;
  }
  
  @Deprecated
  public boolean hasProperty(String paramString)
  {
    return findProperty(paramString) != null;
  }
  
  public SettableBeanProperty removeProperty(PropertyName paramPropertyName)
  {
    return (SettableBeanProperty)this._properties.remove(paramPropertyName.getSimpleName());
  }
  
  @Deprecated
  public SettableBeanProperty removeProperty(String paramString)
  {
    return (SettableBeanProperty)this._properties.remove(paramString);
  }
  
  public void setAnySetter(SettableAnyProperty paramSettableAnyProperty)
  {
    if ((this._anySetter != null) && (paramSettableAnyProperty != null)) {
      throw new IllegalStateException("_anySetter already set to non-null");
    }
    this._anySetter = paramSettableAnyProperty;
  }
  
  public void setIgnoreUnknownProperties(boolean paramBoolean)
  {
    this._ignoreAllUnknown = paramBoolean;
  }
  
  public void setObjectIdReader(ObjectIdReader paramObjectIdReader)
  {
    this._objectIdReader = paramObjectIdReader;
  }
  
  public void setPOJOBuilder(AnnotatedMethod paramAnnotatedMethod, JsonPOJOBuilder.Value paramValue)
  {
    this._buildMethod = paramAnnotatedMethod;
    this._builderConfig = paramValue;
  }
  
  public void setValueInstantiator(ValueInstantiator paramValueInstantiator)
  {
    this._valueInstantiator = paramValueInstantiator;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/BeanDeserializerBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */