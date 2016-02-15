/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DataFormatReaders {
    public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
    protected final int _maxInputLookahead;
    protected final MatchStrength _minimalMatch;
    protected final MatchStrength _optimalMatch;
    protected final ObjectReader[] _readers;

    public DataFormatReaders(Collection<ObjectReader> collection) {
        this(collection.toArray(new ObjectReader[collection.size()]));
    }

    public /* varargs */ DataFormatReaders(ObjectReader ... arrobjectReader) {
        this(arrobjectReader, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
    }

    private DataFormatReaders(ObjectReader[] arrobjectReader, MatchStrength matchStrength, MatchStrength matchStrength2, int n2) {
        this._readers = arrobjectReader;
        this._optimalMatch = matchStrength;
        this._minimalMatch = matchStrength2;
        this._maxInputLookahead = n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Match _findFormat(AccessorForReader var1_1) throws IOException {
        var5_2 = null;
        var4_3 = null;
        var10_4 = this._readers;
        var3_5 = var10_4.length;
        var2_6 = 0;
        do {
            var7_8 = var5_2;
            var6_7 = var4_3;
            if (var2_6 >= var3_5) return var1_1.createMatcher((ObjectReader)var7_8, (MatchStrength)var6_7);
            var9_10 = var10_4[var2_6];
            var1_1.reset();
            var8_9 = var9_10.getFactory().hasFormat(var1_1);
            var6_7 = var5_2;
            var7_8 = var4_3;
            if (var8_9 == null) ** GOTO lbl32
            if (var8_9.ordinal() >= this._minimalMatch.ordinal()) ** GOTO lbl20
            var7_8 = var4_3;
            var6_7 = var5_2;
            ** GOTO lbl32
lbl20: // 1 sources:
            if (var5_2 == null) ** GOTO lbl-1000
            var6_7 = var5_2;
            var7_8 = var4_3;
            if (var4_3.ordinal() < var8_9.ordinal()) lbl-1000: // 2 sources:
            {
                var4_3 = var9_10;
                var5_2 = var8_9;
                var6_7 = var4_3;
                var7_8 = var5_2;
                if (var8_9.ordinal() >= this._optimalMatch.ordinal()) {
                    var6_7 = var5_2;
                    var7_8 = var4_3;
                    return var1_1.createMatcher((ObjectReader)var7_8, (MatchStrength)var6_7);
                }
            }
lbl32: // 6 sources:
            ++var2_6;
            var5_2 = var6_7;
            var4_3 = var7_8;
        } while (true);
    }

    public Match findFormat(InputStream inputStream) throws IOException {
        return this._findFormat(new AccessorForReader(this, inputStream, new byte[this._maxInputLookahead]));
    }

    public Match findFormat(byte[] arrby) throws IOException {
        return this._findFormat(new AccessorForReader(this, arrby));
    }

    public Match findFormat(byte[] arrby, int n2, int n3) throws IOException {
        return this._findFormat(new AccessorForReader(this, arrby, n2, n3));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = this._readers.length;
        if (n2 > 0) {
            stringBuilder.append(this._readers[0].getFactory().getFormatName());
            for (int i2 = 1; i2 < n2; ++i2) {
                stringBuilder.append(", ");
                stringBuilder.append(this._readers[i2].getFactory().getFormatName());
            }
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public DataFormatReaders with(DeserializationConfig deserializationConfig) {
        int n2 = this._readers.length;
        ObjectReader[] arrobjectReader = new ObjectReader[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            arrobjectReader[i2] = this._readers[i2].with(deserializationConfig);
        }
        return new DataFormatReaders(arrobjectReader, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
    }

    public DataFormatReaders with(ObjectReader[] arrobjectReader) {
        return new DataFormatReaders(arrobjectReader, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
    }

    public DataFormatReaders withMaxInputLookahead(int n2) {
        if (n2 == this._maxInputLookahead) {
            return this;
        }
        return new DataFormatReaders(this._readers, this._optimalMatch, this._minimalMatch, n2);
    }

    public DataFormatReaders withMinimalMatch(MatchStrength matchStrength) {
        if (matchStrength == this._minimalMatch) {
            return this;
        }
        return new DataFormatReaders(this._readers, this._optimalMatch, matchStrength, this._maxInputLookahead);
    }

    public DataFormatReaders withOptimalMatch(MatchStrength matchStrength) {
        if (matchStrength == this._optimalMatch) {
            return this;
        }
        return new DataFormatReaders(this._readers, matchStrength, this._minimalMatch, this._maxInputLookahead);
    }

    public DataFormatReaders withType(JavaType javaType) {
        int n2 = this._readers.length;
        ObjectReader[] arrobjectReader = new ObjectReader[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            arrobjectReader[i2] = this._readers[i2].withType(javaType);
        }
        return new DataFormatReaders(arrobjectReader, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
    }

    protected class AccessorForReader
    extends InputAccessor.Std {
        final /* synthetic */ DataFormatReaders this$0;

        public AccessorForReader(DataFormatReaders dataFormatReaders, InputStream inputStream, byte[] arrby) {
            this.this$0 = dataFormatReaders;
            super(inputStream, arrby);
        }

        public AccessorForReader(DataFormatReaders dataFormatReaders, byte[] arrby) {
            this.this$0 = dataFormatReaders;
            super(arrby);
        }

        public AccessorForReader(DataFormatReaders dataFormatReaders, byte[] arrby, int n2, int n3) {
            this.this$0 = dataFormatReaders;
            super(arrby, n2, n3);
        }

        public Match createMatcher(ObjectReader objectReader, MatchStrength matchStrength) {
            return new Match(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, objectReader, matchStrength);
        }
    }

    public static class Match {
        protected final byte[] _bufferedData;
        protected final int _bufferedLength;
        protected final int _bufferedStart;
        protected final ObjectReader _match;
        protected final MatchStrength _matchStrength;
        protected final InputStream _originalStream;

        protected Match(InputStream inputStream, byte[] arrby, int n2, int n3, ObjectReader objectReader, MatchStrength matchStrength) {
            this._originalStream = inputStream;
            this._bufferedData = arrby;
            this._bufferedStart = n2;
            this._bufferedLength = n3;
            this._match = objectReader;
            this._matchStrength = matchStrength;
        }

        public JsonParser createParserWithMatch() throws IOException {
            if (this._match == null) {
                return null;
            }
            JsonFactory jsonFactory = this._match.getFactory();
            if (this._originalStream == null) {
                return jsonFactory.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength);
            }
            return jsonFactory.createParser(this.getDataStream());
        }

        public InputStream getDataStream() {
            if (this._originalStream == null) {
                return new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength);
            }
            return new MergedStream(null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
        }

        public MatchStrength getMatchStrength() {
            if (this._matchStrength == null) {
                return MatchStrength.INCONCLUSIVE;
            }
            return this._matchStrength;
        }

        public String getMatchedFormatName() {
            return this._match.getFactory().getFormatName();
        }

        public ObjectReader getReader() {
            return this._match;
        }

        public boolean hasMatch() {
            if (this._match != null) {
                return true;
            }
            return false;
        }
    }

}

