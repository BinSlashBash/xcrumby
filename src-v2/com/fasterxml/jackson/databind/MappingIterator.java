/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MappingIterator<T>
implements Iterator<T>,
Closeable {
    protected static final MappingIterator<?> EMPTY_ITERATOR = new MappingIterator<T>(null, null, null, null, false, null);
    protected final boolean _closeParser;
    protected final DeserializationContext _context;
    protected final JsonDeserializer<T> _deserializer;
    protected boolean _hasNextChecked;
    protected JsonParser _parser;
    protected final JavaType _type;
    protected final T _updatedValue;

    @Deprecated
    protected MappingIterator(JavaType javaType, JsonParser jsonParser, DeserializationContext deserializationContext, JsonDeserializer<?> jsonDeserializer) {
        this(javaType, jsonParser, deserializationContext, jsonDeserializer, true, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected MappingIterator(JavaType javaType, JsonParser jsonParser, DeserializationContext deserializationContext, JsonDeserializer<?> jsonDeserializer, boolean bl2, Object object) {
        this._type = javaType;
        this._parser = jsonParser;
        this._context = deserializationContext;
        this._deserializer = jsonDeserializer;
        this._closeParser = bl2;
        this._updatedValue = object == null ? null : object;
        if (bl2 && jsonParser != null && jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
            jsonParser.clearCurrentToken();
        }
    }

    protected static <T> MappingIterator<T> emptyIterator() {
        return EMPTY_ITERATOR;
    }

    @Override
    public void close() throws IOException {
        if (this._parser != null) {
            this._parser.close();
        }
    }

    public JsonLocation getCurrentLocation() {
        return this._parser.getCurrentLocation();
    }

    public JsonParser getParser() {
        return this._parser;
    }

    public FormatSchema getParserSchema() {
        return this._parser.getSchema();
    }

    @Override
    public boolean hasNext() {
        try {
            boolean bl2 = this.hasNextValue();
            return bl2;
        }
        catch (JsonMappingException var2_2) {
            throw new RuntimeJsonMappingException(var2_2.getMessage(), var2_2);
        }
        catch (IOException var2_3) {
            throw new RuntimeException(var2_3.getMessage(), var2_3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean hasNextValue() throws IOException {
        if (this._parser == null) {
            return false;
        }
        if (this._hasNextChecked) return true;
        Object object = this._parser.getCurrentToken();
        this._hasNextChecked = true;
        if (object != null) return true;
        object = this._parser.nextToken();
        if (object != null) {
            if (object != JsonToken.END_ARRAY) return true;
        }
        object = this._parser;
        this._parser = null;
        if (!this._closeParser) return false;
        object.close();
        return false;
    }

    @Override
    public T next() {
        T t2;
        try {
            t2 = this.nextValue();
        }
        catch (JsonMappingException var1_2) {
            throw new RuntimeJsonMappingException(var1_2.getMessage(), var1_2);
        }
        catch (IOException var1_3) {
            throw new RuntimeException(var1_3.getMessage(), var1_3);
        }
        return t2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public T nextValue() throws IOException {
        T t2;
        if (!this._hasNextChecked && !this.hasNextValue()) {
            throw new NoSuchElementException();
        }
        if (this._parser == null) {
            throw new NoSuchElementException();
        }
        this._hasNextChecked = false;
        if (this._updatedValue == null) {
            t2 = this._deserializer.deserialize(this._parser, this._context);
        } else {
            this._deserializer.deserialize(this._parser, this._context, this._updatedValue);
            t2 = this._updatedValue;
        }
        this._parser.clearCurrentToken();
        return t2;
    }

    public List<T> readAll() throws IOException {
        return this.readAll(new ArrayList());
    }

    public List<T> readAll(List<T> list) throws IOException {
        while (this.hasNextValue()) {
            list.add(this.nextValue());
        }
        return list;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

