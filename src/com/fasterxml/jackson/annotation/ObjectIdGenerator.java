package com.fasterxml.jackson.annotation;

import java.io.Serializable;

public abstract class ObjectIdGenerator<T>
  implements Serializable
{
  public abstract boolean canUseFor(ObjectIdGenerator<?> paramObjectIdGenerator);
  
  public abstract ObjectIdGenerator<T> forScope(Class<?> paramClass);
  
  public abstract T generateId(Object paramObject);
  
  public abstract Class<?> getScope();
  
  public abstract IdKey key(Object paramObject);
  
  public abstract ObjectIdGenerator<T> newForSerialization(Object paramObject);
  
  public static final class IdKey
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    private final int hashCode;
    public final Object key;
    public final Class<?> scope;
    public final Class<?> type;
    
    public IdKey(Class<?> paramClass1, Class<?> paramClass2, Object paramObject)
    {
      this.type = paramClass1;
      this.scope = paramClass2;
      this.key = paramObject;
      int j = paramObject.hashCode() + paramClass1.getName().hashCode();
      int i = j;
      if (paramClass2 != null) {
        i = j ^ paramClass2.getName().hashCode();
      }
      this.hashCode = i;
    }
    
    public boolean equals(Object paramObject)
    {
      if (paramObject == this) {}
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (paramObject.getClass() != getClass()) {
          return false;
        }
        paramObject = (IdKey)paramObject;
      } while ((((IdKey)paramObject).key.equals(this.key)) && (((IdKey)paramObject).type == this.type) && (((IdKey)paramObject).scope == this.scope));
      return false;
    }
    
    public int hashCode()
    {
      return this.hashCode;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/annotation/ObjectIdGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */