/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.dk;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class az
extends aj {
    private static final String ID = com.google.android.gms.internal.a.ac.toString();
    private static final String XQ = b.bi.toString();
    private static final String Ym = b.cL.toString();
    private static final String Yn = b.cO.toString();
    private static final String Yo = b.co.toString();

    public az() {
        super(ID, XQ);
    }

    private String a(String string2, a iterator, Set<Character> object) {
        switch (.Yp[iterator.ordinal()]) {
            default: {
                return string2;
            }
            case 1: {
                try {
                    iterator = dk.cd(string2);
                    return iterator;
                }
                catch (UnsupportedEncodingException var2_3) {
                    bh.b("Joiner: unsupported encoding", var2_3);
                    return string2;
                }
            }
            case 2: 
        }
        string2 = string2.replace("\\", "\\\\");
        iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next().toString();
            string2 = string2.replace((CharSequence)object, "\\" + (String)object);
        }
        return string2;
    }

    private void a(StringBuilder stringBuilder, String string2, a a2, Set<Character> set) {
        stringBuilder.append(this.a(string2, a2, set));
    }

    private void a(Set<Character> set, String string2) {
        for (int i2 = 0; i2 < string2.length(); ++i2) {
            set.add(Character.valueOf(string2.charAt(i2)));
        }
    }

    @Override
    public boolean jX() {
        return true;
    }

    /*
     * Exception decompiling
     */
    @Override
    public d.a x(Map<String, d.a> var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
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

    private static enum a {
        Yq,
        Yr,
        Ys;
        

        private a() {
        }
    }

}

