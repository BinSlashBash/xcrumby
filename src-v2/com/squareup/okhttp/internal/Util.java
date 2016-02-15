/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal;

import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.spdy.Header;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.ByteString;
import okio.Source;

public final class Util {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final RetryableSink EMPTY_SINK;
    public static final String[] EMPTY_STRING_ARRAY;
    public static final Charset US_ASCII;
    public static final Charset UTF_8;

    static {
        EMPTY_STRING_ARRAY = new String[0];
        US_ASCII = Charset.forName("US-ASCII");
        UTF_8 = Charset.forName("UTF-8");
        EMPTY_SINK = new RetryableSink(0);
    }

    private Util() {
    }

    public static void checkOffsetAndCount(long l2, long l3, long l4) {
        if ((l3 | l4) < 0 || l3 > l2 || l2 - l3 < l4) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void closeAll(Closeable closeable, Closeable object) throws IOException {
        block9 : {
            Object var2_3 = null;
            try {
                closeable.close();
                closeable = var2_3;
            }
            catch (Throwable var0_1) {}
            try {
                object.close();
                object = closeable;
            }
            catch (Throwable var2_4) {
                object = closeable;
                if (closeable != null) break block9;
                object = var2_4;
            }
        }
        if (object == null) {
            return;
        }
        if (object instanceof IOException) {
            throw (IOException)object;
        }
        if (object instanceof RuntimeException) {
            throw (RuntimeException)object;
        }
        if (object instanceof Error) {
            throw (Error)object;
        }
        throw new AssertionError(object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeQuietly(Closeable closeable) {
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeQuietly(ServerSocket serverSocket) {
        if (serverSocket == null) return;
        try {
            serverSocket.close();
            return;
        }
        catch (RuntimeException var0_1) {
            throw var0_1;
        }
        catch (Exception var0_2) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeQuietly(Socket socket) {
        if (socket == null) return;
        try {
            socket.close();
            return;
        }
        catch (RuntimeException var0_1) {
            throw var0_1;
        }
        catch (Exception var0_2) {
            return;
        }
    }

    public static void deleteContents(File file2) throws IOException {
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

    public static RetryableSink emptySink() {
        return EMPTY_SINK;
    }

    public static boolean equal(Object object, Object object2) {
        if (object == object2 || object != null && object.equals(object2)) {
            return true;
        }
        return false;
    }

    public static int getDefaultPort(String string2) {
        if ("http".equals(string2)) {
            return 80;
        }
        if ("https".equals(string2)) {
            return 443;
        }
        return -1;
    }

    private static int getEffectivePort(String string2, int n2) {
        if (n2 != -1) {
            return n2;
        }
        return Util.getDefaultPort(string2);
    }

    public static int getEffectivePort(URI uRI) {
        return Util.getEffectivePort(uRI.getScheme(), uRI.getPort());
    }

    public static int getEffectivePort(URL uRL) {
        return Util.getEffectivePort(uRL.getProtocol(), uRL.getPort());
    }

    public static String hash(String string2) {
        try {
            string2 = ByteString.of(MessageDigest.getInstance("MD5").digest(string2.getBytes("UTF-8"))).hex();
            return string2;
        }
        catch (NoSuchAlgorithmException var0_1) {
            throw new AssertionError(var0_1);
        }
        catch (UnsupportedEncodingException var0_2) {
            throw new AssertionError(var0_2);
        }
    }

    public static /* varargs */ List<Header> headerEntries(String ... arrstring) {
        ArrayList<Header> arrayList = new ArrayList<Header>(arrstring.length / 2);
        for (int i2 = 0; i2 < arrstring.length; i2 += 2) {
            arrayList.add(new Header(arrstring[i2], arrstring[i2 + 1]));
        }
        return arrayList;
    }

    public static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(new ArrayList<T>(list));
    }

    public static /* varargs */ <T> List<T> immutableList(T ... arrT) {
        return Collections.unmodifiableList(Arrays.asList((Object[])arrT.clone()));
    }

    public static boolean skipAll(Source source, int n2) throws IOException {
        long l2 = System.nanoTime();
        Buffer buffer = new Buffer();
        while (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - l2) < (long)n2) {
            if (source.read(buffer, 2048) == -1) {
                return true;
            }
            buffer.clear();
        }
        return false;
    }

    public static ThreadFactory threadFactory(final String string2, final boolean bl2) {
        return new ThreadFactory(){

            @Override
            public Thread newThread(Runnable runnable) {
                runnable = new Thread(runnable, string2);
                runnable.setDaemon(bl2);
                return runnable;
            }
        };
    }

}

