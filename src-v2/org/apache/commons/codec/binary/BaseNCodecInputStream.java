/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.codec.binary.BaseNCodec;

public class BaseNCodecInputStream
extends FilterInputStream {
    private final BaseNCodec baseNCodec;
    private final BaseNCodec.Context context = new BaseNCodec.Context();
    private final boolean doEncode;
    private final byte[] singleByte = new byte[1];

    protected BaseNCodecInputStream(InputStream inputStream, BaseNCodec baseNCodec, boolean bl2) {
        super(inputStream);
        this.doEncode = bl2;
        this.baseNCodec = baseNCodec;
    }

    @Override
    public int available() throws IOException {
        if (this.context.eof) {
            return 0;
        }
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void mark(int n2) {
        // MONITORENTER : this
        // MONITOREXIT : this
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        int n2 = this.read(this.singleByte, 0, 1);
        while (n2 == 0) {
            n2 = this.read(this.singleByte, 0, 1);
        }
        if (n2 > 0) {
            int n3;
            n2 = n3 = this.singleByte[0];
            if (n3 < 0) {
                n2 = n3 + 256;
            }
            return n2;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int read(byte[] arrby, int n2, int n3) throws IOException {
        if (arrby == null) {
            throw new NullPointerException();
        }
        if (n2 < 0) throw new IndexOutOfBoundsException();
        if (n3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 > arrby.length) throw new IndexOutOfBoundsException();
        if (n2 + n3 > arrby.length) {
            throw new IndexOutOfBoundsException();
        }
        if (n3 == 0) {
            return 0;
        }
        int n4 = 0;
        do {
            int n5 = n4;
            if (n4 != 0) return n5;
            if (!this.baseNCodec.hasData(this.context)) {
                n4 = this.doEncode ? 4096 : 8192;
                byte[] arrby2 = new byte[n4];
                n4 = this.in.read(arrby2);
                if (this.doEncode) {
                    this.baseNCodec.encode(arrby2, 0, n4, this.context);
                } else {
                    this.baseNCodec.decode(arrby2, 0, n4, this.context);
                }
            }
            n4 = this.baseNCodec.readResults(arrby, n2, n3, this.context);
        } while (true);
    }

    @Override
    public void reset() throws IOException {
        synchronized (this) {
            throw new IOException("mark/reset not supported");
        }
    }

    @Override
    public long skip(long l2) throws IOException {
        if (l2 < 0) {
            throw new IllegalArgumentException("Negative skip length: " + l2);
        }
        byte[] arrby = new byte[512];
        long l3 = l2;
        int n2;
        while (l3 > 0 && (n2 = this.read(arrby, 0, (int)Math.min((long)arrby.length, l3))) != -1) {
            l3 -= (long)n2;
        }
        return l2 - l3;
    }
}

