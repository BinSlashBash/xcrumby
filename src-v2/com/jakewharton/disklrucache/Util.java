/*
 * Decompiled with CFR 0_110.
 */
package com.jakewharton.disklrucache;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;

public final class Util {
    static final Charset US_ASCII = Charset.forName("US-ASCII");
    static final Charset UTF_8 = Charset.forName("UTF-8");

    private Util() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (RuntimeException var0_1) {
            throw var0_1;
        }
        catch (Exception var0_2) {
            return;
        }
    }

    static void deleteContents(File file2) throws IOException {
        File[] arrfile = file2.listFiles();
        if (arrfile == null) {
            throw new IOException("not a readable directory: " + file2);
        }
        for (File file2 : arrfile) {
            if (file2.isDirectory()) {
                Util.deleteContents(file2);
            }
            if (file2.delete()) continue;
            throw new IOException("failed to delete file: " + file2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String readFully(Reader reader) throws IOException {
        try {
            int n2;
            Object object = new StringWriter();
            char[] arrc = new char[1024];
            while ((n2 = reader.read(arrc)) != -1) {
                object.write(arrc, 0, n2);
            }
            object = object.toString();
            return object;
        }
        finally {
            reader.close();
        }
    }
}

