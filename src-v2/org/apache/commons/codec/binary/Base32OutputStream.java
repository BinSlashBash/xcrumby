/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import java.io.OutputStream;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.BaseNCodec;
import org.apache.commons.codec.binary.BaseNCodecOutputStream;

public class Base32OutputStream
extends BaseNCodecOutputStream {
    public Base32OutputStream(OutputStream outputStream) {
        this(outputStream, true);
    }

    public Base32OutputStream(OutputStream outputStream, boolean bl2) {
        super(outputStream, new Base32(false), bl2);
    }

    public Base32OutputStream(OutputStream outputStream, boolean bl2, int n2, byte[] arrby) {
        super(outputStream, new Base32(n2, arrby), bl2);
    }
}

