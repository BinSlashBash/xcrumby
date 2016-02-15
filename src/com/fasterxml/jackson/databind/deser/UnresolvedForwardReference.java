package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class UnresolvedForwardReference
  extends JsonMappingException
{
  private static final long serialVersionUID = 1L;
  private ReadableObjectId _roid;
  private List<UnresolvedId> _unresolvedIds;
  
  public UnresolvedForwardReference(String paramString)
  {
    super(paramString);
    this._unresolvedIds = new ArrayList();
  }
  
  public UnresolvedForwardReference(String paramString, JsonLocation paramJsonLocation, ReadableObjectId paramReadableObjectId)
  {
    super(paramString, paramJsonLocation);
    this._roid = paramReadableObjectId;
  }
  
  public void addUnresolvedId(Object paramObject, Class<?> paramClass, JsonLocation paramJsonLocation)
  {
    this._unresolvedIds.add(new UnresolvedId(paramObject, paramClass, paramJsonLocation));
  }
  
  public String getMessage()
  {
    Object localObject = super.getMessage();
    if (this._unresolvedIds == null) {
      return (String)localObject;
    }
    localObject = new StringBuilder((String)localObject);
    Iterator localIterator = this._unresolvedIds.iterator();
    while (localIterator.hasNext())
    {
      ((StringBuilder)localObject).append(((UnresolvedId)localIterator.next()).toString());
      if (localIterator.hasNext()) {
        ((StringBuilder)localObject).append(", ");
      }
    }
    ((StringBuilder)localObject).append('.');
    return ((StringBuilder)localObject).toString();
  }
  
  public ReadableObjectId getRoid()
  {
    return this._roid;
  }
  
  public Object getUnresolvedId()
  {
    return this._roid.getKey().key;
  }
  
  public List<UnresolvedId> getUnresolvedIds()
  {
    return this._unresolvedIds;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/UnresolvedForwardReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */