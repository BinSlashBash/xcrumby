package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BeanPropertyMap
  implements Iterable<SettableBeanProperty>, Serializable
{
  private static final long serialVersionUID = 1L;
  private final Bucket[] _buckets;
  private final int _hashMask;
  private int _nextBucketIndex = 0;
  private final int _size;
  
  public BeanPropertyMap(Collection<SettableBeanProperty> paramCollection)
  {
    this._size = paramCollection.size();
    int i = findSize(this._size);
    this._hashMask = (i - 1);
    Bucket[] arrayOfBucket = new Bucket[i];
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      SettableBeanProperty localSettableBeanProperty = (SettableBeanProperty)paramCollection.next();
      String str = localSettableBeanProperty.getName();
      i = str.hashCode() & this._hashMask;
      Bucket localBucket = arrayOfBucket[i];
      int j = this._nextBucketIndex;
      this._nextBucketIndex = (j + 1);
      arrayOfBucket[i] = new Bucket(localBucket, str, localSettableBeanProperty, j);
    }
    this._buckets = arrayOfBucket;
  }
  
  private BeanPropertyMap(Bucket[] paramArrayOfBucket, int paramInt1, int paramInt2)
  {
    this._buckets = paramArrayOfBucket;
    this._size = paramInt1;
    this._hashMask = (paramArrayOfBucket.length - 1);
    this._nextBucketIndex = paramInt2;
  }
  
  private SettableBeanProperty _findWithEquals(String paramString, int paramInt)
  {
    for (Bucket localBucket = this._buckets[paramInt]; localBucket != null; localBucket = localBucket.next) {
      if (paramString.equals(localBucket.key)) {
        return localBucket.value;
      }
    }
    return null;
  }
  
  private static final int findSize(int paramInt)
  {
    if (paramInt <= 32) {
      paramInt += paramInt;
    }
    int i;
    for (;;)
    {
      i = 2;
      while (i < paramInt) {
        i += i;
      }
      paramInt += (paramInt >> 2);
    }
    return i;
  }
  
  public BeanPropertyMap assignIndexes()
  {
    int i = 0;
    Bucket[] arrayOfBucket = this._buckets;
    int k = arrayOfBucket.length;
    int j = 0;
    while (j < k)
    {
      Bucket localBucket = arrayOfBucket[j];
      while (localBucket != null)
      {
        localBucket.value.assignIndex(i);
        localBucket = localBucket.next;
        i += 1;
      }
      j += 1;
    }
    return this;
  }
  
  public SettableBeanProperty find(int paramInt)
  {
    int i = 0;
    int j = this._buckets.length;
    while (i < j)
    {
      for (Bucket localBucket = this._buckets[i]; localBucket != null; localBucket = localBucket.next) {
        if (localBucket.index == paramInt) {
          return localBucket.value;
        }
      }
      i += 1;
    }
    return null;
  }
  
  public SettableBeanProperty find(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Can not pass null property name");
    }
    int i = paramString.hashCode() & this._hashMask;
    Bucket localBucket2 = this._buckets[i];
    if (localBucket2 == null) {
      return null;
    }
    Bucket localBucket1 = localBucket2;
    if (localBucket2.key == paramString) {
      return localBucket2.value;
    }
    do
    {
      localBucket2 = localBucket1.next;
      if (localBucket2 == null) {
        break;
      }
      localBucket1 = localBucket2;
    } while (localBucket2.key != paramString);
    return localBucket2.value;
    return _findWithEquals(paramString, i);
  }
  
  public SettableBeanProperty[] getPropertiesInInsertionOrder()
  {
    SettableBeanProperty[] arrayOfSettableBeanProperty = new SettableBeanProperty[this._nextBucketIndex];
    Bucket[] arrayOfBucket = this._buckets;
    int j = arrayOfBucket.length;
    int i = 0;
    while (i < j)
    {
      for (Bucket localBucket = arrayOfBucket[i]; localBucket != null; localBucket = localBucket.next) {
        arrayOfSettableBeanProperty[localBucket.index] = localBucket.value;
      }
      i += 1;
    }
    return arrayOfSettableBeanProperty;
  }
  
  public Iterator<SettableBeanProperty> iterator()
  {
    return new IteratorImpl(this._buckets);
  }
  
  public void remove(SettableBeanProperty paramSettableBeanProperty)
  {
    String str = paramSettableBeanProperty.getName();
    int j = str.hashCode() & this._buckets.length - 1;
    int i = 0;
    Bucket localBucket2 = this._buckets[j];
    Bucket localBucket1 = null;
    if (localBucket2 != null)
    {
      if ((i == 0) && (localBucket2.key.equals(str))) {
        i = 1;
      }
      for (;;)
      {
        localBucket2 = localBucket2.next;
        break;
        localBucket1 = new Bucket(localBucket1, localBucket2.key, localBucket2.value, localBucket2.index);
      }
    }
    if (i == 0) {
      throw new NoSuchElementException("No entry '" + paramSettableBeanProperty + "' found, can't remove");
    }
    this._buckets[j] = localBucket1;
  }
  
  public BeanPropertyMap renameAll(NameTransformer paramNameTransformer)
  {
    if ((paramNameTransformer == null) || (paramNameTransformer == NameTransformer.NOP)) {
      return this;
    }
    Iterator localIterator = iterator();
    ArrayList localArrayList = new ArrayList();
    while (localIterator.hasNext())
    {
      Object localObject = (SettableBeanProperty)localIterator.next();
      SettableBeanProperty localSettableBeanProperty = ((SettableBeanProperty)localObject).withSimpleName(paramNameTransformer.transform(((SettableBeanProperty)localObject).getName()));
      JsonDeserializer localJsonDeserializer1 = localSettableBeanProperty.getValueDeserializer();
      localObject = localSettableBeanProperty;
      if (localJsonDeserializer1 != null)
      {
        JsonDeserializer localJsonDeserializer2 = localJsonDeserializer1.unwrappingDeserializer(paramNameTransformer);
        localObject = localSettableBeanProperty;
        if (localJsonDeserializer2 != localJsonDeserializer1) {
          localObject = localSettableBeanProperty.withValueDeserializer(localJsonDeserializer2);
        }
      }
      localArrayList.add(localObject);
    }
    return new BeanPropertyMap(localArrayList);
  }
  
  public void replace(SettableBeanProperty paramSettableBeanProperty)
  {
    String str = paramSettableBeanProperty.getName();
    int j = str.hashCode() & this._buckets.length - 1;
    int i = -1;
    Bucket localBucket2 = this._buckets[j];
    Bucket localBucket1 = null;
    if (localBucket2 != null)
    {
      if ((i < 0) && (localBucket2.key.equals(str))) {
        i = localBucket2.index;
      }
      for (;;)
      {
        localBucket2 = localBucket2.next;
        break;
        localBucket1 = new Bucket(localBucket1, localBucket2.key, localBucket2.value, localBucket2.index);
      }
    }
    if (i < 0) {
      throw new NoSuchElementException("No entry '" + paramSettableBeanProperty + "' found, can't replace");
    }
    this._buckets[j] = new Bucket(localBucket1, str, paramSettableBeanProperty, i);
  }
  
  public int size()
  {
    return this._size;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Properties=[");
    SettableBeanProperty[] arrayOfSettableBeanProperty = getPropertiesInInsertionOrder();
    int m = arrayOfSettableBeanProperty.length;
    int j = 0;
    int i = 0;
    if (j < m)
    {
      SettableBeanProperty localSettableBeanProperty = arrayOfSettableBeanProperty[j];
      if (localSettableBeanProperty == null) {}
      for (;;)
      {
        j += 1;
        break;
        int k = i + 1;
        if (i > 0) {
          localStringBuilder.append(", ");
        }
        localStringBuilder.append(localSettableBeanProperty.getName());
        localStringBuilder.append('(');
        localStringBuilder.append(localSettableBeanProperty.getType());
        localStringBuilder.append(')');
        i = k;
      }
    }
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
  
  public BeanPropertyMap withProperty(SettableBeanProperty paramSettableBeanProperty)
  {
    int i = this._buckets.length;
    Object localObject = new Bucket[i];
    System.arraycopy(this._buckets, 0, localObject, 0, i);
    String str = paramSettableBeanProperty.getName();
    if (find(paramSettableBeanProperty.getName()) == null)
    {
      i = str.hashCode() & this._hashMask;
      Bucket localBucket = localObject[i];
      int j = this._nextBucketIndex;
      this._nextBucketIndex = (j + 1);
      localObject[i] = new Bucket(localBucket, str, paramSettableBeanProperty, j);
      return new BeanPropertyMap((Bucket[])localObject, this._size + 1, this._nextBucketIndex);
    }
    localObject = new BeanPropertyMap((Bucket[])localObject, i, this._nextBucketIndex);
    ((BeanPropertyMap)localObject).replace(paramSettableBeanProperty);
    return (BeanPropertyMap)localObject;
  }
  
  private static final class Bucket
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    public final int index;
    public final String key;
    public final Bucket next;
    public final SettableBeanProperty value;
    
    public Bucket(Bucket paramBucket, String paramString, SettableBeanProperty paramSettableBeanProperty, int paramInt)
    {
      this.next = paramBucket;
      this.key = paramString;
      this.value = paramSettableBeanProperty;
      this.index = paramInt;
    }
  }
  
  private static final class IteratorImpl
    implements Iterator<SettableBeanProperty>
  {
    private final BeanPropertyMap.Bucket[] _buckets;
    private BeanPropertyMap.Bucket _currentBucket;
    private int _nextBucketIndex;
    
    public IteratorImpl(BeanPropertyMap.Bucket[] paramArrayOfBucket)
    {
      this._buckets = paramArrayOfBucket;
      int k = this._buckets.length;
      int i = 0;
      int j;
      if (i < k)
      {
        paramArrayOfBucket = this._buckets;
        j = i + 1;
        paramArrayOfBucket = paramArrayOfBucket[i];
        if (paramArrayOfBucket != null) {
          this._currentBucket = paramArrayOfBucket;
        }
      }
      for (;;)
      {
        this._nextBucketIndex = j;
        return;
        i = j;
        break;
        j = i;
      }
    }
    
    public boolean hasNext()
    {
      return this._currentBucket != null;
    }
    
    public SettableBeanProperty next()
    {
      BeanPropertyMap.Bucket localBucket = this._currentBucket;
      if (localBucket == null) {
        throw new NoSuchElementException();
      }
      int i;
      for (Object localObject = localBucket.next; (localObject == null) && (this._nextBucketIndex < this._buckets.length); localObject = localObject[i])
      {
        localObject = this._buckets;
        i = this._nextBucketIndex;
        this._nextBucketIndex = (i + 1);
      }
      this._currentBucket = ((BeanPropertyMap.Bucket)localObject);
      return localBucket.value;
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/impl/BeanPropertyMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */