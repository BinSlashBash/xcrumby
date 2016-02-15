/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

public abstract class NameTransformer {
    public static final NameTransformer NOP = new NameTransformer(){

        @Override
        public String reverse(String string2) {
            return string2;
        }

        @Override
        public String transform(String string2) {
            return string2;
        }
    };

    protected NameTransformer() {
    }

    public static NameTransformer chainedTransformer(NameTransformer nameTransformer, NameTransformer nameTransformer2) {
        return new Chained(nameTransformer, nameTransformer2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static NameTransformer simpleTransformer(final String string2, final String string3) {
        boolean bl2 = true;
        boolean bl3 = string2 != null && string2.length() > 0;
        if (string3 == null) return new NameTransformer(){

            @Override
            public String reverse(String string22) {
                if (string22.startsWith(string2)) {
                    return string22.substring(string2.length());
                }
                return null;
            }

            public String toString() {
                return "[PrefixTransformer('" + string2 + "')]";
            }

            @Override
            public String transform(String string22) {
                return string2 + string22;
            }
        };
        if (string3.length() <= 0) {
            bl2 = false;
        }
        if (!bl3) return NOP;
        if (bl2) {
            return new NameTransformer(){

                @Override
                public String reverse(String string22) {
                    if (string22.startsWith(string2) && (string22 = string22.substring(string2.length())).endsWith(string3)) {
                        return string22.substring(0, string22.length() - string3.length());
                    }
                    return null;
                }

                public String toString() {
                    return "[PreAndSuffixTransformer('" + string2 + "','" + string3 + "')]";
                }

                @Override
                public String transform(String string22) {
                    return string2 + string22 + string3;
                }
            };
        }
        return new ;
    }

    public abstract String reverse(String var1);

    public abstract String transform(String var1);

    public static class Chained
    extends NameTransformer {
        protected final NameTransformer _t1;
        protected final NameTransformer _t2;

        public Chained(NameTransformer nameTransformer, NameTransformer nameTransformer2) {
            this._t1 = nameTransformer;
            this._t2 = nameTransformer2;
        }

        @Override
        public String reverse(String string2) {
            String string3;
            string2 = string3 = this._t1.reverse(string2);
            if (string3 != null) {
                string2 = this._t2.reverse(string3);
            }
            return string2;
        }

        public String toString() {
            return "[ChainedTransformer(" + this._t1 + ", " + this._t2 + ")]";
        }

        @Override
        public String transform(String string2) {
            return this._t1.transform(this._t2.transform(string2));
        }
    }

}

