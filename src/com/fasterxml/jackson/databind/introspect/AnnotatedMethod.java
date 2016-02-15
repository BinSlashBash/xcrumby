package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class AnnotatedMethod
  extends AnnotatedWithParams
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected final transient Method _method;
  protected Class<?>[] _paramClasses;
  protected Serialization _serialization;
  
  protected AnnotatedMethod(Serialization paramSerialization)
  {
    super(null, null);
    this._method = null;
    this._serialization = paramSerialization;
  }
  
  public AnnotatedMethod(Method paramMethod, AnnotationMap paramAnnotationMap, AnnotationMap[] paramArrayOfAnnotationMap)
  {
    super(paramAnnotationMap, paramArrayOfAnnotationMap);
    if (paramMethod == null) {
      throw new IllegalArgumentException("Can not construct AnnotatedMethod with null Method");
    }
    this._method = paramMethod;
  }
  
  public final Object call()
    throws Exception
  {
    return this._method.invoke(null, new Object[0]);
  }
  
  public final Object call(Object[] paramArrayOfObject)
    throws Exception
  {
    return this._method.invoke(null, paramArrayOfObject);
  }
  
  public final Object call1(Object paramObject)
    throws Exception
  {
    return this._method.invoke(null, new Object[] { paramObject });
  }
  
  public Method getAnnotated()
  {
    return this._method;
  }
  
  public Class<?> getDeclaringClass()
  {
    return this._method.getDeclaringClass();
  }
  
  public String getFullName()
  {
    return getDeclaringClass().getName() + "#" + getName() + "(" + getParameterCount() + " params)";
  }
  
  public Type getGenericParameterType(int paramInt)
  {
    Type[] arrayOfType = this._method.getGenericParameterTypes();
    if (paramInt >= arrayOfType.length) {
      return null;
    }
    return arrayOfType[paramInt];
  }
  
  public Type[] getGenericParameterTypes()
  {
    return this._method.getGenericParameterTypes();
  }
  
  public Type getGenericReturnType()
  {
    return this._method.getGenericReturnType();
  }
  
  public Type getGenericType()
  {
    return this._method.getGenericReturnType();
  }
  
  public Method getMember()
  {
    return this._method;
  }
  
  public int getModifiers()
  {
    return this._method.getModifiers();
  }
  
  public String getName()
  {
    return this._method.getName();
  }
  
  public int getParameterCount()
  {
    return getRawParameterTypes().length;
  }
  
  public Class<?> getRawParameterType(int paramInt)
  {
    Class[] arrayOfClass = getRawParameterTypes();
    if (paramInt >= arrayOfClass.length) {
      return null;
    }
    return arrayOfClass[paramInt];
  }
  
  public Class<?>[] getRawParameterTypes()
  {
    if (this._paramClasses == null) {
      this._paramClasses = this._method.getParameterTypes();
    }
    return this._paramClasses;
  }
  
  public Class<?> getRawReturnType()
  {
    return this._method.getReturnType();
  }
  
  public Class<?> getRawType()
  {
    return this._method.getReturnType();
  }
  
  public JavaType getType(TypeBindings paramTypeBindings)
  {
    return getType(paramTypeBindings, this._method.getTypeParameters());
  }
  
  public Object getValue(Object paramObject)
    throws IllegalArgumentException
  {
    try
    {
      paramObject = this._method.invoke(paramObject, new Object[0]);
      return paramObject;
    }
    catch (IllegalAccessException paramObject)
    {
      throw new IllegalArgumentException("Failed to getValue() with method " + getFullName() + ": " + ((IllegalAccessException)paramObject).getMessage(), (Throwable)paramObject);
    }
    catch (InvocationTargetException paramObject)
    {
      throw new IllegalArgumentException("Failed to getValue() with method " + getFullName() + ": " + ((InvocationTargetException)paramObject).getMessage(), (Throwable)paramObject);
    }
  }
  
  public boolean hasReturnType()
  {
    Class localClass = getRawReturnType();
    return (localClass != Void.TYPE) && (localClass != Void.class);
  }
  
  Object readResolve()
  {
    Class localClass = this._serialization.clazz;
    try
    {
      Object localObject = localClass.getDeclaredMethod(this._serialization.name, this._serialization.args);
      if (!((Method)localObject).isAccessible()) {
        ClassUtil.checkAndFixAccess((Member)localObject);
      }
      localObject = new AnnotatedMethod((Method)localObject, null, null);
      return localObject;
    }
    catch (Exception localException)
    {
      throw new IllegalArgumentException("Could not find method '" + this._serialization.name + "' from Class '" + localClass.getName());
    }
  }
  
  public void setValue(Object paramObject1, Object paramObject2)
    throws IllegalArgumentException
  {
    try
    {
      this._method.invoke(paramObject1, new Object[] { paramObject2 });
      return;
    }
    catch (IllegalAccessException paramObject1)
    {
      throw new IllegalArgumentException("Failed to setValue() with method " + getFullName() + ": " + ((IllegalAccessException)paramObject1).getMessage(), (Throwable)paramObject1);
    }
    catch (InvocationTargetException paramObject1)
    {
      throw new IllegalArgumentException("Failed to setValue() with method " + getFullName() + ": " + ((InvocationTargetException)paramObject1).getMessage(), (Throwable)paramObject1);
    }
  }
  
  public String toString()
  {
    return "[method " + getFullName() + "]";
  }
  
  public AnnotatedMethod withAnnotations(AnnotationMap paramAnnotationMap)
  {
    return new AnnotatedMethod(this._method, paramAnnotationMap, this._paramAnnotations);
  }
  
  public AnnotatedMethod withMethod(Method paramMethod)
  {
    return new AnnotatedMethod(paramMethod, this._annotations, this._paramAnnotations);
  }
  
  Object writeReplace()
  {
    return new AnnotatedMethod(new Serialization(this._method));
  }
  
  private static final class Serialization
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    protected Class<?>[] args;
    protected Class<?> clazz;
    protected String name;
    
    public Serialization(Method paramMethod)
    {
      this.clazz = paramMethod.getDeclaringClass();
      this.name = paramMethod.getName();
      this.args = paramMethod.getParameterTypes();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/introspect/AnnotatedMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */