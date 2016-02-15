/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.kn;
import com.google.android.gms.internal.kp;
import com.google.android.gms.internal.kt;
import com.google.android.gms.internal.kv;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class kq<M extends kp<M>, T> {
    protected final Class<T> adV;
    protected final boolean adW;
    protected final int tag;
    protected final int type;

    private kq(int n2, Class<T> class_, int n3, boolean bl2) {
        this.type = n2;
        this.adV = class_;
        this.tag = n3;
        this.adW = bl2;
    }

    public static <M extends kp<M>, T extends kt> kq<M, T> a(int n2, Class<T> class_, int n3) {
        return new kq<M, T>(n2, class_, n3, false);
    }

    protected void a(kv kv2, List<Object> list) {
        list.add(this.o(kn.n(kv2.adZ)));
    }

    protected boolean dd(int n2) {
        if (n2 == this.tag) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    final T f(List<kv> object) {
        void var5_10;
        int n2 = 0;
        if (object == null) {
            object = null;
            return (T)object;
        }
        if (this.adW) {
            int n3;
            ArrayList<Object> arrayList = new ArrayList<Object>();
            for (n3 = 0; n3 < object.size(); ++n3) {
                kv kv2 = (kv)object.get(n3);
                if (!this.dd(kv2.tag) || kv2.adZ.length == 0) continue;
                this.a(kv2, arrayList);
            }
            int n4 = arrayList.size();
            if (n4 == 0) {
                return null;
            }
            T t2 = this.adV.cast(Array.newInstance(this.adV.getComponentType(), n4));
            n3 = n2;
            do {
                object = t2;
                if (n3 >= n4) return (T)object;
                Array.set(t2, n3, arrayList.get(n3));
                ++n3;
            } while (true);
        }
        Object var5_9 = null;
        for (int i2 = object.size() - 1; var5_10 == null && i2 >= 0; --i2) {
            kv kv3 = (kv)object.get(i2);
            if (!this.dd(kv3.tag) || kv3.adZ.length == 0) continue;
            kv kv4 = kv3;
        }
        if (var5_10 != null) return this.adV.cast(this.o(kn.n(var5_10.adZ)));
        return null;
    }

    /*
     * Exception decompiling
     */
    protected Object o(kn var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:366)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }
}

