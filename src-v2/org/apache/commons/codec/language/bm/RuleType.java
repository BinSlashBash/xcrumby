/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language.bm;

public enum RuleType {
    APPROX("approx"),
    EXACT("exact"),
    RULES("rules");
    
    private final String name;

    private RuleType(String string3) {
        this.name = string3;
    }

    public String getName() {
        return this.name;
    }
}

