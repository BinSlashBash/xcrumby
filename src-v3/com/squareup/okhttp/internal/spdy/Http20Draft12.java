package com.squareup.okhttp.internal.spdy;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.games.request.GameRequest;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.spdy.FrameReader.Handler;
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
import org.json.zip.JSONzip;

public final class Http20Draft12 implements Variant {
    private static final ByteString CONNECTION_PREFACE;
    static final byte FLAG_ACK = (byte) 1;
    static final byte FLAG_COMPRESSED = (byte) 32;
    static final byte FLAG_END_HEADERS = (byte) 4;
    static final byte FLAG_END_PUSH_PROMISE = (byte) 4;
    static final byte FLAG_END_SEGMENT = (byte) 2;
    static final byte FLAG_END_STREAM = (byte) 1;
    static final byte FLAG_NONE = (byte) 0;
    static final byte FLAG_PAD_HIGH = (byte) 16;
    static final byte FLAG_PAD_LOW = (byte) 8;
    static final byte FLAG_PRIORITY = (byte) 32;
    static final int MAX_FRAME_SIZE = 16383;
    static final byte TYPE_ALTSVC = (byte) 10;
    static final byte TYPE_BLOCKED = (byte) 11;
    static final byte TYPE_CONTINUATION = (byte) 9;
    static final byte TYPE_DATA = (byte) 0;
    static final byte TYPE_GOAWAY = (byte) 7;
    static final byte TYPE_HEADERS = (byte) 1;
    static final byte TYPE_PING = (byte) 6;
    static final byte TYPE_PRIORITY = (byte) 2;
    static final byte TYPE_PUSH_PROMISE = (byte) 5;
    static final byte TYPE_RST_STREAM = (byte) 3;
    static final byte TYPE_SETTINGS = (byte) 4;
    static final byte TYPE_WINDOW_UPDATE = (byte) 8;
    private static final Logger logger;

    static final class FrameLogger {
        private static final String[] BINARY;
        private static final String[] FLAGS;
        private static final String[] TYPES;

        FrameLogger() {
        }

        static String formatHeader(boolean inbound, int streamId, int length, byte type, byte flags) {
            String formattedType = type < TYPES.length ? TYPES[type] : String.format("0x%02x", new Object[]{Byte.valueOf(type)});
            String formattedFlags = formatFlags(type, flags);
            String str = "%s 0x%08x %5d %-13s %s";
            Object[] objArr = new Object[5];
            objArr[0] = inbound ? "<<" : ">>";
            objArr[1] = Integer.valueOf(streamId);
            objArr[2] = Integer.valueOf(length);
            objArr[3] = formattedType;
            objArr[4] = formattedFlags;
            return String.format(str, objArr);
        }

        static String formatFlags(byte type, byte flags) {
            if (flags == null) {
                return UnsupportedUrlFragment.DISPLAY_NAME;
            }
            switch (type) {
                case Std.STD_URL /*2*/:
                case Std.STD_URI /*3*/:
                case Std.STD_PATTERN /*7*/:
                case Std.STD_LOCALE /*8*/:
                case Std.STD_TIME_ZONE /*10*/:
                case Std.STD_INET_ADDRESS /*11*/:
                    return BINARY[flags];
                case Std.STD_CLASS /*4*/:
                case Std.STD_CURRENCY /*6*/:
                    return flags == 1 ? "ACK" : BINARY[flags];
                default:
                    String result = flags < FLAGS.length ? FLAGS[flags] : BINARY[flags];
                    if (type != 5 || (flags & 4) == 0) {
                        return (type != null || (flags & 32) == 0) ? result : result.replace("PRIORITY", "COMPRESSED");
                    } else {
                        return result.replace("HEADERS", "PUSH_PROMISE");
                    }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static {
            /*
            r17 = 12;
            r0 = r17;
            r0 = new java.lang.String[r0];
            r17 = r0;
            r18 = 0;
            r19 = "DATA";
            r17[r18] = r19;
            r18 = 1;
            r19 = "HEADERS";
            r17[r18] = r19;
            r18 = 2;
            r19 = "PRIORITY";
            r17[r18] = r19;
            r18 = 3;
            r19 = "RST_STREAM";
            r17[r18] = r19;
            r18 = 4;
            r19 = "SETTINGS";
            r17[r18] = r19;
            r18 = 5;
            r19 = "PUSH_PROMISE";
            r17[r18] = r19;
            r18 = 6;
            r19 = "PING";
            r17[r18] = r19;
            r18 = 7;
            r19 = "GOAWAY";
            r17[r18] = r19;
            r18 = 8;
            r19 = "WINDOW_UPDATE";
            r17[r18] = r19;
            r18 = 9;
            r19 = "CONTINUATION";
            r17[r18] = r19;
            r18 = 10;
            r19 = "ALTSVC";
            r17[r18] = r19;
            r18 = 11;
            r19 = "BLOCKED";
            r17[r18] = r19;
            TYPES = r17;
            r17 = 64;
            r0 = r17;
            r0 = new java.lang.String[r0];
            r17 = r0;
            FLAGS = r17;
            r17 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
            r0 = r17;
            r0 = new java.lang.String[r0];
            r17 = r0;
            BINARY = r17;
            r6 = 0;
        L_0x0067:
            r17 = BINARY;
            r0 = r17;
            r0 = r0.length;
            r17 = r0;
            r0 = r17;
            if (r6 >= r0) goto L_0x0097;
        L_0x0072:
            r17 = BINARY;
            r18 = "%8s";
            r19 = 1;
            r0 = r19;
            r0 = new java.lang.Object[r0];
            r19 = r0;
            r20 = 0;
            r21 = java.lang.Integer.toBinaryString(r6);
            r19[r20] = r21;
            r18 = java.lang.String.format(r18, r19);
            r19 = 32;
            r20 = 48;
            r18 = r18.replace(r19, r20);
            r17[r6] = r18;
            r6 = r6 + 1;
            goto L_0x0067;
        L_0x0097:
            r17 = FLAGS;
            r18 = 0;
            r19 = "";
            r17[r18] = r19;
            r17 = FLAGS;
            r18 = 1;
            r19 = "END_STREAM";
            r17[r18] = r19;
            r17 = FLAGS;
            r18 = 2;
            r19 = "END_SEGMENT";
            r17[r18] = r19;
            r17 = FLAGS;
            r18 = 3;
            r19 = "END_STREAM|END_SEGMENT";
            r17[r18] = r19;
            r17 = 3;
            r0 = r17;
            r14 = new int[r0];
            r14 = {1, 2, 3};
            r17 = FLAGS;
            r18 = 8;
            r19 = "PAD_LOW";
            r17[r18] = r19;
            r17 = FLAGS;
            r18 = 24;
            r19 = "PAD_LOW|PAD_HIGH";
            r17[r18] = r19;
            r17 = 2;
            r0 = r17;
            r0 = new int[r0];
            r16 = r0;
            r16 = {8, 24};
            r1 = r14;
            r10 = r1.length;
            r7 = 0;
            r8 = r7;
        L_0x00df:
            if (r8 >= r10) goto L_0x0117;
        L_0x00e1:
            r13 = r1[r8];
            r2 = r16;
            r11 = r2.length;
            r7 = 0;
        L_0x00e7:
            if (r7 >= r11) goto L_0x0113;
        L_0x00e9:
            r15 = r2[r7];
            r17 = FLAGS;
            r18 = r13 | r15;
            r19 = new java.lang.StringBuilder;
            r19.<init>();
            r20 = FLAGS;
            r20 = r20[r13];
            r19 = r19.append(r20);
            r20 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
            r19 = r19.append(r20);
            r20 = FLAGS;
            r20 = r20[r15];
            r19 = r19.append(r20);
            r19 = r19.toString();
            r17[r18] = r19;
            r7 = r7 + 1;
            goto L_0x00e7;
        L_0x0113:
            r7 = r8 + 1;
            r8 = r7;
            goto L_0x00df;
        L_0x0117:
            r17 = FLAGS;
            r18 = 4;
            r19 = "END_HEADERS";
            r17[r18] = r19;
            r17 = FLAGS;
            r18 = 32;
            r19 = "PRIORITY";
            r17[r18] = r19;
            r17 = FLAGS;
            r18 = 36;
            r19 = "END_HEADERS|PRIORITY";
            r17[r18] = r19;
            r17 = 3;
            r0 = r17;
            r5 = new int[r0];
            r5 = {4, 32, 36};
            r1 = r5;
            r10 = r1.length;
            r7 = 0;
            r9 = r7;
        L_0x013c:
            if (r9 >= r10) goto L_0x01b5;
        L_0x013e:
            r4 = r1[r9];
            r2 = r14;
            r11 = r2.length;
            r7 = 0;
            r8 = r7;
        L_0x0144:
            if (r8 >= r11) goto L_0x01b1;
        L_0x0146:
            r13 = r2[r8];
            r17 = FLAGS;
            r18 = r13 | r4;
            r19 = new java.lang.StringBuilder;
            r19.<init>();
            r20 = FLAGS;
            r20 = r20[r13];
            r19 = r19.append(r20);
            r20 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
            r19 = r19.append(r20);
            r20 = FLAGS;
            r20 = r20[r4];
            r19 = r19.append(r20);
            r19 = r19.toString();
            r17[r18] = r19;
            r3 = r16;
            r12 = r3.length;
            r7 = 0;
        L_0x0171:
            if (r7 >= r12) goto L_0x01ad;
        L_0x0173:
            r15 = r3[r7];
            r17 = FLAGS;
            r18 = r13 | r4;
            r18 = r18 | r15;
            r19 = new java.lang.StringBuilder;
            r19.<init>();
            r20 = FLAGS;
            r20 = r20[r13];
            r19 = r19.append(r20);
            r20 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
            r19 = r19.append(r20);
            r20 = FLAGS;
            r20 = r20[r4];
            r19 = r19.append(r20);
            r20 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
            r19 = r19.append(r20);
            r20 = FLAGS;
            r20 = r20[r15];
            r19 = r19.append(r20);
            r19 = r19.toString();
            r17[r18] = r19;
            r7 = r7 + 1;
            goto L_0x0171;
        L_0x01ad:
            r7 = r8 + 1;
            r8 = r7;
            goto L_0x0144;
        L_0x01b1:
            r7 = r9 + 1;
            r9 = r7;
            goto L_0x013c;
        L_0x01b5:
            r6 = 0;
        L_0x01b6:
            r17 = FLAGS;
            r0 = r17;
            r0 = r0.length;
            r17 = r0;
            r0 = r17;
            if (r6 >= r0) goto L_0x01d2;
        L_0x01c1:
            r17 = FLAGS;
            r17 = r17[r6];
            if (r17 != 0) goto L_0x01cf;
        L_0x01c7:
            r17 = FLAGS;
            r18 = BINARY;
            r18 = r18[r6];
            r17[r6] = r18;
        L_0x01cf:
            r6 = r6 + 1;
            goto L_0x01b6;
        L_0x01d2:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.Http20Draft12.FrameLogger.<clinit>():void");
        }
    }

    static final class ContinuationSource implements Source {
        byte flags;
        short left;
        short length;
        short padding;
        private final BufferedSource source;
        int streamId;

        public ContinuationSource(BufferedSource source) {
            this.source = source;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            while (this.left == (short) 0) {
                this.source.skip((long) this.padding);
                this.padding = (short) 0;
                if ((this.flags & 4) != 0) {
                    return -1;
                }
                readContinuationHeader();
            }
            long read = this.source.read(sink, Math.min(byteCount, (long) this.left));
            if (read == -1) {
                return -1;
            }
            this.left = (short) ((int) (((long) this.left) - read));
            return read;
        }

        public Timeout timeout() {
            return this.source.timeout();
        }

        public void close() throws IOException {
        }

        private void readContinuationHeader() throws IOException {
            int previousStreamId = this.streamId;
            int w1 = this.source.readInt();
            int w2 = this.source.readInt();
            this.length = (short) ((1073676288 & w1) >> 16);
            byte type = (byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & w1) >> 8);
            this.flags = (byte) (w1 & MotionEventCompat.ACTION_MASK);
            if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                Http20Draft12.logger.fine(FrameLogger.formatHeader(true, this.streamId, this.length, type, this.flags));
            }
            this.padding = Http20Draft12.readPadding(this.source, this.flags);
            short access$400 = Http20Draft12.lengthWithoutPadding(this.length, this.flags, this.padding);
            this.left = access$400;
            this.length = access$400;
            this.streamId = Integer.MAX_VALUE & w2;
            if (type != 9) {
                throw Http20Draft12.ioException("%s != TYPE_CONTINUATION", Byte.valueOf(type));
            } else if (this.streamId != previousStreamId) {
                throw Http20Draft12.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }
    }

    static final class Reader implements FrameReader {
        private final boolean client;
        private final ContinuationSource continuation;
        final Reader hpackReader;
        private final BufferedSource source;

        Reader(BufferedSource source, int headerTableSize, boolean client) {
            this.source = source;
            this.client = client;
            this.continuation = new ContinuationSource(this.source);
            this.hpackReader = new Reader(headerTableSize, this.continuation);
        }

        public void readConnectionPreface() throws IOException {
            if (!this.client) {
                ByteString connectionPreface = this.source.readByteString((long) Http20Draft12.CONNECTION_PREFACE.size());
                if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                    Http20Draft12.logger.fine(String.format("<< CONNECTION %s", new Object[]{connectionPreface.hex()}));
                }
                if (!Http20Draft12.CONNECTION_PREFACE.equals(connectionPreface)) {
                    throw Http20Draft12.ioException("Expected a connection header but was %s", connectionPreface.utf8());
                }
            }
        }

        public boolean nextFrame(Handler handler) throws IOException {
            try {
                int w1 = this.source.readInt();
                short length = (short) ((1073676288 & w1) >> 16);
                byte type = (byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & w1) >> 8);
                byte flags = (byte) (w1 & MotionEventCompat.ACTION_MASK);
                int streamId = this.source.readInt() & Integer.MAX_VALUE;
                if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                    Http20Draft12.logger.fine(FrameLogger.formatHeader(true, streamId, length, type, flags));
                }
                switch (type) {
                    case JSONzip.zipEmptyObject /*0*/:
                        readData(handler, length, flags, streamId);
                        return true;
                    case Std.STD_FILE /*1*/:
                        readHeaders(handler, length, flags, streamId);
                        return true;
                    case Std.STD_URL /*2*/:
                        readPriority(handler, length, flags, streamId);
                        return true;
                    case Std.STD_URI /*3*/:
                        readRstStream(handler, length, flags, streamId);
                        return true;
                    case Std.STD_CLASS /*4*/:
                        readSettings(handler, length, flags, streamId);
                        return true;
                    case Std.STD_JAVA_TYPE /*5*/:
                        readPushPromise(handler, length, flags, streamId);
                        return true;
                    case Std.STD_CURRENCY /*6*/:
                        readPing(handler, length, flags, streamId);
                        return true;
                    case Std.STD_PATTERN /*7*/:
                        readGoAway(handler, length, flags, streamId);
                        return true;
                    case Std.STD_LOCALE /*8*/:
                        readWindowUpdate(handler, length, flags, streamId);
                        return true;
                    case Std.STD_TIME_ZONE /*10*/:
                        readAlternateService(handler, length, flags, streamId);
                        return true;
                    case Std.STD_INET_ADDRESS /*11*/:
                        if (length == (short) 0) {
                            return true;
                        }
                        throw Http20Draft12.ioException("TYPE_BLOCKED length != 0: %s", Short.valueOf(length));
                    default:
                        throw Http20Draft12.ioException("PROTOCOL_ERROR: unknown frame type %s", Byte.valueOf(type));
                }
            } catch (IOException e) {
                return false;
            }
        }

        private void readHeaders(Handler handler, short length, byte flags, int streamId) throws IOException {
            if (streamId == 0) {
                throw Http20Draft12.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            }
            boolean endStream;
            if ((flags & 1) != 0) {
                endStream = true;
            } else {
                endStream = false;
            }
            short padding = Http20Draft12.readPadding(this.source, flags);
            if ((flags & 32) != 0) {
                readPriority(handler, streamId);
                length = (short) (length - 5);
            }
            handler.headers(false, endStream, streamId, -1, readHeaderBlock(Http20Draft12.lengthWithoutPadding(length, flags, padding), padding, flags, streamId), HeadersMode.HTTP_20_HEADERS);
        }

        private List<Header> readHeaderBlock(short length, short padding, byte flags, int streamId) throws IOException {
            ContinuationSource continuationSource = this.continuation;
            this.continuation.left = length;
            continuationSource.length = length;
            this.continuation.padding = padding;
            this.continuation.flags = flags;
            this.continuation.streamId = streamId;
            this.hpackReader.readHeaders();
            this.hpackReader.emitReferenceSet();
            return this.hpackReader.getAndReset();
        }

        private void readData(Handler handler, short length, byte flags, int streamId) throws IOException {
            boolean inFinished;
            boolean gzipped = true;
            if ((flags & 1) != 0) {
                inFinished = true;
            } else {
                inFinished = false;
            }
            if ((flags & 32) == 0) {
                gzipped = false;
            }
            if (gzipped) {
                throw Http20Draft12.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            }
            short padding = Http20Draft12.readPadding(this.source, flags);
            handler.data(inFinished, streamId, this.source, Http20Draft12.lengthWithoutPadding(length, flags, padding));
            this.source.skip((long) padding);
        }

