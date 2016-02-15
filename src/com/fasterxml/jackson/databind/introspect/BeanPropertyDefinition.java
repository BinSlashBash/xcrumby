package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.util.Named;

public abstract class BeanPropertyDefinition
  implements Named
{
  public boolean couldDeserialize()
  {
    return getMutator() != null;
  }
  
  public boolean couldSerialize()
  {
    return getAccessor() != null;
  }
  
  public ObjectIdInfo findObjectIdInfo()
  {
    return null;
  }
  
  public AnnotationIntrospector.ReferenceProperty findReferenceType()
  {
    return null;
  }
  
  public Class<?>[] findViews()
  {
    return null;
  }
  
  public abstract AnnotatedMember getAccessor();
  
  public abstract AnnotatedParameter getConstructorParameter();
  
  public abstract AnnotatedField getField();
  
  public abstract PropertyName getFullName();
  
  public abstract AnnotatedMethod getGetter();
  
  public abstract String getInternalName();
  
  public abstract PropertyMetadata getMetadata();
  
  public abstract AnnotatedMember getMutator();
  
  public abstract String getName();
  
  public abstract AnnotatedMember getNonConstructorMutator();
  
  public AnnotatedMember getPrimaryMember()
  {
    return null;
  }
  
  public abstract AnnotatedMethod getSetter();
  
  public abstract PropertyName getWrapperName();
  
  public abstract boolean hasConstructorParameter();
  
  public abstract boolean hasField();
  
  public abstract boolean hasGetter();
  
  public abstract boolean hasSetter();
  
  public abstract boolean isExplicitlyIncluded();
  
  public boolean isExplicitlyNamed()
  {
    return isExplicitlyIncluded();
  }
  
  public final boolean isRequired()
  {
    PropertyMetadata localPropertyMetadata = getMetadata();
    return (localPropertyMetadata != null) && (localPropertyMetadata.isRequired());
  }
  
  public boolean isTypeId()
  {
    return false;
  }
  
  public abstract BeanPropertyDefinition withName(PropertyName paramPropertyName);
  
  @Deprecated
  public BeanPropertyDefinition withName(String paramString)
  {
    return withSimpleName(paramString);
  }
  
  public abstract BeanPropertyDefinition withSimpleName(String paramString);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/introspect/BeanPropertyDefinition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */