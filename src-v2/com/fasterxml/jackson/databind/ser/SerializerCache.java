/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
import java.util.HashMap;

public final class SerializerCache {
    private volatile ReadOnlyClassToSerializerMap _readOnlyMap = null;
    private HashMap<TypeKey, JsonSerializer<Object>> _sharedMap = new HashMap(64);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addAndResolveNonTypedSerializer(JavaType javaType, JsonSerializer<Object> jsonSerializer, SerializerProvider serializerProvider) throws JsonMappingException {
        synchronized (this) {
            if (this._sharedMap.put(new TypeKey(javaType, false), jsonSerializer) == null) {
                this._readOnlyMap = null;
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer)((Object)jsonSerializer)).resolve(serializerProvider);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addAndResolveNonTypedSerializer(Class<?> class_, JsonSerializer<Object> jsonSerializer, SerializerProvider serializerProvider) throws JsonMappingException {
        synchronized (this) {
            if (this._sharedMap.put(new TypeKey(class_, false), jsonSerializer) == null) {
                this._readOnlyMap = null;
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer)((Object)jsonSerializer)).resolve(serializerProvider);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addTypedSerializer(JavaType javaType, JsonSerializer<Object> jsonSerializer) {
        synchronized (this) {
            if (this._sharedMap.put(new TypeKey(javaType, true), jsonSerializer) == null) {
                this._readOnlyMap = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addTypedSerializer(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
        synchronized (this) {
            if (this._sharedMap.put(new TypeKey(class_, true), jsonSerializer) == null) {
                this._readOnlyMap = null;
            }
            return;
        }
    }

    public void flush() {
        synchronized (this) {
            this._sharedMap.clear();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ReadOnlyClassToSerializerMap getReadOnlyLookupMap() {
        ReadOnlyClassToSerializerMap readOnlyClassToSerializerMap;
        ReadOnlyClassToSerializerMap readOnlyClassToSerializerMap2 = readOnlyClassToSerializerMap = this._readOnlyMap;
        if (readOnlyClassToSerializerMap == null) {
            synchronized (this) {
                readOnlyClassToSerializerMap2 = readOnlyClassToSerializerMap = this._readOnlyMap;
                if (readOnlyClassToSerializerMap == null) {
                    this._readOnlyMap = readOnlyClassToSerializerMap2 = ReadOnlyClassToSerializerMap.from(this._sharedMap);
                }
            }
        }
        return readOnlyClassToSerializerMap2.instance();
    }

    public int size() {
        synchronized (this) {
            int n2 = this._sharedMap.size();
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonSerializer<Object> typedValueSerializer(JavaType object) {
        synchronized (this) {
            return this._sharedMap.get(new TypeKey((JavaType)object, true));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonSerializer<Object> typedValueSerializer(Class<?> object) {
        synchronized (this) {
            return this._sharedMap.get(new TypeKey(object, true));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonSerializer<Object> untypedValueSerializer(JavaType object) {
        synchronized (this) {
            return this._sharedMap.get(new TypeKey((JavaType)object, false));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonSerializer<Object> untypedValueSerializer(Class<?> object) {
        synchronized (this) {
            return this._sharedMap.get(new TypeKey(object, false));
        }
    }

    public static final class TypeKey {
        protected Class<?> _class;
        protected int _hashCode;
        protected boolean _isTyped;
        protected JavaType _type;

        public TypeKey(JavaType javaType, boolean bl2) {
            this._type = javaType;
            this._class = null;
            this._isTyped = bl2;
            this._hashCode = TypeKey.hash(javaType, bl2);
        }

        public TypeKey(Class<?> class_, boolean bl2) {
            this._class = class_;
            this._type = null;
            this._isTyped = bl2;
            this._hashCode = TypeKey.hash(class_, bl2);
        }

        private static final int hash(JavaType javaType, boolean bl2) {
            int n2;
            int n3 = n2 = javaType.hashCode() - 1;
            if (bl2) {
                n3 = n2 - 1;
            }
            return n3;
        }

        private static final int hash(Class<?> class_, boolean bl2) {
            int n2;
            int n3 = n2 = class_.getName().hashCode();
            if (bl2) {
                n3 = n2 + 1;
            }
            return n3;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public final boolean equals(Object object) {
            boolean bl2 = true;
            if (object == null) {
                return false;
            }
            if (object == this) {
                return true;
            }
            if (object.getClass() != this.getClass()) return false;
            object = (TypeKey)object;
            if (object._isTyped != this._isTyped) return false;
            if (this._class == null) return this._type.equals(object._type);
            if (object._class != this._class) return false;
            return bl2;
        }

        public final int hashCode() {
            return this._hashCode;
        }

        public void resetTyped(JavaType javaType) {
            this._type = javaType;
            this._class = null;
            this._isTyped = true;
            this._hashCode = TypeKey.hash(javaType, true);
        }

        public void resetTyped(Class<?> class_) {
            this._type = null;
            this._class = class_;
            this._isTyped = true;
            this._hashCode = TypeKey.hash(class_, true);
        }

        public void resetUntyped(JavaType javaType) {
            this._type = javaType;
            this._class = null;
            this._isTyped = false;
            this._hashCode = TypeKey.hash(javaType, false);
        }

        public void resetUntyped(Class<?> class_) {
            this._type = null;
            this._class = class_;
            this._isTyped = false;
            this._hashCode = TypeKey.hash(class_, false);
        }

        public final String toString() {
            if (this._class != null) {
                return "{class: " + this._class.getName() + ", typed? " + this._isTyped + "}";
            }
            return "{type: " + this._type + ", typed? " + this._isTyped + "}";
        }
    }

}

