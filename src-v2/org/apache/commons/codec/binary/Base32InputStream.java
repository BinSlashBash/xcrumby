/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import java.io.InputStream;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.BaseNCodec;
import org.apache.commons.codec.binary.BaseNCodecInputStream;

public class Base32InputStream
extends BaseNCodecInputStream {
    public Base32InputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    public Base32InputStream(InputStream inputStream, boolean bl2) {
        super(inputStream, new Base32(false), bl2);
    }

    public Base32InputStream(InputStream inputStream, boolean bl2, int n2, byte[] arrby) {
        super(inputStream, new Base32(n2, arrby), bl2);
    }
}

