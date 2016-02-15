package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.SerializerCache.TypeKey;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class JsonSerializerMap
{
  private final Bucket[] _buckets;
  private final int _size;
  
  public JsonSerializerMap(Map<SerializerCache.TypeKey, JsonSerializer<Object>> paramMap)
  {
    int i = findSize(paramMap.size());
    this._size = i;
    Bucket[] arrayOfBucket = new Bucket[i];
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      SerializerCache.TypeKey localTypeKey = (SerializerCache.TypeKey)localEntry.getKey();
      int j = localTypeKey.hashCode() & i - 1;
      arrayOfBucket[j] = new Bucket(arrayOfBucket[j], localTypeKey, (JsonSerializer)localEntry.getValue());
    }
    this._buckets = arrayOfBucket;
  }
  
  private static final int findSize(int paramInt)
  {
    if (paramInt <= 64) {
      paramInt += paramInt;
    }
    int i;
    for (;;)
    {
      i = 8;
      while (i < paramInt) {
        i += i;
      }
      paramInt += (paramInt >> 2);
    }
    return i;
  }
  
  public JsonSerializer<Object> find(SerializerCache.TypeKey paramTypeKey)
  {
    int i = paramTypeKey.hashCode();
    int j = this._buckets.length;
    Bucket localBucket2 = this._buckets[(i & j - 1)];
    if (localBucket2 == null) {
      return null;
    }
    Bucket localBucket1 = localBucket2;
    if (paramTypeKey.equals(localBucket2.key)) {
      return localBucket2.value;
    }
    do
    {
      localBucket2 = localBucket1.next;
      if (localBucket2 == null) {
        break;
      }
      localBucket1 = localBucket2;
    } while (!paramTypeKey.equals(localBucket2.key));
    return localBucket2.value;
  }
  
  public int size()
  {
    return this._size;
  }
  
  private static final class Bucket
  {
    public final SerializerCache.TypeKey key;
    public final Bucket next;
    public final JsonSerializer<Object> value;
    
    public Bucket(Bucket paramBucket, SerializerCache.TypeKey paramTypeKey, JsonSerializer<Object> paramJsonSerializer)
    {
      this.next = paramBucket;
      this.key = paramTypeKey;
      this.value = paramJsonSerializer;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/impl/JsonSerializerMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */