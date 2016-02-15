/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsonschema;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface JsonSerializableSchema {
    public static final String NO_VALUE = "##irrelevant";

    public String id() default "";

    @Deprecated
    public String schemaItemDefinition() default "##irrelevant";

    @Deprecated
    public String schemaObjectPropertiesDefinition() default "##irrelevant";

    public String schemaType() default "any";
}

