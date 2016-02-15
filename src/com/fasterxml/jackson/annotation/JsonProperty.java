package com.fasterxml.jackson.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER})
public @interface JsonProperty
{
  public static final int INDEX_UNKNOWN = -1;
  public static final String USE_DEFAULT_NAME = "";
  
  int index() default -1;
  
  boolean required() default false;
  
  String value() default "";
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/annotation/JsonProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */