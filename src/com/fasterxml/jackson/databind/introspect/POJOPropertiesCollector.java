package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class POJOPropertiesCollector
{
  protected final AnnotationIntrospector _annotationIntrospector;
  protected LinkedList<AnnotatedMember> _anyGetters = null;
  protected LinkedList<AnnotatedMethod> _anySetters = null;
  protected final AnnotatedClass _classDef;
  protected final MapperConfig<?> _config;
  protected LinkedList<POJOPropertyBuilder> _creatorProperties = null;
  protected final boolean _forSerialization;
  protected HashSet<String> _ignoredPropertyNames;
  protected LinkedHashMap<Object, AnnotatedMember> _injectables;
  protected LinkedList<AnnotatedMethod> _jsonValueGetters = null;
  protected final String _mutatorPrefix;
  protected final LinkedHashMap<String, POJOPropertyBuilder> _properties = new LinkedHashMap();
  protected final JavaType _type;
  protected final VisibilityChecker<?> _visibilityChecker;
  
  protected POJOPropertiesCollector(MapperConfig<?> paramMapperConfig, boolean paramBoolean, JavaType paramJavaType, AnnotatedClass paramAnnotatedClass, String paramString)
  {
    this._config = paramMapperConfig;
    this._forSerialization = paramBoolean;
    this._type = paramJavaType;
    this._classDef = paramAnnotatedClass;
    paramJavaType = paramString;
    if (paramString == null) {
      paramJavaType = "set";
    }
    this._mutatorPrefix = paramJavaType;
    paramJavaType = (JavaType)localObject;
    if (paramMapperConfig.isAnnotationProcessingEnabled()) {
      paramJavaType = this._config.getAnnotationIntrospector();
    }
    this._annotationIntrospector = paramJavaType;
    if (this._annotationIntrospector == null)
    {
      this._visibilityChecker = this._config.getDefaultVisibilityChecker();
      return;
    }
    this._visibilityChecker = this._annotationIntrospector.findAutoDetectVisibility(paramAnnotatedClass, this._config.getDefaultVisibilityChecker());
  }
  
  private void _addIgnored(String paramString)
  {
    if (!this._forSerialization)
    {
      if (this._ignoredPropertyNames == null) {
        this._ignoredPropertyNames = new HashSet();
      }
      this._ignoredPropertyNames.add(paramString);
    }
  }
  
  private PropertyNamingStrategy _findNamingStrategy()
  {
    if (this._annotationIntrospector == null) {}
    for (Object localObject1 = null; localObject1 == null; localObject1 = this._annotationIntrospector.findNamingStrategy(this._classDef)) {
      return this._config.getPropertyNamingStrategy();
    }
    if ((localObject1 instanceof PropertyNamingStrategy)) {
      return (PropertyNamingStrategy)localObject1;
    }
    if (!(localObject1 instanceof Class)) {
      throw new IllegalStateException("AnnotationIntrospector returned PropertyNamingStrategy definition of type " + localObject1.getClass().getName() + "; expected type PropertyNamingStrategy or Class<PropertyNamingStrategy> instead");
    }
    localObject1 = (Class)localObject1;
    if (!PropertyNamingStrategy.class.isAssignableFrom((Class)localObject1)) {
      throw new IllegalStateException("AnnotationIntrospector returned Class " + ((Class)localObject1).getName() + "; expected Class<PropertyNamingStrategy>");
    }
    Object localObject2 = this._config.getHandlerInstantiator();
    if (localObject2 != null)
    {
      localObject2 = ((HandlerInstantiator)localObject2).namingStrategyInstance(this._config, this._classDef, (Class)localObject1);
      if (localObject2 != null) {
        return (PropertyNamingStrategy)localObject2;
      }
    }
    return (PropertyNamingStrategy)ClassUtil.createInstance((Class)localObject1, this._config.canOverrideAccessModifiers());
  }
  
  private PropertyName _propNameFromSimple(String paramString)
  {
    return PropertyName.construct(paramString, null);
  }
  
  protected void _addCreatorParam(AnnotatedParameter paramAnnotatedParameter)
  {
    Object localObject2 = this._annotationIntrospector.findImplicitPropertyName(paramAnnotatedParameter);
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = "";
    }
    localObject2 = this._annotationIntrospector.findNameForDeserialization(paramAnnotatedParameter);
    boolean bool;
    if ((localObject2 != null) && (!((PropertyName)localObject2).isEmpty())) {
      bool = true;
    }
    while (!bool) {
      if (((String)localObject1).isEmpty())
      {
        return;
        bool = false;
      }
      else
      {
        localObject2 = new PropertyName((String)localObject1);
      }
    }
    if (bool) {}
    for (localObject1 = _property((PropertyName)localObject2);; localObject1 = _property((String)localObject1))
    {
      ((POJOPropertyBuilder)localObject1).addCtor(paramAnnotatedParameter, (PropertyName)localObject2, bool, true, false);
      this._creatorProperties.add(localObject1);
      return;
    }
  }
  
  protected void _addCreators()
  {
    if (this._annotationIntrospector != null)
    {
      Iterator localIterator = this._classDef.getConstructors().iterator();
      Object localObject;
      int i;
      int j;
      while (localIterator.hasNext())
      {
        localObject = (AnnotatedConstructor)localIterator.next();
        if (this._creatorProperties == null) {
          this._creatorProperties = new LinkedList();
        }
        i = 0;
        j = ((AnnotatedConstructor)localObject).getParameterCount();
        while (i < j)
        {
          _addCreatorParam(((AnnotatedConstructor)localObject).getParameter(i));
          i += 1;
        }
      }
      localIterator = this._classDef.getStaticMethods().iterator();
      while (localIterator.hasNext())
      {
        localObject = (AnnotatedMethod)localIterator.next();
        if (this._creatorProperties == null) {
          this._creatorProperties = new LinkedList();
        }
        i = 0;
        j = ((AnnotatedMethod)localObject).getParameterCount();
        while (i < j)
        {
          _addCreatorParam(((AnnotatedMethod)localObject).getParameter(i));
          i += 1;
        }
      }
    }
  }
  
  protected void _addFields()
  {
    AnnotationIntrospector localAnnotationIntrospector = this._annotationIntrospector;
    int i;
    Iterator localIterator;
    if ((!this._forSerialization) && (!this._config.isEnabled(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS)))
    {
      i = 1;
      localIterator = this._classDef.fields().iterator();
    }
    label42:
    label72:
    label96:
    label103:
    label144:
    label242:
    label273:
    label278:
    label283:
    label286:
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      AnnotatedField localAnnotatedField = (AnnotatedField)localIterator.next();
      Object localObject1;
      Object localObject2;
      Object localObject3;
      boolean bool2;
      boolean bool3;
      if (localAnnotationIntrospector == null)
      {
        localObject1 = null;
        localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = localAnnotatedField.getName();
        }
        if (localAnnotationIntrospector != null) {
          break label242;
        }
        localObject1 = null;
        if (localObject1 == null) {
          break label273;
        }
        bool1 = true;
        localObject3 = localObject1;
        bool2 = bool1;
        if (bool1)
        {
          localObject3 = localObject1;
          bool2 = bool1;
          if (((PropertyName)localObject1).isEmpty())
          {
            localObject3 = _propNameFromSimple((String)localObject2);
            bool2 = false;
          }
        }
        if (localObject3 == null) {
          break label278;
        }
        bool1 = true;
        bool3 = bool1;
        if (!bool1) {
          bool3 = this._visibilityChecker.isFieldVisible(localAnnotatedField);
        }
        if ((localAnnotationIntrospector == null) || (!localAnnotationIntrospector.hasIgnoreMarker(localAnnotatedField))) {
          break label283;
        }
      }
      for (boolean bool1 = true;; bool1 = false)
      {
        if ((i != 0) && (localObject3 == null) && (!bool1) && (Modifier.isFinal(localAnnotatedField.getModifiers()))) {
          break label286;
        }
        _property((String)localObject2).addField(localAnnotatedField, (PropertyName)localObject3, bool2, bool3, bool1);
        break label42;
        i = 0;
        break;
        localObject1 = localAnnotationIntrospector.findImplicitPropertyName(localAnnotatedField);
        break label72;
        if (this._forSerialization)
        {
          localObject1 = localAnnotationIntrospector.findNameForSerialization(localAnnotatedField);
          break label96;
        }
        localObject1 = localAnnotationIntrospector.findNameForDeserialization(localAnnotatedField);
        break label96;
        bool1 = false;
        break label103;
        bool1 = false;
        break label144;
      }
    }
  }
  
  protected void _addGetterMethod(AnnotatedMethod paramAnnotatedMethod, AnnotationIntrospector paramAnnotationIntrospector)
  {
    boolean bool3 = false;
    Object localObject2 = null;
    Object localObject3 = null;
    if (!paramAnnotatedMethod.hasReturnType()) {}
    Object localObject1;
    boolean bool1;
    label107:
    label115:
    do
    {
      return;
      if (paramAnnotationIntrospector != null)
      {
        if (paramAnnotationIntrospector.hasAnyGetterAnnotation(paramAnnotatedMethod))
        {
          if (this._anyGetters == null) {
            this._anyGetters = new LinkedList();
          }
          this._anyGetters.add(paramAnnotatedMethod);
          return;
        }
        if (paramAnnotationIntrospector.hasAsValueAnnotation(paramAnnotatedMethod))
        {
          if (this._jsonValueGetters == null) {
            this._jsonValueGetters = new LinkedList();
          }
          this._jsonValueGetters.add(paramAnnotatedMethod);
          return;
        }
      }
      if (paramAnnotationIntrospector != null) {
        break;
      }
      localObject1 = null;
      if (localObject1 == null) {
        break label198;
      }
      bool1 = true;
      if (bool1) {
        break label228;
      }
      if (paramAnnotationIntrospector != null) {
        break label203;
      }
      localObject2 = localObject3;
      if (localObject3 == null) {
        localObject2 = BeanUtil.okNameForRegularGetter(paramAnnotatedMethod, paramAnnotatedMethod.getName());
      }
      if (localObject2 != null) {
        break label213;
      }
      localObject2 = BeanUtil.okNameForIsGetter(paramAnnotatedMethod, paramAnnotatedMethod.getName());
    } while (localObject2 == null);
    boolean bool2 = this._visibilityChecker.isIsGetterVisible(paramAnnotatedMethod);
    label166:
    if (paramAnnotationIntrospector == null) {}
    for (;;)
    {
      _property((String)localObject2).addGetter(paramAnnotatedMethod, (PropertyName)localObject1, bool1, bool2, bool3);
      return;
      localObject1 = paramAnnotationIntrospector.findNameForSerialization(paramAnnotatedMethod);
      break;
      label198:
      bool1 = false;
      break label107;
      label203:
      localObject3 = paramAnnotationIntrospector.findImplicitPropertyName(paramAnnotatedMethod);
      break label115;
      label213:
      bool2 = this._visibilityChecker.isGetterVisible(paramAnnotatedMethod);
      break label166;
      label228:
      if (paramAnnotationIntrospector == null) {}
      for (;;)
      {
        localObject3 = localObject2;
        if (localObject2 == null) {
          localObject3 = BeanUtil.okNameForGetter(paramAnnotatedMethod);
        }
        localObject2 = localObject3;
        if (localObject3 == null) {
          localObject2 = paramAnnotatedMethod.getName();
        }
        localObject3 = localObject1;
        if (((PropertyName)localObject1).isEmpty())
        {
          localObject3 = _propNameFromSimple((String)localObject2);
          bool1 = false;
        }
        bool2 = true;
        localObject1 = localObject3;
        break;
        localObject2 = paramAnnotationIntrospector.findImplicitPropertyName(paramAnnotatedMethod);
      }
      bool3 = paramAnnotationIntrospector.hasIgnoreMarker(paramAnnotatedMethod);
    }
  }
  
  protected void _addInjectables()
  {
    AnnotationIntrospector localAnnotationIntrospector = this._annotationIntrospector;
    if (localAnnotationIntrospector == null) {}
    for (;;)
    {
      return;
      Iterator localIterator = this._classDef.fields().iterator();
      Object localObject;
      while (localIterator.hasNext())
      {
        localObject = (AnnotatedField)localIterator.next();
        _doAddInjectable(localAnnotationIntrospector.findInjectableValueId((AnnotatedMember)localObject), (AnnotatedMember)localObject);
      }
      localIterator = this._classDef.memberMethods().iterator();
      while (localIterator.hasNext())
      {
        localObject = (AnnotatedMethod)localIterator.next();
        if (((AnnotatedMethod)localObject).getParameterCount() == 1) {
          _doAddInjectable(localAnnotationIntrospector.findInjectableValueId((AnnotatedMember)localObject), (AnnotatedMember)localObject);
        }
      }
    }
  }
  
  protected void _addMethods()
  {
    AnnotationIntrospector localAnnotationIntrospector = this._annotationIntrospector;
    Iterator localIterator = this._classDef.memberMethods().iterator();
    while (localIterator.hasNext())
    {
      AnnotatedMethod localAnnotatedMethod = (AnnotatedMethod)localIterator.next();
      int i = localAnnotatedMethod.getParameterCount();
      if (i == 0)
      {
        _addGetterMethod(localAnnotatedMethod, localAnnotationIntrospector);
      }
      else if (i == 1)
      {
        _addSetterMethod(localAnnotatedMethod, localAnnotationIntrospector);
      }
      else if ((i == 2) && (localAnnotationIntrospector != null) && (localAnnotationIntrospector.hasAnySetterAnnotation(localAnnotatedMethod)))
      {
        if (this._anySetters == null) {
          this._anySetters = new LinkedList();
        }
        this._anySetters.add(localAnnotatedMethod);
      }
    }
  }
  
  protected void _addSetterMethod(AnnotatedMethod paramAnnotatedMethod, AnnotationIntrospector paramAnnotationIntrospector)
  {
    boolean bool3 = false;
    Object localObject3 = null;
    Object localObject2 = null;
    Object localObject1;
    boolean bool1;
    if (paramAnnotationIntrospector == null)
    {
      localObject1 = null;
      if (localObject1 == null) {
        break label66;
      }
      bool1 = true;
      label23:
      if (bool1) {
        break label119;
      }
      if (paramAnnotationIntrospector != null) {
        break label71;
      }
    }
    for (;;)
    {
      localObject3 = localObject2;
      if (localObject2 == null) {
        localObject3 = BeanUtil.okNameForMutator(paramAnnotatedMethod, this._mutatorPrefix);
      }
      if (localObject3 != null) {
        break label81;
      }
      return;
      localObject1 = paramAnnotationIntrospector.findNameForDeserialization(paramAnnotatedMethod);
      break;
      label66:
      bool1 = false;
      break label23;
      label71:
      localObject2 = paramAnnotationIntrospector.findImplicitPropertyName(paramAnnotatedMethod);
    }
    label81:
    boolean bool2 = this._visibilityChecker.isSetterVisible(paramAnnotatedMethod);
    localObject2 = localObject3;
    if (paramAnnotationIntrospector == null) {}
    for (;;)
    {
      _property((String)localObject2).addSetter(paramAnnotatedMethod, (PropertyName)localObject1, bool1, bool2, bool3);
      return;
      label119:
      if (paramAnnotationIntrospector == null) {}
      for (localObject2 = localObject3;; localObject2 = paramAnnotationIntrospector.findImplicitPropertyName(paramAnnotatedMethod))
      {
        localObject3 = localObject2;
        if (localObject2 == null) {
          localObject3 = BeanUtil.okNameForMutator(paramAnnotatedMethod, this._mutatorPrefix);
        }
        localObject2 = localObject3;
        if (localObject3 == null) {
          localObject2 = paramAnnotatedMethod.getName();
        }
        localObject3 = localObject1;
        if (((PropertyName)localObject1).isEmpty())
        {
          localObject3 = _propNameFromSimple((String)localObject2);
          bool1 = false;
        }
        bool2 = true;
        localObject1 = localObject3;
        break;
      }
      bool3 = paramAnnotationIntrospector.hasIgnoreMarker(paramAnnotatedMethod);
    }
  }
  
  protected void _doAddInjectable(Object paramObject, AnnotatedMember paramAnnotatedMember)
  {
    if (paramObject == null) {}
    do
    {
      return;
      if (this._injectables == null) {
        this._injectables = new LinkedHashMap();
      }
    } while ((AnnotatedMember)this._injectables.put(paramObject, paramAnnotatedMember) == null);
    paramAnnotatedMember = paramObject.getClass().getName();
    throw new IllegalArgumentException("Duplicate injectable value with id '" + String.valueOf(paramObject) + "' (of type " + paramAnnotatedMember + ")");
  }
  
  protected POJOPropertyBuilder _property(PropertyName paramPropertyName)
  {
    return _property(paramPropertyName.getSimpleName());
  }
  
  protected POJOPropertyBuilder _property(String paramString)
  {
    POJOPropertyBuilder localPOJOPropertyBuilder2 = (POJOPropertyBuilder)this._properties.get(paramString);
    POJOPropertyBuilder localPOJOPropertyBuilder1 = localPOJOPropertyBuilder2;
    if (localPOJOPropertyBuilder2 == null)
    {
      localPOJOPropertyBuilder1 = new POJOPropertyBuilder(new PropertyName(paramString), this._annotationIntrospector, this._forSerialization);
      this._properties.put(paramString, localPOJOPropertyBuilder1);
    }
    return localPOJOPropertyBuilder1;
  }
  
  protected void _removeUnwantedProperties()
  {
    Iterator localIterator = this._properties.entrySet().iterator();
    boolean bool;
    if (!this._config.isEnabled(MapperFeature.INFER_PROPERTY_MUTATORS)) {
      bool = true;
    }
    while (localIterator.hasNext())
    {
      POJOPropertyBuilder localPOJOPropertyBuilder = (POJOPropertyBuilder)((Map.Entry)localIterator.next()).getValue();
      if (!localPOJOPropertyBuilder.anyVisible())
      {
        localIterator.remove();
        continue;
        bool = false;
      }
      else
      {
        if (localPOJOPropertyBuilder.anyIgnorals())
        {
          if (!localPOJOPropertyBuilder.isExplicitlyIncluded())
          {
            localIterator.remove();
            _addIgnored(localPOJOPropertyBuilder.getName());
            continue;
          }
          localPOJOPropertyBuilder.removeIgnored();
          if ((!this._forSerialization) && (!localPOJOPropertyBuilder.couldDeserialize())) {
            _addIgnored(localPOJOPropertyBuilder.getName());
          }
        }
        localPOJOPropertyBuilder.removeNonVisible(bool);
      }
    }
  }
  
  protected void _renameProperties()
  {
    Object localObject3 = this._properties.entrySet().iterator();
    Object localObject1 = null;
    POJOPropertyBuilder localPOJOPropertyBuilder;
    Object localObject2;
    while (((Iterator)localObject3).hasNext())
    {
      localPOJOPropertyBuilder = (POJOPropertyBuilder)((Map.Entry)((Iterator)localObject3).next()).getValue();
      Set localSet = localPOJOPropertyBuilder.findExplicitNames();
      if (!localSet.isEmpty())
      {
        ((Iterator)localObject3).remove();
        localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = new LinkedList();
        }
        if (localSet.size() == 1)
        {
          ((LinkedList)localObject2).add(localPOJOPropertyBuilder.withName((PropertyName)localSet.iterator().next()));
          localObject1 = localObject2;
        }
        else
        {
          ((LinkedList)localObject2).addAll(localPOJOPropertyBuilder.explode(localSet));
          localObject1 = localObject2;
        }
      }
    }
    if (localObject1 != null)
    {
      localObject1 = ((LinkedList)localObject1).iterator();
      if (((Iterator)localObject1).hasNext())
      {
        localObject2 = (POJOPropertyBuilder)((Iterator)localObject1).next();
        localObject3 = ((POJOPropertyBuilder)localObject2).getName();
        localPOJOPropertyBuilder = (POJOPropertyBuilder)this._properties.get(localObject3);
        if (localPOJOPropertyBuilder == null) {
          this._properties.put(localObject3, localObject2);
        }
        for (;;)
        {
          _updateCreatorProperty((POJOPropertyBuilder)localObject2, this._creatorProperties);
          break;
          localPOJOPropertyBuilder.addAll((POJOPropertyBuilder)localObject2);
        }
      }
    }
  }
  
  protected void _renameUsing(PropertyNamingStrategy paramPropertyNamingStrategy)
  {
    POJOPropertyBuilder[] arrayOfPOJOPropertyBuilder = (POJOPropertyBuilder[])this._properties.values().toArray(new POJOPropertyBuilder[this._properties.size()]);
    this._properties.clear();
    int j = arrayOfPOJOPropertyBuilder.length;
    int i = 0;
    if (i < j)
    {
      Object localObject2 = arrayOfPOJOPropertyBuilder[i];
      PropertyName localPropertyName = ((POJOPropertyBuilder)localObject2).getFullName();
      Object localObject3 = null;
      Object localObject1 = localObject3;
      if (!((POJOPropertyBuilder)localObject2).isExplicitlyNamed())
      {
        if (!this._forSerialization) {
          break label223;
        }
        if (((POJOPropertyBuilder)localObject2).hasGetter()) {
          localObject1 = paramPropertyNamingStrategy.nameForGetterMethod(this._config, ((POJOPropertyBuilder)localObject2).getGetter(), localPropertyName.getSimpleName());
        }
      }
      else
      {
        label108:
        if ((localObject1 == null) || (localPropertyName.hasSimpleName((String)localObject1))) {
          break label351;
        }
        localObject3 = ((POJOPropertyBuilder)localObject2).withSimpleName((String)localObject1);
        localObject2 = localObject1;
        localObject1 = localObject3;
        label140:
        localObject3 = (POJOPropertyBuilder)this._properties.get(localObject2);
        if (localObject3 != null) {
          break label369;
        }
        this._properties.put(localObject2, localObject1);
      }
      for (;;)
      {
        _updateCreatorProperty((POJOPropertyBuilder)localObject1, this._creatorProperties);
        i += 1;
        break;
        localObject1 = localObject3;
        if (!((POJOPropertyBuilder)localObject2).hasField()) {
          break label108;
        }
        localObject1 = paramPropertyNamingStrategy.nameForField(this._config, ((POJOPropertyBuilder)localObject2).getField(), localPropertyName.getSimpleName());
        break label108;
        label223:
        if (((POJOPropertyBuilder)localObject2).hasSetter())
        {
          localObject1 = paramPropertyNamingStrategy.nameForSetterMethod(this._config, ((POJOPropertyBuilder)localObject2).getSetter(), localPropertyName.getSimpleName());
          break label108;
        }
        if (((POJOPropertyBuilder)localObject2).hasConstructorParameter())
        {
          localObject1 = paramPropertyNamingStrategy.nameForConstructorParameter(this._config, ((POJOPropertyBuilder)localObject2).getConstructorParameter(), localPropertyName.getSimpleName());
          break label108;
        }
        if (((POJOPropertyBuilder)localObject2).hasField())
        {
          localObject1 = paramPropertyNamingStrategy.nameForField(this._config, ((POJOPropertyBuilder)localObject2).getField(), localPropertyName.getSimpleName());
          break label108;
        }
        localObject1 = localObject3;
        if (!((POJOPropertyBuilder)localObject2).hasGetter()) {
          break label108;
        }
        localObject1 = paramPropertyNamingStrategy.nameForGetterMethod(this._config, ((POJOPropertyBuilder)localObject2).getGetter(), localPropertyName.getSimpleName());
        break label108;
        label351:
        localObject3 = localPropertyName.getSimpleName();
        localObject1 = localObject2;
        localObject2 = localObject3;
        break label140;
        label369:
        ((POJOPropertyBuilder)localObject3).addAll((POJOPropertyBuilder)localObject1);
      }
    }
  }
  
  protected void _renameWithWrappers()
  {
    Object localObject3 = this._properties.entrySet().iterator();
    Object localObject1 = null;
    POJOPropertyBuilder localPOJOPropertyBuilder;
    Object localObject2;
    while (((Iterator)localObject3).hasNext())
    {
      localPOJOPropertyBuilder = (POJOPropertyBuilder)((Map.Entry)((Iterator)localObject3).next()).getValue();
      localObject2 = localPOJOPropertyBuilder.getPrimaryMember();
      if (localObject2 != null)
      {
        PropertyName localPropertyName = this._annotationIntrospector.findWrapperName((Annotated)localObject2);
        if ((localPropertyName != null) && (localPropertyName.hasSimpleName()) && (!localPropertyName.equals(localPOJOPropertyBuilder.getFullName())))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new LinkedList();
          }
          ((LinkedList)localObject2).add(localPOJOPropertyBuilder.withName(localPropertyName));
          ((Iterator)localObject3).remove();
          localObject1 = localObject2;
        }
      }
    }
    if (localObject1 != null)
    {
      localObject1 = ((LinkedList)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (POJOPropertyBuilder)((Iterator)localObject1).next();
        localObject3 = ((POJOPropertyBuilder)localObject2).getName();
        localPOJOPropertyBuilder = (POJOPropertyBuilder)this._properties.get(localObject3);
        if (localPOJOPropertyBuilder == null) {
          this._properties.put(localObject3, localObject2);
        } else {
          localPOJOPropertyBuilder.addAll((POJOPropertyBuilder)localObject2);
        }
      }
    }
  }
  
  protected void _sortProperties()
  {
    Object localObject2 = this._annotationIntrospector;
    boolean bool;
    if (localObject2 == null)
    {
      localObject1 = null;
      if (localObject1 != null) {
        break label66;
      }
      bool = this._config.shouldSortPropertiesAlphabetically();
      label27:
      if (localObject2 != null) {
        break label75;
      }
    }
    label66:
    label75:
    for (localObject2 = null;; localObject2 = ((AnnotationIntrospector)localObject2).findSerializationPropertyOrder(this._classDef))
    {
      if ((bool) || (this._creatorProperties != null) || (localObject2 != null)) {
        break label89;
      }
      return;
      localObject1 = ((AnnotationIntrospector)localObject2).findSerializationSortAlphabetically(this._classDef);
      break;
      bool = ((Boolean)localObject1).booleanValue();
      break label27;
    }
    label89:
    int i = this._properties.size();
    if (bool) {}
    Object localObject3;
    Object localObject4;
    for (Object localObject1 = new TreeMap();; localObject1 = new LinkedHashMap(i + i))
    {
      localObject3 = this._properties.values().iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (POJOPropertyBuilder)((Iterator)localObject3).next();
        ((Map)localObject1).put(((POJOPropertyBuilder)localObject4).getName(), localObject4);
      }
    }
    LinkedHashMap localLinkedHashMap = new LinkedHashMap(i + i);
    if (localObject2 != null)
    {
      int j = localObject2.length;
      i = 0;
      while (i < j)
      {
        Object localObject5 = localObject2[i];
        POJOPropertyBuilder localPOJOPropertyBuilder = (POJOPropertyBuilder)((Map)localObject1).get(localObject5);
        localObject4 = localObject5;
        localObject3 = localPOJOPropertyBuilder;
        if (localPOJOPropertyBuilder == null)
        {
          Iterator localIterator = this._properties.values().iterator();
          do
          {
            localObject4 = localObject5;
            localObject3 = localPOJOPropertyBuilder;
            if (!localIterator.hasNext()) {
              break;
            }
            localObject4 = (POJOPropertyBuilder)localIterator.next();
          } while (!((String)localObject5).equals(((POJOPropertyBuilder)localObject4).getInternalName()));
          localObject3 = localObject4;
          localObject4 = ((POJOPropertyBuilder)localObject4).getName();
        }
        if (localObject3 != null) {
          localLinkedHashMap.put(localObject4, localObject3);
        }
        i += 1;
      }
    }
    if (this._creatorProperties != null)
    {
      if (bool)
      {
        localObject2 = new TreeMap();
        localObject3 = this._creatorProperties.iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (POJOPropertyBuilder)((Iterator)localObject3).next();
          ((TreeMap)localObject2).put(((POJOPropertyBuilder)localObject4).getName(), localObject4);
        }
      }
      for (localObject2 = ((TreeMap)localObject2).values();; localObject2 = this._creatorProperties)
      {
        localObject2 = ((Collection)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (POJOPropertyBuilder)((Iterator)localObject2).next();
          localLinkedHashMap.put(((POJOPropertyBuilder)localObject3).getName(), localObject3);
        }
      }
    }
    localLinkedHashMap.putAll((Map)localObject1);
    this._properties.clear();
    this._properties.putAll(localLinkedHashMap);
  }
  
  protected void _updateCreatorProperty(POJOPropertyBuilder paramPOJOPropertyBuilder, List<POJOPropertyBuilder> paramList)
  {
    int i;
    int j;
    if (paramList != null)
    {
      i = 0;
      j = paramList.size();
    }
    for (;;)
    {
      if (i < j)
      {
        if (((POJOPropertyBuilder)paramList.get(i)).getInternalName().equals(paramPOJOPropertyBuilder.getInternalName())) {
          paramList.set(i, paramPOJOPropertyBuilder);
        }
      }
      else {
        return;
      }
      i += 1;
    }
  }
  
  public POJOPropertiesCollector collect()
  {
    this._properties.clear();
    _addFields();
    _addMethods();
    _addCreators();
    _addInjectables();
    _removeUnwantedProperties();
    _renameProperties();
    Object localObject = _findNamingStrategy();
    if (localObject != null) {
      _renameUsing((PropertyNamingStrategy)localObject);
    }
    localObject = this._properties.values().iterator();
    while (((Iterator)localObject).hasNext()) {
      ((POJOPropertyBuilder)((Iterator)localObject).next()).trimByVisibility();
    }
    localObject = this._properties.values().iterator();
    while (((Iterator)localObject).hasNext()) {
      ((POJOPropertyBuilder)((Iterator)localObject).next()).mergeAnnotations(this._forSerialization);
    }
    if (this._config.isEnabled(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME)) {
      _renameWithWrappers();
    }
    _sortProperties();
    return this;
  }
  
  public Class<?> findPOJOBuilderClass()
  {
    return this._annotationIntrospector.findPOJOBuilder(this._classDef);
  }
  
  public AnnotationIntrospector getAnnotationIntrospector()
  {
    return this._annotationIntrospector;
  }
  
  public AnnotatedMember getAnyGetter()
  {
    if (this._anyGetters != null)
    {
      if (this._anyGetters.size() > 1) {
        reportProblem("Multiple 'any-getters' defined (" + this._anyGetters.get(0) + " vs " + this._anyGetters.get(1) + ")");
      }
      return (AnnotatedMember)this._anyGetters.getFirst();
    }
    return null;
  }
  
  public AnnotatedMethod getAnySetterMethod()
  {
    if (this._anySetters != null)
    {
      if (this._anySetters.size() > 1) {
        reportProblem("Multiple 'any-setters' defined (" + this._anySetters.get(0) + " vs " + this._anySetters.get(1) + ")");
      }
      return (AnnotatedMethod)this._anySetters.getFirst();
    }
    return null;
  }
  
  public AnnotatedClass getClassDef()
  {
    return this._classDef;
  }
  
  public MapperConfig<?> getConfig()
  {
    return this._config;
  }
  
  public Set<String> getIgnoredPropertyNames()
  {
    return this._ignoredPropertyNames;
  }
  
  public Map<Object, AnnotatedMember> getInjectables()
  {
    return this._injectables;
  }
  
  public AnnotatedMethod getJsonValueMethod()
  {
    if (this._jsonValueGetters != null)
    {
      if (this._jsonValueGetters.size() > 1) {
        reportProblem("Multiple value properties defined (" + this._jsonValueGetters.get(0) + " vs " + this._jsonValueGetters.get(1) + ")");
      }
      return (AnnotatedMethod)this._jsonValueGetters.get(0);
    }
    return null;
  }
  
  public ObjectIdInfo getObjectIdInfo()
  {
    Object localObject;
    if (this._annotationIntrospector == null) {
      localObject = null;
    }
    ObjectIdInfo localObjectIdInfo;
    do
    {
      return (ObjectIdInfo)localObject;
      localObjectIdInfo = this._annotationIntrospector.findObjectIdInfo(this._classDef);
      localObject = localObjectIdInfo;
    } while (localObjectIdInfo == null);
    return this._annotationIntrospector.findObjectReferenceInfo(this._classDef, localObjectIdInfo);
  }
  
  public List<BeanPropertyDefinition> getProperties()
  {
    return new ArrayList(this._properties.values());
  }
  
  protected Map<String, POJOPropertyBuilder> getPropertyMap()
  {
    return this._properties;
  }
  
  public JavaType getType()
  {
    return this._type;
  }
  
  protected void reportProblem(String paramString)
  {
    throw new IllegalArgumentException("Problem with definition of " + this._classDef + ": " + paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/introspect/POJOPropertiesCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */