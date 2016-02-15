/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class SettableAnyProperty
implements Serializable {
    private static final long serialVersionUID = 1;
    protected final BeanProperty _property;
    protected final transient Method _setter;
    protected final JavaType _type;
    protected JsonDeserializer<Object> _valueDeserializer;
    protected final TypeDeserializer _valueTypeDeserializer;

    @Deprecated
    public SettableAnyProperty(BeanProperty beanProperty, AnnotatedMethod annotatedMethod, JavaType javaType, JsonDeserializer<Object> jsonDeserializer) {
        this(beanProperty, annotatedMethod, javaType, jsonDeserializer, null);
    }

    public SettableAnyProperty(BeanProperty beanProperty, AnnotatedMethod annotatedMethod, JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        this(beanProperty, (Method)annotatedMethod.getAnnotated(), javaType, jsonDeserializer, typeDeserializer);
    }

    @Deprecated
    public SettableAnyProperty(BeanProperty beanProperty, Method method, JavaType javaType, JsonDeserializer<Object> jsonDeserializer) {
        this(beanProperty, method, javaType, jsonDeserializer, null);
    }

    public SettableAnyProperty(BeanProperty beanProperty, Method method, JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        this._property = beanProperty;
        this._type = javaType;
        this._setter = method;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
    }

    private String getClassName() {
        return this._setter.getDeclaringClass().getName();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _throwAsIOE(Exception throwable, String charSequence, Object object) throws IOException {
        if (throwable instanceof IllegalArgumentException) {
            String string2;
            string2 = string2 == null ? "[NULL]" : string2.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder("Problem deserializing \"any\" property '").append((String)charSequence);
            stringBuilder.append("' of class " + this.getClassName() + " (expected type: ").append(this._type);
            stringBuilder.append("; actual type: ").append(string2).append(")");
            string2 = throwable.getMessage();
            if (string2 != null) {
                stringBuilder.append(", problem: ").append(string2);
                throw new JsonMappingException(stringBuilder.toString(), null, throwable);
            }
            stringBuilder.append(" (no error message provided)");
            throw new JsonMappingException(stringBuilder.toString(), null, throwable);
        }
        if (throwable instanceof IOException) {
            throw (IOException)throwable;
        }
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        throw new JsonMappingException(throwable.getMessage(), null, throwable);
    }

    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        if (this._valueTypeDeserializer != null) {
            return this._valueDeserializer.deserializeWithType(jsonParser, deserializationContext, this._valueTypeDeserializer);
        }
        return this._valueDeserializer.deserialize(jsonParser, deserializationContext);
    }

    public final void deserializeAndSet(JsonParser object, DeserializationContext deserializationContext, Object object2, String string2) throws IOException {
        try {
            this.set(object2, string2, this.deserialize((JsonParser)object, deserializationContext));
            return;
        }
        catch (UnresolvedForwardReference var2_3) {
            if (this._valueDeserializer.getObjectIdReader() == null) {
                throw JsonMappingException.from((JsonParser)object, "Unresolved forward reference but no identity info.", var2_3);
            }
            object = new AnySetterReferring(this, var2_3, this._type.getRawClass(), object2, string2);
            var2_3.getRoid().appendReferring((ReadableObjectId.Referring)object);
            return;
        }
    }

    public BeanProperty getProperty() {
        return this._property;
    }

    public JavaType getType() {
        return this._type;
    }

    public boolean hasValueDeserializer() {
        if (this._valueDeserializer != null) {
            return true;
        }
        return false;
    }

    public void set(Object object, String string2, Object object2) throws IOException {
        try {
            this._setter.invoke(object, string2, object2);
            return;
        }
        catch (Exception var1_2) {
            this._throwAsIOE(var1_2, string2, object2);
            return;
        }
    }

    public String toString() {
        return "[any property on class " + this.getClassName() + "]";
    }

    public SettableAnyProperty withValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
        return new SettableAnyProperty(this._property, this._setter, this._type, jsonDeserializer, this._valueTypeDeserializer);
    }

    private static class AnySetterReferring
    extends ReadableObjectId.Referring {
        private final SettableAnyProperty _parent;
        private final Object _pojo;
        private final String _propName;

        public AnySetterReferring(SettableAnyProperty settableAnyProperty, UnresolvedForwardReference unresolvedForwardReference, Class<?> class_, Object object, String string2) {
            super(unresolvedForwardReference, class_);
            this._parent = settableAnyProperty;
            this._pojo = object;
            this._propName = string2;
        }

        @Override
        public void handleResolvedForwardReference(Object object, Object object2) throws IOException {
            if (!this.hasId(object)) {
                throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + object.toString() + "] that wasn't previously registered.");
            }
            this._parent.set(this._pojo, this._propName, object2);
        }
    }

}

