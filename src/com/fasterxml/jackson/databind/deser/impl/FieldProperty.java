package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public final class FieldProperty
  extends SettableBeanProperty
{
  private static final long serialVersionUID = 1L;
  protected final AnnotatedField _annotated;
  protected final transient Field _field;
  
  protected FieldProperty(FieldProperty paramFieldProperty, JsonDeserializer<?> paramJsonDeserializer)
  {
    super(paramFieldProperty, paramJsonDeserializer);
    this._annotated = paramFieldProperty._annotated;
    this._field = paramFieldProperty._field;
  }
  
  protected FieldProperty(FieldProperty paramFieldProperty, PropertyName paramPropertyName)
  {
    super(paramFieldProperty, paramPropertyName);
    this._annotated = paramFieldProperty._annotated;
    this._field = paramFieldProperty._field;
  }
  
  protected FieldProperty(FieldProperty paramFieldProperty, Field paramField)
  {
    super(paramFieldProperty);
    this._annotated = paramFieldProperty._annotated;
    if (paramField == null) {
      throw new IllegalArgumentException("No Field passed for property '" + paramFieldProperty.getName() + "' (class " + paramFieldProperty.getDeclaringClass().getName() + ")");
    }
    this._field = paramField;
  }
  
  public FieldProperty(BeanPropertyDefinition paramBeanPropertyDefinition, JavaType paramJavaType, TypeDeserializer paramTypeDeserializer, Annotations paramAnnotations, AnnotatedField paramAnnotatedField)
  {
    super(paramBeanPropertyDefinition, paramJavaType, paramTypeDeserializer, paramAnnotations);
    this._annotated = paramAnnotatedField;
    this._field = paramAnnotatedField.getAnnotated();
  }
  
  public void deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    set(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
  }
  
  public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    return setAndReturn(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
  }
  
  public <A extends Annotation> A getAnnotation(Class<A> paramClass)
  {
    return this._annotated.getAnnotation(paramClass);
  }
  
  public AnnotatedMember getMember()
  {
    return this._annotated;
  }
  
  Object readResolve()
  {
    return new FieldProperty(this, this._annotated.getAnnotated());
  }
  
  public final void set(Object paramObject1, Object paramObject2)
    throws IOException
  {
    try
    {
      this._field.set(paramObject1, paramObject2);
      return;
    }
    catch (Exception paramObject1)
    {
      _throwAsIOE((Exception)paramObject1, paramObject2);
    }
  }
  
  public Object setAndReturn(Object paramObject1, Object paramObject2)
    throws IOException
  {
    try
    {
      this._field.set(paramObject1, paramObject2);
      return paramObject1;
    }
    catch (Exception localException)
    {
      _throwAsIOE(localException, paramObject2);
    }
    return paramObject1;
  }
  
  public FieldProperty withName(PropertyName paramPropertyName)
  {
    return new FieldProperty(this, paramPropertyName);
  }
  
  public FieldProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
  {
    return new FieldProperty(this, paramJsonDeserializer);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/impl/FieldProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */