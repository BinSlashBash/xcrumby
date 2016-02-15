/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParserSequence
extends JsonParserDelegate {
    protected int _nextParser;
    protected final JsonParser[] _parsers;

    protected JsonParserSequence(JsonParser[] arrjsonParser) {
        super(arrjsonParser[0]);
        this._parsers = arrjsonParser;
        this._nextParser = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static JsonParserSequence createFlattened(JsonParser jsonParser, JsonParser jsonParser2) {
        if (!(jsonParser instanceof JsonParserSequence) && !(jsonParser2 instanceof JsonParserSequence)) {
            return new JsonParserSequence(new JsonParser[]{jsonParser, jsonParser2});
        }
        ArrayList<JsonParser> arrayList = new ArrayList<JsonParser>();
        if (jsonParser instanceof JsonParserSequence) {
            ((JsonParserSequence)jsonParser).addFlattenedActiveParsers(arrayList);
        } else {
            arrayList.add(jsonParser);
        }
        if (jsonParser2 instanceof JsonParserSequence) {
            ((JsonParserSequence)jsonParser2).addFlattenedActiveParsers(arrayList);
            return new JsonParserSequence(arrayList.toArray(new JsonParser[arrayList.size()]));
        }
        arrayList.add(jsonParser2);
        return new JsonParserSequence(arrayList.toArray(new JsonParser[arrayList.size()]));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void addFlattenedActiveParsers(List<JsonParser> list) {
        int n2 = this._nextParser - 1;
        int n3 = this._parsers.length;
        while (n2 < n3) {
            JsonParser jsonParser = this._parsers[n2];
            if (jsonParser instanceof JsonParserSequence) {
                ((JsonParserSequence)jsonParser).addFlattenedActiveParsers(list);
            } else {
                list.add(jsonParser);
            }
            ++n2;
        }
        return;
    }

    @Override
    public void close() throws IOException {
        do {
            this.delegate.close();
        } while (this.switchToNext());
    }

    public int containedParsersCount() {
        return this._parsers.length;
    }

    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        JsonToken jsonToken = this.delegate.nextToken();
        if (jsonToken != null) {
            return jsonToken;
        }
        while (this.switchToNext()) {
            jsonToken = this.delegate.nextToken();
            if (jsonToken == null) continue;
            return jsonToken;
        }
        return null;
    }

    protected boolean switchToNext() {
        if (this._nextParser >= this._parsers.length) {
            return false;
        }
        JsonParser[] arrjsonParser = this._parsers;
        int n2 = this._nextParser;
        this._nextParser = n2 + 1;
        this.delegate = arrjsonParser[n2];
        return true;
    }
}

