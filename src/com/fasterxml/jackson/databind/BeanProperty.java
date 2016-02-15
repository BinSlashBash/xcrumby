package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.Named;
import java.lang.annotation.Annotation;

public abstract interface BeanProperty
  extends Named
{
  public abstract void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor)
    throws JsonMappingException;
  
  public abstract <A extends Annotation> A getAnnotation(Class<A> paramClass);
  
  public abstract <A extends Annotation> A getContextAnnotation(Class<A> paramClass);
  
  public abstract PropertyName getFullName();
  
  public abstract AnnotatedMember getMember();
  
  public abstract PropertyMetadata getMetadata();
  
  public abstract String getName();
  
  public abstract JavaType getType();
  
  public abstract PropertyName getWrapperName();
  
  public abstract boolean isRequired();
  
  public static class Std
    implements BeanProperty
  {
    protected final Annotations _contextAnnotations;
    protected final AnnotatedMember _member;
    protected final PropertyMetadata _metadata;
    protected final PropertyName _name;
    protected final JavaType _type;
    protected final PropertyName _wrapperName;
    
    public Std(PropertyName paramPropertyName1, JavaType paramJavaType, PropertyName paramPropertyName2, Annotations paramAnnotations, AnnotatedMember paramAnnotatedMember, PropertyMetadata paramPropertyMetadata)
    {
      this._name = paramPropertyName1;
      this._type = paramJavaType;
      this._wrapperName = paramPropertyName2;
      this._metadata = paramPropertyMetadata;
      this._member = paramAnnotatedMember;
      this._contextAnnotations = paramAnnotations;
    }
    
    @Deprecated
    public Std(String paramString, JavaType paramJavaType, PropertyName paramPropertyName, Annotations paramAnnotations, AnnotatedMember paramAnnotatedMember, boolean paramBoolean) {}
    
    public void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor)
    {
      throw new UnsupportedOperationException("Instances of " + getClass().getName() + " should not get visited");
    }
    
    public <A extends Annotation> A getAnnotation(Class<A> paramClass)
    {
      if (this._member == null) {
        return null;
      }
      return this._member.getAnnotation(paramClass);
    }
    
    public <A extends Annotation> A getContextAnnotation(Class<A> paramClass)
    {
      if (this._contextAnnotations == null) {
        return null;
      }
      return this._contextAnnotations.get(paramClass);
    }
    
    public PropertyName getFullName()
    {
      return this._name;
    }
    
    public AnnotatedMember getMember()
    {
      return this._member;
    }
    
    public PropertyMetadata getMetadata()
    {
      return this._metadata;
    }
    
    public String getName()
    {
      return this._name.getSimpleName();
    }
    
    public JavaType getType()
    {
      return this._type;
    }
    
    public PropertyName getWrapperName()
    {
      return this._wrapperName;
    }
    
    public boolean isRequired()
    {
      return this._metadata.isRequired();
    }
    
    public Std withType(JavaType paramJavaType)
    {
      return new Std(this._name, paramJavaType, this._wrapperName, this._contextAnnotations, this._member, this._metadata);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/BeanProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */