package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.IOException;
import java.lang.reflect.Method;

public class EnumDeserializer extends StdScalarDeserializer<Enum<?>> {
    private static final long serialVersionUID = -5893263645879532318L;
    protected final EnumResolver<?> _resolver;

    protected static class FactoryBasedDeserializer extends StdScalarDeserializer<Object> {
        private static final long serialVersionUID = -7775129435872564122L;
        protected final Class<?> _enumClass;
        protected final Method _factory;
        protected final Class<?> _inputType;

        public FactoryBasedDeserializer(Class<?> cls, AnnotatedMethod f, Class<?> inputType) {
            super(Enum.class);
            this._enumClass = cls;
            this._factory = f.getAnnotated();
            this._inputType = inputType;
        }

        public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String text;
            if (this._inputType == null) {
                text = jp.getText();
            } else if (this._inputType == Integer.class) {
                text = Integer.valueOf(jp.getValueAsInt());
            } else if (this._inputType == Long.class) {
                text = Long.valueOf(jp.getValueAsLong());
            } else {
                throw ctxt.mappingException(this._enumClass);
            }
            try {
                return this._factory.invoke(this._enumClass, new Object[]{text});
            } catch (Exception e) {
                Throwable t = ClassUtil.getRootCause(e);
                if (t instanceof IOException) {
                    throw ((IOException) t);
                }
                throw ctxt.instantiationException(this._enumClass, t);
            }
        }
    }

    public EnumDeserializer(EnumResolver<?> res) {
        super(Enum.class);
        this._resolver = res;
    }

    public static JsonDeserializer<?> deserializerForCreator(DeserializationConfig config, Class<?> enumClass, AnnotatedMethod factory) {
        Class<?> paramClass = factory.getRawParameterType(0);
        if (paramClass == String.class) {
            paramClass = null;
        } else if (paramClass == Integer.TYPE || paramClass == Integer.class) {
            paramClass = Integer.class;
        } else if (paramClass == Long.TYPE || paramClass == Long.class) {
            paramClass = Long.class;
        } else {
            throw new IllegalArgumentException("Parameter #0 type for factory method (" + factory + ") not suitable, must be java.lang.String or int/Integer/long/Long");
        }
        if (config.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(factory.getMember());
        }
        return new FactoryBasedDeserializer(enumClass, factory, paramClass);
    }

    public boolean isCachable() {
        return true;
    }

    public Enum<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken curr = jp.getCurrentToken();
        Enum<?> result;
        if (curr == JsonToken.VALUE_STRING || curr == JsonToken.FIELD_NAME) {
            String name = jp.getText();
            result = this._resolver.findEnum(name);
            if (result == null) {
                return _deserializeAltString(jp, ctxt, name);
            }
            return result;
        } else if (curr != JsonToken.VALUE_NUMBER_INT) {
            return _deserializeOther(jp, ctxt);
        } else {
            if (ctxt.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
                throw ctxt.mappingException("Not allowed to deserialize Enum value out of JSON number (disable DeserializationConfig.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS to allow)");
            }
            int index = jp.getIntValue();
            result = this._resolver.getEnum(index);
            if (result != null || ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
                return result;
            }
            throw ctxt.weirdNumberException(Integer.valueOf(index), this._resolver.getEnumClass(), "index value outside legal index range [0.." + this._resolver.lastValidIndex() + "]");
        }
    }

    private final Enum<?> _deserializeAltString(JsonParser jp, DeserializationContext ctxt, String name) throws IOException {
        name = name.trim();
        if (name.length() != 0) {
            char c = name.charAt(0);
            if (c >= '0' && c <= '9') {
                try {
                    Enum<?> result = this._resolver.getEnum(Integer.parseInt(name));
                    if (result != null) {
                        return result;
                    }
                } catch (NumberFormatException e) {
                }
            }
        } else if (ctxt.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
            return null;
        }
        if (ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
            return null;
        }
        throw ctxt.weirdStringException(name, this._resolver.getEnumClass(), "value not one of declared Enum instance names: " + this._resolver.getEnums());
    }

    private final Enum<?> _deserializeOther(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getCurrentToken() == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            jp.nextToken();
            Enum<?> parsed = deserialize(jp, ctxt);
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                return parsed;
            }
            throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._resolver.getEnumClass().getName() + "' value but there was more than a single value in the array");
        }
        throw ctxt.mappingException(this._resolver.getEnumClass());
    }
}
