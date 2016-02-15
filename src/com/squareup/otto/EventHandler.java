package com.squareup.otto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class EventHandler
{
  private final int hashCode;
  private final Method method;
  private final Object target;
  private boolean valid = true;
  
  EventHandler(Object paramObject, Method paramMethod)
  {
    if (paramObject == null) {
      throw new NullPointerException("EventHandler target cannot be null.");
    }
    if (paramMethod == null) {
      throw new NullPointerException("EventHandler method cannot be null.");
    }
    this.target = paramObject;
    this.method = paramMethod;
    paramMethod.setAccessible(true);
    this.hashCode = ((paramMethod.hashCode() + 31) * 31 + paramObject.hashCode());
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (EventHandler)paramObject;
    } while ((this.method.equals(((EventHandler)paramObject).method)) && (this.target == ((EventHandler)paramObject).target));
    return false;
  }
  
  public void handleEvent(Object paramObject)
    throws InvocationTargetException
  {
    if (!this.valid) {
      throw new IllegalStateException(toString() + " has been invalidated and can no longer handle events.");
    }
    try
    {
      this.method.invoke(this.target, new Object[] { paramObject });
      return;
    }
    catch (IllegalAccessException paramObject)
    {
      throw new AssertionError(paramObject);
    }
    catch (InvocationTargetException paramObject)
    {
      if ((((InvocationTargetException)paramObject).getCause() instanceof Error)) {
        throw ((Error)((InvocationTargetException)paramObject).getCause());
      }
      throw ((Throwable)paramObject);
    }
  }
  
  public int hashCode()
  {
    return this.hashCode;
  }
  
  public void invalidate()
  {
    this.valid = false;
  }
  
  public boolean isValid()
  {
    return this.valid;
  }
  
  public String toString()
  {
    return "[EventHandler " + this.method + "]";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/otto/EventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */