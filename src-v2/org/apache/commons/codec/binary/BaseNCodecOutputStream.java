/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.codec.binary.BaseNCodec;

public class BaseNCodecOutputStream
extends FilterOutputStream {
    private final BaseNCodec baseNCodec;
    private final BaseNCodec.Context context = new BaseNCodec.Context();
    private final boolean doEncode;
    private final byte[] singleByte = new byte[1];

    public BaseNCodecOutputStream(OutputStream outputStream, BaseNCodec baseNCodec, boolean bl2) {
        super(outputStream);
        this.baseNCodec = baseNCodec;
        this.doEncode = bl2;
    }

    private void flush(boolean bl2) throws IOException {
        int n2 = this.baseNCodec.available(this.context);
        if (n2 > 0) {
            byte[] arrby = new byte[n2];
            if ((n2 = this.baseNCodec.readResults(arrby, 0, n2, this.context)) > 0) {
                this.out.write(arrby, 0, n2);
            }
        }
        if (bl2) {
            this.out.flush();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void close() throws IOException {
        if (this.doEncode) {
            this.baseNCodec.encode(this.singleByte, 0, -1, this.context);
        } else {
            this.baseNCodec.decode(this.singleByte, 0, -1, this.context);
        }
        this.flush();
        this.out.close();
    }

    @Override
    public void flush() throws IOException {
        this.flush(true);
    }

    @Override
    public void write(int n2) throws IOException {
        this.singleByte[0] = (byte)n2;
        this.write(this.singleByte, 0, 1);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(byte[] arrby, int n2, int n3) throws IOException {
        if (arrby == null) {
            throw new NullPointerException();
        }
        if (n2 < 0 || n3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 > arrby.length || n2 + n3 > arrby.length) {
            throw new IndexOutOfBoundsException();
        }
        if (n3 > 0) {
            if (this.doEncode) {
                this.baseNCodec.encode(arrby, n2, n3, this.context);
            } else {
                this.baseNCodec.decode(arrby, n2, n3, this.context);
            }
            this.flush(false);
        }
    }
}

