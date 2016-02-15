package org.jsoup.helper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class DescendableLinkedList<E>
  extends LinkedList<E>
{
  public Iterator<E> descendingIterator()
  {
    return new DescendingIterator(size(), null);
  }
  
  public E peekLast()
  {
    if (size() == 0) {
      return null;
    }
    return (E)getLast();
  }
  
  public E pollLast()
  {
    if (size() == 0) {
      return null;
    }
    return (E)removeLast();
  }
  
  public void push(E paramE)
  {
    addFirst(paramE);
  }
  
  private class DescendingIterator<E>
    implements Iterator<E>
  {
    private final ListIterator<E> iter;
    
    private DescendingIterator(int paramInt)
    {
      this.iter = DescendableLinkedList.this.listIterator(paramInt);
    }
    
    public boolean hasNext()
    {
      return this.iter.hasPrevious();
    }
    
    public E next()
    {
      return (E)this.iter.previous();
    }
    
    public void remove()
    {
      this.iter.remove();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/helper/DescendableLinkedList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */