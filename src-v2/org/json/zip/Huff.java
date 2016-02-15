/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import org.json.JSONException;
import org.json.zip.BitReader;
import org.json.zip.BitWriter;
import org.json.zip.JSONzip;
import org.json.zip.None;
import org.json.zip.PostMortem;

public class Huff
implements None,
PostMortem {
    private final int domain;
    private final Symbol[] symbols;
    private Symbol table;
    private boolean upToDate = false;
    private int width;

    public Huff(int n2) {
        this.domain = n2;
        int n3 = n2 * 2 - 1;
        this.symbols = new Symbol[n3];
        for (int i2 = 0; i2 < n2; ++i2) {
            this.symbols[i2] = new Symbol(i2);
        }
        while (n2 < n3) {
            this.symbols[n2] = new Symbol(-1);
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean postMortem(int n2) {
        boolean bl2 = true;
        int[] arrn = new int[this.domain];
        Symbol symbol = this.symbols[n2];
        if (symbol.integer != n2) {
            return false;
        }
        int n3 = 0;
        do {
            Symbol symbol2;
            if ((symbol2 = symbol.back) == null) {
                if (symbol != this.table) return false;
                this.width = 0;
                symbol = this.table;
                break;
            }
            if (symbol2.zero == symbol) {
                arrn[n3] = 0;
            } else {
                if (symbol2.one != symbol) return false;
                arrn[n3] = 1;
            }
            ++n3;
            symbol = symbol2;
        } while (true);
        while (symbol.integer == -1) {
            if (arrn[--n3] != 0) {
                symbol = symbol.one;
                continue;
            }
            symbol = symbol.zero;
        }
        if (symbol.integer != n2) return false;
        if (n3 != 0) return false;
        return bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void write(Symbol symbol, BitWriter bitWriter) throws JSONException {
        Symbol symbol2;
        try {
            symbol2 = symbol.back;
            if (symbol2 == null) return;
        }
        catch (Throwable var1_2) {
            throw new JSONException(var1_2);
        }
        ++this.width;
        this.write(symbol2, bitWriter);
        if (symbol2.zero == symbol) {
            bitWriter.zero();
            return;
        }
        bitWriter.one();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void generate() {
        block9 : {
            if (this.upToDate != false) return;
            var3_2 = var2_1 = this.symbols[0];
            this.table = null;
            var2_1.next = null;
            var1_3 = 1;
            block0 : do {
                if (var1_3 >= this.domain) break block9;
                var5_5 = this.symbols[var1_3];
                if (var5_5.weight < var2_1.weight) {
                    var5_5.next = var2_1;
                    var2_1 = var5_5;
                } else {
                    var4_4 = var3_2;
                    if (var5_5.weight >= var3_2.weight) break;
                    var4_4 = var2_1;
                    break;
                }
lbl17: // 2 sources:
                do {
                    ++var1_3;
                    continue block0;
                    break;
                } while (true);
                break;
            } while (true);
            do {
                if ((var3_2 = var4_4.next) == null || var5_5.weight < var3_2.weight) {
                    var5_5.next = var3_2;
                    var4_4.next = var5_5;
                    var3_2 = var5_5;
                    ** continue;
                }
                var4_4 = var3_2;
            } while (true);
        }
        var1_3 = this.domain;
        var3_2 = var2_1;
        do {
            var5_5 = var2_1;
            var6_6 = var5_5.next;
            var4_4 = var6_6.next;
            var2_1 = this.symbols[var1_3];
            ++var1_3;
            var2_1.weight = var5_5.weight + var6_6.weight;
            var2_1.zero = var5_5;
            var2_1.one = var6_6;
            var2_1.back = null;
            var5_5.back = var2_1;
            var6_6.back = var2_1;
            if (var4_4 == null) {
                this.table = var2_1;
                this.upToDate = true;
                return;
            }
            if (var2_1.weight >= var4_4.weight) ** GOTO lbl52
            var2_1.next = var4_4;
            var3_2 = var2_1;
            continue;
lbl-1000: // 1 sources:
            {
                var3_2 = var5_5;
lbl52: // 2 sources:
                ** while ((var5_5 = var3_2.next) != null && var2_1.weight >= var5_5.weight)
            }
lbl53: // 1 sources:
            var2_1.next = var5_5;
            var3_2.next = var2_1;
            var3_2 = var2_1;
            var2_1 = var4_4;
        } while (true);
    }

    @Override
    public boolean postMortem(PostMortem postMortem) {
        for (int i2 = 0; i2 < this.domain; ++i2) {
            if (this.postMortem(i2)) continue;
            JSONzip.log("\nBad huff ");
            JSONzip.logchar(i2, i2);
            return false;
        }
        return this.table.postMortem(((Huff)postMortem).table);
    }

    public int read(BitReader bitReader) throws JSONException {
        try {
            this.width = 0;
            Symbol symbol = this.table;
            while (symbol.integer == -1) {
                ++this.width;
                if (bitReader.bit()) {
                    symbol = symbol.one;
                    continue;
                }
                symbol = symbol.zero;
            }
            this.tick(symbol.integer);
            int n2 = symbol.integer;
            return n2;
        }
        catch (Throwable var1_2) {
            throw new JSONException(var1_2);
        }
    }

    public void tick(int n2) {
        Symbol symbol = this.symbols[n2];
        ++symbol.weight;
        this.upToDate = false;
    }

    public void tick(int n2, int n3) {
        while (n2 <= n3) {
            this.tick(n2);
            ++n2;
        }
    }

    public void write(int n2, BitWriter bitWriter) throws JSONException {
        this.width = 0;
        this.write(this.symbols[n2], bitWriter);
        this.tick(n2);
    }

    private static class Symbol
    implements PostMortem {
        public Symbol back;
        public final int integer;
        public Symbol next;
        public Symbol one;
        public long weight;
        public Symbol zero;

        public Symbol(int n2) {
            this.integer = n2;
            this.weight = 0;
            this.next = null;
            this.back = null;
            this.one = null;
            this.zero = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean postMortem(PostMortem postMortem) {
            boolean bl2 = true;
            boolean bl3 = true;
            postMortem = (Symbol)postMortem;
            if (this.integer != postMortem.integer) return false;
            if (this.weight != postMortem.weight) {
                return false;
            }
            boolean bl4 = this.back != null;
            if (postMortem.back == null) return false;
            if (bl4 != bl2) return false;
            Symbol symbol = this.zero;
            Symbol symbol2 = this.one;
            if (symbol == null) {
                if (postMortem.zero != null) return false;
            } else {
                bl3 = symbol.postMortem(postMortem.zero);
            }
            if (symbol2 != null) return symbol2.postMortem(postMortem.one);
            if (postMortem.one != null) return false;
            return bl3;
        }
    }

}

