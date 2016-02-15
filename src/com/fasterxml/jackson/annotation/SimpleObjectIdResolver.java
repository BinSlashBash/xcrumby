package com.fasterxml.jackson.annotation;

import java.util.HashMap;
import java.util.Map;

public class SimpleObjectIdResolver
  implements ObjectIdResolver
{
  private Map<ObjectIdGenerator.IdKey, Object> _items = new HashMap();
  
  public void bindItem(ObjectIdGenerator.IdKey paramIdKey, Object paramObject)
  {
    if (this._items.containsKey(paramIdKey)) {
      throw new IllegalStateException("Already had POJO for id (" + paramIdKey.key.getClass().getName() + ") [" + paramIdKey + "]");
    }
    this._items.put(paramIdKey, paramObject);
  }
  
  public boolean canUseFor(ObjectIdResolver paramObjectIdResolver)
  {
    return paramObjectIdResolver.getClass() == getClass();
  }
  
  public ObjectIdResolver newForDeserialization(Object paramObject)
  {
    return this;
  }
  
  public Object resolveId(ObjectIdGenerator.IdKey paramIdKey)
  {
    return this._items.get(paramIdKey);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/annotation/SimpleObjectIdResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */