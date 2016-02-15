/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package com.uservoice.uservoicesdk.babayaga;

import android.os.AsyncTask;
import java.util.Map;

public class BabayagaTask
extends AsyncTask<String, String, Void> {
    private final String event;
    private final Map<String, Object> eventProps;
    private final Map<String, Object> traits;
    private final String uvts;

    public BabayagaTask(String string2, String string3, Map<String, Object> map, Map<String, Object> map2) {
        this.event = string2;
        this.uvts = string3;
        this.traits = map;
        this.eventProps = map2;
    }

    /*
     * Exception decompiling
     */
    protected /* varargs */ Void doInBackground(String ... var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException: Backjump on non jumping statement [] lbl135 : TryStatement: try { 4[TRYBLOCK]

        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:44)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:22)
        // org.benf.cfr.reader.util.graph.GraphVisitorDFS.process(GraphVisitorDFS.java:68)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner.removeUnreachableCode(Cleaner.java:54)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.RemoveDeterministicJumps.apply(RemoveDeterministicJumps.java:35)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:507)
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

