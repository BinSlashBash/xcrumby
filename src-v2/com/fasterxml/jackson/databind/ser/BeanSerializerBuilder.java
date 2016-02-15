/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.AnyGetterWriter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import java.util.List;

public class BeanSerializerBuilder {
    private static final BeanPropertyWriter[] NO_PROPERTIES = new BeanPropertyWriter[0];
    protected AnyGetterWriter _anyGetter;
    protected final BeanDescription _beanDesc;
    protected SerializationConfig _config;
    protected Object _filterId;
    protected BeanPropertyWriter[] _filteredProperties;
    protected ObjectIdWriter _objectIdWriter;
    protected List<BeanPropertyWriter> _properties;
    protected AnnotatedMember _typeId;

    public BeanSerializerBuilder(BeanDescription beanDescription) {
        this._beanDesc = beanDescription;
    }

    protected BeanSerializerBuilder(BeanSerializerBuilder beanSerializerBuilder) {
        this._beanDesc = beanSerializerBuilder._beanDesc;
        this._properties = beanSerializerBuilder._properties;
        this._filteredProperties = beanSerializerBuilder._filteredProperties;
        this._anyGetter = beanSerializerBuilder._anyGetter;
        this._filterId = beanSerializerBuilder._filterId;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JsonSerializer<?> build() {
        BeanPropertyWriter[] arrbeanPropertyWriter;
        if (this._properties == null || this._properties.isEmpty()) {
            if (this._anyGetter == null && this._objectIdWriter == null) {
                return null;
            }
            arrbeanPropertyWriter = NO_PROPERTIES;
            do {
                return new BeanSerializer(this._beanDesc.getType(), this, arrbeanPropertyWriter, this._filteredProperties);
                break;
            } while (true);
        }
        arrbeanPropertyWriter = this._properties.toArray(new BeanPropertyWriter[this._properties.size()]);
        return new BeanSerializer(this._beanDesc.getType(), this, arrbeanPropertyWriter, this._filteredProperties);
    }

    public BeanSerializer createDummy() {
        return BeanSerializer.createDummy(this._beanDesc.getType());
    }

    public AnyGetterWriter getAnyGetter() {
        return this._anyGetter;
    }

    public BeanDescription getBeanDescription() {
        return this._beanDesc;
    }

    public AnnotatedClass getClassInfo() {
        return this._beanDesc.getClassInfo();
    }

    public Object getFilterId() {
        return this._filterId;
    }

    public BeanPropertyWriter[] getFilteredProperties() {
        return this._filteredProperties;
    }

    public ObjectIdWriter getObjectIdWriter() {
        return this._objectIdWriter;
    }

    public List<BeanPropertyWriter> getProperties() {
        return this._properties;
    }

    public AnnotatedMember getTypeId() {
        return this._typeId;
    }

    public boolean hasProperties() {
        if (this._properties != null && this._properties.size() > 0) {
            return true;
        }
        return false;
    }

    public void setAnyGetter(AnyGetterWriter anyGetterWriter) {
        this._anyGetter = anyGetterWriter;
    }

    protected void setConfig(SerializationConfig serializationConfig) {
        this._config = serializationConfig;
    }

    public void setFilterId(Object object) {
        this._filterId = object;
    }

    public void setFilteredProperties(BeanPropertyWriter[] arrbeanPropertyWriter) {
        this._filteredProperties = arrbeanPropertyWriter;
    }

    public void setObjectIdWriter(ObjectIdWriter objectIdWriter) {
        this._objectIdWriter = objectIdWriter;
    }

    public void setProperties(List<BeanPropertyWriter> list) {
        this._properties = list;
    }

    public void setTypeId(AnnotatedMember annotatedMember) {
        if (this._typeId != null) {
            throw new IllegalArgumentException("Multiple type ids specified with " + this._typeId + " and " + annotatedMember);
        }
        this._typeId = annotatedMember;
    }
}

