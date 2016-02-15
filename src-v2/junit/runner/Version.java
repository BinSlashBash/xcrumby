/*
 * Decompiled with CFR 0_110.
 */
package junit.runner;

import java.io.PrintStream;

public class Version {
    private Version() {
    }

    public static String id() {
        return "4.11";
    }

    public static void main(String[] arrstring) {
        System.out.println(Version.id());
    }
}

