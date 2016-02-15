/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public final class UTF8Writer
extends Writer {
    static final int SURR1_FIRST = 55296;
    static final int SURR1_LAST = 56319;
    static final int SURR2_FIRST = 56320;
    static final int SURR2_LAST = 57343;
    private final IOContext _context;
    private OutputStream _out;
    private byte[] _outBuffer;
    private final int _outBufferEnd;
    private int _outPtr;
    private int _surrogate = 0;

    public UTF8Writer(IOContext iOContext, OutputStream outputStream) {
        this._context = iOContext;
        this._out = outputStream;
        this._outBuffer = iOContext.allocWriteEncodingBuffer();
        this._outBufferEnd = this._outBuffer.length - 4;
        this._outPtr = 0;
    }

    protected static void illegalSurrogate(int n2) throws IOException {
        throw new IOException(UTF8Writer.illegalSurrogateDesc(n2));
    }

    protected static String illegalSurrogateDesc(int n2) {
        if (n2 > 1114111) {
            return "Illegal character point (0x" + Integer.toHexString(n2) + ") to output; max is 0x10FFFF as per RFC 4627";
        }
        if (n2 >= 55296) {
            if (n2 <= 56319) {
                return "Unmatched first part of surrogate pair (0x" + Integer.toHexString(n2) + ")";
            }
            return "Unmatched second part of surrogate pair (0x" + Integer.toHexString(n2) + ")";
        }
        return "Illegal character point (0x" + Integer.toHexString(n2) + ") to output";
    }

    @Override
    public Writer append(char c2) throws IOException {
        this.write(c2);
        return this;
    }

    @Override
    public void close() throws IOException {
        if (this._out != null) {
            if (this._outPtr > 0) {
                this._out.write(this._outBuffer, 0, this._outPtr);
                this._outPtr = 0;
            }
            OutputStream outputStream = this._out;
            this._out = null;
            byte[] arrby = this._outBuffer;
            if (arrby != null) {
                this._outBuffer = null;
                this._context.releaseWriteEncodingBuffer(arrby);
            }
            outputStream.close();
            int n2 = this._surrogate;
            this._surrogate = 0;
            if (n2 > 0) {
                UTF8Writer.illegalSurrogate(n2);
            }
        }
    }

    protected int convertSurrogate(int n2) throws IOException {
        int n3 = this._surrogate;
        this._surrogate = 0;
        if (n2 < 56320 || n2 > 57343) {
            throw new IOException("Broken surrogate pair: first char 0x" + Integer.toHexString(n3) + ", second 0x" + Integer.toHexString(n2) + "; illegal combination");
        }
        return 65536 + (n3 - 55296 << 10) + (n2 - 56320);
    }

    @Override
    public void flush() throws IOException {
        if (this._out != null) {
            if (this._outPtr > 0) {
                this._out.write(this._outBuffer, 0, this._outPtr);
                this._outPtr = 0;
            }
            this._out.flush();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(int n2) throws IOException {
        int n3;
        if (this._surrogate > 0) {
            n3 = this.convertSurrogate(n2);
        } else {
            n3 = n2;
            if (n2 >= 55296) {
                n3 = n2;
                if (n2 <= 57343) {
                    if (n2 > 56319) {
                        UTF8Writer.illegalSurrogate(n2);
                    }
                    this._surrogate = n2;
                    return;
                }
            }
        }
        if (this._outPtr >= this._outBufferEnd) {
            this._out.write(this._outBuffer, 0, this._outPtr);
            this._outPtr = 0;
        }
        if (n3 < 128) {
            byte[] arrby = this._outBuffer;
            n2 = this._outPtr;
            this._outPtr = n2 + 1;
            arrby[n2] = (byte)n3;
            return;
        }
        n2 = this._outPtr;
        if (n3 < 2048) {
            byte[] arrby = this._outBuffer;
            int n4 = n2 + 1;
            arrby[n2] = (byte)(n3 >> 6 | 192);
            arrby = this._outBuffer;
            n2 = n4 + 1;
            arrby[n4] = (byte)(n3 & 63 | 128);
        } else if (n3 <= 65535) {
            byte[] arrby = this._outBuffer;
            int n5 = n2 + 1;
            arrby[n2] = (byte)(n3 >> 12 | 224);
            arrby = this._outBuffer;
            n2 = n5 + 1;
            arrby[n5] = (byte)(n3 >> 6 & 63 | 128);
            this._outBuffer[n2] = (byte)(n3 & 63 | 128);
            ++n2;
        } else {
            if (n3 > 1114111) {
                UTF8Writer.illegalSurrogate(n3);
            }
            byte[] arrby = this._outBuffer;
            int n6 = n2 + 1;
            arrby[n2] = (byte)(n3 >> 18 | 240);
            arrby = this._outBuffer;
            n2 = n6 + 1;
            arrby[n6] = (byte)(n3 >> 12 & 63 | 128);
            arrby = this._outBuffer;
            n6 = n2 + 1;
            arrby[n2] = (byte)(n3 >> 6 & 63 | 128);
            arrby = this._outBuffer;
            n2 = n6 + 1;
            arrby[n6] = (byte)(n3 & 63 | 128);
        }
        this._outPtr = n2;
    }

    @Override
    public void write(String string2) throws IOException {
        this.write(string2, 0, string2.length());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void write(String var1_1, int var2_2, int var3_3) throws IOException {
        if (var3_3 < 2) {
            if (var3_3 != 1) return;
            this.write(var1_1.charAt(var2_2));
            return;
        }
        var4_4 = var2_2;
        var5_5 = var3_3;
        if (this._surrogate > 0) {
            var4_4 = var1_1.charAt(var2_2);
            var5_5 = var3_3 - 1;
            this.write(this.convertSurrogate(var4_4));
            var4_4 = var2_2 + 1;
        }
        var2_2 = this._outPtr;
        var10_6 = this._outBuffer;
        var8_7 = this._outBufferEnd;
        var9_8 = var5_5 + var4_4;
        var3_3 = var4_4;
        block0 : while (var3_3 < var9_8) {
            var4_4 = var2_2;
            if (var2_2 >= var8_7) {
                this._out.write(var10_6, 0, var2_2);
                var4_4 = 0;
            }
            var5_5 = var3_3 + 1;
            var6_9 = var1_1.charAt(var3_3);
            if (var6_9 < 128) ** GOTO lbl29
            var3_3 = var4_4;
            var2_2 = var5_5;
            var4_4 = var6_9;
            ** GOTO lbl44
lbl29: // 1 sources:
            var2_2 = var4_4 + 1;
            var10_6[var4_4] = (byte)var6_9;
            var3_3 = var9_8 - var5_5;
            var6_9 = var8_7 - var2_2;
            var4_4 = var3_3;
            if (var3_3 > var6_9) {
                var4_4 = var6_9;
            }
            var3_3 = var5_5;
            while (var3_3 < var4_4 + var5_5) {
                var6_9 = var3_3 + 1;
                var7_10 = var1_1.charAt(var3_3);
                if (var7_10 >= '') {
                    var3_3 = var2_2;
                    var2_2 = var6_9;
                    var4_4 = var7_10;
lbl44: // 2 sources:
                    if (var4_4 < 2048) {
                        var5_5 = var3_3 + 1;
                        var10_6[var3_3] = (byte)(var4_4 >> 6 | 192);
                        var10_6[var5_5] = (byte)(var4_4 & 63 | 128);
                        var4_4 = var5_5 + 1;
                        var3_3 = var2_2;
                        var2_2 = var4_4;
                        continue block0;
                    }
                } else {
                    var10_6[var2_2] = (byte)var7_10;
                    ++var2_2;
                    var3_3 = var6_9;
                    continue;
                }
                if (var4_4 < 55296 || var4_4 > 57343) {
                    var5_5 = var3_3 + 1;
                    var10_6[var3_3] = (byte)(var4_4 >> 12 | 224);
                    var3_3 = var5_5 + 1;
                    var10_6[var5_5] = (byte)(var4_4 >> 6 & 63 | 128);
                    var5_5 = var3_3 + 1;
                    var10_6[var3_3] = (byte)(var4_4 & 63 | 128);
                    var3_3 = var2_2;
                    var2_2 = var5_5;
                    continue block0;
                }
                if (var4_4 > 56319) {
                    this._outPtr = var3_3;
                    UTF8Writer.illegalSurrogate(var4_4);
                }
                this._surrogate = var4_4;
                if (var2_2 >= var9_8) {
                    var2_2 = var3_3;
                    break block0;
                }
                var4_4 = var2_2 + 1;
                if ((var2_2 = this.convertSurrogate(var1_1.charAt(var2_2))) > 1114111) {
                    this._outPtr = var3_3;
                    UTF8Writer.illegalSurrogate(var2_2);
                }
                var5_5 = var3_3 + 1;
                var10_6[var3_3] = (byte)(var2_2 >> 18 | 240);
                var3_3 = var5_5 + 1;
                var10_6[var5_5] = (byte)(var2_2 >> 12 & 63 | 128);
                var5_5 = var3_3 + 1;
                var10_6[var3_3] = (byte)(var2_2 >> 6 & 63 | 128);
                var10_6[var5_5] = (byte)(var2_2 & 63 | 128);
                var2_2 = var5_5 + 1;
                var3_3 = var4_4;
                continue block0;
            }
        }
        this._outPtr = var2_2;
    }

    @Override
    public void write(char[] arrc) throws IOException {
        this.write(arrc, 0, arrc.length);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void write(char[] var1_1, int var2_2, int var3_3) throws IOException {
        if (var3_3 < 2) {
            if (var3_3 != 1) return;
            this.write(var1_1[var2_2]);
            return;
        }
        var4_4 = var2_2;
        var5_5 = var3_3;
        if (this._surrogate > 0) {
            var4_4 = var1_1[var2_2];
            var5_5 = var3_3 - 1;
            this.write(this.convertSurrogate(var4_4));
            var4_4 = var2_2 + 1;
        }
        var2_2 = this._outPtr;
        var10_6 = this._outBuffer;
        var8_7 = this._outBufferEnd;
        var9_8 = var5_5 + var4_4;
        var3_3 = var4_4;
        block0 : while (var3_3 < var9_8) {
            var4_4 = var2_2;
            if (var2_2 >= var8_7) {
                this._out.write(var10_6, 0, var2_2);
                var4_4 = 0;
            }
            var5_5 = var3_3 + 1;
            var6_9 = var1_1[var3_3];
            if (var6_9 < 128) ** GOTO lbl29
            var3_3 = var4_4;
            var2_2 = var5_5;
            var4_4 = var6_9;
            ** GOTO lbl44
lbl29: // 1 sources:
            var2_2 = var4_4 + 1;
            var10_6[var4_4] = (byte)var6_9;
            var3_3 = var9_8 - var5_5;
            var6_9 = var8_7 - var2_2;
            var4_4 = var3_3;
            if (var3_3 > var6_9) {
                var4_4 = var6_9;
            }
            var3_3 = var5_5;
            while (var3_3 < var4_4 + var5_5) {
                var6_9 = var3_3 + 1;
                var7_10 = var1_1[var3_3];
                if (var7_10 >= 128) {
                    var3_3 = var2_2;
                    var2_2 = var6_9;
                    var4_4 = var7_10;
lbl44: // 2 sources:
                    if (var4_4 < 2048) {
                        var5_5 = var3_3 + 1;
                        var10_6[var3_3] = (byte)(var4_4 >> 6 | 192);
                        var10_6[var5_5] = (byte)(var4_4 & 63 | 128);
                        var4_4 = var5_5 + 1;
                        var3_3 = var2_2;
                        var2_2 = var4_4;
                        continue block0;
                    }
                } else {
                    var10_6[var2_2] = (byte)var7_10;
                    ++var2_2;
                    var3_3 = var6_9;
                    continue;
                }
                if (var4_4 < 55296 || var4_4 > 57343) {
                    var5_5 = var3_3 + 1;
                    var10_6[var3_3] = (byte)(var4_4 >> 12 | 224);
                    var3_3 = var5_5 + 1;
                    var10_6[var5_5] = (byte)(var4_4 >> 6 & 63 | 128);
                    var5_5 = var3_3 + 1;
                    var10_6[var3_3] = (byte)(var4_4 & 63 | 128);
                    var3_3 = var2_2;
                    var2_2 = var5_5;
                    continue block0;
                }
                if (var4_4 > 56319) {
                    this._outPtr = var3_3;
                    UTF8Writer.illegalSurrogate(var4_4);
                }
                this._surrogate = var4_4;
                if (var2_2 >= var9_8) {
                    var2_2 = var3_3;
                    break block0;
                }
                var4_4 = var2_2 + 1;
                if ((var2_2 = this.convertSurrogate(var1_1[var2_2])) > 1114111) {
                    this._outPtr = var3_3;
                    UTF8Writer.illegalSurrogate(var2_2);
                }
                var5_5 = var3_3 + 1;
                var10_6[var3_3] = (byte)(var2_2 >> 18 | 240);
                var3_3 = var5_5 + 1;
                var10_6[var5_5] = (byte)(var2_2 >> 12 & 63 | 128);
                var5_5 = var3_3 + 1;
                var10_6[var3_3] = (byte)(var2_2 >> 6 & 63 | 128);
                var10_6[var5_5] = (byte)(var2_2 & 63 | 128);
                var2_2 = var5_5 + 1;
                var3_3 = var4_4;
                continue block0;
            }
        }
        this._outPtr = var2_2;
    }
}

