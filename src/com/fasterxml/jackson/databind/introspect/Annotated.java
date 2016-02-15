package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public abstract class Annotated
{
  public abstract Iterable<Annotation> annotations();
  
  protected abstract AnnotationMap getAllAnnotations();
  
  public abstract AnnotatedElement getAnnotated();
  
  public abstract <A extends Annotation> A getAnnotation(Class<A> paramClass);
  
  public abstract Type getGenericType();
  
  protected abstract int getModifiers();
  
  public abstract String getName();
  
  public abstract Class<?> getRawType();
  
  public JavaType getType(TypeBindings paramTypeBindings)
  {
    return paramTypeBindings.resolveType(getGenericType());
  }
  
  public final <A extends Annotation> boolean hasAnnotation(Class<A> paramClass)
  {
    return getAnnotation(paramClass) != null;
  }
  
  public final boolean isPublic()
  {
    return Modifier.isPublic(getModifiers());
  }
  
  public abstract Annotated withAnnotations(AnnotationMap paramAnnotationMap);
  
  public final Annotated withFallBackAnnotationsFrom(Annotated paramAnnotated)
  {
    return withAnnotations(AnnotationMap.merge(getAllAnnotations(), paramAnnotated.getAllAnnotations()));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/introspect/Annotated.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */