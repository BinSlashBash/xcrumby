/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.d;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class aj {
    private final Set<String> XU;
    private final String XV;

    public /* varargs */ aj(String string22, String ... arrstring) {
        this.XV = string22;
        this.XU = new HashSet<String>(arrstring.length);
        for (String string22 : arrstring) {
            this.XU.add(string22);
        }
    }

    boolean a(Set<String> set) {
        return set.containsAll(this.XU);
    }

    public abstract boolean jX();

    public String kB() {
        return this.XV;
    }

    public Set<String> kC() {
        return this.XU;
    }

    public abstract d.a x(Map<String, d.a> var1);
}

