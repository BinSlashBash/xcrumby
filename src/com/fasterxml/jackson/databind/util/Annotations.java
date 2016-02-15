package com.fasterxml.jackson.databind.util;

import java.lang.annotation.Annotation;

public abstract interface Annotations
{
  public abstract <A extends Annotation> A get(Class<A> paramClass);
  
  public abstract int size();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/Annotations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */