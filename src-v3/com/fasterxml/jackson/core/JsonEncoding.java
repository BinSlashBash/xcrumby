package com.fasterxml.jackson.core;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Hex;

public enum JsonEncoding {
    UTF8(Hex.DEFAULT_CHARSET_NAME, false, 8),
    UTF16_BE(CharEncoding.UTF_16BE, true, 16),
    UTF16_LE(CharEncoding.UTF_16LE, false, 16),
    UTF32_BE("UTF-32BE", true, 32),
    UTF32_LE("UTF-32LE", false, 32);
    
    protected final boolean _bigEndian;
    protected final int _bits;
    protected final String _javaName;

    private JsonEncoding(String javaName, boolean bigEndian, int bits) {
        this._javaName = javaName;
        this._bigEndian = bigEndian;
        this._bits = bits;
    }

    public String getJavaName() {
        return this._javaName;
    }

    public boolean isBigEndian() {
        return this._bigEndian;
    }

    public int bits() {
        return this._bits;
    }
}
