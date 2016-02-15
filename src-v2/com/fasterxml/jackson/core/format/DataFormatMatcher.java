/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataFormatMatcher {
    protected final byte[] _bufferedData;
    protected final int _bufferedLength;
    protected final int _bufferedStart;
    protected final JsonFactory _match;
    protected final MatchStrength _matchStrength;
    protected final InputStream _originalStream;

    protected DataFormatMatcher(InputStream inputStream, byte[] arrby, int n2, int n3, JsonFactory jsonFactory, MatchStrength matchStrength) {
        this._originalStream = inputStream;
        this._bufferedData = arrby;
        this._bufferedStart = n2;
        this._bufferedLength = n3;
        this._match = jsonFactory;
        this._matchStrength = matchStrength;
    }

    public JsonParser createParserWithMatch() throws IOException {
        if (this._match == null) {
            return null;
        }
        if (this._originalStream == null) {
            return this._match.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength);
        }
        return this._match.createParser(this.getDataStream());
    }

    public InputStream getDataStream() {
        if (this._originalStream == null) {
            return new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength);
        }
        return new MergedStream(null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
    }

    public JsonFactory getMatch() {
        return this._match;
    }

    public MatchStrength getMatchStrength() {
        if (this._matchStrength == null) {
            return MatchStrength.INCONCLUSIVE;
        }
        return this._matchStrength;
    }

    public String getMatchedFormatName() {
        return this._match.getFormatName();
    }

    public boolean hasMatch() {
        if (this._match != null) {
            return true;
        }
        return false;
    }
}

