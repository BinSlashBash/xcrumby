package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class EnumValues
{
  private final Class<Enum<?>> _enumClass;
  private final EnumMap<?, SerializableString> _values;
  
  private EnumValues(Class<Enum<?>> paramClass, Map<Enum<?>, SerializableString> paramMap)
  {
    this._enumClass = paramClass;
    this._values = new EnumMap(paramMap);
  }
  
  public static EnumValues construct(SerializationConfig paramSerializationConfig, Class<Enum<?>> paramClass)
  {
    if (paramSerializationConfig.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
      return constructFromToString(paramSerializationConfig, paramClass);
    }
    return constructFromName(paramSerializationConfig, paramClass);
  }
  
  public static EnumValues constructFromName(MapperConfig<?> paramMapperConfig, Class<Enum<?>> paramClass)
  {
    Enum[] arrayOfEnum = (Enum[])ClassUtil.findEnumType(paramClass).getEnumConstants();
    if (arrayOfEnum != null)
    {
      HashMap localHashMap = new HashMap();
      int j = arrayOfEnum.length;
      int i = 0;
      while (i < j)
      {
        Enum localEnum = arrayOfEnum[i];
        localHashMap.put(localEnum, paramMapperConfig.compileString(paramMapperConfig.getAnnotationIntrospector().findEnumValue(localEnum)));
        i += 1;
      }
      return new EnumValues(paramClass, localHashMap);
    }
    throw new IllegalArgumentException("Can not determine enum constants for Class " + paramClass.getName());
  }
  
  public static EnumValues constructFromToString(MapperConfig<?> paramMapperConfig, Class<Enum<?>> paramClass)
  {
    Enum[] arrayOfEnum = (Enum[])ClassUtil.findEnumType(paramClass).getEnumConstants();
    if (arrayOfEnum != null)
    {
      HashMap localHashMap = new HashMap();
      int j = arrayOfEnum.length;
      int i = 0;
      while (i < j)
      {
        Enum localEnum = arrayOfEnum[i];
        localHashMap.put(localEnum, paramMapperConfig.compileString(localEnum.toString()));
        i += 1;
      }
      return new EnumValues(paramClass, localHashMap);
    }
    throw new IllegalArgumentException("Can not determine enum constants for Class " + paramClass.getName());
  }
  
  public Class<Enum<?>> getEnumClass()
  {
    return this._enumClass;
  }
  
  public EnumMap<?, SerializableString> internalMap()
  {
    return this._values;
  }
  
  public SerializableString serializedValueFor(Enum<?> paramEnum)
  {
    return (SerializableString)this._values.get(paramEnum);
  }
  
  public Collection<SerializableString> values()
  {
    return this._values.values();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/EnumValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */