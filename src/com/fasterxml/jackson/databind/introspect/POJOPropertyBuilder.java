package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class POJOPropertyBuilder
  extends BeanPropertyDefinition
  implements Comparable<POJOPropertyBuilder>
{
  protected final AnnotationIntrospector _annotationIntrospector;
  protected Linked<AnnotatedParameter> _ctorParameters;
  protected Linked<AnnotatedField> _fields;
  protected final boolean _forSerialization;
  protected Linked<AnnotatedMethod> _getters;
  protected final PropertyName _internalName;
  protected final PropertyName _name;
  protected Linked<AnnotatedMethod> _setters;
  
  public POJOPropertyBuilder(PropertyName paramPropertyName, AnnotationIntrospector paramAnnotationIntrospector, boolean paramBoolean)
  {
    this(paramPropertyName, paramPropertyName, paramAnnotationIntrospector, paramBoolean);
  }
  
  protected POJOPropertyBuilder(PropertyName paramPropertyName1, PropertyName paramPropertyName2, AnnotationIntrospector paramAnnotationIntrospector, boolean paramBoolean)
  {
    this._internalName = paramPropertyName1;
    this._name = paramPropertyName2;
    this._annotationIntrospector = paramAnnotationIntrospector;
    this._forSerialization = paramBoolean;
  }
  
  public POJOPropertyBuilder(POJOPropertyBuilder paramPOJOPropertyBuilder, PropertyName paramPropertyName)
  {
    this._internalName = paramPOJOPropertyBuilder._internalName;
    this._name = paramPropertyName;
    this._annotationIntrospector = paramPOJOPropertyBuilder._annotationIntrospector;
    this._fields = paramPOJOPropertyBuilder._fields;
    this._ctorParameters = paramPOJOPropertyBuilder._ctorParameters;
    this._getters = paramPOJOPropertyBuilder._getters;
    this._setters = paramPOJOPropertyBuilder._setters;
    this._forSerialization = paramPOJOPropertyBuilder._forSerialization;
  }
  
  @Deprecated
  public POJOPropertyBuilder(String paramString, AnnotationIntrospector paramAnnotationIntrospector, boolean paramBoolean)
  {
    this(new PropertyName(paramString), paramAnnotationIntrospector, paramBoolean);
  }
  
  private <T> boolean _anyExplicitNames(Linked<T> paramLinked)
  {
    while (paramLinked != null)
    {
      if ((paramLinked.name != null) && (paramLinked.isNameExplicit)) {
        return true;
      }
      paramLinked = paramLinked.next;
    }
    return false;
  }
  
  private <T> boolean _anyExplicits(Linked<T> paramLinked)
  {
    while (paramLinked != null)
    {
      if ((paramLinked.name != null) && (paramLinked.name.hasSimpleName())) {
        return true;
      }
      paramLinked = paramLinked.next;
    }
    return false;
  }
  
  private <T> boolean _anyIgnorals(Linked<T> paramLinked)
  {
    while (paramLinked != null)
    {
      if (paramLinked.isMarkedIgnored) {
        return true;
      }
      paramLinked = paramLinked.next;
    }
    return false;
  }
  
  private <T> boolean _anyVisible(Linked<T> paramLinked)
  {
    while (paramLinked != null)
    {
      if (paramLinked.isVisible) {
        return true;
      }
      paramLinked = paramLinked.next;
    }
    return false;
  }
  
  private void _explode(Collection<PropertyName> paramCollection, Map<PropertyName, POJOPropertyBuilder> paramMap, Linked<?> paramLinked)
  {
    Object localObject = paramLinked;
    if (localObject != null)
    {
      PropertyName localPropertyName = ((Linked)localObject).name;
      if ((!((Linked)localObject).isNameExplicit) || (localPropertyName == null)) {
        throw new IllegalStateException("Conflicting/ambiguous property name definitions (implicit name '" + this._name + "'): found multiple explicit names: " + paramCollection + ", but also implicit accessor: " + localObject);
      }
      POJOPropertyBuilder localPOJOPropertyBuilder2 = (POJOPropertyBuilder)paramMap.get(localPropertyName);
      POJOPropertyBuilder localPOJOPropertyBuilder1 = localPOJOPropertyBuilder2;
      if (localPOJOPropertyBuilder2 == null)
      {
        localPOJOPropertyBuilder1 = new POJOPropertyBuilder(this._internalName, localPropertyName, this._annotationIntrospector, this._forSerialization);
        paramMap.put(localPropertyName, localPOJOPropertyBuilder1);
      }
      if (paramLinked == this._fields) {
        localPOJOPropertyBuilder1._fields = ((Linked)localObject).withNext(localPOJOPropertyBuilder1._fields);
      }
      for (;;)
      {
        localObject = ((Linked)localObject).next;
        break;
        if (paramLinked == this._getters)
        {
          localPOJOPropertyBuilder1._getters = ((Linked)localObject).withNext(localPOJOPropertyBuilder1._getters);
        }
        else if (paramLinked == this._setters)
        {
          localPOJOPropertyBuilder1._setters = ((Linked)localObject).withNext(localPOJOPropertyBuilder1._setters);
        }
        else
        {
          if (paramLinked != this._ctorParameters) {
            break label244;
          }
          localPOJOPropertyBuilder1._ctorParameters = ((Linked)localObject).withNext(localPOJOPropertyBuilder1._ctorParameters);
        }
      }
      label244:
      throw new IllegalStateException("Internal error: mismatched accessors, property: " + this);
    }
  }
  
  private Set<PropertyName> _findExplicitNames(Linked<? extends AnnotatedMember> paramLinked, Set<PropertyName> paramSet)
  {
    Object localObject = paramLinked;
    if (localObject != null)
    {
      paramLinked = paramSet;
      if (((Linked)localObject).isNameExplicit)
      {
        if (((Linked)localObject).name != null) {
          break label34;
        }
        paramLinked = paramSet;
      }
      for (;;)
      {
        localObject = ((Linked)localObject).next;
        paramSet = paramLinked;
        break;
        label34:
        paramLinked = paramSet;
        if (paramSet == null) {
          paramLinked = new HashSet();
        }
        paramLinked.add(((Linked)localObject).name);
      }
    }
    return paramSet;
  }
  
  private AnnotationMap _mergeAnnotations(int paramInt, Linked<? extends AnnotatedMember>... paramVarArgs)
  {
    AnnotationMap localAnnotationMap2 = ((AnnotatedMember)paramVarArgs[paramInt].value).getAllAnnotations();
    paramInt += 1;
    for (;;)
    {
      AnnotationMap localAnnotationMap1 = localAnnotationMap2;
      if (paramInt < paramVarArgs.length)
      {
        if (paramVarArgs[paramInt] != null) {
          localAnnotationMap1 = AnnotationMap.merge(localAnnotationMap2, _mergeAnnotations(paramInt, paramVarArgs));
        }
      }
      else {
        return localAnnotationMap1;
      }
      paramInt += 1;
    }
  }
  
  private PropertyName _propName(String paramString)
  {
    return PropertyName.construct(paramString, null);
  }
  
  private <T> Linked<T> _removeIgnored(Linked<T> paramLinked)
  {
    if (paramLinked == null) {
      return paramLinked;
    }
    return paramLinked.withoutIgnored();
  }
  
  private <T> Linked<T> _removeNonVisible(Linked<T> paramLinked)
  {
    if (paramLinked == null) {
      return paramLinked;
    }
    return paramLinked.withoutNonVisible();
  }
  
  private <T> Linked<T> _trimByVisibility(Linked<T> paramLinked)
  {
    if (paramLinked == null) {
      return paramLinked;
    }
    return paramLinked.trimByVisibility();
  }
  
  private static <T> Linked<T> merge(Linked<T> paramLinked1, Linked<T> paramLinked2)
  {
    if (paramLinked1 == null) {
      return paramLinked2;
    }
    if (paramLinked2 == null) {
      return paramLinked1;
    }
    return paramLinked1.append(paramLinked2);
  }
  
  protected String _findDescription()
  {
    (String)fromMemberAnnotations(new WithMember()
    {
      public String withMember(AnnotatedMember paramAnonymousAnnotatedMember)
      {
        return POJOPropertyBuilder.this._annotationIntrospector.findPropertyDescription(paramAnonymousAnnotatedMember);
      }
    });
  }
  
  protected Integer _findIndex()
  {
    (Integer)fromMemberAnnotations(new WithMember()
    {
      public Integer withMember(AnnotatedMember paramAnonymousAnnotatedMember)
      {
        return POJOPropertyBuilder.this._annotationIntrospector.findPropertyIndex(paramAnonymousAnnotatedMember);
      }
    });
  }
  
  protected Boolean _findRequired()
  {
    (Boolean)fromMemberAnnotations(new WithMember()
    {
      public Boolean withMember(AnnotatedMember paramAnonymousAnnotatedMember)
      {
        return POJOPropertyBuilder.this._annotationIntrospector.hasRequiredMarker(paramAnonymousAnnotatedMember);
      }
    });
  }
  
  protected int _getterPriority(AnnotatedMethod paramAnnotatedMethod)
  {
    int i = 2;
    paramAnnotatedMethod = paramAnnotatedMethod.getName();
    if ((paramAnnotatedMethod.startsWith("get")) && (paramAnnotatedMethod.length() > 3)) {
      i = 1;
    }
    while ((paramAnnotatedMethod.startsWith("is")) && (paramAnnotatedMethod.length() > 2)) {
      return i;
    }
    return 3;
  }
  
  protected int _setterPriority(AnnotatedMethod paramAnnotatedMethod)
  {
    paramAnnotatedMethod = paramAnnotatedMethod.getName();
    if ((paramAnnotatedMethod.startsWith("set")) && (paramAnnotatedMethod.length() > 3)) {
      return 1;
    }
    return 2;
  }
  
  public void addAll(POJOPropertyBuilder paramPOJOPropertyBuilder)
  {
    this._fields = merge(this._fields, paramPOJOPropertyBuilder._fields);
    this._ctorParameters = merge(this._ctorParameters, paramPOJOPropertyBuilder._ctorParameters);
    this._getters = merge(this._getters, paramPOJOPropertyBuilder._getters);
    this._setters = merge(this._setters, paramPOJOPropertyBuilder._setters);
  }
  
  public void addCtor(AnnotatedParameter paramAnnotatedParameter, PropertyName paramPropertyName, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this._ctorParameters = new Linked(paramAnnotatedParameter, this._ctorParameters, paramPropertyName, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  @Deprecated
  public void addCtor(AnnotatedParameter paramAnnotatedParameter, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    PropertyName localPropertyName = _propName(paramString);
    if (paramString != null) {}
    for (boolean bool = true;; bool = false)
    {
      addCtor(paramAnnotatedParameter, localPropertyName, bool, paramBoolean1, paramBoolean2);
      return;
    }
  }
  
  @Deprecated
  public void addCtor(AnnotatedParameter paramAnnotatedParameter, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    addCtor(paramAnnotatedParameter, _propName(paramString), paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public void addField(AnnotatedField paramAnnotatedField, PropertyName paramPropertyName, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this._fields = new Linked(paramAnnotatedField, this._fields, paramPropertyName, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  @Deprecated
  public void addField(AnnotatedField paramAnnotatedField, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    PropertyName localPropertyName = _propName(paramString);
    if (paramString != null) {}
    for (boolean bool = true;; bool = false)
    {
      addField(paramAnnotatedField, localPropertyName, bool, paramBoolean1, paramBoolean2);
      return;
    }
  }
  
  @Deprecated
  public void addField(AnnotatedField paramAnnotatedField, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    addField(paramAnnotatedField, _propName(paramString), paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public void addGetter(AnnotatedMethod paramAnnotatedMethod, PropertyName paramPropertyName, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this._getters = new Linked(paramAnnotatedMethod, this._getters, paramPropertyName, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  @Deprecated
  public void addGetter(AnnotatedMethod paramAnnotatedMethod, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    PropertyName localPropertyName = _propName(paramString);
    if (paramString != null) {}
    for (boolean bool = true;; bool = false)
    {
      addGetter(paramAnnotatedMethod, localPropertyName, bool, paramBoolean1, paramBoolean2);
      return;
    }
  }
  
  @Deprecated
  public void addGetter(AnnotatedMethod paramAnnotatedMethod, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    addGetter(paramAnnotatedMethod, _propName(paramString), paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public void addSetter(AnnotatedMethod paramAnnotatedMethod, PropertyName paramPropertyName, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this._setters = new Linked(paramAnnotatedMethod, this._setters, paramPropertyName, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  @Deprecated
  public void addSetter(AnnotatedMethod paramAnnotatedMethod, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    PropertyName localPropertyName = _propName(paramString);
    if (paramString != null) {}
    for (boolean bool = true;; bool = false)
    {
      addSetter(paramAnnotatedMethod, localPropertyName, bool, paramBoolean1, paramBoolean2);
      return;
    }
  }
  
  @Deprecated
  public void addSetter(AnnotatedMethod paramAnnotatedMethod, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    addSetter(paramAnnotatedMethod, _propName(paramString), paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public boolean anyIgnorals()
  {
    return (_anyIgnorals(this._fields)) || (_anyIgnorals(this._getters)) || (_anyIgnorals(this._setters)) || (_anyIgnorals(this._ctorParameters));
  }
  
  public boolean anyVisible()
  {
    return (_anyVisible(this._fields)) || (_anyVisible(this._getters)) || (_anyVisible(this._setters)) || (_anyVisible(this._ctorParameters));
  }
  
  public int compareTo(POJOPropertyBuilder paramPOJOPropertyBuilder)
  {
    if (this._ctorParameters != null)
    {
      if (paramPOJOPropertyBuilder._ctorParameters == null) {
        return -1;
      }
    }
    else if (paramPOJOPropertyBuilder._ctorParameters != null) {
      return 1;
    }
    return getName().compareTo(paramPOJOPropertyBuilder.getName());
  }
  
  public boolean couldDeserialize()
  {
    return (this._ctorParameters != null) || (this._setters != null) || (this._fields != null);
  }
  
  public boolean couldSerialize()
  {
    return (this._getters != null) || (this._fields != null);
  }
  
  public Collection<POJOPropertyBuilder> explode(Collection<PropertyName> paramCollection)
  {
    HashMap localHashMap = new HashMap();
    _explode(paramCollection, localHashMap, this._fields);
    _explode(paramCollection, localHashMap, this._getters);
    _explode(paramCollection, localHashMap, this._setters);
    _explode(paramCollection, localHashMap, this._ctorParameters);
    return localHashMap.values();
  }
  
  public Set<PropertyName> findExplicitNames()
  {
    Object localObject = _findExplicitNames(this._fields, null);
    localObject = _findExplicitNames(this._getters, (Set)localObject);
    localObject = _findExplicitNames(this._setters, (Set)localObject);
    Set localSet = _findExplicitNames(this._ctorParameters, (Set)localObject);
    localObject = localSet;
    if (localSet == null) {
      localObject = Collections.emptySet();
    }
    return (Set<PropertyName>)localObject;
  }
  
  @Deprecated
  public String findNewName()
  {
    Object localObject = findExplicitNames();
    if (localObject == null) {}
    do
    {
      return null;
      if (((Collection)localObject).size() > 1) {
        throw new IllegalStateException("Conflicting/ambiguous property name definitions (implicit name '" + this._name + "'): found more than one explicit name: " + localObject);
      }
      localObject = (PropertyName)((Collection)localObject).iterator().next();
    } while (((PropertyName)localObject).equals(this._name));
    return ((PropertyName)localObject).getSimpleName();
  }
  
  public ObjectIdInfo findObjectIdInfo()
  {
    (ObjectIdInfo)fromMemberAnnotations(new WithMember()
    {
      public ObjectIdInfo withMember(AnnotatedMember paramAnonymousAnnotatedMember)
      {
        ObjectIdInfo localObjectIdInfo2 = POJOPropertyBuilder.this._annotationIntrospector.findObjectIdInfo(paramAnonymousAnnotatedMember);
        ObjectIdInfo localObjectIdInfo1 = localObjectIdInfo2;
        if (localObjectIdInfo2 != null) {
          localObjectIdInfo1 = POJOPropertyBuilder.this._annotationIntrospector.findObjectReferenceInfo(paramAnonymousAnnotatedMember, localObjectIdInfo2);
        }
        return localObjectIdInfo1;
      }
    });
  }
  
  public AnnotationIntrospector.ReferenceProperty findReferenceType()
  {
    (AnnotationIntrospector.ReferenceProperty)fromMemberAnnotations(new WithMember()
    {
      public AnnotationIntrospector.ReferenceProperty withMember(AnnotatedMember paramAnonymousAnnotatedMember)
      {
        return POJOPropertyBuilder.this._annotationIntrospector.findReferenceType(paramAnonymousAnnotatedMember);
      }
    });
  }
  
  public Class<?>[] findViews()
  {
    (Class[])fromMemberAnnotations(new WithMember()
    {
      public Class<?>[] withMember(AnnotatedMember paramAnonymousAnnotatedMember)
      {
        return POJOPropertyBuilder.this._annotationIntrospector.findViews(paramAnonymousAnnotatedMember);
      }
    });
  }
  
  protected <T> T fromMemberAnnotations(WithMember<T> paramWithMember)
  {
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject1 = null;
    if (this._annotationIntrospector != null)
    {
      if (!this._forSerialization) {
        break label79;
      }
      if (this._getters != null) {
        localObject1 = paramWithMember.withMember((AnnotatedMember)this._getters.value);
      }
    }
    for (;;)
    {
      localObject2 = localObject1;
      if (localObject1 == null)
      {
        localObject2 = localObject1;
        if (this._fields != null) {
          localObject2 = paramWithMember.withMember((AnnotatedMember)this._fields.value);
        }
      }
      return (T)localObject2;
      label79:
      localObject2 = localObject3;
      if (this._ctorParameters != null) {
        localObject2 = paramWithMember.withMember((AnnotatedMember)this._ctorParameters.value);
      }
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = localObject2;
        if (this._setters != null) {
          localObject1 = paramWithMember.withMember((AnnotatedMember)this._setters.value);
        }
      }
    }
  }
  
  public AnnotatedMember getAccessor()
  {
    AnnotatedMethod localAnnotatedMethod = getGetter();
    Object localObject = localAnnotatedMethod;
    if (localAnnotatedMethod == null) {
      localObject = getField();
    }
    return (AnnotatedMember)localObject;
  }
  
  public AnnotatedParameter getConstructorParameter()
  {
    if (this._ctorParameters == null) {
      return null;
    }
    Object localObject = this._ctorParameters;
    Linked localLinked;
    do
    {
      if ((((AnnotatedParameter)((Linked)localObject).value).getOwner() instanceof AnnotatedConstructor)) {
        return (AnnotatedParameter)((Linked)localObject).value;
      }
      localLinked = ((Linked)localObject).next;
      localObject = localLinked;
    } while (localLinked != null);
    return (AnnotatedParameter)this._ctorParameters.value;
  }
  
  public AnnotatedField getField()
  {
    Object localObject2;
    if (this._fields == null) {
      localObject2 = null;
    }
    Object localObject1;
    Linked localLinked;
    do
    {
      return (AnnotatedField)localObject2;
      localObject1 = (AnnotatedField)this._fields.value;
      localLinked = this._fields.next;
      localObject2 = localObject1;
    } while (localLinked == null);
    AnnotatedField localAnnotatedField = (AnnotatedField)localLinked.value;
    Class localClass1 = ((AnnotatedField)localObject1).getDeclaringClass();
    Class localClass2 = localAnnotatedField.getDeclaringClass();
    if (localClass1 != localClass2)
    {
      if (localClass1.isAssignableFrom(localClass2)) {
        localObject2 = localAnnotatedField;
      }
      do
      {
        localLinked = localLinked.next;
        localObject1 = localObject2;
        break;
        localObject2 = localObject1;
      } while (localClass2.isAssignableFrom(localClass1));
    }
    throw new IllegalArgumentException("Multiple fields representing property \"" + getName() + "\": " + ((AnnotatedField)localObject1).getFullName() + " vs " + localAnnotatedField.getFullName());
  }
  
  public PropertyName getFullName()
  {
    return this._name;
  }
  
  public AnnotatedMethod getGetter()
  {
    Object localObject2 = this._getters;
    if (localObject2 == null) {
      return null;
    }
    Object localObject4 = ((Linked)localObject2).next;
    Object localObject3 = localObject2;
    Object localObject1 = localObject4;
    if (localObject4 == null) {
      return (AnnotatedMethod)((Linked)localObject2).value;
    }
    if (localObject1 != null)
    {
      localObject4 = ((AnnotatedMethod)((Linked)localObject3).value).getDeclaringClass();
      Class localClass = ((AnnotatedMethod)((Linked)localObject1).value).getDeclaringClass();
      if (localObject4 != localClass) {
        if (((Class)localObject4).isAssignableFrom(localClass)) {
          localObject2 = localObject1;
        }
      }
      for (;;)
      {
        localObject1 = ((Linked)localObject1).next;
        localObject3 = localObject2;
        break;
        localObject2 = localObject3;
        if (!localClass.isAssignableFrom((Class)localObject4))
        {
          int i = _getterPriority((AnnotatedMethod)((Linked)localObject1).value);
          int j = _getterPriority((AnnotatedMethod)((Linked)localObject3).value);
          if (i == j) {
            break label161;
          }
          localObject2 = localObject3;
          if (i < j) {
            localObject2 = localObject1;
          }
        }
      }
      label161:
      throw new IllegalArgumentException("Conflicting getter definitions for property \"" + getName() + "\": " + ((AnnotatedMethod)((Linked)localObject3).value).getFullName() + " vs " + ((AnnotatedMethod)((Linked)localObject1).value).getFullName());
    }
    this._getters = ((Linked)localObject3).withoutNext();
    return (AnnotatedMethod)((Linked)localObject3).value;
  }
  
  public String getInternalName()
  {
    return this._internalName.getSimpleName();
  }
  
  public PropertyMetadata getMetadata()
  {
    Boolean localBoolean = _findRequired();
    String str = _findDescription();
    Integer localInteger = _findIndex();
    if ((localBoolean == null) && (localInteger == null))
    {
      if (str == null) {
        return PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
      }
      return PropertyMetadata.STD_REQUIRED_OR_OPTIONAL.withDescription(str);
    }
    return PropertyMetadata.construct(localBoolean.booleanValue(), str, localInteger);
  }
  
  public AnnotatedMember getMutator()
  {
    Object localObject2 = getConstructorParameter();
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject2 = getSetter();
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = getField();
      }
    }
    return (AnnotatedMember)localObject1;
  }
  
  public String getName()
  {
    if (this._name == null) {
      return null;
    }
    return this._name.getSimpleName();
  }
  
  public AnnotatedMember getNonConstructorMutator()
  {
    AnnotatedMethod localAnnotatedMethod = getSetter();
    Object localObject = localAnnotatedMethod;
    if (localAnnotatedMethod == null) {
      localObject = getField();
    }
    return (AnnotatedMember)localObject;
  }
  
  public AnnotatedMember getPrimaryMember()
  {
    if (this._forSerialization) {
      return getAccessor();
    }
    return getMutator();
  }
  
  public AnnotatedMethod getSetter()
  {
    Object localObject2 = this._setters;
    if (localObject2 == null) {
      return null;
    }
    Object localObject4 = ((Linked)localObject2).next;
    Object localObject3 = localObject2;
    Object localObject1 = localObject4;
    if (localObject4 == null) {
      return (AnnotatedMethod)((Linked)localObject2).value;
    }
    if (localObject1 != null)
    {
      localObject4 = ((AnnotatedMethod)((Linked)localObject3).value).getDeclaringClass();
      Class localClass = ((AnnotatedMethod)((Linked)localObject1).value).getDeclaringClass();
      if (localObject4 != localClass) {
        if (((Class)localObject4).isAssignableFrom(localClass)) {
          localObject2 = localObject1;
        }
      }
      for (;;)
      {
        localObject1 = ((Linked)localObject1).next;
        localObject3 = localObject2;
        break;
        localObject2 = localObject3;
        if (!localClass.isAssignableFrom((Class)localObject4))
        {
          int i = _setterPriority((AnnotatedMethod)((Linked)localObject1).value);
          int j = _setterPriority((AnnotatedMethod)((Linked)localObject3).value);
          if (i == j) {
            break label161;
          }
          localObject2 = localObject3;
          if (i < j) {
            localObject2 = localObject1;
          }
        }
      }
      label161:
      throw new IllegalArgumentException("Conflicting setter definitions for property \"" + getName() + "\": " + ((AnnotatedMethod)((Linked)localObject3).value).getFullName() + " vs " + ((AnnotatedMethod)((Linked)localObject1).value).getFullName());
    }
    this._setters = ((Linked)localObject3).withoutNext();
    return (AnnotatedMethod)((Linked)localObject3).value;
  }
  
  public PropertyName getWrapperName()
  {
    AnnotatedMember localAnnotatedMember = getPrimaryMember();
    if ((localAnnotatedMember == null) || (this._annotationIntrospector == null)) {
      return null;
    }
    return this._annotationIntrospector.findWrapperName(localAnnotatedMember);
  }
  
  public boolean hasConstructorParameter()
  {
    return this._ctorParameters != null;
  }
  
  public boolean hasField()
  {
    return this._fields != null;
  }
  
  public boolean hasGetter()
  {
    return this._getters != null;
  }
  
  public boolean hasSetter()
  {
    return this._setters != null;
  }
  
  public boolean isExplicitlyIncluded()
  {
    return (_anyExplicits(this._fields)) || (_anyExplicits(this._getters)) || (_anyExplicits(this._setters)) || (_anyExplicits(this._ctorParameters));
  }
  
  public boolean isExplicitlyNamed()
  {
    return (_anyExplicitNames(this._fields)) || (_anyExplicitNames(this._getters)) || (_anyExplicitNames(this._setters)) || (_anyExplicitNames(this._ctorParameters));
  }
  
  public boolean isTypeId()
  {
    Boolean localBoolean = (Boolean)fromMemberAnnotations(new WithMember()
    {
      public Boolean withMember(AnnotatedMember paramAnonymousAnnotatedMember)
      {
        return POJOPropertyBuilder.this._annotationIntrospector.isTypeId(paramAnonymousAnnotatedMember);
      }
    });
    return (localBoolean != null) && (localBoolean.booleanValue());
  }
  
  public void mergeAnnotations(boolean paramBoolean)
  {
    if (paramBoolean) {
      if (this._getters != null)
      {
        localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._getters, this._fields, this._ctorParameters, this._setters });
        this._getters = this._getters.withValue(((AnnotatedMethod)this._getters.value).withAnnotations(localAnnotationMap));
      }
    }
    do
    {
      do
      {
        return;
      } while (this._fields == null);
      localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._fields, this._ctorParameters, this._setters });
      this._fields = this._fields.withValue(((AnnotatedField)this._fields.value).withAnnotations(localAnnotationMap));
      return;
      if (this._ctorParameters != null)
      {
        localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._ctorParameters, this._setters, this._fields, this._getters });
        this._ctorParameters = this._ctorParameters.withValue(((AnnotatedParameter)this._ctorParameters.value).withAnnotations(localAnnotationMap));
        return;
      }
      if (this._setters != null)
      {
        localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._setters, this._fields, this._getters });
        this._setters = this._setters.withValue(((AnnotatedMethod)this._setters.value).withAnnotations(localAnnotationMap));
        return;
      }
    } while (this._fields == null);
    AnnotationMap localAnnotationMap = _mergeAnnotations(0, new Linked[] { this._fields, this._getters });
    this._fields = this._fields.withValue(((AnnotatedField)this._fields.value).withAnnotations(localAnnotationMap));
  }
  
  public void removeIgnored()
  {
    this._fields = _removeIgnored(this._fields);
    this._getters = _removeIgnored(this._getters);
    this._setters = _removeIgnored(this._setters);
    this._ctorParameters = _removeIgnored(this._ctorParameters);
  }
  
  public void removeNonVisible(boolean paramBoolean)
  {
    this._getters = _removeNonVisible(this._getters);
    this._ctorParameters = _removeNonVisible(this._ctorParameters);
    if ((paramBoolean) || (this._getters == null))
    {
      this._fields = _removeNonVisible(this._fields);
      this._setters = _removeNonVisible(this._setters);
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[Property '").append(this._name).append("'; ctors: ").append(this._ctorParameters).append(", field(s): ").append(this._fields).append(", getter(s): ").append(this._getters).append(", setter(s): ").append(this._setters);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public void trimByVisibility()
  {
    this._fields = _trimByVisibility(this._fields);
    this._getters = _trimByVisibility(this._getters);
    this._setters = _trimByVisibility(this._setters);
    this._ctorParameters = _trimByVisibility(this._ctorParameters);
  }
  
  public POJOPropertyBuilder withName(PropertyName paramPropertyName)
  {
    return new POJOPropertyBuilder(this, paramPropertyName);
  }
  
  @Deprecated
  public POJOPropertyBuilder withName(String paramString)
  {
    return withSimpleName(paramString);
  }
  
  public POJOPropertyBuilder withSimpleName(String paramString)
  {
    paramString = this._name.withSimpleName(paramString);
    if (paramString == this._name) {
      return this;
    }
    return new POJOPropertyBuilder(this, paramString);
  }
  
  private static final class Linked<T>
  {
    public final boolean isMarkedIgnored;
    public final boolean isNameExplicit;
    public final boolean isVisible;
    public final PropertyName name;
    public final Linked<T> next;
    public final T value;
    
    public Linked(T paramT, Linked<T> paramLinked, PropertyName paramPropertyName, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      this.value = paramT;
      this.next = paramLinked;
      if ((paramPropertyName == null) || (paramPropertyName.isEmpty())) {}
      for (paramT = null;; paramT = paramPropertyName)
      {
        this.name = paramT;
        bool = paramBoolean1;
        if (!paramBoolean1) {
          break label77;
        }
        if (this.name != null) {
          break;
        }
        throw new IllegalArgumentException("Can not pass true for 'explName' if name is null/empty");
      }
      boolean bool = paramBoolean1;
      if (!paramPropertyName.hasSimpleName()) {
        bool = false;
      }
      label77:
      this.isNameExplicit = bool;
      this.isVisible = paramBoolean2;
      this.isMarkedIgnored = paramBoolean3;
    }
    
    private Linked<T> append(Linked<T> paramLinked)
    {
      if (this.next == null) {
        return withNext(paramLinked);
      }
      return withNext(this.next.append(paramLinked));
    }
    
    public String toString()
    {
      String str2 = this.value.toString() + "[visible=" + this.isVisible + ",ignore=" + this.isMarkedIgnored + ",explicitName=" + this.isNameExplicit + "]";
      String str1 = str2;
      if (this.next != null) {
        str1 = str2 + ", " + this.next.toString();
      }
      return str1;
    }
    
    public Linked<T> trimByVisibility()
    {
      Object localObject;
      if (this.next == null) {
        localObject = this;
      }
      do
      {
        Linked localLinked;
        do
        {
          return (Linked<T>)localObject;
          localLinked = this.next.trimByVisibility();
          if (this.name != null)
          {
            if (localLinked.name == null) {
              return withNext(null);
            }
            return withNext(localLinked);
          }
          localObject = localLinked;
        } while (localLinked.name != null);
        if (this.isVisible == localLinked.isVisible) {
          return withNext(localLinked);
        }
        localObject = localLinked;
      } while (!this.isVisible);
      return withNext(null);
    }
    
    public Linked<T> withNext(Linked<T> paramLinked)
    {
      if (paramLinked == this.next) {
        return this;
      }
      return new Linked(this.value, paramLinked, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
    }
    
    public Linked<T> withValue(T paramT)
    {
      if (paramT == this.value) {
        return this;
      }
      return new Linked(paramT, this.next, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
    }
    
    public Linked<T> withoutIgnored()
    {
      if (this.isMarkedIgnored)
      {
        if (this.next == null) {
          return null;
        }
        return this.next.withoutIgnored();
      }
      if (this.next != null)
      {
        Linked localLinked = this.next.withoutIgnored();
        if (localLinked != this.next) {
          return withNext(localLinked);
        }
      }
      return this;
    }
    
    public Linked<T> withoutNext()
    {
      if (this.next == null) {
        return this;
      }
      return new Linked(this.value, null, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
    }
    
    public Linked<T> withoutNonVisible()
    {
      if (this.next == null) {}
      for (Linked localLinked1 = null;; localLinked1 = this.next.withoutNonVisible())
      {
        Linked localLinked2 = localLinked1;
        if (this.isVisible) {
          localLinked2 = withNext(localLinked1);
        }
        return localLinked2;
      }
    }
  }
  
  private static abstract interface WithMember<T>
  {
    public abstract T withMember(AnnotatedMember paramAnnotatedMember);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/introspect/POJOPropertyBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */