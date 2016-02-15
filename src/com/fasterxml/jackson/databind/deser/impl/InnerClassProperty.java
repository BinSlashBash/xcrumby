package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public final class InnerClassProperty
  extends SettableBeanProperty
{
  private static final long serialVersionUID = 1L;
  protected final Constructor<?> _creator;
  protected final SettableBeanProperty _delegate;
  
  public InnerClassProperty(SettableBeanProperty paramSettableBeanProperty, Constructor<?> paramConstructor)
  {
    super(paramSettableBeanProperty);
    this._delegate = paramSettableBeanProperty;
    this._creator = paramConstructor;
  }
  
  protected InnerClassProperty(InnerClassProperty paramInnerClassProperty, JsonDeserializer<?> paramJsonDeserializer)
  {
    super(paramInnerClassProperty, paramJsonDeserializer);
    this._delegate = paramInnerClassProperty._delegate.withValueDeserializer(paramJsonDeserializer);
    this._creator = paramInnerClassProperty._creator;
  }
  
  protected InnerClassProperty(InnerClassProperty paramInnerClassProperty, PropertyName paramPropertyName)
  {
    super(paramInnerClassProperty, paramPropertyName);
    this._delegate = paramInnerClassProperty._delegate.withName(paramPropertyName);
    this._creator = paramInnerClassProperty._creator;
  }
  
  public void deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
      if (this._nullProvider == null) {
        paramJsonParser = null;
      }
    }
    for (;;)
    {
      set(paramObject, paramJsonParser);
      return;
      paramJsonParser = this._nullProvider.nullValue(paramDeserializationContext);
      continue;
      if (this._valueTypeDeserializer != null)
      {
        paramJsonParser = this._valueDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, this._valueTypeDeserializer);
        continue;
      }
      try
      {
        Object localObject1 = this._creator.newInstance(new Object[] { paramObject });
        this._valueDeserializer.deserialize(paramJsonParser, paramDeserializationContext, localObject1);
        paramJsonParser = (JsonParser)localObject1;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          ClassUtil.unwrapAndThrowAsIAE(localException, "Failed to instantiate class " + this._creator.getDeclaringClass().getName() + ", problem: " + localException.getMessage());
          Object localObject2 = null;
        }
      }
    }
  }
  
  public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    return setAndReturn(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
  }
  
  public <A extends Annotation> A getAnnotation(Class<A> paramClass)
  {
    return this._delegate.getAnnotation(paramClass);
  }
  
  public AnnotatedMember getMember()
  {
    return this._delegate.getMember();
  }
  
  public final void set(Object paramObject1, Object paramObject2)
    throws IOException
  {
    this._delegate.set(paramObject1, paramObject2);
  }
  
  public Object setAndReturn(Object paramObject1, Object paramObject2)
    throws IOException
  {
    return this._delegate.setAndReturn(paramObject1, paramObject2);
  }
  
  public InnerClassProperty withName(PropertyName paramPropertyName)
  {
    return new InnerClassProperty(this, paramPropertyName);
  }
  
  public InnerClassProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
  {
    return new InnerClassProperty(this, paramJsonDeserializer);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/impl/InnerClassProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */