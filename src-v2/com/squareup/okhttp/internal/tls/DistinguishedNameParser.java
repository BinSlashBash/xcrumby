/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.tls;

import javax.security.auth.x500.X500Principal;

final class DistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final String dn;
    private int end;
    private final int length;
    private int pos;

    public DistinguishedNameParser(X500Principal x500Principal) {
        this.dn = x500Principal.getName("RFC2253");
        this.length = this.dn.length();
    }

    private String escapedAV() {
        this.beg = this.pos;
        this.end = this.pos;
        block5 : do {
            char[] arrc;
            int n2;
            if (this.pos >= this.length) {
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            switch (this.chars[this.pos]) {
                default: {
                    arrc = this.chars;
                    n2 = this.end;
                    this.end = n2 + 1;
                    arrc[n2] = this.chars[this.pos];
                    ++this.pos;
                    continue block5;
                }
                case '+': 
                case ',': 
                case ';': {
                    return new String(this.chars, this.beg, this.end - this.beg);
                }
                case '\\': {
                    arrc = this.chars;
                    n2 = this.end;
                    this.end = n2 + 1;
                    arrc[n2] = this.getEscaped();
                    ++this.pos;
                    continue block5;
                }
                case ' ': 
            }
            this.cur = this.end;
            ++this.pos;
            arrc = this.chars;
            n2 = this.end;
            this.end = n2 + 1;
            arrc[n2] = 32;
            while (this.pos < this.length && this.chars[this.pos] == ' ') {
                arrc = this.chars;
                n2 = this.end;
                this.end = n2 + 1;
                arrc[n2] = 32;
                ++this.pos;
            }
            if (this.pos == this.length || this.chars[this.pos] == ',' || this.chars[this.pos] == '+' || this.chars[this.pos] == ';') break;
        } while (true);
        return new String(this.chars, this.beg, this.cur - this.beg);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getByte(int n2) {
        if (n2 + 1 >= this.length) {
            throw new IllegalStateException("Malformed DN: " + this.dn);
        }
        int n3 = this.chars[n2];
        if (n3 >= 48 && n3 <= 57) {
            n3 -= 48;
        } else if (n3 >= 97 && n3 <= 102) {
            n3 -= 87;
        } else {
            if (n3 < 65) throw new IllegalStateException("Malformed DN: " + this.dn);
            if (n3 > 70) throw new IllegalStateException("Malformed DN: " + this.dn);
            n3 -= 55;
        }
        if ((n2 = this.chars[n2 + 1]) >= 48 && n2 <= 57) {
            n2 -= 48;
            return (n3 << 4) + n2;
        }
        if (n2 >= 97 && n2 <= 102) {
            return (n3 << 4) + (n2 -= 87);
        }
        if (n2 < 65) throw new IllegalStateException("Malformed DN: " + this.dn);
        if (n2 > 70) throw new IllegalStateException("Malformed DN: " + this.dn);
        return (n3 << 4) + (n2 -= 55);
    }

    private char getEscaped() {
        ++this.pos;
        if (this.pos == this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        switch (this.chars[this.pos]) {
            default: {
                return this.getUTF8();
            }
            case ' ': 
            case '\"': 
            case '#': 
            case '%': 
            case '*': 
            case '+': 
            case ',': 
            case ';': 
            case '<': 
            case '=': 
            case '>': 
            case '\\': 
            case '_': 
        }
        return this.chars[this.pos];
    }

    /*
     * Enabled aggressive block sorting
     */
    private char getUTF8() {
        int n2;
        char c2 = '?';
        int n3 = this.getByte(this.pos);
        ++this.pos;
        if (n3 < 128) {
            return (char)n3;
        }
        char c3 = c2;
        if (n3 < 192) return c3;
        c3 = c2;
        if (n3 > 247) return c3;
        if (n3 <= 223) {
            n2 = 1;
            n3 &= 31;
        } else if (n3 <= 239) {
            n2 = 2;
            n3 &= 15;
        } else {
            n2 = 3;
            n3 &= 7;
        }
        int n4 = 0;
        int n5 = n3;
        n3 = n4;
        while (n3 < n2) {
            ++this.pos;
            c3 = c2;
            if (this.pos == this.length) return c3;
            c3 = c2;
            if (this.chars[this.pos] != '\\') return c3;
            ++this.pos;
            n4 = this.getByte(this.pos);
            ++this.pos;
            c3 = c2;
            if ((n4 & 192) != 128) return c3;
            n5 = (n5 << 6) + (n4 & 63);
            ++n3;
        }
        return (char)n5;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private String hexAV() {
        if (this.pos + 4 >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.beg = this.pos++;
        do {
            if (this.pos != this.length && this.chars[this.pos] != '+' && this.chars[this.pos] != ',' && this.chars[this.pos] != ';') ** GOTO lbl8
            this.end = this.pos;
            ** GOTO lbl14
lbl8: // 1 sources:
            if (this.chars[this.pos] == ' ') {
                this.end = this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    ++this.pos;
                }
            }
            ** GOTO lbl17
lbl14: // 2 sources:
            if ((var3_3 = this.end - this.beg) < 5) throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            if ((var3_3 & 1) != 0) break;
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
lbl17: // 1 sources:
            if (this.chars[this.pos] >= 'A' && this.chars[this.pos] <= 'F') {
                var4_2 = this.chars;
                var1_1 = this.pos;
                var4_2[var1_1] = (char)(var4_2[var1_1] + 32);
            }
            ++this.pos;
        } while (true);
        var4_2 = new byte[var3_3 / 2];
        var1_1 = 0;
        var2_4 = this.beg + 1;
        while (var1_1 < var4_2.length) {
            var4_2[var1_1] = (byte)this.getByte(var2_4);
            var2_4 += 2;
            ++var1_1;
        }
        return new String(this.chars, this.beg, var3_3);
    }

    private String nextAT() {
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            ++this.pos;
        }
        if (this.pos == this.length) {
            return null;
        }
        this.beg = this.pos++;
        while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] != ' ') {
            ++this.pos;
        }
        if (this.pos >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.end = this.pos;
        if (this.chars[this.pos] == ' ') {
            while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] == ' ') {
                ++this.pos;
            }
            if (this.chars[this.pos] != '=' || this.pos == this.length) {
                throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            }
        }
        ++this.pos;
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            ++this.pos;
        }
        if (!(this.end - this.beg <= 4 || this.chars[this.beg + 3] != '.' || this.chars[this.beg] != 'O' && this.chars[this.beg] != 'o' || this.chars[this.beg + 1] != 'I' && this.chars[this.beg + 1] != 'i' || this.chars[this.beg + 2] != 'D' && this.chars[this.beg + 2] != 'd')) {
            this.beg += 4;
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    /*
     * Enabled aggressive block sorting
     */
    private String quotedAV() {
        ++this.pos;
        this.end = this.beg = this.pos;
        do {
            if (this.pos == this.length) {
                throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            }
            if (this.chars[this.pos] == '\"') {
                ++this.pos;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    ++this.pos;
                }
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            this.chars[this.end] = this.chars[this.pos] == '\\' ? this.getEscaped() : this.chars[this.pos];
            ++this.pos;
            ++this.end;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String findMostSpecific(String string2) {
        String string3;
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        String string4 = string3 = this.nextAT();
        if (string3 == null) {
            return null;
        }
        do {
            string3 = "";
            if (this.pos == this.length) {
                return null;
            }
            switch (this.chars[this.pos]) {
                default: {
                    string3 = this.escapedAV();
                    break;
                }
                case '\"': {
                    string3 = this.quotedAV();
                    break;
                }
                case '#': {
                    string3 = this.hexAV();
                }
                case '+': 
                case ',': 
                case ';': 
            }
            if (string2.equalsIgnoreCase(string4)) return string3;
            if (this.pos >= this.length) {
                return null;
            }
            if (this.chars[this.pos] != ',' && this.chars[this.pos] != ';' && this.chars[this.pos] != '+') {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
            ++this.pos;
            string4 = string3 = this.nextAT();
        } while (string3 != null);
        throw new IllegalStateException("Malformed DN: " + this.dn);
    }
}

