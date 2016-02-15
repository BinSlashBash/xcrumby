package com.fasterxml.jackson.databind.util;

public final class LinkedNode<T>
{
  private LinkedNode<T> next;
  private final T value;
  
  public LinkedNode(T paramT, LinkedNode<T> paramLinkedNode)
  {
    this.value = paramT;
    this.next = paramLinkedNode;
  }
  
  public static <ST> boolean contains(LinkedNode<ST> paramLinkedNode, ST paramST)
  {
    while (paramLinkedNode != null)
    {
      if (paramLinkedNode.value() == paramST) {
        return true;
      }
      paramLinkedNode = paramLinkedNode.next();
    }
    return false;
  }
  
  public void linkNext(LinkedNode<T> paramLinkedNode)
  {
    if (this.next != null) {
      throw new IllegalStateException();
    }
    this.next = paramLinkedNode;
  }
  
  public LinkedNode<T> next()
  {
    return this.next;
  }
  
  public T value()
  {
    return (T)this.value;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/LinkedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */