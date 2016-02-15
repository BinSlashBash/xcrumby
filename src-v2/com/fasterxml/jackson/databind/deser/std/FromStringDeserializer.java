/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public abstract class FromStringDeserializer<T>
extends StdScalarDeserializer<T> {
    protected FromStringDeserializer(Class<?> class_) {
        super(class_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Std findDeserializer(Class<?> class_) {
        int n2;
        if (class_ == File.class) {
            n2 = 1;
            do {
                return new Std(class_, n2);
                break;
            } while (true);
        }
        if (class_ == URL.class) {
            n2 = 2;
            return new Std(class_, n2);
        }
        if (class_ == URI.class) {
            n2 = 3;
            return new Std(class_, n2);
        }
        if (class_ == Class.class) {
            n2 = 4;
            return new Std(class_, n2);
        }
        if (class_ == JavaType.class) {
            n2 = 5;
            return new Std(class_, n2);
        }
        if (class_ == Currency.class) {
            n2 = 6;
            return new Std(class_, n2);
        }
        if (class_ == Pattern.class) {
            n2 = 7;
            return new Std(class_, n2);
        }
        if (class_ == Locale.class) {
            n2 = 8;
            return new Std(class_, n2);
        }
        if (class_ == Charset.class) {
            n2 = 9;
            return new Std(class_, n2);
        }
        if (class_ == TimeZone.class) {
            n2 = 10;
            return new Std(class_, n2);
        }
        if (class_ == InetAddress.class) {
            n2 = 11;
            return new Std(class_, n2);
        }
        if (class_ != InetSocketAddress.class) return null;
        n2 = 12;
        return new Std(class_, n2);
    }

    public static Class<?>[] types() {
        return new Class[]{File.class, URL.class, URI.class, Class.class, JavaType.class, Currency.class, Pattern.class, Locale.class, Charset.class, TimeZone.class, InetAddress.class, InetSocketAddress.class};
    }

    protected abstract T _deserialize(String var1, DeserializationContext var2) throws IOException;

    protected T _deserializeEmbedded(Object object, DeserializationContext deserializationContext) throws IOException {
        throw deserializationContext.mappingException("Don't know how to convert embedded Object of type " + object.getClass().getName() + " into " + this._valueClass.getName());
    }

    protected T _deserializeFromEmptyString() throws IOException {
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public T deserialize(JsonParser var1_1, DeserializationContext var2_3) throws IOException {
        if (var1_1.getCurrentToken() == JsonToken.START_ARRAY && var2_3.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            var1_1.nextToken();
            var3_5 = this.deserialize((JsonParser)var1_1, var2_3);
            if (var1_1.nextToken() == JsonToken.END_ARRAY) return var3_8;
            throw var2_3.wrongTokenException((JsonParser)var1_1, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._valueClass.getName() + "' value but there was more than a single value in the array");
        }
        var3_6 = var1_1.getValueAsString();
        if (var3_6 != null) {
            if (var3_6.length() == 0 || (var4_14 = var3_6.trim()).length() == 0) {
                var3_7 = this._deserializeFromEmptyString();
                return var3_8;
            }
            var1_1 = null;
            try {
                var3_9 = this._deserialize(var4_14, var2_3);
                ** if (var3_9 == null) goto lbl-1000
            }
            catch (IllegalArgumentException var1_2) {
                // empty catch block
            }
lbl15: // 1 sources:
            return var3_9;
lbl-1000: // 1 sources:
            {
            }
            var3_11 = "not a valid textual representation";
            if (var1_1 != null) {
                var3_12 = "not a valid textual representation" + "problem: " + var1_1.getMessage();
            }
            var2_4 = var2_3.weirdStringException(var4_14, this._valueClass, (String)var3_13);
            if (var1_1 == null) throw var2_4;
            var2_4.initCause((Throwable)var1_1);
            throw var2_4;
        }
        if (var1_1.getCurrentToken() != JsonToken.VALUE_EMBEDDED_OBJECT) throw var2_3.mappingException(this._valueClass);
        if ((var1_1 = var1_1.getEmbeddedObject()) == null) {
            return null;
        }
        if (this._valueClass.isAssignableFrom(var1_1.getClass()) == false) return this._deserializeEmbedded(var1_1, var2_3);
        return (T)var1_1;
    }

    public static class Std
    extends FromStringDeserializer<Object> {
        public static final int STD_CHARSET = 9;
        public static final int STD_CLASS = 4;
        public static final int STD_CURRENCY = 6;
        public static final int STD_FILE = 1;
        public static final int STD_INET_ADDRESS = 11;
        public static final int STD_INET_SOCKET_ADDRESS = 12;
        public static final int STD_JAVA_TYPE = 5;
        public static final int STD_LOCALE = 8;
        public static final int STD_PATTERN = 7;
        public static final int STD_TIME_ZONE = 10;
        public static final int STD_URI = 3;
        public static final int STD_URL = 2;
        private static final long serialVersionUID = 1;
        protected final int _kind;

        protected Std(Class<?> class_, int n2) {
            super(class_);
            this._kind = n2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected Object _deserialize(String object, DeserializationContext object2) throws IOException {
            switch (this._kind) {
                default: {
                    throw new IllegalArgumentException();
                }
                case 1: {
                    return new File((String)object);
                }
                case 2: {
                    return new URL((String)object);
                }
                case 3: {
                    return URI.create((String)object);
                }
                case 4: {
                    try {
                        return object2.findClass((String)object);
                    }
                    catch (Exception var1_2) {
                        throw object2.instantiationException(this._valueClass, ClassUtil.getRootCause(var1_2));
                    }
                }
                case 5: {
                    return object2.getTypeFactory().constructFromCanonical((String)object);
                }
                case 6: {
                    return Currency.getInstance((String)object);
                }
                case 7: {
                    return Pattern.compile((String)object);
                }
                case 8: {
                    int n2 = object.indexOf(95);
                    if (n2 < 0) {
                        return new Locale((String)object);
                    }
                    object2 = object.substring(0, n2);
                    object = object.substring(n2 + 1);
                    n2 = object.indexOf(95);
                    if (n2 >= 0) return new Locale((String)object2, object.substring(0, n2), object.substring(n2 + 1));
                    return new Locale((String)object2, (String)object);
                }
                case 9: {
                    return Charset.forName((String)object);
                }
                case 10: {
                    return TimeZone.getTimeZone((String)object);
                }
                case 11: {
                    return InetAddress.getByName((String)object);
                }
                case 12: 
            }
            if (object.startsWith("[")) {
                int n3 = object.lastIndexOf(93);
                if (n3 == -1) {
                    throw new InvalidFormatException("Bracketed IPv6 address must contain closing bracket", object, InetSocketAddress.class);
                }
                int n4 = object.indexOf(58, n3);
                if (n4 > -1) {
                    n4 = Integer.parseInt(object.substring(n4 + 1));
                    do {
                        return new InetSocketAddress(object.substring(0, n3 + 1), n4);
                        break;
                    } while (true);
                }
                n4 = 0;
                return new InetSocketAddress(object.substring(0, n3 + 1), n4);
            }
            int n5 = object.indexOf(58);
            if (n5 < 0) return new InetSocketAddress((String)object, 0);
            if (object.indexOf(58, n5 + 1) >= 0) return new InetSocketAddress((String)object, 0);
            int n6 = Integer.parseInt(object.substring(n5 + 1));
            return new InetSocketAddress(object.substring(0, n5), n6);
        }

        @Override
        protected Object _deserializeFromEmptyString() throws IOException {
            if (this._kind == 3) {
                return URI.create("");
            }
            return super._deserializeFromEmptyString();
        }
    }

}

