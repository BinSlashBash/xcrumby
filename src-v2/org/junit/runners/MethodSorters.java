/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners;

import java.lang.reflect.Method;
import java.util.Comparator;
import org.junit.internal.MethodSorter;

public enum MethodSorters {
    NAME_ASCENDING(MethodSorter.NAME_ASCENDING),
    JVM(null),
    DEFAULT(MethodSorter.DEFAULT);
    
    private final Comparator<Method> fComparator;

    private MethodSorters(Comparator<Method> comparator) {
        this.fComparator = comparator;
    }

    public Comparator<Method> getComparator() {
        return this.fComparator;
    }
}

