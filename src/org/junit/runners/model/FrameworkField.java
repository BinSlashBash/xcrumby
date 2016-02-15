package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FrameworkField
  extends FrameworkMember<FrameworkField>
{
  private final Field fField;
  
  FrameworkField(Field paramField)
  {
    this.fField = paramField;
  }
  
  public Object get(Object paramObject)
    throws IllegalArgumentException, IllegalAccessException
  {
    return this.fField.get(paramObject);
  }
  
  public Annotation[] getAnnotations()
  {
    return this.fField.getAnnotations();
  }
  
  public Field getField()
  {
    return this.fField;
  }
  
  public String getName()
  {
    return getField().getName();
  }
  
  public Class<?> getType()
  {
    return this.fField.getType();
  }
  
  public boolean isPublic()
  {
    return Modifier.isPublic(this.fField.getModifiers());
  }
  
  public boolean isShadowedBy(FrameworkField paramFrameworkField)
  {
    return paramFrameworkField.getName().equals(getName());
  }
  
  public boolean isStatic()
  {
    return Modifier.isStatic(this.fField.getModifiers());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/model/FrameworkField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */