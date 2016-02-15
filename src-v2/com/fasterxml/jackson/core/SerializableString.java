/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public interface SerializableString {
    public int appendQuoted(char[] var1, int var2);

    public int appendQuotedUTF8(byte[] var1, int var2);

    public int appendUnquoted(char[] var1, int var2);

    public int appendUnquotedUTF8(byte[] var1, int var2);

    public char[] asQuotedChars();

    public byte[] asQuotedUTF8();

    public byte[] asUnquotedUTF8();

    public int charLength();

    public String getValue();

    public int putQuotedUTF8(ByteBuffer var1) throws IOException;

    public int putUnquotedUTF8(ByteBuffer var1) throws IOException;

    public int writeQuotedUTF8(OutputStream var1) throws IOException;

    public int writeUnquotedUTF8(OutputStream var1) throws IOException;
}

