/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class TypeAdapter<T> {
    public final T fromJson(Reader reader) throws IOException {
        return this.read(new JsonReader(reader));
    }

    public final T fromJson(String string2) throws IOException {
        return this.fromJson(new StringReader(string2));
    }

    public final T fromJsonTree(JsonElement jsonElement) {
        try {
            jsonElement = this.read(new JsonTreeReader(jsonElement));
        }
        catch (IOException var1_2) {
            throw new JsonIOException(var1_2);
        }
        return (T)jsonElement;
    }

    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>(){

            @Override
            public T read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return TypeAdapter.this.read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, T t2) throws IOException {
                if (t2 == null) {
                    jsonWriter.nullValue();
                    return;
                }
                TypeAdapter.this.write(jsonWriter, t2);
            }
        };
    }

    public abstract T read(JsonReader var1) throws IOException;

    public final String toJson(T t2) throws IOException {
        StringWriter stringWriter = new StringWriter();
        this.toJson(stringWriter, t2);
        return stringWriter.toString();
    }

    public final void toJson(Writer writer, T t2) throws IOException {
        this.write(new JsonWriter(writer), t2);
    }

    public final JsonElement toJsonTree(T object) {
        try {
            JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
            this.write(jsonTreeWriter, object);
            object = jsonTreeWriter.get();
            return object;
        }
        catch (IOException var1_2) {
            throw new JsonIOException(var1_2);
        }
    }

    public abstract void write(JsonWriter var1, T var2) throws IOException;

}

