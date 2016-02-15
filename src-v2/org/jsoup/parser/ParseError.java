/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

public class ParseError {
    private String errorMsg;
    private int pos;

    ParseError(int n2, String string2) {
        this.pos = n2;
        this.errorMsg = string2;
    }

    /* varargs */ ParseError(int n2, String string2, Object ... arrobject) {
        this.errorMsg = String.format(string2, arrobject);
        this.pos = n2;
    }

    public String getErrorMessage() {
        return this.errorMsg;
    }

    public int getPosition() {
        return this.pos;
    }

    public String toString() {
        return "" + this.pos + ": " + this.errorMsg;
    }
}

