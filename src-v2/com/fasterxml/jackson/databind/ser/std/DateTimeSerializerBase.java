/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public abstract class DateTimeSerializerBase<T>
extends StdScalarSerializer<T>
implements ContextualSerializer {
    protected final DateFormat _customFormat;
    protected final Boolean _useTimestamp;

    protected DateTimeSerializerBase(Class<T> class_, Boolean bl2, DateFormat dateFormat) {
        super(class_);
        this._useTimestamp = bl2;
        this._customFormat = dateFormat;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _acceptJsonFormatVisitor(JsonFormatVisitorWrapper object, JavaType javaType, boolean bl2) throws JsonMappingException {
        if (bl2) {
            if ((object = object.expectIntegerFormat(javaType)) == null) return;
            {
                object.numberType(JsonParser.NumberType.LONG);
                object.format(JsonValueFormat.UTC_MILLISEC);
                return;
            }
        } else {
            if ((object = object.expectStringFormat(javaType)) == null) return;
            {
                object.format(JsonValueFormat.DATE_TIME);
                return;
            }
        }
    }

    protected boolean _asTimestamp(SerializerProvider serializerProvider) {
        if (this._useTimestamp != null) {
            return this._useTimestamp;
        }
        if (this._customFormat == null) {
            if (serializerProvider != null) {
                return serializerProvider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            }
            throw new IllegalArgumentException("Null 'provider' passed for " + this.handledType().getName());
        }
        return false;
    }

    protected abstract long _timestamp(T var1);

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        this._acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType, this._asTimestamp(jsonFormatVisitorWrapper.getProvider()));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider object, BeanProperty object2) throws JsonMappingException {
        TimeZone timeZone = null;
        Object object3 = this;
        if (object2 == null) return object3;
        Object object4 = object.getAnnotationIntrospector().findFormat(object2.getMember());
        object3 = this;
        if (object4 == null) return object3;
        if (object4.getShape().isNumeric()) {
            return this.withFormat(Boolean.TRUE, null);
        }
        object2 = timeZone;
        if (object4.getShape() == JsonFormat.Shape.STRING) {
            object2 = Boolean.FALSE;
        }
        timeZone = object4.getTimeZone();
        if (object4.hasPattern()) {
            String string2 = object4.getPattern();
            object3 = object4.hasLocale() ? object4.getLocale() : object.getLocale();
            object4 = new SimpleDateFormat(string2, (Locale)object3);
            object3 = timeZone;
            if (timeZone == null) {
                object3 = object.getTimeZone();
            }
            object4.setTimeZone((TimeZone)object3);
            return this.withFormat((Boolean)object2, (DateFormat)object4);
        }
        object3 = this;
        if (timeZone == null) return object3;
        object3 = object.getConfig().getDateFormat();
        if (object3.getClass() != StdDateFormat.class) {
            object = (DateFormat)object3.clone();
            object.setTimeZone(timeZone);
            return this.withFormat((Boolean)object2, (DateFormat)object);
        }
        object = object4.hasLocale() ? object4.getLocale() : object.getLocale();
        object = StdDateFormat.getISO8601Format(timeZone, (Locale)object);
        return this.withFormat((Boolean)object2, (DateFormat)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonNode getSchema(SerializerProvider object, Type type) {
        if (this._asTimestamp((SerializerProvider)object)) {
            object = "number";
            do {
                return this.createSchemaNode((String)object, true);
                break;
            } while (true);
        }
        object = "string";
        return this.createSchemaNode((String)object, true);
    }

    @Override
    public boolean isEmpty(T t2) {
        if (t2 == null || this._timestamp(t2) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public abstract void serialize(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException;

    public abstract DateTimeSerializerBase<T> withFormat(Boolean var1, DateFormat var2);
}

