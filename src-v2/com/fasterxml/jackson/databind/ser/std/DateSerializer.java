/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

@JacksonStdImpl
public class DateSerializer
extends DateTimeSerializerBase<Date> {
    public static final DateSerializer instance = new DateSerializer();

    public DateSerializer() {
        this(null, null);
    }

    public DateSerializer(Boolean bl2, DateFormat dateFormat) {
        super(Date.class, bl2, dateFormat);
    }

    @Override
    protected long _timestamp(Date date) {
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider object) throws IOException, JsonGenerationException {
        if (this._asTimestamp((SerializerProvider)object)) {
            jsonGenerator.writeNumber(this._timestamp(date));
            return;
        }
        if (this._customFormat != null) {
            object = this._customFormat;
            synchronized (object) {
                jsonGenerator.writeString(this._customFormat.format(date));
                return;
            }
        }
        object.defaultSerializeDateValue(date, jsonGenerator);
    }

    public DateSerializer withFormat(Boolean bl2, DateFormat dateFormat) {
        return new DateSerializer(bl2, dateFormat);
    }
}

