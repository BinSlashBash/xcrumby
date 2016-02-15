package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfig<*>;
import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;

public class RootNameLookup
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected transient LRUMap<ClassKey, PropertyName> _rootNames = new LRUMap(20, 200);
  
  public PropertyName findRootName(JavaType paramJavaType, MapperConfig<?> paramMapperConfig)
  {
    return findRootName(paramJavaType.getRawClass(), paramMapperConfig);
  }
  
  public PropertyName findRootName(Class<?> paramClass, MapperConfig<?> paramMapperConfig)
  {
    ClassKey localClassKey = new ClassKey(paramClass);
    Object localObject = (PropertyName)this._rootNames.get(localClassKey);
    if (localObject != null) {
      return (PropertyName)localObject;
    }
    localObject = paramMapperConfig.introspectClassAnnotations(paramClass);
    localObject = paramMapperConfig.getAnnotationIntrospector().findRootName(((BeanDescription)localObject).getClassInfo());
    if (localObject != null)
    {
      paramMapperConfig = (MapperConfig<?>)localObject;
      if (((PropertyName)localObject).hasSimpleName()) {}
    }
    else
    {
      paramMapperConfig = new PropertyName(paramClass.getSimpleName());
    }
    this._rootNames.put(localClassKey, paramMapperConfig);
    return paramMapperConfig;
  }
  
  protected Object readResolve()
  {
    return new RootNameLookup();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/RootNameLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */