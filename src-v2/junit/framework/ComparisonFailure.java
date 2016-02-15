/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import junit.framework.AssertionFailedError;
import junit.framework.ComparisonCompactor;

public class ComparisonFailure
extends AssertionFailedError {
    private static final int MAX_CONTEXT_LENGTH = 20;
    private static final long serialVersionUID = 1;
    private String fActual;
    private String fExpected;

    public ComparisonFailure(String string2, String string3, String string4) {
        super(string2);
        this.fExpected = string3;
        this.fActual = string4;
    }

    public String getActual() {
        return this.fActual;
    }

    public String getExpected() {
        return this.fExpected;
    }

    public String getMessage() {
        return new ComparisonCompactor(20, this.fExpected, this.fActual).compact(super.getMessage());
    }
}

