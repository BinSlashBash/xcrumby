/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import java.util.Set;

public interface JsonValueFormatVisitor {
    public void enumTypes(Set<String> var1);

    public void format(JsonValueFormat var1);

    public static class Base
    implements JsonValueFormatVisitor {
        @Override
        public void enumTypes(Set<String> set) {
        }

        @Override
        public void format(JsonValueFormat jsonValueFormat) {
        }
    }

}

