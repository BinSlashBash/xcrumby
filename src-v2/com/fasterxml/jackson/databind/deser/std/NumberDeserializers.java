/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

public class NumberDeserializers {
    private static final HashSet<String> _classNames = new HashSet();

    static {
        for (Class class_ : new Class[]{Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class, Number.class, BigDecimal.class, BigInteger.class}) {
            _classNames.add(class_.getName());
        }
    }

    public static JsonDeserializer<?> find(Class<?> class_, String string2) {
        if (class_.isPrimitive()) {
            if (class_ == Integer.TYPE) {
                return primitiveInstance;
            }
            if (class_ == Boolean.TYPE) {
                return primitiveInstance;
            }
            if (class_ == Long.TYPE) {
                return primitiveInstance;
            }
            if (class_ == Double.TYPE) {
                return primitiveInstance;
            }
            if (class_ == Character.TYPE) {
                return primitiveInstance;
            }
            if (class_ == Byte.TYPE) {
                return primitiveInstance;
            }
            if (class_ == Short.TYPE) {
                return primitiveInstance;
            }
            if (class_ == Float.TYPE) {
                return primitiveInstance;
            }
        } else if (_classNames.contains(string2)) {
            if (class_ == Integer.class) {
                return wrapperInstance;
            }
            if (class_ == Boolean.class) {
                return wrapperInstance;
            }
            if (class_ == Long.class) {
                return wrapperInstance;
            }
            if (class_ == Double.class) {
                return wrapperInstance;
            }
            if (class_ == Character.class) {
                return wrapperInstance;
            }
            if (class_ == Byte.class) {
                return wrapperInstance;
            }
            if (class_ == Short.class) {
                return wrapperInstance;
            }
            if (class_ == Float.class) {
                return wrapperInstance;
            }
            if (class_ == Number.class) {
                return NumberDeserializer.instance;
            }
            if (class_ == BigDecimal.class) {
                return BigDecimalDeserializer.instance;
            }
            if (class_ == BigInteger.class) {
                return BigIntegerDeserializer.instance;
            }
        } else {
            return null;
        }
        throw new IllegalArgumentException("Internal error: can't find deserializer for " + class_.getName());
    }

