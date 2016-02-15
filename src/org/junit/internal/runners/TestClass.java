package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.MethodSorter;

@Deprecated
public class TestClass
{
  private final Class<?> fClass;
  
  public TestClass(Class<?> paramClass)
  {
    this.fClass = paramClass;
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
  
  private boolean isShadowed(Method paramMethod1, Method paramMethod2)
  {
    if (!paramMethod2.getName().equals(paramMethod1.getName())) {}
    while (paramMethod2.getParameterTypes().length != paramMethod1.getParameterTypes().length) {
      return false;
    }
    int i = 0;
    for (;;)
    {
      if (i >= paramMethod2.getParameterTypes().length) {
        break label65;
      }
      if (!paramMethod2.getParameterTypes()[i].equals(paramMethod1.getParameterTypes()[i])) {
        break;
      }
      i += 1;
    }
    label65:
    return true;
  }
  
  private boolean isShadowed(Method paramMethod, List<Method> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      if (isShadowed(paramMethod, (Method)paramList.next())) {
        return true;
      }
    }
    return false;
  }
  
  private boolean runsTopToBottom(Class<? extends Annotation> paramClass)
  {
    return (paramClass.equals(Before.class)) || (paramClass.equals(BeforeClass.class));
  }
  
  List<Method> getAfters()
  {
    return getAnnotatedMethods(AfterClass.class);
  }
  
  public List<Method> getAnnotatedMethods(Class<? extends Annotation> paramClass)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = getSuperClasses(this.fClass).iterator();
    while (localIterator.hasNext())
    {
      Method[] arrayOfMethod = MethodSorter.getDeclaredMethods((Class)localIterator.next());
      int j = arrayOfMethod.length;
      int i = 0;
      while (i < j)
      {
        Method localMethod = arrayOfMethod[i];
        if ((localMethod.getAnnotation(paramClass) != null) && (!isShadowed(localMethod, localArrayList))) {
          localArrayList.add(localMethod);
        }
        i += 1;
      }
    }
    if (runsTopToBottom(paramClass)) {
      Collections.reverse(localArrayList);
    }
    return localArrayList;
  }
  
  List<Method> getBefores()
  {
    return getAnnotatedMethods(BeforeClass.class);
  }
  
  public Constructor<?> getConstructor()
    throws SecurityException, NoSuchMethodException
  {
    return this.fClass.getConstructor(new Class[0]);
  }
  
  public Class<?> getJavaClass()
  {
    return this.fClass;
  }
  
  public String getName()
  {
    return this.fClass.getName();
  }
  
  public List<Method> getTestMethods()
  {
    return getAnnotatedMethods(Test.class);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/TestClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */