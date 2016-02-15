/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import java.util.Locale;
import org.jsoup.helper.Validate;

class CharacterReader {
    static final char EOF = '\uffff';
    private final char[] input;
    private final int length;
    private int mark = 0;
    private int pos = 0;

    CharacterReader(String string2) {
        Validate.notNull(string2);
        this.input = string2.toCharArray();
        this.length = this.input.length;
    }

    void advance() {
        ++this.pos;
    }

    /*
     * Enabled aggressive block sorting
     */
    char consume() {
        char c2 = this.isEmpty() ? '\uffff' : this.input[this.pos];
        ++this.pos;
        return c2;
    }

    String consumeAsString() {
        char[] arrc = this.input;
        int n2 = this.pos;
        this.pos = n2 + 1;
        return new String(arrc, n2, 1);
    }

    String consumeDigitSequence() {
        char c2;
        int n2 = this.pos;
        while (this.pos < this.length && (c2 = this.input[this.pos]) >= '0' && c2 <= '9') {
            ++this.pos;
        }
        return new String(this.input, n2, this.pos - n2);
    }

    String consumeHexSequence() {
        char c2;
        int n2 = this.pos;
        while (this.pos < this.length && ((c2 = this.input[this.pos]) >= '0' && c2 <= '9' || c2 >= 'A' && c2 <= 'F' || c2 >= 'a' && c2 <= 'f')) {
            ++this.pos;
        }
        return new String(this.input, n2, this.pos - n2);
    }

    String consumeLetterSequence() {
        char c2;
        int n2 = this.pos;
        while (this.pos < this.length && ((c2 = this.input[this.pos]) >= 'A' && c2 <= 'Z' || c2 >= 'a' && c2 <= 'z')) {
            ++this.pos;
        }
        return new String(this.input, n2, this.pos - n2);
    }

    String consumeLetterThenDigitSequence() {
        char c2;
        int n2 = this.pos;
        while (this.pos < this.length && ((c2 = this.input[this.pos]) >= 'A' && c2 <= 'Z' || c2 >= 'a' && c2 <= 'z')) {
            ++this.pos;
        }
        while (!this.isEmpty() && (c2 = this.input[this.pos]) >= '0' && c2 <= '9') {
            ++this.pos;
        }
        return new String(this.input, n2, this.pos - n2);
    }

    String consumeTo(char c2) {
        int n2 = this.nextIndexOf(c2);
        if (n2 != -1) {
            String string2 = new String(this.input, this.pos, n2);
            this.pos += n2;
            return string2;
        }
        return this.consumeToEnd();
    }

    String consumeTo(String string2) {
        int n2 = this.nextIndexOf(string2);
        if (n2 != -1) {
            string2 = new String(this.input, this.pos, n2);
            this.pos += n2;
            return string2;
        }
        return this.consumeToEnd();
    }

    /*
     * Enabled aggressive block sorting
     */
    /* varargs */ String consumeToAny(char ... arrc) {
        int n2 = this.pos;
        block0 : while (this.pos < this.length) {
            for (int i2 = 0; i2 < arrc.length; ++i2) {
                if (this.input[this.pos] == arrc[i2]) break block0;
            }
            ++this.pos;
        }
        if (this.pos > n2) {
            return new String(this.input, n2, this.pos - n2);
        }
        return "";
    }

    String consumeToEnd() {
        String string2 = new String(this.input, this.pos, this.length - this.pos);
        this.pos = this.length;
        return string2;
    }

    boolean containsIgnoreCase(String string2) {
        String string3 = string2.toLowerCase(Locale.ENGLISH);
        string2 = string2.toUpperCase(Locale.ENGLISH);
        if (this.nextIndexOf(string3) > -1 || this.nextIndexOf(string2) > -1) {
            return true;
        }
        return false;
    }

    char current() {
        if (this.isEmpty()) {
            return '\uffff';
        }
        return this.input[this.pos];
    }

    boolean isEmpty() {
        if (this.pos >= this.length) {
            return true;
        }
        return false;
    }

    void mark() {
        this.mark = this.pos;
    }

    boolean matchConsume(String string2) {
        if (this.matches(string2)) {
            this.pos += string2.length();
            return true;
        }
        return false;
    }

    boolean matchConsumeIgnoreCase(String string2) {
        if (this.matchesIgnoreCase(string2)) {
            this.pos += string2.length();
            return true;
        }
        return false;
    }

    boolean matches(char c2) {
        if (!this.isEmpty() && this.input[this.pos] == c2) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    boolean matches(String string2) {
        int n2 = string2.length();
        if (n2 > this.length - this.pos) {
            return false;
        }
        int n3 = 0;
        while (n3 < n2) {
            if (string2.charAt(n3) != this.input[this.pos + n3]) return false;
            ++n3;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    /* varargs */ boolean matchesAny(char ... arrc) {
        if (this.isEmpty()) {
            return false;
        }
        char c2 = this.input[this.pos];
        int n2 = arrc.length;
        int n3 = 0;
        while (n3 < n2) {
            if (arrc[n3] == c2) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean matchesDigit() {
        char c2;
        if (this.isEmpty() || (c2 = this.input[this.pos]) < '0' || c2 > '9') {
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    boolean matchesIgnoreCase(String string2) {
        int n2 = string2.length();
        if (n2 > this.length - this.pos) {
            return false;
        }
        int n3 = 0;
        while (n3 < n2) {
            if (Character.toUpperCase(string2.charAt(n3)) != Character.toUpperCase(this.input[this.pos + n3])) return false;
            ++n3;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean matchesLetter() {
        char c2;
        if (this.isEmpty() || ((c2 = this.input[this.pos]) < 'A' || c2 > 'Z') && (c2 < 'a' || c2 > 'z')) {
            return false;
        }
        return true;
    }

    int nextIndexOf(char c2) {
        for (int i2 = this.pos; i2 < this.length; ++i2) {
            if (c2 != this.input[i2]) continue;
            return i2 - this.pos;
        }
        return -1;
    }

    int nextIndexOf(CharSequence charSequence) {
        char c2 = charSequence.charAt(0);
        int n2 = this.pos;
        while (n2 < this.length) {
            int n3;
            block6 : {
                n3 = n2;
                if (c2 != this.input[n2]) {
                    n3 = n2;
                    do {
                        n3 = n2 = n3 + 1;
                        if (n2 >= this.length) break block6;
                        n3 = n2;
                    } while (c2 != this.input[n2]);
                    n3 = n2;
                }
            }
            int n4 = n3 + 1;
            int n5 = charSequence.length() + n4 - 1;
            if (n3 < this.length && n5 <= this.length) {
                n2 = 1;
                while (n4 < n5 && charSequence.charAt(n2) == this.input[n4]) {
                    ++n4;
                    ++n2;
                }
                if (n4 == n5) {
                    return n3 - this.pos;
                }
            }
            n2 = n3 + 1;
        }
        return -1;
    }

    int pos() {
        return this.pos;
    }

    void rewindToMark() {
        this.pos = this.mark;
    }

    public String toString() {
        return new String(this.input, this.pos, this.length - this.pos);
    }

    void unconsume() {
        --this.pos;
    }
}

