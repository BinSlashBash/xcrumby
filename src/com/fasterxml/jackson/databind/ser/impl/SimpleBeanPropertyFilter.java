package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class SimpleBeanPropertyFilter
  implements BeanPropertyFilter, PropertyFilter
{
  public static SimpleBeanPropertyFilter filterOutAllExcept(Set<String> paramSet)
  {
    return new FilterExceptFilter(paramSet);
  }
  
  public static SimpleBeanPropertyFilter filterOutAllExcept(String... paramVarArgs)
  {
    HashSet localHashSet = new HashSet(paramVarArgs.length);
    Collections.addAll(localHashSet, paramVarArgs);
    return new FilterExceptFilter(localHashSet);
  }
  
  public static PropertyFilter from(BeanPropertyFilter paramBeanPropertyFilter)
  {
    new PropertyFilter()
    {
      public void depositSchemaProperty(PropertyWriter paramAnonymousPropertyWriter, JsonObjectFormatVisitor paramAnonymousJsonObjectFormatVisitor, SerializerProvider paramAnonymousSerializerProvider)
        throws JsonMappingException
      {
        this.val$src.depositSchemaProperty((BeanPropertyWriter)paramAnonymousPropertyWriter, paramAnonymousJsonObjectFormatVisitor, paramAnonymousSerializerProvider);
      }
      
      public void depositSchemaProperty(PropertyWriter paramAnonymousPropertyWriter, ObjectNode paramAnonymousObjectNode, SerializerProvider paramAnonymousSerializerProvider)
        throws JsonMappingException
      {
        this.val$src.depositSchemaProperty((BeanPropertyWriter)paramAnonymousPropertyWriter, paramAnonymousObjectNode, paramAnonymousSerializerProvider);
      }
      
      public void serializeAsElement(Object paramAnonymousObject, JsonGenerator paramAnonymousJsonGenerator, SerializerProvider paramAnonymousSerializerProvider, PropertyWriter paramAnonymousPropertyWriter)
        throws Exception
      {
        throw new UnsupportedOperationException();
      }
      
      public void serializeAsField(Object paramAnonymousObject, JsonGenerator paramAnonymousJsonGenerator, SerializerProvider paramAnonymousSerializerProvider, PropertyWriter paramAnonymousPropertyWriter)
        throws Exception
      {
        this.val$src.serializeAsField(paramAnonymousObject, paramAnonymousJsonGenerator, paramAnonymousSerializerProvider, (BeanPropertyWriter)paramAnonymousPropertyWriter);
      }
    };
  }
  
  public static SimpleBeanPropertyFilter serializeAllExcept(Set<String> paramSet)
  {
    return new SerializeExceptFilter(paramSet);
  }
  
  public static SimpleBeanPropertyFilter serializeAllExcept(String... paramVarArgs)
  {
    HashSet localHashSet = new HashSet(paramVarArgs.length);
    Collections.addAll(localHashSet, paramVarArgs);
    return new SerializeExceptFilter(localHashSet);
  }
  
  @Deprecated
  public void depositSchemaProperty(BeanPropertyWriter paramBeanPropertyWriter, JsonObjectFormatVisitor paramJsonObjectFormatVisitor, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    if (include(paramBeanPropertyWriter)) {
      paramBeanPropertyWriter.depositSchemaProperty(paramJsonObjectFormatVisitor);
    }
  }
  
  @Deprecated
  public void depositSchemaProperty(BeanPropertyWriter paramBeanPropertyWriter, ObjectNode paramObjectNode, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    if (include(paramBeanPropertyWriter)) {
      paramBeanPropertyWriter.depositSchemaProperty(paramObjectNode, paramSerializerProvider);
    }
  }
  
  public void depositSchemaProperty(PropertyWriter paramPropertyWriter, JsonObjectFormatVisitor paramJsonObjectFormatVisitor, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    if (include(paramPropertyWriter)) {
      paramPropertyWriter.depositSchemaProperty(paramJsonObjectFormatVisitor);
    }
  }
  
  @Deprecated
  public void depositSchemaProperty(PropertyWriter paramPropertyWriter, ObjectNode paramObjectNode, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    if (include(paramPropertyWriter)) {
      paramPropertyWriter.depositSchemaProperty(paramObjectNode, paramSerializerProvider);
    }
  }
  
  protected abstract boolean include(BeanPropertyWriter paramBeanPropertyWriter);
  
  protected abstract boolean include(PropertyWriter paramPropertyWriter);
  
  protected boolean includeElement(Object paramObject)
  {
    return true;
  }
  
  public void serializeAsElement(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, PropertyWriter paramPropertyWriter)
    throws Exception
  {
    if (includeElement(paramObject)) {
      paramPropertyWriter.serializeAsElement(paramObject, paramJsonGenerator, paramSerializerProvider);
    }
  }
  
  @Deprecated
  public void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, BeanPropertyWriter paramBeanPropertyWriter)
    throws Exception
  {
    if (include(paramBeanPropertyWriter)) {
      paramBeanPropertyWriter.serializeAsField(paramObject, paramJsonGenerator, paramSerializerProvider);
    }
    while (paramJsonGenerator.canOmitFields()) {
      return;
    }
    paramBeanPropertyWriter.serializeAsOmittedField(paramObject, paramJsonGenerator, paramSerializerProvider);
  }
  
  public void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, PropertyWriter paramPropertyWriter)
    throws Exception
  {
    if (include(paramPropertyWriter)) {
      paramPropertyWriter.serializeAsField(paramObject, paramJsonGenerator, paramSerializerProvider);
    }
    while (paramJsonGenerator.canOmitFields()) {
      return;
    }
    paramPropertyWriter.serializeAsOmittedField(paramObject, paramJsonGenerator, paramSerializerProvider);
  }
  
  public static class FilterExceptFilter
    extends SimpleBeanPropertyFilter
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    protected final Set<String> _propertiesToInclude;
    
    public FilterExceptFilter(Set<String> paramSet)
    {
      this._propertiesToInclude = paramSet;
    }
    
    protected boolean include(BeanPropertyWriter paramBeanPropertyWriter)
    {
      return this._propertiesToInclude.contains(paramBeanPropertyWriter.getName());
    }
    
    protected boolean include(PropertyWriter paramPropertyWriter)
    {
      return this._propertiesToInclude.contains(paramPropertyWriter.getName());
    }
  }
  
  public static class SerializeExceptFilter
    extends SimpleBeanPropertyFilter
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    protected final Set<String> _propertiesToExclude;
    
    public SerializeExceptFilter(Set<String> paramSet)
    {
      this._propertiesToExclude = paramSet;
    }
    
    protected boolean include(BeanPropertyWriter paramBeanPropertyWriter)
    {
      return !this._propertiesToExclude.contains(paramBeanPropertyWriter.getName());
    }
    
    protected boolean include(PropertyWriter paramPropertyWriter)
    {
      return !this._propertiesToExclude.contains(paramPropertyWriter.getName());
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/impl/SimpleBeanPropertyFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */