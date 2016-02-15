/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import java.io.IOException;
import java.io.OutputStream;
import org.json.zip.BitInputStream;
import org.json.zip.BitWriter;

public class BitOutputStream
implements BitWriter {
    private long nrBits = 0;
    private OutputStream out;
    private int unwritten;
    private int vacant = 8;

    public BitOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    @Override
    public long nrBits() {
        return this.nrBits;
    }

    @Override
    public void one() throws IOException {
        this.write(1, 1);
    }

    @Override
    public void pad(int n2) throws IOException {
        int n3 = n2 - (int)(this.nrBits % (long)n2);
        int n4 = n3 & 7;
        n2 = n3;
        if (n4 > 0) {
            this.write(0, n4);
            n2 = n3 - n4;
        }
        while (n2 > 0) {
            this.write(0, 8);
            n2 -= 8;
        }
        this.out.flush();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void write(int n2, int n3) throws IOException {
        if (n2 == 0 && n3 == 0) {
            return;
        }
        if (n3 <= 0) throw new IOException("Bad write width.");
        int n4 = n3;
        if (n3 > 32) {
            throw new IOException("Bad write width.");
        }
        while (n4 > 0) {
            int n5;
            n3 = n5 = n4;
            if (n5 > this.vacant) {
                n3 = this.vacant;
            }
            this.unwritten |= (n2 >>> n4 - n3 & BitInputStream.mask[n3]) << this.vacant - n3;
            n5 = n4 - n3;
            this.nrBits += (long)n3;
            this.vacant -= n3;
            n4 = n5;
            if (this.vacant != 0) continue;
            this.out.write(this.unwritten);
            this.unwritten = 0;
            this.vacant = 8;
            n4 = n5;
        }
    }

    @Override
    public void zero() throws IOException {
        this.write(0, 1);
    }
}

