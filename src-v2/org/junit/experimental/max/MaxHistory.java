/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.max;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.junit.experimental.max.CouldNotReadCoreException;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class MaxHistory
implements Serializable {
    private static final long serialVersionUID = 1;
    private final Map<String, Long> fDurations = new HashMap<String, Long>();
    private final Map<String, Long> fFailureTimestamps = new HashMap<String, Long>();
    private final File fHistoryStore;

    private MaxHistory(File file) {
        this.fHistoryStore = file;
    }

    public static MaxHistory forFolder(File file) {
        if (file.exists()) {
            try {
                MaxHistory maxHistory = MaxHistory.readHistory(file);
                return maxHistory;
            }
            catch (CouldNotReadCoreException var1_2) {
                var1_2.printStackTrace();
                file.delete();
            }
        }
        return new MaxHistory(file);
    }

    /*
     * Exception decompiling
     */
    private static MaxHistory readHistory(File var0) throws CouldNotReadCoreException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:371)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:449)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
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

    private void save() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.fHistoryStore));
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    Long getFailureTimestamp(Description description) {
        return this.fFailureTimestamps.get(description.toString());
    }

    Long getTestDuration(Description description) {
        return this.fDurations.get(description.toString());
    }

    boolean isNewTest(Description description) {
        if (!this.fDurations.containsKey(description.toString())) {
            return true;
        }
        return false;
    }

    public RunListener listener() {
        return new RememberingListener();
    }

    void putTestDuration(Description description, long l2) {
        this.fDurations.put(description.toString(), l2);
    }

    void putTestFailureTimestamp(Description description, long l2) {
        this.fFailureTimestamps.put(description.toString(), l2);
    }

    public Comparator<Description> testComparator() {
        return new TestComparator();
    }

    private final class RememberingListener
    extends RunListener {
        private long overallStart;
        private Map<Description, Long> starts;

        private RememberingListener() {
            this.overallStart = System.currentTimeMillis();
            this.starts = new HashMap<Description, Long>();
        }

        @Override
        public void testFailure(Failure failure) throws Exception {
            MaxHistory.this.putTestFailureTimestamp(failure.getDescription(), this.overallStart);
        }

        @Override
        public void testFinished(Description description) throws Exception {
            long l2 = System.nanoTime();
            long l3 = this.starts.get(description);
            MaxHistory.this.putTestDuration(description, l2 - l3);
        }

        @Override
        public void testRunFinished(Result result) throws Exception {
            MaxHistory.this.save();
        }

        @Override
        public void testStarted(Description description) throws Exception {
            this.starts.put(description, System.nanoTime());
        }
    }

    private class TestComparator
    implements Comparator<Description> {
        private TestComparator() {
        }

        private Long getFailure(Description serializable) {
            Long l2;
            serializable = l2 = MaxHistory.this.getFailureTimestamp((Description)serializable);
            if (l2 == null) {
                serializable = Long.valueOf(0);
            }
            return serializable;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int compare(Description description, Description description2) {
            int n2;
            if (MaxHistory.this.isNewTest(description)) {
                return -1;
            }
            if (MaxHistory.this.isNewTest(description2)) {
                return 1;
            }
            int n3 = n2 = this.getFailure(description2).compareTo(this.getFailure(description));
            if (n2 != 0) return n3;
            return MaxHistory.this.getTestDuration(description).compareTo(MaxHistory.this.getTestDuration(description2));
        }
    }

}

