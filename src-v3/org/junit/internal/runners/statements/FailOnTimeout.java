package org.junit.internal.runners.statements;

import org.junit.runners.model.Statement;

public class FailOnTimeout extends Statement {
    private final Statement fOriginalStatement;
    private final long fTimeout;

    private static class StatementThread extends Thread {
        private Throwable fExceptionThrownByOriginalStatement;
        private boolean fFinished;
        private StackTraceElement[] fRecordedStackTrace;
        private final Statement fStatement;

        public StatementThread(Statement statement) {
            this.fFinished = false;
            this.fExceptionThrownByOriginalStatement = null;
            this.fRecordedStackTrace = null;
            this.fStatement = statement;
        }

        public void recordStackTrace() {
            this.fRecordedStackTrace = getStackTrace();
        }

        public StackTraceElement[] getRecordedStackTrace() {
            return this.fRecordedStackTrace;
        }

        public void run() {
            try {
                this.fStatement.evaluate();
                this.fFinished = true;
            } catch (InterruptedException e) {
            } catch (Throwable e2) {
                this.fExceptionThrownByOriginalStatement = e2;
            }
        }
    }

    public FailOnTimeout(Statement originalStatement, long timeout) {
        this.fOriginalStatement = originalStatement;
        this.fTimeout = timeout;
    }

    public void evaluate() throws Throwable {
        StatementThread thread = evaluateStatement();
        if (!thread.fFinished) {
            throwExceptionForUnfinishedThread(thread);
        }
    }

    private StatementThread evaluateStatement() throws InterruptedException {
        StatementThread thread = new StatementThread(this.fOriginalStatement);
        thread.start();
        thread.join(this.fTimeout);
        if (!thread.fFinished) {
            thread.recordStackTrace();
        }
        thread.interrupt();
        return thread;
    }

    private void throwExceptionForUnfinishedThread(StatementThread thread) throws Throwable {
        if (thread.fExceptionThrownByOriginalStatement != null) {
            throw thread.fExceptionThrownByOriginalStatement;
        }
        throwTimeoutException(thread);
    }

    private void throwTimeoutException(StatementThread thread) throws Exception {
        Exception exception = new Exception(String.format("test timed out after %d milliseconds", new Object[]{Long.valueOf(this.fTimeout)}));
        exception.setStackTrace(thread.getRecordedStackTrace());
        throw exception;
    }
}
