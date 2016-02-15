/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal;

import java.io.PrintStream;
import org.junit.internal.JUnitSystem;

public class RealSystem
implements JUnitSystem {
    @Override
    public PrintStream out() {
        return System.out;
    }
}

