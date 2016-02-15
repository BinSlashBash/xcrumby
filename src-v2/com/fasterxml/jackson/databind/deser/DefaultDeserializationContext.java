/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.deser.DeserializerCache;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class DefaultDeserializationContext
extends DeserializationContext
implements Serializable {
    private static final long serialVersionUID = 1;
    private List<ObjectIdResolver> _objectIdResolvers;
    protected transient LinkedHashMap<ObjectIdGenerator.IdKey, ReadableObjectId> _objectIds;

    protected DefaultDeserializationContext(DefaultDeserializationContext defaultDeserializationContext, DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
        super(defaultDeserializationContext, deserializationConfig, jsonParser, injectableValues);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext defaultDeserializationContext, DeserializerFactory deserializerFactory) {
        super(defaultDeserializationContext, deserializerFactory);
    }

    protected DefaultDeserializationContext(DeserializerFactory deserializerFactory, DeserializerCache deserializerCache) {
        super(deserializerFactory, deserializerCache);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void checkUnresolvedObjectId() throws UnresolvedForwardReference {
        if (this._objectIds == null) {
            return;
        }
        var2_1 = null;
        var3_2 = this._objectIds.entrySet().iterator();
        block0 : do lbl-1000: // 3 sources:
        {
            if (!var3_2.hasNext()) {
                if (var2_1 == null) return;
                throw var2_1;
            }
            var4_4 = var3_2.next().getValue();
            if (!var4_4.hasReferringProperties()) ** GOTO lbl-1000
            var1_3 = var2_1;
            if (var2_1 == null) {
                var1_3 = new UnresolvedForwardReference("Unresolved forward references for: ");
            }
            var5_5 = var4_4.referringProperties();
            do {
                var2_1 = var1_3;
                if (!var5_5.hasNext()) continue block0;
                var2_1 = var5_5.next();
                var1_3.addUnresolvedId(var4_4.getKey().key, var2_1.getBeanType(), var2_1.getLocation());
            } while (true);
            break;
        } while (true);
    }

    public abstract DefaultDeserializationContext createInstance(DeserializationConfig var1, JsonParser var2, InjectableValues var3);

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonDeserializer<Object> deserializerInstance(Annotated object, Object object2) throws JsonMappingException {
        Object var3_3 = null;
        Object var4_4 = null;
        if (object2 == null) {
            return var4_4;
        }
        if (object2 instanceof JsonDeserializer) {
            object = (JsonDeserializer)object2;
        } else {
            if (!(object2 instanceof Class)) {
                throw new IllegalStateException("AnnotationIntrospector returned deserializer definition of type " + object2.getClass().getName() + "; expected type JsonDeserializer or Class<JsonDeserializer> instead");
            }
            Class class_ = (Class)object2;
            object2 = var4_4;
            if (class_ == JsonDeserializer.None.class) return object2;
            object2 = var4_4;
            if (ClassUtil.isBogusClass(class_)) return object2;
            if (!JsonDeserializer.class.isAssignableFrom(class_)) {
                throw new IllegalStateException("AnnotationIntrospector returned Class " + class_.getName() + "; expected Class<JsonDeserializer>");
            }
            object2 = this._config.getHandlerInstantiator();
            object2 = object2 == null ? var3_3 : object2.deserializerInstance(this._config, (Annotated)object, class_);
            object = object2;
            if (object2 == null) {
                object = (JsonDeserializer)ClassUtil.createInstance(class_, this._config.canOverrideAccessModifiers());
            }
        }
        object2 = object;
        if (!(object instanceof ResolvableDeserializer)) return object2;
        ((ResolvableDeserializer)object).resolve(this);
        return object;
    }

    @Deprecated
    @Override
    public ReadableObjectId findObjectId(Object object, ObjectIdGenerator<?> objectIdGenerator) {
        return this.findObjectId(object, objectIdGenerator, new SimpleObjectIdResolver());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ReadableObjectId findObjectId(Object object, ObjectIdGenerator<?> object2, ObjectIdResolver objectIdResolver) {
        ObjectIdGenerator.IdKey idKey = object2.key(object);
        if (this._objectIds == null) {
            this._objectIds = new LinkedHashMap<K, V>();
        } else {
            object = this._objectIds.get(idKey);
            if (object != null) {
                return object;
            }
        }
        object2 = null;
        if (this._objectIdResolvers == null) {
            this._objectIdResolvers = new ArrayList<ObjectIdResolver>(8);
            object = object2;
        } else {
            Iterator<ObjectIdResolver> iterator = this._objectIdResolvers.iterator();
            do {
                object = object2;
            } while (iterator.hasNext() && !(object = iterator.next()).canUseFor(objectIdResolver));
        }
        object2 = object;
        if (object == null) {
            object2 = objectIdResolver.newForDeserialization(this);
            this._objectIdResolvers.add((ObjectIdResolver)object2);
        }
        object = new ReadableObjectId(idKey);
        object.setResolver((ObjectIdResolver)object2);
        this._objectIds.put(idKey, (ReadableObjectId)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final KeyDeserializer keyDeserializerInstance(Annotated object, Object object2) throws JsonMappingException {
        Object var3_3 = null;
        Object var4_4 = null;
        if (object2 == null) {
            return var4_4;
        }
        if (object2 instanceof KeyDeserializer) {
            object = (KeyDeserializer)object2;
        } else {
            if (!(object2 instanceof Class)) {
                throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + object2.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
            }
            Class class_ = (Class)object2;
            object2 = var4_4;
            if (class_ == KeyDeserializer.None.class) return object2;
            object2 = var4_4;
            if (ClassUtil.isBogusClass(class_)) return object2;
            if (!KeyDeserializer.class.isAssignableFrom(class_)) {
                throw new IllegalStateException("AnnotationIntrospector returned Class " + class_.getName() + "; expected Class<KeyDeserializer>");
            }
            object2 = this._config.getHandlerInstantiator();
            object2 = object2 == null ? var3_3 : object2.keyDeserializerInstance(this._config, (Annotated)object, class_);
            object = object2;
            if (object2 == null) {
                object = (KeyDeserializer)ClassUtil.createInstance(class_, this._config.canOverrideAccessModifiers());
            }
        }
        object2 = object;
        if (!(object instanceof ResolvableDeserializer)) return object2;
        ((ResolvableDeserializer)object).resolve(this);
        return object;
    }

    public abstract DefaultDeserializationContext with(DeserializerFactory var1);

    public static final class Impl
    extends DefaultDeserializationContext {
        private static final long serialVersionUID = 1;

        protected Impl(Impl impl, DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
            super(impl, deserializationConfig, jsonParser, injectableValues);
        }

        protected Impl(Impl impl, DeserializerFactory deserializerFactory) {
            super(impl, deserializerFactory);
        }

        public Impl(DeserializerFactory deserializerFactory) {
            super(deserializerFactory, null);
        }

        @Override
        public DefaultDeserializationContext createInstance(DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
            return new Impl(this, deserializationConfig, jsonParser, injectableValues);
        }

        @Override
        public DefaultDeserializationContext with(DeserializerFactory deserializerFactory) {
            return new Impl(this, deserializerFactory);
        }
    }

}

