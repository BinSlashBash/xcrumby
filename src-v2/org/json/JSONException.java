/*
 * Decompiled with CFR 0_110.
 */
package org.json;

public class JSONException
extends RuntimeException {
    private static final long serialVersionUID = 0;
    private Throwable cause;

    public JSONException(String string2) {
        super(string2);
    }

    public JSONException(Throwable throwable) {
        super(throwable.getMessage());
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}

