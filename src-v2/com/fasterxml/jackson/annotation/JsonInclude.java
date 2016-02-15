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
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
public @interface JsonInclude {
    public Include value() default Include.ALWAYS;

    public static enum Include {
        ALWAYS,
        NON_NULL,
        NON_DEFAULT,
        NON_EMPTY;
        

        private Include() {
        }
    }

}

