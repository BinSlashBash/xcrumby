package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

public final class AnnotatedField
  extends AnnotatedMember
  implements Serializable
{
  private static final long serialVersionUID = 7364428299211355871L;
  protected final transient Field _field;
  protected Serialization _serialization;
  
  protected AnnotatedField(Serialization paramSerialization)
  {
    super(null);
    this._field = null;
    this._serialization = paramSerialization;
  }
  
  public AnnotatedField(Field paramField, AnnotationMap paramAnnotationMap)
  {
    super(paramAnnotationMap);
    this._field = paramField;
  }
  
  public Field getAnnotated()
  {
    return this._field;
  }
  
  public <A extends Annotation> A getAnnotation(Class<A> paramClass)
  {
    if (this._annotations == null) {
      return null;
    }
    return this._annotations.get(paramClass);
  }
  
  public int getAnnotationCount()
  {
    return this._annotations.size();
  }
  
  public Class<?> getDeclaringClass()
  {
    return this._field.getDeclaringClass();
  }
  
  public String getFullName()
  {
    return getDeclaringClass().getName() + "#" + getName();
  }
  
  public Type getGenericType()
  {
    return this._field.getGenericType();
  }
  
  public Member getMember()
  {
    return this._field;
  }
  
  public int getModifiers()
  {
    return this._field.getModifiers();
  }
  
  public String getName()
  {
    return this._field.getName();
  }
  
  public Class<?> getRawType()
  {
    return this._field.getType();
  }
  
  public Object getValue(Object paramObject)
    throws IllegalArgumentException
  {
    try
    {
      paramObject = this._field.get(paramObject);
      return paramObject;
    }
    catch (IllegalAccessException paramObject)
    {
      throw new IllegalArgumentException("Failed to getValue() for field " + getFullName() + ": " + ((IllegalAccessException)paramObject).getMessage(), (Throwable)paramObject);
    }
  }
  
  Object readResolve()
  {
    Class localClass = this._serialization.clazz;
    try
    {
      Object localObject = localClass.getDeclaredField(this._serialization.name);
      if (!((Field)localObject).isAccessible()) {
        ClassUtil.checkAndFixAccess((Member)localObject);
      }
      localObject = new AnnotatedField((Field)localObject, null);
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
      this._field.set(paramObject1, paramObject2);
      return;
    }
    catch (IllegalAccessException paramObject1)
    {
      throw new IllegalArgumentException("Failed to setValue() for field " + getFullName() + ": " + ((IllegalAccessException)paramObject1).getMessage(), (Throwable)paramObject1);
    }
  }
  
  public String toString()
  {
    return "[field " + getFullName() + "]";
  }
  
  public AnnotatedField withAnnotations(AnnotationMap paramAnnotationMap)
  {
    return new AnnotatedField(this._field, paramAnnotationMap);
  }
  
  Object writeReplace()
  {
    return new AnnotatedField(new Serialization(this._field));
  }
  
  private static final class Serialization
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    protected Class<?> clazz;
    protected String name;
    
    public Serialization(Field paramField)
    {
      this.clazz = paramField.getDeclaringClass();
      this.name = paramField.getName();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/introspect/AnnotatedField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */