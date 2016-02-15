/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Writer;

public final class Streams {
    public static JsonElement parse(JsonReader object) throws JsonParseException {
        boolean bl2 = true;
        object.peek();
        bl2 = false;
        try {
            object = TypeAdapters.JSON_ELEMENT.read((JsonReader)object);
            return object;
        }
        catch (EOFException var0_1) {
            if (bl2) {
                return JsonNull.INSTANCE;
            }
            throw new JsonSyntaxException(var0_1);
        }
        catch (MalformedJsonException var0_2) {
            throw new JsonSyntaxException(var0_2);
        }
        catch (IOException var0_3) {
            throw new JsonIOException(var0_3);
        }
        catch (NumberFormatException var0_4) {
            throw new JsonSyntaxException(var0_4);
        }
    }

    public static void write(JsonElement jsonElement, JsonWriter jsonWriter) throws IOException {
        TypeAdapters.JSON_ELEMENT.write(jsonWriter, jsonElement);
    }

    public static Writer writerForAppendable(Appendable appendable) {
        if (appendable instanceof Writer) {
            return (Writer)appendable;
        }
        return new AppendableWriter(appendable);
    }

    private static final class AppendableWriter
    extends Writer {
        private final Appendable appendable;
        private final CurrentWrite currentWrite = new CurrentWrite();

        private AppendableWriter(Appendable appendable) {
            this.appendable = appendable;
        }

        @Override
        public void close() {
        }

        @Override
        public void flush() {
        }

        @Override
        public void write(int n2) throws IOException {
            this.appendable.append((char)n2);
        }

        @Override
        public void write(char[] arrc, int n2, int n3) throws IOException {
            this.currentWrite.chars = arrc;
            this.appendable.append(this.currentWrite, n2, n2 + n3);
        }

        static class CurrentWrite
        implements CharSequence {
            char[] chars;

            CurrentWrite() {
            }

            @Override
            public char charAt(int n2) {
                return this.chars[n2];
            }

            @Override
            public int length() {
                return this.chars.length;
            }

            @Override
            public CharSequence subSequence(int n2, int n3) {
                return new String(this.chars, n2, n3 - n2);
            }
        }

    }

}

