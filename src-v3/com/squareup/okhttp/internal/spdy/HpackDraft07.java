package com.squareup.okhttp.internal.spdy;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.device.DeviceFragment;
import com.squareup.okhttp.internal.BitArray;
import com.squareup.okhttp.internal.BitArray.FixedCapacity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

final class HpackDraft07 {
    private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX;
    private static final int PREFIX_4_BITS = 15;
    private static final int PREFIX_6_BITS = 63;
    private static final int PREFIX_7_BITS = 127;
    private static final Header[] STATIC_HEADER_TABLE;

    static final class Reader {
        private final List<Header> emittedHeaders;
        BitArray emittedReferencedHeaders;
        int headerCount;
        Header[] headerTable;
        int headerTableByteCount;
        private int maxHeaderTableByteCount;
        private int maxHeaderTableByteCountSetting;
        int nextHeaderIndex;
        BitArray referencedHeaders;
        private final BufferedSource source;

        Reader(int maxHeaderTableByteCountSetting, Source source) {
            this.emittedHeaders = new ArrayList();
            this.headerTable = new Header[8];
            this.nextHeaderIndex = this.headerTable.length - 1;
            this.headerCount = 0;
            this.referencedHeaders = new FixedCapacity();
            this.emittedReferencedHeaders = new FixedCapacity();
            this.headerTableByteCount = 0;
            this.maxHeaderTableByteCountSetting = maxHeaderTableByteCountSetting;
            this.maxHeaderTableByteCount = maxHeaderTableByteCountSetting;
            this.source = Okio.buffer(source);
        }

        int maxHeaderTableByteCount() {
            return this.maxHeaderTableByteCount;
        }

        void maxHeaderTableByteCountSetting(int newMaxHeaderTableByteCountSetting) {
            this.maxHeaderTableByteCountSetting = newMaxHeaderTableByteCountSetting;
            this.maxHeaderTableByteCount = this.maxHeaderTableByteCountSetting;
            adjustHeaderTableByteCount();
        }

        private void adjustHeaderTableByteCount() {
            if (this.maxHeaderTableByteCount >= this.headerTableByteCount) {
                return;
            }
            if (this.maxHeaderTableByteCount == 0) {
                clearHeaderTable();
            } else {
                evictToRecoverBytes(this.headerTableByteCount - this.maxHeaderTableByteCount);
            }
        }

        private void clearHeaderTable() {
            clearReferenceSet();
            Arrays.fill(this.headerTable, null);
            this.nextHeaderIndex = this.headerTable.length - 1;
            this.headerCount = 0;
            this.headerTableByteCount = 0;
        }

        private int evictToRecoverBytes(int bytesToRecover) {
            int entriesToEvict = 0;
            if (bytesToRecover > 0) {
                for (int j = this.headerTable.length - 1; j >= this.nextHeaderIndex && bytesToRecover > 0; j--) {
                    bytesToRecover -= this.headerTable[j].hpackSize;
                    this.headerTableByteCount -= this.headerTable[j].hpackSize;
                    this.headerCount--;
                    entriesToEvict++;
                }
                this.referencedHeaders.shiftLeft(entriesToEvict);
                this.emittedReferencedHeaders.shiftLeft(entriesToEvict);
                System.arraycopy(this.headerTable, this.nextHeaderIndex + 1, this.headerTable, (this.nextHeaderIndex + 1) + entriesToEvict, this.headerCount);
                this.nextHeaderIndex += entriesToEvict;
            }
            return entriesToEvict;
        }

