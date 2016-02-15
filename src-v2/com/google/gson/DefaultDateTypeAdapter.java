/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

final class DefaultDateTypeAdapter
implements JsonSerializer<Date>,
JsonDeserializer<Date> {
    private final DateFormat enUsFormat;
    private final DateFormat iso8601Format;
    private final DateFormat localFormat;

    DefaultDateTypeAdapter() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    DefaultDateTypeAdapter(int n2) {
        this(DateFormat.getDateInstance(n2, Locale.US), DateFormat.getDateInstance(n2));
    }

    public DefaultDateTypeAdapter(int n2, int n3) {
        this(DateFormat.getDateTimeInstance(n2, n3, Locale.US), DateFormat.getDateTimeInstance(n2, n3));
    }

    DefaultDateTypeAdapter(String string2) {
        this(new SimpleDateFormat(string2, Locale.US), new SimpleDateFormat(string2));
    }

    DefaultDateTypeAdapter(DateFormat dateFormat, DateFormat dateFormat2) {
        this.enUsFormat = dateFormat;
        this.localFormat = dateFormat2;
        this.iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        this.iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Date deserializeToDate(JsonElement jsonElement) {
        DateFormat dateFormat = this.localFormat;
        synchronized (dateFormat) {
            try {
                return this.localFormat.parse(jsonElement.getAsString());
            }
            catch (ParseException var3_4) {
                try {
                    return this.enUsFormat.parse(jsonElement.getAsString());
                }
                catch (ParseException var3_6) {
                    try {
                        return this.iso8601Format.parse(jsonElement.getAsString());
                    }
                    catch (ParseException var3_8) {
                        throw new JsonSyntaxException(jsonElement.getAsString(), var3_8);
                    }
                }
            }
        }
    }

    @Override
    public Date deserialize(JsonElement object, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!(object instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }
        object = this.deserializeToDate((JsonElement)object);
        if (type == Date.class) {
            return object;
        }
        if (type == Timestamp.class) {
            return new Timestamp(object.getTime());
        }
        if (type == java.sql.Date.class) {
            return new java.sql.Date(object.getTime());
        }
        throw new IllegalArgumentException(this.getClass() + " cannot deserialize to " + type);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public JsonElement serialize(Date object, Type object2, JsonSerializationContext jsonSerializationContext) {
        object2 = this.localFormat;
        synchronized (object2) {
            return new JsonPrimitive(this.enUsFormat.format((Date)object));
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DefaultDateTypeAdapter.class.getSimpleName());
        stringBuilder.append('(').append(this.localFormat.getClass().getSimpleName()).append(')');
        return stringBuilder.toString();
    }
}

