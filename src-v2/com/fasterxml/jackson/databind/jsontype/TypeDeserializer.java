/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;

public abstract class TypeDeserializer {
    public static Object deserializeIfNatural(JsonParser jsonParser, DeserializationContext deserializationContext, JavaType javaType) throws IOException {
        return TypeDeserializer.deserializeIfNatural(jsonParser, deserializationContext, javaType.getRawClass());
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static Object deserializeIfNatural(JsonParser jsonParser, DeserializationContext object, Class<?> class_) throws IOException {
        object = jsonParser.getCurrentToken();
        if (object == null) {
            return null;
        }
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[object.ordinal()]) {
            default: {
                return null;
            }
            case 1: {
                if (!class_.isAssignableFrom(String.class)) return null;
                return jsonParser.getText();
            }
            case 2: {
                if (!class_.isAssignableFrom(Integer.class)) return null;
                return jsonParser.getIntValue();
            }
            case 3: {
                if (!class_.isAssignableFrom(Double.class)) return null;
                return jsonParser.getDoubleValue();
            }
            case 4: {
                if (!class_.isAssignableFrom(Boolean.class)) return null;
                return Boolean.TRUE;
            }
            case 5: 
        }
        if (!class_.isAssignableFrom(Boolean.class)) return null;
        return Boolean.FALSE;
    }

    public abstract Object deserializeTypedFromAny(JsonParser var1, DeserializationContext var2) throws IOException;

    public abstract Object deserializeTypedFromArray(JsonParser var1, DeserializationContext var2) throws IOException;

    public abstract Object deserializeTypedFromObject(JsonParser var1, DeserializationContext var2) throws IOException;

    public abstract Object deserializeTypedFromScalar(JsonParser var1, DeserializationContext var2) throws IOException;

    public abstract TypeDeserializer forProperty(BeanProperty var1);

    public abstract Class<?> getDefaultImpl();

    public abstract String getPropertyName();

    public abstract TypeIdResolver getTypeIdResolver();

    public abstract JsonTypeInfo.As getTypeInclusion();

}

