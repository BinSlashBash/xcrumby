package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;

public class JsonLocationInstantiator
  extends ValueInstantiator
{
  private static final int _int(Object paramObject)
  {
    if (paramObject == null) {
      return 0;
    }
    return ((Number)paramObject).intValue();
  }
  
  private static final long _long(Object paramObject)
  {
    if (paramObject == null) {
      return 0L;
    }
    return ((Number)paramObject).longValue();
  }
  
  private static CreatorProperty creatorProp(String paramString, JavaType paramJavaType, int paramInt)
  {
    return new CreatorProperty(new PropertyName(paramString), paramJavaType, null, null, null, null, paramInt, null, PropertyMetadata.STD_REQUIRED);
  }
  
  public boolean canCreateFromObjectWith()
  {
    return true;
  }
  
  public Object createFromObjectWith(DeserializationContext paramDeserializationContext, Object[] paramArrayOfObject)
  {
    return new JsonLocation(paramArrayOfObject[0], _long(paramArrayOfObject[1]), _long(paramArrayOfObject[2]), _int(paramArrayOfObject[3]), _int(paramArrayOfObject[4]));
  }
  
  public CreatorProperty[] getFromObjectArguments(DeserializationConfig paramDeserializationConfig)
  {
    JavaType localJavaType1 = paramDeserializationConfig.constructType(Integer.TYPE);
    JavaType localJavaType2 = paramDeserializationConfig.constructType(Long.TYPE);
    return new CreatorProperty[] { creatorProp("sourceRef", paramDeserializationConfig.constructType(Object.class), 0), creatorProp("byteOffset", localJavaType2, 1), creatorProp("charOffset", localJavaType2, 2), creatorProp("lineNr", localJavaType1, 3), creatorProp("columnNr", localJavaType1, 4) };
  }
  
  public String getValueTypeDesc()
  {
    return JsonLocation.class.getName();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/JsonLocationInstantiator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */