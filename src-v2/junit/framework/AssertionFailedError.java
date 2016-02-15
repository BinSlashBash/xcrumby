/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

public class AssertionFailedError
extends AssertionError {
    private static final long serialVersionUID = 1;

    public AssertionFailedError() {
    }

    public AssertionFailedError(String string2) {
        super((Object)AssertionFailedError.defaultString(string2));
    }

    private static String defaultString(String string2) {
        String string3 = string2;
        if (string2 == null) {
            string3 = "";
        }
        return string3;
    }
}

