/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

public class StdKeySerializers {
    protected static final JsonSerializer<Object> DEFAULT_KEY_SERIALIZER = new StdKeySerializer();
    protected static final JsonSerializer<Object> DEFAULT_STRING_SERIALIZER = new StringKeySerializer();

    private StdKeySerializers() {
    }

    public static JsonSerializer<Object> getStdKeySerializer(JavaType type) {
        if (type == null) {
            return DEFAULT_KEY_SERIALIZER;
        }
        if ((type = type.getRawClass()) == String.class) {
            return DEFAULT_STRING_SERIALIZER;
        }
        if (type == Object.class || type.isPrimitive() || Number.class.isAssignableFrom(type)) {
            return DEFAULT_KEY_SERIALIZER;
        }
        if (Date.class.isAssignableFrom(type)) {
            return DateKeySerializer.instance;
        }
        if (Calendar.class.isAssignableFrom(type)) {
            return CalendarKeySerializer.instance;
        }
        return DEFAULT_KEY_SERIALIZER;
    }

    public static class CalendarKeySerializer
    extends StdSerializer<Calendar> {
        protected static final JsonSerializer<?> instance = new CalendarKeySerializer();

        public CalendarKeySerializer() {
            super(Calendar.class);
        }

        @Override
        public void serialize(Calendar calendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            serializerProvider.defaultSerializeDateKey(calendar.getTimeInMillis(), jsonGenerator);
        }
    }

    public static class DateKeySerializer
    extends StdSerializer<Date> {
        protected static final JsonSerializer<?> instance = new DateKeySerializer();

        public DateKeySerializer() {
            super(Date.class);
        }

        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            serializerProvider.defaultSerializeDateKey(date, jsonGenerator);
        }
    }

    public static class StringKeySerializer
    extends StdSerializer<String> {
        public StringKeySerializer() {
            super(String.class);
        }

        @Override
        public void serialize(String string2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeFieldName(string2);
        }
    }

}

