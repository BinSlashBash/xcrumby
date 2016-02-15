/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.io.IOException;
import java.io.Writer;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWriter {
    private static final int maxdepth = 200;
    private boolean comma = false;
    protected char mode = 105;
    private final JSONObject[] stack = new JSONObject[200];
    private int top = 0;
    protected Writer writer;

    public JSONWriter(Writer writer) {
        this.writer = writer;
    }

    private JSONWriter append(String string2) throws JSONException {
        if (string2 == null) {
            throw new JSONException("Null pointer");
        }
        if (this.mode == 'o' || this.mode == 'a') {
            try {
                if (this.comma && this.mode == 'a') {
                    this.writer.write(44);
                }
                this.writer.write(string2);
                if (this.mode == 'o') {
                    this.mode = 107;
                }
                this.comma = true;
                return this;
            }
            catch (IOException var1_2) {
                throw new JSONException(var1_2);
            }
        }
        throw new JSONException("Value out of sequence.");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private JSONWriter end(char c2, char c3) throws JSONException {
        if (this.mode != c2) {
            String string2;
            if (c2 == 'a') {
                string2 = "Misplaced endArray.";
                do {
                    throw new JSONException(string2);
                    break;
                } while (true);
            }
            string2 = "Misplaced endObject.";
            throw new JSONException(string2);
        }
        this.pop(c2);
        try {
            this.writer.write(c3);
            this.comma = true;
            return this;
        }
        catch (IOException var3_4) {
            throw new JSONException(var3_4);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void pop(char c2) throws JSONException {
        int n2 = 97;
        if (this.top <= 0) {
            throw new JSONException("Nesting error.");
        }
        char c3 = this.stack[this.top - 1] == null ? 'a' : 'k';
        if (c3 != c2) {
            throw new JSONException("Nesting error.");
        }
        --this.top;
        if (this.top == 0) {
            n2 = 100;
        } else if (this.stack[this.top - 1] != null) {
            n2 = 107;
        }
        this.mode = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void push(JSONObject jSONObject) throws JSONException {
        if (this.top >= 200) {
            throw new JSONException("Nesting too deep.");
        }
        this.stack[this.top] = jSONObject;
        int n2 = jSONObject == null ? 97 : 107;
        this.mode = n2;
        ++this.top;
    }

    public JSONWriter array() throws JSONException {
        if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
            this.push(null);
            this.append("[");
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }

    public JSONWriter endArray() throws JSONException {
        return this.end('a', ']');
    }

    public JSONWriter endObject() throws JSONException {
        return this.end('k', '}');
    }

    public JSONWriter key(String string2) throws JSONException {
        if (string2 == null) {
            throw new JSONException("Null key.");
        }
        if (this.mode == 'k') {
            try {
                this.stack[this.top - 1].putOnce(string2, Boolean.TRUE);
                if (this.comma) {
                    this.writer.write(44);
                }
                this.writer.write(JSONObject.quote(string2));
                this.writer.write(58);
                this.comma = false;
                this.mode = 111;
                return this;
            }
            catch (IOException var1_2) {
                throw new JSONException(var1_2);
            }
        }
        throw new JSONException("Misplaced key.");
    }

    public JSONWriter object() throws JSONException {
        if (this.mode == 'i') {
            this.mode = 111;
        }
        if (this.mode == 'o' || this.mode == 'a') {
            this.append("{");
            this.push(new JSONObject());
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced object.");
    }

    public JSONWriter value(double d2) throws JSONException {
        return this.value(new Double(d2));
    }

    public JSONWriter value(long l2) throws JSONException {
        return this.append(Long.toString(l2));
    }

    public JSONWriter value(Object object) throws JSONException {
        return this.append(JSONObject.valueToString(object));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JSONWriter value(boolean bl2) throws JSONException {
        String string2;
        if (bl2) {
            string2 = "true";
            do {
                return this.append(string2);
                break;
            } while (true);
        }
        string2 = "false";
        return this.append(string2);
    }
}

