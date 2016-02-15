package com.fasterxml.jackson.databind.util;

import java.io.Serializable;

public class ViewMatcher
  implements Serializable
{
  protected static final ViewMatcher EMPTY = new ViewMatcher();
  private static final long serialVersionUID = 1L;
  
  public static ViewMatcher construct(Class<?>[] paramArrayOfClass)
  {
    if (paramArrayOfClass == null) {
      return EMPTY;
    }
    switch (paramArrayOfClass.length)
    {
    default: 
      return new Multi(paramArrayOfClass);
    case 0: 
      return EMPTY;
    }
    return new Single(paramArrayOfClass[0]);
  }
  
  public boolean isVisibleForView(Class<?> paramClass)
  {
    return false;
  }
  
  private static final class Multi
    extends ViewMatcher
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    private final Class<?>[] _views;
    
    public Multi(Class<?>[] paramArrayOfClass)
    {
      this._views = paramArrayOfClass;
    }
    
    public boolean isVisibleForView(Class<?> paramClass)
    {
      int i = 0;
      int j = this._views.length;
      while (i < j)
      {
        Class localClass = this._views[i];
        if ((paramClass == localClass) || (localClass.isAssignableFrom(paramClass))) {
          return true;
        }
        i += 1;
      }
      return false;
    }
  }
  
  private static final class Single
    extends ViewMatcher
  {
    private static final long serialVersionUID = 1L;
    private final Class<?> _view;
    
    public Single(Class<?> paramClass)
    {
      this._view = paramClass;
    }
    
    public boolean isVisibleForView(Class<?> paramClass)
    {
      return (paramClass == this._view) || (this._view.isAssignableFrom(paramClass));
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/ViewMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */