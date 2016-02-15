package com.squareup.otto;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class AnnotatedHandlerFinder
{
  private static final Map<Class<?>, Map<Class<?>, Method>> PRODUCERS_CACHE = new HashMap();
  private static final Map<Class<?>, Map<Class<?>, Set<Method>>> SUBSCRIBERS_CACHE = new HashMap();
  
  static Map<Class<?>, EventProducer> findAllProducers(Object paramObject)
  {
    Object localObject = paramObject.getClass();
    HashMap localHashMap = new HashMap();
    if (!PRODUCERS_CACHE.containsKey(localObject)) {
      loadAnnotatedMethods((Class)localObject);
    }
    localObject = (Map)PRODUCERS_CACHE.get(localObject);
    if (!((Map)localObject).isEmpty())
    {
      localObject = ((Map)localObject).entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        EventProducer localEventProducer = new EventProducer(paramObject, (Method)localEntry.getValue());
        localHashMap.put(localEntry.getKey(), localEventProducer);
      }
    }
    return localHashMap;
  }
  
  static Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object paramObject)
  {
    Object localObject = paramObject.getClass();
    HashMap localHashMap = new HashMap();
    if (!SUBSCRIBERS_CACHE.containsKey(localObject)) {
      loadAnnotatedMethods((Class)localObject);
    }
    localObject = (Map)SUBSCRIBERS_CACHE.get(localObject);
    if (!((Map)localObject).isEmpty())
    {
      localObject = ((Map)localObject).entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        HashSet localHashSet = new HashSet();
        Iterator localIterator = ((Set)localEntry.getValue()).iterator();
        while (localIterator.hasNext()) {
          localHashSet.add(new EventHandler(paramObject, (Method)localIterator.next()));
        }
        localHashMap.put(localEntry.getKey(), localHashSet);
      }
    }
    return localHashMap;
  }
  
  private static void loadAnnotatedMethods(Class<?> paramClass)
  {
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    Method[] arrayOfMethod = paramClass.getDeclaredMethods();
    int j = arrayOfMethod.length;
    int i = 0;
    if (i < j)
    {
      Method localMethod = arrayOfMethod[i];
      Object localObject1;
      if (localMethod.isAnnotationPresent(Subscribe.class))
      {
        localObject1 = localMethod.getParameterTypes();
        if (localObject1.length != 1) {
          throw new IllegalArgumentException("Method " + localMethod + " has @Subscribe annotation but requires " + localObject1.length + " arguments.  Methods must require a single argument.");
        }
        Object localObject2 = localObject1[0];
        if (((Class)localObject2).isInterface()) {
          throw new IllegalArgumentException("Method " + localMethod + " has @Subscribe annotation on " + localObject2 + " which is an interface.  Subscription must be on a concrete class type.");
        }
        if ((localMethod.getModifiers() & 0x1) == 0) {
          throw new IllegalArgumentException("Method " + localMethod + " has @Subscribe annotation on " + localObject2 + " but is not 'public'.");
        }
        Set localSet = (Set)localHashMap1.get(localObject2);
        localObject1 = localSet;
        if (localSet == null)
        {
          localObject1 = new HashSet();
          localHashMap1.put(localObject2, localObject1);
        }
        ((Set)localObject1).add(localMethod);
      }
      for (;;)
      {
        i += 1;
        break;
        if (localMethod.isAnnotationPresent(Produce.class))
        {
          localObject1 = localMethod.getParameterTypes();
          if (localObject1.length != 0) {
            throw new IllegalArgumentException("Method " + localMethod + "has @Produce annotation but requires " + localObject1.length + " arguments.  Methods must require zero arguments.");
          }
          if (localMethod.getReturnType() == Void.class) {
            throw new IllegalArgumentException("Method " + localMethod + " has a return type of void.  Must declare a non-void type.");
          }
          localObject1 = localMethod.getReturnType();
          if (((Class)localObject1).isInterface()) {
            throw new IllegalArgumentException("Method " + localMethod + " has @Produce annotation on " + localObject1 + " which is an interface.  Producers must return a concrete class type.");
          }
          if (localObject1.equals(Void.TYPE)) {
            throw new IllegalArgumentException("Method " + localMethod + " has @Produce annotation but has no return type.");
          }
          if ((localMethod.getModifiers() & 0x1) == 0) {
            throw new IllegalArgumentException("Method " + localMethod + " has @Produce annotation on " + localObject1 + " but is not 'public'.");
          }
          if (localHashMap2.containsKey(localObject1)) {
            throw new IllegalArgumentException("Producer for type " + localObject1 + " has already been registered.");
          }
          localHashMap2.put(localObject1, localMethod);
        }
      }
    }
    PRODUCERS_CACHE.put(paramClass, localHashMap2);
    SUBSCRIBERS_CACHE.put(paramClass, localHashMap1);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/otto/AnnotatedHandlerFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */