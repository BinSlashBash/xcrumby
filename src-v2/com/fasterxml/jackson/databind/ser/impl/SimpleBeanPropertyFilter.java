/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class SimpleBeanPropertyFilter
implements BeanPropertyFilter,
PropertyFilter {
    protected SimpleBeanPropertyFilter() {
    }

    public static SimpleBeanPropertyFilter filterOutAllExcept(Set<String> set) {
        return new FilterExceptFilter(set);
    }

    public static /* varargs */ SimpleBeanPropertyFilter filterOutAllExcept(String ... arrstring) {
        HashSet<String> hashSet = new HashSet<String>(arrstring.length);
        Collections.addAll(hashSet, arrstring);
        return new FilterExceptFilter(hashSet);
    }

    public static PropertyFilter from(final BeanPropertyFilter beanPropertyFilter) {
        return new PropertyFilter(){

            @Override
            public void depositSchemaProperty(PropertyWriter propertyWriter, JsonObjectFormatVisitor jsonObjectFormatVisitor, SerializerProvider serializerProvider) throws JsonMappingException {
                beanPropertyFilter.depositSchemaProperty((BeanPropertyWriter)propertyWriter, jsonObjectFormatVisitor, serializerProvider);
            }

            @Override
            public void depositSchemaProperty(PropertyWriter propertyWriter, ObjectNode objectNode, SerializerProvider serializerProvider) throws JsonMappingException {
                beanPropertyFilter.depositSchemaProperty((BeanPropertyWriter)propertyWriter, objectNode, serializerProvider);
            }

            @Override
            public void serializeAsElement(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, PropertyWriter propertyWriter) throws Exception {
                throw new UnsupportedOperationException();
            }

            @Override
            public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, PropertyWriter propertyWriter) throws Exception {
                beanPropertyFilter.serializeAsField(object, jsonGenerator, serializerProvider, (BeanPropertyWriter)propertyWriter);
            }
        };
    }

    public static SimpleBeanPropertyFilter serializeAllExcept(Set<String> set) {
        return new SerializeExceptFilter(set);
    }

    public static /* varargs */ SimpleBeanPropertyFilter serializeAllExcept(String ... arrstring) {
        HashSet<String> hashSet = new HashSet<String>(arrstring.length);
        Collections.addAll(hashSet, arrstring);
        return new SerializeExceptFilter(hashSet);
    }

    @Deprecated
    @Override
    public void depositSchemaProperty(BeanPropertyWriter beanPropertyWriter, JsonObjectFormatVisitor jsonObjectFormatVisitor, SerializerProvider serializerProvider) throws JsonMappingException {
        if (this.include(beanPropertyWriter)) {
            beanPropertyWriter.depositSchemaProperty(jsonObjectFormatVisitor);
        }
    }

    @Deprecated
    @Override
    public void depositSchemaProperty(BeanPropertyWriter beanPropertyWriter, ObjectNode objectNode, SerializerProvider serializerProvider) throws JsonMappingException {
        if (this.include(beanPropertyWriter)) {
            beanPropertyWriter.depositSchemaProperty(objectNode, serializerProvider);
        }
    }

    @Override
    public void depositSchemaProperty(PropertyWriter propertyWriter, JsonObjectFormatVisitor jsonObjectFormatVisitor, SerializerProvider serializerProvider) throws JsonMappingException {
        if (this.include(propertyWriter)) {
            propertyWriter.depositSchemaProperty(jsonObjectFormatVisitor);
        }
    }

    @Deprecated
    @Override
    public void depositSchemaProperty(PropertyWriter propertyWriter, ObjectNode objectNode, SerializerProvider serializerProvider) throws JsonMappingException {
        if (this.include(propertyWriter)) {
            propertyWriter.depositSchemaProperty(objectNode, serializerProvider);
        }
    }

    protected abstract boolean include(BeanPropertyWriter var1);

    protected abstract boolean include(PropertyWriter var1);

    protected boolean includeElement(Object object) {
        return true;
    }

    @Override
    public void serializeAsElement(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, PropertyWriter propertyWriter) throws Exception {
        if (this.includeElement(object)) {
            propertyWriter.serializeAsElement(object, jsonGenerator, serializerProvider);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    @Override
    public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, BeanPropertyWriter beanPropertyWriter) throws Exception {
        if (this.include(beanPropertyWriter)) {
            beanPropertyWriter.serializeAsField(object, jsonGenerator, serializerProvider);
            return;
        } else {
            if (jsonGenerator.canOmitFields()) return;
            {
                beanPropertyWriter.serializeAsOmittedField(object, jsonGenerator, serializerProvider);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, PropertyWriter propertyWriter) throws Exception {
        if (this.include(propertyWriter)) {
            propertyWriter.serializeAsField(object, jsonGenerator, serializerProvider);
            return;
        } else {
            if (jsonGenerator.canOmitFields()) return;
            {
                propertyWriter.serializeAsOmittedField(object, jsonGenerator, serializerProvider);
                return;
            }
        }
    }

    public static class FilterExceptFilter
    extends SimpleBeanPropertyFilter
    implements Serializable {
        private static final long serialVersionUID = 1;
        protected final Set<String> _propertiesToInclude;

        public FilterExceptFilter(Set<String> set) {
            this._propertiesToInclude = set;
        }

        @Override
        protected boolean include(BeanPropertyWriter beanPropertyWriter) {
            return this._propertiesToInclude.contains(beanPropertyWriter.getName());
        }

        @Override
        protected boolean include(PropertyWriter propertyWriter) {
            return this._propertiesToInclude.contains(propertyWriter.getName());
        }
    }

    public static class SerializeExceptFilter
    extends SimpleBeanPropertyFilter
    implements Serializable {
        private static final long serialVersionUID = 1;
        protected final Set<String> _propertiesToExclude;

        public SerializeExceptFilter(Set<String> set) {
            this._propertiesToExclude = set;
        }

        @Override
        protected boolean include(BeanPropertyWriter beanPropertyWriter) {
            if (!this._propertiesToExclude.contains(beanPropertyWriter.getName())) {
                return true;
            }
            return false;
        }

        @Override
        protected boolean include(PropertyWriter propertyWriter) {
            if (!this._propertiesToExclude.contains(propertyWriter.getName())) {
                return true;
            }
            return false;
        }
    }

}

