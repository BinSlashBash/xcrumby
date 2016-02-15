package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;

public abstract class FrameworkMember<T extends FrameworkMember<T>>
{
  abstract Annotation[] getAnnotations();
  
  public abstract String getName();
  
  public abstract Class<?> getType();
  
  public abstract boolean isPublic();
  
  boolean isShadowedBy(List<T> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      if (isShadowedBy((FrameworkMember)paramList.next())) {
        return true;
      }
    }
    return false;
  }
  
  abstract boolean isShadowedBy(T paramT);
  
  public abstract boolean isStatic();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/model/FrameworkMember.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */