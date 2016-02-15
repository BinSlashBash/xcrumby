package com.fasterxml.jackson.databind.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUMap<K, V>
  extends LinkedHashMap<K, V>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected transient int _jdkSerializeMaxEntries;
  protected final transient int _maxEntries;
  protected final transient Lock _readLock;
  protected final transient Lock _writeLock;
  
  public LRUMap(int paramInt1, int paramInt2)
  {
    super(paramInt1, 0.8F, true);
    this._maxEntries = paramInt2;
    ReentrantReadWriteLock localReentrantReadWriteLock = new ReentrantReadWriteLock();
    this._readLock = localReentrantReadWriteLock.readLock();
    this._writeLock = localReentrantReadWriteLock.writeLock();
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException
  {
    this._jdkSerializeMaxEntries = paramObjectInputStream.readInt();
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeInt(this._jdkSerializeMaxEntries);
  }
  
  public void clear()
  {
    this._writeLock.lock();
    try
    {
      super.clear();
      return;
    }
    finally
    {
      this._writeLock.unlock();
    }
  }
  
  public V get(Object paramObject)
  {
    this._readLock.lock();
    try
    {
      paramObject = super.get(paramObject);
      return (V)paramObject;
    }
    finally
    {
      this._readLock.unlock();
    }
  }
  
  public V put(K paramK, V paramV)
  {
    this._writeLock.lock();
    try
    {
      paramK = super.put(paramK, paramV);
      return paramK;
    }
    finally
    {
      this._writeLock.unlock();
    }
  }
  
  protected Object readResolve()
  {
    return new LRUMap(this._jdkSerializeMaxEntries, this._jdkSerializeMaxEntries);
  }
  
  public V remove(Object paramObject)
  {
    this._writeLock.lock();
    try
    {
      paramObject = super.remove(paramObject);
      return (V)paramObject;
    }
    finally
    {
      this._writeLock.unlock();
    }
  }
  
  protected boolean removeEldestEntry(Map.Entry<K, V> paramEntry)
  {
    return size() > this._maxEntries;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/LRUMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */