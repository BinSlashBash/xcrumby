package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;

public final class MapType
  extends MapLikeType
{
  private static final long serialVersionUID = -811146779148281500L;
  
  private MapType(Class<?> paramClass, JavaType paramJavaType1, JavaType paramJavaType2, Object paramObject1, Object paramObject2, boolean paramBoolean)
  {
    super(paramClass, paramJavaType1, paramJavaType2, paramObject1, paramObject2, paramBoolean);
  }
  
  public static MapType construct(Class<?> paramClass, JavaType paramJavaType1, JavaType paramJavaType2)
  {
    return new MapType(paramClass, paramJavaType1, paramJavaType2, null, null, false);
  }
  
  protected JavaType _narrow(Class<?> paramClass)
  {
    return new MapType(paramClass, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
  }
  
  public JavaType narrowContentsBy(Class<?> paramClass)
  {
    if (paramClass == this._valueType.getRawClass()) {
      return this;
    }
    return new MapType(this._class, this._keyType, this._valueType.narrowBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
  }
  
  public JavaType narrowKey(Class<?> paramClass)
  {
    if (paramClass == this._keyType.getRawClass()) {
      return this;
    }
    return new MapType(this._class, this._keyType.narrowBy(paramClass), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
  }
  
  public String toString()
  {
    return "[map type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
  }
  
  public JavaType widenContentsBy(Class<?> paramClass)
  {
    if (paramClass == this._valueType.getRawClass()) {
      return this;
    }
    return new MapType(this._class, this._keyType, this._valueType.widenBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
  }
  
  public JavaType widenKey(Class<?> paramClass)
  {
    if (paramClass == this._keyType.getRawClass()) {
      return this;
    }
    return new MapType(this._class, this._keyType.widenBy(paramClass), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
  }
  
  public MapType withContentTypeHandler(Object paramObject)
  {
    return new MapType(this._class, this._keyType, this._valueType.withTypeHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
  }
  
  public MapType withContentValueHandler(Object paramObject)
  {
    return new MapType(this._class, this._keyType, this._valueType.withValueHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
  }
  
  public MapType withKeyTypeHandler(Object paramObject)
  {
    return new MapType(this._class, this._keyType.withTypeHandler(paramObject), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
  }
  
  public MapType withKeyValueHandler(Object paramObject)
  {
    return new MapType(this._class, this._keyType.withValueHandler(paramObject), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
  }
  
  public MapType withStaticTyping()
  {
    if (this._asStatic) {
      return this;
    }
    return new MapType(this._class, this._keyType.withStaticTyping(), this._valueType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
  }
  
  public MapType withTypeHandler(Object paramObject)
  {
    return new MapType(this._class, this._keyType, this._valueType, this._valueHandler, paramObject, this._asStatic);
  }
  
  public MapType withValueHandler(Object paramObject)
  {
    return new MapType(this._class, this._keyType, this._valueType, paramObject, this._typeHandler, this._asStatic);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/type/MapType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */