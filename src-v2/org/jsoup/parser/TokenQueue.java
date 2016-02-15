/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;

public class TokenQueue {
    private static final char ESC = '\\';
    private int pos = 0;
    private String queue;

    public TokenQueue(String string2) {
        Validate.notNull(string2);
        this.queue = string2;
    }

    private int remainingLength() {
        return this.queue.length() - this.pos;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String unescape(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        char c2 = '\u0000';
        char[] arrc = string2.toCharArray();
        int n2 = arrc.length;
        int n3 = 0;
        while (n3 < n2) {
            char c3 = arrc[n3];
            if (c3 == '\\') {
                if (c2 != '\u0000' && c2 == '\\') {
                    stringBuilder.append(c3);
                }
            } else {
                stringBuilder.append(c3);
            }
            c2 = c3;
            ++n3;
        }
        return stringBuilder.toString();
    }

    public void addFirst(Character c2) {
        this.addFirst(c2.toString());
    }

    public void addFirst(String string2) {
        this.queue = string2 + this.queue.substring(this.pos);
        this.pos = 0;
    }

    public void advance() {
        if (!this.isEmpty()) {
            ++this.pos;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String chompBalanced(char var1_1, char var2_2) {
        var4_3 = -1;
        var3_4 = -1;
        var9_5 = 0;
        var8_6 = 0;
        while (!this.isEmpty()) {
            var10_10 = Character.valueOf(this.consume());
            if (var8_6 == 0) ** GOTO lbl-1000
            var6_8 = var9_5;
            var5_7 = var4_3;
            if (var8_6 != 92) lbl-1000: // 2 sources:
            {
                if (var10_10.equals(Character.valueOf(var1_1))) {
                    var6_8 = var7_9 = var9_5 + 1;
                    var5_7 = var4_3;
                    if (var4_3 == -1) {
                        var5_7 = this.pos;
                        var6_8 = var7_9;
                    }
                } else {
                    var6_8 = var9_5;
                    var5_7 = var4_3;
                    if (var10_10.equals(Character.valueOf(var2_2))) {
                        var6_8 = var9_5 - 1;
                        var5_7 = var4_3;
                    }
                }
            }
            var7_9 = var3_4;
            if (var6_8 > 0) {
                var7_9 = var3_4;
                if (var8_6 != 0) {
                    var7_9 = this.pos;
                }
            }
            var8_6 = var10_10.charValue();
            var9_5 = var6_8;
            var3_4 = var7_9;
            var4_3 = var5_7;
            if (var6_8 > 0) continue;
            var3_4 = var7_9;
            var4_3 = var5_7;
            break;
        }
        if (var3_4 < 0) return "";
        return this.queue.substring(var4_3, var3_4);
    }

    public String chompTo(String string2) {
        String string3 = this.consumeTo(string2);
        this.matchChomp(string2);
        return string3;
    }

    public String chompToIgnoreCase(String string2) {
        String string3 = this.consumeToIgnoreCase(string2);
        this.matchChomp(string2);
        return string3;
    }

    public char consume() {
        String string2 = this.queue;
        int n2 = this.pos;
        this.pos = n2 + 1;
        return string2.charAt(n2);
    }

    public void consume(String string2) {
        if (!this.matches(string2)) {
            throw new IllegalStateException("Queue did not match expected sequence");
        }
        int n2 = string2.length();
        if (n2 > this.remainingLength()) {
            throw new IllegalStateException("Queue not long enough to consume sequence");
        }
        this.pos += n2;
    }

    public String consumeAttributeKey() {
        int n2 = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny('-', '_', ':'))) {
            ++this.pos;
        }
        return this.queue.substring(n2, this.pos);
    }

    public String consumeCssIdentifier() {
        int n2 = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny('-', '_'))) {
            ++this.pos;
        }
        return this.queue.substring(n2, this.pos);
    }

    public String consumeElementSelector() {
        int n2 = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny('|', '_', '-'))) {
            ++this.pos;
        }
        return this.queue.substring(n2, this.pos);
    }

    public String consumeTagName() {
        int n2 = this.pos;
        while (!this.isEmpty() && (this.matchesWord() || this.matchesAny(':', '_', '-'))) {
            ++this.pos;
        }
        return this.queue.substring(n2, this.pos);
    }

    public String consumeTo(String string2) {
        int n2 = this.queue.indexOf(string2, this.pos);
        if (n2 != -1) {
            string2 = this.queue.substring(this.pos, n2);
            this.pos += string2.length();
            return string2;
        }
        return this.remainder();
    }

    public /* varargs */ String consumeToAny(String ... arrstring) {
        int n2 = this.pos;
        while (!this.isEmpty() && !this.matchesAny(arrstring)) {
            ++this.pos;
        }
        return this.queue.substring(n2, this.pos);
    }

    public String consumeToIgnoreCase(String string2) {
        int n2 = this.pos;
        String string3 = string2.substring(0, 1);
        boolean bl2 = string3.toLowerCase().equals(string3.toUpperCase());
        while (!this.isEmpty() && !this.matches(string2)) {
            if (bl2) {
                int n3 = this.queue.indexOf(string3, this.pos) - this.pos;
                if (n3 == 0) {
                    ++this.pos;
                    continue;
                }
                if (n3 < 0) {
                    this.pos = this.queue.length();
                    continue;
                }
                this.pos += n3;
                continue;
            }
            ++this.pos;
        }
        return this.queue.substring(n2, this.pos);
    }

    public boolean consumeWhitespace() {
        boolean bl2 = false;
        while (this.matchesWhitespace()) {
            ++this.pos;
            bl2 = true;
        }
        return bl2;
    }

    public String consumeWord() {
        int n2 = this.pos;
        while (this.matchesWord()) {
            ++this.pos;
        }
        return this.queue.substring(n2, this.pos);
    }

    public boolean isEmpty() {
        if (this.remainingLength() == 0) {
            return true;
        }
        return false;
    }

    public boolean matchChomp(String string2) {
        if (this.matches(string2)) {
            this.pos += string2.length();
            return true;
        }
        return false;
    }

    public boolean matches(String string2) {
        return this.queue.regionMatches(true, this.pos, string2, 0, string2.length());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public /* varargs */ boolean matchesAny(char ... arrc) {
        if (this.isEmpty()) {
            return false;
        }
        int n2 = arrc.length;
        int n3 = 0;
        while (n3 < n2) {
            char c2 = arrc[n3];
            if (this.queue.charAt(this.pos) == c2) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public /* varargs */ boolean matchesAny(String ... arrstring) {
        int n2 = arrstring.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (!this.matches(arrstring[i2])) continue;
            return true;
        }
        return false;
    }

    public boolean matchesCS(String string2) {
        return this.queue.startsWith(string2, this.pos);
    }

    public boolean matchesStartTag() {
        if (this.remainingLength() >= 2 && this.queue.charAt(this.pos) == '<' && Character.isLetter(this.queue.charAt(this.pos + 1))) {
            return true;
        }
        return false;
    }

    public boolean matchesWhitespace() {
        if (!this.isEmpty() && StringUtil.isWhitespace(this.queue.charAt(this.pos))) {
            return true;
        }
        return false;
    }

    public boolean matchesWord() {
        if (!this.isEmpty() && Character.isLetterOrDigit(this.queue.charAt(this.pos))) {
            return true;
        }
        return false;
    }

    public char peek() {
        if (this.isEmpty()) {
            return '\u0000';
        }
        return this.queue.charAt(this.pos);
    }

    public String remainder() {
        String string2 = this.queue.substring(this.pos, this.queue.length());
        this.pos = this.queue.length();
        return string2;
    }

    public String toString() {
        return this.queue.substring(this.pos);
    }
}

