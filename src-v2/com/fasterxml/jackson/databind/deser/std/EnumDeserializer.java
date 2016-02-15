/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

public class EnumDeserializer
extends StdScalarDeserializer<Enum<?>> {
    private static final long serialVersionUID = -5893263645879532318L;
    protected final EnumResolver<?> _resolver;

    public EnumDeserializer(EnumResolver<?> enumResolver) {
        super(Enum.class);
        this._resolver = enumResolver;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final Enum<?> _deserializeAltString(JsonParser object, DeserializationContext deserializationContext, String string2) throws IOException {
        String string3;
        string3 = string2.trim();
        if (string3.length() == 0) {
            if (deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
                return null;
            }
        } else {
            int n2 = string3.charAt(0);
            if (n2 >= 48 && n2 <= 57) {
                try {
                    Object obj;
                    n2 = Integer.parseInt(string3);
                    object = obj = this._resolver.getEnum(n2);
                    if (obj != null) return object;
                }
                catch (NumberFormatException var1_2) {}
            }
        }
        if (deserializationContext.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) return null;
        throw deserializationContext.weirdStringException(string3, this._resolver.getEnumClass(), "value not one of declared Enum instance names: " + this._resolver.getEnums());
    }

    private final Enum<?> _deserializeOther(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object object;
        if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            jsonParser.nextToken();
            object = this.deserialize(jsonParser, deserializationContext);
            if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                throw deserializationContext.wrongTokenException(jsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._resolver.getEnumClass().getName() + "' value but there was more than a single value in the array");
            }
        } else {
            throw deserializationContext.mappingException(this._resolver.getEnumClass());
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static JsonDeserializer<?> deserializerForCreator(DeserializationConfig deserializationConfig, Class<?> class_, AnnotatedMethod annotatedMethod) {
        void var3_5;
        Class class_2 = annotatedMethod.getRawParameterType(0);
        if (class_2 == String.class) {
            Object var3_4 = null;
        } else if (class_2 == Integer.TYPE || class_2 == Integer.class) {
            reference var3_6 = Integer.class;
        } else {
            if (class_2 != Long.TYPE && class_2 != Long.class) {
                throw new IllegalArgumentException("Parameter #0 type for factory method (" + annotatedMethod + ") not suitable, must be java.lang.String or int/Integer/long/Long");
            }
            reference var3_7 = Long.class;
        }
        if (!deserializationConfig.canOverrideAccessModifiers()) return new FactoryBasedDeserializer(class_, annotatedMethod, var3_5);
        ClassUtil.checkAndFixAccess(annotatedMethod.getMember());
        return new FactoryBasedDeserializer(class_, annotatedMethod, var3_5);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Enum<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object obj;
        void var2_3;
        Enum enum_ = jsonParser.getCurrentToken();
        if (enum_ == JsonToken.VALUE_STRING || enum_ == JsonToken.FIELD_NAME) {
            Object obj2;
            String string2 = jsonParser.getText();
            enum_ = obj2 = this._resolver.findEnum(string2);
            if (obj2 != null) return enum_;
            return this._deserializeAltString(jsonParser, (DeserializationContext)var2_3, string2);
        }
        if (enum_ != JsonToken.VALUE_NUMBER_INT) return this._deserializeOther(jsonParser, (DeserializationContext)var2_3);
        if (var2_3.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
            throw var2_3.mappingException("Not allowed to deserialize Enum value out of JSON number (disable DeserializationConfig.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS to allow)");
        }
        int n2 = jsonParser.getIntValue();
        enum_ = obj = this._resolver.getEnum(n2);
        if (obj != null) return enum_;
        enum_ = obj;
        if (var2_3.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) return enum_;
        throw var2_3.weirdNumberException(n2, this._resolver.getEnumClass(), "index value outside legal index range [0.." + this._resolver.lastValidIndex() + "]");
    }

    @Override
    public boolean isCachable() {
        return true;
    }

    protected static class FactoryBasedDeserializer
    extends StdScalarDeserializer<Object> {
        private static final long serialVersionUID = -7775129435872564122L;
        protected final Class<?> _enumClass;
        protected final Method _factory;
        protected final Class<?> _inputType;

        public FactoryBasedDeserializer(Class<?> class_, AnnotatedMethod annotatedMethod, Class<?> class_2) {
            super(Enum.class);
            this._enumClass = class_;
            this._factory = annotatedMethod.getAnnotated();
            this._inputType = class_2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Object deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (this._inputType == null) {
                object = object.getText();
            } else if (this._inputType == Integer.class) {
                object = object.getValueAsInt();
            } else {
                if (this._inputType != Long.class) {
                    throw deserializationContext.mappingException(this._enumClass);
                }
                object = object.getValueAsLong();
            }
            try {
                return this._factory.invoke(this._enumClass, object);
            }
            catch (Exception var1_2) {
                Throwable throwable = ClassUtil.getRootCause(var1_2);
                if (!(throwable instanceof IOException)) throw deserializationContext.instantiationException(this._enumClass, throwable);
                throw (IOException)throwable;
            }
        }
    }

}

