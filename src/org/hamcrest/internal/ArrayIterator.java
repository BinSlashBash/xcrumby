package org.hamcrest.internal;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ArrayIterator
  implements Iterator<Object>
{
  private final Object array;
  private int currentIndex = 0;
  
  public ArrayIterator(Object paramObject)
  {
    if (!paramObject.getClass().isArray()) {
      throw new IllegalArgumentException("not an array");
    }
    this.array = paramObject;
  }
  
  public boolean hasNext()
  {
    return this.currentIndex < Array.getLength(this.array);
  }
  
  public Object next()
  {
    Object localObject = this.array;
    int i = this.currentIndex;
    this.currentIndex = (i + 1);
    return Array.get(localObject, i);
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException("cannot remove items from an array");
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/internal/ArrayIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */