package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class ObjectIdReferenceProperty
  extends SettableBeanProperty
{
  private static final long serialVersionUID = 8465266677345565407L;
  private SettableBeanProperty _forward;
  
  public ObjectIdReferenceProperty(SettableBeanProperty paramSettableBeanProperty, ObjectIdInfo paramObjectIdInfo)
  {
    super(paramSettableBeanProperty);
    this._forward = paramSettableBeanProperty;
    this._objectIdInfo = paramObjectIdInfo;
  }
  
  public ObjectIdReferenceProperty(ObjectIdReferenceProperty paramObjectIdReferenceProperty, JsonDeserializer<?> paramJsonDeserializer)
  {
    super(paramObjectIdReferenceProperty, paramJsonDeserializer);
    this._forward = paramObjectIdReferenceProperty._forward;
    this._objectIdInfo = paramObjectIdReferenceProperty._objectIdInfo;
  }
  
  public ObjectIdReferenceProperty(ObjectIdReferenceProperty paramObjectIdReferenceProperty, PropertyName paramPropertyName)
  {
    super(paramObjectIdReferenceProperty, paramPropertyName);
    this._forward = paramObjectIdReferenceProperty._forward;
    this._objectIdInfo = paramObjectIdReferenceProperty._objectIdInfo;
  }
  
  public void deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    deserializeSetAndReturn(paramJsonParser, paramDeserializationContext, paramObject);
  }
  
  public Object deserializeSetAndReturn(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
    throws IOException, JsonProcessingException
  {
    if ((this._objectIdInfo != null) || (this._valueDeserializer.getObjectIdReader() != null)) {}
    for (int i = 1;; i = 0) {
      try
      {
        paramDeserializationContext = setAndReturn(paramObject, deserialize(paramJsonParser, paramDeserializationContext));
        return paramDeserializationContext;
      }
      catch (UnresolvedForwardReference paramDeserializationContext)
      {
        if (i != 0) {
          break;
        }
        throw JsonMappingException.from(paramJsonParser, "Unresolved forward reference but no identity info.", paramDeserializationContext);
        paramDeserializationContext.getRoid().appendReferring(new PropertyReferring(this, paramDeserializationContext, this._type.getRawClass(), paramObject));
      }
    }
    return null;
  }
  
  public <A extends Annotation> A getAnnotation(Class<A> paramClass)
  {
    return this._forward.getAnnotation(paramClass);
  }
  
  public AnnotatedMember getMember()
  {
    return this._forward.getMember();
  }
  
  public void set(Object paramObject1, Object paramObject2)
    throws IOException
  {
    this._forward.set(paramObject1, paramObject2);
  }
  
  public Object setAndReturn(Object paramObject1, Object paramObject2)
    throws IOException
  {
    return this._forward.setAndReturn(paramObject1, paramObject2);
  }
  
  public SettableBeanProperty withName(PropertyName paramPropertyName)
  {
    return new ObjectIdReferenceProperty(this, paramPropertyName);
  }
  
  public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> paramJsonDeserializer)
  {
    return new ObjectIdReferenceProperty(this, paramJsonDeserializer);
  }
  
  public static final class PropertyReferring
    extends ReadableObjectId.Referring
  {
    private final ObjectIdReferenceProperty _parent;
    public final Object _pojo;
    
    public PropertyReferring(ObjectIdReferenceProperty paramObjectIdReferenceProperty, UnresolvedForwardReference paramUnresolvedForwardReference, Class<?> paramClass, Object paramObject)
    {
      super(paramClass);
      this._parent = paramObjectIdReferenceProperty;
      this._pojo = paramObject;
    }
    
    public void handleResolvedForwardReference(Object paramObject1, Object paramObject2)
      throws IOException
    {
      if (!hasId(paramObject1)) {
        throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + paramObject1 + "] that wasn't previously seen as unresolved.");
      }
      this._parent.set(this._pojo, paramObject2);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/impl/ObjectIdReferenceProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */