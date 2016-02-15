/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public class CoreXMLSerializers
extends Serializers.Base {
    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig serializable, JavaType javaType, BeanDescription beanDescription) {
        serializable = javaType.getRawClass();
        if (Duration.class.isAssignableFrom(serializable) || QName.class.isAssignableFrom(serializable)) {
            return ToStringSerializer.instance;
        }
        if (XMLGregorianCalendar.class.isAssignableFrom(serializable)) {
            return new XMLGregorianCalendarSerializer();
        }
        return null;
    }

    public static class XMLGregorianCalendarSerializer
    extends StdSerializer<XMLGregorianCalendar> {
        public XMLGregorianCalendarSerializer() {
            super(XMLGregorianCalendar.class);
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            CalendarSerializer.instance.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, null);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
            return CalendarSerializer.instance.getSchema(serializerProvider, type);
        }

        @Override
        public void serialize(XMLGregorianCalendar xMLGregorianCalendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            CalendarSerializer.instance.serialize(xMLGregorianCalendar.toGregorianCalendar(), jsonGenerator, serializerProvider);
        }
    }

}

