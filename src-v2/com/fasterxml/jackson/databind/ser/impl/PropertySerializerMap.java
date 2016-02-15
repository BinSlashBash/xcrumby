/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public abstract class PropertySerializerMap {
    public static PropertySerializerMap emptyMap() {
        return Empty.instance;
    }

    public final SerializerAndMapResult findAndAddPrimarySerializer(JavaType javaType, SerializerProvider object, BeanProperty beanProperty) throws JsonMappingException {
        object = object.findPrimaryPropertySerializer(javaType, beanProperty);
        return new SerializerAndMapResult((JsonSerializer<Object>)object, this.newWith(javaType.getRawClass(), (JsonSerializer<Object>)object));
    }

    public final SerializerAndMapResult findAndAddPrimarySerializer(Class<?> class_, SerializerProvider object, BeanProperty beanProperty) throws JsonMappingException {
        object = object.findPrimaryPropertySerializer(class_, beanProperty);
        return new SerializerAndMapResult((JsonSerializer<Object>)object, this.newWith(class_, (JsonSerializer<Object>)object));
    }

    public final SerializerAndMapResult findAndAddSecondarySerializer(JavaType javaType, SerializerProvider object, BeanProperty beanProperty) throws JsonMappingException {
        object = object.findValueSerializer(javaType, beanProperty);
        return new SerializerAndMapResult((JsonSerializer<Object>)object, this.newWith(javaType.getRawClass(), (JsonSerializer<Object>)object));
    }

    public final SerializerAndMapResult findAndAddSecondarySerializer(Class<?> class_, SerializerProvider object, BeanProperty beanProperty) throws JsonMappingException {
        object = object.findValueSerializer(class_, beanProperty);
        return new SerializerAndMapResult((JsonSerializer<Object>)object, this.newWith(class_, (JsonSerializer<Object>)object));
    }

    @Deprecated
    public final SerializerAndMapResult findAndAddSerializer(JavaType javaType, SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        return this.findAndAddSecondarySerializer(javaType, serializerProvider, beanProperty);
    }

    @Deprecated
    public final SerializerAndMapResult findAndAddSerializer(Class<?> class_, SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        return this.findAndAddSecondarySerializer(class_, serializerProvider, beanProperty);
    }

    public abstract PropertySerializerMap newWith(Class<?> var1, JsonSerializer<Object> var2);

    public abstract JsonSerializer<Object> serializerFor(Class<?> var1);

    private static final class Double
    extends PropertySerializerMap {
        private final JsonSerializer<Object> _serializer1;
        private final JsonSerializer<Object> _serializer2;
        private final Class<?> _type1;
        private final Class<?> _type2;

        public Double(Class<?> class_, JsonSerializer<Object> jsonSerializer, Class<?> class_2, JsonSerializer<Object> jsonSerializer2) {
            this._type1 = class_;
            this._serializer1 = jsonSerializer;
            this._type2 = class_2;
            this._serializer2 = jsonSerializer2;
        }

        @Override
        public PropertySerializerMap newWith(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            return new Multi(new TypeAndSerializer[]{new TypeAndSerializer(this._type1, this._serializer1), new TypeAndSerializer(this._type2, this._serializer2)});
        }

        @Override
        public JsonSerializer<Object> serializerFor(Class<?> class_) {
            if (class_ == this._type1) {
                return this._serializer1;
            }
            if (class_ == this._type2) {
                return this._serializer2;
            }
            return null;
        }
    }

    private static final class Empty
    extends PropertySerializerMap {
        protected static final Empty instance = new Empty();

        private Empty() {
        }

        @Override
        public PropertySerializerMap newWith(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            return new Single(class_, jsonSerializer);
        }

        @Override
        public JsonSerializer<Object> serializerFor(Class<?> class_) {
            return null;
        }
    }

    private static final class Multi
    extends PropertySerializerMap {
        private static final int MAX_ENTRIES = 8;
        private final TypeAndSerializer[] _entries;

        public Multi(TypeAndSerializer[] arrtypeAndSerializer) {
            this._entries = arrtypeAndSerializer;
        }

        @Override
        public PropertySerializerMap newWith(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            int n2 = this._entries.length;
            if (n2 == 8) {
                return this;
            }
            TypeAndSerializer[] arrtypeAndSerializer = new TypeAndSerializer[n2 + 1];
            System.arraycopy(this._entries, 0, arrtypeAndSerializer, 0, n2);
            arrtypeAndSerializer[n2] = new TypeAndSerializer(class_, jsonSerializer);
            return new Multi(arrtypeAndSerializer);
        }

        @Override
        public JsonSerializer<Object> serializerFor(Class<?> class_) {
            for (TypeAndSerializer typeAndSerializer : this._entries) {
                if (typeAndSerializer.type != class_) continue;
                return typeAndSerializer.serializer;
            }
            return null;
        }
    }

    public static final class SerializerAndMapResult {
        public final PropertySerializerMap map;
        public final JsonSerializer<Object> serializer;

        public SerializerAndMapResult(JsonSerializer<Object> jsonSerializer, PropertySerializerMap propertySerializerMap) {
            this.serializer = jsonSerializer;
            this.map = propertySerializerMap;
        }
    }

    private static final class Single
    extends PropertySerializerMap {
        private final JsonSerializer<Object> _serializer;
        private final Class<?> _type;

        public Single(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            this._type = class_;
            this._serializer = jsonSerializer;
        }

        @Override
        public PropertySerializerMap newWith(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            return new Double(this._type, this._serializer, class_, jsonSerializer);
        }

        @Override
        public JsonSerializer<Object> serializerFor(Class<?> class_) {
            if (class_ == this._type) {
                return this._serializer;
            }
            return null;
        }
    }

    private static final class TypeAndSerializer {
        public final JsonSerializer<Object> serializer;
        public final Class<?> type;

        public TypeAndSerializer(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            this.type = class_;
            this.serializer = jsonSerializer;
        }
    }

}

