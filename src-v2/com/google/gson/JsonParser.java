/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonElement parse(JsonReader jsonReader) throws JsonIOException, JsonSyntaxException {
        boolean bl2 = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            JsonElement jsonElement = Streams.parse(jsonReader);
            return jsonElement;
        }
        catch (StackOverflowError var3_4) {
            throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", var3_4);
        }
        catch (OutOfMemoryError var3_6) {
            throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", var3_6);
        }
        finally {
            jsonReader.setLenient(bl2);
        }
    }

    public JsonElement parse(Reader closeable) throws JsonIOException, JsonSyntaxException {
        JsonElement jsonElement;
        try {
            closeable = new JsonReader((Reader)closeable);
            jsonElement = this.parse((JsonReader)closeable);
            if (!jsonElement.isJsonNull() && closeable.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            }
        }
        catch (MalformedJsonException var1_2) {
            throw new JsonSyntaxException(var1_2);
        }
        catch (IOException var1_3) {
            throw new JsonIOException(var1_3);
        }
        catch (NumberFormatException var1_4) {
            throw new JsonSyntaxException(var1_4);
        }
        return jsonElement;
    }

    public JsonElement parse(String string2) throws JsonSyntaxException {
        return this.parse(new StringReader(string2));
    }
}

