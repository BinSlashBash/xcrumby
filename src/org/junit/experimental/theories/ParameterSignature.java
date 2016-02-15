package org.junit.experimental.theories;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ParameterSignature
{
  private final Annotation[] annotations;
  private final Class<?> type;
  
  private ParameterSignature(Class<?> paramClass, Annotation[] paramArrayOfAnnotation)
  {
    this.type = paramClass;
    this.annotations = paramArrayOfAnnotation;
  }
  
  private <T extends Annotation> T findDeepAnnotation(Annotation[] paramArrayOfAnnotation, Class<T> paramClass, int paramInt)
  {
    if (paramInt == 0) {}
    for (;;)
    {
      return null;
      int j = paramArrayOfAnnotation.length;
      int i = 0;
      while (i < j)
      {
        Annotation localAnnotation = paramArrayOfAnnotation[i];
        if (paramClass.isInstance(localAnnotation)) {
          return (Annotation)paramClass.cast(localAnnotation);
        }
        localAnnotation = findDeepAnnotation(localAnnotation.annotationType().getAnnotations(), paramClass, paramInt - 1);
        if (localAnnotation != null) {
          return (Annotation)paramClass.cast(localAnnotation);
        }
        i += 1;
      }
    }
  }
  
  public static ArrayList<ParameterSignature> signatures(Method paramMethod)
  {
    return signatures(paramMethod.getParameterTypes(), paramMethod.getParameterAnnotations());
  }
  
  private static ArrayList<ParameterSignature> signatures(Class<?>[] paramArrayOfClass, Annotation[][] paramArrayOfAnnotation)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramArrayOfClass.length)
    {
      localArrayList.add(new ParameterSignature(paramArrayOfClass[i], paramArrayOfAnnotation[i]));
      i += 1;
    }
    return localArrayList;
  }
  
  public static List<ParameterSignature> signatures(Constructor<?> paramConstructor)
  {
    return signatures(paramConstructor.getParameterTypes(), paramConstructor.getParameterAnnotations());
  }
  
  public boolean canAcceptArrayType(Class<?> paramClass)
  {
    return (paramClass.isArray()) && (canAcceptType(paramClass.getComponentType()));
  }
  
  public boolean canAcceptType(Class<?> paramClass)
  {
    return this.type.isAssignableFrom(paramClass);
  }
  
  public <T extends Annotation> T findDeepAnnotation(Class<T> paramClass)
  {
    return findDeepAnnotation(this.annotations, paramClass, 3);
  }
  
  public <T extends Annotation> T getAnnotation(Class<T> paramClass)
  {
    Iterator localIterator = getAnnotations().iterator();
    while (localIterator.hasNext())
    {
      Annotation localAnnotation = (Annotation)localIterator.next();
      if (paramClass.isInstance(localAnnotation)) {
        return (Annotation)paramClass.cast(localAnnotation);
      }
    }
    return null;
  }
  
  public List<Annotation> getAnnotations()
  {
    return Arrays.asList(this.annotations);
  }
  
  public Class<?> getType()
  {
    return this.type;
  }
  
  public boolean hasAnnotation(Class<? extends Annotation> paramClass)
  {
    return getAnnotation(paramClass) != null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/theories/ParameterSignature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */