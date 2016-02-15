/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

@JacksonStdImpl
public class JsonValueSerializer
extends StdSerializer<Object>
implements ContextualSerializer,
JsonFormatVisitable,
SchemaAware {
    protected final Method _accessorMethod;
    protected final boolean _forceTypeInformation;
    protected final BeanProperty _property;
    protected final JsonSerializer<Object> _valueSerializer;

    public JsonValueSerializer(JsonValueSerializer jsonValueSerializer, BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, boolean bl2) {
        super(JsonValueSerializer._notNullClass(jsonValueSerializer.handledType()));
        this._accessorMethod = jsonValueSerializer._accessorMethod;
        this._valueSerializer = jsonSerializer;
        this._property = beanProperty;
        this._forceTypeInformation = bl2;
    }

    public JsonValueSerializer(Method method, JsonSerializer<Object> jsonSerializer) {
        super(Object.class);
        this._accessorMethod = method;
        this._valueSerializer = jsonSerializer;
        this._property = null;
        this._forceTypeInformation = true;
    }

    private static final Class<Object> _notNullClass(Class<?> var0) {
        void var1_3;
        reference var1_1 = var0;
        if (var0 == null) {
            reference var1_2 = Object.class;
        }
        return var1_3;
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType object) throws JsonMappingException {
        Object object2;
        Object object3 = object2 = this._valueSerializer;
        if (object2 == null) {
            object2 = object;
            if (object == null) {
                if (this._property != null) {
                    object = this._property.getType();
                }
                object2 = object;
                if (object == null) {
                    object2 = jsonFormatVisitorWrapper.getProvider().constructType(this._accessorMethod.getReturnType());
                }
            }
            object3 = object = jsonFormatVisitorWrapper.getProvider().findTypedValueSerializer((JavaType)object2, false, this._property);
            if (object == null) {
                jsonFormatVisitorWrapper.expectAnyFormat((JavaType)object2);
                return;
            }
        }
        object3.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider jsonSerializer, BeanProperty beanProperty) throws JsonMappingException {
        Object object = this._valueSerializer;
        if (object != null) return this.withResolved(beanProperty, jsonSerializer.handlePrimaryContextualization(object, beanProperty), this._forceTypeInformation);
        if (!jsonSerializer.isEnabled(MapperFeature.USE_STATIC_TYPING)) {
            object = this;
            if (!Modifier.isFinal(this._accessorMethod.getReturnType().getModifiers())) return object;
        }
        object = jsonSerializer.constructType(this._accessorMethod.getGenericReturnType());
        jsonSerializer = jsonSerializer.findPrimaryPropertySerializer((JavaType)object, this._property);
        return this.withResolved(beanProperty, jsonSerializer, this.isNaturalTypeWithStdHandling(object.getRawClass(), jsonSerializer));
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        if (this._valueSerializer instanceof SchemaAware) {
            return ((SchemaAware)((Object)this._valueSerializer)).getSchema(serializerProvider, null);
        }
        return JsonSchema.getDefaultSchemaNode();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean isNaturalTypeWithStdHandling(Class<?> class_, JsonSerializer<?> jsonSerializer) {
        if (class_.isPrimitive() ? class_ != Integer.TYPE && class_ != Boolean.TYPE && class_ != Double.TYPE : class_ != String.class && class_ != Integer.class && class_ != Boolean.class && class_ != Double.class) {
            return false;
        }
        return this.isDefaultSerializer(jsonSerializer);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        try {
            JsonSerializer<Object> jsonSerializer;
            Object object2 = this._accessorMethod.invoke(object, new Object[0]);
            if (object2 == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                return;
            }
            JsonSerializer<Object> jsonSerializer2 = jsonSerializer = this._valueSerializer;
            if (jsonSerializer == null) {
                jsonSerializer2 = serializerProvider.findTypedValueSerializer(object2.getClass(), true, this._property);
            }
            jsonSerializer2.serialize(object2, jsonGenerator, serializerProvider);
            return;
        }
        catch (IOException var1_2) {
            throw var1_2;
        }
        catch (Exception var2_4) {
            Throwable throwable;
            while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
                throwable = throwable.getCause();
            }
            if (throwable instanceof Error) {
                throw (Error)throwable;
            }
            throw JsonMappingException.wrapWithPath(throwable, object, this._accessorMethod.getName() + "()");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        try {
            JsonSerializer<Object> jsonSerializer;
            Object object2;
            block9 : {
                object2 = this._accessorMethod.invoke(object, new Object[0]);
                if (object2 == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                    return;
                }
                JsonSerializer<Object> jsonSerializer2 = this._valueSerializer;
                if (jsonSerializer2 == null) {
                    jsonSerializer = serializerProvider.findValueSerializer(object2.getClass(), this._property);
                } else {
                    jsonSerializer = jsonSerializer2;
                    if (!this._forceTypeInformation) break block9;
                    typeSerializer.writeTypePrefixForScalar(object, jsonGenerator);
                    jsonSerializer2.serialize(object2, jsonGenerator, serializerProvider);
                    typeSerializer.writeTypeSuffixForScalar(object, jsonGenerator);
                    return;
                }
            }
            jsonSerializer.serializeWithType(object2, jsonGenerator, serializerProvider, typeSerializer);
            return;
        }
        catch (IOException var1_2) {
            throw var1_2;
        }
        catch (Exception var2_4) {
            Throwable throwable;
            while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
                throwable = throwable.getCause();
            }
            if (throwable instanceof Error) {
                throw (Error)throwable;
            }
            throw JsonMappingException.wrapWithPath(throwable, object, this._accessorMethod.getName() + "()");
        }
    }

    public String toString() {
        return "(@JsonValue serializer for method " + this._accessorMethod.getDeclaringClass() + "#" + this._accessorMethod.getName() + ")";
    }

    public JsonValueSerializer withResolved(BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, boolean bl2) {
        if (this._property == beanProperty && this._valueSerializer == jsonSerializer && bl2 == this._forceTypeInformation) {
            return this;
        }
        return new JsonValueSerializer(this, beanProperty, jsonSerializer, bl2);
    }
}