        void readHeaders() throws IOException {
            while (!this.source.exhausted()) {
                int b = this.source.readByte() & MotionEventCompat.ACTION_MASK;
                if (b == TransportMediator.FLAG_KEY_MEDIA_NEXT) {
                    throw new IOException("index == 0");
                } else if ((b & TransportMediator.FLAG_KEY_MEDIA_NEXT) == TransportMediator.FLAG_KEY_MEDIA_NEXT) {
                    readIndexedHeader(readInt(b, HpackDraft07.PREFIX_7_BITS) - 1);
                } else if (b == 64) {
                    readLiteralHeaderWithIncrementalIndexingNewName();
                } else if ((b & 64) == 64) {
                    readLiteralHeaderWithIncrementalIndexingIndexedName(readInt(b, HpackDraft07.PREFIX_6_BITS) - 1);
                } else if ((b & 32) == 32) {
                    if ((b & 16) != 16) {
                        this.maxHeaderTableByteCount = readInt(b, HpackDraft07.PREFIX_4_BITS);
                        if (this.maxHeaderTableByteCount < 0 || this.maxHeaderTableByteCount > this.maxHeaderTableByteCountSetting) {
                            throw new IOException("Invalid header table byte count " + this.maxHeaderTableByteCount);
                        }
                        adjustHeaderTableByteCount();
                    } else if ((b & HpackDraft07.PREFIX_4_BITS) != 0) {
                        throw new IOException("Invalid header table state change " + b);
                    } else {
                        clearReferenceSet();
                    }
                } else if (b == 16 || b == 0) {
                    readLiteralHeaderWithoutIndexingNewName();
                } else {
                    readLiteralHeaderWithoutIndexingIndexedName(readInt(b, HpackDraft07.PREFIX_4_BITS) - 1);
                }
            }
        }

        private void clearReferenceSet() {
            this.referencedHeaders.clear();
            this.emittedReferencedHeaders.clear();
        }

        void emitReferenceSet() {
            int i = this.headerTable.length - 1;
            while (i != this.nextHeaderIndex) {
                if (this.referencedHeaders.get(i) && !this.emittedReferencedHeaders.get(i)) {
                    this.emittedHeaders.add(this.headerTable[i]);
                }
                i--;
            }
        }

        List<Header> getAndReset() {
            List<Header> result = new ArrayList(this.emittedHeaders);
            this.emittedHeaders.clear();
            this.emittedReferencedHeaders.clear();
            return result;
        }

        private void readIndexedHeader(int index) throws IOException {
            if (isStaticHeader(index)) {
                index -= this.headerCount;
                if (index > HpackDraft07.STATIC_HEADER_TABLE.length - 1) {
                    throw new IOException("Header index too large " + (index + 1));
                }
                Header staticEntry = HpackDraft07.STATIC_HEADER_TABLE[index];
                if (this.maxHeaderTableByteCount == 0) {
                    this.emittedHeaders.add(staticEntry);
                    return;
                } else {
                    insertIntoHeaderTable(-1, staticEntry);
                    return;
                }
            }
            int headerTableIndex = headerTableIndex(index);
            if (!this.referencedHeaders.get(headerTableIndex)) {
                this.emittedHeaders.add(this.headerTable[headerTableIndex]);
                this.emittedReferencedHeaders.set(headerTableIndex);
            }
            this.referencedHeaders.toggle(headerTableIndex);
        }

