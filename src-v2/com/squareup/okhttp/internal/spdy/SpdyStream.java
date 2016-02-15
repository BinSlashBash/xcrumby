/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.HeadersMode;
import com.squareup.okhttp.internal.spdy.Settings;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class SpdyStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    long bytesLeftInWriteWindow;
    private final SpdyConnection connection;
    private ErrorCode errorCode;
    private final int id;
    private final SpdyTimeout readTimeout;
    private long readTimeoutMillis = 0;
    private final List<Header> requestHeaders;
    private List<Header> responseHeaders;
    final SpdyDataSink sink;
    private final SpdyDataSource source;
    long unacknowledgedBytesRead = 0;
    private final SpdyTimeout writeTimeout;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !SpdyStream.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
    }

    SpdyStream(int n2, SpdyConnection spdyConnection, boolean bl2, boolean bl3, List<Header> list) {
        this.readTimeout = new SpdyTimeout();
        this.writeTimeout = new SpdyTimeout();
        this.errorCode = null;
        if (spdyConnection == null) {
            throw new NullPointerException("connection == null");
        }
        if (list == null) {
            throw new NullPointerException("requestHeaders == null");
        }
        this.id = n2;
        this.connection = spdyConnection;
        this.bytesLeftInWriteWindow = spdyConnection.peerSettings.getInitialWindowSize(65536);
        this.source = new SpdyDataSource(spdyConnection.okHttpSettings.getInitialWindowSize(65536));
        this.sink = new SpdyDataSink();
        this.source.finished = bl3;
        this.sink.finished = bl2;
        this.requestHeaders = list;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void cancelStreamIfNecessary() throws IOException {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        // MONITORENTER : this
        boolean bl2 = !this.source.finished && this.source.closed && (this.sink.finished || this.sink.closed);
        boolean bl3 = this.isOpen();
        // MONITOREXIT : this
        if (bl2) {
            this.close(ErrorCode.CANCEL);
            return;
        }
        if (bl3) return;
        this.connection.removeStream(this.id);
    }

    private void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        }
        if (this.sink.finished) {
            throw new IOException("stream finished");
        }
        if (this.errorCode != null) {
            throw new IOException("stream was reset: " + (Object)((Object)this.errorCode));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean closeInternal(ErrorCode errorCode) {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            }
            if (this.source.finished && this.sink.finished) {
                return false;
            }
            this.errorCode = errorCode;
            this.notifyAll();
        }
        this.connection.removeStream(this.id);
        return true;
    }

    private void waitForIo() throws InterruptedIOException {
        try {
            this.wait();
            return;
        }
        catch (InterruptedException var1_1) {
            throw new InterruptedIOException();
        }
    }

    void addBytesToWriteWindow(long l2) {
        this.bytesLeftInWriteWindow += l2;
        if (l2 > 0) {
            this.notifyAll();
        }
    }

    public void close(ErrorCode errorCode) throws IOException {
        if (!this.closeInternal(errorCode)) {
            return;
        }
        this.connection.writeSynReset(this.id, errorCode);
    }

    public void closeLater(ErrorCode errorCode) {
        if (!this.closeInternal(errorCode)) {
            return;
        }
        this.connection.writeSynResetLater(this.id, errorCode);
    }

    public SpdyConnection getConnection() {
        return this.connection;
    }

    public ErrorCode getErrorCode() {
        synchronized (this) {
            ErrorCode errorCode = this.errorCode;
            return errorCode;
        }
    }

    public int getId() {
        return this.id;
    }

    public List<Header> getRequestHeaders() {
        return this.requestHeaders;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<Header> getResponseHeaders() throws IOException {
        synchronized (this) {
            this.readTimeout.enter();
            try {
                while (this.responseHeaders == null && this.errorCode == null) {
                    this.waitForIo();
                }
            }
            finally {
                this.readTimeout.exitAndThrowIfTimedOut();
            }
            if (this.responseHeaders == null) throw new IOException("stream was reset: " + (Object)((Object)this.errorCode));
            return this.responseHeaders;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Sink getSink() {
        synchronized (this) {
            if (this.responseHeaders == null && !this.isLocallyInitiated()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
            return this.sink;
        }
    }

    public Source getSource() {
        return this.source;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isLocallyInitiated() {
        boolean bl2 = (this.id & 1) == 1;
        if (this.connection.client != bl2) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isOpen() {
        boolean bl2 = false;
        synchronized (this) {
            Object object;
            block6 : {
                object = this.errorCode;
                if (object == null) break block6;
                return bl2;
            }
            if (!this.source.finished) {
                if (!this.source.closed) return true;
            }
            if (!this.sink.finished) {
                if (!this.sink.closed) return true;
            }
            if ((object = this.responseHeaders) != null) return bl2;
            return true;
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    void receiveData(BufferedSource bufferedSource, int n2) throws IOException {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        this.source.receive(bufferedSource, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void receiveFin() {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        // MONITORENTER : this
        this.source.finished = true;
        boolean bl2 = this.isOpen();
        this.notifyAll();
        // MONITOREXIT : this
        if (bl2) return;
        this.connection.removeStream(this.id);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void receiveHeaders(List<Header> object, HeadersMode list) {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        Object var4_3 = null;
        boolean bl2 = true;
        // MONITORENTER : this
        if (this.responseHeaders == null) {
            if (list.failIfHeadersAbsent()) {
                object = ErrorCode.PROTOCOL_ERROR;
            } else {
                this.responseHeaders = object;
                bl2 = this.isOpen();
                this.notifyAll();
                object = var4_3;
            }
        } else if (list.failIfHeadersPresent()) {
            object = ErrorCode.STREAM_IN_USE;
        } else {
            list = new ArrayList();
            list.addAll(this.responseHeaders);
            list.addAll(object);
            this.responseHeaders = list;
            object = var4_3;
        }
        // MONITOREXIT : this
        if (object != null) {
            this.closeLater((ErrorCode)((Object)object));
            return;
        }
        if (bl2) return;
        this.connection.removeStream(this.id);
    }

    void receiveRstStream(ErrorCode errorCode) {
        synchronized (this) {
            if (this.errorCode == null) {
                this.errorCode = errorCode;
                this.notifyAll();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reply(List<Header> list, boolean bl2) throws IOException {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        boolean bl3 = false;
        synchronized (this) {
            if (list == null) {
                throw new NullPointerException("responseHeaders == null");
            }
            if (this.responseHeaders != null) {
                throw new IllegalStateException("reply already sent");
            }
            this.responseHeaders = list;
            if (!bl2) {
                this.sink.finished = true;
                bl3 = true;
            }
        }
        this.connection.writeSynReply(this.id, bl3, list);
        if (bl3) {
            this.connection.flush();
        }
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }

    final class SpdyDataSink
    implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled;
        private boolean closed;
        private boolean finished;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl2 = !SpdyStream.class.desiredAssertionStatus();
            $assertionsDisabled = bl2;
        }

        SpdyDataSink() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void close() throws IOException {
            if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                throw new AssertionError();
            }
            SpdyStream spdyStream = SpdyStream.this;
            // MONITORENTER : spdyStream
            if (this.closed) {
                // MONITOREXIT : spdyStream
                return;
            }
            // MONITOREXIT : spdyStream
            if (!SpdyStream.this.sink.finished) {
                SpdyStream.this.connection.writeData(SpdyStream.this.id, true, null, 0);
            }
            spdyStream = SpdyStream.this;
            // MONITORENTER : spdyStream
            this.closed = true;
            // MONITOREXIT : spdyStream
            SpdyStream.this.connection.flush();
            SpdyStream.this.cancelStreamIfNecessary();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void flush() throws IOException {
            if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                throw new AssertionError();
            }
            SpdyStream spdyStream = SpdyStream.this;
            synchronized (spdyStream) {
                SpdyStream.this.checkOutNotClosed();
            }
            SpdyStream.this.connection.flush();
        }

        @Override
        public Timeout timeout() {
            return SpdyStream.this.writeTimeout;
        }

        /*
         * Exception decompiling
         */
        @Override
        public void write(Buffer var1_1, long var2_3) throws IOException {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:371)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:449)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:664)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:747)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
            // org.benf.cfr.reader.Main.doJar(Main.java:128)
            // org.benf.cfr.reader.Main.main(Main.java:178)
            throw new IllegalStateException("Decompilation failed");
        }
    }

    private final class SpdyDataSource
    implements Source {
        static final /* synthetic */ boolean $assertionsDisabled;
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer;
        private final Buffer receiveBuffer;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl2 = !SpdyStream.class.desiredAssertionStatus();
            $assertionsDisabled = bl2;
        }

        private SpdyDataSource(long l2) {
            this.receiveBuffer = new Buffer();
            this.readBuffer = new Buffer();
            this.maxByteCount = l2;
        }

        private void checkNotClosed() throws IOException {
            if (this.closed) {
                throw new IOException("stream closed");
            }
            if (SpdyStream.this.errorCode != null) {
                throw new IOException("stream was reset: " + (Object)((Object)SpdyStream.this.errorCode));
            }
        }

        private void waitUntilReadable() throws IOException {
            SpdyStream.this.readTimeout.enter();
            try {
                while (this.readBuffer.size() == 0 && !this.finished && !this.closed && SpdyStream.this.errorCode == null) {
                    SpdyStream.this.waitForIo();
                }
            }
            finally {
                SpdyStream.this.readTimeout.exitAndThrowIfTimedOut();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void close() throws IOException {
            SpdyStream spdyStream = SpdyStream.this;
            synchronized (spdyStream) {
                this.closed = true;
                this.readBuffer.clear();
                SpdyStream.this.notifyAll();
            }
            SpdyStream.this.cancelStreamIfNecessary();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public long read(Buffer object, long l2) throws IOException {
            if (l2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + l2);
            }
            Object object2 = SpdyStream.this;
            synchronized (object2) {
                this.waitUntilReadable();
                this.checkNotClosed();
                if (this.readBuffer.size() == 0) {
                    return -1;
                }
                l2 = this.readBuffer.read((Buffer)object, Math.min(l2, this.readBuffer.size()));
                object = SpdyStream.this;
                object.unacknowledgedBytesRead += l2;
                if (SpdyStream.this.unacknowledgedBytesRead >= (long)(SpdyStream.access$500((SpdyStream)SpdyStream.this).okHttpSettings.getInitialWindowSize(65536) / 2)) {
                    SpdyStream.this.connection.writeWindowUpdateLater(SpdyStream.this.id, SpdyStream.this.unacknowledgedBytesRead);
                    SpdyStream.this.unacknowledgedBytesRead = 0;
                }
            }
            object = SpdyStream.this.connection;
            synchronized (object) {
                object2 = SpdyStream.this.connection;
                object2.unacknowledgedBytesRead += l2;
                if (SpdyStream.access$500((SpdyStream)SpdyStream.this).unacknowledgedBytesRead >= (long)(SpdyStream.access$500((SpdyStream)SpdyStream.this).okHttpSettings.getInitialWindowSize(65536) / 2)) {
                    SpdyStream.this.connection.writeWindowUpdateLater(0, SpdyStream.access$500((SpdyStream)SpdyStream.this).unacknowledgedBytesRead);
                    SpdyStream.access$500((SpdyStream)SpdyStream.this).unacknowledgedBytesRead = 0;
                }
                return l2;
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
        void receive(BufferedSource var1_1, long var2_2) throws IOException {
            var5_3 = var2_2;
            if (SpdyDataSource.$assertionsDisabled) ** GOTO lbl14
            var5_3 = var2_2;
            if (!Thread.holdsLock(SpdyStream.this)) ** GOTO lbl14
            throw new AssertionError();
lbl-1000: // 1 sources:
            {
                var5_3 -= var2_2;
                var8_6 = SpdyStream.this;
                // MONITORENTER : var8_6
                var4_4 = this.readBuffer.size() == 0;
                this.readBuffer.writeAll(this.receiveBuffer);
                if (var4_4) {
                    SpdyStream.this.notifyAll();
                }
                // MONITOREXIT : var8_6
lbl14: // 3 sources:
                if (var5_3 <= 0) return;
                var8_6 = SpdyStream.this;
                // MONITORENTER : var8_6
                var7_5 = this.finished;
                var4_4 = this.readBuffer.size() + var5_3 > this.maxByteCount;
                // MONITOREXIT : var8_6
                if (var4_4) {
                    var1_1.skip(var5_3);
                    SpdyStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                }
                if (!var7_5) continue;
                var1_1.skip(var5_3);
                return;
                ** while ((var2_2 = var1_1.read((Buffer)this.receiveBuffer, (long)var5_3)) != -1)
            }
lbl28: // 1 sources:
            throw new EOFException();
        }

        @Override
        public Timeout timeout() {
            return SpdyStream.this.readTimeout;
        }
    }

    class SpdyTimeout
    extends AsyncTimeout {
        SpdyTimeout() {
        }

        public void exitAndThrowIfTimedOut() throws InterruptedIOException {
            if (this.exit()) {
                throw new InterruptedIOException("timeout");
            }
        }

        @Override
        protected void timedOut() {
            SpdyStream.this.closeLater(ErrorCode.CANCEL);
        }
    }

}

