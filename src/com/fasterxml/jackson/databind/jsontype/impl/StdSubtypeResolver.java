package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class StdSubtypeResolver
  extends SubtypeResolver
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected LinkedHashSet<NamedType> _registeredSubtypes;
  
  protected void _collectAndResolve(AnnotatedClass paramAnnotatedClass, NamedType paramNamedType, MapperConfig<?> paramMapperConfig, AnnotationIntrospector paramAnnotationIntrospector, HashMap<NamedType, NamedType> paramHashMap)
  {
    Object localObject1 = paramNamedType;
    Object localObject2;
    if (!paramNamedType.hasName())
    {
      localObject2 = paramAnnotationIntrospector.findTypeName(paramAnnotatedClass);
      localObject1 = paramNamedType;
      if (localObject2 != null) {
        localObject1 = new NamedType(paramNamedType.getType(), (String)localObject2);
      }
    }
    if (paramHashMap.containsKey(localObject1)) {
      if ((((NamedType)localObject1).hasName()) && (!((NamedType)paramHashMap.get(localObject1)).hasName())) {
        paramHashMap.put(localObject1, localObject1);
      }
    }
    for (;;)
    {
      return;
      paramHashMap.put(localObject1, localObject1);
      paramAnnotatedClass = paramAnnotationIntrospector.findSubtypes(paramAnnotatedClass);
      if ((paramAnnotatedClass != null) && (!paramAnnotatedClass.isEmpty()))
      {
        localObject1 = paramAnnotatedClass.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          paramNamedType = (NamedType)((Iterator)localObject1).next();
          localObject2 = AnnotatedClass.constructWithoutSuperTypes(paramNamedType.getType(), paramAnnotationIntrospector, paramMapperConfig);
          paramAnnotatedClass = paramNamedType;
          if (!paramNamedType.hasName()) {
            paramAnnotatedClass = new NamedType(paramNamedType.getType(), paramAnnotationIntrospector.findTypeName((AnnotatedClass)localObject2));
          }
          _collectAndResolve((AnnotatedClass)localObject2, paramAnnotatedClass, paramMapperConfig, paramAnnotationIntrospector, paramHashMap);
        }
      }
    }
  }
  
  public Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass paramAnnotatedClass, MapperConfig<?> paramMapperConfig, AnnotationIntrospector paramAnnotationIntrospector)
  {
    HashMap localHashMap = new HashMap();
    if (this._registeredSubtypes != null)
    {
      Class localClass = paramAnnotatedClass.getRawType();
      Iterator localIterator = this._registeredSubtypes.iterator();
      while (localIterator.hasNext())
      {
        NamedType localNamedType = (NamedType)localIterator.next();
        if (localClass.isAssignableFrom(localNamedType.getType())) {
          _collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(localNamedType.getType(), paramAnnotationIntrospector, paramMapperConfig), localNamedType, paramMapperConfig, paramAnnotationIntrospector, localHashMap);
        }
      }
    }
    _collectAndResolve(paramAnnotatedClass, new NamedType(paramAnnotatedClass.getRawType(), null), paramMapperConfig, paramAnnotationIntrospector, localHashMap);
    return new ArrayList(localHashMap.values());
  }
  
  public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember paramAnnotatedMember, MapperConfig<?> paramMapperConfig, AnnotationIntrospector paramAnnotationIntrospector, JavaType paramJavaType)
  {
    if (paramJavaType == null) {}
    HashMap localHashMap;
    Object localObject;
    for (paramJavaType = paramAnnotatedMember.getRawType();; paramJavaType = paramJavaType.getRawClass())
    {
      localHashMap = new HashMap();
      if (this._registeredSubtypes == null) {
        break;
      }
      localObject = this._registeredSubtypes.iterator();
      while (((Iterator)localObject).hasNext())
      {
        NamedType localNamedType = (NamedType)((Iterator)localObject).next();
        if (paramJavaType.isAssignableFrom(localNamedType.getType())) {
          _collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(localNamedType.getType(), paramAnnotationIntrospector, paramMapperConfig), localNamedType, paramMapperConfig, paramAnnotationIntrospector, localHashMap);
        }
      }
    }
    paramAnnotatedMember = paramAnnotationIntrospector.findSubtypes(paramAnnotatedMember);
    if (paramAnnotatedMember != null)
    {
      paramAnnotatedMember = paramAnnotatedMember.iterator();
      while (paramAnnotatedMember.hasNext())
      {
        localObject = (NamedType)paramAnnotatedMember.next();
        _collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(((NamedType)localObject).getType(), paramAnnotationIntrospector, paramMapperConfig), (NamedType)localObject, paramMapperConfig, paramAnnotationIntrospector, localHashMap);
      }
    }
    paramAnnotatedMember = new NamedType(paramJavaType, null);
    _collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(paramJavaType, paramAnnotationIntrospector, paramMapperConfig), paramAnnotatedMember, paramMapperConfig, paramAnnotationIntrospector, localHashMap);
    return new ArrayList(localHashMap.values());
  }
  
  public void registerSubtypes(NamedType... paramVarArgs)
  {
    if (this._registeredSubtypes == null) {
      this._registeredSubtypes = new LinkedHashSet();
    }
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      NamedType localNamedType = paramVarArgs[i];
      this._registeredSubtypes.add(localNamedType);
      i += 1;
    }
  }
  
  public void registerSubtypes(Class<?>... paramVarArgs)
  {
    NamedType[] arrayOfNamedType = new NamedType[paramVarArgs.length];
    int i = 0;
    int j = paramVarArgs.length;
    while (i < j)
    {
      arrayOfNamedType[i] = new NamedType(paramVarArgs[i]);
      i += 1;
    }
    registerSubtypes(arrayOfNamedType);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsontype/impl/StdSubtypeResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */