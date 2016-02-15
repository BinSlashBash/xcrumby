/*
 * Decompiled with CFR 0_110.
 */
package com.google.gdata.util.common.base;

import com.google.gdata.util.common.base.Escaper;
import com.google.gdata.util.common.base.Preconditions;
import java.io.IOException;

public abstract class UnicodeEscaper
implements Escaper {
    private static final int DEST_PAD = 32;
    private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal<char[]>(){

        @Override
        protected char[] initialValue() {
            return new char[1024];
        }
    };

    protected static final int codePointAt(CharSequence charSequence, int n2, int n3) {
        if (n2 < n3) {
            int n4 = n2 + 1;
            char c2 = charSequence.charAt(n2);
            if (c2 < '\ud800' || c2 > '\udfff') {
                return c2;
            }
            if (c2 <= '\udbff') {
                if (n4 == n3) {
                    return - c2;
                }
                char c3 = charSequence.charAt(n4);
                if (Character.isLowSurrogate(c3)) {
                    return Character.toCodePoint(c2, c3);
                }
                throw new IllegalArgumentException("Expected low surrogate but got char '" + c3 + "' with value " + c3 + " at index " + n4);
            }
            throw new IllegalArgumentException("Unexpected low surrogate character '" + c2 + "' with value " + c2 + " at index " + (n4 - 1));
        }
        throw new IndexOutOfBoundsException("Index exceeds specified range");
    }

    private static final char[] growBuffer(char[] arrc, int n2, int n3) {
        char[] arrc2 = new char[n3];
        if (n2 > 0) {
            System.arraycopy(arrc, 0, arrc2, 0, n2);
        }
        return arrc2;
    }

    @Override
    public Appendable escape(final Appendable appendable) {
        Preconditions.checkNotNull(appendable);
        return new Appendable(){
            char[] decodedChars;
            int pendingHighSurrogate;

            private void outputChars(char[] arrc, int n2) throws IOException {
                for (int i2 = 0; i2 < n2; ++i2) {
                    appendable.append(arrc[i2]);
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public Appendable append(char c2) throws IOException {
                if (this.pendingHighSurrogate != -1) {
                    if (!Character.isLowSurrogate(c2)) {
                        throw new IllegalArgumentException("Expected low surrogate character but got '" + c2 + "' with value " + c2);
                    }
                    char[] arrc = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c2));
                    if (arrc != null) {
                        this.outputChars(arrc, arrc.length);
                    } else {
                        appendable.append((char)this.pendingHighSurrogate);
                        appendable.append(c2);
                    }
                    this.pendingHighSurrogate = -1;
                    return this;
                }
                if (Character.isHighSurrogate(c2)) {
                    this.pendingHighSurrogate = c2;
                    return this;
                }
                if (Character.isLowSurrogate(c2)) {
                    throw new IllegalArgumentException("Unexpected low surrogate character '" + c2 + "' with value " + c2);
                }
                char[] arrc = UnicodeEscaper.this.escape(c2);
                if (arrc != null) {
                    this.outputChars(arrc, arrc.length);
                    return this;
                }
                appendable.append(c2);
                return this;
            }

            @Override
            public Appendable append(CharSequence charSequence) throws IOException {
                return this.append(charSequence, 0, charSequence.length());
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public Appendable append(CharSequence charSequence, int n2, int n3) throws IOException {
                if (n2 < n3) {
                    char[] arrc;
                    int n4 = n2;
                    int n5 = n2;
                    int n6 = n4;
                    if (this.pendingHighSurrogate != -1) {
                        char c2 = charSequence.charAt(n2);
                        if (!Character.isLowSurrogate(c2)) {
                            throw new IllegalArgumentException("Expected low surrogate character but got " + c2);
                        }
                        arrc = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c2));
                        if (arrc != null) {
                            this.outputChars(arrc, arrc.length);
                            n6 = n4 + 1;
                        } else {
                            appendable.append((char)this.pendingHighSurrogate);
                            n6 = n4;
                        }
                        this.pendingHighSurrogate = -1;
                        n5 = n2 + 1;
                    }
                    do {
                        if ((n5 = UnicodeEscaper.this.nextEscapeIndex(charSequence, n5, n3)) > n6) {
                            appendable.append(charSequence, n6, n5);
                        }
                        if (n5 == n3) break;
                        n2 = UnicodeEscaper.codePointAt(charSequence, n5, n3);
                        if (n2 < 0) {
                            this.pendingHighSurrogate = - n2;
                            return this;
                        }
                        arrc = UnicodeEscaper.this.escape(n2);
                        if (arrc != null) {
                            this.outputChars(arrc, arrc.length);
                        } else {
                            n6 = Character.toChars(n2, this.decodedChars, 0);
                            this.outputChars(this.decodedChars, n6);
                        }
                        n2 = Character.isSupplementaryCodePoint(n2) ? 2 : 1;
                        n6 = n5 += n2;
                    } while (true);
                }
                return this;
            }
        };
    }

    @Override
    public String escape(String string2) {
        int n2 = string2.length();
        int n3 = this.nextEscapeIndex(string2, 0, n2);
        if (n3 == n2) {
            return string2;
        }
        return this.escapeSlow(string2, n3);
    }

    protected abstract char[] escape(int var1);

    /*
     * Enabled aggressive block sorting
     */
    protected final String escapeSlow(String string2, int n2) {
        char[] arrc;
        int n3;
        int n4 = string2.length();
        char[] arrc2 = DEST_TL.get();
        int n5 = 0;
        int n6 = 0;
        int n7 = n2;
        n2 = n5;
        while (n7 < n4) {
            int n8 = UnicodeEscaper.codePointAt(string2, n7, n4);
            if (n8 < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] arrc3 = this.escape(n8);
            char[] arrc4 = arrc2;
            n5 = n2;
            if (arrc3 != null) {
                n5 = n7 - n6;
                n3 = n2 + n5 + arrc3.length;
                arrc = arrc2;
                if (arrc2.length < n3) {
                    arrc = UnicodeEscaper.growBuffer(arrc2, n2, n4 - n7 + n3 + 32);
                }
                n3 = n2;
                if (n5 > 0) {
                    string2.getChars(n6, n7, arrc, n2);
                    n3 = n2 + n5;
                }
                arrc4 = arrc;
                n5 = n3;
                if (arrc3.length > 0) {
                    System.arraycopy(arrc3, 0, arrc, n3, arrc3.length);
                    n5 = n3 + arrc3.length;
                    arrc4 = arrc;
                }
            }
            n2 = Character.isSupplementaryCodePoint(n8) ? 2 : 1;
            n6 = n7 + n2;
            n7 = this.nextEscapeIndex(string2, n6, n4);
            arrc2 = arrc4;
            n2 = n5;
        }
        n3 = n4 - n6;
        arrc = arrc2;
        n5 = n2;
        if (n3 > 0) {
            n5 = n2 + n3;
            arrc = arrc2;
            if (arrc2.length < n5) {
                arrc = UnicodeEscaper.growBuffer(arrc2, n2, n5);
            }
            string2.getChars(n6, n4, arrc, n2);
        }
        return new String(arrc, 0, n5);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int nextEscapeIndex(CharSequence charSequence, int n2, int n3) {
        int n4;
        while (n2 < n3 && (n4 = UnicodeEscaper.codePointAt(charSequence, n2, n3)) >= 0 && this.escape(n4) == null) {
            n4 = Character.isSupplementaryCodePoint(n4) ? 2 : 1;
            n2 += n4;
        }
        return n2;
    }

}

