/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import com.fasterxml.jackson.core.io.UTF32Reader;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ByteSourceJsonBootstrapper {
    static final byte UTF8_BOM_1 = -17;
    static final byte UTF8_BOM_2 = -69;
    static final byte UTF8_BOM_3 = -65;
    protected boolean _bigEndian = true;
    private final boolean _bufferRecyclable;
    protected int _bytesPerChar = 0;
    protected final IOContext _context;
    protected final InputStream _in;
    protected final byte[] _inputBuffer;
    private int _inputEnd;
    protected int _inputProcessed;
    private int _inputPtr;

    public ByteSourceJsonBootstrapper(IOContext iOContext, InputStream inputStream) {
        this._context = iOContext;
        this._in = inputStream;
        this._inputBuffer = iOContext.allocReadIOBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._inputProcessed = 0;
        this._bufferRecyclable = true;
    }

    public ByteSourceJsonBootstrapper(IOContext iOContext, byte[] arrby, int n2, int n3) {
        this._context = iOContext;
        this._in = null;
        this._inputBuffer = arrby;
        this._inputPtr = n2;
        this._inputEnd = n2 + n3;
        this._inputProcessed = - n2;
        this._bufferRecyclable = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkUTF16(int n2) {
        boolean bl2 = false;
        if ((65280 & n2) == 0) {
            this._bigEndian = true;
        } else {
            if ((n2 & 255) != 0) return bl2;
            this._bigEndian = false;
        }
        this._bytesPerChar = 2;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkUTF32(int n2) throws IOException {
        boolean bl2 = false;
        if (n2 >> 8 == 0) {
            this._bigEndian = true;
        } else if ((16777215 & n2) == 0) {
            this._bigEndian = false;
        } else if ((-16711681 & n2) == 0) {
            this.reportWeirdUCS4("3412");
        } else {
            if ((-65281 & n2) != 0) return bl2;
            this.reportWeirdUCS4("2143");
        }
        this._bytesPerChar = 4;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean handleBOM(int n2) throws IOException {
        switch (n2) {
            case 65279: {
                this._bigEndian = true;
                this._inputPtr += 4;
                this._bytesPerChar = 4;
                return true;
            }
            case -131072: {
                this._inputPtr += 4;
                this._bytesPerChar = 4;
                this._bigEndian = false;
                return true;
            }
            case 65534: {
                this.reportWeirdUCS4("2143");
            }
            case -16842752: {
                this.reportWeirdUCS4("3412");
            }
        }
        int n3 = n2 >>> 16;
        if (n3 == 65279) {
            this._inputPtr += 2;
            this._bytesPerChar = 2;
            this._bigEndian = true;
            return true;
        }
        if (n3 == 65534) {
            this._inputPtr += 2;
            this._bytesPerChar = 2;
            this._bigEndian = false;
            return true;
        }
        if (n2 >>> 8 == 15711167) {
            this._inputPtr += 3;
            this._bytesPerChar = 1;
            this._bigEndian = true;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
        byte by2;
        MatchStrength matchStrength;
        int n2;
        if (!inputAccessor.hasMoreBytes()) {
            return MatchStrength.INCONCLUSIVE;
        }
        byte by3 = by2 = inputAccessor.nextByte();
        if (by2 == -17) {
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (inputAccessor.nextByte() != -69) {
                return MatchStrength.NO_MATCH;
            }
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (inputAccessor.nextByte() != -65) {
                return MatchStrength.NO_MATCH;
            }
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            by3 = inputAccessor.nextByte();
        }
        if ((n2 = ByteSourceJsonBootstrapper.skipSpace(inputAccessor, by3)) < 0) {
            return MatchStrength.INCONCLUSIVE;
        }
        if (n2 == 123) {
            n2 = ByteSourceJsonBootstrapper.skipSpace(inputAccessor);
            if (n2 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (n2 == 34) return MatchStrength.SOLID_MATCH;
            if (n2 != 125) return MatchStrength.NO_MATCH;
            return MatchStrength.SOLID_MATCH;
        }
        if (n2 == 91) {
            n2 = ByteSourceJsonBootstrapper.skipSpace(inputAccessor);
            if (n2 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (n2 == 93) return MatchStrength.SOLID_MATCH;
            if (n2 != 91) return MatchStrength.SOLID_MATCH;
            return MatchStrength.SOLID_MATCH;
        }
        MatchStrength matchStrength2 = matchStrength = MatchStrength.WEAK_MATCH;
        if (n2 == 34) return matchStrength2;
        if (n2 <= 57) {
            matchStrength2 = matchStrength;
            if (n2 >= 48) return matchStrength2;
        }
        if (n2 == 45) {
            n2 = ByteSourceJsonBootstrapper.skipSpace(inputAccessor);
            if (n2 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (n2 > 57) return MatchStrength.NO_MATCH;
            matchStrength2 = matchStrength;
            if (n2 >= 48) return matchStrength2;
            return MatchStrength.NO_MATCH;
        }
        if (n2 == 110) {
            return ByteSourceJsonBootstrapper.tryMatch(inputAccessor, "ull", matchStrength);
        }
        if (n2 == 116) {
            return ByteSourceJsonBootstrapper.tryMatch(inputAccessor, "rue", matchStrength);
        }
        if (n2 != 102) return MatchStrength.NO_MATCH;
        return ByteSourceJsonBootstrapper.tryMatch(inputAccessor, "alse", matchStrength);
    }

    private void reportWeirdUCS4(String string2) throws IOException {
        throw new CharConversionException("Unsupported UCS-4 endianness (" + string2 + ") detected");
    }

    private static int skipSpace(InputAccessor inputAccessor) throws IOException {
        if (!inputAccessor.hasMoreBytes()) {
            return -1;
        }
        return ByteSourceJsonBootstrapper.skipSpace(inputAccessor, inputAccessor.nextByte());
    }

    private static int skipSpace(InputAccessor inputAccessor, byte by2) throws IOException {
        while ((by2 = (byte)(by2 & 255)) == 32 || by2 == 13 || by2 == 10 || by2 == 9) {
            if (!inputAccessor.hasMoreBytes()) {
                return -1;
            }
            by2 = inputAccessor.nextByte();
        }
        return by2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static MatchStrength tryMatch(InputAccessor inputAccessor, String string2, MatchStrength matchStrength) throws IOException {
        int n2 = 0;
        int n3 = string2.length();
        do {
            MatchStrength matchStrength2 = matchStrength;
            if (n2 >= n3) return matchStrength2;
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (inputAccessor.nextByte() != string2.charAt(n2)) {
                return MatchStrength.NO_MATCH;
            }
            ++n2;
        } while (true);
    }

    public JsonParser constructParser(int n2, ObjectCodec objectCodec, BytesToNameCanonicalizer bytesToNameCanonicalizer, CharsToNameCanonicalizer charsToNameCanonicalizer, int n3) throws IOException {
        if (this.detectEncoding() == JsonEncoding.UTF8 && JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(n3)) {
            bytesToNameCanonicalizer = bytesToNameCanonicalizer.makeChild(n3);
            return new UTF8StreamJsonParser(this._context, n2, this._in, objectCodec, bytesToNameCanonicalizer, this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
        }
        return new ReaderBasedJsonParser(this._context, n2, this.constructReader(), objectCodec, charsToNameCanonicalizer.makeChild(n3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public Reader constructReader() throws IOException {
        JsonEncoding jsonEncoding = this._context.getEncoding();
        switch (jsonEncoding.bits()) {
            default: {
                throw new RuntimeException("Internal error");
            }
            case 8: 
            case 16: {
                InputStream inputStream = this._in;
                if (inputStream == null) {
                    inputStream = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
                    return new InputStreamReader(inputStream, jsonEncoding.getJavaName());
                }
                if (this._inputPtr >= this._inputEnd) return new InputStreamReader(inputStream, jsonEncoding.getJavaName());
                inputStream = new MergedStream(this._context, inputStream, this._inputBuffer, this._inputPtr, this._inputEnd);
                return new InputStreamReader(inputStream, jsonEncoding.getJavaName());
            }
            case 32: 
        }
        return new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonEncoding detectEncoding() throws IOException {
        JsonEncoding jsonEncoding;
        boolean bl2;
        boolean bl3 = false;
        if (this.ensureLoaded(4)) {
            int n2 = this._inputBuffer[this._inputPtr] << 24 | (this._inputBuffer[this._inputPtr + 1] & 255) << 16 | (this._inputBuffer[this._inputPtr + 2] & 255) << 8 | this._inputBuffer[this._inputPtr + 3] & 255;
            if (this.handleBOM(n2)) {
                bl2 = true;
            } else if (this.checkUTF32(n2)) {
                bl2 = true;
            } else {
                bl2 = bl3;
                if (this.checkUTF16(n2 >>> 16)) {
                    bl2 = true;
                }
            }
        } else {
            bl2 = bl3;
            if (this.ensureLoaded(2)) {
                bl2 = bl3;
                if (this.checkUTF16((this._inputBuffer[this._inputPtr] & 255) << 8 | this._inputBuffer[this._inputPtr + 1] & 255)) {
                    bl2 = true;
                }
            }
        }
        if (!bl2) {
            jsonEncoding = JsonEncoding.UTF8;
        } else {
            switch (this._bytesPerChar) {
                default: {
                    throw new RuntimeException("Internal error");
                }
                case 1: {
                    jsonEncoding = JsonEncoding.UTF8;
                    break;
                }
                case 2: {
                    if (this._bigEndian) {
                        jsonEncoding = JsonEncoding.UTF16_BE;
                        break;
                    }
                    jsonEncoding = JsonEncoding.UTF16_LE;
                    break;
                }
                case 4: {
                    jsonEncoding = this._bigEndian ? JsonEncoding.UTF32_BE : JsonEncoding.UTF32_LE;
                }
            }
        }
        this._context.setEncoding(jsonEncoding);
        return jsonEncoding;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean ensureLoaded(int n2) throws IOException {
        boolean bl2 = true;
        int n3 = this._inputEnd - this._inputPtr;
        do {
            boolean bl3 = bl2;
            if (n3 >= n2) return bl3;
            int n4 = this._in == null ? -1 : this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
            if (n4 < 1) {
                return false;
            }
            this._inputEnd += n4;
            n3 += n4;
        } while (true);
    }
}

