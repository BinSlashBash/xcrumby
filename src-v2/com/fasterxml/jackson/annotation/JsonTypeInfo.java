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
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface JsonTypeInfo {
    public Class<?> defaultImpl() default None.class;

    public As include() default As.PROPERTY;

    public String property() default "";

    public Id use();

    public boolean visible() default 0;

    public static enum As {
        PROPERTY,
        WRAPPER_OBJECT,
        WRAPPER_ARRAY,
        EXTERNAL_PROPERTY,
        EXISTING_PROPERTY;
        

        private As() {
        }
    }

    public static enum Id {
        NONE(null),
        CLASS("@class"),
        MINIMAL_CLASS("@c"),
        NAME("@type"),
        CUSTOM(null);
        
        private final String _defaultPropertyName;

        private Id(String string3) {
            this._defaultPropertyName = string3;
        }

        public String getDefaultPropertyName() {
            return this._defaultPropertyName;
        }
    }

    @Deprecated
    public static abstract class None {
    }

}

