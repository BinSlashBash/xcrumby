package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.type.ResolvedType;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public abstract class JavaType
  extends ResolvedType
  implements Serializable, Type
{
  private static final long serialVersionUID = 1L;
  protected final boolean _asStatic;
  protected final Class<?> _class;
  protected final int _hash;
  protected final Object _typeHandler;
  protected final Object _valueHandler;
  
  protected JavaType(Class<?> paramClass, int paramInt, Object paramObject1, Object paramObject2, boolean paramBoolean)
  {
    this._class = paramClass;
    this._hash = (paramClass.getName().hashCode() + paramInt);
    this._valueHandler = paramObject1;
    this._typeHandler = paramObject2;
    this._asStatic = paramBoolean;
  }
  
  protected void _assertSubclass(Class<?> paramClass1, Class<?> paramClass2)
  {
    if (!this._class.isAssignableFrom(paramClass1)) {
      throw new IllegalArgumentException("Class " + paramClass1.getName() + " is not assignable to " + this._class.getName());
    }
  }
  
  protected abstract JavaType _narrow(Class<?> paramClass);
  
  protected JavaType _widen(Class<?> paramClass)
  {
    return _narrow(paramClass);
  }
  
  public JavaType containedType(int paramInt)
  {
    return null;
  }
  
  public int containedTypeCount()
  {
    return 0;
  }
  
  public String containedTypeName(int paramInt)
  {
    return null;
  }
  
  public abstract boolean equals(Object paramObject);
  
  public JavaType forcedNarrowBy(Class<?> paramClass)
  {
    if (paramClass == this._class) {
      return this;
    }
    Object localObject = _narrow(paramClass);
    paramClass = (Class<?>)localObject;
    if (this._valueHandler != ((JavaType)localObject).getValueHandler()) {
      paramClass = ((JavaType)localObject).withValueHandler(this._valueHandler);
    }
    localObject = paramClass;
    if (this._typeHandler != paramClass.getTypeHandler()) {
      localObject = paramClass.withTypeHandler(this._typeHandler);
    }
    return (JavaType)localObject;
  }
  
  public JavaType getContentType()
  {
    return null;
  }
  
  public String getErasedSignature()
  {
    StringBuilder localStringBuilder = new StringBuilder(40);
    getErasedSignature(localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public abstract StringBuilder getErasedSignature(StringBuilder paramStringBuilder);
  
  public String getGenericSignature()
  {
    StringBuilder localStringBuilder = new StringBuilder(40);
    getGenericSignature(localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public abstract StringBuilder getGenericSignature(StringBuilder paramStringBuilder);
  
  public JavaType getKeyType()
  {
    return null;
  }
  
  public final Class<?> getRawClass()
  {
    return this._class;
  }
  
  public <T> T getTypeHandler()
  {
    return (T)this._typeHandler;
  }
  
  public <T> T getValueHandler()
  {
    return (T)this._valueHandler;
  }
  
  public boolean hasGenericTypes()
  {
    return containedTypeCount() > 0;
  }
  
  public final boolean hasRawClass(Class<?> paramClass)
  {
    return this._class == paramClass;
  }
  
  public final int hashCode()
  {
    return this._hash;
  }
  
  public boolean isAbstract()
  {
    return Modifier.isAbstract(this._class.getModifiers());
  }
  
  public boolean isArrayType()
  {
    return false;
  }
  
  public boolean isCollectionLikeType()
  {
    return false;
  }
  
  public boolean isConcrete()
  {
    if ((this._class.getModifiers() & 0x600) == 0) {
      return true;
    }
    return this._class.isPrimitive();
  }
  
  public abstract boolean isContainerType();
  
  public final boolean isEnumType()
  {
    return this._class.isEnum();
  }
  
  public final boolean isFinal()
  {
    return Modifier.isFinal(this._class.getModifiers());
  }
  
  public final boolean isInterface()
  {
    return this._class.isInterface();
  }
  
  public boolean isMapLikeType()
  {
    return false;
  }
  
  public final boolean isPrimitive()
  {
    return this._class.isPrimitive();
  }
  
  public boolean isThrowable()
  {
    return Throwable.class.isAssignableFrom(this._class);
  }
  
  public JavaType narrowBy(Class<?> paramClass)
  {
    if (paramClass == this._class) {
      return this;
    }
    _assertSubclass(paramClass, this._class);
    Object localObject = _narrow(paramClass);
    paramClass = (Class<?>)localObject;
    if (this._valueHandler != ((JavaType)localObject).getValueHandler()) {
      paramClass = ((JavaType)localObject).withValueHandler(this._valueHandler);
    }
    localObject = paramClass;
    if (this._typeHandler != paramClass.getTypeHandler()) {
      localObject = paramClass.withTypeHandler(this._typeHandler);
    }
    return (JavaType)localObject;
  }
  
  public abstract JavaType narrowContentsBy(Class<?> paramClass);
  
  public abstract String toString();
  
  public final boolean useStaticType()
  {
    return this._asStatic;
  }
  
  public JavaType widenBy(Class<?> paramClass)
  {
    if (paramClass == this._class) {
      return this;
    }
    _assertSubclass(this._class, paramClass);
    return _widen(paramClass);
  }
  
  public abstract JavaType widenContentsBy(Class<?> paramClass);
  
  public abstract JavaType withContentTypeHandler(Object paramObject);
  
  public abstract JavaType withContentValueHandler(Object paramObject);
  
  public abstract JavaType withStaticTyping();
  
  public abstract JavaType withTypeHandler(Object paramObject);
  
  public abstract JavaType withValueHandler(Object paramObject);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/JavaType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */