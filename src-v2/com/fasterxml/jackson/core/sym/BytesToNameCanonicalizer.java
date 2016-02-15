/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.sym.Name;
import com.fasterxml.jackson.core.sym.Name1;
import com.fasterxml.jackson.core.sym.Name2;
import com.fasterxml.jackson.core.sym.Name3;
import com.fasterxml.jackson.core.sym.NameN;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicReference;

public final class BytesToNameCanonicalizer {
    private static final int DEFAULT_T_SIZE = 64;
    static final int INITIAL_COLLISION_LEN = 32;
    static final int LAST_VALID_BUCKET = 254;
    private static final int MAX_COLL_CHAIN_LENGTH = 100;
    private static final int MAX_ENTRIES_FOR_REUSE = 6000;
    private static final int MAX_T_SIZE = 65536;
    static final int MIN_HASH_SIZE = 16;
    private static final int MULT = 33;
    private static final int MULT2 = 65599;
    private static final int MULT3 = 31;
    protected int _collCount;
    protected int _collEnd;
    protected Bucket[] _collList;
    private boolean _collListShared;
    protected int _count;
    protected final boolean _failOnDoS;
    protected int[] _hash;
    protected int _hashMask;
    private boolean _hashShared;
    protected boolean _intern;
    protected int _longestCollisionList;
    protected Name[] _mainNames;
    private boolean _namesShared;
    private transient boolean _needRehash;
    protected BitSet _overflows;
    protected final BytesToNameCanonicalizer _parent;
    private final int _seed;
    protected final AtomicReference<TableInfo> _tableInfo;

    /*
     * Enabled aggressive block sorting
     */
    private BytesToNameCanonicalizer(int n2, boolean bl2, int n3, boolean bl3) {
        this._parent = null;
        this._seed = n3;
        this._intern = bl2;
        this._failOnDoS = bl3;
        if (n2 < 16) {
            n3 = 16;
        } else {
            n3 = n2;
            if ((n2 - 1 & n2) != 0) {
                for (n3 = 16; n3 < n2; n3 += n3) {
                }
            }
        }
        this._tableInfo = new AtomicReference<TableInfo>(this.initTableInfo(n3));
    }

    private BytesToNameCanonicalizer(BytesToNameCanonicalizer bytesToNameCanonicalizer, boolean bl2, int n2, boolean bl3, TableInfo tableInfo) {
        this._parent = bytesToNameCanonicalizer;
        this._seed = n2;
        this._intern = bl2;
        this._failOnDoS = bl3;
        this._tableInfo = null;
        this._count = tableInfo.count;
        this._hashMask = tableInfo.mainHashMask;
        this._hash = tableInfo.mainHash;
        this._mainNames = tableInfo.mainNames;
        this._collList = tableInfo.collList;
        this._collCount = tableInfo.collCount;
        this._collEnd = tableInfo.collEnd;
        this._longestCollisionList = tableInfo.longestCollisionList;
        this._needRehash = false;
        this._hashShared = true;
        this._namesShared = true;
        this._collListShared = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _addSymbol(int n2, Name object) {
        int n3;
        if (this._hashShared) {
            this.unshareMain();
        }
        if (this._needRehash) {
            this.rehash();
        }
        ++this._count;
        int n4 = n2 & this._hashMask;
        if (this._mainNames[n4] == null) {
            this._hash[n4] = n2 << 8;
            if (this._namesShared) {
                this.unshareNames();
            }
            this._mainNames[n4] = object;
        } else {
            if (this._collListShared) {
                this.unshareCollision();
            }
            ++this._collCount;
            int n5 = this._hash[n4];
            n2 = n5 & 255;
            if (n2 == 0) {
                if (this._collEnd <= 254) {
                    n2 = n3 = this._collEnd++;
                    if (n3 >= this._collList.length) {
                        this.expandCollision();
                        n2 = n3;
                    }
                } else {
                    n2 = this.findBestBucket();
                }
                this._hash[n4] = n5 & -256 | n2 + 1;
            } else {
                --n2;
            }
            if (((Bucket)(object = new Bucket((Name)object, this._collList[n2]))).length > 100) {
                this._handleSpillOverflow(n2, (Bucket)object);
            } else {
                this._collList[n2] = object;
                this._longestCollisionList = Math.max(((Bucket)object).length, this._longestCollisionList);
            }
        }
        if (this._count <= (n2 = this._hash.length) >> 1) return;
        {
            n3 = n2 >> 2;
            if (this._count > n2 - n3) {
                this._needRehash = true;
                return;
            } else {
                if (this._collCount < n3) return;
                {
                    this._needRehash = true;
                    return;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _handleSpillOverflow(int n2, Bucket bucket) {
        if (this._overflows == null) {
            this._overflows = new BitSet();
            this._overflows.set(n2);
        } else if (this._overflows.get(n2)) {
            if (this._failOnDoS) {
                this.reportTooManyCollisions(100);
            }
            this._intern = false;
        } else {
            this._overflows.set(n2);
        }
        this._collList[n2] = null;
        this._count -= bucket.length;
        this._longestCollisionList = -1;
    }

    protected static int[] calcQuads(byte[] arrby) {
        int n2 = arrby.length;
        int[] arrn = new int[(n2 + 3) / 4];
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            int n5 = arrby[n3] & 255;
            int n6 = n4 = n3 + 1;
            n3 = n5;
            if (n4 < n2) {
                n5 = n5 << 8 | arrby[n4] & 255;
                n6 = ++n4;
                n3 = n5;
                if (n4 < n2) {
                    n5 = n5 << 8 | arrby[n4] & 255;
                    n6 = ++n4;
                    n3 = n5;
                    if (n4 < n2) {
                        n3 = n5 << 8 | arrby[n4] & 255;
                        n6 = n4;
                    }
                }
            }
            arrn[n6 >> 2] = n3;
            n3 = n6 + 1;
        }
        return arrn;
    }

    private static Name constructName(int n2, String string2, int n3, int n4) {
        if (n4 == 0) {
            return new Name1(string2, n2, n3);
        }
        return new Name2(string2, n2, n3, n4);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Name constructName(int n2, String string2, int[] arrn, int n3) {
        if (n3 >= 4) return NameN.construct(string2, n2, arrn, n3);
        switch (n3) {
            default: {
                return NameN.construct(string2, n2, arrn, n3);
            }
            case 1: {
                return new Name1(string2, n2, arrn[0]);
            }
            case 2: {
                return new Name2(string2, n2, arrn[0], arrn[1]);
            }
            case 3: 
        }
        return new Name3(string2, n2, arrn[0], arrn[1], arrn[2]);
    }

    public static BytesToNameCanonicalizer createRoot() {
        long l2 = System.currentTimeMillis();
        return BytesToNameCanonicalizer.createRoot((int)l2 + (int)(l2 >>> 32) | 1);
    }

    protected static BytesToNameCanonicalizer createRoot(int n2) {
        return new BytesToNameCanonicalizer(64, true, n2, true);
    }

    private void expandCollision() {
        Bucket[] arrbucket = this._collList;
        this._collList = Arrays.copyOf(arrbucket, arrbucket.length * 2);
    }

    private int findBestBucket() {
        Bucket[] arrbucket = this._collList;
        int n2 = Integer.MAX_VALUE;
        int n3 = -1;
        int n4 = this._collEnd;
        for (int i2 = 0; i2 < n4; ++i2) {
            int n5 = arrbucket[i2].length;
            int n6 = n2;
            if (n5 < n2) {
                if (n5 == 1) {
                    return i2;
                }
                n6 = n5;
                n3 = i2;
            }
            n2 = n6;
        }
        return n3;
    }

    public static Name getEmptyName() {
        return Name1.getEmptyName();
    }

    private TableInfo initTableInfo(int n2) {
        return new TableInfo(0, n2 - 1, new int[n2], new Name[n2], null, 0, 0, 0);
    }

    private void mergeChild(TableInfo tableInfo) {
        int n2 = tableInfo.count;
        TableInfo tableInfo2 = this._tableInfo.get();
        if (n2 == tableInfo2.count) {
            return;
        }
        if (n2 > 6000) {
            tableInfo = this.initTableInfo(64);
        }
        this._tableInfo.compareAndSet(tableInfo2, tableInfo);
    }

    private void nukeSymbols() {
        this._count = 0;
        this._longestCollisionList = 0;
        Arrays.fill(this._hash, 0);
        Arrays.fill(this._mainNames, null);
        Arrays.fill(this._collList, null);
        this._collCount = 0;
        this._collEnd = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void rehash() {
        int n2;
        int n3;
        Bucket[] arrbucket;
        int n4;
        this._needRehash = false;
        this._namesShared = false;
        int n5 = this._hash.length;
        int n6 = n5 + n5;
        if (n6 > 65536) {
            this.nukeSymbols();
            return;
        }
        this._hash = new int[n6];
        this._hashMask = n6 - 1;
        Object object = this._mainNames;
        this._mainNames = new Name[n6];
        n6 = 0;
        for (n3 = 0; n3 < n5; ++n3) {
            arrbucket = object[n3];
            n4 = n6;
            if (arrbucket != null) {
                n4 = n6 + 1;
                n6 = arrbucket.hashCode();
                n2 = n6 & this._hashMask;
                this._mainNames[n2] = arrbucket;
                this._hash[n2] = n6 << 8;
            }
            n6 = n4;
        }
        int n7 = this._collEnd;
        if (n7 == 0) {
            this._longestCollisionList = 0;
            return;
        }
        this._collCount = 0;
        this._collEnd = 0;
        this._collListShared = false;
        n4 = 0;
        arrbucket = this._collList;
        this._collList = new Bucket[arrbucket.length];
        n3 = 0;
        do {
            if (n3 >= n7) {
                this._longestCollisionList = n4;
                if (n6 == this._count) return;
                throw new RuntimeException("Internal error: count after rehash " + n6 + "; should be " + this._count);
            }
            object = arrbucket[n3];
            while (object != null) {
                n5 = n6 + 1;
                Object object2 = object.name;
                n6 = object2.hashCode();
                int n8 = n6 & this._hashMask;
                int n9 = this._hash[n8];
                if (this._mainNames[n8] == null) {
                    this._hash[n8] = n6 << 8;
                    this._mainNames[n8] = object2;
                } else {
                    ++this._collCount;
                    n6 = n9 & 255;
                    if (n6 == 0) {
                        if (this._collEnd <= 254) {
                            n6 = n2 = this._collEnd++;
                            if (n2 >= this._collList.length) {
                                this.expandCollision();
                                n6 = n2;
                            }
                        } else {
                            n6 = this.findBestBucket();
                        }
                        this._hash[n8] = n9 & -256 | n6 + 1;
                    } else {
                        --n6;
                    }
                    this._collList[n6] = object2 = new Bucket((Name)object2, this._collList[n6]);
                    n4 = Math.max(n4, ((Bucket)object2).length);
                }
                object = object.next;
                n6 = n5;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void unshareCollision() {
        Bucket[] arrbucket = this._collList;
        this._collList = arrbucket == null ? new Bucket[32] : Arrays.copyOf(arrbucket, arrbucket.length);
        this._collListShared = false;
    }

    private void unshareMain() {
        int[] arrn = this._hash;
        this._hash = Arrays.copyOf(arrn, arrn.length);
        this._hashShared = false;
    }

    private void unshareNames() {
        Name[] arrname = this._mainNames;
        this._mainNames = Arrays.copyOf(arrname, arrname.length);
        this._namesShared = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Name addName(String object, int n2, int n3) {
        Object object2 = object;
        if (this._intern) {
            object2 = InternCache.instance.intern((String)object);
        }
        int n4 = n3 == 0 ? this.calcHash(n2) : this.calcHash(n2, n3);
        object = BytesToNameCanonicalizer.constructName(n4, (String)object2, n2, n3);
        this._addSymbol(n4, (Name)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Name addName(String object, int[] arrn, int n2) {
        Object object2 = object;
        if (this._intern) {
            object2 = InternCache.instance.intern((String)object);
        }
        int n3 = n2 < 3 ? (n2 == 1 ? this.calcHash(arrn[0]) : this.calcHash(arrn[0], arrn[1])) : this.calcHash(arrn, n2);
        object = BytesToNameCanonicalizer.constructName(n3, (String)object2, arrn, n2);
        this._addSymbol(n3, (Name)object);
        return object;
    }

    public int bucketCount() {
        return this._hash.length;
    }

    public int calcHash(int n2) {
        n2 ^= this._seed;
        n2 += n2 >>> 15;
        return n2 ^ n2 >>> 9;
    }

    public int calcHash(int n2, int n3) {
        n2 = (n2 ^ n2 >>> 15) + n3 * 33 ^ this._seed;
        return n2 + (n2 >>> 7);
    }

    public int calcHash(int[] arrn, int n2) {
        if (n2 < 3) {
            throw new IllegalArgumentException();
        }
        int n3 = arrn[0] ^ this._seed;
        n3 = ((n3 + (n3 >>> 9)) * 33 + arrn[1]) * 65599;
        n3 = n3 + (n3 >>> 15) ^ arrn[2];
        int n4 = n3 + (n3 >>> 17);
        for (n3 = 3; n3 < n2; ++n3) {
            n4 = n4 * 31 ^ arrn[n3];
            n4 += n4 >>> 3;
            n4 ^= n4 << 7;
        }
        n2 = n4 + (n4 >>> 15);
        return n2 ^ n2 << 9;
    }

    public int collisionCount() {
        return this._collCount;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Name findName(int n2) {
        Object object;
        int n3 = this.calcHash(n2);
        int n4 = n3 & this._hashMask;
        int n5 = this._hash[n4];
        if ((n5 >> 8 ^ n3) << 8 == 0) {
            Name name = this._mainNames[n4];
            if (name == null) {
                return null;
            }
            object = name;
            if (name.equals(n2)) {
                return object;
            }
        } else if (n5 == 0) {
            return null;
        }
        if ((n4 = n5 & 255) <= 0) return null;
        object = this._collList[n4 - 1];
        if (object == null) return null;
        return object.find(n3, n2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Name findName(int n2, int n3) {
        Object object;
        int n4 = n3 == 0 ? this.calcHash(n2) : this.calcHash(n2, n3);
        int n5 = n4 & this._hashMask;
        int n6 = this._hash[n5];
        if ((n6 >> 8 ^ n4) << 8 == 0) {
            Name name = this._mainNames[n5];
            if (name == null) {
                return null;
            }
            object = name;
            if (name.equals(n2, n3)) {
                return object;
            }
        } else if (n6 == 0) {
            return null;
        }
        if ((n5 = n6 & 255) <= 0) return null;
        object = this._collList[n5 - 1];
        if (object == null) return null;
        return object.find(n4, n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Name findName(int[] arrn, int n2) {
        Object object;
        int n3 = 0;
        if (n2 < 3) {
            int n4 = arrn[0];
            n2 = n2 < 2 ? n3 : arrn[1];
            return this.findName(n4, n2);
        }
        n3 = this.calcHash(arrn, n2);
        int n5 = n3 & this._hashMask;
        int n6 = this._hash[n5];
        if ((n6 >> 8 ^ n3) << 8 == 0) {
            Name name;
            object = name = this._mainNames[n5];
            if (name == null) return object;
            object = name;
            if (name.equals(arrn, n2)) {
                return object;
            }
        } else if (n6 == 0) {
            return null;
        }
        if ((n5 = n6 & 255) <= 0) return null;
        object = this._collList[n5 - 1];
        if (object == null) return null;
        return object.find(n3, arrn, n2);
    }

    public int hashSeed() {
        return this._seed;
    }

    public BytesToNameCanonicalizer makeChild(int n2) {
        return new BytesToNameCanonicalizer(this, JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(n2), this._seed, JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(n2), this._tableInfo.get());
    }

    @Deprecated
    public BytesToNameCanonicalizer makeChild(boolean bl2, boolean bl3) {
        return new BytesToNameCanonicalizer(this, bl3, this._seed, true, this._tableInfo.get());
    }

    public int maxCollisionLength() {
        return this._longestCollisionList;
    }

    public boolean maybeDirty() {
        if (!this._hashShared) {
            return true;
        }
        return false;
    }

    public void release() {
        if (this._parent != null && this.maybeDirty()) {
            this._parent.mergeChild(new TableInfo(this));
            this._hashShared = true;
            this._namesShared = true;
            this._collListShared = true;
        }
    }

    protected void reportTooManyCollisions(int n2) {
        throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._count + ") now exceeds maximum, " + n2 + " -- suspect a DoS attack based on hash collisions");
    }

    public int size() {
        if (this._tableInfo != null) {
            return this._tableInfo.get().count;
        }
        return this._count;
    }

    private static final class Bucket {
        private final int hash;
        private final int length;
        protected final Name name;
        protected final Bucket next;

        /*
         * Enabled aggressive block sorting
         */
        Bucket(Name name, Bucket bucket) {
            this.name = name;
            this.next = bucket;
            int n2 = bucket == null ? 1 : bucket.length + 1;
            this.length = n2;
            this.hash = name.hashCode();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Name find(int n2, int n3, int n4) {
            if (this.hash == n2 && this.name.equals(n3, n4)) {
                return this.name;
            }
            Bucket bucket = this.next;
            while (bucket != null) {
                if (bucket.hash == n2) {
                    Name name;
                    Name name2 = name = bucket.name;
                    if (name.equals(n3, n4)) return name2;
                }
                bucket = bucket.next;
            }
            return null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Name find(int n2, int[] arrn, int n3) {
            if (this.hash == n2 && this.name.equals(arrn, n3)) {
                return this.name;
            }
            Bucket bucket = this.next;
            while (bucket != null) {
                if (bucket.hash == n2) {
                    Name name;
                    Name name2 = name = bucket.name;
                    if (name.equals(arrn, n3)) return name2;
                }
                bucket = bucket.next;
            }
            return null;
        }
    }

    private static final class TableInfo {
        public final int collCount;
        public final int collEnd;
        public final Bucket[] collList;
        public final int count;
        public final int longestCollisionList;
        public final int[] mainHash;
        public final int mainHashMask;
        public final Name[] mainNames;

        public TableInfo(int n2, int n3, int[] arrn, Name[] arrname, Bucket[] arrbucket, int n4, int n5, int n6) {
            this.count = n2;
            this.mainHashMask = n3;
            this.mainHash = arrn;
            this.mainNames = arrname;
            this.collList = arrbucket;
            this.collCount = n4;
            this.collEnd = n5;
            this.longestCollisionList = n6;
        }

        public TableInfo(BytesToNameCanonicalizer bytesToNameCanonicalizer) {
            this.count = bytesToNameCanonicalizer._count;
            this.mainHashMask = bytesToNameCanonicalizer._hashMask;
            this.mainHash = bytesToNameCanonicalizer._hash;
            this.mainNames = bytesToNameCanonicalizer._mainNames;
            this.collList = bytesToNameCanonicalizer._collList;
            this.collCount = bytesToNameCanonicalizer._collCount;
            this.collEnd = bytesToNameCanonicalizer._collEnd;
            this.longestCollisionList = bytesToNameCanonicalizer._longestCollisionList;
        }
    }

}

