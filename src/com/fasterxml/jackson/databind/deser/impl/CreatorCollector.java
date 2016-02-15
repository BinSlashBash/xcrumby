package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreatorCollector
{
  protected final BeanDescription _beanDesc;
  protected AnnotatedWithParams _booleanCreator;
  protected final boolean _canFixAccess;
  protected AnnotatedWithParams _defaultConstructor;
  protected CreatorProperty[] _delegateArgs;
  protected AnnotatedWithParams _delegateCreator;
  protected AnnotatedWithParams _doubleCreator;
  protected AnnotatedParameter _incompleteParameter;
  protected AnnotatedWithParams _intCreator;
  protected AnnotatedWithParams _longCreator;
  protected CreatorProperty[] _propertyBasedArgs = null;
  protected AnnotatedWithParams _propertyBasedCreator;
  protected AnnotatedWithParams _stringCreator;
  
  public CreatorCollector(BeanDescription paramBeanDescription, boolean paramBoolean)
  {
    this._beanDesc = paramBeanDescription;
    this._canFixAccess = paramBoolean;
  }
  
  private <T extends AnnotatedMember> T _fixAccess(T paramT)
  {
    if ((paramT != null) && (this._canFixAccess)) {
      ClassUtil.checkAndFixAccess((Member)paramT.getAnnotated());
    }
    return paramT;
  }
  
  public void addBooleanCreator(AnnotatedWithParams paramAnnotatedWithParams)
  {
    this._booleanCreator = verifyNonDup(paramAnnotatedWithParams, this._booleanCreator, "boolean");
  }
  
  public void addDelegatingCreator(AnnotatedWithParams paramAnnotatedWithParams, CreatorProperty[] paramArrayOfCreatorProperty)
  {
    this._delegateCreator = verifyNonDup(paramAnnotatedWithParams, this._delegateCreator, "delegate");
    this._delegateArgs = paramArrayOfCreatorProperty;
  }
  
  public void addDoubleCreator(AnnotatedWithParams paramAnnotatedWithParams)
  {
    this._doubleCreator = verifyNonDup(paramAnnotatedWithParams, this._doubleCreator, "double");
  }
  
  public void addIncompeteParameter(AnnotatedParameter paramAnnotatedParameter)
  {
    if (this._incompleteParameter == null) {
      this._incompleteParameter = paramAnnotatedParameter;
    }
  }
  
  public void addIntCreator(AnnotatedWithParams paramAnnotatedWithParams)
  {
    this._intCreator = verifyNonDup(paramAnnotatedWithParams, this._intCreator, "int");
  }
  
  public void addLongCreator(AnnotatedWithParams paramAnnotatedWithParams)
  {
    this._longCreator = verifyNonDup(paramAnnotatedWithParams, this._longCreator, "long");
  }
  
  public void addPropertyCreator(AnnotatedWithParams paramAnnotatedWithParams, CreatorProperty[] paramArrayOfCreatorProperty)
  {
    this._propertyBasedCreator = verifyNonDup(paramAnnotatedWithParams, this._propertyBasedCreator, "property-based");
    if (paramArrayOfCreatorProperty.length > 1)
    {
      paramAnnotatedWithParams = new HashMap();
      int i = 0;
      int j = paramArrayOfCreatorProperty.length;
      if (i < j)
      {
        String str = paramArrayOfCreatorProperty[i].getName();
        if ((str.length() == 0) && (paramArrayOfCreatorProperty[i].getInjectableValueId() != null)) {}
        Integer localInteger;
        do
        {
          i += 1;
          break;
          localInteger = (Integer)paramAnnotatedWithParams.put(str, Integer.valueOf(i));
        } while (localInteger == null);
        throw new IllegalArgumentException("Duplicate creator property \"" + str + "\" (index " + localInteger + " vs " + i + ")");
      }
    }
    this._propertyBasedArgs = paramArrayOfCreatorProperty;
  }
  
  public void addStringCreator(AnnotatedWithParams paramAnnotatedWithParams)
  {
    this._stringCreator = verifyNonDup(paramAnnotatedWithParams, this._stringCreator, "String");
  }
  
  public ValueInstantiator constructValueInstantiator(DeserializationConfig paramDeserializationConfig)
  {
    int m = 0;
    if (this._delegateCreator == null) {}
    JavaType localJavaType1;
    JavaType localJavaType2;
    int i;
    Class localClass;
    for (int j = 1; j != 0; j = 0)
    {
      localJavaType1 = null;
      localJavaType2 = this._beanDesc.getType();
      i = m;
      if (this._propertyBasedCreator == null)
      {
        i = m;
        if (this._delegateCreator == null)
        {
          i = m;
          if (this._stringCreator == null)
          {
            i = m;
            if (this._longCreator == null)
            {
              i = m;
              if (this._doubleCreator == null)
              {
                i = m;
                if (this._booleanCreator == null) {
                  i = 1;
                }
              }
            }
          }
        }
      }
      if ((j & i) == 0) {
        break label253;
      }
      localClass = localJavaType2.getRawClass();
      if ((localClass != Collection.class) && (localClass != List.class) && (localClass != ArrayList.class)) {
        break label214;
      }
      return new Vanilla(1);
    }
    int n = 0;
    int k = n;
    int i1;
    if (this._delegateArgs != null)
    {
      i = 0;
      i1 = this._delegateArgs.length;
    }
    for (;;)
    {
      k = n;
      if (i < i1)
      {
        if (this._delegateArgs[i] == null) {
          k = i;
        }
      }
      else
      {
        localJavaType1 = this._beanDesc.bindingsForBeanType().resolveType(this._delegateCreator.getGenericParameterType(k));
        break;
      }
      i += 1;
    }
    label214:
    if ((localClass == Map.class) || (localClass == LinkedHashMap.class)) {
      return new Vanilla(2);
    }
    if (localClass == HashMap.class) {
      return new Vanilla(3);
    }
    label253:
    paramDeserializationConfig = new StdValueInstantiator(paramDeserializationConfig, localJavaType2);
    paramDeserializationConfig.configureFromObjectSettings(this._defaultConstructor, this._delegateCreator, localJavaType1, this._delegateArgs, this._propertyBasedCreator, this._propertyBasedArgs);
    paramDeserializationConfig.configureFromStringCreator(this._stringCreator);
    paramDeserializationConfig.configureFromIntCreator(this._intCreator);
    paramDeserializationConfig.configureFromLongCreator(this._longCreator);
    paramDeserializationConfig.configureFromDoubleCreator(this._doubleCreator);
    paramDeserializationConfig.configureFromBooleanCreator(this._booleanCreator);
    paramDeserializationConfig.configureIncompleteParameter(this._incompleteParameter);
    return paramDeserializationConfig;
  }
  
  public boolean hasDefaultCreator()
  {
    return this._defaultConstructor != null;
  }
  
  public void setDefaultCreator(AnnotatedWithParams paramAnnotatedWithParams)
  {
    this._defaultConstructor = ((AnnotatedWithParams)_fixAccess(paramAnnotatedWithParams));
  }
  
  protected AnnotatedWithParams verifyNonDup(AnnotatedWithParams paramAnnotatedWithParams1, AnnotatedWithParams paramAnnotatedWithParams2, String paramString)
  {
    if ((paramAnnotatedWithParams2 != null) && (paramAnnotatedWithParams2.getClass() == paramAnnotatedWithParams1.getClass())) {
      throw new IllegalArgumentException("Conflicting " + paramString + " creators: already had " + paramAnnotatedWithParams2 + ", encountered " + paramAnnotatedWithParams1);
    }
    return (AnnotatedWithParams)_fixAccess(paramAnnotatedWithParams1);
  }
  
  protected static final class Vanilla
    extends ValueInstantiator
    implements Serializable
  {
    public static final int TYPE_COLLECTION = 1;
    public static final int TYPE_HASH_MAP = 3;
    public static final int TYPE_MAP = 2;
    private static final long serialVersionUID = 1L;
    private final int _type;
    
    public Vanilla(int paramInt)
    {
      this._type = paramInt;
    }
    
    public boolean canCreateUsingDefault()
    {
      return true;
    }
    
    public boolean canInstantiate()
    {
      return true;
    }
    
    public Object createUsingDefault(DeserializationContext paramDeserializationContext)
      throws IOException
    {
      switch (this._type)
      {
      default: 
        throw new IllegalStateException("Unknown type " + this._type);
      case 1: 
        return new ArrayList();
      case 2: 
        return new LinkedHashMap();
      }
      return new HashMap();
    }
    
    public String getValueTypeDesc()
    {
      switch (this._type)
      {
      default: 
        return Object.class.getName();
      case 1: 
        return ArrayList.class.getName();
      case 2: 
        return LinkedHashMap.class.getName();
      }
      return HashMap.class.getName();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/impl/CreatorCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */