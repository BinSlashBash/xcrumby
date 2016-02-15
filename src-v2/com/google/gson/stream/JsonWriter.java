/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.stream;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class JsonWriter
implements Closeable,
Flushable {
    private static final String[] HTML_SAFE_REPLACEMENT_CHARS;
    private static final String[] REPLACEMENT_CHARS;
    private String deferredName;
    private boolean htmlSafe;
    private String indent;
    private boolean lenient;
    private final Writer out;
    private String separator;
    private boolean serializeNulls;
    private int[] stack = new int[32];
    private int stackSize = 0;

    static {
        REPLACEMENT_CHARS = new String[128];
        for (int i2 = 0; i2 <= 31; ++i2) {
            JsonWriter.REPLACEMENT_CHARS[i2] = String.format("\\u%04x", i2);
        }
        JsonWriter.REPLACEMENT_CHARS[34] = "\\\"";
        JsonWriter.REPLACEMENT_CHARS[92] = "\\\\";
        JsonWriter.REPLACEMENT_CHARS[9] = "\\t";
        JsonWriter.REPLACEMENT_CHARS[8] = "\\b";
        JsonWriter.REPLACEMENT_CHARS[10] = "\\n";
        JsonWriter.REPLACEMENT_CHARS[13] = "\\r";
        JsonWriter.REPLACEMENT_CHARS[12] = "\\f";
        HTML_SAFE_REPLACEMENT_CHARS = (String[])REPLACEMENT_CHARS.clone();
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[60] = "\\u003c";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[62] = "\\u003e";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[38] = "\\u0026";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[61] = "\\u003d";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[39] = "\\u0027";
    }

    public JsonWriter(Writer writer) {
        this.push(6);
        this.separator = ":";
        this.serializeNulls = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.out = writer;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void beforeName() throws IOException {
        int n2 = this.peek();
        if (n2 == 5) {
            this.out.write(44);
        } else if (n2 != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        this.newline();
        this.replaceTop(4);
    }

    private void beforeValue(boolean bl2) throws IOException {
        switch (this.peek()) {
            default: {
                throw new IllegalStateException("Nesting problem.");
            }
            case 7: {
                if (!this.lenient) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            case 6: {
                if (!this.lenient && !bl2) {
                    throw new IllegalStateException("JSON must start with an array or an object.");
                }
                this.replaceTop(7);
                return;
            }
            case 1: {
                this.replaceTop(2);
                this.newline();
                return;
            }
            case 2: {
                this.out.append(',');
                this.newline();
                return;
            }
            case 4: 
        }
        this.out.append(this.separator);
        this.replaceTop(5);
    }

    private JsonWriter close(int n2, int n3, String string2) throws IOException {
        int n4 = this.peek();
        if (n4 != n3 && n4 != n2) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        }
        --this.stackSize;
        if (n4 == n3) {
            this.newline();
        }
        this.out.write(string2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void newline() throws IOException {
        if (this.indent != null) {
            this.out.write("\n");
            int n2 = this.stackSize;
            for (int i2 = 1; i2 < n2; ++i2) {
                this.out.write(this.indent);
            }
        }
    }

    private JsonWriter open(int n2, String string2) throws IOException {
        this.beforeValue(true);
        this.push(n2);
        this.out.write(string2);
        return this;
    }

    private int peek() {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        return this.stack[this.stackSize - 1];
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

    private void replaceTop(int n2) {
        this.stack[this.stackSize - 1] = n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void string(String var1_1) throws IOException {
        var8_2 = this.htmlSafe != false ? JsonWriter.HTML_SAFE_REPLACEMENT_CHARS : JsonWriter.REPLACEMENT_CHARS;
        this.out.write("\"");
        var3_3 = 0;
        var5_4 = var1_1.length();
        for (var2_5 = 0; var2_5 < var5_4; ++var2_5) {
            var6_7 = var1_1.charAt(var2_5);
            if (var6_7 >= '') ** GOTO lbl12
            var7_8 = var9_9 = var8_2[var6_7];
            if (var9_9 != null) ** GOTO lbl18
            var4_6 = var3_3;
            ** GOTO lbl22
lbl12: // 1 sources:
            if (var6_7 != '\u2028') ** GOTO lbl15
            var7_8 = "\\u2028";
            ** GOTO lbl18
lbl15: // 1 sources:
            var4_6 = var3_3;
            if (var6_7 == '\u2029') {
                var7_8 = "\\u2029";
lbl18: // 3 sources:
                if (var3_3 < var2_5) {
                    this.out.write(var1_1, var3_3, var2_5 - var3_3);
                }
                this.out.write(var7_8);
                var4_6 = var2_5 + 1;
            }
lbl22: // 4 sources:
            var3_3 = var4_6;
        }
        if (var3_3 < var5_4) {
            this.out.write(var1_1, var3_3, var5_4 - var3_3);
        }
        this.out.write("\"");
    }

    private void writeDeferredName() throws IOException {
        if (this.deferredName != null) {
            this.beforeName();
            this.string(this.deferredName);
            this.deferredName = null;
        }
    }

    public JsonWriter beginArray() throws IOException {
        this.writeDeferredName();
        return this.open(1, "[");
    }

    public JsonWriter beginObject() throws IOException {
        this.writeDeferredName();
        return this.open(3, "{");
    }

    @Override
    public void close() throws IOException {
        this.out.close();
        int n2 = this.stackSize;
        if (n2 > 1 || n2 == 1 && this.stack[n2 - 1] != 7) {
            throw new IOException("Incomplete document");
        }
        this.stackSize = 0;
    }

    public JsonWriter endArray() throws IOException {
        return this.close(1, 2, "]");
    }

    public JsonWriter endObject() throws IOException {
        return this.close(3, 5, "}");
    }

    @Override
    public void flush() throws IOException {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.out.flush();
    }

    public final boolean getSerializeNulls() {
        return this.serializeNulls;
    }

    public final boolean isHtmlSafe() {
        return this.htmlSafe;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public JsonWriter name(String string2) throws IOException {
        if (string2 == null) {
            throw new NullPointerException("name == null");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException();
        }
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.deferredName = string2;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonWriter nullValue() throws IOException {
        if (this.deferredName != null) {
            if (!this.serializeNulls) {
                this.deferredName = null;
                return this;
            }
            this.writeDeferredName();
        }
        this.beforeValue(false);
        this.out.write("null");
        return this;
    }

    public final void setHtmlSafe(boolean bl2) {
        this.htmlSafe = bl2;
    }

    public final void setIndent(String string2) {
        if (string2.length() == 0) {
            this.indent = null;
            this.separator = ":";
            return;
        }
        this.indent = string2;
        this.separator = ": ";
    }

    public final void setLenient(boolean bl2) {
        this.lenient = bl2;
    }

    public final void setSerializeNulls(boolean bl2) {
        this.serializeNulls = bl2;
    }

    public JsonWriter value(double d2) throws IOException {
        if (Double.isNaN(d2) || Double.isInfinite(d2)) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + d2);
        }
        this.writeDeferredName();
        this.beforeValue(false);
        this.out.append(Double.toString(d2));
        return this;
    }

    public JsonWriter value(long l2) throws IOException {
        this.writeDeferredName();
        this.beforeValue(false);
        this.out.write(Long.toString(l2));
        return this;
    }

    public JsonWriter value(Number number) throws IOException {
        if (number == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        String string2 = number.toString();
        if (!this.lenient && (string2.equals("-Infinity") || string2.equals("Infinity") || string2.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
        }
        this.beforeValue(false);
        this.out.append(string2);
        return this;
    }

    public JsonWriter value(String string2) throws IOException {
        if (string2 == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        this.beforeValue(false);
        this.string(string2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonWriter value(boolean bl2) throws IOException {
        this.writeDeferredName();
        this.beforeValue(false);
        Writer writer = this.out;
        String string2 = bl2 ? "true" : "false";
        writer.write(string2);
        return this;
    }
}

