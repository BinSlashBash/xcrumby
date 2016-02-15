/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import java.io.PrintStream;
import org.json.zip.Huff;
import org.json.zip.MapKeep;
import org.json.zip.None;
import org.json.zip.PostMortem;
import org.json.zip.TrieKeep;

public abstract class JSONzip
implements None,
PostMortem {
    public static final byte[] bcd;
    public static final int end = 256;
    public static final int endOfNumber;
    public static final long int14 = 16384;
    public static final long int4 = 16;
    public static final long int7 = 128;
    public static final int maxSubstringLength = 10;
    public static final int minSubstringLength = 3;
    public static final boolean probe = false;
    public static final int substringLimit = 40;
    public static final int[] twos;
    public static final int zipArrayString = 6;
    public static final int zipArrayValue = 7;
    public static final int zipEmptyArray = 1;
    public static final int zipEmptyObject = 0;
    public static final int zipFalse = 3;
    public static final int zipNull = 4;
    public static final int zipObject = 5;
    public static final int zipTrue = 2;
    protected final Huff namehuff = new Huff(257);
    protected final MapKeep namekeep = new MapKeep(9);
    protected final MapKeep stringkeep = new MapKeep(11);
    protected final Huff substringhuff = new Huff(257);
    protected final TrieKeep substringkeep = new TrieKeep(12);
    protected final MapKeep values = new MapKeep(10);

    static {
        twos = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536};
        bcd = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 46, 45, 43, 69};
        endOfNumber = bcd.length;
    }

    protected JSONzip() {
        this.namehuff.tick(32, 125);
        this.namehuff.tick(97, 122);
        this.namehuff.tick(256);
        this.namehuff.tick(256);
        this.substringhuff.tick(32, 125);
        this.substringhuff.tick(97, 122);
        this.substringhuff.tick(256);
        this.substringhuff.tick(256);
    }

    static void log() {
        JSONzip.log("\n");
    }

    static void log(int n2) {
        JSONzip.log(new StringBuffer().append(n2).append(" ").toString());
    }

    static void log(int n2, int n3) {
        JSONzip.log(new StringBuffer().append(n2).append(":").append(n3).append(" ").toString());
    }

    static void log(String string2) {
        System.out.print(string2);
    }

    static void logchar(int n2, int n3) {
        if (n2 > 32 && n2 <= 125) {
            JSONzip.log(new StringBuffer().append("'").append((char)n2).append("':").append(n3).append(" ").toString());
            return;
        }
        JSONzip.log(n2, n3);
    }

    protected void begin() {
        this.namehuff.generate();
        this.substringhuff.generate();
    }

    @Override
    public boolean postMortem(PostMortem postMortem) {
        postMortem = (JSONzip)postMortem;
        if (this.namehuff.postMortem(postMortem.namehuff) && this.namekeep.postMortem(postMortem.namekeep) && this.stringkeep.postMortem(postMortem.stringkeep) && this.substringhuff.postMortem(postMortem.substringhuff) && this.substringkeep.postMortem(postMortem.substringkeep) && this.values.postMortem(postMortem.values)) {
            return true;
        }
        return false;
    }
}

