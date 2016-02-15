/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners.statements;

import org.junit.runners.model.Statement;

public class FailOnTimeout
extends Statement {
    private final Statement fOriginalStatement;
    private final long fTimeout;

    public FailOnTimeout(Statement statement, long l2) {
        this.fOriginalStatement = statement;
        this.fTimeout = l2;
    }

    private StatementThread evaluateStatement() throws InterruptedException {
        StatementThread statementThread = new StatementThread(this.fOriginalStatement);
        statementThread.start();
        statementThread.join(this.fTimeout);
        if (!statementThread.fFinished) {
            statementThread.recordStackTrace();
        }
        statementThread.interrupt();
        return statementThread;
    }

    private void throwExceptionForUnfinishedThread(StatementThread statementThread) throws Throwable {
        if (statementThread.fExceptionThrownByOriginalStatement != null) {
            throw statementThread.fExceptionThrownByOriginalStatement;
        }
        this.throwTimeoutException(statementThread);
    }

    private void throwTimeoutException(StatementThread statementThread) throws Exception {
        Exception exception = new Exception(String.format("test timed out after %d milliseconds", this.fTimeout));
        exception.setStackTrace(statementThread.getRecordedStackTrace());
        throw exception;
    }

    @Override
    public void evaluate() throws Throwable {
        StatementThread statementThread = this.evaluateStatement();
        if (!statementThread.fFinished) {
            this.throwExceptionForUnfinishedThread(statementThread);
        }
    }

    private static class StatementThread
    extends Thread {
        private Throwable fExceptionThrownByOriginalStatement = null;
        private boolean fFinished = false;
        private StackTraceElement[] fRecordedStackTrace = null;
        private final Statement fStatement;

        public StatementThread(Statement statement) {
            this.fStatement = statement;
        }

        public StackTraceElement[] getRecordedStackTrace() {
            return this.fRecordedStackTrace;
        }

        public void recordStackTrace() {
            this.fRecordedStackTrace = this.getStackTrace();
        }

        @Override
        public void run() {
            try {
                this.fStatement.evaluate();
                this.fFinished = true;
                return;
            }
            catch (Throwable var1_1) {
                this.fExceptionThrownByOriginalStatement = var1_1;
                return;
            }
            catch (InterruptedException var1_2) {
                return;
            }
        }
    }

}

