/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.stream;

import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class JsonReader
implements Closeable {
    private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
    private static final char[] NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
    private static final int NUMBER_CHAR_DECIMAL = 3;
    private static final int NUMBER_CHAR_DIGIT = 2;
    private static final int NUMBER_CHAR_EXP_DIGIT = 7;
    private static final int NUMBER_CHAR_EXP_E = 5;
    private static final int NUMBER_CHAR_EXP_SIGN = 6;
    private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
    private static final int NUMBER_CHAR_NONE = 0;
    private static final int NUMBER_CHAR_SIGN = 1;
    private static final int PEEKED_BEGIN_ARRAY = 3;
    private static final int PEEKED_BEGIN_OBJECT = 1;
    private static final int PEEKED_BUFFERED = 11;
    private static final int PEEKED_DOUBLE_QUOTED = 9;
    private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    private static final int PEEKED_END_ARRAY = 4;
    private static final int PEEKED_END_OBJECT = 2;
    private static final int PEEKED_EOF = 17;
    private static final int PEEKED_FALSE = 6;
    private static final int PEEKED_LONG = 15;
    private static final int PEEKED_NONE = 0;
    private static final int PEEKED_NULL = 7;
    private static final int PEEKED_NUMBER = 16;
    private static final int PEEKED_SINGLE_QUOTED = 8;
    private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    private static final int PEEKED_TRUE = 5;
    private static final int PEEKED_UNQUOTED = 10;
    private static final int PEEKED_UNQUOTED_NAME = 14;
    private final char[] buffer = new char[1024];
    private final Reader in;
    private boolean lenient = false;
    private int limit = 0;
    private int lineNumber = 0;
    private int lineStart = 0;
    private int peeked = 0;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int pos = 0;
    private int[] stack = new int[32];
    private int stackSize = 0;

    static {
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess(){

            @Override
            public void promoteNameToValue(JsonReader jsonReader) throws IOException {
                int n2;
                if (jsonReader instanceof JsonTreeReader) {
                    ((JsonTreeReader)jsonReader).promoteNameToValue();
                    return;
                }
                int n3 = n2 = jsonReader.peeked;
                if (n2 == 0) {
                    n3 = jsonReader.doPeek();
                }
                if (n3 == 13) {
                    jsonReader.peeked = 9;
                    return;
                }
                if (n3 == 12) {
                    jsonReader.peeked = 8;
                    return;
                }
                if (n3 == 14) {
                    jsonReader.peeked = 10;
                    return;
                }
                throw new IllegalStateException("Expected a name but was " + (Object)((Object)jsonReader.peek()) + " " + " at line " + jsonReader.getLineNumber() + " column " + jsonReader.getColumnNumber());
            }
        };
    }

    public JsonReader(Reader reader) {
        int[] arrn = this.stack;
        int n2 = this.stackSize;
        this.stackSize = n2 + 1;
        arrn[n2] = 6;
        if (reader == null) {
            throw new NullPointerException("in == null");
        }
        this.in = reader;
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void consumeNonExecutePrefix() throws IOException {
        this.nextNonWhitespace(true);
        --this.pos;
        if (this.pos + NON_EXECUTE_PREFIX.length <= this.limit || this.fillBuffer(NON_EXECUTE_PREFIX.length)) {
            int n2 = 0;
            do {
                if (n2 >= NON_EXECUTE_PREFIX.length) {
                    this.pos += NON_EXECUTE_PREFIX.length;
                    return;
                }
                if (this.buffer[this.pos + n2] != NON_EXECUTE_PREFIX[n2]) break;
                ++n2;
            } while (true);
        }
    }

    /*
     * Exception decompiling
     */
    private int doPeek() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean fillBuffer(int n2) throws IOException {
        int n3;
        boolean bl2 = false;
        char[] arrc = this.buffer;
        this.lineStart -= this.pos;
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy(arrc, this.pos, arrc, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            n3 = this.in.read(arrc, this.limit, arrc.length - this.limit);
            boolean bl3 = bl2;
            if (n3 == -1) return bl3;
            this.limit += n3;
            n3 = n2;
            if (this.lineNumber == 0) {
                n3 = n2;
                if (this.lineStart == 0) {
                    n3 = n2;
                    if (this.limit > 0) {
                        n3 = n2;
                        if (arrc[0] == '\ufeff') {
                            ++this.pos;
                            ++this.lineStart;
                            n3 = n2 + 1;
                        }
                    }
                }
            }
            n2 = n3;
        } while (this.limit < n3);
        return true;
    }

    private int getColumnNumber() {
        return this.pos - this.lineStart + 1;
    }

    private int getLineNumber() {
        return this.lineNumber + 1;
    }

    private boolean isLiteral(char c2) throws IOException {
        switch (c2) {
            default: {
                return true;
            }
            case '#': 
            case '/': 
            case ';': 
            case '=': 
            case '\\': {
                this.checkLenient();
            }
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': 
            case ',': 
            case ':': 
            case '[': 
            case ']': 
            case '{': 
            case '}': 
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int nextNonWhitespace(boolean bl2) throws IOException {
        char[] arrc = this.buffer;
        int n2 = this.pos;
        int n3 = this.limit;
        block4 : do {
            int n4 = n3;
            int n5 = n2;
            if (n2 == n3) {
                this.pos = n2;
                if (!this.fillBuffer(1)) {
                    if (!bl2) return -1;
                    throw new EOFException("End of input at line " + this.getLineNumber() + " column " + this.getColumnNumber());
                }
                n5 = this.pos;
                n4 = this.limit;
            }
            n2 = n5 + 1;
            n3 = arrc[n5];
            if (n3 == 10) {
                ++this.lineNumber;
                this.lineStart = n2;
                n3 = n4;
                continue;
            }
            if (n3 != 32 && n3 != 13) {
                if (n3 == 9) {
                    n3 = n4;
                    continue;
                }
                if (n3 == 47) {
                    this.pos = n2;
                    if (n2 == n4) {
                        --this.pos;
                        boolean bl3 = this.fillBuffer(2);
                        ++this.pos;
                        if (!bl3) {
                            return n3;
                        }
                    }
                    this.checkLenient();
                    switch (arrc[this.pos]) {
                        default: {
                            return n3;
                        }
                        case '*': {
                            ++this.pos;
                            if (!this.skipTo("*/")) {
                                throw this.syntaxError("Unterminated comment");
                            }
                            n2 = this.pos + 2;
                            n3 = this.limit;
                            continue block4;
                        }
                        case '/': 
                    }
                    ++this.pos;
                    this.skipToEndOfLine();
                    n2 = this.pos;
                    n3 = this.limit;
                    continue;
                }
                if (n3 == 35) {
                    this.pos = n2;
                    this.checkLenient();
                    this.skipToEndOfLine();
                    n2 = this.pos;
                    n3 = this.limit;
                    continue;
                }
                this.pos = n2;
                return n3;
            }
            n3 = n4;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private String nextQuotedValue(char c2) throws IOException {
        char[] arrc = this.buffer;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int n2 = this.pos;
            int n3 = this.limit;
            int n4 = n2;
            while (n2 < n3) {
                int n5;
                int n6;
                int n7 = n2 + 1;
                char c3 = arrc[n2];
                if (c3 == c2) {
                    this.pos = n7;
                    stringBuilder.append(arrc, n4, n7 - n4 - 1);
                    return stringBuilder.toString();
                }
                if (c3 == '\\') {
                    this.pos = n7;
                    stringBuilder.append(arrc, n4, n7 - n4 - 1);
                    stringBuilder.append(this.readEscapeCharacter());
                    n2 = this.pos;
                    n5 = this.limit;
                    n6 = n2;
                } else {
                    n5 = n3;
                    n2 = n7;
                    n6 = n4;
                    if (c3 == '\n') {
                        ++this.lineNumber;
                        this.lineStart = n7;
                        n5 = n3;
                        n2 = n7;
                        n6 = n4;
                    }
                }
                n3 = n5;
                n4 = n6;
            }
            stringBuilder.append(arrc, n4, n2 - n4);
            this.pos = n2;
        } while (this.fillBuffer(1));
        throw this.syntaxError("Unterminated string");
    }

    /*
     * Enabled aggressive block sorting
     */
    private String nextUnquotedValue() throws IOException {
        StringBuilder stringBuilder;
        void var3_4;
        int n2;
        Object var3_1 = null;
        int n3 = 0;
        block4 : do {
            void var3_2;
            if (this.pos + n3 < this.limit) {
                stringBuilder = var3_2;
                n2 = n3;
                switch (this.buffer[this.pos + n3]) {
                    default: {
                        ++n3;
                        continue block4;
                    }
                    case '#': 
                    case '/': 
                    case ';': 
                    case '=': 
                    case '\\': {
                        this.checkLenient();
                        n2 = n3;
                        stringBuilder = var3_2;
                    }
                    case '\t': 
                    case '\n': 
                    case '\f': 
                    case '\r': 
                    case ' ': 
                    case ',': 
                    case ':': 
                    case '[': 
                    case ']': 
                    case '{': 
                    case '}': 
                }
                break;
            }
            if (n3 < this.buffer.length) {
                stringBuilder = var3_2;
                n2 = n3;
                if (!this.fillBuffer(n3 + 1)) break;
                continue;
            }
            stringBuilder = var3_2;
            if (var3_2 == null) {
                stringBuilder = new StringBuilder();
            }
            stringBuilder.append(this.buffer, this.pos, n3);
            this.pos += n3;
            n2 = 0;
            n3 = 0;
            StringBuilder stringBuilder2 = stringBuilder;
            if (!this.fillBuffer(1)) break;
        } while (true);
        if (stringBuilder == null) {
            String string2 = new String(this.buffer, this.pos, n2);
        } else {
            stringBuilder.append(this.buffer, this.pos, n2);
            String string3 = stringBuilder.toString();
        }
        this.pos += n2;
        return var3_4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int peekKeyword() throws IOException {
        String string2;
        String string3;
        int n2 = this.buffer[this.pos];
        if (n2 == 116 || n2 == 84) {
            string2 = "true";
            string3 = "TRUE";
            n2 = 5;
        } else if (n2 == 102 || n2 == 70) {
            string2 = "false";
            string3 = "FALSE";
            n2 = 6;
        } else {
            if (n2 != 110 && n2 != 78) {
                return 0;
            }
            string2 = "null";
            string3 = "NULL";
            n2 = 7;
        }
        int n3 = string2.length();
        for (int i2 = 1; i2 < n3; ++i2) {
            if (this.pos + i2 >= this.limit && !this.fillBuffer(i2 + 1)) {
                return 0;
            }
            char c2 = this.buffer[this.pos + i2];
            if (c2 == string2.charAt(i2) || c2 == string3.charAt(i2)) continue;
            return 0;
        }
        if ((this.pos + n3 < this.limit || this.fillBuffer(n3 + 1)) && this.isLiteral(this.buffer[this.pos + n3])) {
            return 0;
        }
        this.pos += n3;
        this.peeked = n2;
        return n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int peekNumber() throws IOException {
        block15 : {
            block16 : {
                var15_1 = this.buffer;
                var2_2 = this.pos;
                var9_3 = this.limit;
                var11_4 = 0;
                var3_5 = false;
                var5_6 = 1;
                var8_7 = 0;
                var4_8 = 0;
                block6 : do {
                    var7_11 = var9_3;
                    var6_10 = var2_2;
                    if (var2_2 + var4_8 != var9_3) ** GOTO lbl18
                    if (var4_8 == var15_1.length) {
                        return 0;
                    }
                    if (!this.fillBuffer(var4_8 + 1)) ** GOTO lbl24
                    var6_10 = this.pos;
                    var7_11 = this.limit;
lbl18: // 2 sources:
                    var1_9 = var15_1[var6_10 + var4_8];
                    switch (var1_9) {
                        default: {
                            if (var1_9 >= '0' && var1_9 <= '9') break;
                            if (this.isLiteral(var1_9)) {
                                return 0;
                            }
lbl24: // 3 sources:
                            if (var8_7 != 2 || var5_6 == 0 || var11_4 == Long.MIN_VALUE && !var3_5) break block15;
                            if (!var3_5) break block6;
                            break block16;
                        }
                        case '-': {
                            if (var8_7 == 0) {
                                var10_12 = true;
                                var2_2 = 1;
                                var13_13 = var11_4;
                                var9_3 = var5_6;
                            } else {
                                if (var8_7 != 5) return 0;
                                var2_2 = 6;
                                var9_3 = var5_6;
                                var10_12 = var3_5;
                                var13_13 = var11_4;
                            }
                            ** GOTO lbl93
                        }
                        case '+': {
                            if (var8_7 != 5) return 0;
                            var2_2 = 6;
                            var9_3 = var5_6;
                            var10_12 = var3_5;
                            var13_13 = var11_4;
                            ** GOTO lbl93
                        }
                        case 'E': 
                        case 'e': {
                            if (var8_7 != 2) {
                                if (var8_7 != 4) return 0;
                            }
                            var2_2 = 5;
                            var9_3 = var5_6;
                            var10_12 = var3_5;
                            var13_13 = var11_4;
                            ** GOTO lbl93
                        }
                        case '.': {
                            if (var8_7 != 2) return 0;
                            var2_2 = 3;
                            var9_3 = var5_6;
                            var10_12 = var3_5;
                            var13_13 = var11_4;
                            ** GOTO lbl93
                        }
                    }
                    if (var8_7 != 1 && var8_7 != 0) ** GOTO lbl68
                    var13_13 = - var1_9 - 48;
                    var2_2 = 2;
                    var9_3 = var5_6;
                    var10_12 = var3_5;
                    ** GOTO lbl93
lbl68: // 1 sources:
                    if (var8_7 != 2) ** GOTO lbl77
                    if (var11_4 == 0) {
                        return 0;
                    }
                    var13_13 = 10 * var11_4 - (long)(var1_9 - 48);
                    var2_2 = var11_4 > -922337203685477580L || var11_4 == -922337203685477580L && var13_13 < var11_4 ? 1 : 0;
                    var9_3 = var5_6 & var2_2;
                    var2_2 = var8_7;
                    var10_12 = var3_5;
                    ** GOTO lbl93
lbl77: // 1 sources:
                    if (var8_7 != 3) ** GOTO lbl83
                    var2_2 = 4;
                    var9_3 = var5_6;
                    var10_12 = var3_5;
                    var13_13 = var11_4;
                    ** GOTO lbl93
lbl83: // 1 sources:
                    if (var8_7 == 5) ** GOTO lbl-1000
                    var9_3 = var5_6;
                    var2_2 = var8_7;
                    var10_12 = var3_5;
                    var13_13 = var11_4;
                    if (var8_7 == 6) lbl-1000: // 2 sources:
                    {
                        var2_2 = 7;
                        var9_3 = var5_6;
                        var10_12 = var3_5;
                        var13_13 = var11_4;
                    }
lbl93: // 11 sources:
                    ++var4_8;
                    var5_6 = var9_3;
                    var9_3 = var7_11;
                    var8_7 = var2_2;
                    var3_5 = var10_12;
                    var2_2 = var6_10;
                    var11_4 = var13_13;
                } while (true);
                var11_4 = - var11_4;
            }
            this.peekedLong = var11_4;
            this.pos += var4_8;
            this.peeked = 15;
            return 15;
        }
        if (var8_7 != 2 && var8_7 != 4) {
            if (var8_7 != 7) return 0;
        }
        this.peekedNumberLength = var4_8;
        this.peeked = 16;
        return 16;
    }

    private void push(int n2) {
        int[] arrn;
        if (this.stackSize == this.stack.length) {
            arrn = new int[this.stackSize * 2];
            System.arraycopy(this.stack, 0, arrn, 0, this.stackSize);
            this.stack = arrn;
        }
        arrn = this.stack;
        int n3 = this.stackSize;
        this.stackSize = n3 + 1;
        arrn[n3] = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !this.fillBuffer(1)) {
            throw this.syntaxError("Unterminated escape sequence");
        }
        char[] arrc = this.buffer;
        int n2 = this.pos;
        this.pos = n2 + 1;
        char c2 = arrc[n2];
        switch (c2) {
            default: {
                return c2;
            }
            case 'u': {
                int n3;
                if (this.pos + 4 > this.limit && !this.fillBuffer(4)) {
                    throw this.syntaxError("Unterminated escape sequence");
                }
                c2 = '\u0000';
                n2 = n3 = this.pos;
                do {
                    if (n2 >= n3 + 4) {
                        this.pos += 4;
                        return c2;
                    }
                    char c3 = this.buffer[n2];
                    char c4 = (char)(c2 << 4);
                    if (c3 >= '0' && c3 <= '9') {
                        c2 = (char)(c3 - 48 + c4);
                    } else if (c3 >= 'a' && c3 <= 'f') {
                        c2 = (char)(c3 - 97 + 10 + c4);
                    } else {
                        if (c3 < 'A') throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                        if (c3 > 'F') throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                        c2 = (char)(c3 - 65 + 10 + c4);
                    }
                    ++n2;
                } while (true);
            }
            case 't': {
                return '\t';
            }
            case 'b': {
                return '\b';
            }
            case 'n': {
                return '\n';
            }
            case 'r': {
                return '\r';
            }
            case 'f': {
                return '\f';
            }
            case '\n': 
        }
        ++this.lineNumber;
        this.lineStart = this.pos;
        return c2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void skipQuotedValue(char c2) throws IOException {
        char[] arrc = this.buffer;
        do {
            int n2 = this.pos;
            int n3 = this.limit;
            while (n2 < n3) {
                int n4;
                int n5 = n2 + 1;
                char c3 = arrc[n2];
                if (c3 == c2) {
                    this.pos = n5;
                    return;
                }
                if (c3 == '\\') {
                    this.pos = n5;
                    this.readEscapeCharacter();
                    n2 = this.pos;
                    n4 = this.limit;
                } else {
                    n4 = n3;
                    n2 = n5;
                    if (c3 == '\n') {
                        ++this.lineNumber;
                        this.lineStart = n5;
                        n4 = n3;
                        n2 = n5;
                    }
                }
                n3 = n4;
            }
            this.pos = n2;
        } while (this.fillBuffer(1));
        throw this.syntaxError("Unterminated string");
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean skipTo(String string2) throws IOException {
        do {
            block6 : {
                if (this.pos + string2.length() > this.limit && !this.fillBuffer(string2.length())) {
                    return false;
                }
                if (this.buffer[this.pos] == '\n') {
                    ++this.lineNumber;
                    this.lineStart = this.pos + 1;
                } else {
                    int n2 = 0;
                    while (n2 < string2.length()) {
                        if (this.buffer[this.pos + n2] == string2.charAt(n2)) {
                            ++n2;
                            continue;
                        }
                        break block6;
                    }
                    return true;
                }
            }
            ++this.pos;
        } while (true);
    }

    private void skipToEndOfLine() throws IOException {
        int n2;
        do {
            if (this.pos < this.limit || this.fillBuffer(1)) {
                char[] arrc = this.buffer;
                n2 = this.pos;
                this.pos = n2 + 1;
                if ((n2 = arrc[n2]) != 10) continue;
                ++this.lineNumber;
                this.lineStart = this.pos;
            }
            return;
        } while (n2 != 13);
    }

    private void skipUnquotedValue() throws IOException {
        do {
            int n2 = 0;
            block5 : while (this.pos + n2 < this.limit) {
                switch (this.buffer[this.pos + n2]) {
                    default: {
                        ++n2;
                        continue block5;
                    }
                    case '#': 
                    case '/': 
                    case ';': 
                    case '=': 
                    case '\\': {
                        this.checkLenient();
                    }
                    case '\t': 
                    case '\n': 
                    case '\f': 
                    case '\r': 
                    case ' ': 
                    case ',': 
                    case ':': 
                    case '[': 
                    case ']': 
                    case '{': 
                    case '}': 
                }
                this.pos += n2;
                return;
            }
            this.pos += n2;
        } while (this.fillBuffer(1));
    }

    private IOException syntaxError(String string2) throws IOException {
        throw new MalformedJsonException(string2 + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }

    public void beginArray() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 3) {
            this.push(1);
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_ARRAY but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }

    public void beginObject() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 1) {
            this.push(3);
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_OBJECT but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }

    @Override
    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }

    public void endArray() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 4) {
            --this.stackSize;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_ARRAY but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }

    public void endObject() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 2) {
            --this.stackSize;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_OBJECT but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }

    public boolean hasNext() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 != 2 && n3 != 4) {
            return true;
        }
        return false;
    }

    public final boolean isLenient() {
        return this.lenient;
    }

    public boolean nextBoolean() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 5) {
            this.peeked = 0;
            return true;
        }
        if (n3 == 6) {
            this.peeked = 0;
            return false;
        }
        throw new IllegalStateException("Expected a boolean but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }

    /*
     * Enabled aggressive block sorting
     */
    public double nextDouble() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 15) {
            this.peeked = 0;
            return this.peekedLong;
        }
        if (n3 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (n3 == 8 || n3 == 9) {
            char c2 = n3 == 8 ? '\'' : '\"';
            this.peekedString = this.nextQuotedValue(c2);
        } else if (n3 == 10) {
            this.peekedString = this.nextUnquotedValue();
        } else if (n3 != 11) {
            throw new IllegalStateException("Expected a double but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.peeked = 11;
        double d2 = Double.parseDouble(this.peekedString);
        if (!this.lenient && (Double.isNaN(d2) || Double.isInfinite(d2))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + d2 + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.peekedString = null;
        this.peeked = 0;
        return d2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int nextInt() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 15) {
            n3 = (int)this.peekedLong;
            if (this.peekedLong != (long)n3) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            this.peeked = 0;
            return n3;
        }
        if (n3 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (n3 != 8 && n3 != 9) {
                throw new IllegalStateException("Expected an int but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            char c2 = n3 == 8 ? '\'' : '\"';
            this.peekedString = this.nextQuotedValue(c2);
            try {
                n3 = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                return n3;
            }
            catch (NumberFormatException var6_5) {}
        }
        this.peeked = 11;
        double d2 = Double.parseDouble(this.peekedString);
        n3 = (int)d2;
        if ((double)n3 != d2) {
            throw new NumberFormatException("Expected an int but was " + this.peekedString + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.peekedString = null;
        this.peeked = 0;
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long nextLong() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 15) {
            this.peeked = 0;
            return this.peekedLong;
        }
        if (n3 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (n3 != 8 && n3 != 9) {
                throw new IllegalStateException("Expected a long but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            char c2 = n3 == 8 ? '\'' : '\"';
            this.peekedString = this.nextQuotedValue(c2);
            try {
                long l2 = Long.parseLong(this.peekedString);
                this.peeked = 0;
                return l2;
            }
            catch (NumberFormatException var8_7) {}
        }
        this.peeked = 11;
        double d2 = Double.parseDouble(this.peekedString);
        long l3 = (long)d2;
        if ((double)l3 != d2) {
            throw new NumberFormatException("Expected a long but was " + this.peekedString + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.peekedString = null;
        this.peeked = 0;
        return l3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String nextName() throws IOException {
        String string2;
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 14) {
            string2 = this.nextUnquotedValue();
        } else if (n3 == 12) {
            string2 = this.nextQuotedValue('\'');
        } else {
            if (n3 != 13) {
                throw new IllegalStateException("Expected a name but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            string2 = this.nextQuotedValue('\"');
        }
        this.peeked = 0;
        return string2;
    }

    public void nextNull() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 7) {
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected null but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }

    /*
     * Enabled aggressive block sorting
     */
    public String nextString() throws IOException {
        String string2;
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        if (n3 == 10) {
            string2 = this.nextUnquotedValue();
        } else if (n3 == 8) {
            string2 = this.nextQuotedValue('\'');
        } else if (n3 == 9) {
            string2 = this.nextQuotedValue('\"');
        } else if (n3 == 11) {
            string2 = this.peekedString;
            this.peekedString = null;
        } else if (n3 == 15) {
            string2 = Long.toString(this.peekedLong);
        } else {
            if (n3 != 16) {
                throw new IllegalStateException("Expected a string but was " + (Object)((Object)this.peek()) + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            string2 = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        }
        this.peeked = 0;
        return string2;
    }

    public JsonToken peek() throws IOException {
        int n2;
        int n3 = n2 = this.peeked;
        if (n2 == 0) {
            n3 = this.doPeek();
        }
        switch (n3) {
            default: {
                throw new AssertionError();
            }
            case 1: {
                return JsonToken.BEGIN_OBJECT;
            }
            case 2: {
                return JsonToken.END_OBJECT;
            }
            case 3: {
                return JsonToken.BEGIN_ARRAY;
            }
            case 4: {
                return JsonToken.END_ARRAY;
            }
            case 12: 
            case 13: 
            case 14: {
                return JsonToken.NAME;
            }
            case 5: 
            case 6: {
                return JsonToken.BOOLEAN;
            }
            case 7: {
                return JsonToken.NULL;
            }
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                return JsonToken.STRING;
            }
            case 15: 
            case 16: {
                return JsonToken.NUMBER;
            }
            case 17: 
        }
        return JsonToken.END_DOCUMENT;
    }

    public final void setLenient(boolean bl2) {
        this.lenient = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void skipValue() throws IOException {
        int n2;
        int n3 = 0;
        do {
            int n4 = n2 = this.peeked;
            if (n2 == 0) {
                n4 = this.doPeek();
            }
            if (n4 == 3) {
                this.push(1);
                n2 = n3 + 1;
            } else if (n4 == 1) {
                this.push(3);
                n2 = n3 + 1;
            } else if (n4 == 4) {
                --this.stackSize;
                n2 = n3 - 1;
            } else if (n4 == 2) {
                --this.stackSize;
                n2 = n3 - 1;
            } else if (n4 == 14 || n4 == 10) {
                this.skipUnquotedValue();
                n2 = n3;
            } else if (n4 == 8 || n4 == 12) {
                this.skipQuotedValue('\'');
                n2 = n3;
            } else if (n4 == 9 || n4 == 13) {
                this.skipQuotedValue('\"');
                n2 = n3;
            } else {
                n2 = n3;
                if (n4 == 16) {
                    this.pos += this.peekedNumberLength;
                    n2 = n3;
                }
            }
            this.peeked = 0;
            n3 = n2;
        } while (n2 != 0);
    }

    public String toString() {
        return this.getClass().getSimpleName() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber();
    }

}

