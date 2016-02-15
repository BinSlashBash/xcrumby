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
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class JsonStreamParser
implements Iterator<JsonElement> {
    private final Object lock;
    private final JsonReader parser;

    public JsonStreamParser(Reader reader) {
        this.parser = new JsonReader(reader);
        this.parser.setLenient(true);
        this.lock = new Object();
    }

    public JsonStreamParser(String string2) {
        this(new StringReader(string2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean hasNext() {
        Object object = this.lock;
        synchronized (object) {
            try {
                JsonToken jsonToken = this.parser.peek();
                JsonToken jsonToken2 = JsonToken.END_DOCUMENT;
                if (jsonToken == jsonToken2) return false;
                return true;
            }
            catch (MalformedJsonException var3_3) {
                throw new JsonSyntaxException(var3_3);
            }
            catch (IOException var3_4) {
                throw new JsonIOException(var3_4);
            }
        }
    }

    @Override
    public JsonElement next() throws JsonParseException {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            JsonElement jsonElement = Streams.parse(this.parser);
            return jsonElement;
        }
        catch (StackOverflowError var1_2) {
            throw new JsonParseException("Failed parsing JSON source to Json", var1_2);
        }
        catch (OutOfMemoryError var1_3) {
            throw new JsonParseException("Failed parsing JSON source to Json", var1_3);
        }
        catch (JsonParseException var2_5) {
            RuntimeException runtimeException = var2_5;
            if (var2_5.getCause() instanceof EOFException) {
                runtimeException = new NoSuchElementException();
            }
            throw runtimeException;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

