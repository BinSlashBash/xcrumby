package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public final class PropertyBasedCreator {
    protected final Object[] _defaultValues;
    protected final HashMap<String, SettableBeanProperty> _properties;
    protected final SettableBeanProperty[] _propertiesWithInjectables;
    protected final int _propertyCount;
    protected final ValueInstantiator _valueInstantiator;

    protected PropertyBasedCreator(ValueInstantiator valueInstantiator, SettableBeanProperty[] creatorProps, Object[] defaultValues) {
        this._valueInstantiator = valueInstantiator;
        this._properties = new HashMap();
        SettableBeanProperty[] propertiesWithInjectables = null;
        int len = creatorProps.length;
        this._propertyCount = len;
        for (int i = 0; i < len; i++) {
            SettableBeanProperty prop = creatorProps[i];
            this._properties.put(prop.getName(), prop);
            if (prop.getInjectableValueId() != null) {
                if (propertiesWithInjectables == null) {
                    propertiesWithInjectables = new SettableBeanProperty[len];
                }
                propertiesWithInjectables[i] = prop;
            }
        }
        this._defaultValues = defaultValues;
        this._propertiesWithInjectables = propertiesWithInjectables;
    }

    public static PropertyBasedCreator construct(DeserializationContext ctxt, ValueInstantiator valueInstantiator, SettableBeanProperty[] srcProps) throws JsonMappingException {
        int len = srcProps.length;
        SettableBeanProperty[] creatorProps = new SettableBeanProperty[len];
        Object[] defaultValues = null;
        for (int i = 0; i < len; i++) {
            SettableBeanProperty prop = srcProps[i];
            if (!prop.hasValueDeserializer()) {
                prop = prop.withValueDeserializer(ctxt.findContextualValueDeserializer(prop.getType(), prop));
            }
            creatorProps[i] = prop;
            JsonDeserializer<?> deser = prop.getValueDeserializer();
            Object nullValue = deser == null ? null : deser.getNullValue();
            if (nullValue == null && prop.getType().isPrimitive()) {
                nullValue = ClassUtil.defaultValue(prop.getType().getRawClass());
            }
            if (nullValue != null) {
                if (defaultValues == null) {
                    defaultValues = new Object[len];
                }
                defaultValues[i] = nullValue;
            }
        }
        return new PropertyBasedCreator(valueInstantiator, creatorProps, defaultValues);
    }

    public void assignDeserializer(SettableBeanProperty prop, JsonDeserializer<Object> deser) {
        prop = prop.withValueDeserializer(deser);
        this._properties.put(prop.getName(), prop);
    }

    public Collection<SettableBeanProperty> properties() {
        return this._properties.values();
    }

    public SettableBeanProperty findCreatorProperty(String name) {
        return (SettableBeanProperty) this._properties.get(name);
    }

    public SettableBeanProperty findCreatorProperty(int propertyIndex) {
        for (SettableBeanProperty prop : this._properties.values()) {
            if (prop.getPropertyIndex() == propertyIndex) {
                return prop;
            }
        }
        return null;
    }

    public PropertyValueBuffer startBuilding(JsonParser jp, DeserializationContext ctxt, ObjectIdReader oir) {
        PropertyValueBuffer buffer = new PropertyValueBuffer(jp, ctxt, this._propertyCount, oir);
        if (this._propertiesWithInjectables != null) {
            buffer.inject(this._propertiesWithInjectables);
        }
        return buffer;
    }

    public Object build(DeserializationContext ctxt, PropertyValueBuffer buffer) throws IOException {
        Object bean = buffer.handleIdValue(ctxt, this._valueInstantiator.createFromObjectWith(ctxt, buffer.getParameters(this._defaultValues)));
        for (PropertyValue pv = buffer.buffered(); pv != null; pv = pv.next) {
            pv.assign(bean);
        }
        return bean;
    }
}
