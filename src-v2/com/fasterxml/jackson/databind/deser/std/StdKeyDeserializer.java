/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@JacksonStdImpl
public class StdKeyDeserializer
extends KeyDeserializer
implements Serializable {
    public static final int TYPE_BOOLEAN = 1;
    public static final int TYPE_BYTE = 2;
    public static final int TYPE_CALENDAR = 11;
    public static final int TYPE_CHAR = 4;
    public static final int TYPE_DATE = 10;
    public static final int TYPE_DOUBLE = 8;
    public static final int TYPE_FLOAT = 7;
    public static final int TYPE_INT = 5;
    public static final int TYPE_LOCALE = 9;
    public static final int TYPE_LONG = 6;
    public static final int TYPE_SHORT = 3;
    public static final int TYPE_UUID = 12;
    private static final long serialVersionUID = 1;
    protected final FromStringDeserializer<?> _deser;
    protected final Class<?> _keyClass;
    protected final int _kind;

    protected StdKeyDeserializer(int n2, Class<?> class_) {
        this(n2, class_, null);
    }

    protected StdKeyDeserializer(int n2, Class<?> class_, FromStringDeserializer<?> fromStringDeserializer) {
        this._kind = n2;
        this._keyClass = class_;
        this._deser = fromStringDeserializer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static StdKeyDeserializer forType(Class<?> class_) {
        int n2;
        if (class_ == String.class || class_ == Object.class) {
            return StringKD.forType(class_);
        }
        if (class_ == UUID.class) {
            n2 = 12;
            do {
                return new StdKeyDeserializer(n2, class_);
                break;
            } while (true);
        }
        if (class_ == Integer.class) {
            n2 = 5;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ == Long.class) {
            n2 = 6;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ == Date.class) {
            n2 = 10;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ == Calendar.class) {
            n2 = 11;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ == Boolean.class) {
            n2 = 1;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ == Byte.class) {
            n2 = 2;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ == Character.class) {
            n2 = 4;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ == Short.class) {
            n2 = 3;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ == Float.class) {
            n2 = 7;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ == Double.class) {
            n2 = 8;
            return new StdKeyDeserializer(n2, class_);
        }
        if (class_ != Locale.class) return null;
        return new StdKeyDeserializer(9, class_, FromStringDeserializer.findDeserializer(Locale.class));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Object _parse(String object, DeserializationContext deserializationContext) throws Exception {
        switch (this._kind) {
            default: {
                return null;
            }
            case 1: {
                if ("true".equals(object)) {
                    return Boolean.TRUE;
                }
                if (!"false".equals(object)) throw deserializationContext.weirdKeyException(this._keyClass, (String)object, "value not 'true' or 'false'");
                return Boolean.FALSE;
            }
            case 2: {
                int n2 = this._parseInt((String)object);
                if (n2 < -128) throw deserializationContext.weirdKeyException(this._keyClass, (String)object, "overflow, value can not be represented as 8-bit value");
                if (n2 <= 255) return Byte.valueOf((byte)n2);
                throw deserializationContext.weirdKeyException(this._keyClass, (String)object, "overflow, value can not be represented as 8-bit value");
            }
            case 3: {
                int n3 = this._parseInt((String)object);
                if (n3 < -32768) throw deserializationContext.weirdKeyException(this._keyClass, (String)object, "overflow, value can not be represented as 16-bit value");
                if (n3 <= 32767) return (short)n3;
                throw deserializationContext.weirdKeyException(this._keyClass, (String)object, "overflow, value can not be represented as 16-bit value");
            }
            case 4: {
                if (object.length() != 1) throw deserializationContext.weirdKeyException(this._keyClass, (String)object, "can only convert 1-character Strings");
                return Character.valueOf(object.charAt(0));
            }
            case 5: {
                return this._parseInt((String)object);
            }
            case 6: {
                return this._parseLong((String)object);
            }
            case 7: {
                return Float.valueOf((float)this._parseDouble((String)object));
            }
            case 8: {
                return this._parseDouble((String)object);
            }
            case 9: {
                try {
                    return this._deser._deserialize((String)object, deserializationContext);
                }
                catch (IOException var4_6) {
                    throw deserializationContext.weirdKeyException(this._keyClass, (String)object, "unable to parse key as locale");
                }
            }
            case 10: {
                return deserializationContext.parseDate((String)object);
            }
            case 11: {
                if ((object = deserializationContext.parseDate((String)object)) == null) return null;
                return deserializationContext.constructCalendar((Date)object);
            }
            case 12: 
        }
        return UUID.fromString((String)object);
    }

    protected double _parseDouble(String string2) throws IllegalArgumentException {
        return NumberInput.parseDouble(string2);
    }

    protected int _parseInt(String string2) throws IllegalArgumentException {
        return Integer.parseInt(string2);
    }

    protected long _parseLong(String string2) throws IllegalArgumentException {
        return Long.parseLong(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object deserializeKey(String string2, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (string2 == null) {
            return null;
        }
        try {
            Object object;
            Object object2 = object = this._parse(string2, deserializationContext);
            if (object != null) return object2;
            if (!this._keyClass.isEnum()) throw deserializationContext.weirdKeyException(this._keyClass, string2, "not a valid representation");
            if (!deserializationContext.getConfig().isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) throw deserializationContext.weirdKeyException(this._keyClass, string2, "not a valid representation");
            return null;
        }
        catch (Exception var3_4) {
            throw deserializationContext.weirdKeyException(this._keyClass, string2, "not a valid representation: " + var3_4.getMessage());
        }
    }

    public Class<?> getKeyClass() {
        return this._keyClass;
    }

    static final class DelegatingKD
    extends KeyDeserializer
    implements Serializable {
        private static final long serialVersionUID = 1;
        protected final JsonDeserializer<?> _delegate;
        protected final Class<?> _keyClass;

        protected DelegatingKD(Class<?> class_, JsonDeserializer<?> jsonDeserializer) {
            this._keyClass = class_;
            this._delegate = jsonDeserializer;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public final Object deserializeKey(String string2, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Object obj;
            if (string2 == null) {
                return null;
            }
            try {
                obj = this._delegate.deserialize(deserializationContext.getParser(), deserializationContext);
            }
            catch (Exception var3_4) {
                throw deserializationContext.weirdKeyException(this._keyClass, string2, "not a valid representation: " + var3_4.getMessage());
            }
            Object var3_3 = obj;
            if (obj != null) return var3_3;
            throw deserializationContext.weirdKeyException(this._keyClass, string2, "not a valid representation");
        }

        public Class<?> getKeyClass() {
            return this._keyClass;
        }
    }

    @JacksonStdImpl
    static final class EnumKD
    extends StdKeyDeserializer {
        private static final long serialVersionUID = 1;
        protected final AnnotatedMethod _factory;
        protected final EnumResolver<?> _resolver;

        protected EnumKD(EnumResolver<?> enumResolver, AnnotatedMethod annotatedMethod) {
            super(-1, enumResolver.getEnumClass());
            this._resolver = enumResolver;
            this._factory = annotatedMethod;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Object _parse(String string2, DeserializationContext deserializationContext) throws JsonMappingException {
            Object obj;
            if (this._factory != null) {
                try {
                    return this._factory.call1(string2);
                }
                catch (Exception var3_4) {
                    ClassUtil.unwrapAndThrowAsIAE(var3_4);
                }
            }
            Object object = obj = this._resolver.findEnum(string2);
            if (obj != null) return object;
            object = obj;
            if (deserializationContext.getConfig().isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) return object;
            throw deserializationContext.weirdKeyException(this._keyClass, string2, "not one of values for Enum class");
        }
    }

    static final class StringCtorKeyDeserializer
    extends StdKeyDeserializer {
        private static final long serialVersionUID = 1;
        protected final Constructor<?> _ctor;

        public StringCtorKeyDeserializer(Constructor<?> constructor) {
            super(-1, constructor.getDeclaringClass());
            this._ctor = constructor;
        }

        @Override
        public Object _parse(String string2, DeserializationContext deserializationContext) throws Exception {
            return this._ctor.newInstance(string2);
        }
    }

    static final class StringFactoryKeyDeserializer
    extends StdKeyDeserializer {
        private static final long serialVersionUID = 1;
        final Method _factoryMethod;

        public StringFactoryKeyDeserializer(Method method) {
            super(-1, method.getDeclaringClass());
            this._factoryMethod = method;
        }

        @Override
        public Object _parse(String string2, DeserializationContext deserializationContext) throws Exception {
            return this._factoryMethod.invoke(null, string2);
        }
    }

    @JacksonStdImpl
    static final class StringKD
    extends StdKeyDeserializer {
        private static final StringKD sObject;
        private static final StringKD sString;
        private static final long serialVersionUID = 1;

        static {
            sString = new StringKD(String.class);
            sObject = new StringKD(Object.class);
        }

        private StringKD(Class<?> class_) {
            super(-1, class_);
        }

        public static StringKD forType(Class<?> class_) {
            if (class_ == String.class) {
                return sString;
            }
            if (class_ == Object.class) {
                return sObject;
            }
            return new StringKD(class_);
        }

        @Override
        public Object deserializeKey(String string2, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return string2;
        }
    }

}

