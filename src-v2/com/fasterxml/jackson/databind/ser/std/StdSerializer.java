/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public abstract class StdSerializer<T>
extends JsonSerializer<T>
implements JsonFormatVisitable,
SchemaAware {
    protected final Class<T> _handledType;

    protected StdSerializer(JavaType javaType) {
        this._handledType = javaType.getRawClass();
    }

    protected StdSerializer(Class<T> class_) {
        this._handledType = class_;
    }

    protected StdSerializer(Class<?> class_, boolean bl2) {
        this._handledType = class_;
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        jsonFormatVisitorWrapper.expectAnyFormat(javaType);
    }

    protected ObjectNode createObjectNode() {
        return JsonNodeFactory.instance.objectNode();
    }

    protected ObjectNode createSchemaNode(String string2) {
        ObjectNode objectNode = this.createObjectNode();
        objectNode.put("type", string2);
        return objectNode;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected ObjectNode createSchemaNode(String object, boolean bl2) {
        object = this.createSchemaNode((String)object);
        if (!bl2) {
            bl2 = !bl2;
            object.put("required", bl2);
        }
        return object;
    }

    protected JsonSerializer<?> findConvertingContentSerializer(SerializerProvider serializerProvider, BeanProperty beanProperty, JsonSerializer<?> jsonSerializer) throws JsonMappingException {
        Object object = serializerProvider.getAnnotationIntrospector();
        if (object != null && beanProperty != null && (object = object.findSerializationContentConverter(beanProperty.getMember())) != null) {
            Converter<Object, Object> converter = serializerProvider.converterInstance(beanProperty.getMember(), object);
            JavaType javaType = converter.getOutputType(serializerProvider.getTypeFactory());
            object = jsonSerializer;
            if (jsonSerializer == null) {
                object = serializerProvider.findValueSerializer(javaType, beanProperty);
            }
            return new StdDelegatingSerializer(converter, javaType, object);
        }
        return jsonSerializer;
    }

    protected PropertyFilter findPropertyFilter(SerializerProvider object, Object object2, Object object3) throws JsonMappingException {
        if ((object = object.getFilterProvider()) == null) {
            throw new JsonMappingException("Can not resolve PropertyFilter with id '" + object2 + "'; no FilterProvider configured");
        }
        return object.findPropertyFilter(object2, object3);
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        return this.createSchemaNode("string");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonNode getSchema(SerializerProvider object, Type type, boolean bl2) throws JsonMappingException {
        object = (ObjectNode)this.getSchema((SerializerProvider)object, type);
        if (!bl2) {
            bl2 = !bl2;
            object.put("required", bl2);
        }
        return object;
    }

    @Override
    public Class<T> handledType() {
        return this._handledType;
    }

    protected boolean isDefaultSerializer(JsonSerializer<?> jsonSerializer) {
        return ClassUtil.isJacksonStdImpl(jsonSerializer);
    }

    @Override
    public abstract void serialize(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException;

    /*
     * Enabled aggressive block sorting
     */
    public void wrapAndThrow(SerializerProvider serializerProvider, Throwable throwable, Object object, int n2) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        boolean bl2 = serializerProvider == null || serializerProvider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS);
        if (throwable instanceof IOException) {
            if (bl2 && throwable instanceof JsonMappingException) throw JsonMappingException.wrapWithPath(throwable, object, n2);
            {
                throw (IOException)throwable;
            }
        } else {
            if (!(throwable instanceof RuntimeException)) throw JsonMappingException.wrapWithPath(throwable, object, n2);
            {
                throw (RuntimeException)throwable;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void wrapAndThrow(SerializerProvider serializerProvider, Throwable throwable, Object object, String string2) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        boolean bl2 = serializerProvider == null || serializerProvider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS);
        if (throwable instanceof IOException) {
            if (bl2 && throwable instanceof JsonMappingException) throw JsonMappingException.wrapWithPath(throwable, object, string2);
            {
                throw (IOException)throwable;
            }
        } else {
            if (!(throwable instanceof RuntimeException)) throw JsonMappingException.wrapWithPath(throwable, object, string2);
            {
                throw (RuntimeException)throwable;
            }
        }
    }
}

