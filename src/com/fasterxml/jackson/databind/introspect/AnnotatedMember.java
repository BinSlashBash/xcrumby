package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.Collections;

public abstract class AnnotatedMember
  extends Annotated
  implements Serializable
{
  private static final long serialVersionUID = 7364428299211355871L;
  protected final transient AnnotationMap _annotations;
  
  protected AnnotatedMember(AnnotationMap paramAnnotationMap)
  {
    this._annotations = paramAnnotationMap;
  }
  
  public final void addIfNotPresent(Annotation paramAnnotation)
  {
    this._annotations.addIfNotPresent(paramAnnotation);
  }
  
  public final void addOrOverride(Annotation paramAnnotation)
  {
    this._annotations.add(paramAnnotation);
  }
  
  public Iterable<Annotation> annotations()
  {
    if (this._annotations == null) {
      return Collections.emptyList();
    }
    return this._annotations.annotations();
  }
  
  public final void fixAccess()
  {
    ClassUtil.checkAndFixAccess(getMember());
  }
  
  protected AnnotationMap getAllAnnotations()
  {
    return this._annotations;
  }
  
  public abstract Class<?> getDeclaringClass();
  
  public abstract Member getMember();
  
  public abstract Object getValue(Object paramObject)
    throws UnsupportedOperationException, IllegalArgumentException;
  
  public abstract void setValue(Object paramObject1, Object paramObject2)
    throws UnsupportedOperationException, IllegalArgumentException;
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/introspect/AnnotatedMember.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */