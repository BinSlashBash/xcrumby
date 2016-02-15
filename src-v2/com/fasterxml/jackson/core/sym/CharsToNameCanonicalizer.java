/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.BitSet;

public final class CharsToNameCanonicalizer {
    protected static final int DEFAULT_T_SIZE = 64;
    public static final int HASH_MULT = 33;
    static final int MAX_COLL_CHAIN_LENGTH = 100;
    static final int MAX_ENTRIES_FOR_REUSE = 12000;
    protected static final int MAX_T_SIZE = 65536;
    static final CharsToNameCanonicalizer sBootstrapSymbolTable = new CharsToNameCanonicalizer();
    protected Bucket[] _buckets;
    protected boolean _canonicalize;
    protected boolean _dirty;
    protected final int _flags;
    private final int _hashSeed;
    protected int _indexMask;
    protected int _longestCollisionList;
    protected BitSet _overflows;
    protected CharsToNameCanonicalizer _parent;
    protected int _size;
    protected int _sizeThreshold;
    protected String[] _symbols;

    private CharsToNameCanonicalizer() {
        this._canonicalize = true;
        this._flags = -1;
        this._dirty = true;
        this._hashSeed = 0;
        this._longestCollisionList = 0;
        this.initTables(64);
    }

    private CharsToNameCanonicalizer(CharsToNameCanonicalizer charsToNameCanonicalizer, int n2, String[] arrstring, Bucket[] arrbucket, int n3, int n4, int n5) {
        this._parent = charsToNameCanonicalizer;
        this._flags = n2;
        this._canonicalize = JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(n2);
        this._symbols = arrstring;
        this._buckets = arrbucket;
        this._size = n3;
        this._hashSeed = n4;
        n2 = arrstring.length;
        this._sizeThreshold = CharsToNameCanonicalizer._thresholdSize(n2);
        this._indexMask = n2 - 1;
        this._longestCollisionList = n5;
        this._dirty = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String _addSymbol(char[] object, int n2, int n3, int n4, int n5) {
        Object object2;
        if (!this._dirty) {
            this.copyArrays();
            this._dirty = true;
        } else if (this._size >= this._sizeThreshold) {
            this.rehash();
            n5 = this._hashToIndex(this.calcHash((char[])object, n2, n3));
        }
        object = object2 = new String((char[])object, n2, n3);
        if (JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(this._flags)) {
            object = InternCache.instance.intern((String)object2);
        }
        ++this._size;
        if (this._symbols[n5] == null) {
            this._symbols[n5] = object;
            return object;
        }
        n2 = n5 >> 1;
        object2 = new Bucket((String)object, this._buckets[n2]);
        n3 = ((Bucket)object2).length;
        if (n3 > 100) {
            this._handleSpillOverflow(n2, (Bucket)object2);
            return object;
        }
        this._buckets[n2] = object2;
        this._longestCollisionList = Math.max(n3, this._longestCollisionList);
        return object;
    }

    private String _findSymbol2(char[] arrc, int n2, int n3, Bucket bucket) {
        while (bucket != null) {
            String string2 = bucket.has(arrc, n2, n3);
            if (string2 != null) {
                return string2;
            }
            bucket = bucket.next;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _handleSpillOverflow(int n2, Bucket bucket) {
        if (this._overflows == null) {
            this._overflows = new BitSet();
            this._overflows.set(n2);
        } else if (this._overflows.get(n2)) {
            if (JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(this._flags)) {
                this.reportTooManyCollisions(100);
            }
            this._canonicalize = false;
        } else {
            this._overflows.set(n2);
        }
        this._symbols[n2 + n2] = bucket.symbol;
        this._buckets[n2] = null;
        this._size -= bucket.length;
        this._longestCollisionList = -1;
    }

    private static int _thresholdSize(int n2) {
        return n2 - (n2 >> 2);
    }

    private void copyArrays() {
        String[] arrstring = this._symbols;
        this._symbols = Arrays.copyOf(arrstring, arrstring.length);
        arrstring = this._buckets;
        this._buckets = (Bucket[])Arrays.copyOf(arrstring, arrstring.length);
    }

    public static CharsToNameCanonicalizer createRoot() {
        long l2 = System.currentTimeMillis();
        return CharsToNameCanonicalizer.createRoot((int)l2 + (int)(l2 >>> 32) | 1);
    }

    protected static CharsToNameCanonicalizer createRoot(int n2) {
        return sBootstrapSymbolTable.makeOrphan(n2);
    }

    private void initTables(int n2) {
        this._symbols = new String[n2];
        this._buckets = new Bucket[n2 >> 1];
        this._indexMask = n2 - 1;
        this._size = 0;
        this._longestCollisionList = 0;
        this._sizeThreshold = CharsToNameCanonicalizer._thresholdSize(n2);
    }

    private CharsToNameCanonicalizer makeOrphan(int n2) {
        return new CharsToNameCanonicalizer(null, -1, this._symbols, this._buckets, this._size, n2, this._longestCollisionList);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void mergeChild(CharsToNameCanonicalizer charsToNameCanonicalizer) {
        if (charsToNameCanonicalizer.size() > 12000) {
            synchronized (this) {
                this.initTables(256);
                this._dirty = false;
                return;
            }
        }
        if (charsToNameCanonicalizer.size() > this.size()) {
            synchronized (this) {
                this._symbols = charsToNameCanonicalizer._symbols;
                this._buckets = charsToNameCanonicalizer._buckets;
                this._size = charsToNameCanonicalizer._size;
                this._sizeThreshold = charsToNameCanonicalizer._sizeThreshold;
                this._indexMask = charsToNameCanonicalizer._indexMask;
                this._longestCollisionList = charsToNameCanonicalizer._longestCollisionList;
                this._dirty = false;
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void rehash() {
        int n2;
        Object object;
        int n3;
        int n4 = this._symbols.length;
        int n5 = n4 + n4;
        if (n5 > 65536) {
            this._size = 0;
            this._canonicalize = false;
            this._symbols = new String[64];
            this._buckets = new Bucket[32];
            this._indexMask = 63;
            this._dirty = true;
            return;
        }
        Object object2 = this._symbols;
        Bucket[] arrbucket = this._buckets;
        this._symbols = new String[n5];
        this._buckets = new Bucket[n5 >> 1];
        this._indexMask = n5 - 1;
        this._sizeThreshold = CharsToNameCanonicalizer._thresholdSize(n5);
        int n6 = 0;
        n5 = 0;
        for (n2 = 0; n2 < n4; ++n2) {
            object = object2[n2];
            n3 = n6;
            int n7 = n5;
            if (object != null) {
                n3 = n6 + 1;
                n6 = this._hashToIndex(this.calcHash((String)object));
                if (this._symbols[n6] == null) {
                    this._symbols[n6] = object;
                    n7 = n5;
                } else {
                    this._buckets[n6 >>= 1] = object = new Bucket((String)object, this._buckets[n6]);
                    n7 = Math.max(n5, ((Bucket)object).length);
                }
            }
            n6 = n3;
            n5 = n7;
        }
        n2 = 0;
        n3 = n5;
        n5 = n2;
        n2 = n6;
        do {
            if (n5 >= n4 >> 1) {
                this._longestCollisionList = n3;
                this._overflows = null;
                if (n2 == this._size) return;
                throw new Error("Internal error on SymbolTable.rehash(): had " + this._size + " entries; now have " + n2 + ".");
            }
            object2 = arrbucket[n5];
            n6 = n3;
            while (object2 != null) {
                ++n2;
                object = ((Bucket)object2).symbol;
                n3 = this._hashToIndex(this.calcHash((String)object));
                if (this._symbols[n3] == null) {
                    this._symbols[n3] = object;
                } else {
                    this._buckets[n3 >>= 1] = object = new Bucket((String)object, this._buckets[n3]);
                    n6 = Math.max(n6, ((Bucket)object).length);
                }
                object2 = ((Bucket)object2).next;
            }
            ++n5;
            n3 = n6;
        } while (true);
    }

    public int _hashToIndex(int n2) {
        return this._indexMask & n2 + (n2 >>> 15);
    }

    public int bucketCount() {
        return this._symbols.length;
    }

    public int calcHash(String string2) {
        int n2;
        int n3 = string2.length();
        int n4 = this._hashSeed;
        for (n2 = 0; n2 < n3; ++n2) {
            n4 = n4 * 33 + string2.charAt(n2);
        }
        n2 = n4;
        if (n4 == 0) {
            n2 = 1;
        }
        return n2;
    }

    public int calcHash(char[] arrc, int n2, int n3) {
        int n4 = this._hashSeed;
        for (int i2 = n2; i2 < n2 + n3; ++i2) {
            n4 = n4 * 33 + arrc[i2];
        }
        n2 = n4;
        if (n4 == 0) {
            n2 = 1;
        }
        return n2;
    }

    public int collisionCount() {
        int n2 = 0;
        for (Bucket bucket : this._buckets) {
            int n3 = n2;
            if (bucket != null) {
                n3 = n2 + bucket.length;
            }
            n2 = n3;
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String findSymbol(char[] arrc, int n2, int n3, int n4) {
        Bucket bucket;
        String string2;
        if (n3 < 1) {
            return "";
        }
        if (!this._canonicalize) {
            return new String(arrc, n2, n3);
        }
        int n5 = this._hashToIndex(n4);
        String string3 = this._symbols[n5];
        if (string3 == null) return this._addSymbol(arrc, n2, n3, n4, n5);
        if (string3.length() == n3) {
            int n6 = 0;
            while (string3.charAt(n6) == arrc[n2 + n6]) {
                int n7;
                n6 = n7 = n6 + 1;
                if (n7 != n3) continue;
                return string3;
            }
        }
        if ((bucket = this._buckets[n5 >> 1]) == null) return this._addSymbol(arrc, n2, n3, n4, n5);
        string3 = string2 = bucket.has(arrc, n2, n3);
        if (string2 != null) return string3;
        string3 = string2 = this._findSymbol2(arrc, n2, n3, bucket.next);
        if (string2 != null) return string3;
        return this._addSymbol(arrc, n2, n3, n4, n5);
    }

    public int hashSeed() {
        return this._hashSeed;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CharsToNameCanonicalizer makeChild(int n2) {
        synchronized (this) {
            String[] arrstring = this._symbols;
            Bucket[] arrbucket = this._buckets;
            int n3 = this._size;
            int n4 = this._hashSeed;
            int n5 = this._longestCollisionList;
            return new CharsToNameCanonicalizer(this, n2, arrstring, arrbucket, n3, n4, n5);
        }
    }

    public int maxCollisionLength() {
        return this._longestCollisionList;
    }

    public boolean maybeDirty() {
        return this._dirty;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void release() {
        if (!this.maybeDirty() || this._parent == null || !this._canonicalize) {
            return;
        }
        this._parent.mergeChild(this);
        this._dirty = false;
    }

    protected void reportTooManyCollisions(int n2) {
        throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._size + ") now exceeds maximum, " + n2 + " -- suspect a DoS attack based on hash collisions");
    }

    public int size() {
        return this._size;
    }

    static final class Bucket {
        private final int length;
        private final Bucket next;
        private final String symbol;

        /*
         * Enabled aggressive block sorting
         */
        public Bucket(String string2, Bucket bucket) {
            this.symbol = string2;
            this.next = bucket;
            int n2 = bucket == null ? 1 : bucket.length + 1;
            this.length = n2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public String has(char[] arrc, int n2, int n3) {
            int n4;
            if (this.symbol.length() != n3) {
                return null;
            }
            int n5 = 0;
            do {
                if (this.symbol.charAt(n5) != arrc[n2 + n5]) return null;
                n5 = n4 = n5 + 1;
            } while (n4 < n3);
            return this.symbol;
        }
    }

}

