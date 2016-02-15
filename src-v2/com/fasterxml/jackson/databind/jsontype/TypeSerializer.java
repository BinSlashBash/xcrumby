/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;

public abstract class TypeSerializer {
    public abstract TypeSerializer forProperty(BeanProperty var1);

    public abstract String getPropertyName();

    public abstract TypeIdResolver getTypeIdResolver();

    public abstract JsonTypeInfo.As getTypeInclusion();

    public abstract void writeCustomTypePrefixForArray(Object var1, JsonGenerator var2, String var3) throws IOException;

    public abstract void writeCustomTypePrefixForObject(Object var1, JsonGenerator var2, String var3) throws IOException;

    public abstract void writeCustomTypePrefixForScalar(Object var1, JsonGenerator var2, String var3) throws IOException, JsonProcessingException;

    public abstract void writeCustomTypeSuffixForArray(Object var1, JsonGenerator var2, String var3) throws IOException;

    public abstract void writeCustomTypeSuffixForObject(Object var1, JsonGenerator var2, String var3) throws IOException;

    public abstract void writeCustomTypeSuffixForScalar(Object var1, JsonGenerator var2, String var3) throws IOException;

    public abstract void writeTypePrefixForArray(Object var1, JsonGenerator var2) throws IOException;

    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        this.writeTypePrefixForArray(object, jsonGenerator);
    }

    public abstract void writeTypePrefixForObject(Object var1, JsonGenerator var2) throws IOException;

    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        this.writeTypePrefixForObject(object, jsonGenerator);
    }

    public abstract void writeTypePrefixForScalar(Object var1, JsonGenerator var2) throws IOException;

    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        this.writeTypePrefixForScalar(object, jsonGenerator);
    }

    public abstract void writeTypeSuffixForArray(Object var1, JsonGenerator var2) throws IOException;

    public abstract void writeTypeSuffixForObject(Object var1, JsonGenerator var2) throws IOException;

    public abstract void writeTypeSuffixForScalar(Object var1, JsonGenerator var2) throws IOException;
}