        private void readPriority(Handler handler, short length, byte flags, int streamId) throws IOException {
            if (length != (short) 5) {
                throw Http20Draft12.ioException("TYPE_PRIORITY length: %d != 5", Short.valueOf(length));
            } else if (streamId == 0) {
                throw Http20Draft12.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            } else {
                readPriority(handler, streamId);
            }
        }

        private void readPriority(Handler handler, int streamId) throws IOException {
            int w1 = this.source.readInt();
            handler.priority(streamId, w1 & Integer.MAX_VALUE, (this.source.readByte() & MotionEventCompat.ACTION_MASK) + 1, (ExploreByTouchHelper.INVALID_ID & w1) != 0);
        }

        private void readRstStream(Handler handler, short length, byte flags, int streamId) throws IOException {
            if (length != (short) 4) {
                throw Http20Draft12.ioException("TYPE_RST_STREAM length: %d != 4", Short.valueOf(length));
            } else if (streamId == 0) {
                throw Http20Draft12.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            } else {
                ErrorCode errorCode = ErrorCode.fromHttp2(this.source.readInt());
                if (errorCode == null) {
                    throw Http20Draft12.ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(errorCodeInt));
                } else {
                    handler.rstStream(streamId, errorCode);
                }
            }
        }

        private void readSettings(Handler handler, short length, byte flags, int streamId) throws IOException {
            if (streamId != 0) {
                throw Http20Draft12.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
            } else if ((flags & 1) != 0) {
                if (length != (short) 0) {
                    throw Http20Draft12.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                }
                handler.ackSettings();
            } else if (length % 5 != 0) {
                throw Http20Draft12.ioException("TYPE_SETTINGS length %% 5 != 0: %s", Short.valueOf(length));
            } else {
                Settings settings = new Settings();
                for (short i = (short) 0; i < length; i += 5) {
                    int id = this.source.readByte();
                    int value = this.source.readInt();
                    switch (id) {
                        case Std.STD_FILE /*1*/:
                        case Std.STD_JAVA_TYPE /*5*/:
                            break;
                        case Std.STD_URL /*2*/:
                            if (!(value == 0 || value == 1)) {
                                throw Http20Draft12.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            }
                        case Std.STD_URI /*3*/:
                            id = 4;
                            break;
                        case Std.STD_CLASS /*4*/:
                            id = 7;
                            if (value >= 0) {
                                break;
                            }
                            throw Http20Draft12.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                        default:
                            throw Http20Draft12.ioException("PROTOCOL_ERROR invalid settings id: %s", Integer.valueOf(id));
                    }
                    settings.set(id, 0, value);
                }
                handler.settings(false, settings);
                if (settings.getHeaderTableSize() >= 0) {
                    this.hpackReader.maxHeaderTableByteCountSetting(settings.getHeaderTableSize());
                }
            }
        }

