package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;

public class StdDelegatingDeserializer<T>
  extends StdDeserializer<T>
  implements ContextualDeserializer, ResolvableDeserializer
{
  private static final long serialVersionUID = 1L;
  protected final Converter<Object, T> _converter;
  protected final JsonDeserializer<Object> _delegateDeserializer;
  protected final JavaType _delegateType;
  
  public StdDelegatingDeserializer(Converter<?, T> paramConverter)
  {
    super(Object.class);
    this._converter = paramConverter;
    this._delegateType = null;
    this._delegateDeserializer = null;
  }
  
  public StdDelegatingDeserializer(Converter<Object, T> paramConverter, JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer)
  {
    super(paramJavaType);
    this._converter = paramConverter;
    this._delegateType = paramJavaType;
    this._delegateDeserializer = paramJsonDeserializer;
  }
  
  protected T convertValue(Object paramObject)
  {
    return (T)this._converter.convert(paramObject);
  }
  
  public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    if (this._delegateDeserializer != null)
    {
      paramBeanProperty = paramDeserializationContext.handleSecondaryContextualization(this._delegateDeserializer, paramBeanProperty);
      paramDeserializationContext = this;
      if (paramBeanProperty != this._delegateDeserializer) {
        paramDeserializationContext = withDelegate(this._converter, this._delegateType, paramBeanProperty);
      }
      return paramDeserializationContext;
    }
    JavaType localJavaType = this._converter.getInputType(paramDeserializationContext.getTypeFactory());
    return withDelegate(this._converter, localJavaType, paramDeserializationContext.findContextualValueDeserializer(localJavaType, paramBeanProperty));
  }
  
  public T deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException, JsonProcessingException
  {
    paramJsonParser = this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
    if (paramJsonParser == null) {
      return null;
    }
    return (T)convertValue(paramJsonParser);
  }
  
  public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
    throws IOException, JsonProcessingException
  {
    paramJsonParser = this._delegateDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, paramTypeDeserializer);
    if (paramJsonParser == null) {
      return null;
    }
    return convertValue(paramJsonParser);
  }
  
  public JsonDeserializer<?> getDelegatee()
  {
    return this._delegateDeserializer;
  }
  
  public Class<?> handledType()
  {
    return this._delegateDeserializer.handledType();
  }
  
  public void resolve(DeserializationContext paramDeserializationContext)
    throws JsonMappingException
  {
    if ((this._delegateDeserializer != null) && ((this._delegateDeserializer instanceof ResolvableDeserializer))) {
      ((ResolvableDeserializer)this._delegateDeserializer).resolve(paramDeserializationContext);
    }
  }
  
  protected StdDelegatingDeserializer<T> withDelegate(Converter<Object, T> paramConverter, JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer)
  {
    if (getClass() != StdDelegatingDeserializer.class) {
      throw new IllegalStateException("Sub-class " + getClass().getName() + " must override 'withDelegate'");
    }
    return new StdDelegatingDeserializer(paramConverter, paramJavaType, paramJsonDeserializer);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/StdDelegatingDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */