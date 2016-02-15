/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language.bm;

public enum NameType {
    ASHKENAZI("ash"),
    GENERIC("gen"),
    SEPHARDIC("sep");
    
    private final String name;

    private NameType(String string3) {
        this.name = string3;
    }

    public String getName() {
        return this.name;
    }
}

