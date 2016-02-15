/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
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

    protected PropertyBasedCreator(ValueInstantiator arrsettableBeanProperty, SettableBeanProperty[] arrsettableBeanProperty2, Object[] arrobject) {
        int n2;
        this._valueInstantiator = arrsettableBeanProperty;
        this._properties = new HashMap();
        arrsettableBeanProperty = null;
        this._propertyCount = n2 = arrsettableBeanProperty2.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            SettableBeanProperty settableBeanProperty = arrsettableBeanProperty2[i2];
            this._properties.put(settableBeanProperty.getName(), settableBeanProperty);
            SettableBeanProperty[] arrsettableBeanProperty3 = arrsettableBeanProperty;
            if (settableBeanProperty.getInjectableValueId() != null) {
                arrsettableBeanProperty3 = arrsettableBeanProperty;
                if (arrsettableBeanProperty == null) {
                    arrsettableBeanProperty3 = new SettableBeanProperty[n2];
                }
                arrsettableBeanProperty3[i2] = settableBeanProperty;
            }
            arrsettableBeanProperty = arrsettableBeanProperty3;
        }
        this._defaultValues = arrobject;
        this._propertiesWithInjectables = arrsettableBeanProperty;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static PropertyBasedCreator construct(DeserializationContext deserializationContext, ValueInstantiator valueInstantiator, SettableBeanProperty[] arrsettableBeanProperty) throws JsonMappingException {
        int n2 = arrsettableBeanProperty.length;
        SettableBeanProperty[] arrsettableBeanProperty2 = new SettableBeanProperty[n2];
        Object[] arrobject = null;
        int n3 = 0;
        while (n3 < n2) {
            Object object;
            Object[] arrobject2 = object = arrsettableBeanProperty[n3];
            if (!object.hasValueDeserializer()) {
                arrobject2 = object.withValueDeserializer(deserializationContext.findContextualValueDeserializer(object.getType(), (BeanProperty)object));
            }
            arrsettableBeanProperty2[n3] = arrobject2;
            object = arrobject2.getValueDeserializer();
            object = object == null ? null : object.getNullValue();
            Object object2 = object;
            if (object == null) {
                object2 = object;
                if (arrobject2.getType().isPrimitive()) {
                    object2 = ClassUtil.defaultValue(arrobject2.getType().getRawClass());
                }
            }
            object = arrobject;
            if (object2 != null) {
                object = arrobject;
                if (arrobject == null) {
                    object = new Object[n2];
                }
                object[n3] = object2;
            }
            ++n3;
            arrobject = object;
        }
        return new PropertyBasedCreator(valueInstantiator, arrsettableBeanProperty2, arrobject);
    }

    public void assignDeserializer(SettableBeanProperty settableBeanProperty, JsonDeserializer<Object> jsonDeserializer) {
        settableBeanProperty = settableBeanProperty.withValueDeserializer(jsonDeserializer);
        this._properties.put(settableBeanProperty.getName(), settableBeanProperty);
    }

    public Object build(DeserializationContext object, PropertyValueBuffer propertyValueBuffer) throws IOException {
        Object object2 = propertyValueBuffer.handleIdValue((DeserializationContext)object, this._valueInstantiator.createFromObjectWith((DeserializationContext)object, propertyValueBuffer.getParameters(this._defaultValues)));
        object = propertyValueBuffer.buffered();
        while (object != null) {
            object.assign(object2);
            object = object.next;
        }
        return object2;
    }

    public SettableBeanProperty findCreatorProperty(int n2) {
        for (SettableBeanProperty settableBeanProperty : this._properties.values()) {
            if (settableBeanProperty.getPropertyIndex() != n2) continue;
            return settableBeanProperty;
        }
        return null;
    }

    public SettableBeanProperty findCreatorProperty(String string2) {
        return this._properties.get(string2);
    }

    public Collection<SettableBeanProperty> properties() {
        return this._properties.values();
    }

    public PropertyValueBuffer startBuilding(JsonParser object, DeserializationContext deserializationContext, ObjectIdReader objectIdReader) {
        object = new PropertyValueBuffer((JsonParser)object, deserializationContext, this._propertyCount, objectIdReader);
        if (this._propertiesWithInjectables != null) {
            object.inject(this._propertiesWithInjectables);
        }
        return object;
    }
}

