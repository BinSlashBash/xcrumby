/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.XML;

public class XMLTokener
extends JSONTokener {
    public static final HashMap entity = new HashMap(8);

    static {
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.GT);
        entity.put("lt", XML.LT);
        entity.put("quot", XML.QUOT);
    }

    public XMLTokener(String string2) {
        super(string2);
    }

    public String nextCDATA() throws JSONException {
        int n2;
        StringBuffer stringBuffer = new StringBuffer();
        do {
            char c2 = this.next();
            if (this.end()) {
                throw this.syntaxError("Unclosed CDATA");
            }
            stringBuffer.append(c2);
        } while ((n2 = stringBuffer.length() - 3) < 0 || stringBuffer.charAt(n2) != ']' || stringBuffer.charAt(n2 + 1) != ']' || stringBuffer.charAt(n2 + 2) != '>');
        stringBuffer.setLength(n2);
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object nextContent() throws JSONException {
        char c2;
        while (Character.isWhitespace(c2 = this.next())) {
        }
        if (c2 == '\u0000') {
            return null;
        }
        if (c2 == '<') {
            return XML.LT;
        }
        StringBuffer stringBuffer = new StringBuffer();
        do {
            if (c2 == '<' || c2 == '\u0000') {
                this.back();
                return stringBuffer.toString().trim();
            }
            if (c2 == '&') {
                stringBuffer.append(this.nextEntity(c2));
            } else {
                stringBuffer.append(c2);
            }
            c2 = this.next();
        } while (true);
    }

    public Object nextEntity(char c2) throws JSONException {
        char c3;
        CharSequence charSequence = new StringBuffer();
        while (Character.isLetterOrDigit(c3 = this.next()) || c3 == '#') {
            charSequence.append(Character.toLowerCase(c3));
        }
        if (c3 == ';') {
            Object v2 = entity.get(charSequence = charSequence.toString());
            if (v2 != null) {
                return v2;
            }
        } else {
            throw this.syntaxError(new StringBuffer().append("Missing ';' in XML entity: &").append((Object)charSequence).toString());
        }
        return new StringBuffer().append(c2).append((String)charSequence).append(";").toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Object nextMeta() throws JSONException {
        while (Character.isWhitespace(var1_1 = this.next())) {
        }
        switch (var1_1) {
            default: {
                break;
            }
            case '\u0000': {
                throw this.syntaxError("Misshaped meta tag");
            }
            case '<': {
                return XML.LT;
            }
            case '>': {
                return XML.GT;
            }
            case '/': {
                return XML.SLASH;
            }
            case '=': {
                return XML.EQ;
            }
            case '!': {
                return XML.BANG;
            }
            case '?': {
                return XML.QUEST;
            }
            case '\"': 
            case '\'': {
                ** GOTO lbl31
            }
        }
        block14 : do {
            if (Character.isWhitespace(var1_1 = this.next())) {
                return Boolean.TRUE;
            }
            switch (var1_1) {
                default: {
                    continue block14;
                }
                case '\u0000': 
                case '!': 
                case '\"': 
                case '\'': 
                case '/': 
                case '<': 
                case '=': 
                case '>': 
                case '?': 
            }
            break;
        } while (true);
        this.back();
        return Boolean.TRUE;
lbl31: // 1 sources:
        do {
            if ((var2_2 = this.next()) != '\u0000') continue;
            throw this.syntaxError("Unterminated string");
        } while (var2_2 != var1_1);
        return Boolean.TRUE;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Object nextToken() throws JSONException {
        while (Character.isWhitespace(var1_1 = this.next())) {
        }
        switch (var1_1) {
            default: {
                var3_2 = new StringBuffer();
                break;
            }
            case '\u0000': {
                throw this.syntaxError("Misshaped element");
            }
            case '<': {
                throw this.syntaxError("Misplaced '<'");
            }
            case '>': {
                return XML.GT;
            }
            case '/': {
                return XML.SLASH;
            }
            case '=': {
                return XML.EQ;
            }
            case '!': {
                return XML.BANG;
            }
            case '?': {
                return XML.QUEST;
            }
            case '\"': 
            case '\'': {
                var3_3 = new StringBuffer();
                ** GOTO lbl39
            }
        }
        block16 : do {
            var3_2.append(var1_1);
            var1_1 = this.next();
            if (Character.isWhitespace(var1_1)) {
                return var3_2.toString();
            }
            switch (var1_1) {
                default: {
                    continue block16;
                }
                case '\u0000': {
                    return var3_2.toString();
                }
                case '!': 
                case '/': 
                case '=': 
                case '>': 
                case '?': 
                case '[': 
                case ']': {
                    this.back();
                    return var3_2.toString();
                }
                case '\"': 
                case '\'': 
                case '<': 
            }
            break;
        } while (true);
        throw this.syntaxError("Bad character in a name");
lbl39: // 1 sources:
        do {
            if ((var2_4 = this.next()) == '\u0000') {
                throw this.syntaxError("Unterminated string");
            }
            if (var2_4 == var1_1) {
                return var3_3.toString();
            }
            if (var2_4 == '&') {
                var3_3.append(this.nextEntity(var2_4));
                continue;
            }
            var3_3.append(var2_4);
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean skipPast(String var1_1) throws JSONException {
        var5_2 = 0;
        var8_3 = var1_1.length();
        var9_4 = new char[var8_3];
        var4_5 = 0;
        do {
            var3_7 = var5_2;
            if (var4_5 >= var8_3) ** GOTO lbl18
            var2_6 = this.next();
            if (var2_6 == '\u0000') {
                return false;
            }
            var9_4[var4_5] = var2_6;
            ++var4_5;
        } while (true);
lbl-1000: // 1 sources:
        {
            var9_4[var3_7] = var2_6;
            var3_7 = var4_5 = var3_7 + 1;
            if (var4_5 >= var8_3) {
                var3_7 = var4_5 - var8_3;
            }
lbl18: // 4 sources:
            var4_5 = var3_7;
            var7_9 = 1;
            var5_2 = 0;
            do {
                var6_8 = var7_9;
                if (var5_2 >= var8_3) return true;
                if (var9_4[var4_5] != var1_1.charAt(var5_2)) {
                    var6_8 = 0;
                    if (var6_8 == 0) continue block1;
                    return true;
                }
                var4_5 = var6_8 = var4_5 + 1;
                if (var6_8 >= var8_3) {
                    var4_5 = var6_8 - var8_3;
                }
                ++var5_2;
            } while (true);
            ** while ((var2_6 = this.next()) != '\u0000')
        }
lbl34: // 1 sources:
        return false;
    }
}