    @JacksonStdImpl
    public static class BigDecimalDeserializer
    extends StdScalarDeserializer<BigDecimal> {
        public static final BigDecimalDeserializer instance = new BigDecimalDeserializer();

        public BigDecimalDeserializer() {
            super(BigDecimal.class);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public BigDecimal deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Object object2 = object.getCurrentToken();
            if (object2 == JsonToken.VALUE_NUMBER_INT) return object.getDecimalValue();
            if (object2 == JsonToken.VALUE_NUMBER_FLOAT) {
                return object.getDecimalValue();
            }
            if (object2 == JsonToken.VALUE_STRING) {
                if ((object = object.getText().trim()).length() == 0) {
                    return null;
                }
                try {
                    return new BigDecimal((String)object);
                }
                catch (IllegalArgumentException var3_4) {
                    throw deserializationContext.weirdStringException((String)object, this._valueClass, "not a valid representation");
                }
            }
            if (object2 != JsonToken.START_ARRAY) throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)((Object)object2)));
            if (!deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)((Object)object2)));
            object.nextToken();
            object2 = this.deserialize((JsonParser)object, deserializationContext);
            if (object.nextToken() == JsonToken.END_ARRAY) return object2;
            throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'BigDecimal' value but there was more than a single value in the array");
        }
    }

    @JacksonStdImpl
    public static class BigIntegerDeserializer
    extends StdScalarDeserializer<BigInteger> {
        public static final BigIntegerDeserializer instance = new BigIntegerDeserializer();

        public BigIntegerDeserializer() {
            super(BigInteger.class);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public BigInteger deserialize(JsonParser var1_1, DeserializationContext var2_2) throws IOException, JsonProcessingException {
            var3_3 = var1_1.getCurrentToken();
            if (var3_3 != JsonToken.VALUE_NUMBER_INT) ** GOTO lbl8
            switch (.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[var1_1.getNumberType().ordinal()]) {
                default: {
                    ** GOTO lbl17
                }
                case 1: 
                case 2: {
                    return BigInteger.valueOf(var1_1.getLongValue());
                }
            }
lbl8: // 1 sources:
            if (var3_3 == JsonToken.VALUE_NUMBER_FLOAT) {
                return var1_1.getDecimalValue().toBigInteger();
            }
            if (var3_3 == JsonToken.START_ARRAY && var2_2.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                var1_1.nextToken();
                var3_3 = this.deserialize((JsonParser)var1_1, var2_2);
                if (var1_1.nextToken() == JsonToken.END_ARRAY) return var3_3;
                throw var2_2.wrongTokenException((JsonParser)var1_1, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'BigInteger' value but there was more than a single value in the array");
            }
            if (var3_3 != JsonToken.VALUE_STRING) {
                throw var2_2.mappingException(this._valueClass, (JsonToken)var3_3);
            }
lbl17: // 3 sources:
            if ((var1_1 = var1_1.getText().trim()).length() == 0) {
                return null;
            }
            try {
                return new BigInteger((String)var1_1);
            }
            catch (IllegalArgumentException var3_4) {
                throw var2_2.weirdStringException((String)var1_1, this._valueClass, "not a valid representation");
            }
        }
    }

    @JacksonStdImpl
    public static final class BooleanDeserializer
    extends PrimitiveOrWrapperDeserializer<Boolean> {
        private static final BooleanDeserializer primitiveInstance = new BooleanDeserializer(Boolean.class, Boolean.FALSE);
        private static final long serialVersionUID = 1;
        private static final BooleanDeserializer wrapperInstance = new BooleanDeserializer(Boolean.TYPE, null);

        public BooleanDeserializer(Class<Boolean> class_, Boolean bl2) {
            super(class_, bl2);
        }

        @Override
        public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseBoolean(jsonParser, deserializationContext);
        }

        @Override
        public Boolean deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            return this._parseBoolean(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static class ByteDeserializer
    extends PrimitiveOrWrapperDeserializer<Byte> {
        private static final ByteDeserializer primitiveInstance = new ByteDeserializer(Byte.TYPE, Byte.valueOf(0));
        private static final long serialVersionUID = 1;
        private static final ByteDeserializer wrapperInstance = new ByteDeserializer(Byte.class, null);

        public ByteDeserializer(Class<Byte> class_, Byte by2) {
            super(class_, by2);
        }

        @Override
        public Byte deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseByte(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static class CharacterDeserializer
    extends PrimitiveOrWrapperDeserializer<Character> {
        private static final CharacterDeserializer primitiveInstance = new CharacterDeserializer(Character.class, Character.valueOf('\u0000'));
        private static final long serialVersionUID = 1;
        private static final CharacterDeserializer wrapperInstance = new CharacterDeserializer(Character.TYPE, null);

        public CharacterDeserializer(Class<Character> class_, Character c2) {
            super(class_, c2);
        }

        @Override
        public Character deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Object object2 = object.getCurrentToken();
            if (object2 == JsonToken.VALUE_NUMBER_INT) {
                int n2 = object.getIntValue();
                if (n2 >= 0 && n2 <= 65535) {
                    return Character.valueOf((char)n2);
                }
            } else if (object2 == JsonToken.VALUE_STRING) {
                if ((object = object.getText()).length() == 1) {
                    return Character.valueOf(object.charAt(0));
                }
                if (object.length() == 0) {
                    return (Character)this.getEmptyValue();
                }
            } else if (object2 == JsonToken.START_ARRAY && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                object.nextToken();
                object2 = this.deserialize((JsonParser)object, deserializationContext);
                if (object.nextToken() != JsonToken.END_ARRAY) {
                    throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._valueClass.getName() + "' value but there was more than a single value in the array");
                }
                return object2;
            }
            throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)object2));
        }
    }

    @JacksonStdImpl
    public static class DoubleDeserializer
    extends PrimitiveOrWrapperDeserializer<Double> {
        private static final DoubleDeserializer primitiveInstance = new DoubleDeserializer(Double.class, 0.0);
        private static final long serialVersionUID = 1;
        private static final DoubleDeserializer wrapperInstance = new DoubleDeserializer(Double.TYPE, null);

        public DoubleDeserializer(Class<Double> class_, Double d2) {
            super(class_, d2);
        }

        @Override
        public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseDouble(jsonParser, deserializationContext);
        }

        @Override
        public Double deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            return this._parseDouble(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static class FloatDeserializer
    extends PrimitiveOrWrapperDeserializer<Float> {
        private static final FloatDeserializer primitiveInstance = new FloatDeserializer(Float.class, Float.valueOf(0.0f));
        private static final long serialVersionUID = 1;
        private static final FloatDeserializer wrapperInstance = new FloatDeserializer(Float.TYPE, null);

        public FloatDeserializer(Class<Float> class_, Float f2) {
            super(class_, f2);
        }

        @Override
        public Float deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseFloat(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static final class IntegerDeserializer
    extends PrimitiveOrWrapperDeserializer<Integer> {
        private static final IntegerDeserializer primitiveInstance = new IntegerDeserializer(Integer.class, 0);
        private static final long serialVersionUID = 1;
        private static final IntegerDeserializer wrapperInstance = new IntegerDeserializer(Integer.TYPE, null);

        public IntegerDeserializer(Class<Integer> class_, Integer n2) {
            super(class_, n2);
        }

        @Override
        public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseInteger(jsonParser, deserializationContext);
        }

        @Override
        public Integer deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            return this._parseInteger(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static final class LongDeserializer
    extends PrimitiveOrWrapperDeserializer<Long> {
        private static final LongDeserializer primitiveInstance = new LongDeserializer(Long.class, 0);
        private static final long serialVersionUID = 1;
        private static final LongDeserializer wrapperInstance = new LongDeserializer(Long.TYPE, null);

        public LongDeserializer(Class<Long> class_, Long l2) {
            super(class_, l2);
        }

        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseLong(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static class NumberDeserializer
    extends StdScalarDeserializer<Number> {
        public static final NumberDeserializer instance = new NumberDeserializer();

        public NumberDeserializer() {
            super(Number.class);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Number deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Object object2 = object.getCurrentToken();
            if (object2 == JsonToken.VALUE_NUMBER_INT) {
                if (!deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) return object.getNumberValue();
                return object.getBigIntegerValue();
            }
            if (object2 == JsonToken.VALUE_NUMBER_FLOAT) {
                if (!deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) return object.getDoubleValue();
                return object.getDecimalValue();
            }
            if (object2 == JsonToken.VALUE_STRING) {
                if ((object = object.getText().trim()).length() == 0) {
                    return (Number)this.getEmptyValue();
                }
                if (this._hasTextualNull((String)object)) {
                    return (Number)this.getNullValue();
                }
                if (this._isPosInf((String)object)) {
                    return Double.POSITIVE_INFINITY;
                }
                if (this._isNegInf((String)object)) {
                    return Double.NEGATIVE_INFINITY;
                }
                if (this._isNaN((String)object)) {
                    return Double.NaN;
                }
                try {
                    if (object.indexOf(46) >= 0) {
                        if (!deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) return new Double((String)object);
                        return new BigDecimal((String)object);
                    }
                }
                catch (IllegalArgumentException var5_4) {
                    throw deserializationContext.weirdStringException((String)object, this._valueClass, "not a valid number");
                }
                if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                    return new BigInteger((String)object);
                }
                long l2 = Long.parseLong((String)object);
                if (l2 > Integer.MAX_VALUE) return l2;
                if (l2 < Integer.MIN_VALUE) return l2;
                return (int)l2;
            }
            if (object2 != JsonToken.START_ARRAY) throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)((Object)object2)));
            if (!deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)((Object)object2)));
            object.nextToken();
            object2 = this.deserialize((JsonParser)object, deserializationContext);
            if (object.nextToken() == JsonToken.END_ARRAY) return object2;
            throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._valueClass.getName() + "' value but there was more than a single value in the array");
        }

        @Override
        public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
                default: {
                    return typeDeserializer.deserializeTypedFromScalar(jsonParser, deserializationContext);
                }
                case 1: 
                case 2: 
                case 3: 
            }
            return this.deserialize(jsonParser, deserializationContext);
        }
    }

    protected static abstract class PrimitiveOrWrapperDeserializer<T>
    extends StdScalarDeserializer<T> {
        private static final long serialVersionUID = 1;
        protected final T _nullValue;

        protected PrimitiveOrWrapperDeserializer(Class<T> class_, T t2) {
            super(class_);
            this._nullValue = t2;
        }

        @Override
        public final T getNullValue() {
            return this._nullValue;
        }
    }

    @JacksonStdImpl
    public static class ShortDeserializer
    extends PrimitiveOrWrapperDeserializer<Short> {
        private static final ShortDeserializer primitiveInstance = new ShortDeserializer(Short.class, 0);
        private static final long serialVersionUID = 1;
        private static final ShortDeserializer wrapperInstance = new ShortDeserializer(Short.TYPE, null);

        public ShortDeserializer(Class<Short> class_, Short s2) {
            super(class_, s2);
        }

        @Override
        public Short deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseShort(jsonParser, deserializationContext);
        }
    }

}