        private int headerTableIndex(int index) {
            return (this.nextHeaderIndex + 1) + index;
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int index) throws IOException {
            this.emittedHeaders.add(new Header(getName(index), readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            this.emittedHeaders.add(new Header(HpackDraft07.checkLowercase(readByteString()), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int nameIndex) throws IOException {
            insertIntoHeaderTable(-1, new Header(getName(nameIndex), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            insertIntoHeaderTable(-1, new Header(HpackDraft07.checkLowercase(readByteString()), readByteString()));
        }

        private ByteString getName(int index) {
            if (isStaticHeader(index)) {
                return HpackDraft07.STATIC_HEADER_TABLE[index - this.headerCount].name;
            }
            return this.headerTable[headerTableIndex(index)].name;
        }

        private boolean isStaticHeader(int index) {
            return index >= this.headerCount;
        }

        private void insertIntoHeaderTable(int index, Header entry) {
            int delta = entry.hpackSize;
            if (index != -1) {
                delta -= this.headerTable[headerTableIndex(index)].hpackSize;
            }
            if (delta > this.maxHeaderTableByteCount) {
                clearHeaderTable();
                this.emittedHeaders.add(entry);
                return;
            }
            int entriesEvicted = evictToRecoverBytes((this.headerTableByteCount + delta) - this.maxHeaderTableByteCount);
            if (index == -1) {
                if (this.headerCount + 1 > this.headerTable.length) {
                    Header[] doubled = new Header[(this.headerTable.length * 2)];
                    System.arraycopy(this.headerTable, 0, doubled, this.headerTable.length, this.headerTable.length);
                    if (doubled.length == 64) {
                        this.referencedHeaders = ((FixedCapacity) this.referencedHeaders).toVariableCapacity();
                        this.emittedReferencedHeaders = ((FixedCapacity) this.emittedReferencedHeaders).toVariableCapacity();
                    }
                    this.referencedHeaders.shiftLeft(this.headerTable.length);
                    this.emittedReferencedHeaders.shiftLeft(this.headerTable.length);
                    this.nextHeaderIndex = this.headerTable.length - 1;
                    this.headerTable = doubled;
                }
                index = this.nextHeaderIndex;
                this.nextHeaderIndex = index - 1;
                this.referencedHeaders.set(index);
                this.headerTable[index] = entry;
                this.headerCount++;
            } else {
                index += headerTableIndex(index) + entriesEvicted;
                this.referencedHeaders.set(index);
                this.headerTable[index] = entry;
            }
            this.headerTableByteCount += delta;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & MotionEventCompat.ACTION_MASK;
        }

        int readInt(int firstByte, int prefixMask) throws IOException {
            int prefix = firstByte & prefixMask;
            if (prefix < prefixMask) {
                return prefix;
            }
            int result = prefixMask;
            int shift = 0;
            while (true) {
                int b = readByte();
                if ((b & TransportMediator.FLAG_KEY_MEDIA_NEXT) == 0) {
                    return result + (b << shift);
                }
                result += (b & HpackDraft07.PREFIX_7_BITS) << shift;
                shift += 7;
            }
        }

        ByteString readByteString() throws IOException {
            int firstByte = readByte();
            boolean huffmanDecode = (firstByte & TransportMediator.FLAG_KEY_MEDIA_NEXT) == TransportMediator.FLAG_KEY_MEDIA_NEXT;
            int length = readInt(firstByte, HpackDraft07.PREFIX_7_BITS);
            if (huffmanDecode) {
                return ByteString.of(Huffman.get().decode(this.source.readByteArray((long) length)));
            }
            return this.source.readByteString((long) length);
        }
    }

    static final class Writer {
        private final Buffer out;

        Writer(Buffer out) {
            this.out = out;
        }

        void writeHeaders(List<Header> headerBlock) throws IOException {
            int size = headerBlock.size();
            for (int i = 0; i < size; i++) {
                ByteString name = ((Header) headerBlock.get(i)).name.toAsciiLowercase();
                Integer staticIndex = (Integer) HpackDraft07.NAME_TO_FIRST_INDEX.get(name);
                if (staticIndex != null) {
                    writeInt(staticIndex.intValue() + 1, HpackDraft07.PREFIX_4_BITS, 0);
                    writeByteString(((Header) headerBlock.get(i)).value);
                } else {
                    this.out.writeByte(0);
                    writeByteString(name);
                    writeByteString(((Header) headerBlock.get(i)).value);
                }
            }
        }

        void writeInt(int value, int prefixMask, int bits) throws IOException {
            if (value < prefixMask) {
                this.out.writeByte(bits | value);
                return;
            }
            this.out.writeByte(bits | prefixMask);
            value -= prefixMask;
            while (value >= TransportMediator.FLAG_KEY_MEDIA_NEXT) {
                this.out.writeByte((value & HpackDraft07.PREFIX_7_BITS) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
                value >>>= 7;
            }
            this.out.writeByte(value);
        }

        void writeByteString(ByteString data) throws IOException {
            writeInt(data.size(), HpackDraft07.PREFIX_7_BITS, 0);
            this.out.write(data);
        }
    }

    static {
        STATIC_HEADER_TABLE = new Header[]{new Header(Header.TARGET_AUTHORITY, UnsupportedUrlFragment.DISPLAY_NAME), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, DeviceFragment.REGEX_BASE), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", UnsupportedUrlFragment.DISPLAY_NAME), new Header("accept-encoding", UnsupportedUrlFragment.DISPLAY_NAME), new Header("accept-language", UnsupportedUrlFragment.DISPLAY_NAME), new Header("accept-ranges", UnsupportedUrlFragment.DISPLAY_NAME), new Header("accept", UnsupportedUrlFragment.DISPLAY_NAME), new Header("access-control-allow-origin", UnsupportedUrlFragment.DISPLAY_NAME), new Header("age", UnsupportedUrlFragment.DISPLAY_NAME), new Header("allow", UnsupportedUrlFragment.DISPLAY_NAME), new Header("authorization", UnsupportedUrlFragment.DISPLAY_NAME), new Header("cache-control", UnsupportedUrlFragment.DISPLAY_NAME), new Header("content-disposition", UnsupportedUrlFragment.DISPLAY_NAME), new Header("content-encoding", UnsupportedUrlFragment.DISPLAY_NAME), new Header("content-language", UnsupportedUrlFragment.DISPLAY_NAME), new Header("content-length", UnsupportedUrlFragment.DISPLAY_NAME), new Header("content-location", UnsupportedUrlFragment.DISPLAY_NAME), new Header("content-range", UnsupportedUrlFragment.DISPLAY_NAME), new Header("content-type", UnsupportedUrlFragment.DISPLAY_NAME), new Header("cookie", UnsupportedUrlFragment.DISPLAY_NAME), new Header("date", UnsupportedUrlFragment.DISPLAY_NAME), new Header("etag", UnsupportedUrlFragment.DISPLAY_NAME), new Header("expect", UnsupportedUrlFragment.DISPLAY_NAME), new Header("expires", UnsupportedUrlFragment.DISPLAY_NAME), new Header("from", UnsupportedUrlFragment.DISPLAY_NAME), new Header("host", UnsupportedUrlFragment.DISPLAY_NAME), new Header("if-match", UnsupportedUrlFragment.DISPLAY_NAME), new Header("if-modified-since", UnsupportedUrlFragment.DISPLAY_NAME), new Header("if-none-match", UnsupportedUrlFragment.DISPLAY_NAME), new Header("if-range", UnsupportedUrlFragment.DISPLAY_NAME), new Header("if-unmodified-since", UnsupportedUrlFragment.DISPLAY_NAME), new Header("last-modified", UnsupportedUrlFragment.DISPLAY_NAME), new Header("link", UnsupportedUrlFragment.DISPLAY_NAME), new Header("location", UnsupportedUrlFragment.DISPLAY_NAME), new Header("max-forwards", UnsupportedUrlFragment.DISPLAY_NAME), new Header("proxy-authenticate", UnsupportedUrlFragment.DISPLAY_NAME), new Header("proxy-authorization", UnsupportedUrlFragment.DISPLAY_NAME), new Header("range", UnsupportedUrlFragment.DISPLAY_NAME), new Header("referer", UnsupportedUrlFragment.DISPLAY_NAME), new Header("refresh", UnsupportedUrlFragment.DISPLAY_NAME), new Header("retry-after", UnsupportedUrlFragment.DISPLAY_NAME), new Header("server", UnsupportedUrlFragment.DISPLAY_NAME), new Header("set-cookie", UnsupportedUrlFragment.DISPLAY_NAME), new Header("strict-transport-security", UnsupportedUrlFragment.DISPLAY_NAME), new Header("transfer-encoding", UnsupportedUrlFragment.DISPLAY_NAME), new Header("user-agent", UnsupportedUrlFragment.DISPLAY_NAME), new Header("vary", UnsupportedUrlFragment.DISPLAY_NAME), new Header("via", UnsupportedUrlFragment.DISPLAY_NAME), new Header("www-authenticate", UnsupportedUrlFragment.DISPLAY_NAME)};
        NAME_TO_FIRST_INDEX = nameToFirstIndex();
    }

    private HpackDraft07() {
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        Map<ByteString, Integer> result = new LinkedHashMap(STATIC_HEADER_TABLE.length);
        for (int i = 0; i < STATIC_HEADER_TABLE.length; i++) {
            if (!result.containsKey(STATIC_HEADER_TABLE[i].name)) {
                result.put(STATIC_HEADER_TABLE[i].name, Integer.valueOf(i));
            }
        }
        return Collections.unmodifiableMap(result);
    }

    private static ByteString checkLowercase(ByteString name) throws IOException {
        int i = 0;
        int length = name.size();
        while (i < length) {
            byte c = name.getByte(i);
            if (c < 65 || c > 90) {
                i++;
            } else {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + name.utf8());
            }
        }
        return name;
    }
}
