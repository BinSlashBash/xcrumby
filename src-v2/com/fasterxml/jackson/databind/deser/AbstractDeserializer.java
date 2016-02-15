/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class AbstractDeserializer
extends JsonDeserializer<Object>
implements Serializable {
    private static final long serialVersionUID = -3010349050434697698L;
    protected final boolean _acceptBoolean;
    protected final boolean _acceptDouble;
    protected final boolean _acceptInt;
    protected final boolean _acceptString;
    protected final Map<String, SettableBeanProperty> _backRefProperties;
    protected final JavaType _baseType;
    protected final ObjectIdReader _objectIdReader;

    /*
     * Enabled aggressive block sorting
     */
    protected AbstractDeserializer(BeanDescription class_) {
        boolean bl2;
        block2 : {
            boolean bl3 = false;
            this._baseType = class_.getType();
            this._objectIdReader = null;
            this._backRefProperties = null;
            class_ = this._baseType.getRawClass();
            this._acceptString = class_.isAssignableFrom(String.class);
            bl2 = class_ == Boolean.TYPE || class_.isAssignableFrom(Boolean.class);
            this._acceptBoolean = bl2;
            bl2 = class_ == Integer.TYPE || class_.isAssignableFrom(Integer.class);
            this._acceptInt = bl2;
            if (class_ != Double.TYPE) {
                bl2 = bl3;
                if (!class_.isAssignableFrom(Double.class)) break block2;
            }
            bl2 = true;
        }
        this._acceptDouble = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public AbstractDeserializer(BeanDeserializerBuilder class_, BeanDescription beanDescription, Map<String, SettableBeanProperty> map) {
        boolean bl2;
        block2 : {
            boolean bl3 = false;
            this._baseType = beanDescription.getType();
            this._objectIdReader = class_.getObjectIdReader();
            this._backRefProperties = map;
            class_ = this._baseType.getRawClass();
            this._acceptString = class_.isAssignableFrom(String.class);
            bl2 = class_ == Boolean.TYPE || class_.isAssignableFrom(Boolean.class);
            this._acceptBoolean = bl2;
            bl2 = class_ == Integer.TYPE || class_.isAssignableFrom(Integer.class);
            this._acceptInt = bl2;
            if (class_ != Double.TYPE) {
                bl2 = bl3;
                if (!class_.isAssignableFrom(Double.class)) break block2;
            }
            bl2 = true;
        }
        this._acceptDouble = bl2;
    }

    public static AbstractDeserializer constructForNonPOJO(BeanDescription beanDescription) {
        return new AbstractDeserializer(beanDescription);
    }

    protected Object _deserializeFromObjectId(JsonParser object, DeserializationContext object2) throws IOException, JsonProcessingException {
        object = this._objectIdReader.readObjectReference((JsonParser)object, (DeserializationContext)object2);
        if ((object2 = object2.findObjectId(object, this._objectIdReader.generator, this._objectIdReader.resolver).resolve()) == null) {
            throw new IllegalStateException("Could not resolve Object Id [" + object + "] -- unresolved forward-reference?");
        }
        return object2;
    }

    protected Object _deserializeIfNatural(JsonParser jsonParser, DeserializationContext object) throws IOException, JsonProcessingException {
        object = jsonParser.getCurrentToken();
        if (object.isScalarValue()) {
            if (object == JsonToken.VALUE_STRING) {
                if (this._acceptString) {
                    return jsonParser.getText();
                }
            } else if (object == JsonToken.VALUE_NUMBER_INT) {
                if (this._acceptInt) {
                    return jsonParser.getIntValue();
                }
            } else if (object == JsonToken.VALUE_NUMBER_FLOAT) {
                if (this._acceptDouble) {
                    return jsonParser.getDoubleValue();
                }
            } else if (object == JsonToken.VALUE_TRUE) {
                if (this._acceptBoolean) {
                    return Boolean.TRUE;
                }
            } else if (object == JsonToken.VALUE_FALSE && this._acceptBoolean) {
                return Boolean.FALSE;
            }
        }
        return null;
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        throw deserializationContext.instantiationException(this._baseType.getRawClass(), "abstract types either need to be mapped to concrete types, have custom deserializer, or be instantiated with additional type information");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        Object object;
        Object object2;
        if (this._objectIdReader != null && (object = jsonParser.getCurrentToken()) != null && object.isScalarValue()) {
            return this._deserializeFromObjectId(jsonParser, deserializationContext);
        }
        object = object2 = this._deserializeIfNatural(jsonParser, deserializationContext);
        if (object2 != null) return object;
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    @Override
    public SettableBeanProperty findBackReference(String string2) {
        if (this._backRefProperties == null) {
            return null;
        }
        return this._backRefProperties.get(string2);
    }

    @Override
    public ObjectIdReader getObjectIdReader() {
        return this._objectIdReader;
    }

    @Override
    public Class<?> handledType() {
        return this._baseType.getRawClass();
    }

    @Override
    public boolean isCachable() {
        return true;
    }
}

