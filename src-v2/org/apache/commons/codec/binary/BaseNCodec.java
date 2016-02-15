/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import java.util.Arrays;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;

public abstract class BaseNCodec
implements BinaryEncoder,
BinaryDecoder {
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    static final int EOF = -1;
    protected static final int MASK_8BITS = 255;
    public static final int MIME_CHUNK_SIZE = 76;
    protected static final byte PAD_DEFAULT = 61;
    public static final int PEM_CHUNK_SIZE = 64;
    protected final byte PAD;
    private final int chunkSeparatorLength;
    private final int encodedBlockSize;
    protected final int lineLength;
    private final int unencodedBlockSize;

    /*
     * Enabled aggressive block sorting
     */
    protected BaseNCodec(int n2, int n3, int n4, int n5) {
        int n6 = 0;
        this.PAD = 61;
        this.unencodedBlockSize = n2;
        this.encodedBlockSize = n3;
        n2 = n4 > 0 && n5 > 0 ? 1 : 0;
        if (n2 != 0) {
            n6 = n4 / n3 * n3;
        }
        this.lineLength = n6;
        this.chunkSeparatorLength = n5;
    }

    protected static boolean isWhiteSpace(byte by2) {
        switch (by2) {
            default: {
                return false;
            }
            case 9: 
            case 10: 
            case 13: 
            case 32: 
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private byte[] resizeBuffer(Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[this.getDefaultBufferSize()];
            context.pos = 0;
            context.readPos = 0;
            do {
                return context.buffer;
                break;
            } while (true);
        }
        byte[] arrby = new byte[context.buffer.length * 2];
        System.arraycopy(context.buffer, 0, arrby, 0, context.buffer.length);
        context.buffer = arrby;
        return context.buffer;
    }

    int available(Context context) {
        if (context.buffer != null) {
            return context.pos - context.readPos;
        }
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean containsAlphabetOrPad(byte[] arrby) {
        if (arrby == null) {
            return false;
        }
        int n2 = arrby.length;
        int n3 = 0;
        while (n3 < n2) {
            byte by2 = arrby[n3];
            if (61 == by2 || this.isInAlphabet(by2)) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    @Override
    public Object decode(Object object) throws DecoderException {
        if (object instanceof byte[]) {
            return this.decode((byte[])object);
        }
        if (object instanceof String) {
            return this.decode((String)object);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    abstract void decode(byte[] var1, int var2, int var3, Context var4);

    public byte[] decode(String string2) {
        return this.decode(StringUtils.getBytesUtf8(string2));
    }

    @Override
    public byte[] decode(byte[] arrby) {
        if (arrby == null || arrby.length == 0) {
            return arrby;
        }
        Context context = new Context();
        this.decode(arrby, 0, arrby.length, context);
        this.decode(arrby, 0, -1, context);
        arrby = new byte[context.pos];
        this.readResults(arrby, 0, arrby.length, context);
        return arrby;
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof byte[])) {
            throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
        }
        return this.encode((byte[])object);
    }

    abstract void encode(byte[] var1, int var2, int var3, Context var4);

    @Override
    public byte[] encode(byte[] arrby) {
        if (arrby == null || arrby.length == 0) {
            return arrby;
        }
        Context context = new Context();
        this.encode(arrby, 0, arrby.length, context);
        this.encode(arrby, 0, -1, context);
        arrby = new byte[context.pos - context.readPos];
        this.readResults(arrby, 0, arrby.length, context);
        return arrby;
    }

    public String encodeAsString(byte[] arrby) {
        return StringUtils.newStringUtf8(this.encode(arrby));
    }

    public String encodeToString(byte[] arrby) {
        return StringUtils.newStringUtf8(this.encode(arrby));
    }

    protected byte[] ensureBufferSize(int n2, Context context) {
        if (context.buffer == null || context.buffer.length < context.pos + n2) {
            return this.resizeBuffer(context);
        }
        return context.buffer;
    }

    protected int getDefaultBufferSize() {
        return 8192;
    }

    public long getEncodedLength(byte[] arrby) {
        long l2;
        long l3 = l2 = (long)((arrby.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize) * (long)this.encodedBlockSize;
        if (this.lineLength > 0) {
            l3 = l2 + ((long)this.lineLength + l2 - 1) / (long)this.lineLength * (long)this.chunkSeparatorLength;
        }
        return l3;
    }

    boolean hasData(Context context) {
        if (context.buffer != null) {
            return true;
        }
        return false;
    }

    protected abstract boolean isInAlphabet(byte var1);

    public boolean isInAlphabet(String string2) {
        return this.isInAlphabet(StringUtils.getBytesUtf8(string2), true);
    }

    public boolean isInAlphabet(byte[] arrby, boolean bl2) {
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            if (this.isInAlphabet(arrby[i2]) || bl2 && (arrby[i2] == 61 || BaseNCodec.isWhiteSpace(arrby[i2]))) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int readResults(byte[] arrby, int n2, int n3, Context context) {
        if (context.buffer != null) {
            n3 = Math.min(this.available(context), n3);
            System.arraycopy(context.buffer, context.readPos, arrby, n2, n3);
            context.readPos += n3;
            if (context.readPos < context.pos) return n3;
            context.buffer = null;
            return n3;
        }
        if (!context.eof) return 0;
        return -1;
    }

    static class Context {
        byte[] buffer;
        int currentLinePos;
        boolean eof;
        int ibitWorkArea;
        long lbitWorkArea;
        int modulus;
        int pos;
        int readPos;

        Context() {
        }

        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", this.getClass().getSimpleName(), Arrays.toString(this.buffer), this.currentLinePos, this.eof, this.ibitWorkArea, this.lbitWorkArea, this.modulus, this.pos, this.readPos);
        }
    }

}

