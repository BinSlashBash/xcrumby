/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.SerializerCache;
import com.fasterxml.jackson.databind.ser.impl.JsonSerializerMap;
import java.util.HashMap;
import java.util.Map;

public final class ReadOnlyClassToSerializerMap {
    protected SerializerCache.TypeKey _cacheKey = null;
    protected final JsonSerializerMap _map;

    private ReadOnlyClassToSerializerMap(JsonSerializerMap jsonSerializerMap) {
        this._map = jsonSerializerMap;
    }

    public static ReadOnlyClassToSerializerMap from(HashMap<SerializerCache.TypeKey, JsonSerializer<Object>> hashMap) {
        return new ReadOnlyClassToSerializerMap(new JsonSerializerMap(hashMap));
    }

    public ReadOnlyClassToSerializerMap instance() {
        return new ReadOnlyClassToSerializerMap(this._map);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JsonSerializer<Object> typedValueSerializer(JavaType javaType) {
        if (this._cacheKey == null) {
            this._cacheKey = new SerializerCache.TypeKey(javaType, true);
            do {
                return this._map.find(this._cacheKey);
                break;
            } while (true);
        }
        this._cacheKey.resetTyped(javaType);
        return this._map.find(this._cacheKey);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JsonSerializer<Object> typedValueSerializer(Class<?> class_) {
        if (this._cacheKey == null) {
            this._cacheKey = new SerializerCache.TypeKey(class_, true);
            do {
                return this._map.find(this._cacheKey);
                break;
            } while (true);
        }
        this._cacheKey.resetTyped(class_);
        return this._map.find(this._cacheKey);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JsonSerializer<Object> untypedValueSerializer(JavaType javaType) {
        if (this._cacheKey == null) {
            this._cacheKey = new SerializerCache.TypeKey(javaType, false);
            do {
                return this._map.find(this._cacheKey);
                break;
            } while (true);
        }
        this._cacheKey.resetUntyped(javaType);
        return this._map.find(this._cacheKey);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JsonSerializer<Object> untypedValueSerializer(Class<?> class_) {
        if (this._cacheKey == null) {
            this._cacheKey = new SerializerCache.TypeKey(class_, false);
            do {
                return this._map.find(this._cacheKey);
                break;
            } while (true);
        }
        this._cacheKey.resetUntyped(class_);
        return this._map.find(this._cacheKey);
    }
}

