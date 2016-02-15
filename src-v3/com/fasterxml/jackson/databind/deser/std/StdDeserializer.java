package com.fasterxml.jackson.databind.deser.std;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import com.crumby.C0065R;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
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
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public abstract class StdDeserializer<T> extends JsonDeserializer<T> implements Serializable {
    private static final long serialVersionUID = 1;
    protected final Class<?> _valueClass;

    protected StdDeserializer(Class<?> vc) {
        this._valueClass = vc;
    }

    protected StdDeserializer(JavaType valueType) {
        this._valueClass = valueType == null ? null : valueType.getRawClass();
    }

    public Class<?> handledType() {
        return this._valueClass;
    }

    @Deprecated
    public final Class<?> getValueClass() {
        return this._valueClass;
    }

    public JavaType getValueType() {
        return null;
    }

    protected boolean isDefaultDeserializer(JsonDeserializer<?> deserializer) {
        return ClassUtil.isJacksonStdImpl((Object) deserializer);
    }

    protected boolean isDefaultKeyDeserializer(KeyDeserializer keyDeser) {
        return ClassUtil.isJacksonStdImpl((Object) keyDeser);
    }

    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jp, ctxt);
    }

    protected final boolean _parseBooleanPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        boolean z = true;
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (t == JsonToken.VALUE_FALSE || t == JsonToken.VALUE_NULL) {
            return false;
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
            if (jp.getNumberType() != NumberType.INT) {
                return _parseBooleanFromNumber(jp, ctxt);
            }
            if (jp.getIntValue() == 0) {
                z = false;
            }
            return z;
        } else if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if ("true".equals(text) || "True".equals(text)) {
                return true;
            }
            if ("false".equals(text) || "False".equals(text) || text.length() == 0 || _hasTextualNull(text)) {
                return false;
            }
            throw ctxt.weirdStringException(text, this._valueClass, "only \"true\" or \"false\" recognized");
        } else if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            jp.nextToken();
            boolean parsed = _parseBooleanPrimitive(jp, ctxt);
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                return parsed;
            }
            throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'boolean' value but there was more than a single value in the array");
        } else {
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected final Boolean _parseBoolean(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (t == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
            if (jp.getNumberType() == NumberType.INT) {
                return jp.getIntValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
            } else {
                return Boolean.valueOf(_parseBooleanFromNumber(jp, ctxt));
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return (Boolean) getNullValue();
        } else {
            if (t == JsonToken.VALUE_STRING) {
                String text = jp.getText().trim();
                if ("true".equals(text) || "True".equals(text)) {
                    return Boolean.TRUE;
                }
                if ("false".equals(text) || "False".equals(text)) {
                    return Boolean.FALSE;
                }
                if (text.length() == 0) {
                    return (Boolean) getEmptyValue();
                }
                if (_hasTextualNull(text)) {
                    return (Boolean) getNullValue();
                }
                throw ctxt.weirdStringException(text, this._valueClass, "only \"true\" or \"false\" recognized");
            } else if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                Boolean parsed = _parseBoolean(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Boolean' value but there was more than a single value in the array");
            } else {
                throw ctxt.mappingException(this._valueClass, t);
            }
        }
    }

    protected final boolean _parseBooleanFromNumber(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getNumberType() == NumberType.LONG) {
            return (jp.getLongValue() == 0 ? Boolean.FALSE : Boolean.TRUE).booleanValue();
        }
        String str = jp.getText();
        if ("0.0".equals(str) || "0".equals(str)) {
            return Boolean.FALSE.booleanValue();
        }
        return Boolean.TRUE.booleanValue();
    }

    protected Byte _parseByte(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return Byte.valueOf(jp.getByteValue());
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (_hasTextualNull(text)) {
                return (Byte) getNullValue();
            }
            try {
                if (text.length() == 0) {
                    return (Byte) getEmptyValue();
                }
                int value = NumberInput.parseInt(text);
                if (value >= -128 && value <= MotionEventCompat.ACTION_MASK) {
                    return Byte.valueOf((byte) value);
                }
                throw ctxt.weirdStringException(text, this._valueClass, "overflow, value can not be represented as 8-bit value");
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid Byte value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return (Byte) getNullValue();
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                Byte parsed = _parseByte(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Byte' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected Short _parseShort(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return Short.valueOf(jp.getShortValue());
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            try {
                if (text.length() == 0) {
                    return (Short) getEmptyValue();
                }
                if (_hasTextualNull(text)) {
                    return (Short) getNullValue();
                }
                int value = NumberInput.parseInt(text);
                if (value >= -32768 && value <= 32767) {
                    return Short.valueOf((short) value);
                }
                throw ctxt.weirdStringException(text, this._valueClass, "overflow, value can not be represented as 16-bit value");
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid Short value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return (Short) getNullValue();
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                Short parsed = _parseShort(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Short' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected final short _parseShortPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        int value = _parseIntPrimitive(jp, ctxt);
        if (value >= -32768 && value <= 32767) {
            return (short) value;
        }
        throw ctxt.weirdStringException(String.valueOf(value), this._valueClass, "overflow, value can not be represented as 16-bit value");
    }

    protected final int _parseIntPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return jp.getIntValue();
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (_hasTextualNull(text)) {
                return 0;
            }
            try {
                int len = text.length();
                if (len > 9) {
                    long l = Long.parseLong(text);
                    if (l >= -2147483648L && l <= 2147483647L) {
                        return (int) l;
                    }
                    throw ctxt.weirdStringException(text, this._valueClass, "Overflow: numeric value (" + text + ") out of range of int (" + ExploreByTouchHelper.INVALID_ID + " - " + Integer.MAX_VALUE + ")");
                } else if (len != 0) {
                    return NumberInput.parseInt(text);
                } else {
                    return 0;
                }
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid int value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return 0;
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                int parsed = _parseIntPrimitive(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'int' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected final Integer _parseInteger(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return Integer.valueOf(jp.getIntValue());
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            try {
                int len = text.length();
                if (_hasTextualNull(text)) {
                    return (Integer) getNullValue();
                }
                if (len > 9) {
                    long l = Long.parseLong(text);
                    if (l >= -2147483648L && l <= 2147483647L) {
                        return Integer.valueOf((int) l);
                    }
                    throw ctxt.weirdStringException(text, this._valueClass, "Overflow: numeric value (" + text + ") out of range of Integer (" + ExploreByTouchHelper.INVALID_ID + " - " + Integer.MAX_VALUE + ")");
                } else if (len == 0) {
                    return (Integer) getEmptyValue();
                } else {
                    return Integer.valueOf(NumberInput.parseInt(text));
                }
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid Integer value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return (Integer) getNullValue();
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                Integer parsed = _parseInteger(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Integer' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected final Long _parseLong(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return Long.valueOf(jp.getLongValue());
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (text.length() == 0) {
                return (Long) getEmptyValue();
            }
            if (_hasTextualNull(text)) {
                return (Long) getNullValue();
            }
            try {
                return Long.valueOf(NumberInput.parseLong(text));
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid Long value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return (Long) getNullValue();
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                Long parsed = _parseLong(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Long' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected final long _parseLongPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return jp.getLongValue();
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (text.length() == 0 || _hasTextualNull(text)) {
                return 0;
            }
            try {
                return NumberInput.parseLong(text);
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid long value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return 0;
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                long parsed = _parseLongPrimitive(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'long' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected final Float _parseFloat(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return Float.valueOf(jp.getFloatValue());
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (text.length() == 0) {
                return (Float) getEmptyValue();
            }
            if (_hasTextualNull(text)) {
                return (Float) getNullValue();
            }
            switch (text.charAt(0)) {
                case C0065R.styleable.TwoWayView_android_contentDescription /*45*/:
                    if (_isNegInf(text)) {
                        return Float.valueOf(Float.NEGATIVE_INFINITY);
                    }
                    break;
                case 'I':
                    if (_isPosInf(text)) {
                        return Float.valueOf(Float.POSITIVE_INFINITY);
                    }
                    break;
                case 'N':
                    if (_isNaN(text)) {
                        return Float.valueOf(Float.NaN);
                    }
                    break;
            }
            try {
                return Float.valueOf(Float.parseFloat(text));
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid Float value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return (Float) getNullValue();
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                Float parsed = _parseFloat(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Byte' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected final float _parseFloatPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return jp.getFloatValue();
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (text.length() == 0 || _hasTextualNull(text)) {
                return 0.0f;
            }
            switch (text.charAt(0)) {
                case C0065R.styleable.TwoWayView_android_contentDescription /*45*/:
                    if (_isNegInf(text)) {
                        return Float.NEGATIVE_INFINITY;
                    }
                    break;
                case 'I':
                    if (_isPosInf(text)) {
                        return Float.POSITIVE_INFINITY;
                    }
                    break;
                case 'N':
                    if (_isNaN(text)) {
                        return Float.NaN;
                    }
                    break;
            }
            try {
                return Float.parseFloat(text);
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid float value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return 0.0f;
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                float parsed = _parseFloatPrimitive(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'float' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected final Double _parseDouble(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return Double.valueOf(jp.getDoubleValue());
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (text.length() == 0) {
                return (Double) getEmptyValue();
            }
            if (_hasTextualNull(text)) {
                return (Double) getNullValue();
            }
            switch (text.charAt(0)) {
                case C0065R.styleable.TwoWayView_android_contentDescription /*45*/:
                    if (_isNegInf(text)) {
                        return Double.valueOf(Double.NEGATIVE_INFINITY);
                    }
                    break;
                case 'I':
                    if (_isPosInf(text)) {
                        return Double.valueOf(Double.POSITIVE_INFINITY);
                    }
                    break;
                case 'N':
                    if (_isNaN(text)) {
                        return Double.valueOf(Double.NaN);
                    }
                    break;
            }
            try {
                return Double.valueOf(parseDouble(text));
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid Double value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return (Double) getNullValue();
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                Double parsed = _parseDouble(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Double' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected final double _parseDoublePrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return jp.getDoubleValue();
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (text.length() == 0 || _hasTextualNull(text)) {
                return 0.0d;
            }
            switch (text.charAt(0)) {
                case C0065R.styleable.TwoWayView_android_contentDescription /*45*/:
                    if (_isNegInf(text)) {
                        return Double.NEGATIVE_INFINITY;
                    }
                    break;
                case 'I':
                    if (_isPosInf(text)) {
                        return Double.POSITIVE_INFINITY;
                    }
                    break;
                case 'N':
                    if (_isNaN(text)) {
                        return Double.NaN;
                    }
                    break;
            }
            try {
                return parseDouble(text);
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(text, this._valueClass, "not a valid double value");
            }
        } else if (t == JsonToken.VALUE_NULL) {
            return 0.0d;
        } else {
            if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jp.nextToken();
                double parsed = _parseDoublePrimitive(jp, ctxt);
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return parsed;
                }
                throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'Byte' value but there was more than a single value in the array");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected Date _parseDate(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return new Date(jp.getLongValue());
        }
        if (t == JsonToken.VALUE_NULL) {
            return (Date) getNullValue();
        }
        if (t == JsonToken.VALUE_STRING) {
            try {
                String value = jp.getText().trim();
                if (value.length() == 0) {
                    return (Date) getEmptyValue();
                }
                if (_hasTextualNull(value)) {
                    return (Date) getNullValue();
                }
                return ctxt.parseDate(value);
            } catch (IllegalArgumentException iae) {
                throw ctxt.weirdStringException(null, this._valueClass, "not a valid representation (error: " + iae.getMessage() + ")");
            }
        } else if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            jp.nextToken();
            Date parsed = _parseDate(jp, ctxt);
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                return parsed;
            }
            throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'java.util.Date' value but there was more than a single value in the array");
        } else {
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    protected static final double parseDouble(String numStr) throws NumberFormatException {
        if (NumberInput.NASTY_SMALL_DOUBLE.equals(numStr)) {
            return Double.MIN_VALUE;
        }
        return Double.parseDouble(numStr);
    }

    protected final String _parseString(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            return jp.getText();
        }
        if (t == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            jp.nextToken();
            String parsed = _parseString(jp, ctxt);
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                return parsed;
            }
            throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'String' value but there was more than a single value in the array");
        }
        String value = jp.getValueAsString();
        if (value != null) {
            return value;
        }
        throw ctxt.mappingException(String.class, jp.getCurrentToken());
    }

    protected boolean _hasTextualNull(String value) {
        return "null".equals(value);
    }

    protected final boolean _isNegInf(String text) {
        return "-Infinity".equals(text) || "-INF".equals(text);
    }

    protected final boolean _isPosInf(String text) {
        return "Infinity".equals(text) || "INF".equals(text);
    }

    protected final boolean _isNaN(String text) {
        return "NaN".equals(text);
    }

    protected JsonDeserializer<Object> findDeserializer(DeserializationContext ctxt, JavaType type, BeanProperty property) throws JsonMappingException {
        return ctxt.findContextualValueDeserializer(type, property);
    }

    protected JsonDeserializer<?> findConvertingContentDeserializer(DeserializationContext ctxt, BeanProperty prop, JsonDeserializer<?> existingDeserializer) throws JsonMappingException {
        AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
        if (!(intr == null || prop == null)) {
            Object convDef = intr.findDeserializationContentConverter(prop.getMember());
            if (convDef != null) {
                Converter<Object, Object> conv = ctxt.converterInstance(prop.getMember(), convDef);
                JavaType delegateType = conv.getInputType(ctxt.getTypeFactory());
                if (existingDeserializer == null) {
                    existingDeserializer = ctxt.findContextualValueDeserializer(delegateType, prop);
                }
                return new StdDelegatingDeserializer(conv, delegateType, existingDeserializer);
            }
        }
        return existingDeserializer;
    }

    protected void handleUnknownProperty(JsonParser jp, DeserializationContext ctxt, Object instanceOrClass, String propName) throws IOException {
        if (instanceOrClass == null) {
            instanceOrClass = handledType();
        }
        if (!ctxt.handleUnknownProperty(jp, this, instanceOrClass, propName)) {
            ctxt.reportUnknownProperty(instanceOrClass, propName, this);
            jp.skipChildren();
        }
    }
}
