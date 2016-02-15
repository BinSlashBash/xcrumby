package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.internal.MethodSorter;

public class TestClass
{
  private final Class<?> fClass;
  private Map<Class<?>, List<FrameworkField>> fFieldsForAnnotations = new HashMap();
  private Map<Class<?>, List<FrameworkMethod>> fMethodsForAnnotations = new HashMap();
  
  public TestClass(Class<?> paramClass)
  {
    this.fClass = paramClass;
    if ((paramClass != null) && (paramClass.getConstructors().length > 1)) {
      throw new IllegalArgumentException("Test class can only have one constructor");
    }
    paramClass = getSuperClasses(this.fClass).iterator();
    while (paramClass.hasNext())
    {
      Object localObject = (Class)paramClass.next();
      Method[] arrayOfMethod = MethodSorter.getDeclaredMethods((Class)localObject);
      int j = arrayOfMethod.length;
      int i = 0;
      while (i < j)
      {
        addToAnnotationLists(new FrameworkMethod(arrayOfMethod[i]), this.fMethodsForAnnotations);
        i += 1;
      }
      localObject = ((Class)localObject).getDeclaredFields();
      j = localObject.length;
      i = 0;
      while (i < j)
      {
        addToAnnotationLists(new FrameworkField(localObject[i]), this.fFieldsForAnnotations);
        i += 1;
      }
    }
  }
  
  private <T extends FrameworkMember<T>> void addToAnnotationLists(T paramT, Map<Class<?>, List<T>> paramMap)
  {
    Annotation[] arrayOfAnnotation = paramT.getAnnotations();
    int j = arrayOfAnnotation.length;
    int i = 0;
    Class localClass;
    List localList;
    if (i < j)
    {
      localClass = arrayOfAnnotation[i].annotationType();
      localList = getAnnotatedMembers(paramMap, localClass);
      if (!paramT.isShadowedBy(localList)) {}
    }
    else
    {
      return;
    }
    if (runsTopToBottom(localClass)) {
      localList.add(0, paramT);
    }
    for (;;)
    {
      i += 1;
      break;
      localList.add(paramT);
    }
  }
  
  private <T> List<T> getAnnotatedMembers(Map<Class<?>, List<T>> paramMap, Class<? extends Annotation> paramClass)
  {
    if (!paramMap.containsKey(paramClass)) {
      paramMap.put(paramClass, new ArrayList());
    }
    return (List)paramMap.get(paramClass);
  }
  
  private List<Class<?>> getSuperClasses(Class<?> paramClass)
  {
    ArrayList localArrayList = new ArrayList();
    while (paramClass != null)
    {
      localArrayList.add(paramClass);
      paramClass = paramClass.getSuperclass();
    }
    return localArrayList;
  }
  
  private boolean runsTopToBottom(Class<? extends Annotation> paramClass)
  {
    return (paramClass.equals(Before.class)) || (paramClass.equals(BeforeClass.class));
  }
  
  public <T> List<T> getAnnotatedFieldValues(Object paramObject, Class<? extends Annotation> paramClass, Class<T> paramClass1)
  {
    ArrayList localArrayList = new ArrayList();
    paramClass = getAnnotatedFields(paramClass).iterator();
    while (paramClass.hasNext())
    {
      Object localObject = (FrameworkField)paramClass.next();
      try
      {
        localObject = ((FrameworkField)localObject).get(paramObject);
        if (paramClass1.isInstance(localObject)) {
          localArrayList.add(paramClass1.cast(localObject));
        }
      }
      catch (IllegalAccessException paramObject)
      {
        throw new RuntimeException("How did getFields return a field we couldn't access?", (Throwable)paramObject);
      }
    }
    return localArrayList;
  }
  
  public List<FrameworkField> getAnnotatedFields(Class<? extends Annotation> paramClass)
  {
    return getAnnotatedMembers(this.fFieldsForAnnotations, paramClass);
  }
  
  public <T> List<T> getAnnotatedMethodValues(Object paramObject, Class<? extends Annotation> paramClass, Class<T> paramClass1)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = getAnnotatedMethods(paramClass).iterator();
    while (localIterator.hasNext())
    {
      paramClass = (FrameworkMethod)localIterator.next();
      try
      {
        Object localObject = paramClass.invokeExplosively(paramObject, new Object[0]);
        if (paramClass1.isInstance(localObject)) {
          localArrayList.add(paramClass1.cast(localObject));
        }
      }
      catch (Throwable paramObject)
      {
        throw new RuntimeException("Exception in " + paramClass.getName(), (Throwable)paramObject);
      }
    }
    return localArrayList;
  }
  
  public List<FrameworkMethod> getAnnotatedMethods(Class<? extends Annotation> paramClass)
  {
    return getAnnotatedMembers(this.fMethodsForAnnotations, paramClass);
  }
  
  public Annotation[] getAnnotations()
  {
    if (this.fClass == null) {
      return new Annotation[0];
    }
    return this.fClass.getAnnotations();
  }
  
  public Class<?> getJavaClass()
  {
    return this.fClass;
  }
  
  public String getName()
  {
    if (this.fClass == null) {
      return "null";
    }
    return this.fClass.getName();
  }
  
  public Constructor<?> getOnlyConstructor()
  {
    Constructor[] arrayOfConstructor = this.fClass.getConstructors();
    Assert.assertEquals(1L, arrayOfConstructor.length);
    return arrayOfConstructor[0];
  }
  
  public boolean isANonStaticInnerClass()
  {
    return (this.fClass.isMemberClass()) && (!Modifier.isStatic(this.fClass.getModifiers()));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/model/TestClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */