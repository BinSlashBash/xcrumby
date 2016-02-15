package com.fasterxml.jackson.databind.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EmptyIterator<T>
  implements Iterator<T>
{
  private static final EmptyIterator<?> instance = new EmptyIterator();
  
  public static <T> Iterator<T> instance()
  {
    return instance;
  }
  
  public boolean hasNext()
  {
    return false;
  }
  
  public T next()
  {
    throw new NoSuchElementException();
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/EmptyIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */