package com.google.gdata.util.common.base;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.IOException;

public abstract class UnicodeEscaper implements Escaper {
    private static final int DEST_PAD = 32;
    private static final ThreadLocal<char[]> DEST_TL;

    /* renamed from: com.google.gdata.util.common.base.UnicodeEscaper.1 */
    class C05701 implements Appendable {
        char[] decodedChars;
        int pendingHighSurrogate;
        final /* synthetic */ Appendable val$out;

        C05701(Appendable appendable) {
            this.val$out = appendable;
            this.pendingHighSurrogate = -1;
            this.decodedChars = new char[2];
        }

        public Appendable append(CharSequence csq) throws IOException {
            return append(csq, 0, csq.length());
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Appendable append(java.lang.CharSequence r11, int r12, int r13) throws java.io.IOException {
            /*
            r10 = this;
            r9 = -1;
            r3 = r12;
            if (r3 >= r13) goto L_0x0055;
        L_0x0004:
            r6 = r3;
            r7 = r10.pendingHighSurrogate;
            if (r7 == r9) goto L_0x0046;
        L_0x0009:
            r4 = r3 + 1;
            r0 = r11.charAt(r3);
            r7 = java.lang.Character.isLowSurrogate(r0);
            if (r7 != 0) goto L_0x002e;
        L_0x0015:
            r7 = new java.lang.IllegalArgumentException;
            r8 = new java.lang.StringBuilder;
            r8.<init>();
            r9 = "Expected low surrogate character but got ";
            r8 = r8.append(r9);
            r8 = r8.append(r0);
            r8 = r8.toString();
            r7.<init>(r8);
            throw r7;
        L_0x002e:
            r7 = com.google.gdata.util.common.base.UnicodeEscaper.this;
            r8 = r10.pendingHighSurrogate;
            r8 = (char) r8;
            r8 = java.lang.Character.toCodePoint(r8, r0);
            r2 = r7.escape(r8);
            if (r2 == 0) goto L_0x0056;
        L_0x003d:
            r7 = r2.length;
            r10.outputChars(r2, r7);
            r6 = r6 + 1;
        L_0x0043:
            r10.pendingHighSurrogate = r9;
            r3 = r4;
        L_0x0046:
            r7 = com.google.gdata.util.common.base.UnicodeEscaper.this;
            r3 = r7.nextEscapeIndex(r11, r3, r13);
            if (r3 <= r6) goto L_0x0053;
        L_0x004e:
            r7 = r10.val$out;
            r7.append(r11, r6, r3);
        L_0x0053:
            if (r3 != r13) goto L_0x005f;
        L_0x0055:
            return r10;
        L_0x0056:
            r7 = r10.val$out;
            r8 = r10.pendingHighSurrogate;
            r8 = (char) r8;
            r7.append(r8);
            goto L_0x0043;
        L_0x005f:
            r1 = com.google.gdata.util.common.base.UnicodeEscaper.codePointAt(r11, r3, r13);
            if (r1 >= 0) goto L_0x0069;
        L_0x0065:
            r7 = -r1;
            r10.pendingHighSurrogate = r7;
            goto L_0x0055;
        L_0x0069:
            r7 = com.google.gdata.util.common.base.UnicodeEscaper.this;
            r2 = r7.escape(r1);
            if (r2 == 0) goto L_0x007f;
        L_0x0071:
            r7 = r2.length;
            r10.outputChars(r2, r7);
        L_0x0075:
            r7 = java.lang.Character.isSupplementaryCodePoint(r1);
            if (r7 == 0) goto L_0x008c;
        L_0x007b:
            r7 = 2;
        L_0x007c:
            r3 = r3 + r7;
            r6 = r3;
            goto L_0x0046;
        L_0x007f:
            r7 = r10.decodedChars;
            r8 = 0;
            r5 = java.lang.Character.toChars(r1, r7, r8);
            r7 = r10.decodedChars;
            r10.outputChars(r7, r5);
            goto L_0x0075;
        L_0x008c:
            r7 = 1;
            goto L_0x007c;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gdata.util.common.base.UnicodeEscaper.1.append(java.lang.CharSequence, int, int):java.lang.Appendable");
        }

        public Appendable append(char c) throws IOException {
            char[] escaped;
            if (this.pendingHighSurrogate != -1) {
                if (Character.isLowSurrogate(c)) {
                    escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char) this.pendingHighSurrogate, c));
                    if (escaped != null) {
                        outputChars(escaped, escaped.length);
                    } else {
                        this.val$out.append((char) this.pendingHighSurrogate);
                        this.val$out.append(c);
                    }
                    this.pendingHighSurrogate = -1;
                } else {
                    throw new IllegalArgumentException("Expected low surrogate character but got '" + c + "' with value " + c);
                }
            } else if (Character.isHighSurrogate(c)) {
                this.pendingHighSurrogate = c;
            } else if (Character.isLowSurrogate(c)) {
                throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + c);
            } else {
                escaped = UnicodeEscaper.this.escape((int) c);
                if (escaped != null) {
                    outputChars(escaped, escaped.length);
                } else {
                    this.val$out.append(c);
                }
            }
            return this;
        }

        private void outputChars(char[] chars, int len) throws IOException {
            for (int n = 0; n < len; n++) {
                this.val$out.append(chars[n]);
            }
        }
    }

    /* renamed from: com.google.gdata.util.common.base.UnicodeEscaper.2 */
    static class C05712 extends ThreadLocal<char[]> {
        C05712() {
        }

        protected char[] initialValue() {
            return new char[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        }
    }

    protected abstract char[] escape(int i);

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected int nextEscapeIndex(java.lang.CharSequence r4, int r5, int r6) {
        /*
        r3 = this;
        r1 = r5;
    L_0x0001:
        if (r1 >= r6) goto L_0x000f;
    L_0x0003:
        r0 = codePointAt(r4, r1, r6);
        if (r0 < 0) goto L_0x000f;
    L_0x0009:
        r2 = r3.escape(r0);
        if (r2 == 0) goto L_0x0010;
    L_0x000f:
        return r1;
    L_0x0010:
        r2 = java.lang.Character.isSupplementaryCodePoint(r0);
        if (r2 == 0) goto L_0x0019;
    L_0x0016:
        r2 = 2;
    L_0x0017:
        r1 = r1 + r2;
        goto L_0x0001;
    L_0x0019:
        r2 = 1;
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gdata.util.common.base.UnicodeEscaper.nextEscapeIndex(java.lang.CharSequence, int, int):int");
    }

    public String escape(String string) {
        int end = string.length();
        int index = nextEscapeIndex(string, 0, end);
        return index == end ? string : escapeSlow(string, index);
    }

    protected final String escapeSlow(String s, int index) {
        int charsSkipped;
        int end = s.length();
        char[] dest = (char[]) DEST_TL.get();
        int destIndex = 0;
        int unescapedChunkStart = 0;
        while (index < end) {
            int cp = codePointAt(s, index, end);
            if (cp < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] escaped = escape(cp);
            if (escaped != null) {
                charsSkipped = index - unescapedChunkStart;
                int sizeNeeded = (destIndex + charsSkipped) + escaped.length;
                if (dest.length < sizeNeeded) {
                    dest = growBuffer(dest, destIndex, ((end - index) + sizeNeeded) + DEST_PAD);
                }
                if (charsSkipped > 0) {
                    s.getChars(unescapedChunkStart, index, dest, destIndex);
                    destIndex += charsSkipped;
                }
                if (escaped.length > 0) {
                    System.arraycopy(escaped, 0, dest, destIndex, escaped.length);
                    destIndex += escaped.length;
                }
            }
            unescapedChunkStart = index + (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
            index = nextEscapeIndex(s, unescapedChunkStart, end);
        }
        charsSkipped = end - unescapedChunkStart;
        if (charsSkipped > 0) {
            int endIndex = destIndex + charsSkipped;
            if (dest.length < endIndex) {
                dest = growBuffer(dest, destIndex, endIndex);
            }
            s.getChars(unescapedChunkStart, end, dest, destIndex);
            destIndex = endIndex;
        }
        return new String(dest, 0, destIndex);
    }

    public Appendable escape(Appendable out) {
        Preconditions.checkNotNull(out);
        return new C05701(out);
    }

    protected static final int codePointAt(CharSequence seq, int index, int end) {
        if (index < end) {
            int index2 = index + 1;
            char c1 = seq.charAt(index);
            if (c1 < '\ud800' || c1 > '\udfff') {
                return c1;
            }
            if (c1 > '\udbff') {
                throw new IllegalArgumentException("Unexpected low surrogate character '" + c1 + "' with value " + c1 + " at index " + (index2 - 1));
            } else if (index2 == end) {
                return -c1;
            } else {
                char c2 = seq.charAt(index2);
                if (Character.isLowSurrogate(c2)) {
                    return Character.toCodePoint(c1, c2);
                }
                throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + c2 + " at index " + index2);
            }
        }
        throw new IndexOutOfBoundsException("Index exceeds specified range");
    }

    private static final char[] growBuffer(char[] dest, int index, int size) {
        char[] copy = new char[size];
        if (index > 0) {
            System.arraycopy(dest, 0, copy, 0, index);
        }
        return copy;
    }

    static {
        DEST_TL = new C05712();
    }
}
