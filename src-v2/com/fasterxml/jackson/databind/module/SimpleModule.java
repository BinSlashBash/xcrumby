/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleKeyDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.module.SimpleValueInstantiators;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleModule
extends Module
implements Serializable {
    private static final long serialVersionUID = -8905749147637667249L;
    protected SimpleAbstractTypeResolver _abstractTypes = null;
    protected BeanDeserializerModifier _deserializerModifier = null;
    protected SimpleDeserializers _deserializers = null;
    protected SimpleKeyDeserializers _keyDeserializers = null;
    protected SimpleSerializers _keySerializers = null;
    protected HashMap<Class<?>, Class<?>> _mixins = null;
    protected final String _name;
    protected PropertyNamingStrategy _namingStrategy = null;
    protected BeanSerializerModifier _serializerModifier = null;
    protected SimpleSerializers _serializers = null;
    protected LinkedHashSet<NamedType> _subtypes = null;
    protected SimpleValueInstantiators _valueInstantiators = null;
    protected final Version _version;

    public SimpleModule() {
        this._name = "SimpleModule-" + System.identityHashCode(this);
        this._version = Version.unknownVersion();
    }

    public SimpleModule(Version version) {
        this._name = version.getArtifactId();
        this._version = version;
    }

    public SimpleModule(String string2) {
        this(string2, Version.unknownVersion());
    }

    public SimpleModule(String string2, Version version) {
        this._name = string2;
        this._version = version;
    }

    public SimpleModule(String string2, Version version, List<JsonSerializer<?>> list) {
        this(string2, version, null, list);
    }

    public SimpleModule(String string2, Version version, Map<Class<?>, JsonDeserializer<?>> map) {
        this(string2, version, map, null);
    }

    public SimpleModule(String string2, Version version, Map<Class<?>, JsonDeserializer<?>> map, List<JsonSerializer<?>> list) {
        this._name = string2;
        this._version = version;
        if (map != null) {
            this._deserializers = new SimpleDeserializers(map);
        }
        if (list != null) {
            this._serializers = new SimpleSerializers(list);
        }
    }

    public <T> SimpleModule addAbstractTypeMapping(Class<T> class_, Class<? extends T> class_2) {
        if (this._abstractTypes == null) {
            this._abstractTypes = new SimpleAbstractTypeResolver();
        }
        this._abstractTypes = this._abstractTypes.addMapping(class_, class_2);
        return this;
    }

    public <T> SimpleModule addDeserializer(Class<T> class_, JsonDeserializer<? extends T> jsonDeserializer) {
        if (this._deserializers == null) {
            this._deserializers = new SimpleDeserializers();
        }
        this._deserializers.addDeserializer(class_, jsonDeserializer);
        return this;
    }

    public SimpleModule addKeyDeserializer(Class<?> class_, KeyDeserializer keyDeserializer) {
        if (this._keyDeserializers == null) {
            this._keyDeserializers = new SimpleKeyDeserializers();
        }
        this._keyDeserializers.addDeserializer(class_, keyDeserializer);
        return this;
    }

    public <T> SimpleModule addKeySerializer(Class<? extends T> class_, JsonSerializer<T> jsonSerializer) {
        if (this._keySerializers == null) {
            this._keySerializers = new SimpleSerializers();
        }
        this._keySerializers.addSerializer(class_, jsonSerializer);
        return this;
    }

    public SimpleModule addSerializer(JsonSerializer<?> jsonSerializer) {
        if (this._serializers == null) {
            this._serializers = new SimpleSerializers();
        }
        this._serializers.addSerializer(jsonSerializer);
        return this;
    }

    public <T> SimpleModule addSerializer(Class<? extends T> class_, JsonSerializer<T> jsonSerializer) {
        if (this._serializers == null) {
            this._serializers = new SimpleSerializers();
        }
        this._serializers.addSerializer(class_, jsonSerializer);
        return this;
    }

    public SimpleModule addValueInstantiator(Class<?> class_, ValueInstantiator valueInstantiator) {
        if (this._valueInstantiators == null) {
            this._valueInstantiators = new SimpleValueInstantiators();
        }
        this._valueInstantiators = this._valueInstantiators.addValueInstantiator(class_, valueInstantiator);
        return this;
    }

    @Override
    public String getModuleName() {
        return this._name;
    }

    public /* varargs */ SimpleModule registerSubtypes(NamedType ... arrnamedType) {
        if (this._subtypes == null) {
            this._subtypes = new LinkedHashSet(Math.max(16, arrnamedType.length));
        }
        for (NamedType namedType : arrnamedType) {
            this._subtypes.add(namedType);
        }
        return this;
    }

    public /* varargs */ SimpleModule registerSubtypes(Class<?> ... arrclass) {
        if (this._subtypes == null) {
            this._subtypes = new LinkedHashSet(Math.max(16, arrclass.length));
        }
        for (Class class_ : arrclass) {
            this._subtypes.add(new NamedType(class_));
        }
        return this;
    }

    public void setAbstractTypes(SimpleAbstractTypeResolver simpleAbstractTypeResolver) {
        this._abstractTypes = simpleAbstractTypeResolver;
    }

    public SimpleModule setDeserializerModifier(BeanDeserializerModifier beanDeserializerModifier) {
        this._deserializerModifier = beanDeserializerModifier;
        return this;
    }

    public void setDeserializers(SimpleDeserializers simpleDeserializers) {
        this._deserializers = simpleDeserializers;
    }

    public void setKeyDeserializers(SimpleKeyDeserializers simpleKeyDeserializers) {
        this._keyDeserializers = simpleKeyDeserializers;
    }

    public void setKeySerializers(SimpleSerializers simpleSerializers) {
        this._keySerializers = simpleSerializers;
    }

    public SimpleModule setMixInAnnotation(Class<?> class_, Class<?> class_2) {
        if (this._mixins == null) {
            this._mixins = new HashMap();
        }
        this._mixins.put(class_, class_2);
        return this;
    }

    protected SimpleModule setNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        this._namingStrategy = propertyNamingStrategy;
        return this;
    }

    public SimpleModule setSerializerModifier(BeanSerializerModifier beanSerializerModifier) {
        this._serializerModifier = beanSerializerModifier;
        return this;
    }

    public void setSerializers(SimpleSerializers simpleSerializers) {
        this._serializers = simpleSerializers;
    }

    public void setValueInstantiators(SimpleValueInstantiators simpleValueInstantiators) {
        this._valueInstantiators = simpleValueInstantiators;
    }

    @Override
    public void setupModule(Module.SetupContext setupContext) {
        if (this._serializers != null) {
            setupContext.addSerializers(this._serializers);
        }
        if (this._deserializers != null) {
            setupContext.addDeserializers(this._deserializers);
        }
        if (this._keySerializers != null) {
            setupContext.addKeySerializers(this._keySerializers);
        }
        if (this._keyDeserializers != null) {
            setupContext.addKeyDeserializers(this._keyDeserializers);
        }
        if (this._abstractTypes != null) {
            setupContext.addAbstractTypeResolver(this._abstractTypes);
        }
        if (this._valueInstantiators != null) {
            setupContext.addValueInstantiators(this._valueInstantiators);
        }
        if (this._deserializerModifier != null) {
            setupContext.addBeanDeserializerModifier(this._deserializerModifier);
        }
        if (this._serializerModifier != null) {
            setupContext.addBeanSerializerModifier(this._serializerModifier);
        }
        if (this._subtypes != null && this._subtypes.size() > 0) {
            setupContext.registerSubtypes(this._subtypes.toArray(new NamedType[this._subtypes.size()]));
        }
        if (this._namingStrategy != null) {
            setupContext.setNamingStrategy(this._namingStrategy);
        }
        if (this._mixins != null) {
            for (Map.Entry entry : this._mixins.entrySet()) {
                setupContext.setMixInAnnotations(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Version version() {
        return this._version;
    }
}