        private void readPushPromise(Handler handler, short length, byte flags, int streamId) throws IOException {
            if (streamId == 0) {
                throw Http20Draft12.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            }
            handler.pushPromise(streamId, this.source.readInt() & Integer.MAX_VALUE, readHeaderBlock((short) (length - 4), Http20Draft12.readPadding(this.source, flags), flags, streamId));
        }

        private void readPing(Handler handler, short length, byte flags, int streamId) throws IOException {
            boolean ack = true;
            if (length != (short) 8) {
                throw Http20Draft12.ioException("TYPE_PING length != 8: %s", Short.valueOf(length));
            } else if (streamId != 0) {
                throw Http20Draft12.ioException("TYPE_PING streamId != 0", new Object[0]);
            } else {
                int payload1 = this.source.readInt();
                int payload2 = this.source.readInt();
                if ((flags & 1) == 0) {
                    ack = false;
                }
                handler.ping(ack, payload1, payload2);
            }
        }

        private void readGoAway(Handler handler, short length, byte flags, int streamId) throws IOException {
            if (length < (short) 8) {
                throw Http20Draft12.ioException("TYPE_GOAWAY length < 8: %s", Short.valueOf(length));
            } else if (streamId != 0) {
                throw Http20Draft12.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            } else {
                int lastStreamId = this.source.readInt();
                int opaqueDataLength = length - 8;
                ErrorCode errorCode = ErrorCode.fromHttp2(this.source.readInt());
                if (errorCode == null) {
                    throw Http20Draft12.ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(errorCodeInt));
                }
                ByteString debugData = ByteString.EMPTY;
                if (opaqueDataLength > 0) {
                    debugData = this.source.readByteString((long) opaqueDataLength);
                }
                handler.goAway(lastStreamId, errorCode, debugData);
            }
        }

        private void readWindowUpdate(Handler handler, short length, byte flags, int streamId) throws IOException {
            if (length != (short) 4) {
                throw Http20Draft12.ioException("TYPE_WINDOW_UPDATE length !=4: %s", Short.valueOf(length));
            }
            long increment = ((long) this.source.readInt()) & 2147483647L;
            if (increment == 0) {
                throw Http20Draft12.ioException("windowSizeIncrement was 0", Long.valueOf(increment));
            } else {
                handler.windowUpdate(streamId, increment);
            }
        }

        private void readAlternateService(Handler handler, short length, byte flags, int streamId) throws IOException {
            long maxAge = ((long) this.source.readInt()) & 4294967295L;
            int port = this.source.readShort() & GameRequest.TYPE_ALL;
            this.source.readByte();
            int protocolLength = this.source.readByte() & MotionEventCompat.ACTION_MASK;
            ByteString protocol = this.source.readByteString((long) protocolLength);
            int hostLength = this.source.readByte() & MotionEventCompat.ACTION_MASK;
            String host = this.source.readUtf8((long) hostLength);
            handler.alternateService(streamId, this.source.readUtf8((long) (((length - 9) - protocolLength) - hostLength)), protocol, host, port, maxAge);
        }

        public void close() throws IOException {
            this.source.close();
        }
    }

    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final Buffer hpackBuffer;
        private final Writer hpackWriter;
        private final BufferedSink sink;

        Writer(BufferedSink sink, boolean client) {
            this.sink = sink;
            this.client = client;
            this.hpackBuffer = new Buffer();
            this.hpackWriter = new Writer(this.hpackBuffer);
        }

        public synchronized void flush() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.sink.flush();
        }

        public synchronized void ackSettings() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, 0, Http20Draft12.TYPE_SETTINGS, Http20Draft12.TYPE_HEADERS);
            this.sink.flush();
        }

        public synchronized void connectionPreface() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (this.client) {
                if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                    Http20Draft12.logger.fine(String.format(">> CONNECTION %s", new Object[]{Http20Draft12.CONNECTION_PREFACE.hex()}));
                }
                this.sink.write(Http20Draft12.CONNECTION_PREFACE.toByteArray());
                this.sink.flush();
            }
        }

        public synchronized void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock) throws IOException {
            if (inFinished) {
                throw new UnsupportedOperationException();
            } else if (this.closed) {
                throw new IOException("closed");
            } else {
                headers(outFinished, streamId, headerBlock);
            }
        }

        public synchronized void synReply(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(outFinished, streamId, headerBlock);
        }

        public synchronized void headers(int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            headers(false, streamId, headerBlock);
        }

        public synchronized void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (this.hpackBuffer.size() != 0) {
                throw new IllegalStateException();
            } else {
                this.hpackWriter.writeHeaders(requestHeaders);
                long byteCount = this.hpackBuffer.size();
                int length = (int) Math.min(16379, byteCount);
                frameHeader(streamId, length + 4, Http20Draft12.TYPE_PUSH_PROMISE, byteCount == ((long) length) ? Http20Draft12.TYPE_SETTINGS : Http20Draft12.TYPE_DATA);
                this.sink.writeInt(Integer.MAX_VALUE & promisedStreamId);
                this.sink.write(this.hpackBuffer, (long) length);
                if (byteCount > ((long) length)) {
                    writeContinuationFrames(streamId, byteCount - ((long) length));
                }
            }
        }

        void headers(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (this.hpackBuffer.size() != 0) {
                throw new IllegalStateException();
            } else {
                this.hpackWriter.writeHeaders(headerBlock);
                long byteCount = this.hpackBuffer.size();
                int length = (int) Math.min(16383, byteCount);
                byte flags = byteCount == ((long) length) ? Http20Draft12.TYPE_SETTINGS : Http20Draft12.TYPE_DATA;
                if (outFinished) {
                    flags = (byte) (flags | 1);
                }
                frameHeader(streamId, length, Http20Draft12.TYPE_HEADERS, flags);
                this.sink.write(this.hpackBuffer, (long) length);
                if (byteCount > ((long) length)) {
                    writeContinuationFrames(streamId, byteCount - ((long) length));
                }
            }
        }

        private void writeContinuationFrames(int streamId, long byteCount) throws IOException {
            while (byteCount > 0) {
                int length = (int) Math.min(16383, byteCount);
                byteCount -= (long) length;
                frameHeader(streamId, length, Http20Draft12.TYPE_CONTINUATION, byteCount == 0 ? Http20Draft12.TYPE_SETTINGS : Http20Draft12.TYPE_DATA);
                this.sink.write(this.hpackBuffer, (long) length);
            }
        }

        public synchronized void rstStream(int streamId, ErrorCode errorCode) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.spdyRstCode == -1) {
                throw new IllegalArgumentException();
            } else {
                frameHeader(streamId, 4, Http20Draft12.TYPE_RST_STREAM, Http20Draft12.TYPE_DATA);
                this.sink.writeInt(errorCode.httpCode);
                this.sink.flush();
            }
        }

        public synchronized void data(boolean outFinished, int streamId, Buffer source) throws IOException {
            data(outFinished, streamId, source, (int) source.size());
        }

        public synchronized void data(boolean outFinished, int streamId, Buffer source, int byteCount) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            byte flags = Http20Draft12.TYPE_DATA;
            if (outFinished) {
                flags = (byte) 1;
            }
            dataFrame(streamId, flags, source, byteCount);
        }

        void dataFrame(int streamId, byte flags, Buffer buffer, int byteCount) throws IOException {
            frameHeader(streamId, byteCount, Http20Draft12.TYPE_DATA, flags);
            if (byteCount > 0) {
                this.sink.write(buffer, (long) byteCount);
            }
        }

        public synchronized void settings(Settings settings) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, settings.size() * 5, Http20Draft12.TYPE_SETTINGS, Http20Draft12.TYPE_DATA);
            for (int i = 0; i < 10; i++) {
                if (settings.isSet(i)) {
                    int id = i;
                    if (id == 4) {
                        id = 3;
                    } else if (id == 7) {
                        id = 4;
                    }
                    this.sink.writeByte(id);
                    this.sink.writeInt(settings.get(i));
                }
            }
            this.sink.flush();
        }

        public synchronized void ping(boolean ack, int payload1, int payload2) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, 8, Http20Draft12.TYPE_PING, ack ? Http20Draft12.TYPE_HEADERS : Http20Draft12.TYPE_DATA);
            this.sink.writeInt(payload1);
            this.sink.writeInt(payload2);
            this.sink.flush();
        }

        public synchronized void goAway(int lastGoodStreamId, ErrorCode errorCode, byte[] debugData) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (errorCode.httpCode == -1) {
                throw Http20Draft12.illegalArgument("errorCode.httpCode == -1", new Object[0]);
            } else {
                frameHeader(0, debugData.length + 8, Http20Draft12.TYPE_GOAWAY, Http20Draft12.TYPE_DATA);
                this.sink.writeInt(lastGoodStreamId);
                this.sink.writeInt(errorCode.httpCode);
                if (debugData.length > 0) {
                    this.sink.write(debugData);
                }
                this.sink.flush();
            }
        }

        public synchronized void windowUpdate(int streamId, long windowSizeIncrement) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (windowSizeIncrement == 0 || windowSizeIncrement > 2147483647L) {
                throw Http20Draft12.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(windowSizeIncrement));
            } else {
                frameHeader(streamId, 4, Http20Draft12.TYPE_WINDOW_UPDATE, Http20Draft12.TYPE_DATA);
                this.sink.writeInt((int) windowSizeIncrement);
                this.sink.flush();
            }
        }

        public synchronized void close() throws IOException {
            this.closed = true;
            this.sink.close();
        }

        void frameHeader(int streamId, int length, byte type, byte flags) throws IOException {
            if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                Http20Draft12.logger.fine(FrameLogger.formatHeader(false, streamId, length, type, flags));
            }
            if (length > Http20Draft12.MAX_FRAME_SIZE) {
                throw Http20Draft12.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(Http20Draft12.MAX_FRAME_SIZE), Integer.valueOf(length));
            } else if ((ExploreByTouchHelper.INVALID_ID & streamId) != 0) {
                throw Http20Draft12.illegalArgument("reserved bit set: %s", Integer.valueOf(streamId));
            } else {
                this.sink.writeInt((((length & Http20Draft12.MAX_FRAME_SIZE) << 16) | ((type & MotionEventCompat.ACTION_MASK) << 8)) | (flags & MotionEventCompat.ACTION_MASK));
                this.sink.writeInt(Integer.MAX_VALUE & streamId);
            }
        }
    }

    static {
        logger = Logger.getLogger(Http20Draft12.class.getName());
        CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    }

    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    public FrameReader newReader(BufferedSource source, boolean client) {
        return new Reader(source, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, client);
    }

    public FrameWriter newWriter(BufferedSink sink, boolean client) {
        return new Writer(sink, client);
    }

    public int maxFrameSize() {
        return MAX_FRAME_SIZE;
    }

    private static IllegalArgumentException illegalArgument(String message, Object... args) {
        throw new IllegalArgumentException(String.format(message, args));
    }

    private static IOException ioException(String message, Object... args) throws IOException {
        throw new IOException(String.format(message, args));
    }

    private static short readPadding(BufferedSource source, byte flags) throws IOException {
        if ((flags & 16) == 0 || (flags & 8) != 0) {
            int padding = 0;
            if ((flags & 16) != 0) {
                padding = source.readShort() & GameRequest.TYPE_ALL;
            } else if ((flags & 8) != 0) {
                padding = source.readByte() & MotionEventCompat.ACTION_MASK;
            }
            if (padding <= MAX_FRAME_SIZE) {
                return (short) padding;
            }
            throw ioException("PROTOCOL_ERROR padding > %d: %d", Integer.valueOf(MAX_FRAME_SIZE), Integer.valueOf(padding));
        }
        throw ioException("PROTOCOL_ERROR FLAG_PAD_HIGH set without FLAG_PAD_LOW", new Object[0]);
    }

    private static short lengthWithoutPadding(short length, byte flags, short padding) throws IOException {
        if ((flags & 16) != 0) {
            length = (short) (length - 2);
        } else if ((flags & 8) != 0) {
            length = (short) (length - 1);
        }
        if (padding <= length) {
            return (short) (length - padding);
        }
        throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(padding), Short.valueOf(length));
    }
}
