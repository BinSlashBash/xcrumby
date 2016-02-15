package org.json.zip;

import org.json.JSONException;

public class Huff implements None, PostMortem {
    private final int domain;
    private final Symbol[] symbols;
    private Symbol table;
    private boolean upToDate;
    private int width;

    private static class Symbol implements PostMortem {
        public Symbol back;
        public final int integer;
        public Symbol next;
        public Symbol one;
        public long weight;
        public Symbol zero;

        public Symbol(int integer) {
            this.integer = integer;
            this.weight = 0;
            this.next = null;
            this.back = null;
            this.one = null;
            this.zero = null;
        }

        public boolean postMortem(PostMortem pm) {
            boolean z = true;
            boolean result = true;
            Symbol that = (Symbol) pm;
            if (this.integer != that.integer || this.weight != that.weight) {
                return false;
            }
            boolean z2;
            if (this.back != null) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (that.back == null) {
                z = false;
            }
            if (z2 != z) {
                return false;
            }
            Symbol zero = this.zero;
            Symbol one = this.one;
            if (zero != null) {
                result = zero.postMortem(that.zero);
            } else if (that.zero != null) {
                return false;
            }
            if (one != null) {
                result = one.postMortem(that.one);
            } else if (that.one != null) {
                return false;
            }
            return result;
        }
    }

    public Huff(int domain) {
        int i;
        this.upToDate = false;
        this.domain = domain;
        int length = (domain * 2) - 1;
        this.symbols = new Symbol[length];
        for (i = 0; i < domain; i++) {
            this.symbols[i] = new Symbol(i);
        }
        for (i = domain; i < length; i++) {
            this.symbols[i] = new Symbol(-1);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void generate() {
        /*
        r13 = this;
        r12 = 0;
        r8 = r13.upToDate;
        if (r8 != 0) goto L_0x0069;
    L_0x0005:
        r8 = r13.symbols;
        r9 = 0;
        r2 = r8[r9];
        r5 = r2;
        r13.table = r12;
        r2.next = r12;
        r3 = 1;
    L_0x0010:
        r8 = r13.domain;
        if (r3 >= r8) goto L_0x0043;
    L_0x0014:
        r8 = r13.symbols;
        r7 = r8[r3];
        r8 = r7.weight;
        r10 = r2.weight;
        r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r8 >= 0) goto L_0x0026;
    L_0x0020:
        r7.next = r2;
        r2 = r7;
    L_0x0023:
        r3 = r3 + 1;
        goto L_0x0010;
    L_0x0026:
        r8 = r7.weight;
        r10 = r5.weight;
        r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r8 >= 0) goto L_0x002f;
    L_0x002e:
        r5 = r2;
    L_0x002f:
        r4 = r5.next;
        if (r4 == 0) goto L_0x003b;
    L_0x0033:
        r8 = r7.weight;
        r10 = r4.weight;
        r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r8 >= 0) goto L_0x0041;
    L_0x003b:
        r7.next = r4;
        r5.next = r7;
        r5 = r7;
        goto L_0x0023;
    L_0x0041:
        r5 = r4;
        goto L_0x002f;
    L_0x0043:
        r0 = r13.domain;
        r5 = r2;
    L_0x0046:
        r1 = r2;
        r6 = r1.next;
        r2 = r6.next;
        r8 = r13.symbols;
        r7 = r8[r0];
        r0 = r0 + 1;
        r8 = r1.weight;
        r10 = r6.weight;
        r8 = r8 + r10;
        r7.weight = r8;
        r7.zero = r1;
        r7.one = r6;
        r7.back = r12;
        r1.back = r7;
        r6.back = r7;
        if (r2 != 0) goto L_0x006a;
    L_0x0064:
        r13.table = r7;
        r8 = 1;
        r13.upToDate = r8;
    L_0x0069:
        return;
    L_0x006a:
        r8 = r7.weight;
        r10 = r2.weight;
        r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r8 >= 0) goto L_0x0078;
    L_0x0072:
        r7.next = r2;
        r2 = r7;
        r5 = r2;
        goto L_0x0046;
    L_0x0077:
        r5 = r4;
    L_0x0078:
        r4 = r5.next;
        if (r4 == 0) goto L_0x0084;
    L_0x007c:
        r8 = r7.weight;
        r10 = r4.weight;
        r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r8 >= 0) goto L_0x0077;
    L_0x0084:
        r7.next = r4;
        r5.next = r7;
        r5 = r7;
        goto L_0x0046;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.zip.Huff.generate():void");
    }

    private boolean postMortem(int integer) {
        boolean z = true;
        int[] bits = new int[this.domain];
        Symbol symbol = this.symbols[integer];
        if (symbol.integer != integer) {
            return false;
        }
        int i = 0;
        while (true) {
            Symbol back = symbol.back;
            if (back == null) {
                break;
            }
            if (back.zero == symbol) {
                bits[i] = 0;
            } else if (back.one != symbol) {
                return false;
            } else {
                bits[i] = 1;
            }
            i++;
            symbol = back;
        }
        if (symbol != this.table) {
            return false;
        }
        this.width = 0;
        symbol = this.table;
        while (symbol.integer == -1) {
            i--;
            if (bits[i] != 0) {
                symbol = symbol.one;
            } else {
                symbol = symbol.zero;
            }
        }
        if (!(symbol.integer == integer && i == 0)) {
            z = false;
        }
        return z;
    }

    public boolean postMortem(PostMortem pm) {
        int integer = 0;
        while (integer < this.domain) {
            if (postMortem(integer)) {
                integer++;
            } else {
                JSONzip.log("\nBad huff ");
                JSONzip.logchar(integer, integer);
                return false;
            }
        }
        return this.table.postMortem(((Huff) pm).table);
    }

    public int read(BitReader bitreader) throws JSONException {
        try {
            this.width = 0;
            Symbol symbol = this.table;
            while (symbol.integer == -1) {
                this.width++;
                symbol = bitreader.bit() ? symbol.one : symbol.zero;
            }
            tick(symbol.integer);
            return symbol.integer;
        } catch (Throwable e) {
            JSONException jSONException = new JSONException(e);
        }
    }

    public void tick(int value) {
        Symbol symbol = this.symbols[value];
        symbol.weight++;
        this.upToDate = false;
    }

    public void tick(int from, int to) {
        for (int value = from; value <= to; value++) {
            tick(value);
        }
    }

    private void write(Symbol symbol, BitWriter bitwriter) throws JSONException {
        try {
            Symbol back = symbol.back;
            if (back != null) {
                this.width++;
                write(back, bitwriter);
                if (back.zero == symbol) {
                    bitwriter.zero();
                } else {
                    bitwriter.one();
                }
            }
        } catch (Throwable e) {
            JSONException jSONException = new JSONException(e);
        }
    }

    public void write(int value, BitWriter bitwriter) throws JSONException {
        this.width = 0;
        write(this.symbols[value], bitwriter);
        tick(value);
    }
}
