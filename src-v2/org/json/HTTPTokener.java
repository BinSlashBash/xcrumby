/*
 * Decompiled with CFR 0_110.
 */
package org.json;

import org.json.JSONException;
import org.json.JSONTokener;

public class HTTPTokener
extends JSONTokener {
    public HTTPTokener(String string2) {
        super(string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String nextToken() throws JSONException {
        var3_1 = new StringBuffer();
        while (Character.isWhitespace(var2_2 = this.next())) {
        }
        if (var2_2 == '\"') ** GOTO lbl7
        var1_3 = var2_2;
        if (var2_2 != '\'') ** GOTO lbl16
lbl7: // 2 sources:
        do {
            if ((var1_3 = this.next()) < ' ') {
                throw this.syntaxError("Unterminated string.");
            }
            if (var1_3 == var2_2) {
                return var3_1.toString();
            }
            var3_1.append(var1_3);
        } while (true);
lbl-1000: // 1 sources:
        {
            var3_1.append(var1_3);
            var1_3 = this.next();
lbl16: // 2 sources:
            if (var1_3 == '\u0000') return var3_1.toString();
            ** while (!Character.isWhitespace((char)var1_3))
        }
lbl18: // 1 sources:
        return var3_1.toString();
    }
}

