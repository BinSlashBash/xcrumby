/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.BitArray;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.Huffman;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    static {
        STATIC_HEADER_TABLE = new Header[]{new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", ""), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
        NAME_TO_FIRST_INDEX = HpackDraft07.nameToFirstIndex();
    }

    private HpackDraft07() {
    }

    private static ByteString checkLowercase(ByteString byteString) throws IOException {
        int n2 = byteString.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            byte by2 = byteString.getByte(i2);
            if (by2 < 65 || by2 > 90) continue;
            throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + byteString.utf8());
        }
        return byteString;
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        LinkedHashMap<ByteString, Integer> linkedHashMap = new LinkedHashMap<ByteString, Integer>(STATIC_HEADER_TABLE.length);
        for (int i2 = 0; i2 < STATIC_HEADER_TABLE.length; ++i2) {
            if (linkedHashMap.containsKey(HpackDraft07.STATIC_HEADER_TABLE[i2].name)) continue;
            linkedHashMap.put(HpackDraft07.STATIC_HEADER_TABLE[i2].name, i2);
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    static final class Reader {
        private final List<Header> emittedHeaders = new ArrayList<Header>();
        BitArray emittedReferencedHeaders = new BitArray.FixedCapacity();
        int headerCount = 0;
        Header[] headerTable = new Header[8];
        int headerTableByteCount = 0;
        private int maxHeaderTableByteCount;
        private int maxHeaderTableByteCountSetting;
        int nextHeaderIndex = this.headerTable.length - 1;
        BitArray referencedHeaders = new BitArray.FixedCapacity();
        private final BufferedSource source;

        Reader(int n2, Source source) {
            this.maxHeaderTableByteCountSetting = n2;
            this.maxHeaderTableByteCount = n2;
            this.source = Okio.buffer(source);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void adjustHeaderTableByteCount() {
            if (this.maxHeaderTableByteCount >= this.headerTableByteCount) return;
            if (this.maxHeaderTableByteCount == 0) {
                this.clearHeaderTable();
                return;
            }
            this.evictToRecoverBytes(this.headerTableByteCount - this.maxHeaderTableByteCount);
        }

        private void clearHeaderTable() {
            this.clearReferenceSet();
            Arrays.fill(this.headerTable, null);
            this.nextHeaderIndex = this.headerTable.length - 1;
            this.headerCount = 0;
            this.headerTableByteCount = 0;
        }

        private void clearReferenceSet() {
            this.referencedHeaders.clear();
            this.emittedReferencedHeaders.clear();
        }

        private int evictToRecoverBytes(int n2) {
            int n3 = 0;
            int n4 = 0;
            if (n2 > 0) {
                int n5 = n2;
                n2 = n4;
                for (n3 = this.headerTable.length - 1; n3 >= this.nextHeaderIndex && n5 > 0; --n3) {
                    n5 -= this.headerTable[n3].hpackSize;
                    this.headerTableByteCount -= this.headerTable[n3].hpackSize;
                    --this.headerCount;
                    ++n2;
                }
                this.referencedHeaders.shiftLeft(n2);
                this.emittedReferencedHeaders.shiftLeft(n2);
                System.arraycopy(this.headerTable, this.nextHeaderIndex + 1, this.headerTable, this.nextHeaderIndex + 1 + n2, this.headerCount);
                this.nextHeaderIndex += n2;
                n3 = n2;
            }
            return n3;
        }

        private ByteString getName(int n2) {
            if (this.isStaticHeader(n2)) {
                return HpackDraft07.access$000()[n2 - this.headerCount].name;
            }
            return this.headerTable[this.headerTableIndex((int)n2)].name;
        }

        private int headerTableIndex(int n2) {
            return this.nextHeaderIndex + 1 + n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void insertIntoHeaderTable(int n2, Header header) {
            int n3;
            int n4 = n3 = header.hpackSize;
            if (n2 != -1) {
                n4 = n3 - this.headerTable[this.headerTableIndex((int)n2)].hpackSize;
            }
            if (n4 > this.maxHeaderTableByteCount) {
                this.clearHeaderTable();
                this.emittedHeaders.add(header);
                return;
            }
            n3 = this.evictToRecoverBytes(this.headerTableByteCount + n4 - this.maxHeaderTableByteCount);
            if (n2 == -1) {
                if (this.headerCount + 1 > this.headerTable.length) {
                    Header[] arrheader = new Header[this.headerTable.length * 2];
                    System.arraycopy(this.headerTable, 0, arrheader, this.headerTable.length, this.headerTable.length);
                    if (arrheader.length == 64) {
                        this.referencedHeaders = ((BitArray.FixedCapacity)this.referencedHeaders).toVariableCapacity();
                        this.emittedReferencedHeaders = ((BitArray.FixedCapacity)this.emittedReferencedHeaders).toVariableCapacity();
                    }
                    this.referencedHeaders.shiftLeft(this.headerTable.length);
                    this.emittedReferencedHeaders.shiftLeft(this.headerTable.length);
                    this.nextHeaderIndex = this.headerTable.length - 1;
                    this.headerTable = arrheader;
                }
                n2 = this.nextHeaderIndex;
                this.nextHeaderIndex = n2 - 1;
                this.referencedHeaders.set(n2);
                this.headerTable[n2] = header;
                ++this.headerCount;
            } else {
                n2 += this.headerTableIndex(n2) + n3;
                this.referencedHeaders.set(n2);
                this.headerTable[n2] = header;
            }
            this.headerTableByteCount += n4;
        }

        private boolean isStaticHeader(int n2) {
            if (n2 >= this.headerCount) {
                return true;
            }
            return false;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & 255;
        }

        private void readIndexedHeader(int n2) throws IOException {
            if (this.isStaticHeader(n2)) {
                if ((n2 -= this.headerCount) > STATIC_HEADER_TABLE.length - 1) {
                    throw new IOException("Header index too large " + (n2 + 1));
                }
                Header header = STATIC_HEADER_TABLE[n2];
                if (this.maxHeaderTableByteCount == 0) {
                    this.emittedHeaders.add(header);
                    return;
                }
                this.insertIntoHeaderTable(-1, header);
                return;
            }
            if (!this.referencedHeaders.get(n2 = this.headerTableIndex(n2))) {
                this.emittedHeaders.add(this.headerTable[n2]);
                this.emittedReferencedHeaders.set(n2);
            }
            this.referencedHeaders.toggle(n2);
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int n2) throws IOException {
            this.insertIntoHeaderTable(-1, new Header(this.getName(n2), this.readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            this.insertIntoHeaderTable(-1, new Header(HpackDraft07.checkLowercase(this.readByteString()), this.readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int n2) throws IOException {
            ByteString byteString = this.getName(n2);
            ByteString byteString2 = this.readByteString();
            this.emittedHeaders.add(new Header(byteString, byteString2));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            ByteString byteString = HpackDraft07.checkLowercase(this.readByteString());
            ByteString byteString2 = this.readByteString();
            this.emittedHeaders.add(new Header(byteString, byteString2));
        }

        void emitReferenceSet() {
            for (int i2 = this.headerTable.length - 1; i2 != this.nextHeaderIndex; --i2) {
                if (!this.referencedHeaders.get(i2) || this.emittedReferencedHeaders.get(i2)) continue;
                this.emittedHeaders.add(this.headerTable[i2]);
            }
        }

        List<Header> getAndReset() {
            ArrayList<Header> arrayList = new ArrayList<Header>(this.emittedHeaders);
            this.emittedHeaders.clear();
            this.emittedReferencedHeaders.clear();
            return arrayList;
        }

        int maxHeaderTableByteCount() {
            return this.maxHeaderTableByteCount;
        }

        void maxHeaderTableByteCountSetting(int n2) {
            this.maxHeaderTableByteCount = this.maxHeaderTableByteCountSetting = n2;
            this.adjustHeaderTableByteCount();
        }

        /*
         * Enabled aggressive block sorting
         */
        ByteString readByteString() throws IOException {
            int n2 = this.readByte();
            boolean bl2 = (n2 & 128) == 128;
            n2 = this.readInt(n2, 127);
            if (bl2) {
                return ByteString.of(Huffman.get().decode(this.source.readByteArray(n2)));
            }
            return this.source.readByteString(n2);
        }

        void readHeaders() throws IOException {
            while (!this.source.exhausted()) {
                int n2 = this.source.readByte() & 255;
                if (n2 == 128) {
                    throw new IOException("index == 0");
                }
                if ((n2 & 128) == 128) {
                    this.readIndexedHeader(this.readInt(n2, 127) - 1);
                    continue;
                }
                if (n2 == 64) {
                    this.readLiteralHeaderWithIncrementalIndexingNewName();
                    continue;
                }
                if ((n2 & 64) == 64) {
                    this.readLiteralHeaderWithIncrementalIndexingIndexedName(this.readInt(n2, 63) - 1);
                    continue;
                }
                if ((n2 & 32) == 32) {
                    if ((n2 & 16) == 16) {
                        if ((n2 & 15) != 0) {
                            throw new IOException("Invalid header table state change " + n2);
                        }
                        this.clearReferenceSet();
                        continue;
                    }
                    this.maxHeaderTableByteCount = this.readInt(n2, 15);
                    if (this.maxHeaderTableByteCount < 0 || this.maxHeaderTableByteCount > this.maxHeaderTableByteCountSetting) {
                        throw new IOException("Invalid header table byte count " + this.maxHeaderTableByteCount);
                    }
                    this.adjustHeaderTableByteCount();
                    continue;
                }
                if (n2 == 16 || n2 == 0) {
                    this.readLiteralHeaderWithoutIndexingNewName();
                    continue;
                }
                this.readLiteralHeaderWithoutIndexingIndexedName(this.readInt(n2, 15) - 1);
            }
        }

        int readInt(int n2, int n3) throws IOException {
            int n4;
            if ((n2 &= n3) < n3) {
                return n2;
            }
            n2 = 0;
            while (((n4 = this.readByte()) & 128) != 0) {
                n3 += (n4 & 127) << n2;
                n2 += 7;
            }
            return n3 + (n4 << n2);
        }
    }

    static final class Writer {
        private final Buffer out;

        Writer(Buffer buffer) {
            this.out = buffer;
        }

        void writeByteString(ByteString byteString) throws IOException {
            this.writeInt(byteString.size(), 127, 0);
            this.out.write(byteString);
        }

        /*
         * Enabled aggressive block sorting
         */
        void writeHeaders(List<Header> list) throws IOException {
            int n2 = 0;
            int n3 = list.size();
            while (n2 < n3) {
                ByteString byteString = list.get((int)n2).name.toAsciiLowercase();
                Integer n4 = (Integer)NAME_TO_FIRST_INDEX.get(byteString);
                if (n4 != null) {
                    this.writeInt(n4 + 1, 15, 0);
                    this.writeByteString(list.get((int)n2).value);
                } else {
                    this.out.writeByte(0);
                    this.writeByteString(byteString);
                    this.writeByteString(list.get((int)n2).value);
                }
                ++n2;
            }
            return;
        }

        void writeInt(int n2, int n3, int n4) throws IOException {
            if (n2 < n3) {
                this.out.writeByte(n4 | n2);
                return;
            }
            this.out.writeByte(n4 | n3);
            n2 -= n3;
            while (n2 >= 128) {
                this.out.writeByte(n2 & 127 | 128);
                n2 >>>= 7;
            }
            this.out.writeByte(n2);
        }
    }

}

