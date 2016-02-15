/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;

public abstract class FilteredBeanPropertyWriter {
    public static BeanPropertyWriter constructViewBased(BeanPropertyWriter beanPropertyWriter, Class<?>[] arrclass) {
        if (arrclass.length == 1) {
            return new SingleView(beanPropertyWriter, arrclass[0]);
        }
        return new MultiView(beanPropertyWriter, arrclass);
    }

    private static final class MultiView
    extends BeanPropertyWriter {
        protected final BeanPropertyWriter _delegate;
        protected final Class<?>[] _views;

        protected MultiView(BeanPropertyWriter beanPropertyWriter, Class<?>[] arrclass) {
            super(beanPropertyWriter);
            this._delegate = beanPropertyWriter;
            this._views = arrclass;
        }

        @Override
        public void assignNullSerializer(JsonSerializer<Object> jsonSerializer) {
            this._delegate.assignNullSerializer(jsonSerializer);
        }

        @Override
        public void assignSerializer(JsonSerializer<Object> jsonSerializer) {
            this._delegate.assignSerializer(jsonSerializer);
        }

        @Override
        public MultiView rename(NameTransformer nameTransformer) {
            return new MultiView(this._delegate.rename(nameTransformer), this._views);
        }

        @Override
        public void serializeAsElement(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
            Class class_ = serializerProvider.getActiveView();
            if (class_ != null) {
                int n2 = 0;
                int n3 = this._views.length;
                do {
                    if (n2 >= n3 || this._views[n2].isAssignableFrom(class_)) {
                        if (n2 != n3) break;
                        this._delegate.serializeAsPlaceholder(object, jsonGenerator, serializerProvider);
                        return;
                    }
                    ++n2;
                } while (true);
            }
            this._delegate.serializeAsElement(object, jsonGenerator, serializerProvider);
        }

        @Override
        public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
            Class class_ = serializerProvider.getActiveView();
            if (class_ != null) {
                int n2 = 0;
                int n3 = this._views.length;
                do {
                    if (n2 >= n3 || this._views[n2].isAssignableFrom(class_)) {
                        if (n2 != n3) break;
                        this._delegate.serializeAsOmittedField(object, jsonGenerator, serializerProvider);
                        return;
                    }
                    ++n2;
                } while (true);
            }
            this._delegate.serializeAsField(object, jsonGenerator, serializerProvider);
        }
    }

    private static final class SingleView
    extends BeanPropertyWriter {
        protected final BeanPropertyWriter _delegate;
        protected final Class<?> _view;

        protected SingleView(BeanPropertyWriter beanPropertyWriter, Class<?> class_) {
            super(beanPropertyWriter);
            this._delegate = beanPropertyWriter;
            this._view = class_;
        }

        @Override
        public void assignNullSerializer(JsonSerializer<Object> jsonSerializer) {
            this._delegate.assignNullSerializer(jsonSerializer);
        }

        @Override
        public void assignSerializer(JsonSerializer<Object> jsonSerializer) {
            this._delegate.assignSerializer(jsonSerializer);
        }

        @Override
        public SingleView rename(NameTransformer nameTransformer) {
            return new SingleView(this._delegate.rename(nameTransformer), this._view);
        }

        @Override
        public void serializeAsElement(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
            Class class_ = serializerProvider.getActiveView();
            if (class_ == null || this._view.isAssignableFrom(class_)) {
                this._delegate.serializeAsElement(object, jsonGenerator, serializerProvider);
                return;
            }
            this._delegate.serializeAsPlaceholder(object, jsonGenerator, serializerProvider);
        }

        @Override
        public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
            Class class_ = serializerProvider.getActiveView();
            if (class_ == null || this._view.isAssignableFrom(class_)) {
                this._delegate.serializeAsField(object, jsonGenerator, serializerProvider);
                return;
            }
            this._delegate.serializeAsOmittedField(object, jsonGenerator, serializerProvider);
        }
    }

}

