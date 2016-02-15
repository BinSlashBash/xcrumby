package org.json.zip;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.cast.Cast;

public abstract class JSONzip implements None, PostMortem {
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
    protected final Huff namehuff;
    protected final MapKeep namekeep;
    protected final MapKeep stringkeep;
    protected final Huff substringhuff;
    protected final TrieKeep substringkeep;
    protected final MapKeep values;

    static {
        twos = new int[]{zipEmptyArray, zipTrue, zipNull, 8, 16, 32, 64, TransportMediator.FLAG_KEY_MEDIA_NEXT, end, AdRequest.MAX_CONTENT_URL_LENGTH, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, AccessibilityNodeInfoCompat.ACTION_COPY, AccessibilityNodeInfoCompat.ACTION_PASTE, Cast.MAX_MESSAGE_LENGTH};
        bcd = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 46, (byte) 45, (byte) 43, (byte) 69};
        endOfNumber = bcd.length;
    }

    protected JSONzip() {
        this.namehuff = new Huff(257);
        this.namekeep = new MapKeep(9);
        this.stringkeep = new MapKeep(11);
        this.substringhuff = new Huff(257);
        this.substringkeep = new TrieKeep(12);
        this.values = new MapKeep(maxSubstringLength);
        this.namehuff.tick(32, 125);
        this.namehuff.tick(97, 122);
        this.namehuff.tick(end);
        this.namehuff.tick(end);
        this.substringhuff.tick(32, 125);
        this.substringhuff.tick(97, 122);
        this.substringhuff.tick(end);
        this.substringhuff.tick(end);
    }

    protected void begin() {
        this.namehuff.generate();
        this.substringhuff.generate();
    }

    static void log() {
        log("\n");
    }

    static void log(int integer) {
        log(new StringBuffer().append(integer).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString());
    }

    static void log(int integer, int width) {
        log(new StringBuffer().append(integer).append(":").append(width).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString());
    }

    static void log(String string) {
        System.out.print(string);
    }

    static void logchar(int integer, int width) {
        if (integer <= 32 || integer > 125) {
            log(integer, width);
        } else {
            log(new StringBuffer().append("'").append((char) integer).append("':").append(width).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString());
        }
    }

    public boolean postMortem(PostMortem pm) {
        JSONzip that = (JSONzip) pm;
        return (this.namehuff.postMortem(that.namehuff) && this.namekeep.postMortem(that.namekeep) && this.stringkeep.postMortem(that.stringkeep) && this.substringhuff.postMortem(that.substringhuff) && this.substringkeep.postMortem(that.substringkeep) && this.values.postMortem(that.values)) ? true : probe;
    }
}
