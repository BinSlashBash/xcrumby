/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.Serializable;
import java.util.Arrays;

public final class Base64Variant
implements Serializable {
    public static final int BASE64_VALUE_INVALID = -1;
    public static final int BASE64_VALUE_PADDING = -2;
    private static final int INT_SPACE = 32;
    static final char PADDING_CHAR_NONE = '\u0000';
    private static final long serialVersionUID = 1;
    private final transient int[] _asciiToBase64 = new int[128];
    private final transient byte[] _base64ToAsciiB = new byte[64];
    private final transient char[] _base64ToAsciiC = new char[64];
    protected final transient int _maxLineLength;
    protected final String _name;
    protected final transient char _paddingChar;
    protected final transient boolean _usesPadding;

    public Base64Variant(Base64Variant base64Variant, String string2, int n2) {
        this(base64Variant, string2, base64Variant._usesPadding, base64Variant._paddingChar, n2);
    }

    public Base64Variant(Base64Variant base64Variant, String string2, boolean bl2, char c2, int n2) {
        this._name = string2;
        string2 = (String)base64Variant._base64ToAsciiB;
        System.arraycopy(string2, 0, this._base64ToAsciiB, 0, string2.length);
        string2 = (String)base64Variant._base64ToAsciiC;
        System.arraycopy(string2, 0, this._base64ToAsciiC, 0, string2.length);
        base64Variant = (Base64Variant)base64Variant._asciiToBase64;
        System.arraycopy(base64Variant, 0, this._asciiToBase64, 0, base64Variant.length);
        this._usesPadding = bl2;
        this._paddingChar = c2;
        this._maxLineLength = n2;
    }

    public Base64Variant(String string2, String string3, boolean bl2, char c2, int n2) {
        this._name = string2;
        this._usesPadding = bl2;
        this._paddingChar = c2;
        this._maxLineLength = n2;
        int n3 = string3.length();
        if (n3 != 64) {
            throw new IllegalArgumentException("Base64Alphabet length must be exactly 64 (was " + n3 + ")");
        }
        string3.getChars(0, n3, this._base64ToAsciiC, 0);
        Arrays.fill(this._asciiToBase64, -1);
        n2 = 0;
        while (n2 < n3) {
            char c3 = this._base64ToAsciiC[n2];
            this._base64ToAsciiB[n2] = (byte)c3;
            this._asciiToBase64[c3] = n2++;
        }
        if (bl2) {
            this._asciiToBase64[c2] = -2;
        }
    }

    protected void _reportBase64EOF() throws IllegalArgumentException {
        throw new IllegalArgumentException("Unexpected end-of-String in base64 content");
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _reportInvalidBase64(char c2, int n2, String string2) throws IllegalArgumentException {
        String string3 = c2 <= ' ' ? "Illegal white space character (code 0x" + Integer.toHexString(c2) + ") as character #" + (n2 + 1) + " of 4-char base64 unit: can only used between units" : (this.usesPaddingChar(c2) ? "Unexpected padding character ('" + this.getPaddingChar() + "') as character #" + (n2 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character" : (!Character.isDefined(c2) || Character.isISOControl(c2) ? "Illegal character (code 0x" + Integer.toHexString(c2) + ") in base64 content" : "Illegal character '" + c2 + "' (code 0x" + Integer.toHexString(c2) + ") in base64 content"));
        String string4 = string3;
        if (string2 != null) {
            string4 = string3 + ": " + string2;
        }
        throw new IllegalArgumentException(string4);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void decode(String string2, ByteArrayBuilder byteArrayBuilder) throws IllegalArgumentException {
        int n2 = 0;
        int n3 = string2.length();
        block0 : while (n2 < n3) {
            do {
                int n4 = n2 + 1;
                char c2 = string2.charAt(n2);
                if (n4 >= n3) {
                    return;
                }
                if (c2 > ' ') {
                    int n5 = this.decodeBase64Char(c2);
                    if (n5 < 0) {
                        this._reportInvalidBase64(c2, 0, null);
                    }
                    if (n4 >= n3) {
                        this._reportBase64EOF();
                    }
                    n2 = n4 + 1;
                    c2 = string2.charAt(n4);
                    n4 = this.decodeBase64Char(c2);
                    if (n4 < 0) {
                        this._reportInvalidBase64(c2, 1, null);
                    }
                    n5 = n5 << 6 | n4;
                    if (n2 >= n3) {
                        if (!this.usesPadding()) {
                            byteArrayBuilder.append(n5 >> 4);
                            return;
                        }
                        this._reportBase64EOF();
                    }
                    n4 = n2 + 1;
                    c2 = string2.charAt(n2);
                    n2 = this.decodeBase64Char(c2);
                    if (n2 < 0) {
                        if (n2 != -2) {
                            this._reportInvalidBase64(c2, 2, null);
                        }
                        if (n4 >= n3) {
                            this._reportBase64EOF();
                        }
                        n2 = n4 + 1;
                        c2 = string2.charAt(n4);
                        if (!this.usesPaddingChar(c2)) {
                            this._reportInvalidBase64(c2, 3, "expected padding character '" + this.getPaddingChar() + "'");
                        }
                        byteArrayBuilder.append(n5 >> 4);
                        continue block0;
                    }
                    n5 = n5 << 6 | n2;
                    if (n4 >= n3) {
                        if (!this.usesPadding()) {
                            byteArrayBuilder.appendTwoBytes(n5 >> 2);
                            return;
                        }
                        this._reportBase64EOF();
                    }
                    n2 = n4 + 1;
                    c2 = string2.charAt(n4);
                    n4 = this.decodeBase64Char(c2);
                    if (n4 < 0) {
                        if (n4 != -2) {
                            this._reportInvalidBase64(c2, 3, null);
                        }
                        byteArrayBuilder.appendTwoBytes(n5 >> 2);
                        continue block0;
                    }
                    byteArrayBuilder.appendThreeBytes(n5 << 6 | n4);
                    continue block0;
                }
                n2 = n4;
            } while (true);
            break;
        }
        return;
    }

    public byte[] decode(String string2) throws IllegalArgumentException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        this.decode(string2, byteArrayBuilder);
        return byteArrayBuilder.toByteArray();
    }

    public int decodeBase64Byte(byte by2) {
        if (by2 <= 127) {
            return this._asciiToBase64[by2];
        }
        return -1;
    }

    public int decodeBase64Char(char c2) {
        if (c2 <= '') {
            return this._asciiToBase64[c2];
        }
        return -1;
    }

    public int decodeBase64Char(int n2) {
        if (n2 <= 127) {
            return this._asciiToBase64[n2];
        }
        return -1;
    }

    public String encode(byte[] arrby) {
        return this.encode(arrby, false);
    }

    public String encode(byte[] arrby, boolean bl2) {
        int n2;
        int n3 = arrby.length;
        StringBuilder stringBuilder = new StringBuilder((n3 >> 2) + n3 + (n3 >> 3));
        if (bl2) {
            stringBuilder.append('\"');
        }
        int n4 = this.getMaxLineLength() >> 2;
        int n5 = 0;
        while (n5 <= n3 - 3) {
            int n6 = n5 + 1;
            n5 = arrby[n5];
            n2 = n6 + 1;
            this.encodeBase64Chunk(stringBuilder, (n5 << 8 | arrby[n6] & 255) << 8 | arrby[n2] & 255);
            n4 = n5 = n4 - 1;
            if (n5 <= 0) {
                stringBuilder.append('\\');
                stringBuilder.append('n');
                n4 = this.getMaxLineLength() >> 2;
            }
            n5 = n2 + 1;
        }
        if ((n3 -= n5) > 0) {
            n2 = n5 + 1;
            n4 = n5 = arrby[n5] << 16;
            if (n3 == 2) {
                n4 = n5 | (arrby[n2] & 255) << 8;
            }
            this.encodeBase64Partial(stringBuilder, n4, n3);
        }
        if (bl2) {
            stringBuilder.append('\"');
        }
        return stringBuilder.toString();
    }

    public byte encodeBase64BitsAsByte(int n2) {
        return this._base64ToAsciiB[n2];
    }

    public char encodeBase64BitsAsChar(int n2) {
        return this._base64ToAsciiC[n2];
    }

    public int encodeBase64Chunk(int n2, byte[] arrby, int n3) {
        int n4 = n3 + 1;
        arrby[n3] = this._base64ToAsciiB[n2 >> 18 & 63];
        n3 = n4 + 1;
        arrby[n4] = this._base64ToAsciiB[n2 >> 12 & 63];
        n4 = n3 + 1;
        arrby[n3] = this._base64ToAsciiB[n2 >> 6 & 63];
        arrby[n4] = this._base64ToAsciiB[n2 & 63];
        return n4 + 1;
    }

    public int encodeBase64Chunk(int n2, char[] arrc, int n3) {
        int n4 = n3 + 1;
        arrc[n3] = this._base64ToAsciiC[n2 >> 18 & 63];
        n3 = n4 + 1;
        arrc[n4] = this._base64ToAsciiC[n2 >> 12 & 63];
        n4 = n3 + 1;
        arrc[n3] = this._base64ToAsciiC[n2 >> 6 & 63];
        arrc[n4] = this._base64ToAsciiC[n2 & 63];
        return n4 + 1;
    }

    public void encodeBase64Chunk(StringBuilder stringBuilder, int n2) {
        stringBuilder.append(this._base64ToAsciiC[n2 >> 18 & 63]);
        stringBuilder.append(this._base64ToAsciiC[n2 >> 12 & 63]);
        stringBuilder.append(this._base64ToAsciiC[n2 >> 6 & 63]);
        stringBuilder.append(this._base64ToAsciiC[n2 & 63]);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int encodeBase64Partial(int n2, int n3, byte[] arrby, int n4) {
        int n5 = n4 + 1;
        arrby[n4] = this._base64ToAsciiB[n2 >> 18 & 63];
        int n6 = n5 + 1;
        arrby[n5] = this._base64ToAsciiB[n2 >> 12 & 63];
        if (this._usesPadding) {
            byte by2 = (byte)this._paddingChar;
            n5 = n6 + 1;
            byte by3 = n3 == 2 ? this._base64ToAsciiB[n2 >> 6 & 63] : by2;
            arrby[n6] = by3;
            n4 = n5 + 1;
            arrby[n5] = by2;
            return n4;
        } else {
            n4 = n6;
            if (n3 != 2) return n4;
            {
                arrby[n6] = this._base64ToAsciiB[n2 >> 6 & 63];
                return n6 + 1;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public int encodeBase64Partial(int n2, int n3, char[] arrc, int n4) {
        int n5 = n4 + 1;
        arrc[n4] = this._base64ToAsciiC[n2 >> 18 & 63];
        int n6 = n5 + 1;
        arrc[n5] = this._base64ToAsciiC[n2 >> 12 & 63];
        if (this._usesPadding) {
            n5 = n6 + 1;
            char c2 = n3 == 2 ? this._base64ToAsciiC[n2 >> 6 & 63] : this._paddingChar;
            arrc[n6] = c2;
            n4 = n5 + 1;
            arrc[n5] = this._paddingChar;
            return n4;
        } else {
            n4 = n6;
            if (n3 != 2) return n4;
            {
                arrc[n6] = this._base64ToAsciiC[n2 >> 6 & 63];
                return n6 + 1;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void encodeBase64Partial(StringBuilder stringBuilder, int n2, int n3) {
        stringBuilder.append(this._base64ToAsciiC[n2 >> 18 & 63]);
        stringBuilder.append(this._base64ToAsciiC[n2 >> 12 & 63]);
        if (this._usesPadding) {
            char c2 = n3 == 2 ? this._base64ToAsciiC[n2 >> 6 & 63] : this._paddingChar;
            stringBuilder.append(c2);
            stringBuilder.append(this._paddingChar);
            return;
        } else {
            if (n3 != 2) return;
            {
                stringBuilder.append(this._base64ToAsciiC[n2 >> 6 & 63]);
                return;
            }
        }
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        return false;
    }

    public int getMaxLineLength() {
        return this._maxLineLength;
    }

    public String getName() {
        return this._name;
    }

    public byte getPaddingByte() {
        return (byte)this._paddingChar;
    }

    public char getPaddingChar() {
        return this._paddingChar;
    }

    public int hashCode() {
        return this._name.hashCode();
    }

    protected Object readResolve() {
        return Base64Variants.valueOf(this._name);
    }

    public String toString() {
        return this._name;
    }

    public boolean usesPadding() {
        return this._usesPadding;
    }

    public boolean usesPaddingChar(char c2) {
        if (c2 == this._paddingChar) {
            return true;
        }
        return false;
    }

    public boolean usesPaddingChar(int n2) {
        if (n2 == this._paddingChar) {
            return true;
        }
        return false;
    }
}

