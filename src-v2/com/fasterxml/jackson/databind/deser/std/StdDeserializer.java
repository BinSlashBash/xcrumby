/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public abstract class StdDeserializer<T>
extends JsonDeserializer<T>
implements Serializable {
    private static final long serialVersionUID = 1;
    protected final Class<?> _valueClass;

    /*
     * Enabled aggressive block sorting
     */
    protected StdDeserializer(JavaType javaType) {
        void var1_3;
        if (javaType == null) {
            Object var1_2 = null;
        } else {
            Class class_ = javaType.getRawClass();
        }
        this._valueClass = var1_3;
    }

    protected StdDeserializer(Class<?> class_) {
        this._valueClass = class_;
    }

    protected static final double parseDouble(String string2) throws NumberFormatException {
        if ("2.2250738585072012e-308".equals(string2)) {
            return Double.MIN_VALUE;
        }
        return Double.parseDouble(string2);
    }

    protected boolean _hasTextualNull(String string2) {
        return "null".equals(string2);
    }

    protected final boolean _isNaN(String string2) {
        return "NaN".equals(string2);
    }

    protected final boolean _isNegInf(String string2) {
        if ("-Infinity".equals(string2) || "-INF".equals(string2)) {
            return true;
        }
        return false;
    }

    protected final boolean _isPosInf(String string2) {
        if ("Infinity".equals(string2) || "INF".equals(string2)) {
            return true;
        }
        return false;
    }

    protected final Boolean _parseBoolean(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        Object object2 = object.getCurrentToken();
        if (object2 == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (object2 == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        if (object2 == JsonToken.VALUE_NUMBER_INT) {
            if (object.getNumberType() == JsonParser.NumberType.INT) {
                if (object.getIntValue() == 0) {
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            }
            return this._parseBooleanFromNumber((JsonParser)object, deserializationContext);
        }
        if (object2 == JsonToken.VALUE_NULL) {
            return (Boolean)this.getNullValue();
        }
        if (object2 == JsonToken.VALUE_STRING) {
            if ("true".equals(object = object.getText().trim()) || "True".equals(object)) {
                return Boolean.TRUE;
            }
            if ("false".equals(object) || "False".equals(object)) {
                return Boolean.FALSE;
            }
            if (object.length() == 0) {
                return (Boolean)this.getEmptyValue();
            }
            if (this._hasTextualNull((String)object)) {
                return (Boolean)this.getNullValue();
            }
            throw deserializationContext.weirdStringException((String)object, this._valueClass, "only \"true\" or \"false\" recognized");
        }
        if (object2 == JsonToken.START_ARRAY && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            object.nextToken();
            object2 = this._parseBoolean((JsonParser)object, deserializationContext);
            if (object.nextToken() != JsonToken.END_ARRAY) {
                throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Boolean' value but there was more than a single value in the array");
            }
            return object2;
        }
        throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)object2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final boolean _parseBooleanFromNumber(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        if (object.getNumberType() == JsonParser.NumberType.LONG) {
            if (object.getLongValue() == 0) {
                object = Boolean.FALSE;
                do {
                    return object.booleanValue();
                    break;
                } while (true);
            }
            object = Boolean.TRUE;
            return object.booleanValue();
        }
        if (!"0.0".equals(object = object.getText()) && !"0".equals(object)) return Boolean.TRUE;
        return Boolean.FALSE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final boolean _parseBooleanPrimitive(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        boolean bl2 = true;
        boolean bl3 = false;
        JsonToken jsonToken = object.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_TRUE) {
            return true;
        }
        boolean bl4 = bl3;
        if (jsonToken == JsonToken.VALUE_FALSE) return bl4;
        bl4 = bl3;
        if (jsonToken == JsonToken.VALUE_NULL) return bl4;
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            if (object.getNumberType() != JsonParser.NumberType.INT) return this._parseBooleanFromNumber((JsonParser)object, deserializationContext);
            if (object.getIntValue() == 0) return false;
            return bl2;
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            if ("true".equals(object = object.getText().trim())) return true;
            if ("True".equals(object)) {
                return true;
            }
            bl4 = bl3;
            if ("false".equals(object)) return bl4;
            bl4 = bl3;
            if ("False".equals(object)) return bl4;
            bl4 = bl3;
            if (object.length() == 0) return bl4;
            bl4 = bl3;
            if (this._hasTextualNull((String)object)) return bl4;
            throw deserializationContext.weirdStringException((String)object, this._valueClass, "only \"true\" or \"false\" recognized");
        }
        if (jsonToken != JsonToken.START_ARRAY) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        if (!deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        object.nextToken();
        bl4 = this._parseBooleanPrimitive((JsonParser)object, deserializationContext);
        if (object.nextToken() == JsonToken.END_ARRAY) return bl4;
        throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'boolean' value but there was more than a single value in the array");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Byte _parseByte(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        Object object2 = object.getCurrentToken();
        if (object2 == JsonToken.VALUE_NUMBER_INT || object2 == JsonToken.VALUE_NUMBER_FLOAT) {
            return Byte.valueOf(object.getByteValue());
        }
        if (object2 == JsonToken.VALUE_STRING) {
            if (this._hasTextualNull((String)(object = object.getText().trim()))) {
                return (Byte)this.getNullValue();
            }
            try {
                if (object.length() == 0) {
                    return (Byte)this.getEmptyValue();
                }
                int n2 = NumberInput.parseInt((String)object);
                if (n2 >= -128 && n2 <= 255) return Byte.valueOf((byte)n2);
            }
            catch (IllegalArgumentException var4_4) {
                throw deserializationContext.weirdStringException((String)object, this._valueClass, "not a valid Byte value");
            }
            throw deserializationContext.weirdStringException((String)object, this._valueClass, "overflow, value can not be represented as 8-bit value");
        }
        if (object2 == JsonToken.VALUE_NULL) {
            return (Byte)this.getNullValue();
        }
        if (object2 != JsonToken.START_ARRAY || !deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)object2));
        object.nextToken();
        object2 = this._parseByte((JsonParser)object, deserializationContext);
        if (object.nextToken() == JsonToken.END_ARRAY) return object2;
        throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Byte' value but there was more than a single value in the array");
    }

    protected Date _parseDate(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        Object object2 = object.getCurrentToken();
        if (object2 == JsonToken.VALUE_NUMBER_INT) {
            return new Date(object.getLongValue());
        }
        if (object2 == JsonToken.VALUE_NULL) {
            return (Date)this.getNullValue();
        }
        if (object2 == JsonToken.VALUE_STRING) {
            block13 : {
                block12 : {
                    object2 = null;
                    try {
                        object2 = object = object.getText().trim();
                    }
                    catch (IllegalArgumentException var1_2) {
                        throw deserializationContext.weirdStringException((String)object2, this._valueClass, "not a valid representation (error: " + var1_2.getMessage() + ")");
                    }
                    if (object.length() != 0) break block12;
                    object2 = object;
                    return (Date)this.getEmptyValue();
                }
                object2 = object;
                if (!this._hasTextualNull((String)object)) break block13;
                object2 = object;
                return (Date)this.getNullValue();
            }
            object2 = object;
            object = deserializationContext.parseDate((String)object);
            return object;
        }
        if (object2 == JsonToken.START_ARRAY && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            object.nextToken();
            object2 = this._parseDate((JsonParser)object, deserializationContext);
            if (object.nextToken() != JsonToken.END_ARRAY) {
                throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'java.util.Date' value but there was more than a single value in the array");
            }
            return object2;
        }
        throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)object2));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected final Double _parseDouble(JsonParser var1_1, DeserializationContext var2_2) throws IOException {
        var5_3 = var1_1.getCurrentToken();
        if (var5_3 == JsonToken.VALUE_NUMBER_INT) return var1_1.getDoubleValue();
        if (var5_3 == JsonToken.VALUE_NUMBER_FLOAT) {
            return var1_1.getDoubleValue();
        }
        if (var5_3 != JsonToken.VALUE_STRING) ** GOTO lbl28
        if ((var1_1 = var1_1.getText().trim()).length() == 0) {
            return (Double)this.getEmptyValue();
        }
        if (this._hasTextualNull((String)var1_1)) {
            return (Double)this.getNullValue();
        }
        switch (var1_1.charAt(0)) {
            case 'I': {
                if (this._isPosInf((String)var1_1)) {
                    return Double.POSITIVE_INFINITY;
                }
                ** GOTO lbl18
            }
            case 'N': {
                if (this._isNaN((String)var1_1)) {
                    return Double.NaN;
                }
            }
lbl18: // 4 sources:
            default: {
                ** GOTO lbl23
            }
            case '-': 
        }
        if (this._isNegInf((String)var1_1)) {
            return Double.NEGATIVE_INFINITY;
        }
lbl23: // 3 sources:
        try {
            var3_5 = StdDeserializer.parseDouble((String)var1_1);
        }
        catch (IllegalArgumentException var5_4) {
            throw var2_2.weirdStringException((String)var1_1, this._valueClass, "not a valid Double value");
        }
        return var3_5;
lbl28: // 1 sources:
        if (var5_3 == JsonToken.VALUE_NULL) {
            return (Double)this.getNullValue();
        }
        if (var5_3 != JsonToken.START_ARRAY) throw var2_2.mappingException(this._valueClass, (JsonToken)var5_3);
        if (var2_2.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS) == false) throw var2_2.mappingException(this._valueClass, (JsonToken)var5_3);
        var1_1.nextToken();
        var5_3 = this._parseDouble((JsonParser)var1_1, var2_2);
        if (var1_1.nextToken() == JsonToken.END_ARRAY) return var5_3;
        throw var2_2.wrongTokenException((JsonParser)var1_1, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Double' value but there was more than a single value in the array");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected final double _parseDoublePrimitive(JsonParser var1_1, DeserializationContext var2_2) throws IOException {
        var5_3 = 0.0;
        var7_4 = var1_1.getCurrentToken();
        if (var7_4 == JsonToken.VALUE_NUMBER_INT) return var1_1.getDoubleValue();
        if (var7_4 == JsonToken.VALUE_NUMBER_FLOAT) {
            return var1_1.getDoubleValue();
        }
        if (var7_4 != JsonToken.VALUE_STRING) {
            var3_6 = var5_3;
            if (var7_4 == JsonToken.VALUE_NULL) return var3_6;
            if (var7_4 != JsonToken.START_ARRAY) throw var2_2.mappingException(this._valueClass, var7_4);
            if (var2_2.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS) == false) throw var2_2.mappingException(this._valueClass, var7_4);
            var1_1.nextToken();
            var3_6 = this._parseDoublePrimitive((JsonParser)var1_1, var2_2);
            if (var1_1.nextToken() == JsonToken.END_ARRAY) return var3_6;
            throw var2_2.wrongTokenException((JsonParser)var1_1, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Byte' value but there was more than a single value in the array");
        }
        var1_1 = var1_1.getText().trim();
        var3_6 = var5_3;
        if (var1_1.length() == 0) return var3_6;
        var3_6 = var5_3;
        if (this._hasTextualNull((String)var1_1) != false) return var3_6;
        switch (var1_1.charAt(0)) {
            case 'I': {
                if (this._isPosInf((String)var1_1)) {
                    return Double.POSITIVE_INFINITY;
                }
                ** GOTO lbl28
            }
            case 'N': {
                if (this._isNaN((String)var1_1)) {
                    return Double.NaN;
                }
            }
lbl28: // 4 sources:
            default: {
                ** GOTO lbl33
            }
            case '-': 
        }
        if (this._isNegInf((String)var1_1)) {
            return Double.NEGATIVE_INFINITY;
        }
lbl33: // 3 sources:
        try {
            return StdDeserializer.parseDouble((String)var1_1);
        }
        catch (IllegalArgumentException var7_5) {
            throw var2_2.weirdStringException((String)var1_1, this._valueClass, "not a valid double value");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected final Float _parseFloat(JsonParser var1_1, DeserializationContext var2_2) throws IOException {
        var4_3 = var1_1.getCurrentToken();
        if (var4_3 == JsonToken.VALUE_NUMBER_INT) return Float.valueOf(var1_1.getFloatValue());
        if (var4_3 == JsonToken.VALUE_NUMBER_FLOAT) {
            return Float.valueOf(var1_1.getFloatValue());
        }
        if (var4_3 != JsonToken.VALUE_STRING) ** GOTO lbl28
        if ((var1_1 = var1_1.getText().trim()).length() == 0) {
            return (Float)this.getEmptyValue();
        }
        if (this._hasTextualNull((String)var1_1)) {
            return (Float)this.getNullValue();
        }
        switch (var1_1.charAt(0)) {
            case 'I': {
                if (this._isPosInf((String)var1_1)) {
                    return Float.valueOf(Float.POSITIVE_INFINITY);
                }
                ** GOTO lbl18
            }
            case 'N': {
                if (this._isNaN((String)var1_1)) {
                    return Float.valueOf(Float.NaN);
                }
            }
lbl18: // 4 sources:
            default: {
                ** GOTO lbl23
            }
            case '-': 
        }
        if (this._isNegInf((String)var1_1)) {
            return Float.valueOf(Float.NEGATIVE_INFINITY);
        }
lbl23: // 3 sources:
        try {
            var3_5 = Float.parseFloat((String)var1_1);
        }
        catch (IllegalArgumentException var4_4) {
            throw var2_2.weirdStringException((String)var1_1, this._valueClass, "not a valid Float value");
        }
        return Float.valueOf(var3_5);
lbl28: // 1 sources:
        if (var4_3 == JsonToken.VALUE_NULL) {
            return (Float)this.getNullValue();
        }
        if (var4_3 != JsonToken.START_ARRAY) throw var2_2.mappingException(this._valueClass, (JsonToken)var4_3);
        if (var2_2.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS) == false) throw var2_2.mappingException(this._valueClass, (JsonToken)var4_3);
        var1_1.nextToken();
        var4_3 = this._parseFloat((JsonParser)var1_1, var2_2);
        if (var1_1.nextToken() == JsonToken.END_ARRAY) return var4_3;
        throw var2_2.wrongTokenException((JsonParser)var1_1, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Byte' value but there was more than a single value in the array");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected final float _parseFloatPrimitive(JsonParser var1_1, DeserializationContext var2_2) throws IOException {
        var4_3 = 0.0f;
        var5_4 = var1_1.getCurrentToken();
        if (var5_4 == JsonToken.VALUE_NUMBER_INT) return var1_1.getFloatValue();
        if (var5_4 == JsonToken.VALUE_NUMBER_FLOAT) {
            return var1_1.getFloatValue();
        }
        if (var5_4 != JsonToken.VALUE_STRING) {
            var3_6 = var4_3;
            if (var5_4 == JsonToken.VALUE_NULL) return var3_6;
            if (var5_4 != JsonToken.START_ARRAY) throw var2_2.mappingException(this._valueClass, var5_4);
            if (var2_2.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS) == false) throw var2_2.mappingException(this._valueClass, var5_4);
            var1_1.nextToken();
            var3_6 = this._parseFloatPrimitive((JsonParser)var1_1, var2_2);
            if (var1_1.nextToken() == JsonToken.END_ARRAY) return var3_6;
            throw var2_2.wrongTokenException((JsonParser)var1_1, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'float' value but there was more than a single value in the array");
        }
        var1_1 = var1_1.getText().trim();
        var3_6 = var4_3;
        if (var1_1.length() == 0) return var3_6;
        var3_6 = var4_3;
        if (this._hasTextualNull((String)var1_1) != false) return var3_6;
        switch (var1_1.charAt(0)) {
            case 'I': {
                if (this._isPosInf((String)var1_1)) {
                    return Float.POSITIVE_INFINITY;
                }
                ** GOTO lbl28
            }
            case 'N': {
                if (this._isNaN((String)var1_1)) {
                    return Float.NaN;
                }
            }
lbl28: // 4 sources:
            default: {
                ** GOTO lbl33
            }
            case '-': 
        }
        if (this._isNegInf((String)var1_1)) {
            return Float.NEGATIVE_INFINITY;
        }
lbl33: // 3 sources:
        try {
            return Float.parseFloat((String)var1_1);
        }
        catch (IllegalArgumentException var5_5) {
            throw var2_2.weirdStringException((String)var1_1, this._valueClass, "not a valid float value");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final int _parseIntPrimitive(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        int n2;
        int n3 = 0;
        JsonToken jsonToken = object.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return object.getIntValue();
        if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return object.getIntValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            int n4;
            block6 : {
                object = object.getText().trim();
                n2 = n3;
                if (this._hasTextualNull((String)object)) return n2;
                try {
                    n4 = object.length();
                    if (n4 <= 9) break block6;
                    long l2 = Long.parseLong((String)object);
                    if (l2 < Integer.MIN_VALUE) throw deserializationContext.weirdStringException((String)object, this._valueClass, "Overflow: numeric value (" + (String)object + ") out of range of int (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
                    if (l2 <= Integer.MAX_VALUE) return (int)l2;
                    throw deserializationContext.weirdStringException((String)object, this._valueClass, "Overflow: numeric value (" + (String)object + ") out of range of int (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
                }
                catch (IllegalArgumentException var8_5) {
                    throw deserializationContext.weirdStringException((String)object, this._valueClass, "not a valid int value");
                }
            }
            n2 = n3;
            if (n4 == 0) return n2;
            return NumberInput.parseInt((String)object);
        }
        n2 = n3;
        if (jsonToken == JsonToken.VALUE_NULL) return n2;
        if (jsonToken != JsonToken.START_ARRAY) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        if (!deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        object.nextToken();
        n2 = this._parseIntPrimitive((JsonParser)object, deserializationContext);
        if (object.nextToken() == JsonToken.END_ARRAY) return n2;
        throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'int' value but there was more than a single value in the array");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final Integer _parseInteger(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        Object object2 = object.getCurrentToken();
        if (object2 == JsonToken.VALUE_NUMBER_INT || object2 == JsonToken.VALUE_NUMBER_FLOAT) {
            return object.getIntValue();
        }
        if (object2 == JsonToken.VALUE_STRING) {
            int n2;
            block11 : {
                long l2;
                block12 : {
                    object = object.getText().trim();
                    try {
                        n2 = object.length();
                        if (this._hasTextualNull((String)object)) {
                            return (Integer)this.getNullValue();
                        }
                        if (n2 <= 9) break block11;
                        l2 = Long.parseLong((String)object);
                        if (l2 >= Integer.MIN_VALUE && l2 <= Integer.MAX_VALUE) break block12;
                        throw deserializationContext.weirdStringException((String)object, this._valueClass, "Overflow: numeric value (" + (String)object + ") out of range of Integer (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
                    }
                    catch (IllegalArgumentException var6_4) {
                        throw deserializationContext.weirdStringException((String)object, this._valueClass, "not a valid Integer value");
                    }
                }
                n2 = (int)l2;
                return n2;
            }
            if (n2 == 0) {
                return (Integer)this.getEmptyValue();
            }
            n2 = NumberInput.parseInt((String)object);
            return n2;
        }
        if (object2 == JsonToken.VALUE_NULL) {
            return (Integer)this.getNullValue();
        }
        if (object2 == JsonToken.START_ARRAY && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            object.nextToken();
            object2 = this._parseInteger((JsonParser)object, deserializationContext);
            if (object.nextToken() != JsonToken.END_ARRAY) {
                throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Integer' value but there was more than a single value in the array");
            }
            return object2;
        }
        throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)object2));
    }

    protected final Long _parseLong(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        Object object2 = object.getCurrentToken();
        if (object2 == JsonToken.VALUE_NUMBER_INT || object2 == JsonToken.VALUE_NUMBER_FLOAT) {
            return object.getLongValue();
        }
        if (object2 == JsonToken.VALUE_STRING) {
            long l2;
            if ((object = object.getText().trim()).length() == 0) {
                return (Long)this.getEmptyValue();
            }
            if (this._hasTextualNull((String)object)) {
                return (Long)this.getNullValue();
            }
            try {
                l2 = NumberInput.parseLong((String)object);
            }
            catch (IllegalArgumentException var5_4) {
                throw deserializationContext.weirdStringException((String)object, this._valueClass, "not a valid Long value");
            }
            return l2;
        }
        if (object2 == JsonToken.VALUE_NULL) {
            return (Long)this.getNullValue();
        }
        if (object2 == JsonToken.START_ARRAY && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            object.nextToken();
            object2 = this._parseLong((JsonParser)object, deserializationContext);
            if (object.nextToken() != JsonToken.END_ARRAY) {
                throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Long' value but there was more than a single value in the array");
            }
            return object2;
        }
        throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)object2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final long _parseLongPrimitive(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        long l2;
        long l3 = 0;
        JsonToken jsonToken = object.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return object.getLongValue();
        if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return object.getLongValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            object = object.getText().trim();
            l2 = l3;
            if (object.length() == 0) return l2;
            l2 = l3;
            if (this._hasTextualNull((String)object)) return l2;
            try {
                return NumberInput.parseLong((String)object);
            }
            catch (IllegalArgumentException var7_5) {
                throw deserializationContext.weirdStringException((String)object, this._valueClass, "not a valid long value");
            }
        }
        l2 = l3;
        if (jsonToken == JsonToken.VALUE_NULL) return l2;
        if (jsonToken != JsonToken.START_ARRAY) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        if (!deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        object.nextToken();
        l2 = this._parseLongPrimitive((JsonParser)object, deserializationContext);
        if (object.nextToken() == JsonToken.END_ARRAY) return l2;
        throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'long' value but there was more than a single value in the array");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Short _parseShort(JsonParser object, DeserializationContext deserializationContext) throws IOException {
        Object object2 = object.getCurrentToken();
        if (object2 == JsonToken.VALUE_NUMBER_INT || object2 == JsonToken.VALUE_NUMBER_FLOAT) {
            return object.getShortValue();
        }
        if (object2 == JsonToken.VALUE_STRING) {
            object = object.getText().trim();
            try {
                if (object.length() == 0) {
                    return (Short)this.getEmptyValue();
                }
                if (this._hasTextualNull((String)object)) {
                    return (Short)this.getNullValue();
                }
                int n2 = NumberInput.parseInt((String)object);
                if (n2 >= -32768 && n2 <= 32767) return (short)n2;
            }
            catch (IllegalArgumentException var4_4) {
                throw deserializationContext.weirdStringException((String)object, this._valueClass, "not a valid Short value");
            }
            throw deserializationContext.weirdStringException((String)object, this._valueClass, "overflow, value can not be represented as 16-bit value");
        }
        if (object2 == JsonToken.VALUE_NULL) {
            return (Short)this.getNullValue();
        }
        if (object2 != JsonToken.START_ARRAY || !deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) throw deserializationContext.mappingException(this._valueClass, (JsonToken)((Object)object2));
        object.nextToken();
        object2 = this._parseShort((JsonParser)object, deserializationContext);
        if (object.nextToken() == JsonToken.END_ARRAY) return object2;
        throw deserializationContext.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Short' value but there was more than a single value in the array");
    }

    protected final short _parseShortPrimitive(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int n2 = this._parseIntPrimitive(jsonParser, deserializationContext);
        if (n2 < -32768 || n2 > 32767) {
            throw deserializationContext.weirdStringException(String.valueOf(n2), this._valueClass, "overflow, value can not be represented as 16-bit value");
        }
        return (short)n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final String _parseString(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object object = jsonParser.getCurrentToken();
        if (object == JsonToken.VALUE_STRING) {
            return jsonParser.getText();
        }
        if (object == JsonToken.START_ARRAY && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            jsonParser.nextToken();
            object = this._parseString(jsonParser, deserializationContext);
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) return object;
            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'String' value but there was more than a single value in the array");
        }
        object = jsonParser.getValueAsString();
        if (object == null) throw deserializationContext.mappingException(String.class, jsonParser.getCurrentToken());
        return object;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    protected JsonDeserializer<?> findConvertingContentDeserializer(DeserializationContext deserializationContext, BeanProperty beanProperty, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Object object = deserializationContext.getAnnotationIntrospector();
        if (object != null && beanProperty != null && (object = object.findDeserializationContentConverter(beanProperty.getMember())) != null) {
            Converter<Object, Object> converter = deserializationContext.converterInstance(beanProperty.getMember(), object);
            JavaType javaType = converter.getInputType(deserializationContext.getTypeFactory());
            object = jsonDeserializer;
            if (jsonDeserializer == null) {
                object = deserializationContext.findContextualValueDeserializer(javaType, beanProperty);
            }
            return new StdDelegatingDeserializer<Object>(converter, javaType, object);
        }
        return jsonDeserializer;
    }

    protected JsonDeserializer<Object> findDeserializer(DeserializationContext deserializationContext, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        return deserializationContext.findContextualValueDeserializer(javaType, beanProperty);
    }

    @Deprecated
    public final Class<?> getValueClass() {
        return this._valueClass;
    }

    public JavaType getValueType() {
        return null;
    }

    protected void handleUnknownProperty(JsonParser jsonParser, DeserializationContext deserializationContext, Object class_, String string2) throws IOException {
        Class class_2 = class_;
        if (class_ == null) {
            class_2 = this.handledType();
        }
        if (deserializationContext.handleUnknownProperty(jsonParser, this, class_2, string2)) {
            return;
        }
        deserializationContext.reportUnknownProperty(class_2, string2, this);
        jsonParser.skipChildren();
    }

    @Override
    public Class<?> handledType() {
        return this._valueClass;
    }

    protected boolean isDefaultDeserializer(JsonDeserializer<?> jsonDeserializer) {
        return ClassUtil.isJacksonStdImpl(jsonDeserializer);
    }

    protected boolean isDefaultKeyDeserializer(KeyDeserializer keyDeserializer) {
        return ClassUtil.isJacksonStdImpl(keyDeserializer);
    }
}

