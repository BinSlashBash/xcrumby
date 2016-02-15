package org.json.zip;

import java.io.IOException;

public interface BitReader {
    boolean bit() throws IOException;

    long nrBits();

    boolean pad(int i) throws IOException;

    int read(int i) throws IOException;
}
