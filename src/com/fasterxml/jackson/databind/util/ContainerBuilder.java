package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ContainerBuilder
{
  private static final int MAX_BUF = 1000;
  private Object[] b;
  private List<Object> list;
  private Map<String, Object> map;
  private int start;
  private int tail;
  
  public ContainerBuilder(int paramInt)
  {
    this.b = new Object[paramInt & 0xFFFFFFFE];
  }
  
  private List<Object> _buildList(boolean paramBoolean)
  {
    int j = this.tail - this.start;
    int i;
    if (paramBoolean)
    {
      i = j;
      if (j < 2) {
        i = 2;
      }
    }
    ArrayList localArrayList;
    for (;;)
    {
      localArrayList = new ArrayList(i);
      i = this.start;
      while (i < this.tail)
      {
        localArrayList.add(this.b[i]);
        i += 1;
      }
      if (j < 20) {
        i = 20;
      } else if (j < 1000) {
        i = j + (j >> 1);
      } else {
        i = j + (j >> 2);
      }
    }
    this.tail = this.start;
    return localArrayList;
  }
  
  private Map<String, Object> _buildMap(boolean paramBoolean)
  {
    int i = this.tail - this.start >> 1;
    if (paramBoolean) {
      if (i <= 3) {
        i = 4;
      }
    }
    LinkedHashMap localLinkedHashMap;
    for (;;)
    {
      localLinkedHashMap = new LinkedHashMap(i, 0.8F);
      i = this.start;
      while (i < this.tail)
      {
        localLinkedHashMap.put((String)this.b[i], this.b[(i + 1)]);
        i += 2;
      }
      if (i <= 40)
      {
        i += (i >> 1);
      }
      else
      {
        i += (i >> 2) + (i >> 4);
        continue;
        if (i < 10) {
          i = 16;
        } else if (i < 1000) {
          i += (i >> 1);
        } else {
          i += i / 3;
        }
      }
    }
    this.tail = this.start;
    return localLinkedHashMap;
  }
  
  private void _expandList(Object paramObject)
  {
    if (this.b.length < 1000)
    {
      this.b = Arrays.copyOf(this.b, this.b.length << 1);
      Object[] arrayOfObject = this.b;
      int i = this.tail;
      this.tail = (i + 1);
      arrayOfObject[i] = paramObject;
      return;
    }
    this.list = _buildList(false);
    this.list.add(paramObject);
  }
  
  private void _expandMap(String paramString, Object paramObject)
  {
    if (this.b.length < 1000)
    {
      this.b = Arrays.copyOf(this.b, this.b.length << 1);
      Object[] arrayOfObject = this.b;
      int i = this.tail;
      this.tail = (i + 1);
      arrayOfObject[i] = paramString;
      paramString = this.b;
      i = this.tail;
      this.tail = (i + 1);
      paramString[i] = paramObject;
      return;
    }
    this.map = _buildMap(false);
    this.map.put(paramString, paramObject);
  }
  
  public void add(Object paramObject)
  {
    if (this.list != null)
    {
      this.list.add(paramObject);
      return;
    }
    if (this.tail >= this.b.length)
    {
      _expandList(paramObject);
      return;
    }
    Object[] arrayOfObject = this.b;
    int i = this.tail;
    this.tail = (i + 1);
    arrayOfObject[i] = paramObject;
  }
  
  public int bufferLength()
  {
    return this.b.length;
  }
  
  public boolean canReuse()
  {
    return (this.list == null) && (this.map == null);
  }
  
  public Object[] finishArray(int paramInt)
  {
    Object[] arrayOfObject;
    if (this.list == null) {
      arrayOfObject = Arrays.copyOfRange(this.b, this.start, this.tail);
    }
    for (;;)
    {
      this.start = paramInt;
      return arrayOfObject;
      arrayOfObject = this.list.toArray(new Object[this.tail - this.start]);
      this.list = null;
    }
  }
  
  public <T> Object[] finishArray(int paramInt, Class<T> paramClass)
  {
    int i = this.tail - this.start;
    paramClass = (Object[])Array.newInstance(paramClass, i);
    if (this.list == null) {
      System.arraycopy(this.b, this.start, paramClass, 0, i);
    }
    for (;;)
    {
      this.start = paramInt;
      return paramClass;
      paramClass = this.list.toArray(paramClass);
      this.list = null;
    }
  }
  
  public List<Object> finishList(int paramInt)
  {
    List localList = this.list;
    if (localList == null) {
      localList = _buildList(true);
    }
    for (;;)
    {
      this.start = paramInt;
      return localList;
      this.list = null;
    }
  }
  
  public Map<String, Object> finishMap(int paramInt)
  {
    Map localMap = this.map;
    if (localMap == null) {
      localMap = _buildMap(true);
    }
    for (;;)
    {
      this.start = paramInt;
      return localMap;
      this.map = null;
    }
  }
  
  public void put(String paramString, Object paramObject)
  {
    if (this.map != null)
    {
      this.map.put(paramString, paramObject);
      return;
    }
    if (this.tail + 2 > this.b.length)
    {
      _expandMap(paramString, paramObject);
      return;
    }
    Object[] arrayOfObject = this.b;
    int i = this.tail;
    this.tail = (i + 1);
    arrayOfObject[i] = paramString;
    paramString = this.b;
    i = this.tail;
    this.tail = (i + 1);
    paramString[i] = paramObject;
  }
  
  public int start()
  {
    if ((this.list != null) || (this.map != null)) {
      throw new IllegalStateException();
    }
    int i = this.start;
    this.start = this.tail;
    return i;
  }
  
  public int startList(Object paramObject)
  {
    if ((this.list != null) || (this.map != null)) {
      throw new IllegalStateException();
    }
    int i = this.start;
    this.start = this.tail;
    add(paramObject);
    return i;
  }
  
  public int startMap(String paramString, Object paramObject)
  {
    if ((this.list != null) || (this.map != null)) {
      throw new IllegalStateException();
    }
    int i = this.start;
    this.start = this.tail;
    put(paramString, paramObject);
    return i;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/ContainerBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */