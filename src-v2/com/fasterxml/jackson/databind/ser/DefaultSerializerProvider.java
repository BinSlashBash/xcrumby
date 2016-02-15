/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.SerializerCache;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DefaultSerializerProvider
extends SerializerProvider
implements Serializable {
    private static final long serialVersionUID = 1;
    protected transient ArrayList<ObjectIdGenerator<?>> _objectIdGenerators;
    protected transient Map<Object, WritableObjectId> _seenObjectIds;

    protected DefaultSerializerProvider() {
    }

    protected DefaultSerializerProvider(SerializerProvider serializerProvider, SerializationConfig serializationConfig, SerializerFactory serializerFactory) {
        super(serializerProvider, serializationConfig, serializerFactory);
    }

    protected Map<Object, WritableObjectId> _createObjectIdMap() {
        if (this.isEnabled(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID)) {
            return new HashMap<Object, WritableObjectId>();
        }
        return new IdentityHashMap<Object, WritableObjectId>();
    }

    protected void _serializeNull(JsonGenerator object) throws IOException {
        Object object2 = this.getDefaultNullValueSerializer();
        try {
            object2.serialize(null, (JsonGenerator)object, this);
            return;
        }
        catch (IOException var1_2) {
            throw var1_2;
        }
        catch (Exception var3_4) {
            object = object2 = var3_4.getMessage();
            if (object2 == null) {
                object = "[no message for " + var3_4.getClass().getName() + "]";
            }
            throw new JsonMappingException((String)object, var3_4);
        }
    }

    public void acceptJsonFormatVisitor(JavaType javaType, JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        if (javaType == null) {
            throw new IllegalArgumentException("A class must be provided");
        }
        jsonFormatVisitorWrapper.setProvider(this);
        this.findValueSerializer(javaType, null).acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
    }

    public int cachedSerializersCount() {
        return this._serializerCache.size();
    }

    public abstract DefaultSerializerProvider createInstance(SerializationConfig var1, SerializerFactory var2);

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public WritableObjectId findObjectId(Object var1_1, ObjectIdGenerator<?> var2_2) {
        if (this._seenObjectIds == null) {
            this._seenObjectIds = this._createObjectIdMap();
        } else {
            var5_3 = this._seenObjectIds.get(var1_1);
            if (var5_3 != null) {
                return var5_3;
            }
        }
        var6_4 = null;
        if (this._objectIdGenerators != null) {
            var3_5 = 0;
            var4_6 = this._objectIdGenerators.size();
        } else {
            this._objectIdGenerators = new ArrayList<E>(8);
            var5_3 = var6_4;
            do {
                var6_4 = var5_3;
                if (var5_3 == null) {
                    var6_4 = var2_2.newForSerialization(this);
                    this._objectIdGenerators.add((Object)var6_4);
                }
                var2_2 = new WritableObjectId(var6_4);
                this._seenObjectIds.put(var1_1, (WritableObjectId)var2_2);
                return var2_2;
                break;
            } while (true);
        }
        do {
            var5_3 = var6_4;
            if (var3_5 >= var4_6 || (var5_3 = this._objectIdGenerators.get(var3_5)).canUseFor(var2_2)) ** continue;
            ++var3_5;
        } while (true);
    }

    public void flushCachedSerializers() {
        this._serializerCache.flush();
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonSchema generateJsonSchema(Class<?> class_) throws JsonMappingException {
        if (class_ == null) {
            throw new IllegalArgumentException("A class must be provided");
        }
        JsonSerializer<Object> jsonSerializer = this.findValueSerializer(class_, null);
        jsonSerializer = jsonSerializer instanceof SchemaAware ? ((SchemaAware)((Object)jsonSerializer)).getSchema(this, null) : JsonSchema.getDefaultSchemaNode();
        if (!(jsonSerializer instanceof ObjectNode)) {
            throw new IllegalArgumentException("Class " + class_.getName() + " would not be serialized as a JSON object and therefore has no schema");
        }
        return new JsonSchema((ObjectNode)((Object)jsonSerializer));
    }

    @Deprecated
    public boolean hasSerializerFor(Class<?> class_) {
        return this.hasSerializerFor(class_, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasSerializerFor(Class<?> jsonSerializer, AtomicReference<Throwable> atomicReference) {
        boolean bl2 = false;
        try {
            jsonSerializer = this._findExplicitUntypedSerializer(jsonSerializer);
            if (jsonSerializer == null) return bl2;
            return true;
        }
        catch (JsonMappingException var1_2) {
            if (atomicReference == null) return bl2;
            atomicReference.set(var1_2);
            return false;
        }
        catch (RuntimeException var1_3) {
            if (atomicReference == null) {
                throw var1_3;
            }
            atomicReference.set(var1_3);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void serializeValue(JsonGenerator object, Object object2) throws IOException {
        boolean bl2;
        if (object2 == null) {
            this._serializeNull((JsonGenerator)object);
            return;
        }
        JsonSerializer<Object> jsonSerializer = this.findTypedValueSerializer(object2.getClass(), true, null);
        Object object3 = this._config.getRootName();
        if (object3 == null) {
            boolean bl3;
            bl2 = bl3 = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
            if (bl3) {
                object3 = this._rootNames.findRootName(object2.getClass(), this._config);
                object.writeStartObject();
                object.writeFieldName(object3.simpleAsEncoded(this._config));
                bl2 = bl3;
            }
        } else if (object3.length() == 0) {
            bl2 = false;
        } else {
            bl2 = true;
            object.writeStartObject();
            object.writeFieldName((String)object3);
        }
        try {
            jsonSerializer.serialize(object2, (JsonGenerator)object, this);
            if (!bl2) return;
            object.writeEndObject();
            return;
        }
        catch (IOException var1_2) {
            throw var1_2;
        }
        catch (Exception var5_5) {
            object = object2 = var5_5.getMessage();
            if (object2 != null) throw new JsonMappingException((String)object, var5_5);
            {
                object = "[no message for " + var5_5.getClass().getName() + "]";
            }
            throw new JsonMappingException((String)object, var5_5);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void serializeValue(JsonGenerator object, Object object2, JavaType jsonSerializer) throws IOException {
        boolean bl2;
        if (object2 == null) {
            this._serializeNull((JsonGenerator)object);
            return;
        }
        if (!jsonSerializer.getRawClass().isAssignableFrom(object2.getClass())) {
            this._reportIncompatibleRootType(object2, (JavaType)((Object)jsonSerializer));
        }
        jsonSerializer = this.findTypedValueSerializer((JavaType)((Object)jsonSerializer), true, null);
        String string2 = this._config.getRootName();
        if (string2 == null) {
            boolean bl3;
            bl2 = bl3 = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
            if (bl3) {
                object.writeStartObject();
                object.writeFieldName(this._rootNames.findRootName(object2.getClass(), this._config).simpleAsEncoded(this._config));
                bl2 = bl3;
            }
        } else if (string2.length() == 0) {
            bl2 = false;
        } else {
            bl2 = true;
            object.writeStartObject();
            object.writeFieldName(string2);
        }
        try {
            jsonSerializer.serialize(object2, (JsonGenerator)object, this);
            if (!bl2) return;
            object.writeEndObject();
            return;
        }
        catch (IOException var1_2) {
            throw var1_2;
        }
        catch (Exception var3_5) {
            object = object2 = var3_5.getMessage();
            if (object2 != null) throw new JsonMappingException((String)object, var3_5);
            {
                object = "[no message for " + var3_5.getClass().getName() + "]";
            }
            throw new JsonMappingException((String)object, var3_5);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void serializeValue(JsonGenerator object, Object object2, JavaType javaType, JsonSerializer<Object> object3) throws IOException {
        boolean bl2;
        String string2;
        if (object2 == null) {
            this._serializeNull((JsonGenerator)object);
            return;
        }
        if (javaType != null && !javaType.getRawClass().isAssignableFrom(object2.getClass())) {
            this._reportIncompatibleRootType(object2, javaType);
        }
        JsonSerializer<Object> jsonSerializer = string2;
        if (string2 == null) {
            jsonSerializer = this.findTypedValueSerializer(javaType, true, null);
        }
        if ((string2 = this._config.getRootName()) == null) {
            boolean bl3;
            bl2 = bl3 = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
            if (bl3) {
                void var3_6;
                object.writeStartObject();
                if (javaType == null) {
                    PropertyName propertyName = this._rootNames.findRootName(object2.getClass(), this._config);
                } else {
                    PropertyName propertyName = this._rootNames.findRootName(javaType, this._config);
                }
                object.writeFieldName(var3_6.simpleAsEncoded(this._config));
                bl2 = bl3;
            }
        } else if (string2.length() == 0) {
            bl2 = false;
        } else {
            bl2 = true;
            object.writeStartObject();
            object.writeFieldName(string2);
        }
        try {
            jsonSerializer.serialize(object2, (JsonGenerator)object, this);
            if (!bl2) return;
            object.writeEndObject();
            return;
        }
        catch (IOException var1_2) {
            throw var1_2;
        }
        catch (Exception var3_8) {
            object = object2 = var3_8.getMessage();
            if (object2 != null) throw new JsonMappingException((String)object, var3_8);
            {
                object = "[no message for " + var3_8.getClass().getName() + "]";
            }
            throw new JsonMappingException((String)object, var3_8);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSerializer<Object> serializerInstance(Annotated object, Object object2) throws JsonMappingException {
        if (object2 == null) {
            return null;
        }
        if (object2 instanceof JsonSerializer) {
            object = (JsonSerializer)object2;
            return this._handleResolvable(object);
        }
        if (!(object2 instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector returned serializer definition of type " + object2.getClass().getName() + "; expected type JsonSerializer or Class<JsonSerializer> instead");
        }
        Class class_ = (Class)object2;
        if (class_ == JsonSerializer.None.class) return null;
        if (ClassUtil.isBogusClass(class_)) return null;
        if (!JsonSerializer.class.isAssignableFrom(class_)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + class_.getName() + "; expected Class<JsonSerializer>");
        }
        object2 = this._config.getHandlerInstantiator();
        object2 = object2 == null ? null : object2.serializerInstance(this._config, (Annotated)object, class_);
        object = object2;
        if (object2 != null) return this._handleResolvable(object);
        object = (JsonSerializer)ClassUtil.createInstance(class_, this._config.canOverrideAccessModifiers());
        return this._handleResolvable(object);
    }

    public static final class Impl
    extends DefaultSerializerProvider {
        private static final long serialVersionUID = 1;

        public Impl() {
        }

        protected Impl(SerializerProvider serializerProvider, SerializationConfig serializationConfig, SerializerFactory serializerFactory) {
            super(serializerProvider, serializationConfig, serializerFactory);
        }

        @Override
        public Impl createInstance(SerializationConfig serializationConfig, SerializerFactory serializerFactory) {
            return new Impl(this, serializationConfig, serializerFactory);
        }
    }

}

