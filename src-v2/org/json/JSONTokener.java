/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTokener {
    private long character;
    private boolean eof;
    private long index;
    private long line;
    private char previous;
    private Reader reader;
    private boolean usePrevious;

    public JSONTokener(InputStream inputStream) throws JSONException {
        this(new InputStreamReader(inputStream));
    }

    /*
     * Enabled aggressive block sorting
     */
    public JSONTokener(Reader reader) {
        if (!reader.markSupported()) {
            reader = new BufferedReader(reader);
        }
        this.reader = reader;
        this.eof = false;
        this.usePrevious = false;
        this.previous = '\u0000';
        this.index = 0;
        this.character = 1;
        this.line = 1;
    }

    public JSONTokener(String string2) {
        this(new StringReader(string2));
    }

    public static int dehexchar(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - 48;
        }
        if (c2 >= 'A' && c2 <= 'F') {
            return c2 - 55;
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return c2 - 87;
        }
        return -1;
    }

    public void back() throws JSONException {
        if (this.usePrevious || this.index <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        --this.index;
        --this.character;
        this.usePrevious = true;
        this.eof = false;
    }

    public boolean end() {
        if (this.eof && !this.usePrevious) {
            return true;
        }
        return false;
    }

    public boolean more() throws JSONException {
        this.next();
        if (this.end()) {
            return false;
        }
        this.back();
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public char next() throws JSONException {
        block6 : {
            var3_1 = 0;
            if (this.usePrevious) {
                this.usePrevious = false;
                var1_2 = this.previous;
            } else {
                var1_2 = var2_3 = this.reader.read();
                if (var2_3 > 0) break block6;
                this.eof = true;
                var1_2 = 0;
            }
        }
        ++this.index;
        if (this.previous != '\r') ** GOTO lbl22
        ++this.line;
        if (var1_2 != 10) ** GOTO lbl19
        ** GOTO lbl20
        catch (IOException var5_4) {
            throw new JSONException(var5_4);
        }
lbl19: // 1 sources:
        var3_1 = 1;
lbl20: // 2 sources:
        this.character = var3_1;
        ** GOTO lbl27
lbl22: // 1 sources:
        if (var1_2 == 10) {
            this.line = 1 + this.line;
            this.character = 0;
        } else {
            ++this.character;
        }
lbl27: // 3 sources:
        this.previous = (char)var1_2;
        return this.previous;
    }

    public char next(char c2) throws JSONException {
        char c3 = this.next();
        if (c3 != c2) {
            throw this.syntaxError(new StringBuffer().append("Expected '").append(c2).append("' and instead saw '").append(c3).append("'").toString());
        }
        return c3;
    }

    public String next(int n2) throws JSONException {
        if (n2 == 0) {
            return "";
        }
        char[] arrc = new char[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            arrc[i2] = this.next();
            if (!this.end()) continue;
            throw this.syntaxError("Substring bounds error");
        }
        return new String(arrc);
    }

    public char nextClean() throws JSONException {
        char c2;
        while ((c2 = this.next()) != '\u0000' && c2 <= ' ') {
        }
        return c2;
    }

    public String nextString(char c2) throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        block13 : do {
            char c3 = this.next();
            switch (c3) {
                default: {
                    if (c3 != c2) break;
                    return stringBuffer.toString();
                }
                case '\u0000': 
                case '\n': 
                case '\r': {
                    throw this.syntaxError("Unterminated string");
                }
                case '\\': {
                    c3 = this.next();
                    switch (c3) {
                        default: {
                            throw this.syntaxError("Illegal escape.");
                        }
                        case 'b': {
                            stringBuffer.append('\b');
                            continue block13;
                        }
                        case 't': {
                            stringBuffer.append('\t');
                            continue block13;
                        }
                        case 'n': {
                            stringBuffer.append('\n');
                            continue block13;
                        }
                        case 'f': {
                            stringBuffer.append('\f');
                            continue block13;
                        }
                        case 'r': {
                            stringBuffer.append('\r');
                            continue block13;
                        }
                        case 'u': {
                            stringBuffer.append((char)Integer.parseInt(this.next(4), 16));
                            continue block13;
                        }
                        case '\"': 
                        case '\'': 
                        case '/': 
                        case '\\': 
                    }
                    stringBuffer.append(c3);
                    continue block13;
                }
            }
            stringBuffer.append(c3);
        } while (true);
    }

    public String nextTo(char c2) throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        do {
            char c3;
            if ((c3 = this.next()) == c2 || c3 == '\u0000' || c3 == '\n' || c3 == '\r') {
                if (c3 != '\u0000') {
                    this.back();
                }
                return stringBuffer.toString().trim();
            }
            stringBuffer.append(c3);
        } while (true);
    }

    public String nextTo(String string2) throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        do {
            char c2;
            if (string2.indexOf(c2 = this.next()) >= 0 || c2 == '\u0000' || c2 == '\n' || c2 == '\r') {
                if (c2 != '\u0000') {
                    this.back();
                }
                return stringBuffer.toString().trim();
            }
            stringBuffer.append(c2);
        } while (true);
    }

    public Object nextValue() throws JSONException {
        CharSequence charSequence2;
        char c2 = this.nextClean();
        switch (c2) {
            default: {
                CharSequence charSequence2 = new StringBuffer();
                while (c2 >= ' ' && ",:]}/\\\"[{;=#".indexOf(c2) < 0) {
                    charSequence2.append(c2);
                    c2 = this.next();
                }
                break;
            }
            case '\"': 
            case '\'': {
                return this.nextString(c2);
            }
            case '{': {
                this.back();
                return new JSONObject(this);
            }
            case '[': {
                this.back();
                return new JSONArray(this);
            }
        }
        this.back();
        charSequence2 = charSequence2.toString().trim();
        if ("".equals(charSequence2)) {
            throw this.syntaxError("Missing value");
        }
        return JSONObject.stringToValue((String)charSequence2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public char skipTo(char c2) throws JSONException {
        try {
            char c3;
            long l2 = this.index;
            long l3 = this.character;
            long l4 = this.line;
            this.reader.mark(1000000);
            do {
                if ((c3 = this.next()) != '\u0000') continue;
                this.reader.reset();
                this.index = l2;
                this.character = l3;
                this.line = l4;
                return c3;
            } while (c3 != c2);
            this.back();
            return c3;
        }
        catch (IOException var9_6) {
            throw new JSONException(var9_6);
        }
    }

    public JSONException syntaxError(String string2) {
        return new JSONException(new StringBuffer().append(string2).append(this.toString()).toString());
    }

    public String toString() {
        return new StringBuffer().append(" at ").append(this.index).append(" [character ").append(this.character).append(" line ").append(this.line).append("]").toString();
    }
}

