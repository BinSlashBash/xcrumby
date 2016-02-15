/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface JsonProperty {
    public static final int INDEX_UNKNOWN = -1;
    public static final String USE_DEFAULT_NAME = "";

    public int index() default -1;

    public boolean required() default 0;

    public String value() default "";
}

