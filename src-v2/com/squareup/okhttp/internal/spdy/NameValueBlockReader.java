/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.Spdy3;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSource;
import okio.InflaterSource;
import okio.Okio;
import okio.Source;

class NameValueBlockReader {
    private int compressedLimit;
    private final InflaterSource inflaterSource;
    private final BufferedSource source;

    public NameValueBlockReader(BufferedSource bufferedSource) {
        this.inflaterSource = new InflaterSource(new ForwardingSource(bufferedSource){

            @Override
            public long read(Buffer buffer, long l2) throws IOException {
                if (NameValueBlockReader.this.compressedLimit == 0) {
                    return -1;
                }
                if ((l2 = super.read(buffer, Math.min(l2, (long)NameValueBlockReader.this.compressedLimit))) == -1) {
                    return -1;
                }
                NameValueBlockReader.access$022(NameValueBlockReader.this, l2);
                return l2;
            }
        }, new Inflater(){

            @Override
            public int inflate(byte[] arrby, int n2, int n3) throws DataFormatException {
                int n4;
                int n5 = n4 = super.inflate(arrby, n2, n3);
                if (n4 == 0) {
                    n5 = n4;
                    if (this.needsDictionary()) {
                        this.setDictionary(Spdy3.DICTIONARY);
                        n5 = super.inflate(arrby, n2, n3);
                    }
                }
                return n5;
            }
        });
        this.source = Okio.buffer(this.inflaterSource);
    }

    static /* synthetic */ int access$022(NameValueBlockReader nameValueBlockReader, long l2) {
        int n2;
        nameValueBlockReader.compressedLimit = n2 = (int)((long)nameValueBlockReader.compressedLimit - l2);
        return n2;
    }

    private void doneReading() throws IOException {
        if (this.compressedLimit > 0) {
            this.inflaterSource.refill();
            if (this.compressedLimit != 0) {
                throw new IOException("compressedLimit > 0: " + this.compressedLimit);
            }
        }
    }

    private ByteString readByteString() throws IOException {
        int n2 = this.source.readInt();
        return this.source.readByteString(n2);
    }

    public void close() throws IOException {
        this.source.close();
    }

    public List<Header> readNameValueBlock(int n2) throws IOException {
        this.compressedLimit += n2;
        int n3 = this.source.readInt();
        if (n3 < 0) {
            throw new IOException("numberOfPairs < 0: " + n3);
        }
        if (n3 > 1024) {
            throw new IOException("numberOfPairs > 1024: " + n3);
        }
        ArrayList<Header> arrayList = new ArrayList<Header>(n3);
        for (n2 = 0; n2 < n3; ++n2) {
            ByteString byteString = this.readByteString().toAsciiLowercase();
            ByteString byteString2 = this.readByteString();
            if (byteString.size() == 0) {
                throw new IOException("name.size == 0");
            }
            arrayList.add(new Header(byteString, byteString2));
        }
        this.doneReading();
        return arrayList;
    }

}

