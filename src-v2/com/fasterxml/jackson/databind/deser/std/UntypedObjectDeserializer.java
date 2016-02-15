/*
 * Decompiled with CFR 0_110.
 */
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
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JacksonStdImpl
public class UntypedObjectDeserializer
extends StdDeserializer<Object>
implements ResolvableDeserializer,
ContextualDeserializer {
    protected static final Object[] NO_OBJECTS = new Object[0];
    @Deprecated
    public static final UntypedObjectDeserializer instance = new UntypedObjectDeserializer();
    private static final long serialVersionUID = 1;
    protected JsonDeserializer<Object> _listDeserializer;
    protected JsonDeserializer<Object> _mapDeserializer;
    protected JsonDeserializer<Object> _numberDeserializer;
    protected JsonDeserializer<Object> _stringDeserializer;

    public UntypedObjectDeserializer() {
        super(Object.class);
    }

    public UntypedObjectDeserializer(UntypedObjectDeserializer untypedObjectDeserializer, JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, JsonDeserializer<?> jsonDeserializer3, JsonDeserializer<?> jsonDeserializer4) {
        super(Object.class);
        this._mapDeserializer = jsonDeserializer;
        this._listDeserializer = jsonDeserializer2;
        this._stringDeserializer = jsonDeserializer3;
        this._numberDeserializer = jsonDeserializer4;
    }

    protected JsonDeserializer<Object> _findCustomDeser(DeserializationContext object, JavaType object2) throws JsonMappingException {
        object = object2 = object.findRootValueDeserializer((JavaType)object2);
        if (ClassUtil.isJacksonStdImpl(object2)) {
            object = null;
        }
        return object;
    }

    protected JsonDeserializer<?> _withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, JsonDeserializer<?> jsonDeserializer3, JsonDeserializer<?> jsonDeserializer4) {
        return new UntypedObjectDeserializer(this, jsonDeserializer, jsonDeserializer2, jsonDeserializer3, jsonDeserializer4);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext serializable, BeanProperty beanProperty) throws JsonMappingException {
        void var2_5;
        JsonDeserializer<Object> jsonDeserializer;
        void var5_16;
        void var4_9;
        void var6_20;
        JsonDeserializer<Object> jsonDeserializer2;
        JsonDeserializer<Object> jsonDeserializer3;
        void var3_12;
        void var1_3;
        JsonDeserializer<Object> jsonDeserializer4;
        if (this._stringDeserializer == null && this._numberDeserializer == null && this._mapDeserializer == null && this._listDeserializer == null && this.getClass() == UntypedObjectDeserializer.class) {
            Vanilla vanilla = Vanilla.std;
            return var1_3;
        }
        JsonDeserializer<Object> jsonDeserializer5 = jsonDeserializer3 = this._mapDeserializer;
        if (jsonDeserializer3 instanceof ContextualDeserializer) {
            JsonDeserializer jsonDeserializer6 = ((ContextualDeserializer)((Object)jsonDeserializer3)).createContextual((DeserializationContext)serializable, (BeanProperty)var2_5);
        }
        JsonDeserializer<Object> jsonDeserializer7 = jsonDeserializer4 = this._listDeserializer;
        if (jsonDeserializer4 instanceof ContextualDeserializer) {
            JsonDeserializer jsonDeserializer8 = ((ContextualDeserializer)((Object)jsonDeserializer4)).createContextual((DeserializationContext)serializable, (BeanProperty)var2_5);
        }
        JsonDeserializer<Object> jsonDeserializer9 = jsonDeserializer2 = this._stringDeserializer;
        if (jsonDeserializer2 instanceof ContextualDeserializer) {
            JsonDeserializer jsonDeserializer10 = ((ContextualDeserializer)((Object)jsonDeserializer2)).createContextual((DeserializationContext)serializable, (BeanProperty)var2_5);
        }
        JsonDeserializer<Object> jsonDeserializer11 = jsonDeserializer = this._numberDeserializer;
        if (jsonDeserializer instanceof ContextualDeserializer) {
            JsonDeserializer jsonDeserializer12 = ((ContextualDeserializer)((Object)jsonDeserializer)).createContextual((DeserializationContext)serializable, (BeanProperty)var2_5);
        }
        if (var3_12 != this._mapDeserializer || var4_9 != this._listDeserializer || var5_16 != this._stringDeserializer) return this._withResolved(var3_12, var4_9, var5_16, var6_20);
        UntypedObjectDeserializer untypedObjectDeserializer = this;
        if (var6_20 == this._numberDeserializer) return var1_3;
        return this._withResolved(var3_12, var4_9, var5_16, var6_20);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
            default: {
                throw deserializationContext.mappingException(Object.class);
            }
            case 1: 
            case 2: {
                if (this._mapDeserializer != null) {
                    return this._mapDeserializer.deserialize(jsonParser, deserializationContext);
                }
                return this.mapObject(jsonParser, deserializationContext);
            }
            case 3: {
                if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                    return this.mapArrayToArray(jsonParser, deserializationContext);
                }
                if (this._listDeserializer != null) {
                    return this._listDeserializer.deserialize(jsonParser, deserializationContext);
                }
                return this.mapArray(jsonParser, deserializationContext);
            }
            case 4: {
                return jsonParser.getEmbeddedObject();
            }
            case 5: {
                if (this._stringDeserializer != null) {
                    return this._stringDeserializer.deserialize(jsonParser, deserializationContext);
                }
                return jsonParser.getText();
            }
            case 6: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext);
                }
                if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jsonParser.getBigIntegerValue();
                }
                return jsonParser.getNumberValue();
            }
            case 7: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext);
                }
                if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jsonParser.getDecimalValue();
                }
                return jsonParser.getDoubleValue();
            }
            case 8: {
                return Boolean.TRUE;
            }
            case 9: {
                return Boolean.FALSE;
            }
            case 10: 
        }
        return null;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonToken.ordinal()]) {
            default: {
                throw deserializationContext.mappingException(Object.class);
            }
            case 1: 
            case 2: 
            case 3: {
                return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
            }
            case 5: {
                if (this._stringDeserializer != null) {
                    return this._stringDeserializer.deserialize(jsonParser, deserializationContext);
                }
                return jsonParser.getText();
            }
            case 6: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext);
                }
                if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jsonParser.getBigIntegerValue();
                }
                return jsonParser.getNumberValue();
            }
            case 7: {
                if (this._numberDeserializer != null) {
                    return this._numberDeserializer.deserialize(jsonParser, deserializationContext);
                }
                if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jsonParser.getDecimalValue();
                }
                return jsonParser.getDoubleValue();
            }
            case 8: {
                return Boolean.TRUE;
            }
            case 9: {
                return Boolean.FALSE;
            }
            case 4: {
                return jsonParser.getEmbeddedObject();
            }
            case 10: 
        }
        return null;
    }

    protected Object mapArray(JsonParser arrayList, DeserializationContext deserializationContext) throws IOException {
        if (arrayList.nextToken() == JsonToken.END_ARRAY) {
            return new ArrayList(2);
        }
        Object[] arrobject = this.deserialize((JsonParser)((Object)arrayList), deserializationContext);
        if (arrayList.nextToken() == JsonToken.END_ARRAY) {
            arrayList = new ArrayList(2);
            arrayList.add(arrobject);
            return arrayList;
        }
        Object object = this.deserialize((JsonParser)((Object)arrayList), deserializationContext);
        if (arrayList.nextToken() == JsonToken.END_ARRAY) {
            arrayList = new ArrayList<Object>(2);
            arrayList.add(arrobject);
            arrayList.add(object);
            return arrayList;
        }
        ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] arrobject2 = objectBuffer.resetAndStart();
        int n2 = 0 + 1;
        arrobject2[0] = arrobject;
        int n3 = n2 + 1;
        arrobject2[n2] = object;
        n2 = n3;
        do {
            object = this.deserialize((JsonParser)((Object)arrayList), deserializationContext);
            int n4 = n2 + 1;
            n2 = n3;
            arrobject = arrobject2;
            if (n3 >= arrobject2.length) {
                arrobject = objectBuffer.appendCompletedChunk(arrobject2);
                n2 = 0;
            }
            n3 = n2 + 1;
            arrobject[n2] = object;
            if (arrayList.nextToken() == JsonToken.END_ARRAY) {
                arrayList = new ArrayList(n4);
                objectBuffer.completeAndClearBuffer(arrobject, n3, arrayList);
                return arrayList;
            }
            n2 = n4;
            arrobject2 = arrobject;
        } while (true);
    }

    protected Object[] mapArrayToArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            return NO_OBJECTS;
        }
        ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] arrobject = objectBuffer.resetAndStart();
        int n2 = 0;
        do {
            Object object = this.deserialize(jsonParser, deserializationContext);
            int n3 = n2;
            Object[] arrobject2 = arrobject;
            if (n2 >= arrobject.length) {
                arrobject2 = objectBuffer.appendCompletedChunk(arrobject);
                n3 = 0;
            }
            n2 = n3 + 1;
            arrobject2[n3] = object;
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                return objectBuffer.completeAndClearBuffer(arrobject2, n2);
            }
            arrobject = arrobject2;
        } while (true);
    }

    protected Object mapObject(JsonParser linkedHashMap, DeserializationContext deserializationContext) throws IOException {
        Object object;
        Object object2 = object = linkedHashMap.getCurrentToken();
        if (object == JsonToken.START_OBJECT) {
            object2 = linkedHashMap.nextToken();
        }
        if (object2 == JsonToken.END_OBJECT) {
            return new LinkedHashMap(2);
        }
        object2 = linkedHashMap.getCurrentName();
        linkedHashMap.nextToken();
        object = this.deserialize((JsonParser)((Object)linkedHashMap), deserializationContext);
        if (linkedHashMap.nextToken() == JsonToken.END_OBJECT) {
            linkedHashMap = new LinkedHashMap(2);
            linkedHashMap.put(object2, object);
            return linkedHashMap;
        }
        String string2 = linkedHashMap.getCurrentName();
        linkedHashMap.nextToken();
        Object object3 = this.deserialize((JsonParser)((Object)linkedHashMap), deserializationContext);
        if (linkedHashMap.nextToken() == JsonToken.END_OBJECT) {
            linkedHashMap = new LinkedHashMap<Object, Object>(4);
            linkedHashMap.put(object2, object);
            linkedHashMap.put(string2, object3);
            return linkedHashMap;
        }
        LinkedHashMap<Object, Object> linkedHashMap2 = new LinkedHashMap<Object, Object>();
        linkedHashMap2.put(object2, object);
        linkedHashMap2.put(string2, object3);
        do {
            object2 = linkedHashMap.getCurrentName();
            linkedHashMap.nextToken();
            linkedHashMap2.put(object2, this.deserialize((JsonParser)((Object)linkedHashMap), deserializationContext));
        } while (linkedHashMap.nextToken() != JsonToken.END_OBJECT);
        return linkedHashMap2;
    }

    @Override
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        JavaType javaType = deserializationContext.constructType(Object.class);
        JavaType javaType2 = deserializationContext.constructType(String.class);
        TypeFactory typeFactory = deserializationContext.getTypeFactory();
        this._mapDeserializer = this._findCustomDeser(deserializationContext, typeFactory.constructMapType(Map.class, javaType2, javaType));
        this._listDeserializer = this._findCustomDeser(deserializationContext, typeFactory.constructCollectionType(List.class, javaType));
        this._stringDeserializer = this._findCustomDeser(deserializationContext, javaType2);
        this._numberDeserializer = this._findCustomDeser(deserializationContext, typeFactory.constructType(Number.class));
    }

    @JacksonStdImpl
    public static class Vanilla
    extends StdDeserializer<Object> {
        private static final long serialVersionUID = 1;
        public static final Vanilla std = new Vanilla();

        public Vanilla() {
            super(Object.class);
        }

        @Override
        public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            switch (jsonParser.getCurrentTokenId()) {
                default: {
                    throw deserializationContext.mappingException(Object.class);
                }
                case 1: {
                    if (jsonParser.nextToken() == JsonToken.END_OBJECT) {
                        return new LinkedHashMap(2);
                    }
                }
                case 5: {
                    return this.mapObject(jsonParser, deserializationContext);
                }
                case 3: {
                    if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                        if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                            return UntypedObjectDeserializer.NO_OBJECTS;
                        }
                        return new ArrayList(2);
                    }
                    if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                        return this.mapArrayToArray(jsonParser, deserializationContext);
                    }
                    return this.mapArray(jsonParser, deserializationContext);
                }
                case 12: {
                    return jsonParser.getEmbeddedObject();
                }
                case 6: {
                    return jsonParser.getText();
                }
                case 7: {
                    if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                        return jsonParser.getBigIntegerValue();
                    }
                    return jsonParser.getNumberValue();
                }
                case 8: {
                    if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                        return jsonParser.getDecimalValue();
                    }
                    return jsonParser.getDoubleValue();
                }
                case 9: {
                    return Boolean.TRUE;
                }
                case 10: {
                    return Boolean.FALSE;
                }
                case 11: 
            }
            return null;
        }

        @Override
        public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
            switch (jsonParser.getCurrentTokenId()) {
                default: {
                    throw deserializationContext.mappingException(Object.class);
                }
                case 1: 
                case 3: 
                case 5: {
                    return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
                }
                case 6: {
                    return jsonParser.getText();
                }
                case 7: {
                    if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                        return jsonParser.getBigIntegerValue();
                    }
                    return jsonParser.getNumberValue();
                }
                case 8: {
                    if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                        return jsonParser.getDecimalValue();
                    }
                    return jsonParser.getDoubleValue();
                }
                case 9: {
                    return Boolean.TRUE;
                }
                case 10: {
                    return Boolean.FALSE;
                }
                case 12: {
                    return jsonParser.getEmbeddedObject();
                }
                case 11: 
            }
            return null;
        }

        protected Object mapArray(JsonParser arrayList, DeserializationContext deserializationContext) throws IOException {
            Object[] arrobject = this.deserialize((JsonParser)((Object)arrayList), deserializationContext);
            if (arrayList.nextToken() == JsonToken.END_ARRAY) {
                arrayList = new ArrayList(2);
                arrayList.add(arrobject);
                return arrayList;
            }
            Object object = this.deserialize((JsonParser)((Object)arrayList), deserializationContext);
            if (arrayList.nextToken() == JsonToken.END_ARRAY) {
                arrayList = new ArrayList<Object>(2);
                arrayList.add(arrobject);
                arrayList.add(object);
                return arrayList;
            }
            ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
            Object[] arrobject2 = objectBuffer.resetAndStart();
            int n2 = 0 + 1;
            arrobject2[0] = arrobject;
            int n3 = n2 + 1;
            arrobject2[n2] = object;
            n2 = n3;
            do {
                object = this.deserialize((JsonParser)((Object)arrayList), deserializationContext);
                int n4 = n2 + 1;
                n2 = n3;
                arrobject = arrobject2;
                if (n3 >= arrobject2.length) {
                    arrobject = objectBuffer.appendCompletedChunk(arrobject2);
                    n2 = 0;
                }
                n3 = n2 + 1;
                arrobject[n2] = object;
                if (arrayList.nextToken() == JsonToken.END_ARRAY) {
                    arrayList = new ArrayList(n4);
                    objectBuffer.completeAndClearBuffer(arrobject, n3, arrayList);
                    return arrayList;
                }
                n2 = n4;
                arrobject2 = arrobject;
            } while (true);
        }

        protected Object[] mapArrayToArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
            Object[] arrobject = objectBuffer.resetAndStart();
            int n2 = 0;
            do {
                Object object = this.deserialize(jsonParser, deserializationContext);
                int n3 = n2;
                Object[] arrobject2 = arrobject;
                if (n2 >= arrobject.length) {
                    arrobject2 = objectBuffer.appendCompletedChunk(arrobject);
                    n3 = 0;
                }
                n2 = n3 + 1;
                arrobject2[n3] = object;
                if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                    return objectBuffer.completeAndClearBuffer(arrobject2, n2);
                }
                arrobject = arrobject2;
            } while (true);
        }

        protected Object mapObject(JsonParser linkedHashMap, DeserializationContext deserializationContext) throws IOException {
            String string2 = linkedHashMap.getText();
            linkedHashMap.nextToken();
            Object object = this.deserialize((JsonParser)((Object)linkedHashMap), deserializationContext);
            if (linkedHashMap.nextToken() == JsonToken.END_OBJECT) {
                linkedHashMap = new LinkedHashMap(2);
                linkedHashMap.put(string2, object);
                return linkedHashMap;
            }
            String string3 = linkedHashMap.getText();
            linkedHashMap.nextToken();
            Object object2 = this.deserialize((JsonParser)((Object)linkedHashMap), deserializationContext);
            if (linkedHashMap.nextToken() == JsonToken.END_OBJECT) {
                linkedHashMap = new LinkedHashMap<String, Object>(4);
                linkedHashMap.put(string2, object);
                linkedHashMap.put(string3, object2);
                return linkedHashMap;
            }
            LinkedHashMap<String, Object> linkedHashMap2 = new LinkedHashMap<String, Object>();
            linkedHashMap2.put(string2, object);
            linkedHashMap2.put(string3, object2);
            do {
                string2 = linkedHashMap.getText();
                linkedHashMap.nextToken();
                linkedHashMap2.put(string2, this.deserialize((JsonParser)((Object)linkedHashMap), deserializationContext));
            } while (linkedHashMap.nextToken() != JsonToken.END_OBJECT);
            return linkedHashMap2;
        }
    }

}

