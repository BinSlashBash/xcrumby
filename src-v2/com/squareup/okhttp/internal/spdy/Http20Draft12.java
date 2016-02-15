/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.FrameReader;
import com.squareup.okhttp.internal.spdy.FrameWriter;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.HeadersMode;
import com.squareup.okhttp.internal.spdy.HpackDraft07;
import com.squareup.okhttp.internal.spdy.Settings;
import com.squareup.okhttp.internal.spdy.Variant;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public final class Http20Draft12
implements Variant {
    private static final ByteString CONNECTION_PREFACE;
    static final byte FLAG_ACK = 1;
    static final byte FLAG_COMPRESSED = 32;
    static final byte FLAG_END_HEADERS = 4;
    static final byte FLAG_END_PUSH_PROMISE = 4;
    static final byte FLAG_END_SEGMENT = 2;
    static final byte FLAG_END_STREAM = 1;
    static final byte FLAG_NONE = 0;
    static final byte FLAG_PAD_HIGH = 16;
    static final byte FLAG_PAD_LOW = 8;
    static final byte FLAG_PRIORITY = 32;
    static final int MAX_FRAME_SIZE = 16383;
    static final byte TYPE_ALTSVC = 10;
    static final byte TYPE_BLOCKED = 11;
    static final byte TYPE_CONTINUATION = 9;
    static final byte TYPE_DATA = 0;
    static final byte TYPE_GOAWAY = 7;
    static final byte TYPE_HEADERS = 1;
    static final byte TYPE_PING = 6;
    static final byte TYPE_PRIORITY = 2;
    static final byte TYPE_PUSH_PROMISE = 5;
    static final byte TYPE_RST_STREAM = 3;
    static final byte TYPE_SETTINGS = 4;
    static final byte TYPE_WINDOW_UPDATE = 8;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(Http20Draft12.class.getName());
        CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    }

    private static /* varargs */ IllegalArgumentException illegalArgument(String string2, Object ... arrobject) {
        throw new IllegalArgumentException(String.format(string2, arrobject));
    }

    private static /* varargs */ IOException ioException(String string2, Object ... arrobject) throws IOException {
        throw new IOException(String.format(string2, arrobject));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static short lengthWithoutPadding(short s2, byte by2, short s3) throws IOException {
        short s4;
        if ((by2 & 16) != 0) {
            s4 = (short)(s2 - 2);
        } else {
            s4 = s2;
            if ((by2 & 8) != 0) {
                s4 = (short)(s2 - 1);
            }
        }
        if (s3 > s4) {
            throw Http20Draft12.ioException("PROTOCOL_ERROR padding %s > remaining length %s", s3, s4);
        }
        return (short)(s4 - s3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static short readPadding(BufferedSource bufferedSource, byte by2) throws IOException {
        if ((by2 & 16) != 0 && (by2 & 8) == 0) {
            throw Http20Draft12.ioException("PROTOCOL_ERROR FLAG_PAD_HIGH set without FLAG_PAD_LOW", new Object[0]);
        }
        int n2 = 0;
        if ((by2 & 16) != 0) {
            n2 = bufferedSource.readShort() & 65535;
        } else if ((by2 & 8) != 0) {
            n2 = bufferedSource.readByte() & 255;
        }
        if (n2 > 16383) {
            throw Http20Draft12.ioException("PROTOCOL_ERROR padding > %d: %d", 16383, n2);
        }
        return (short)n2;
    }

    @Override
    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    @Override
    public int maxFrameSize() {
        return 16383;
    }

    @Override
    public FrameReader newReader(BufferedSource bufferedSource, boolean bl2) {
        return new Reader(bufferedSource, 4096, bl2);
    }

    @Override
    public FrameWriter newWriter(BufferedSink bufferedSink, boolean bl2) {
        return new Writer(bufferedSink, bl2);
    }

    static final class ContinuationSource
    implements Source {
        byte flags;
        short left;
        short length;
        short padding;
        private final BufferedSource source;
        int streamId;

        public ContinuationSource(BufferedSource bufferedSource) {
            this.source = bufferedSource;
        }

        private void readContinuationHeader() throws IOException {
            short s2;
            int n2 = this.streamId;
            int n3 = this.source.readInt();
            int n4 = this.source.readInt();
            this.length = (short)((1073676288 & n3) >> 16);
            byte by2 = (byte)((65280 & n3) >> 8);
            this.flags = (byte)(n3 & 255);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(FrameLogger.formatHeader(true, this.streamId, this.length, by2, this.flags));
            }
            this.padding = Http20Draft12.readPadding(this.source, this.flags);
            this.left = s2 = Http20Draft12.lengthWithoutPadding(this.length, this.flags, this.padding);
            this.length = s2;
            this.streamId = Integer.MAX_VALUE & n4;
            if (by2 != 9) {
                throw Http20Draft12.ioException("%s != TYPE_CONTINUATION", new Object[]{Byte.valueOf(by2)});
            }
            if (this.streamId != n2) {
                throw Http20Draft12.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public long read(Buffer buffer, long l2) throws IOException {
            while (this.left == 0) {
                this.source.skip(this.padding);
                this.padding = 0;
                if ((this.flags & 4) != 0) {
                    return -1;
                }
                this.readContinuationHeader();
            }
            if ((l2 = this.source.read(buffer, Math.min(l2, (long)this.left))) == -1) {
                return -1;
            }
            this.left = (short)((long)this.left - l2);
            return l2;
        }

        @Override
        public Timeout timeout() {
            return this.source.timeout();
        }
    }

    static final class FrameLogger {
        private static final String[] BINARY;
        private static final String[] FLAGS;
        private static final String[] TYPES;

        static {
            int n2;
            int[] arrn;
            int[] arrn2;
            int[] arrn3;
            int n3;
            TYPES = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION", "ALTSVC", "BLOCKED"};
            FLAGS = new String[64];
            BINARY = new String[256];
            for (n2 = 0; n2 < BINARY.length; ++n2) {
                FrameLogger.BINARY[n2] = String.format("%8s", Integer.toBinaryString(n2)).replace(' ', '0');
            }
            FrameLogger.FLAGS[0] = "";
            FrameLogger.FLAGS[1] = "END_STREAM";
            FrameLogger.FLAGS[2] = "END_SEGMENT";
            FrameLogger.FLAGS[3] = "END_STREAM|END_SEGMENT";
            int[] arrn4 = arrn2 = new int[3];
            arrn4[0] = 1;
            arrn4[1] = 2;
            arrn4[2] = 3;
            FrameLogger.FLAGS[8] = "PAD_LOW";
            FrameLogger.FLAGS[24] = "PAD_LOW|PAD_HIGH";
            int[] arrn5 = arrn3 = new int[2];
            arrn5[0] = 8;
            arrn5[1] = 24;
            int n4 = arrn2.length;
            for (n2 = 0; n2 < n4; ++n2) {
                n3 = arrn2[n2];
                for (int n5 : arrn3) {
                    FrameLogger.FLAGS[n3 | n5] = FLAGS[n3] + '|' + FLAGS[n5];
                }
            }
            FrameLogger.FLAGS[4] = "END_HEADERS";
            FrameLogger.FLAGS[32] = "PRIORITY";
            FrameLogger.FLAGS[36] = "END_HEADERS|PRIORITY";
            int[] arrn6 = arrn = new int[3];
            arrn6[0] = 4;
            arrn6[1] = 32;
            arrn6[2] = 36;
            n3 = arrn.length;
            for (n2 = 0; n2 < n3; ++n2) {
                int n6 = arrn[n2];
                for (int n7 : arrn2) {
                    FrameLogger.FLAGS[n7 | n6] = FLAGS[n7] + '|' + FLAGS[n6];
                    for (int n8 : arrn3) {
                        FrameLogger.FLAGS[n7 | n6 | n8] = FLAGS[n7] + '|' + FLAGS[n6] + '|' + FLAGS[n8];
                    }
                }
            }
            for (n2 = 0; n2 < FLAGS.length; ++n2) {
                if (FLAGS[n2] != null) continue;
                FrameLogger.FLAGS[n2] = BINARY[n2];
            }
        }

        FrameLogger() {
        }

        /*
         * Enabled aggressive block sorting
         */
        static String formatFlags(byte by2, byte by3) {
            String string2;
            if (by3 == 0) {
                return "";
            }
            switch (by2) {
                default: {
                    string2 = by3 < FLAGS.length ? FLAGS[by3] : BINARY[by3];
                }
                case 4: 
                case 6: {
                    if (by3 == 1) {
                        return "ACK";
                    }
                    return BINARY[by3];
                }
                case 2: 
                case 3: 
                case 7: 
                case 8: 
                case 10: 
                case 11: {
                    return BINARY[by3];
                }
            }
            if (by2 == 5 && (by3 & 4) != 0) {
                return string2.replace("HEADERS", "PUSH_PROMISE");
            }
            if (by2 == 0 && (by3 & 32) != 0) {
                return string2.replace("PRIORITY", "COMPRESSED");
            }
            return string2;
        }

        /*
         * Enabled aggressive block sorting
         */
        static String formatHeader(boolean bl2, int n2, int n3, byte by2, byte by3) {
            String string2 = by2 < TYPES.length ? TYPES[by2] : String.format("0x%02x", Byte.valueOf(by2));
            String string3 = FrameLogger.formatFlags(by2, by3);
            String string4 = bl2 ? "<<" : ">>";
            return String.format("%s 0x%08x %5d %-13s %s", string4, n2, n3, string2, string3);
        }
    }

    static final class Reader
    implements FrameReader {
        private final boolean client;
        private final ContinuationSource continuation;
        final HpackDraft07.Reader hpackReader;
        private final BufferedSource source;

        Reader(BufferedSource bufferedSource, int n2, boolean bl2) {
            this.source = bufferedSource;
            this.client = bl2;
            this.continuation = new ContinuationSource(this.source);
            this.hpackReader = new HpackDraft07.Reader(n2, this.continuation);
        }

        private void readAlternateService(FrameReader.Handler handler, short s2, byte by2, int n2) throws IOException {
            long l2 = this.source.readInt();
            by2 = (byte)this.source.readShort();
            this.source.readByte();
            int n3 = this.source.readByte() & 255;
            ByteString byteString = this.source.readByteString(n3);
            int n4 = this.source.readByte() & 255;
            String string2 = this.source.readUtf8(n4);
            handler.alternateService(n2, this.source.readUtf8(s2 - 9 - n3 - n4), byteString, string2, by2 & 65535, l2 & 0xFFFFFFFFL);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void readData(FrameReader.Handler handler, short s2, byte by2, int n2) throws IOException {
            short s3 = 1;
            boolean bl2 = (by2 & 1) != 0;
            if ((by2 & 32) == 0) {
                s3 = 0;
            }
            if (s3 != 0) {
                throw Http20Draft12.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            }
            short s4 = Http20Draft12.readPadding(this.source, by2);
            s3 = Http20Draft12.lengthWithoutPadding(s2, by2, s4);
            handler.data(bl2, n2, this.source, s3);
            this.source.skip(s4);
        }

        private void readGoAway(FrameReader.Handler handler, short s2, byte by2, int n2) throws IOException {
            if (s2 < 8) {
                throw Http20Draft12.ioException("TYPE_GOAWAY length < 8: %s", new Object[]{s2});
            }
            if (n2 != 0) {
                throw Http20Draft12.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            }
            by2 = (byte)this.source.readInt();
            n2 = this.source.readInt();
            int n3 = s2 - 8;
            ErrorCode errorCode = ErrorCode.fromHttp2(n2);
            if (errorCode == null) {
                throw Http20Draft12.ioException("TYPE_GOAWAY unexpected error code: %d", new Object[]{n2});
            }
            ByteString byteString = ByteString.EMPTY;
            if (n3 > 0) {
                byteString = this.source.readByteString(n3);
            }
            handler.goAway(by2, errorCode, byteString);
        }

        private List<Header> readHeaderBlock(short s2, short s3, byte by2, int n2) throws IOException {
            ContinuationSource continuationSource = this.continuation;
            this.continuation.left = s2;
            continuationSource.length = s2;
            this.continuation.padding = s3;
            this.continuation.flags = by2;
            this.continuation.streamId = n2;
            this.hpackReader.readHeaders();
            this.hpackReader.emitReferenceSet();
            return this.hpackReader.getAndReset();
        }

        /*
         * Enabled aggressive block sorting
         */
        private void readHeaders(FrameReader.Handler handler, short s2, byte by2, int n2) throws IOException {
            if (n2 == 0) {
                throw Http20Draft12.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            }
            boolean bl2 = (by2 & 1) != 0;
            short s3 = Http20Draft12.readPadding(this.source, by2);
            short s4 = s2;
            if ((by2 & 32) != 0) {
                this.readPriority(handler, n2);
                s4 = (short)(s2 - 5);
            }
            handler.headers(false, bl2, n2, -1, this.readHeaderBlock(Http20Draft12.lengthWithoutPadding(s4, by2, s3), s3, by2, n2), HeadersMode.HTTP_20_HEADERS);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void readPing(FrameReader.Handler handler, short s2, byte by2, int n2) throws IOException {
            boolean bl2 = true;
            if (s2 != 8) {
                throw Http20Draft12.ioException("TYPE_PING length != 8: %s", new Object[]{s2});
            }
            if (n2 != 0) {
                throw Http20Draft12.ioException("TYPE_PING streamId != 0", new Object[0]);
            }
            n2 = this.source.readInt();
            int n3 = this.source.readInt();
            if ((by2 & 1) == 0) {
                bl2 = false;
            }
            handler.ping(bl2, n2, n3);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void readPriority(FrameReader.Handler handler, int n2) throws IOException {
            int n3 = this.source.readInt();
            boolean bl2 = (Integer.MIN_VALUE & n3) != 0;
            handler.priority(n2, n3 & Integer.MAX_VALUE, (this.source.readByte() & 255) + 1, bl2);
        }

        private void readPriority(FrameReader.Handler handler, short s2, byte by2, int n2) throws IOException {
            if (s2 != 5) {
                throw Http20Draft12.ioException("TYPE_PRIORITY length: %d != 5", new Object[]{s2});
            }
            if (n2 == 0) {
                throw Http20Draft12.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            }
            this.readPriority(handler, n2);
        }

        private void readPushPromise(FrameReader.Handler handler, short s2, byte by2, int n2) throws IOException {
            if (n2 == 0) {
                throw Http20Draft12.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            }
            short s3 = Http20Draft12.readPadding(this.source, by2);
            handler.pushPromise(n2, this.source.readInt() & Integer.MAX_VALUE, this.readHeaderBlock((short)(s2 - 4), s3, by2, n2));
        }

        private void readRstStream(FrameReader.Handler handler, short s2, byte by2, int n2) throws IOException {
            if (s2 != 4) {
                throw Http20Draft12.ioException("TYPE_RST_STREAM length: %d != 4", new Object[]{s2});
            }
            if (n2 == 0) {
                throw Http20Draft12.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            }
            by2 = (byte)this.source.readInt();
            ErrorCode errorCode = ErrorCode.fromHttp2(by2);
            if (errorCode == null) {
                throw Http20Draft12.ioException("TYPE_RST_STREAM unexpected error code: %d", new Object[]{by2});
            }
            handler.rstStream(n2, errorCode);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private void readSettings(FrameReader.Handler var1_1, short var2_2, byte var3_3, int var4_4) throws IOException {
            if (var4_4 != 0) {
                throw Http20Draft12.access$200("TYPE_SETTINGS streamId != 0", new Object[0]);
            }
            if ((var3_3 & 1) != 0) {
                if (var2_2 != 0) {
                    throw Http20Draft12.access$200("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                }
                var1_1.ackSettings();
                return;
            }
            if (var2_2 % 5 != 0) {
                throw Http20Draft12.access$200("TYPE_SETTINGS length %% 5 != 0: %s", new Object[]{var2_2});
            }
            var7_5 = new Settings();
            var4_4 = 0;
            do {
                if (var4_4 >= var2_2) {
                    var1_1.settings(false, var7_5);
                    if (var7_5.getHeaderTableSize() < 0) return;
                    this.hpackReader.maxHeaderTableByteCountSetting(var7_5.getHeaderTableSize());
                    return;
                }
                var5_6 = this.source.readByte();
                var6_7 = this.source.readInt();
                var3_3 = var5_6;
                switch (var5_6) {
                    default: {
                        throw Http20Draft12.access$200("PROTOCOL_ERROR invalid settings id: %s", new Object[]{var5_6});
                    }
                    case 2: {
                        var3_3 = var5_6;
                        if (var6_7 != 0) {
                            var3_3 = var5_6;
                            if (var6_7 != 1) {
                                throw Http20Draft12.access$200("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            }
                        }
                        ** GOTO lbl33
                    }
                    case 3: {
                        var3_3 = (byte)4;
                    }
lbl33: // 3 sources:
                    case 1: 
                    case 5: {
                        break;
                    }
                    case 4: {
                        var3_3 = (byte)7;
                        if (var6_7 >= 0) break;
                        throw Http20Draft12.access$200("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                    }
                }
                var7_5.set(var3_3, 0, var6_7);
                var4_4 += 5;
            } while (true);
        }

        private void readWindowUpdate(FrameReader.Handler handler, short s2, byte by2, int n2) throws IOException {
            if (s2 != 4) {
                throw Http20Draft12.ioException("TYPE_WINDOW_UPDATE length !=4: %s", new Object[]{s2});
            }
            long l2 = (long)this.source.readInt() & Integer.MAX_VALUE;
            if (l2 == 0) {
                throw Http20Draft12.ioException("windowSizeIncrement was 0", new Object[]{l2});
            }
            handler.windowUpdate(n2, l2);
        }

        @Override
        public void close() throws IOException {
            this.source.close();
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public boolean nextFrame(FrameReader.Handler handler) throws IOException {
            boolean bl2 = true;
            int n2 = this.source.readInt();
            int n3 = this.source.readInt();
            {
                catch (IOException iOException) {
                    return false;
                }
            }
            short s2 = (short)((1073676288 & n2) >> 16);
            byte by2 = (byte)((65280 & n2) >> 8);
            byte by3 = (byte)(n2 & 255);
            n2 = n3 & Integer.MAX_VALUE;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(FrameLogger.formatHeader(true, n2, s2, by2, by3));
            }
            switch (by2) {
                default: {
                    throw Http20Draft12.ioException("PROTOCOL_ERROR: unknown frame type %s", new Object[]{Byte.valueOf(by2)});
                }
                case 0: {
                    this.readData(handler, s2, by3, n2);
                    return true;
                }
                case 1: {
                    this.readHeaders(handler, s2, by3, n2);
                    return true;
                }
                case 2: {
                    this.readPriority(handler, s2, by3, n2);
                    return true;
                }
                case 3: {
                    this.readRstStream(handler, s2, by3, n2);
                    return true;
                }
                case 4: {
                    this.readSettings(handler, s2, by3, n2);
                    return true;
                }
                case 5: {
                    this.readPushPromise(handler, s2, by3, n2);
                    return true;
                }
                case 6: {
                    this.readPing(handler, s2, by3, n2);
                    return true;
                }
                case 7: {
                    this.readGoAway(handler, s2, by3, n2);
                    return true;
                }
                case 8: {
                    this.readWindowUpdate(handler, s2, by3, n2);
                    return true;
                }
                case 10: {
                    this.readAlternateService(handler, s2, by3, n2);
                    return true;
                }
                case 11: 
            }
            if (s2 == 0) return bl2;
            throw Http20Draft12.ioException("TYPE_BLOCKED length != 0: %s", new Object[]{s2});
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void readConnectionPreface() throws IOException {
            if (this.client) {
                return;
            }
            ByteString byteString = this.source.readByteString(CONNECTION_PREFACE.size());
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("<< CONNECTION %s", byteString.hex()));
            }
            if (CONNECTION_PREFACE.equals(byteString)) return;
            throw Http20Draft12.ioException("Expected a connection header but was %s", new Object[]{byteString.utf8()});
        }
    }

    static final class Writer
    implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final Buffer hpackBuffer;
        private final HpackDraft07.Writer hpackWriter;
        private final BufferedSink sink;

        Writer(BufferedSink bufferedSink, boolean bl2) {
            this.sink = bufferedSink;
            this.client = bl2;
            this.hpackBuffer = new Buffer();
            this.hpackWriter = new HpackDraft07.Writer(this.hpackBuffer);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void writeContinuationFrames(int n2, long l2) throws IOException {
            while (l2 > 0) {
                int n3;
                byte by2 = (l2 -= (long)(n3 = (int)Math.min(16383, l2))) == 0 ? 4 : 0;
                this.frameHeader(n2, n3, 9, by2);
                this.sink.write(this.hpackBuffer, n3);
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void ackSettings() throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.frameHeader(0, 0, 4, 1);
                this.sink.flush();
                return;
            }
        }

        @Override
        public void close() throws IOException {
            synchronized (this) {
                this.closed = true;
                this.sink.close();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void connectionPreface() throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                boolean bl2 = this.client;
                if (bl2) {
                    if (logger.isLoggable(Level.FINE)) {
                        logger.fine(String.format(">> CONNECTION %s", CONNECTION_PREFACE.hex()));
                    }
                    this.sink.write(CONNECTION_PREFACE.toByteArray());
                    this.sink.flush();
                }
                return;
            }
        }

        @Override
        public void data(boolean bl2, int n2, Buffer buffer) throws IOException {
            synchronized (this) {
                this.data(bl2, n2, buffer, (int)buffer.size());
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void data(boolean bl2, int n2, Buffer buffer, int n3) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                byte by2 = 0;
                if (bl2) {
                    by2 = 1;
                }
                this.dataFrame(n2, by2, buffer, n3);
                return;
            }
        }

        void dataFrame(int n2, byte by2, Buffer buffer, int n3) throws IOException {
            this.frameHeader(n2, n3, 0, by2);
            if (n3 > 0) {
                this.sink.write(buffer, n3);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void flush() throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.sink.flush();
                return;
            }
        }

        void frameHeader(int n2, int n3, byte by2, byte by3) throws IOException {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(FrameLogger.formatHeader(false, n2, n3, by2, by3));
            }
            if (n3 > 16383) {
                throw Http20Draft12.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", new Object[]{16383, n3});
            }
            if ((Integer.MIN_VALUE & n2) != 0) {
                throw Http20Draft12.illegalArgument("reserved bit set: %s", new Object[]{n2});
            }
            this.sink.writeInt((n3 & 16383) << 16 | (by2 & 255) << 8 | by3 & 255);
            this.sink.writeInt(Integer.MAX_VALUE & n2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void goAway(int n2, ErrorCode errorCode, byte[] arrby) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (errorCode.httpCode == -1) {
                    throw Http20Draft12.illegalArgument("errorCode.httpCode == -1", new Object[0]);
                }
                this.frameHeader(0, arrby.length + 8, 7, 0);
                this.sink.writeInt(n2);
                this.sink.writeInt(errorCode.httpCode);
                if (arrby.length > 0) {
                    this.sink.write(arrby);
                }
                this.sink.flush();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void headers(int n2, List<Header> list) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.headers(false, n2, list);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        void headers(boolean bl2, int n2, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (this.hpackBuffer.size() != 0) {
                throw new IllegalStateException();
            }
            this.hpackWriter.writeHeaders(list);
            long l2 = this.hpackBuffer.size();
            int n3 = (int)Math.min(16383, l2);
            byte by2 = l2 == (long)n3 ? 4 : 0;
            byte by3 = by2;
            if (bl2) {
                by3 = (byte)(by2 | 1);
            }
            this.frameHeader(n2, n3, 1, by3);
            this.sink.write(this.hpackBuffer, n3);
            if (l2 > (long)n3) {
                this.writeContinuationFrames(n2, l2 - (long)n3);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void ping(boolean bl2, int n2, int n3) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                byte by2 = bl2 ? 1 : 0;
                this.frameHeader(0, 8, 6, by2);
                this.sink.writeInt(n2);
                this.sink.writeInt(n3);
                this.sink.flush();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void pushPromise(int n2, int n3, List<Header> list) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (this.hpackBuffer.size() != 0) {
                    throw new IllegalStateException();
                }
                this.hpackWriter.writeHeaders(list);
                long l2 = this.hpackBuffer.size();
                int n4 = (int)Math.min(16379, l2);
                byte by2 = l2 == (long)n4 ? 4 : 0;
                this.frameHeader(n2, n4 + 4, 5, by2);
                this.sink.writeInt(Integer.MAX_VALUE & n3);
                this.sink.write(this.hpackBuffer, n4);
                if (l2 > (long)n4) {
                    this.writeContinuationFrames(n2, l2 - (long)n4);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void rstStream(int n2, ErrorCode errorCode) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (errorCode.spdyRstCode == -1) {
                    throw new IllegalArgumentException();
                }
                this.frameHeader(n2, 4, 3, 0);
                this.sink.writeInt(errorCode.httpCode);
                this.sink.flush();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void settings(Settings settings) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.frameHeader(0, settings.size() * 5, 4, 0);
                int n2 = 0;
                do {
                    if (n2 >= 10) {
                        this.sink.flush();
                        return;
                    }
                    if (settings.isSet(n2)) {
                        int n3;
                        int n4 = n2;
                        if (n4 == 4) {
                            n3 = 3;
                        } else {
                            n3 = n4;
                            if (n4 == 7) {
                                n3 = 4;
                            }
                        }
                        this.sink.writeByte(n3);
                        this.sink.writeInt(settings.get(n2));
                    }
                    ++n2;
                } while (true);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void synReply(boolean bl2, int n2, List<Header> list) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.headers(bl2, n2, list);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void synStream(boolean bl2, boolean bl3, int n2, int n3, List<Header> list) throws IOException {
            synchronized (this) {
                if (bl3) {
                    throw new UnsupportedOperationException();
                }
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.headers(bl2, n2, list);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void windowUpdate(int n2, long l2) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (l2 != 0 && l2 <= Integer.MAX_VALUE) {
                    this.frameHeader(n2, 4, 8, 0);
                    this.sink.writeInt((int)l2);
                    this.sink.flush();
                    return;
                }
                throw Http20Draft12.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", new Object[]{l2});
            }
        }
    }

}

