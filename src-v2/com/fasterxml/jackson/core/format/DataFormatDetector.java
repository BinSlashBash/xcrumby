/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.format.DataFormatMatcher;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DataFormatDetector {
    public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
    protected final JsonFactory[] _detectors;
    protected final int _maxInputLookahead;
    protected final MatchStrength _minimalMatch;
    protected final MatchStrength _optimalMatch;

    public DataFormatDetector(Collection<JsonFactory> collection) {
        this(collection.toArray(new JsonFactory[collection.size()]));
    }

    public /* varargs */ DataFormatDetector(JsonFactory ... arrjsonFactory) {
        this(arrjsonFactory, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
    }

    private DataFormatDetector(JsonFactory[] arrjsonFactory, MatchStrength matchStrength, MatchStrength matchStrength2, int n2) {
        this._detectors = arrjsonFactory;
        this._optimalMatch = matchStrength;
        this._minimalMatch = matchStrength2;
        this._maxInputLookahead = n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private DataFormatMatcher _findFormat(InputAccessor.Std var1_1) throws IOException {
        var5_2 = null;
        var4_3 = null;
        var10_4 = this._detectors;
        var3_5 = var10_4.length;
        var2_6 = 0;
        do {
            var7_8 = var5_2;
            var6_7 = var4_3;
            if (var2_6 >= var3_5) return var1_1.createMatcher((JsonFactory)var7_8, (MatchStrength)var6_7);
            var9_10 = var10_4[var2_6];
            var1_1.reset();
            var8_9 = var9_10.hasFormat(var1_1);
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
                    return var1_1.createMatcher((JsonFactory)var7_8, (MatchStrength)var6_7);
                }
            }
lbl32: // 6 sources:
            ++var2_6;
            var5_2 = var6_7;
            var4_3 = var7_8;
        } while (true);
    }

    public DataFormatMatcher findFormat(InputStream inputStream) throws IOException {
        return this._findFormat(new InputAccessor.Std(inputStream, new byte[this._maxInputLookahead]));
    }

    public DataFormatMatcher findFormat(byte[] arrby) throws IOException {
        return this._findFormat(new InputAccessor.Std(arrby));
    }

    public DataFormatMatcher findFormat(byte[] arrby, int n2, int n3) throws IOException {
        return this._findFormat(new InputAccessor.Std(arrby, n2, n3));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = this._detectors.length;
        if (n2 > 0) {
            stringBuilder.append(this._detectors[0].getFormatName());
            for (int i2 = 1; i2 < n2; ++i2) {
                stringBuilder.append(", ");
                stringBuilder.append(this._detectors[i2].getFormatName());
            }
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public DataFormatDetector withMaxInputLookahead(int n2) {
        if (n2 == this._maxInputLookahead) {
            return this;
        }
        return new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, n2);
    }

    public DataFormatDetector withMinimalMatch(MatchStrength matchStrength) {
        if (matchStrength == this._minimalMatch) {
            return this;
        }
        return new DataFormatDetector(this._detectors, this._optimalMatch, matchStrength, this._maxInputLookahead);
    }

    public DataFormatDetector withOptimalMatch(MatchStrength matchStrength) {
        if (matchStrength == this._optimalMatch) {
            return this;
        }
        return new DataFormatDetector(this._detectors, matchStrength, this._minimalMatch, this._maxInputLookahead);
    }
}

