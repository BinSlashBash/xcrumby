/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public class CoreXMLDeserializers
extends Deserializers.Base {
    protected static final int TYPE_DURATION = 1;
    protected static final int TYPE_G_CALENDAR = 2;
    protected static final int TYPE_QNAME = 3;
    static final DatatypeFactory _dataTypeFactory;

    static {
        try {
            _dataTypeFactory = DatatypeFactory.newInstance();
            return;
        }
        catch (DatatypeConfigurationException var0) {
            throw new RuntimeException(var0);
        }
    }

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig deserializationConfig, BeanDescription beanDescription) {
        if ((type = type.getRawClass()) == QName.class) {
            return new Std(type, 3);
        }
        if (type == XMLGregorianCalendar.class) {
            return new Std(type, 2);
        }
        if (type == Duration.class) {
            return new Std(type, 1);
        }
        return null;
    }

    public static class Std
    extends FromStringDeserializer<Object> {
        private static final long serialVersionUID = 1;
        protected final int _kind;

        public Std(Class<?> class_, int n2) {
            super(class_);
            this._kind = n2;
        }

        @Override
        protected Object _deserialize(String string2, DeserializationContext deserializationContext) throws IllegalArgumentException {
            switch (this._kind) {
                default: {
                    throw new IllegalStateException();
                }
                case 1: {
                    return CoreXMLDeserializers._dataTypeFactory.newDuration(string2);
                }
                case 3: 
            }
            return QName.valueOf(string2);
        }

        @Override
        public Object deserialize(JsonParser object, DeserializationContext serializable) throws IOException, JsonProcessingException {
            if (this._kind == 2) {
                Date date = this._parseDate((JsonParser)object, (DeserializationContext)serializable);
                if (date == null) {
                    return null;
                }
                object = new GregorianCalendar();
                object.setTime(date);
                serializable = serializable.getTimeZone();
                if (serializable != null) {
                    object.setTimeZone((TimeZone)serializable);
                }
                return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar((GregorianCalendar)object);
            }
            return super.deserialize((JsonParser)object, (DeserializationContext)serializable);
        }
    }

}

