package com.squareup.otto;

import java.util.Map;
import java.util.Set;

abstract interface HandlerFinder
{
  public static final HandlerFinder ANNOTATED = new HandlerFinder()
  {
    public Map<Class<?>, EventProducer> findAllProducers(Object paramAnonymousObject)
    {
      return AnnotatedHandlerFinder.findAllProducers(paramAnonymousObject);
    }
    
    public Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object paramAnonymousObject)
    {
      return AnnotatedHandlerFinder.findAllSubscribers(paramAnonymousObject);
    }
  };
  
  public abstract Map<Class<?>, EventProducer> findAllProducers(Object paramObject);
  
  public abstract Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object paramObject);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/otto/HandlerFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */