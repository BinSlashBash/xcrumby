package org.json.zip;

import java.io.IOException;

public interface BitWriter {
    long nrBits();

    void one() throws IOException;

    void pad(int i) throws IOException;

    void write(int i, int i2) throws IOException;

    void zero() throws IOException;
}
