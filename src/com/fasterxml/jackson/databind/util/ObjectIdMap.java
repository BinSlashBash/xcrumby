package com.fasterxml.jackson.databind.util;

import java.util.IdentityHashMap;

public class ObjectIdMap
  extends IdentityHashMap<Object, Object>
{
  public ObjectIdMap()
  {
    super(16);
  }
  
  public Object findId(Object paramObject)
  {
    return get(paramObject);
  }
  
  public void insertId(Object paramObject1, Object paramObject2)
  {
    put(paramObject1, paramObject2);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/ObjectIdMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */