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
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeTypeAdapter
extends TypeAdapter<Time> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Time.class) {
                return new TimeTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Time read(JsonReader object) throws IOException {
        synchronized (this) {
            if (object.peek() == JsonToken.NULL) {
                object.nextNull();
                return null;
            }
            try {
                return new Time(this.format.parse(object.nextString()).getTime());
            }
            catch (ParseException var1_2) {
                throw new JsonSyntaxException(var1_2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(JsonWriter jsonWriter, Time object) throws IOException {
        synchronized (this) {
            String string2;
            string2 = string2 == null ? null : this.format.format((Date)((Object)string2));
            jsonWriter.value(string2);
            return;
        }
    }

}

