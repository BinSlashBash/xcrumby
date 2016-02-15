/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public abstract class TypeBase
extends JavaType
implements JsonSerializable {
    private static final long serialVersionUID = -3581199092426900829L;
    volatile transient String _canonicalName;

    @Deprecated
    protected TypeBase(Class<?> class_, int n2, Object object, Object object2) {
        this(class_, n2, object, object2, false);
    }

    protected TypeBase(Class<?> class_, int n2, Object object, Object object2, boolean bl2) {
        super(class_, n2, object, object2, bl2);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected static StringBuilder _classSignature(Class<?> object, StringBuilder stringBuilder, boolean bl2) {
        if (object.isPrimitive()) {
            if (object == Boolean.TYPE) {
                stringBuilder.append('Z');
                return stringBuilder;
            }
            if (object == Byte.TYPE) {
                stringBuilder.append('B');
                return stringBuilder;
            }
            if (object == Short.TYPE) {
                stringBuilder.append('S');
                return stringBuilder;
            }
            if (object == Character.TYPE) {
                stringBuilder.append('C');
                return stringBuilder;
            }
            if (object == Integer.TYPE) {
                stringBuilder.append('I');
                return stringBuilder;
            }
            if (object == Long.TYPE) {
                stringBuilder.append('J');
                return stringBuilder;
            }
            if (object == Float.TYPE) {
                stringBuilder.append('F');
                return stringBuilder;
            }
            if (object == Double.TYPE) {
                stringBuilder.append('D');
                return stringBuilder;
            }
            if (object != Void.TYPE) throw new IllegalStateException("Unrecognized primitive type: " + object.getName());
            stringBuilder.append('V');
            return stringBuilder;
        }
        stringBuilder.append('L');
        object = object.getName();
        int n2 = 0;
        int n3 = object.length();
        do {
            char c2;
            if (n2 >= n3) {
                if (!bl2) return stringBuilder;
                stringBuilder.append(';');
                return stringBuilder;
            }
            char c3 = c2 = object.charAt(n2);
            if (c2 == '.') {
                c3 = '/';
            }
            stringBuilder.append(c3);
            ++n2;
        } while (true);
    }

    protected abstract String buildCanonicalName();

    @Override
    public abstract StringBuilder getErasedSignature(StringBuilder var1);

    @Override
    public abstract StringBuilder getGenericSignature(StringBuilder var1);

    @Override
    public <T> T getTypeHandler() {
        return (T)this._typeHandler;
    }

    @Override
    public <T> T getValueHandler() {
        return (T)this._valueHandler;
    }

    @Override
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(this.toCanonical());
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        typeSerializer.writeTypePrefixForScalar(this, jsonGenerator);
        this.serialize(jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar(this, jsonGenerator);
    }

    @Override
    public String toCanonical() {
        String string2;
        String string3 = string2 = this._canonicalName;
        if (string2 == null) {
            string3 = this.buildCanonicalName();
        }
        return string3;
    }
}

