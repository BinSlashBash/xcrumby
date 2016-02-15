/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import java.io.IOException;

public abstract class PrimitiveArrayDeserializers<T>
extends StdDeserializer<T> {
    protected PrimitiveArrayDeserializers(Class<T> class_) {
        super(class_);
    }

    public static JsonDeserializer<?> forType(Class<?> class_) {
        if (class_ == Integer.TYPE) {
            return IntDeser.instance;
        }
        if (class_ == Long.TYPE) {
            return LongDeser.instance;
        }
        if (class_ == Byte.TYPE) {
            return new ByteDeser();
        }
        if (class_ == Short.TYPE) {
            return new ShortDeser();
        }
        if (class_ == Float.TYPE) {
            return new FloatDeser();
        }
        if (class_ == Double.TYPE) {
            return new DoubleDeser();
        }
        if (class_ == Boolean.TYPE) {
            return new BooleanDeser();
        }
        if (class_ == Character.TYPE) {
            return new CharDeser();
        }
        throw new IllegalStateException();
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    @JacksonStdImpl
    static final class BooleanDeser
    extends PrimitiveArrayDeserializers<boolean[]> {
        private static final long serialVersionUID = 1;

        public BooleanDeser() {
            super(boolean[].class);
        }

        private final boolean[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            return new boolean[]{this._parseBooleanPrimitive(jsonParser, deserializationContext)};
        }

        @Override
        public boolean[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return this.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.BooleanBuilder booleanBuilder = deserializationContext.getArrayBuilders().getBooleanBuilder();
            boolean[] arrbl = (boolean[])booleanBuilder.resetAndStart();
            int n2 = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                boolean bl2 = this._parseBooleanPrimitive(jsonParser, deserializationContext);
                boolean[] arrbl2 = arrbl;
                int n3 = n2;
                if (n2 >= arrbl.length) {
                    arrbl2 = booleanBuilder.appendCompletedChunk(arrbl, n2);
                    n3 = 0;
                }
                arrbl2[n3] = bl2;
                n2 = n3 + 1;
                arrbl = arrbl2;
            }
            return booleanBuilder.completeAndClearBuffer(arrbl, n2);
        }
    }

    @JacksonStdImpl
    static final class ByteDeser
    extends PrimitiveArrayDeserializers<byte[]> {
        private static final long serialVersionUID = 1;

        public ByteDeser() {
            super(byte[].class);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private final byte[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            byte by2;
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.VALUE_NUMBER_INT || jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
                by2 = jsonParser.getByteValue();
                do {
                    return new byte[]{by2};
                    break;
                } while (true);
            }
            if (jsonToken != JsonToken.VALUE_NULL) {
                throw deserializationContext.mappingException(this._valueClass.getComponentType());
            }
            by2 = 0;
            return new byte[]{by2};
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public byte[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Object object = jsonParser.getCurrentToken();
            if (object == JsonToken.VALUE_STRING) {
                return jsonParser.getBinaryValue(deserializationContext.getBase64Variant());
            }
            if (object == JsonToken.VALUE_EMBEDDED_OBJECT) {
                object = jsonParser.getEmbeddedObject();
                if (object == null) {
                    return null;
                }
                if (object instanceof byte[]) {
                    return (byte[])object;
                }
            }
            if (!jsonParser.isExpectedStartArrayToken()) {
                return this.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.ByteBuilder byteBuilder = deserializationContext.getArrayBuilders().getByteBuilder();
            object = (byte[])byteBuilder.resetAndStart();
            int n2 = 0;
            Object object2;
            while ((object2 = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
                byte by2;
                if (object2 == JsonToken.VALUE_NUMBER_INT || object2 == JsonToken.VALUE_NUMBER_FLOAT) {
                    by2 = jsonParser.getByteValue();
                } else {
                    if (object2 != JsonToken.VALUE_NULL) {
                        throw deserializationContext.mappingException(this._valueClass.getComponentType());
                    }
                    by2 = 0;
                }
                object2 = object;
                int n3 = n2;
                if (n2 >= object.length) {
                    object2 = (byte[])byteBuilder.appendCompletedChunk(object, n2);
                    n3 = 0;
                }
                object2[n3] = by2;
                n2 = n3 + 1;
                object = object2;
            }
            return (byte[])byteBuilder.completeAndClearBuffer(object, n2);
        }
    }

    @JacksonStdImpl
    static final class CharDeser
    extends PrimitiveArrayDeserializers<char[]> {
        private static final long serialVersionUID = 1;

        public CharDeser() {
            super(char[].class);
        }

        @Override
        public char[] deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Object object2 = object.getCurrentToken();
            if (object2 == JsonToken.VALUE_STRING) {
                deserializationContext = (DeserializationContext)object.getTextCharacters();
                int n2 = object.getTextOffset();
                int n3 = object.getTextLength();
                object = new char[n3];
                System.arraycopy(deserializationContext, n2, object, 0, n3);
                return object;
            }
            if (object.isExpectedStartArrayToken()) {
                Object object3;
                object2 = new StringBuilder(64);
                while ((object3 = object.nextToken()) != JsonToken.END_ARRAY) {
                    if (object3 != JsonToken.VALUE_STRING) {
                        throw deserializationContext.mappingException(Character.TYPE);
                    }
                    object3 = object.getText();
                    if (object3.length() != 1) {
                        throw JsonMappingException.from((JsonParser)object, "Can not convert a JSON String of length " + object3.length() + " into a char element of char array");
                    }
                    object2.append(object3.charAt(0));
                }
                return object2.toString().toCharArray();
            }
            if (object2 == JsonToken.VALUE_EMBEDDED_OBJECT) {
                if ((object = object.getEmbeddedObject()) == null) {
                    return null;
                }
                if (object instanceof char[]) {
                    return (char[])object;
                }
                if (object instanceof String) {
                    return ((String)object).toCharArray();
                }
                if (object instanceof byte[]) {
                    return Base64Variants.getDefaultVariant().encode((byte[])object, false).toCharArray();
                }
            }
            throw deserializationContext.mappingException(this._valueClass);
        }
    }

    @JacksonStdImpl
    static final class DoubleDeser
    extends PrimitiveArrayDeserializers<double[]> {
        private static final long serialVersionUID = 1;

        public DoubleDeser() {
            super(double[].class);
        }

        private final double[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            return new double[]{this._parseDoublePrimitive(jsonParser, deserializationContext)};
        }

        @Override
        public double[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return this.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.DoubleBuilder doubleBuilder = deserializationContext.getArrayBuilders().getDoubleBuilder();
            double[] arrd = (double[])doubleBuilder.resetAndStart();
            int n2 = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                double d2 = this._parseDoublePrimitive(jsonParser, deserializationContext);
                double[] arrd2 = arrd;
                int n3 = n2;
                if (n2 >= arrd.length) {
                    arrd2 = doubleBuilder.appendCompletedChunk(arrd, n2);
                    n3 = 0;
                }
                arrd2[n3] = d2;
                n2 = n3 + 1;
                arrd = arrd2;
            }
            return doubleBuilder.completeAndClearBuffer(arrd, n2);
        }
    }

    @JacksonStdImpl
    static final class FloatDeser
    extends PrimitiveArrayDeserializers<float[]> {
        private static final long serialVersionUID = 1;

        public FloatDeser() {
            super(float[].class);
        }

        private final float[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            return new float[]{this._parseFloatPrimitive(jsonParser, deserializationContext)};
        }

        @Override
        public float[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return this.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.FloatBuilder floatBuilder = deserializationContext.getArrayBuilders().getFloatBuilder();
            float[] arrf = (float[])floatBuilder.resetAndStart();
            int n2 = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                float f2 = this._parseFloatPrimitive(jsonParser, deserializationContext);
                float[] arrf2 = arrf;
                int n3 = n2;
                if (n2 >= arrf.length) {
                    arrf2 = floatBuilder.appendCompletedChunk(arrf, n2);
                    n3 = 0;
                }
                arrf2[n3] = f2;
                n2 = n3 + 1;
                arrf = arrf2;
            }
            return floatBuilder.completeAndClearBuffer(arrf, n2);
        }
    }

    @JacksonStdImpl
    static final class IntDeser
    extends PrimitiveArrayDeserializers<int[]> {
        public static final IntDeser instance = new IntDeser();
        private static final long serialVersionUID = 1;

        public IntDeser() {
            super(int[].class);
        }

        private final int[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            return new int[]{this._parseIntPrimitive(jsonParser, deserializationContext)};
        }

        @Override
        public int[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return this.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.IntBuilder intBuilder = deserializationContext.getArrayBuilders().getIntBuilder();
            int[] arrn = (int[])intBuilder.resetAndStart();
            int n2 = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                int n3 = this._parseIntPrimitive(jsonParser, deserializationContext);
                int[] arrn2 = arrn;
                int n4 = n2;
                if (n2 >= arrn.length) {
                    arrn2 = intBuilder.appendCompletedChunk(arrn, n2);
                    n4 = 0;
                }
                arrn2[n4] = n3;
                n2 = n4 + 1;
                arrn = arrn2;
            }
            return intBuilder.completeAndClearBuffer(arrn, n2);
        }
    }

    @JacksonStdImpl
    static final class LongDeser
    extends PrimitiveArrayDeserializers<long[]> {
        public static final LongDeser instance = new LongDeser();
        private static final long serialVersionUID = 1;

        public LongDeser() {
            super(long[].class);
        }

        private final long[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            return new long[]{this._parseLongPrimitive(jsonParser, deserializationContext)};
        }

        @Override
        public long[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return this.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.LongBuilder longBuilder = deserializationContext.getArrayBuilders().getLongBuilder();
            long[] arrl = (long[])longBuilder.resetAndStart();
            int n2 = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                long l2 = this._parseLongPrimitive(jsonParser, deserializationContext);
                long[] arrl2 = arrl;
                int n3 = n2;
                if (n2 >= arrl.length) {
                    arrl2 = longBuilder.appendCompletedChunk(arrl, n2);
                    n3 = 0;
                }
                arrl2[n3] = l2;
                n2 = n3 + 1;
                arrl = arrl2;
            }
            return longBuilder.completeAndClearBuffer(arrl, n2);
        }
    }

    @JacksonStdImpl
    static final class ShortDeser
    extends PrimitiveArrayDeserializers<short[]> {
        private static final long serialVersionUID = 1;

        public ShortDeser() {
            super(short[].class);
        }

        private final short[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            return new short[]{this._parseShortPrimitive(jsonParser, deserializationContext)};
        }

        @Override
        public short[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return this.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.ShortBuilder shortBuilder = deserializationContext.getArrayBuilders().getShortBuilder();
            short[] arrs = (short[])shortBuilder.resetAndStart();
            int n2 = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                short s2 = this._parseShortPrimitive(jsonParser, deserializationContext);
                short[] arrs2 = arrs;
                int n3 = n2;
                if (n2 >= arrs.length) {
                    arrs2 = shortBuilder.appendCompletedChunk(arrs, n2);
                    n3 = 0;
                }
                arrs2[n3] = s2;
                n2 = n3 + 1;
                arrs = arrs2;
            }
            return shortBuilder.completeAndClearBuffer(arrs, n2);
        }
    }

}

