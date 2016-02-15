package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JacksonStdImpl
public class UntypedObjectDeserializer extends StdDeserializer<Object> implements ResolvableDeserializer, ContextualDeserializer {
    protected static final Object[] NO_OBJECTS;
    @Deprecated
    public static final UntypedObjectDeserializer instance;
    private static final long serialVersionUID = 1;
    protected JsonDeserializer<Object> _listDeserializer;
    protected JsonDeserializer<Object> _mapDeserializer;
    protected JsonDeserializer<Object> _numberDeserializer;
    protected JsonDeserializer<Object> _stringDeserializer;

    /* renamed from: com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer.1 */
    static /* synthetic */ class C01811 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$core$JsonToken;

        static {
            $SwitchMap$com$fasterxml$jackson$core$JsonToken = new int[JsonToken.values().length];
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.FIELD_NAME.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.START_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.START_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_EMBEDDED_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_STRING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NUMBER_INT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NUMBER_FLOAT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_TRUE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_FALSE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NULL.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.END_ARRAY.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.END_OBJECT.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    @JacksonStdImpl
    public static class Vanilla extends StdDeserializer<Object> {
        private static final long serialVersionUID = 1;
        public static final Vanilla std;

        static {
            std = new Vanilla();
        }

        public Vanilla() {
            super(Object.class);
        }

        public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            switch (jp.getCurrentTokenId()) {
                case Std.STD_FILE /*1*/:
                    if (jp.nextToken() == JsonToken.END_OBJECT) {
                        return new LinkedHashMap(2);
                    }
                    break;
                case Std.STD_URI /*3*/:
                    if (jp.nextToken() == JsonToken.END_ARRAY) {
                        if (ctxt.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                            return UntypedObjectDeserializer.NO_OBJECTS;
                        }
                        return new ArrayList(2);
                    } else if (ctxt.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                        return mapArrayToArray(jp, ctxt);
                    } else {
                        return mapArray(jp, ctxt);
                    }
                case Std.STD_JAVA_TYPE /*5*/:
                    break;
                case Std.STD_CURRENCY /*6*/:
                    return jp.getText();
                case Std.STD_PATTERN /*7*/:
                    if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                        return jp.getBigIntegerValue();
                    }
                    return jp.getNumberValue();
                case Std.STD_LOCALE /*8*/:
                    if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                        return jp.getDecimalValue();
                    }
                    return Double.valueOf(jp.getDoubleValue());
                case Std.STD_CHARSET /*9*/:
                    return Boolean.TRUE;
                case Std.STD_TIME_ZONE /*10*/:
                    return Boolean.FALSE;
                case Std.STD_INET_ADDRESS /*11*/:
                    return null;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    return jp.getEmbeddedObject();
                default:
                    throw ctxt.mappingException(Object.class);
            }
            return mapObject(jp, ctxt);
        }

        public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
            switch (jp.getCurrentTokenId()) {
                case Std.STD_FILE /*1*/:
                case Std.STD_URI /*3*/:
                case Std.STD_JAVA_TYPE /*5*/:
                    return typeDeserializer.deserializeTypedFromAny(jp, ctxt);
                case Std.STD_CURRENCY /*6*/:
                    return jp.getText();
                case Std.STD_PATTERN /*7*/:
                    if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                        return jp.getBigIntegerValue();
                    }
                    return jp.getNumberValue();
                case Std.STD_LOCALE /*8*/:
                    if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                        return jp.getDecimalValue();
                    }
                    return Double.valueOf(jp.getDoubleValue());
                case Std.STD_CHARSET /*9*/:
                    return Boolean.TRUE;
                case Std.STD_TIME_ZONE /*10*/:
                    return Boolean.FALSE;
                case Std.STD_INET_ADDRESS /*11*/:
                    return null;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    return jp.getEmbeddedObject();
                default:
                    throw ctxt.mappingException(Object.class);
            }
        }

        protected Object mapArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
            Object value = deserialize(jp, ctxt);
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                ArrayList<Object> l = new ArrayList(2);
                l.add(value);
                return l;
            }
            Object value2 = deserialize(jp, ctxt);
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                l = new ArrayList(2);
                l.add(value);
                l.add(value2);
                return l;
            }
            ObjectBuffer buffer = ctxt.leaseObjectBuffer();
            Object[] values = buffer.resetAndStart();
            int ptr = 0 + 1;
            values[0] = value;
            int ptr2 = ptr + 1;
            values[ptr] = value2;
            int totalSize = ptr2;
            while (true) {
                value = deserialize(jp, ctxt);
                totalSize++;
                if (ptr2 >= values.length) {
                    values = buffer.appendCompletedChunk(values);
                    ptr2 = 0;
                }
                ptr = ptr2 + 1;
                values[ptr2] = value;
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    List result = new ArrayList(totalSize);
                    buffer.completeAndClearBuffer(values, ptr, result);
                    return result;
                }
                ptr2 = ptr;
            }
        }

        protected Object mapObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
            String field1 = jp.getText();
            jp.nextToken();
            Object value1 = deserialize(jp, ctxt);
            if (jp.nextToken() == JsonToken.END_OBJECT) {
                LinkedHashMap<String, Object> result = new LinkedHashMap(2);
                result.put(field1, value1);
                return result;
            }
            String field2 = jp.getText();
            jp.nextToken();
            Object value2 = deserialize(jp, ctxt);
            if (jp.nextToken() == JsonToken.END_OBJECT) {
                result = new LinkedHashMap(4);
                result.put(field1, value1);
                result.put(field2, value2);
                return result;
            }
            result = new LinkedHashMap();
            result.put(field1, value1);
            result.put(field2, value2);
            do {
                String fieldName = jp.getText();
                jp.nextToken();
                result.put(fieldName, deserialize(jp, ctxt));
            } while (jp.nextToken() != JsonToken.END_OBJECT);
            return result;
        }

        protected Object[] mapArrayToArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
            ObjectBuffer buffer = ctxt.leaseObjectBuffer();
            Object[] values = buffer.resetAndStart();
            int ptr = 0;
            while (true) {
                Object value = deserialize(jp, ctxt);
                if (ptr >= values.length) {
                    values = buffer.appendCompletedChunk(values);
                    ptr = 0;
                }
                int ptr2 = ptr + 1;
                values[ptr] = value;
                if (jp.nextToken() == JsonToken.END_ARRAY) {
                    return buffer.completeAndClearBuffer(values, ptr2);
                }
                ptr = ptr2;
            }
        }
    }

    static {
        NO_OBJECTS = new Object[0];
        instance = new UntypedObjectDeserializer();
    }

    public UntypedObjectDeserializer() {
        super(Object.class);
    }

    public UntypedObjectDeserializer(UntypedObjectDeserializer base, JsonDeserializer<?> mapDeser, JsonDeserializer<?> listDeser, JsonDeserializer<?> stringDeser, JsonDeserializer<?> numberDeser) {
        super(Object.class);
        this._mapDeserializer = mapDeser;
        this._listDeserializer = listDeser;
        this._stringDeserializer = stringDeser;
        this._numberDeserializer = numberDeser;
    }

    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        JavaType obType = ctxt.constructType(Object.class);
        JavaType stringType = ctxt.constructType(String.class);
        TypeFactory tf = ctxt.getTypeFactory();
        this._mapDeserializer = _findCustomDeser(ctxt, tf.constructMapType(Map.class, stringType, obType));
        this._listDeserializer = _findCustomDeser(ctxt, tf.constructCollectionType(List.class, obType));
        this._stringDeserializer = _findCustomDeser(ctxt, stringType);
        this._numberDeserializer = _findCustomDeser(ctxt, tf.constructType((Type) Number.class));
    }

    protected JsonDeserializer<Object> _findCustomDeser(DeserializationContext ctxt, JavaType type) throws JsonMappingException {
        Object deser = ctxt.findRootValueDeserializer(type);
        if (ClassUtil.isJacksonStdImpl(deser)) {
            return null;
        }
        return deser;
    }

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        if (this._stringDeserializer == null && this._numberDeserializer == null && this._mapDeserializer == null && this._listDeserializer == null && getClass() == UntypedObjectDeserializer.class) {
            return Vanilla.std;
        }
        JsonDeserializer<?> mapDeserializer = this._mapDeserializer;
        if (mapDeserializer instanceof ContextualDeserializer) {
            mapDeserializer = ((ContextualDeserializer) mapDeserializer).createContextual(ctxt, property);
        }
        JsonDeserializer<?> listDeserializer = this._listDeserializer;
        if (listDeserializer instanceof ContextualDeserializer) {
            listDeserializer = ((ContextualDeserializer) listDeserializer).createContextual(ctxt, property);
        }
        JsonDeserializer<?> stringDeserializer = this._stringDeserializer;
        if (stringDeserializer instanceof ContextualDeserializer) {
            stringDeserializer = ((ContextualDeserializer) stringDeserializer).createContextual(ctxt, property);
        }
        JsonDeserializer<?> numberDeserializer = this._numberDeserializer;
        if (numberDeserializer instanceof ContextualDeserializer) {
            numberDeserializer = ((ContextualDeserializer) numberDeserializer).createContextual(ctxt, property);
        }
        if (mapDeserializer == this._mapDeserializer && listDeserializer == this._listDeserializer && stringDeserializer == this._stringDeserializer && numberDeserializer == this._numberDeserializer) {
            return this;
        }
        return _withResolved(mapDeserializer, listDeserializer, stringDeserializer, numberDeserializer);
    }

    protected JsonDeserializer<?> _withResolved(JsonDeserializer<?> mapDeser, JsonDeserializer<?> listDeser, JsonDeserializer<?> stringDeser, JsonDeserializer<?> numberDeser) {
        return new UntypedObjectDeserializer(this, mapDeser, listDeser, stringDeser, numberDeser);
    }

    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        switch (C01811.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jp.getCurrentToken().ordinal()]) {
            case Std.STD_FILE /*1*/:
            case Std.STD_URL /*2*/:
                if (this._mapDeserializer != null) {
                    return this._mapDeserializer.deserialize(jp, ctxt);
                }
                return mapObject(jp, ctxt);
            case Std.STD_URI /*3*/:
                if (ctxt.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                    return mapArrayToArray(jp, ctxt);
                }
                if (this._listDeserializer != null) {
                    return this._listDeserializer.deserialize(jp, ctxt);
                }
                return mapArray(jp, ctxt);
            case Std.STD_CLASS /*4*/:
                return jp.getEmbeddedObject();
            case Std.STD_JAVA_TYPE /*5*/:
                if (this._stringDeserializer != null) {
                    return this._stringDeserializer.deserialize(jp, ctxt);
                }
                return jp.getText();
            case Std.STD_CURRENCY /*6*/:
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jp, ctxt);
                }
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jp.getBigIntegerValue();
                }
                return jp.getNumberValue();
            case Std.STD_PATTERN /*7*/:
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jp, ctxt);
                }
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jp.getDecimalValue();
                }
                return Double.valueOf(jp.getDoubleValue());
            case Std.STD_LOCALE /*8*/:
                return Boolean.TRUE;
            case Std.STD_CHARSET /*9*/:
                return Boolean.FALSE;
            case Std.STD_TIME_ZONE /*10*/:
                return null;
            default:
                throw ctxt.mappingException(Object.class);
        }
    }

    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        switch (C01811.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jp.getCurrentToken().ordinal()]) {
            case Std.STD_FILE /*1*/:
            case Std.STD_URL /*2*/:
            case Std.STD_URI /*3*/:
                return typeDeserializer.deserializeTypedFromAny(jp, ctxt);
            case Std.STD_CLASS /*4*/:
                return jp.getEmbeddedObject();
            case Std.STD_JAVA_TYPE /*5*/:
                if (this._stringDeserializer != null) {
                    return this._stringDeserializer.deserialize(jp, ctxt);
                }
                return jp.getText();
            case Std.STD_CURRENCY /*6*/:
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jp, ctxt);
                }
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jp.getBigIntegerValue();
                }
                return jp.getNumberValue();
            case Std.STD_PATTERN /*7*/:
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jp, ctxt);
                }
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jp.getDecimalValue();
                }
                return Double.valueOf(jp.getDoubleValue());
            case Std.STD_LOCALE /*8*/:
                return Boolean.TRUE;
            case Std.STD_CHARSET /*9*/:
                return Boolean.FALSE;
            case Std.STD_TIME_ZONE /*10*/:
                return null;
            default:
                throw ctxt.mappingException(Object.class);
        }
    }

    protected Object mapArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.nextToken() == JsonToken.END_ARRAY) {
            return new ArrayList(2);
        }
        Object value = deserialize(jp, ctxt);
        if (jp.nextToken() == JsonToken.END_ARRAY) {
            Object l = new ArrayList(2);
            l.add(value);
            return l;
        }
        Object value2 = deserialize(jp, ctxt);
        if (jp.nextToken() == JsonToken.END_ARRAY) {
            l = new ArrayList(2);
            l.add(value);
            l.add(value2);
            return l;
        }
        ObjectBuffer buffer = ctxt.leaseObjectBuffer();
        Object[] values = buffer.resetAndStart();
        int ptr = 0 + 1;
        values[0] = value;
        int ptr2 = ptr + 1;
        values[ptr] = value2;
        int totalSize = ptr2;
        while (true) {
            value = deserialize(jp, ctxt);
            totalSize++;
            if (ptr2 >= values.length) {
                values = buffer.appendCompletedChunk(values);
                ptr2 = 0;
            }
            ptr = ptr2 + 1;
            values[ptr2] = value;
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                List result = new ArrayList(totalSize);
                buffer.completeAndClearBuffer(values, ptr, result);
                return result;
            }
            ptr2 = ptr;
        }
    }

    protected Object mapObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        if (t == JsonToken.END_OBJECT) {
            return new LinkedHashMap(2);
        }
        String field1 = jp.getCurrentName();
        jp.nextToken();
        Object value1 = deserialize(jp, ctxt);
        if (jp.nextToken() == JsonToken.END_OBJECT) {
            Object result = new LinkedHashMap(2);
            result.put(field1, value1);
            return result;
        }
        String field2 = jp.getCurrentName();
        jp.nextToken();
        Object value2 = deserialize(jp, ctxt);
        if (jp.nextToken() == JsonToken.END_OBJECT) {
            result = new LinkedHashMap(4);
            result.put(field1, value1);
            result.put(field2, value2);
            return result;
        }
        result = new LinkedHashMap();
        result.put(field1, value1);
        result.put(field2, value2);
        do {
            String fieldName = jp.getCurrentName();
            jp.nextToken();
            result.put(fieldName, deserialize(jp, ctxt));
        } while (jp.nextToken() != JsonToken.END_OBJECT);
        return result;
    }

    protected Object[] mapArrayToArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.nextToken() == JsonToken.END_ARRAY) {
            return NO_OBJECTS;
        }
        ObjectBuffer buffer = ctxt.leaseObjectBuffer();
        Object[] values = buffer.resetAndStart();
        int ptr = 0;
        while (true) {
            Object value = deserialize(jp, ctxt);
            if (ptr >= values.length) {
                values = buffer.appendCompletedChunk(values);
                ptr = 0;
            }
            int ptr2 = ptr + 1;
            values[ptr] = value;
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                return buffer.completeAndClearBuffer(values, ptr2);
            }
            ptr = ptr2;
        }
    }
}
