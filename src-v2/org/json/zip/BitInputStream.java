/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import java.io.IOException;
import java.io.InputStream;
import org.json.zip.BitReader;

public class BitInputStream
implements BitReader {
    static final int[] mask = new int[]{0, 1, 3, 7, 15, 31, 63, 127, 255};
    private int available = 0;
    private InputStream in;
    private long nrBits = 0;
    private int unread = 0;

    public BitInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public BitInputStream(InputStream inputStream, int n2) {
        this.in = inputStream;
        this.unread = n2;
        this.available = 8;
    }

    @Override
    public boolean bit() throws IOException {
        if (this.read(1) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public long nrBits() {
        return this.nrBits;
    }

    @Override
    public boolean pad(int n2) throws IOException {
        int n3 = (int)(this.nrBits % (long)n2);
        boolean bl2 = true;
        for (int i2 = 0; i2 < n2 - n3; ++i2) {
            if (!this.bit()) continue;
            bl2 = false;
        }
        return bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int read(int n2) throws IOException {
        if (n2 == 0) {
            return 0;
        }
        if (n2 < 0) throw new IOException("Bad read width.");
        if (n2 > 32) {
            throw new IOException("Bad read width.");
        }
        int n3 = 0;
        do {
            int n4;
            int n5 = n3;
            if (n2 <= 0) return n5;
            if (this.available == 0) {
                this.unread = this.in.read();
                if (this.unread < 0) {
                    throw new IOException("Attempt to read past end.");
                }
                this.available = 8;
            }
            n5 = n4 = n2;
            if (n4 > this.available) {
                n5 = this.available;
            }
            n3 |= (this.unread >>> this.available - n5 & mask[n5]) << n2 - n5;
            this.nrBits += (long)n5;
            this.available -= n5;
            n2 -= n5;
        } while (true);
    }
}

