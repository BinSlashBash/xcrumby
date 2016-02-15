/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import java.io.StringWriter;
import java.io.Writer;
import org.json.JSONWriter;

public class JSONStringer
extends JSONWriter {
    public JSONStringer() {
        super(new StringWriter());
    }

    public String toString() {
        if (this.mode == 'd') {
            return this.writer.toString();
        }
        return null;
    }
}

