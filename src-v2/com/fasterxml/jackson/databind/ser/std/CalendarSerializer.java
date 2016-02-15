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
import java.util.Calendar;
import java.util.Date;

@JacksonStdImpl
public class CalendarSerializer
extends DateTimeSerializerBase<Calendar> {
    public static final CalendarSerializer instance = new CalendarSerializer();

    public CalendarSerializer() {
        this(null, null);
    }

    public CalendarSerializer(Boolean bl2, DateFormat dateFormat) {
        super(Calendar.class, bl2, dateFormat);
    }

    @Override
    protected long _timestamp(Calendar calendar) {
        if (calendar == null) {
            return 0;
        }
        return calendar.getTimeInMillis();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serialize(Calendar calendar, JsonGenerator jsonGenerator, SerializerProvider object) throws IOException, JsonGenerationException {
        if (this._asTimestamp((SerializerProvider)object)) {
            jsonGenerator.writeNumber(this._timestamp(calendar));
            return;
        }
        if (this._customFormat != null) {
            object = this._customFormat;
            synchronized (object) {
                jsonGenerator.writeString(this._customFormat.format(calendar.getTime()));
                return;
            }
        }
        object.defaultSerializeDateValue(calendar.getTime(), jsonGenerator);
    }

    public CalendarSerializer withFormat(Boolean bl2, DateFormat dateFormat) {
        return new CalendarSerializer(bl2, dateFormat);
    }
}

