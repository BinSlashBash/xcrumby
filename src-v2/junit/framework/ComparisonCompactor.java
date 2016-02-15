/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import junit.framework.Assert;

public class ComparisonCompactor {
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";
    private static final String ELLIPSIS = "...";
    private String fActual;
    private int fContextLength;
    private String fExpected;
    private int fPrefix;
    private int fSuffix;

    public ComparisonCompactor(int n2, String string2, String string3) {
        this.fContextLength = n2;
        this.fExpected = string2;
        this.fActual = string3;
    }

    private boolean areStringsEqual() {
        return this.fExpected.equals(this.fActual);
    }

    private String compactString(String string2) {
        String string3;
        string2 = string3 = "[" + string2.substring(this.fPrefix, string2.length() - this.fSuffix + 1) + "]";
        if (this.fPrefix > 0) {
            string2 = this.computeCommonPrefix() + string3;
        }
        string3 = string2;
        if (this.fSuffix > 0) {
            string3 = string2 + this.computeCommonSuffix();
        }
        return string3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String computeCommonPrefix() {
        String string2;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.fPrefix > this.fContextLength) {
            string2 = "...";
            do {
                return stringBuilder.append(string2).append(this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix)).toString();
                break;
            } while (true);
        }
        string2 = "";
        return stringBuilder.append(string2).append(this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix)).toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String computeCommonSuffix() {
        String string2;
        int n2 = Math.min(this.fExpected.length() - this.fSuffix + 1 + this.fContextLength, this.fExpected.length());
        StringBuilder stringBuilder = new StringBuilder().append(this.fExpected.substring(this.fExpected.length() - this.fSuffix + 1, n2));
        if (this.fExpected.length() - this.fSuffix + 1 < this.fExpected.length() - this.fContextLength) {
            string2 = "...";
            do {
                return stringBuilder.append(string2).toString();
                break;
            } while (true);
        }
        string2 = "";
        return stringBuilder.append(string2).toString();
    }

    private void findCommonPrefix() {
        this.fPrefix = 0;
        int n2 = Math.min(this.fExpected.length(), this.fActual.length());
        while (this.fPrefix < n2 && this.fExpected.charAt(this.fPrefix) == this.fActual.charAt(this.fPrefix)) {
            ++this.fPrefix;
        }
        return;
    }

    private void findCommonSuffix() {
        int n2 = this.fExpected.length() - 1;
        int n3 = this.fActual.length() - 1;
        do {
            if (n3 < this.fPrefix || n2 < this.fPrefix || this.fExpected.charAt(n2) != this.fActual.charAt(n3)) {
                this.fSuffix = this.fExpected.length() - n2;
                return;
            }
            --n3;
            --n2;
        } while (true);
    }

    public String compact(String string2) {
        if (this.fExpected == null || this.fActual == null || this.areStringsEqual()) {
            return Assert.format(string2, this.fExpected, this.fActual);
        }
        this.findCommonPrefix();
        this.findCommonSuffix();
        return Assert.format(string2, this.compactString(this.fExpected), this.compactString(this.fActual));
    }
}

