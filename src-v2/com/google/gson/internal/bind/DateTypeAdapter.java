/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateTypeAdapter
extends TypeAdapter<Date> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Date.class) {
                return new DateTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat enUsFormat = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat iso8601Format = DateTypeAdapter.buildIso8601Format();
    private final DateFormat localFormat = DateFormat.getDateTimeInstance(2, 2);

    private static DateFormat buildIso8601Format() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Date deserializeToDate(String object) {
        synchronized (this) {
            try {
                Date date = this.localFormat.parse((String)object);
                return date;
            }
            catch (ParseException var2_4) {
                try {
                    Date date = this.enUsFormat.parse((String)object);
                    return date;
                }
                catch (ParseException var2_6) {
                    try {
                        Date date = this.iso8601Format.parse((String)object);
                        return date;
                    }
                    catch (ParseException var2_8) {
                        throw new JsonSyntaxException((String)object, var2_8);
                    }
                }
            }
        }
    }

    @Override
    public Date read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return this.deserializeToDate(jsonReader.nextString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        synchronized (this) {
            void var2_2;
            if (var2_2 == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(this.enUsFormat.format((Date)var2_2));
            }
            return;
        }
    }

}

