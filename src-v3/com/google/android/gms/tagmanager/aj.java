package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0355d.C1367a;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class aj {
    private final Set<String> XU;
    private final String XV;

    public aj(String str, String... strArr) {
        this.XV = str;
        this.XU = new HashSet(strArr.length);
        for (Object add : strArr) {
            this.XU.add(add);
        }
    }

    boolean m1365a(Set<String> set) {
        return set.containsAll(this.XU);
    }

    public abstract boolean jX();

    public String kB() {
        return this.XV;
    }

    public Set<String> kC() {
        return this.XU;
    }

    public abstract C1367a m1366x(Map<String, C1367a> map);
}
