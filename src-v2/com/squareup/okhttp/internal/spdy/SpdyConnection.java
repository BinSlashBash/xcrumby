/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.FrameReader;
import com.squareup.okhttp.internal.spdy.FrameWriter;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.HeadersMode;
import com.squareup.okhttp.internal.spdy.Http20Draft12;
import com.squareup.okhttp.internal.spdy.IncomingStreamHandler;
import com.squareup.okhttp.internal.spdy.Ping;
import com.squareup.okhttp.internal.spdy.PushObserver;
import com.squareup.okhttp.internal.spdy.Settings;
import com.squareup.okhttp.internal.spdy.Spdy3;
import com.squareup.okhttp.internal.spdy.SpdyStream;
import com.squareup.okhttp.internal.spdy.Variant;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public final class SpdyConnection
implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    private static final ExecutorService executor;
    long bytesLeftInWriteWindow;
    final boolean client;
    private final Set<Integer> currentPushRequests;
    final FrameWriter frameWriter;
    private final IncomingStreamHandler handler;
    private final String hostName;
    private long idleStartTimeNs;
    private int lastGoodStreamId;
    final long maxFrameSize;
    private int nextPingId;
    private int nextStreamId;
    final Settings okHttpSettings;
    final Settings peerSettings;
    private Map<Integer, Ping> pings;
    final Protocol protocol;
    private final ExecutorService pushExecutor;
    private final PushObserver pushObserver;
    final Reader readerRunnable;
    private boolean receivedInitialPeerSettings;
    private boolean shutdown;
    final Socket socket;
    private final Map<Integer, SpdyStream> streams;
    long unacknowledgedBytesRead;
    final Variant variant;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !SpdyConnection.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
        executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp SpdyConnection", true));
    }

    /*
     * Enabled aggressive block sorting
     */
    private SpdyConnection(Builder builder) throws IOException {
        int n2 = 2;
        this.streams = new HashMap<Integer, SpdyStream>();
        this.idleStartTimeNs = System.nanoTime();
        this.unacknowledgedBytesRead = 0;
        this.okHttpSettings = new Settings();
        this.peerSettings = new Settings();
        this.receivedInitialPeerSettings = false;
        this.currentPushRequests = new LinkedHashSet<Integer>();
        this.protocol = builder.protocol;
        this.pushObserver = builder.pushObserver;
        this.client = builder.client;
        this.handler = builder.handler;
        int n3 = builder.client ? 1 : 2;
        this.nextStreamId = n3;
        if (builder.client && this.protocol == Protocol.HTTP_2) {
            this.nextStreamId += 2;
        }
        n3 = n2;
        if (builder.client) {
            n3 = 1;
        }
        this.nextPingId = n3;
        if (builder.client) {
            this.okHttpSettings.set(7, 0, 16777216);
        }
        this.hostName = builder.hostName;
        if (this.protocol == Protocol.HTTP_2) {
            this.variant = new Http20Draft12();
            this.pushExecutor = new ThreadPoolExecutor(0, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory(String.format("OkHttp %s Push Observer", this.hostName), true));
        } else {
            if (this.protocol != Protocol.SPDY_3) {
                throw new AssertionError((Object)this.protocol);
            }
            this.variant = new Spdy3();
            this.pushExecutor = null;
        }
        this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize(65536);
        this.socket = builder.socket;
        this.frameWriter = this.variant.newWriter(Okio.buffer(Okio.sink(builder.socket)), this.client);
        this.maxFrameSize = this.variant.maxFrameSize();
        this.readerRunnable = new Reader();
        new Thread(this.readerRunnable).start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void close(ErrorCode object, ErrorCode errorCode) throws IOException {
        block21 : {
            int n2;
            int n3;
            Object object2;
            if (!$assertionsDisabled && Thread.holdsLock(this)) {
                throw new AssertionError();
            }
            object2 = null;
            try {
                this.shutdown((ErrorCode)((Object)object));
                object = object2;
            }
            catch (IOException var1_2) {}
            SpdyStream[] arrspdyStream = null;
            Ping[] arrping = null;
            synchronized (this) {
                if (!this.streams.isEmpty()) {
                    arrspdyStream = this.streams.values().toArray(new SpdyStream[this.streams.size()]);
                    this.streams.clear();
                    this.setIdle(false);
                }
                if (this.pings != null) {
                    arrping = this.pings.values().toArray(new Ping[this.pings.size()]);
                    this.pings = null;
                }
            }
            object2 = object;
            if (arrspdyStream != null) {
                n3 = arrspdyStream.length;
                n2 = 0;
                do {
                    block20 : {
                        object2 = object;
                        if (n2 >= n3) break;
                        object2 = arrspdyStream[n2];
                        try {
                            object2.close(errorCode);
                            object2 = object;
                        }
                        catch (IOException var8_11) {
                            object2 = object;
                            if (object == null) break block20;
                            object2 = var8_11;
                        }
                    }
                    ++n2;
                    object = object2;
                } while (true);
            }
            if (arrping != null) {
                n3 = arrping.length;
                for (n2 = 0; n2 < n3; ++n2) {
                    arrping[n2].cancel();
                }
            }
            try {
                this.frameWriter.close();
                object = object2;
            }
            catch (IOException var2_5) {
                object = object2;
                if (object2 != null) break block21;
                object = var2_5;
            }
        }
        try {
            this.socket.close();
        }
        catch (IOException var1_3) {}
        if (object != null) {
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private SpdyStream newStream(int n2, List<Header> list, boolean bl2, boolean bl3) throws IOException {
        boolean bl4 = true;
        boolean bl5 = !bl2;
        bl3 = !bl3 ? bl4 : false;
        FrameWriter frameWriter = this.frameWriter;
        // MONITORENTER : frameWriter
        // MONITORENTER : this
        if (this.shutdown) {
            throw new IOException("shutdown");
        }
        int n3 = this.nextStreamId;
        this.nextStreamId += 2;
        SpdyStream spdyStream = new SpdyStream(n3, this, bl5, bl3, list);
        if (spdyStream.isOpen()) {
            this.streams.put(n3, spdyStream);
            this.setIdle(false);
        }
        // MONITOREXIT : this
        if (n2 == 0) {
            this.frameWriter.synStream(bl5, bl3, n3, n2, list);
        } else {
            if (this.client) {
                throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
            }
            this.frameWriter.pushPromise(n2, n3, list);
        }
        // MONITOREXIT : frameWriter
        if (bl2) return spdyStream;
        this.frameWriter.flush();
        return spdyStream;
    }

    private void pushDataLater(final int n2, BufferedSource bufferedSource, final int n3, final boolean bl2) throws IOException {
        final Buffer buffer = new Buffer();
        bufferedSource.require(n3);
        bufferedSource.read(buffer, n3);
        if (buffer.size() != (long)n3) {
            throw new IOException("" + buffer.size() + " != " + n3);
        }
        this.pushExecutor.submit(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.hostName, n2}){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void execute() {
                try {
                    boolean bl22 = SpdyConnection.this.pushObserver.onData(n2, buffer, n3, bl2);
                    if (bl22) {
                        SpdyConnection.this.frameWriter.rstStream(n2, ErrorCode.CANCEL);
                    }
                    if (!bl22) {
                        if (!bl2) return;
                    }
                    SpdyConnection spdyConnection = SpdyConnection.this;
                    // MONITORENTER : spdyConnection
                    SpdyConnection.this.currentPushRequests.remove(n2);
                }
                catch (IOException var2_3) {
                    // empty catch block
                }
                // MONITOREXIT : spdyConnection
                return;
            }
        });
    }

    private void pushHeadersLater(final int n2, final List<Header> list, final boolean bl2) {
        this.pushExecutor.submit(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.hostName, n2}){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void execute() {
                var1_1 = SpdyConnection.access$2500(SpdyConnection.this).onHeaders(n2, list, bl2);
                if (!var1_1) ** GOTO lbl5
                try {
                    SpdyConnection.this.frameWriter.rstStream(n2, ErrorCode.CANCEL);
lbl5: // 2 sources:
                    if (!var1_1) {
                        if (bl2 == false) return;
                    }
                    var2_2 = SpdyConnection.this;
                    // MONITORENTER : var2_2
                    SpdyConnection.access$2600(SpdyConnection.this).remove(n2);
                }
                catch (IOException var2_3) {
                    // empty catch block
                }
                // MONITOREXIT : var2_2
                return;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void pushRequestLater(final int n2, final List<Header> list) {
        synchronized (this) {
            if (this.currentPushRequests.contains(n2)) {
                this.writeSynResetLater(n2, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.currentPushRequests.add(n2);
        }
        this.pushExecutor.submit(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.hostName, n2}){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void execute() {
                if (!SpdyConnection.this.pushObserver.onRequest(n2, list)) return;
                try {
                    SpdyConnection.this.frameWriter.rstStream(n2, ErrorCode.CANCEL);
                    SpdyConnection spdyConnection = SpdyConnection.this;
                    // MONITORENTER : spdyConnection
                    SpdyConnection.this.currentPushRequests.remove(n2);
                }
                catch (IOException var1_2) {
                    // empty catch block
                }
                // MONITOREXIT : spdyConnection
                return;
            }
        });
    }

    private void pushResetLater(final int n2, final ErrorCode errorCode) {
        this.pushExecutor.submit(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.hostName, n2}){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void execute() {
                SpdyConnection.this.pushObserver.onReset(n2, errorCode);
                SpdyConnection spdyConnection = SpdyConnection.this;
                synchronized (spdyConnection) {
                    SpdyConnection.this.currentPushRequests.remove(n2);
                    return;
                }
            }
        });
    }

    private boolean pushedStream(int n2) {
        if (this.protocol == Protocol.HTTP_2 && n2 != 0 && (n2 & 1) == 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Ping removePing(int n2) {
        synchronized (this) {
            if (this.pings == null) return null;
            return this.pings.remove(n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setIdle(boolean bl2) {
        synchronized (this) {
            long l2 = bl2 ? System.nanoTime() : Long.MAX_VALUE;
            this.idleStartTimeNs = l2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writePing(boolean bl2, int n2, int n3, Ping ping) throws IOException {
        FrameWriter frameWriter = this.frameWriter;
        synchronized (frameWriter) {
            if (ping != null) {
                ping.send();
            }
            this.frameWriter.ping(bl2, n2, n3);
            return;
        }
    }

    private void writePingLater(final boolean bl2, final int n2, final int n3, final Ping ping) {
        executor.submit(new NamedRunnable("OkHttp %s ping %08x%08x", new Object[]{this.hostName, n2, n3}){

            @Override
            public void execute() {
                try {
                    SpdyConnection.this.writePing(bl2, n2, n3, ping);
                    return;
                }
                catch (IOException var1_1) {
                    return;
                }
            }
        });
    }

    void addBytesToWriteWindow(long l2) {
        this.bytesLeftInWriteWindow += l2;
        if (l2 > 0) {
            this.notifyAll();
        }
    }

    @Override
    public void close() throws IOException {
        this.close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    public void flush() throws IOException {
        this.frameWriter.flush();
    }

    public long getIdleStartTimeNs() {
        synchronized (this) {
            long l2 = this.idleStartTimeNs;
            return l2;
        }
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    SpdyStream getStream(int n2) {
        synchronized (this) {
            SpdyStream spdyStream = this.streams.get(n2);
            return spdyStream;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isIdle() {
        synchronized (this) {
            long l2 = this.idleStartTimeNs;
            if (l2 == Long.MAX_VALUE) return false;
            return true;
        }
    }

    public SpdyStream newStream(List<Header> list, boolean bl2, boolean bl3) throws IOException {
        return this.newStream(0, list, bl2, bl3);
    }

    public int openStreamCount() {
        synchronized (this) {
            int n2 = this.streams.size();
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Ping ping() throws IOException {
        int n2;
        Ping ping = new Ping();
        synchronized (this) {
            if (this.shutdown) {
                throw new IOException("shutdown");
            }
            n2 = this.nextPingId;
            this.nextPingId += 2;
            if (this.pings == null) {
                this.pings = new HashMap<Integer, Ping>();
            }
            this.pings.put(n2, ping);
        }
        this.writePing(false, n2, 1330343787, ping);
        return ping;
    }

    public SpdyStream pushStream(int n2, List<Header> list, boolean bl2) throws IOException {
        if (this.client) {
            throw new IllegalStateException("Client cannot push requests.");
        }
        if (this.protocol != Protocol.HTTP_2) {
            throw new IllegalStateException("protocol != HTTP_2");
        }
        return this.newStream(n2, list, bl2, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    SpdyStream removeStream(int n2) {
        synchronized (this) {
            SpdyStream spdyStream = this.streams.remove(n2);
            if (spdyStream != null && this.streams.isEmpty()) {
                this.setIdle(true);
            }
            return spdyStream;
        }
    }

    public void sendConnectionPreface() throws IOException {
        this.frameWriter.connectionPreface();
        this.frameWriter.settings(this.okHttpSettings);
        int n2 = this.okHttpSettings.getInitialWindowSize(65536);
        if (n2 != 65536) {
            this.frameWriter.windowUpdate(0, n2 - 65536);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void shutdown(ErrorCode errorCode) throws IOException {
        FrameWriter frameWriter = this.frameWriter;
        synchronized (frameWriter) {
            int n2;
            synchronized (this) {
                if (this.shutdown) {
                    return;
                }
                this.shutdown = true;
                n2 = this.lastGoodStreamId;
            }
            this.frameWriter.goAway(n2, errorCode, Util.EMPTY_BYTE_ARRAY);
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void writeData(int var1_1, boolean var2_2, Buffer var3_3, long var4_5) throws IOException {
        var8_6 = var4_5;
        if (var4_5 != 0) ** GOTO lbl11
        this.frameWriter.data(var2_2, var1_1, var3_3, 0);
        return;
        {
            var6_7 = (int)Math.min(Math.min(var8_6, this.bytesLeftInWriteWindow), this.maxFrameSize);
            this.bytesLeftInWriteWindow -= (long)var6_7;
            // MONITOREXIT : this
            var10_9 = this.frameWriter;
            var7_8 = var2_2 != false && (var8_6 -= (long)var6_7) == 0;
            var10_9.data(var7_8, var1_1, var3_3, var6_7);
lbl11: // 2 sources:
            if (var8_6 <= 0) return;
            // MONITORENTER : this
            do {
                if (this.bytesLeftInWriteWindow > 0) continue block5;
                this.wait();
            } while (true);
        }
        catch (InterruptedException var3_4) {
            throw new InterruptedIOException();
        }
    }

    void writeSynReply(int n2, boolean bl2, List<Header> list) throws IOException {
        this.frameWriter.synReply(bl2, n2, list);
    }

    void writeSynReset(int n2, ErrorCode errorCode) throws IOException {
        this.frameWriter.rstStream(n2, errorCode);
    }

    void writeSynResetLater(final int n2, final ErrorCode errorCode) {
        executor.submit(new NamedRunnable("OkHttp %s stream %d", new Object[]{this.hostName, n2}){

            @Override
            public void execute() {
                try {
                    SpdyConnection.this.writeSynReset(n2, errorCode);
                    return;
                }
                catch (IOException var1_1) {
                    return;
                }
            }
        });
    }

    void writeWindowUpdateLater(final int n2, final long l2) {
        executor.submit(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.hostName, n2}){

            @Override
            public void execute() {
                try {
                    SpdyConnection.this.frameWriter.windowUpdate(n2, l2);
                    return;
                }
                catch (IOException var1_1) {
                    return;
                }
            }
        });
    }

    public static class Builder {
        private boolean client;
        private IncomingStreamHandler handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
        private String hostName;
        private Protocol protocol = Protocol.SPDY_3;
        private PushObserver pushObserver = PushObserver.CANCEL;
        private Socket socket;

        public Builder(String string2, boolean bl2, Socket socket) throws IOException {
            this.hostName = string2;
            this.client = bl2;
            this.socket = socket;
        }

        public Builder(boolean bl2, Socket socket) throws IOException {
            this(((InetSocketAddress)socket.getRemoteSocketAddress()).getHostName(), bl2, socket);
        }

        public SpdyConnection build() throws IOException {
            return new SpdyConnection(this);
        }

        public Builder handler(IncomingStreamHandler incomingStreamHandler) {
            this.handler = incomingStreamHandler;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.pushObserver = pushObserver;
            return this;
        }
    }

    class Reader
    extends NamedRunnable
    implements FrameReader.Handler {
        FrameReader frameReader;

        private Reader() {
            super("OkHttp %s", SpdyConnection.this.hostName);
        }

        private void ackSettingsLater() {
            executor.submit(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{SpdyConnection.this.hostName}){

                @Override
                public void execute() {
                    try {
                        SpdyConnection.this.frameWriter.ackSettings();
                        return;
                    }
                    catch (IOException var1_1) {
                        return;
                    }
                }
            });
        }

        @Override
        public void ackSettings() {
        }

        @Override
        public void alternateService(int n2, String string2, ByteString byteString, String string3, int n3, long l2) {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void data(boolean bl2, int n2, BufferedSource bufferedSource, int n3) throws IOException {
            if (SpdyConnection.this.pushedStream(n2)) {
                SpdyConnection.this.pushDataLater(n2, bufferedSource, n3, bl2);
                return;
            } else {
                SpdyStream spdyStream = SpdyConnection.this.getStream(n2);
                if (spdyStream == null) {
                    SpdyConnection.this.writeSynResetLater(n2, ErrorCode.INVALID_STREAM);
                    bufferedSource.skip(n3);
                    return;
                }
                spdyStream.receiveData(bufferedSource, n3);
                if (!bl2) return;
                {
                    spdyStream.receiveFin();
                    return;
                }
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        protected void execute() {
            block12 : {
                ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
                ErrorCode errorCode2 = ErrorCode.INTERNAL_ERROR;
                ErrorCode errorCode3 = errorCode;
                ErrorCode errorCode4 = errorCode;
                this.frameReader = SpdyConnection.this.variant.newReader(Okio.buffer(Okio.source(SpdyConnection.this.socket)), SpdyConnection.this.client);
                errorCode3 = errorCode;
                errorCode4 = errorCode;
                if (!SpdyConnection.this.client) {
                    errorCode3 = errorCode;
                    errorCode4 = errorCode;
                    this.frameReader.readConnectionPreface();
                }
                do {
                    errorCode3 = errorCode;
                    errorCode4 = errorCode;
                } while (this.frameReader.nextFrame(this));
                errorCode3 = errorCode;
                errorCode4 = errorCode;
                errorCode3 = errorCode = ErrorCode.NO_ERROR;
                errorCode4 = errorCode;
                ErrorCode errorCode5 = ErrorCode.CANCEL;
                try {
                    SpdyConnection.this.close(errorCode, errorCode5);
                    break block12;
                }
                catch (IOException var1_9) {}
                catch (IOException iOException) {
                    errorCode4 = errorCode3;
                    try {
                        errorCode4 = errorCode3 = ErrorCode.PROTOCOL_ERROR;
                        errorCode = ErrorCode.PROTOCOL_ERROR;
                    }
                    catch (Throwable var2_4) {
                        try {
                            SpdyConnection.this.close(errorCode4, errorCode2);
                        }
                        catch (IOException var1_7) {}
                        Util.closeQuietly(this.frameReader);
                        throw var2_4;
                    }
                    try {
                        SpdyConnection.this.close(errorCode3, errorCode);
                    }
                    catch (IOException var1_8) {}
                    Util.closeQuietly(this.frameReader);
                    return;
                }
            }
            Util.closeQuietly(this.frameReader);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void goAway(int n2, ErrorCode object, ByteString object2) {
            if (object2.size() > 0) {
                // empty if block
            }
            object = SpdyConnection.this;
            synchronized (object) {
                SpdyConnection.this.shutdown = true;
                object2 = SpdyConnection.this.streams.entrySet().iterator();
                while (object2.hasNext()) {
                    Map.Entry entry = (Map.Entry)object2.next();
                    if ((Integer)entry.getKey() <= n2 || !((SpdyStream)entry.getValue()).isLocallyInitiated()) continue;
                    ((SpdyStream)entry.getValue()).receiveRstStream(ErrorCode.REFUSED_STREAM);
                    object2.remove();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void headers(boolean bl2, boolean bl3, int n2, int n3, List<Header> object, HeadersMode headersMode) {
            if (SpdyConnection.this.pushedStream(n2)) {
                SpdyConnection.this.pushHeadersLater(n2, (List)object, bl3);
                return;
            }
            SpdyConnection spdyConnection = SpdyConnection.this;
            // MONITORENTER : spdyConnection
            if (SpdyConnection.this.shutdown) {
                // MONITOREXIT : spdyConnection
                return;
            }
            SpdyStream spdyStream = SpdyConnection.this.getStream(n2);
            if (spdyStream == null) {
                if (headersMode.failIfStreamAbsent()) {
                    SpdyConnection.this.writeSynResetLater(n2, ErrorCode.INVALID_STREAM);
                    // MONITOREXIT : spdyConnection
                    return;
                }
                if (n2 <= SpdyConnection.this.lastGoodStreamId) {
                    // MONITOREXIT : spdyConnection
                    return;
                }
                if (n2 % 2 == SpdyConnection.this.nextStreamId % 2) {
                    // MONITOREXIT : spdyConnection
                    return;
                }
                object = new SpdyStream(n2, SpdyConnection.this, bl2, bl3, (List<Header>)object);
                SpdyConnection.this.lastGoodStreamId = n2;
                SpdyConnection.this.streams.put(n2, object);
                executor.submit(new NamedRunnable("OkHttp %s stream %d", new Object[]{SpdyConnection.this.hostName, n2}, (SpdyStream)object){
                    final /* synthetic */ SpdyStream val$newStream;

                    @Override
                    public void execute() {
                        try {
                            SpdyConnection.this.handler.receive(this.val$newStream);
                            return;
                        }
                        catch (IOException var1_1) {
                            throw new RuntimeException(var1_1);
                        }
                    }
                });
                // MONITOREXIT : spdyConnection
                return;
            }
            // MONITOREXIT : spdyConnection
            if (headersMode.failIfStreamPresent()) {
                spdyStream.closeLater(ErrorCode.PROTOCOL_ERROR);
                SpdyConnection.this.removeStream(n2);
                return;
            }
            spdyStream.receiveHeaders((List<Header>)object, headersMode);
            if (!bl3) return;
            spdyStream.receiveFin();
        }

        @Override
        public void ping(boolean bl2, int n2, int n3) {
            if (bl2) {
                Ping ping = SpdyConnection.this.removePing(n2);
                if (ping != null) {
                    ping.receive();
                }
                return;
            }
            SpdyConnection.this.writePingLater(true, n2, n3, null);
        }

        @Override
        public void priority(int n2, int n3, int n4, boolean bl2) {
        }

        @Override
        public void pushPromise(int n2, int n3, List<Header> list) {
            SpdyConnection.this.pushRequestLater(n3, list);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void rstStream(int n2, ErrorCode errorCode) {
            if (SpdyConnection.this.pushedStream(n2)) {
                SpdyConnection.this.pushResetLater(n2, errorCode);
                return;
            } else {
                SpdyStream spdyStream = SpdyConnection.this.removeStream(n2);
                if (spdyStream == null) return;
                {
                    spdyStream.receiveRstStream(errorCode);
                    return;
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void settings(boolean bl2, Settings arrspdyStream) {
            long l2 = 0;
            Iterator iterator = null;
            SpdyConnection spdyConnection = SpdyConnection.this;
            // MONITORENTER : spdyConnection
            int n2 = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
            if (bl2) {
                SpdyConnection.this.peerSettings.clear();
            }
            SpdyConnection.this.peerSettings.merge((Settings)arrspdyStream);
            if (SpdyConnection.this.getProtocol() == Protocol.HTTP_2) {
                this.ackSettingsLater();
            }
            int n3 = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
            long l3 = l2;
            arrspdyStream = iterator;
            if (n3 != -1) {
                l3 = l2;
                arrspdyStream = iterator;
                if (n3 != n2) {
                    l2 = n3 - n2;
                    if (!SpdyConnection.this.receivedInitialPeerSettings) {
                        SpdyConnection.this.addBytesToWriteWindow(l2);
                        SpdyConnection.this.receivedInitialPeerSettings = true;
                    }
                    l3 = l2;
                    arrspdyStream = iterator;
                    if (!SpdyConnection.this.streams.isEmpty()) {
                        arrspdyStream = SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
                        l3 = l2;
                    }
                }
            }
            // MONITOREXIT : spdyConnection
            if (arrspdyStream == null) return;
            if (l3 == 0) return;
            iterator = SpdyConnection.this.streams.values().iterator();
            while (iterator.hasNext()) {
                arrspdyStream = (SpdyStream)iterator.next();
                // MONITORENTER : arrspdyStream
                arrspdyStream.addBytesToWriteWindow(l3);
                // MONITOREXIT : arrspdyStream
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void windowUpdate(int n2, long l2) {
            if (n2 == 0) {
                SpdyConnection spdyConnection = SpdyConnection.this;
                synchronized (spdyConnection) {
                    SpdyConnection spdyConnection2 = SpdyConnection.this;
                    spdyConnection2.bytesLeftInWriteWindow += l2;
                    SpdyConnection.this.notifyAll();
                    return;
                }
            }
            SpdyStream spdyStream = SpdyConnection.this.getStream(n2);
            if (spdyStream != null) {
                synchronized (spdyStream) {
                    spdyStream.addBytesToWriteWindow(l2);
                    return;
                }
            }
        }

    }

}

