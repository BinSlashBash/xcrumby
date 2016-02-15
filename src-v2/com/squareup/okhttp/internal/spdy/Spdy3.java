/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.FrameReader;
import com.squareup.okhttp.internal.spdy.FrameWriter;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.HeadersMode;
import com.squareup.okhttp.internal.spdy.NameValueBlockReader;
import com.squareup.okhttp.internal.spdy.Settings;
import com.squareup.okhttp.internal.spdy.Variant;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.Deflater;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.DeflaterSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class Spdy3
implements Variant {
    static final byte[] DICTIONARY;
    static final int FLAG_FIN = 1;
    static final int FLAG_UNIDIRECTIONAL = 2;
    static final int TYPE_DATA = 0;
    static final int TYPE_GOAWAY = 7;
    static final int TYPE_HEADERS = 8;
    static final int TYPE_PING = 6;
    static final int TYPE_RST_STREAM = 3;
    static final int TYPE_SETTINGS = 4;
    static final int TYPE_SYN_REPLY = 2;
    static final int TYPE_SYN_STREAM = 1;
    static final int TYPE_WINDOW_UPDATE = 9;
    static final int VERSION = 3;

    static {
        try {
            DICTIONARY = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(Util.UTF_8.name());
            return;
        }
        catch (UnsupportedEncodingException var0) {
            throw new AssertionError();
        }
    }

    @Override
    public Protocol getProtocol() {
        return Protocol.SPDY_3;
    }

    @Override
    public int maxFrameSize() {
        return 16383;
    }

    @Override
    public FrameReader newReader(BufferedSource bufferedSource, boolean bl2) {
        return new Reader(bufferedSource, bl2);
    }

    @Override
    public FrameWriter newWriter(BufferedSink bufferedSink, boolean bl2) {
        return new Writer(bufferedSink, bl2);
    }

    static final class Reader
    implements FrameReader {
        private final boolean client;
        private final NameValueBlockReader headerBlockReader;
        private final BufferedSource source;

        Reader(BufferedSource bufferedSource, boolean bl2) {
            this.source = bufferedSource;
            this.headerBlockReader = new NameValueBlockReader(this.source);
            this.client = bl2;
        }

        private static /* varargs */ IOException ioException(String string2, Object ... arrobject) throws IOException {
            throw new IOException(String.format(string2, arrobject));
        }

        private void readGoAway(FrameReader.Handler handler, int n2, int n3) throws IOException {
            if (n3 != 8) {
                throw Reader.ioException("TYPE_GOAWAY length: %d != 8", n3);
            }
            n2 = this.source.readInt();
            n3 = this.source.readInt();
            ErrorCode errorCode = ErrorCode.fromSpdyGoAway(n3);
            if (errorCode == null) {
                throw Reader.ioException("TYPE_GOAWAY unexpected error code: %d", n3);
            }
            handler.goAway(n2 & Integer.MAX_VALUE, errorCode, ByteString.EMPTY);
        }

        private void readHeaders(FrameReader.Handler handler, int n2, int n3) throws IOException {
            handler.headers(false, false, this.source.readInt() & Integer.MAX_VALUE, -1, this.headerBlockReader.readNameValueBlock(n3 - 4), HeadersMode.SPDY_HEADERS);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void readPing(FrameReader.Handler handler, int n2, int n3) throws IOException {
            boolean bl2 = true;
            if (n3 != 4) {
                throw Reader.ioException("TYPE_PING length: %d != 4", n3);
            }
            n2 = this.source.readInt();
            boolean bl3 = this.client;
            boolean bl4 = (n2 & 1) == 1;
            bl4 = bl3 == bl4 ? bl2 : false;
            handler.ping(bl4, n2, 0);
        }

        private void readRstStream(FrameReader.Handler handler, int n2, int n3) throws IOException {
            if (n3 != 8) {
                throw Reader.ioException("TYPE_RST_STREAM length: %d != 8", n3);
            }
            n2 = this.source.readInt();
            n3 = this.source.readInt();
            ErrorCode errorCode = ErrorCode.fromSpdy3Rst(n3);
            if (errorCode == null) {
                throw Reader.ioException("TYPE_RST_STREAM unexpected error code: %d", n3);
            }
            handler.rstStream(n2 & Integer.MAX_VALUE, errorCode);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void readSettings(FrameReader.Handler handler, int n2, int n3) throws IOException {
            boolean bl2 = true;
            int n4 = this.source.readInt();
            if (n3 != n4 * 8 + 4) {
                throw Reader.ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", n3, n4);
            }
            Settings settings = new Settings();
            for (n3 = 0; n3 < n4; ++n3) {
                int n5 = this.source.readInt();
                settings.set(n5 & 16777215, (-16777216 & n5) >>> 24, this.source.readInt());
            }
            if ((n2 & 1) == 0) {
                bl2 = false;
            }
            handler.settings(bl2, settings);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void readSynReply(FrameReader.Handler handler, int n2, int n3) throws IOException {
            int n4 = this.source.readInt();
            List<Header> list = this.headerBlockReader.readNameValueBlock(n3 - 4);
            boolean bl2 = (n2 & 1) != 0;
            handler.headers(false, bl2, n4 & Integer.MAX_VALUE, -1, list, HeadersMode.SPDY_REPLY);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void readSynStream(FrameReader.Handler handler, int n2, int n3) throws IOException {
            boolean bl2 = true;
            int n4 = this.source.readInt();
            int n5 = this.source.readInt();
            this.source.readShort();
            List<Header> list = this.headerBlockReader.readNameValueBlock(n3 - 10);
            boolean bl3 = (n2 & 1) != 0;
            if ((n2 & 2) == 0) {
                bl2 = false;
            }
            handler.headers(bl2, bl3, n4 & Integer.MAX_VALUE, n5 & Integer.MAX_VALUE, list, HeadersMode.SPDY_SYN_STREAM);
        }

        private void readWindowUpdate(FrameReader.Handler handler, int n2, int n3) throws IOException {
            if (n3 != 8) {
                throw Reader.ioException("TYPE_WINDOW_UPDATE length: %d != 8", n3);
            }
            n2 = this.source.readInt();
            long l2 = this.source.readInt() & Integer.MAX_VALUE;
            if (l2 == 0) {
                throw Reader.ioException("windowSizeIncrement was 0", l2);
            }
            handler.windowUpdate(n2 & Integer.MAX_VALUE, l2);
        }

        @Override
        public void close() throws IOException {
            this.headerBlockReader.close();
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public boolean nextFrame(FrameReader.Handler var1_1) throws IOException {
            var6_3 = false;
            try {
                var3_4 = this.source.readInt();
                var4_5 = this.source.readInt();
                var2_6 = (Integer.MIN_VALUE & var3_4) != 0 ? 1 : 0;
            }
            catch (IOException var1_2) {
                return false;
            }
            var5_7 = (-16777216 & var4_5) >>> 24;
            var4_5 &= 16777215;
            if (var2_6 == 0) ** GOTO lbl43
            var2_6 = (2147418112 & var3_4) >>> 16;
            if (var2_6 != 3) {
                throw new ProtocolException("version != 3: " + var2_6);
            }
            switch (var3_4 & 65535) {
                default: {
                    this.source.skip(var4_5);
                    return true;
                }
                case 1: {
                    this.readSynStream(var1_1, var5_7, var4_5);
                    return true;
                }
                case 2: {
                    this.readSynReply(var1_1, var5_7, var4_5);
                    return true;
                }
                case 3: {
                    this.readRstStream(var1_1, var5_7, var4_5);
                    return true;
                }
                case 4: {
                    this.readSettings(var1_1, var5_7, var4_5);
                    return true;
                }
                case 6: {
                    this.readPing(var1_1, var5_7, var4_5);
                    return true;
                }
                case 7: {
                    this.readGoAway(var1_1, var5_7, var4_5);
                    return true;
                }
                case 8: {
                    this.readHeaders(var1_1, var5_7, var4_5);
                    return true;
                }
                case 9: 
            }
            this.readWindowUpdate(var1_1, var5_7, var4_5);
            return true;
lbl43: // 1 sources:
            if ((var5_7 & 1) != 0) {
                var6_3 = true;
            }
            var1_1.data(var6_3, var3_4 & Integer.MAX_VALUE, this.source, var4_5);
            return true;
        }

        @Override
        public void readConnectionPreface() {
        }
    }

    static final class Writer
    implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final Buffer headerBlockBuffer;
        private final BufferedSink headerBlockOut;
        private final BufferedSink sink;

        Writer(BufferedSink object, boolean bl2) {
            this.sink = object;
            this.client = bl2;
            object = new Deflater();
            object.setDictionary(Spdy3.DICTIONARY);
            this.headerBlockBuffer = new Buffer();
            this.headerBlockOut = Okio.buffer(new DeflaterSink((Sink)this.headerBlockBuffer, (Deflater)object));
        }

        private void writeNameValueBlockToBuffer(List<Header> list) throws IOException {
            if (this.headerBlockBuffer.size() != 0) {
                throw new IllegalStateException();
            }
            this.headerBlockOut.writeInt(list.size());
            int n2 = list.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                ByteString byteString = list.get((int)i2).name;
                this.headerBlockOut.writeInt(byteString.size());
                this.headerBlockOut.write(byteString);
                byteString = list.get((int)i2).value;
                this.headerBlockOut.writeInt(byteString.size());
                this.headerBlockOut.write(byteString);
            }
            this.headerBlockOut.flush();
        }

        @Override
        public void ackSettings() {
        }

        @Override
        public void close() throws IOException {
            synchronized (this) {
                this.closed = true;
                Util.closeAll(this.sink, this.headerBlockOut);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void connectionPreface() {
            // MONITORENTER : this
            // MONITOREXIT : this
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
                void var4_4;
                int n4 = bl2 ? 1 : 0;
                this.sendDataFrame(n2, n4, buffer, (int)var4_4);
                return;
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
                if (errorCode.spdyGoAwayCode == -1) {
                    throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
                }
                this.sink.writeInt(-2147287033);
                this.sink.writeInt(8);
                this.sink.writeInt(n2);
                this.sink.writeInt(errorCode.spdyGoAwayCode);
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
                this.writeNameValueBlockToBuffer(list);
                int n3 = (int)(this.headerBlockBuffer.size() + 4);
                this.sink.writeInt(-2147287032);
                this.sink.writeInt(16777215 & n3 | 0);
                this.sink.writeInt(Integer.MAX_VALUE & n2);
                this.sink.writeAll(this.headerBlockBuffer);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void ping(boolean bl2, int n2, int n3) throws IOException {
            boolean bl3 = true;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                boolean bl4 = this.client;
                boolean bl5 = (n2 & 1) == 1;
                bl5 = bl4 != bl5 ? bl3 : false;
                if (bl2 != bl5) {
                    throw new IllegalArgumentException("payload != reply");
                }
                this.sink.writeInt(-2147287034);
                this.sink.writeInt(4);
                this.sink.writeInt(n2);
                this.sink.flush();
                return;
            }
        }

        @Override
        public void pushPromise(int n2, int n3, List<Header> list) throws IOException {
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
                this.sink.writeInt(-2147287037);
                this.sink.writeInt(8);
                this.sink.writeInt(Integer.MAX_VALUE & n2);
                this.sink.writeInt(errorCode.spdyRstCode);
                this.sink.flush();
                return;
            }
        }

        void sendDataFrame(int n2, int n3, Buffer buffer, int n4) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if ((long)n4 > 0xFFFFFF) {
                throw new IllegalArgumentException("FRAME_TOO_LARGE max size is 16Mib: " + n4);
            }
            this.sink.writeInt(Integer.MAX_VALUE & n2);
            this.sink.writeInt((n3 & 255) << 24 | 16777215 & n4);
            if (n4 > 0) {
                this.sink.write(buffer, n4);
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
                int n2 = settings.size();
                this.sink.writeInt(-2147287036);
                this.sink.writeInt(n2 * 8 + 4 & 16777215 | 0);
                this.sink.writeInt(n2);
                n2 = 0;
                do {
                    if (n2 > 10) {
                        this.sink.flush();
                        return;
                    }
                    if (settings.isSet(n2)) {
                        int n3 = settings.flags(n2);
                        this.sink.writeInt((n3 & 255) << 24 | n2 & 16777215);
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
                this.writeNameValueBlockToBuffer(list);
                int n3 = bl2 ? 1 : 0;
                int n4 = (int)(this.headerBlockBuffer.size() + 4);
                this.sink.writeInt(-2147287038);
                this.sink.writeInt((n3 & 255) << 24 | 16777215 & n4);
                this.sink.writeInt(Integer.MAX_VALUE & n2);
                this.sink.writeAll(this.headerBlockBuffer);
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
        public void synStream(boolean bl2, boolean bl3, int n2, int n3, List<Header> list) throws IOException {
            int n4 = 0;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.writeNameValueBlockToBuffer(list);
                int n5 = (int)(10 + this.headerBlockBuffer.size());
                int n6 = bl2 ? 1 : 0;
                if (bl3) {
                    n4 = 2;
                }
                this.sink.writeInt(-2147287039);
                this.sink.writeInt(((n6 | n4) & 255) << 24 | 16777215 & n5);
                this.sink.writeInt(n2 & Integer.MAX_VALUE);
                this.sink.writeInt(n3 & Integer.MAX_VALUE);
                this.sink.writeShort(0);
                this.sink.writeAll(this.headerBlockBuffer);
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
        public void windowUpdate(int n2, long l2) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (l2 != 0 && l2 <= Integer.MAX_VALUE) {
                    this.sink.writeInt(-2147287031);
                    this.sink.writeInt(8);
                    this.sink.writeInt(n2);
                    this.sink.writeInt((int)l2);
                    this.sink.flush();
                    return;
                }
                throw new IllegalArgumentException("windowSizeIncrement must be between 1 and 0x7fffffff: " + l2);
            }
        }
    }

}

