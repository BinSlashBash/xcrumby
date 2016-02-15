/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import java.io.IOException;

public interface BitWriter {
    public long nrBits();

    public void one() throws IOException;

    public void pad(int var1) throws IOException;

    public void write(int var1, int var2) throws IOException;

    public void zero() throws IOException;
}

